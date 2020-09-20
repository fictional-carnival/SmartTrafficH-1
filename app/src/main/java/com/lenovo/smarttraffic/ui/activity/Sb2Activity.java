package com.lenovo.smarttraffic.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.lenovo.smarttraffic.InitApp;
import com.lenovo.smarttraffic.R;
import com.lenovo.smarttraffic.bean.D;
import com.lenovo.smarttraffic.ui.adapter.BasePagerAdapter;
import com.lenovo.smarttraffic.util.CommonUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author Amoly
 * @date 2019/4/11.
 * description：
 */

public class Sb2Activity extends BaseActivity {

    private List<D.ROWSDETAILBean> subways;
    private int index;
    private D.ROWSDETAILBean ssb;

    @BindView(R.id.gv1)
    GridView gv1;
    private ArrayList<TextView> tvs;
    private List<String> routes;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        index = getIntent().getIntExtra("index", 0);
        InitView();
        InitData();
    }
    @Override
    protected int getLayout() {
        return R.layout.activity_sub2;
    }

    private void InitView() {
        subways = InitApp.gson.fromJson(InitApp.getData("subway"), D.class).getROWS_DETAIL();
        ssb = subways.get(index);
        routes = ssb.getRoute();
        initToolBar(findViewById(R.id.toolbar), true, "地铁"+ssb.getMetro_code()+"线路详情");
        tvs = new ArrayList<TextView>() {{
            add(findViewById(R.id.tv1));
            add(findViewById(R.id.tv2));
            add(findViewById(R.id.tv3));
            add(findViewById(R.id.tv4));
            add(findViewById(R.id.tv5));
            add(findViewById(R.id.tv6));
            add(findViewById(R.id.tv7));
            add(findViewById(R.id.tv8));
        }};

        tvs.get(0).setText(ssb.getStart_place());
        tvs.get(1).setText("首班"+ssb.getStart_place_start_time());
        tvs.get(2).setText("末班"+ssb.getStart_place_end_time());
        tvs.get(3).setText(ssb.getEnd_place());
        tvs.get(4).setText("首班"+ssb.getEnd_place_start_time());
        tvs.get(5).setText("末班" + ssb.getStart_place_end_time());
        tvs.get(6).setText(routes.size()+"站/"+(routes.size()-1)*2+"公里");
        tvs.get(7).setText("票价：最高票价"+(float)((routes.size()-1)*0.4));

        gv1.setAdapter(new gAdapter());

    }

    private void InitData() {
        BasePagerAdapter basePagerAdapter = new BasePagerAdapter(getSupportFragmentManager());

    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    private class gAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return routes.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View cv;
            if (view == null) {
                cv = View.inflate(getApplicationContext(), R.layout.gv2_list, null);
            } else {
                cv = view;
            }
            TextView tv_1 = cv.findViewById(R.id.tv_1);

            tv_1.setText(routes.get(i));

            return cv;
        }
    }
}
