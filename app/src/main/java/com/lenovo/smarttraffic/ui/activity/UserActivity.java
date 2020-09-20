package com.lenovo.smarttraffic.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.lenovo.smarttraffic.InitApp;
import com.lenovo.smarttraffic.R;
import com.lenovo.smarttraffic.bean.User;
import com.lenovo.smarttraffic.ui.adapter.BasePagerAdapter;
import com.lenovo.smarttraffic.util.CommonUtil;

import java.util.List;

import butterknife.BindView;

/**
 * @author Amoly
 * @date 2019/4/11.
 * description：
 */

public class UserActivity extends BaseActivity {


    @BindView(R.id.lv1)
    ListView lv1;
    private UserAdapter userAdapter;
    private List<User.ROWSDETAILBean> userarray;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        InitView();
    }
    @Override
    protected int getLayout() {
        return R.layout.activity_user;
    }

    private void InitView() {
        initToolBar(findViewById(R.id.toolbar), true, "用户中心");
        String users = InitApp.sp.getString("users", "");
        if (users.equals("")) {
            userarray = InitApp.gson.fromJson(InitApp.getData("User"), User.class).getROWS_DETAIL();

            InitApp.edit.putString("users", InitApp.gson.toJson(userarray)).commit();
        } else {
            userarray = InitApp.gson.fromJson(users, new TypeToken<List<User.ROWSDETAILBean>>(){}.getType());
        }

        userAdapter = new UserAdapter();
        lv1.setAdapter(userAdapter);

    }


    @Override
    protected void onResume() {
        super.onResume();
        InitView();
    }

    private class UserAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return userarray.size();
        }

        @Override
        public User.ROWSDETAILBean getItem(int i) {
            return userarray.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View cv;
            if (view == null) {
                cv = View.inflate(getApplicationContext(), R.layout.user_list, null);
            } else {
                cv = view;
            }
           ImageView im1 = cv.findViewById(R.id.im_1);
           TextView tv_1 = cv.findViewById(R.id.tv_1);
           TextView tv_2 = cv.findViewById(R.id.tv_2);
           TextView tv_3 = cv.findViewById(R.id.tv_3);
           TextView tv_4 = cv.findViewById(R.id.tv_4);
           TextView tv_5 = cv.findViewById(R.id.tv_5);
           TextView tv_6 = cv.findViewById(R.id.tv_6);
           TextView tv_7 = cv.findViewById(R.id.tv_7);
           TextView tv_8 = cv.findViewById(R.id.tv_8);
            HorizontalScrollView hz1 = cv.findViewById(R.id.hz_1);

            hz1.scrollTo(0,0);
            User.ROWSDETAILBean item = getItem(i);

            if (item.getPsex().equals("男")) {
                im1.setImageResource(R.mipmap.touxiang_2);
            } else {
                im1.setImageResource(R.mipmap.touxiang_1);

            }

            tv_1.setText("用户名："+item.getUsername());
            tv_2.setText("姓名："+item.getPname());
            tv_3.setText("电话："+item.getPtel());
            tv_4.setVisibility(View.GONE);
            tv_8.setVisibility(View.GONE);
            tv_4.setText(item.getPregisterdate());
            if (i < 5) {
                userarray.get(i).setZj(1);
                InitApp.edit.putString("users", InitApp.gson.toJson(userarray)).commit();
                tv_5.setText("一般管理员");
            } else {
                tv_5.setText("普通用户");
            }

            tv_6.setText("查询详情");
            if (item.getSc() == 1) {
                tv_7.setText("已收藏");
            } else {
                tv_7.setText("收藏");
            }
            im1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), UserWzActivity.class);
                    intent.putExtra("pid", item.getPcardid());
                    startActivity(intent);
                }
            });
            tv_6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getApplicationContext(), UserScActivity.class));
                }
            });
            tv_7.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (item.getSc() == 0) {
                        userarray.get(i).setSc(1);
                        InitApp.edit.putString("users", InitApp.gson.toJson(userarray)).commit();
                    } else {
                        InitApp.toast("已收藏");
                    }
                    notifyDataSetChanged();
                    hz1.scrollTo(0,0);
                }
            });
            return cv;
        }
    }
}
