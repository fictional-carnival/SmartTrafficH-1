package com.lenovo.smarttraffic.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lenovo.smarttraffic.InitApp;
import com.lenovo.smarttraffic.R;
import com.lenovo.smarttraffic.ui.adapter.BasePagerAdapter;
import com.lenovo.smarttraffic.ui.fragment.NewsFragment;
import com.lenovo.smarttraffic.util.CommonUtil;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * @author Amoly
 * @date 2019/4/11.
 * description：
 */

public class Item1Activity extends BaseActivity {
    @BindView(R.id.tab_layout_list)
    TabLayout tabLayoutList;
    @BindView(R.id.header_layout)
    LinearLayout headerLayout;
    @BindView(R.id.iv_addlabel)
    ImageView addLabel;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    private BasePagerAdapter basePagerAdapter;
    private ArrayList<NewsFragment> fragments;
    private ArrayList<String> titles;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InitView();
        InitData();
    }
    @Override
    protected int getLayout() {
        return R.layout.activity_list_tab;
    }

    private void InitView() {
        initToolBar(findViewById(R.id.toolbar), true, getString(R.string.item1));
        tabLayoutList.setupWithViewPager(viewPager);
        tabLayoutList.setTabMode(TabLayout.MODE_SCROLLABLE);
        headerLayout.setBackgroundColor(CommonUtil.getInstance().getColor());
    }

    private void InitData() {
        fragments = new ArrayList<>();
        titles = new ArrayList<>();
        initLabel();
        basePagerAdapter = new BasePagerAdapter(getSupportFragmentManager(),fragments,titles);
        viewPager.setAdapter(basePagerAdapter);
        viewPager.setOffscreenPageLimit(2);
        addLabel.setOnClickListener(view -> {
            startActivity(new Intent(this,AddLabelActivity.class));
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                fragments.get(position).setNew();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initLabel() {
        fragments.clear();
        titles.clear();
        String dingyue = InitApp.sp.getString("dingyue", null);
        if (null == dingyue) {
            titles = new ArrayList<>(InitApp.label.subList(0, 4));
        } else {
            titles = InitApp.gson.fromJson(dingyue, ArrayList.class);
        }
        for (int i = 0, l = titles.size(); i < l; i++) {
            NewsFragment newsFragment = new NewsFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("category", InitApp.label.indexOf(titles.get(i)) + 1);
            newsFragment.setArguments(bundle);
            fragments.add(newsFragment);
        }
        fragments.get(0).setNew();
    }


    @Override
    protected void onResume() {
        super.onResume();
        headerLayout.setBackgroundColor(CommonUtil.getInstance().getColor());
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initLabel();
        basePagerAdapter.recreateItems(fragments, titles);
        viewPager.setCurrentItem(0);
        tabLayoutList.setScrollPosition(0,0,false);
    }
}
