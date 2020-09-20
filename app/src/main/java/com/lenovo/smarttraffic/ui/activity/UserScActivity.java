package com.lenovo.smarttraffic.ui.activity;

import android.content.Intent;
import android.graphics.Color;
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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;

/**
 * @author Amoly
 * @date 2019/4/11.
 * description：
 */

public class UserScActivity extends BaseActivity {
    @BindView(R.id.lv1)
    ListView lv1;
    private UserAdapter userAdapter;
    private List<User.ROWSDETAILBean> userarray;
    private ArrayList<User.ROWSDETAILBean> userarray2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        InitView();
        InitData();
    }
    @Override
    protected int getLayout() {
        return R.layout.activity_user;
    }

    private void InitView() {
        initToolBar(findViewById(R.id.toolbar), true, "用户收藏");
        String users = InitApp.sp.getString("users", "");
        userarray = InitApp.gson.fromJson(users, new TypeToken<List<User.ROWSDETAILBean>>(){}.getType());
        update();
        userAdapter = new UserAdapter();
        lv1.setAdapter(userAdapter);

    }

    private void update() {
        userarray2 = new ArrayList<>();

        for (int i = 0; i < userarray.size(); i++) {
            if (userarray.get(i).getSc() == 1) {
                userarray2.add(userarray.get(i));
            }
        }
        userarray2.sort(new Comparator<User.ROWSDETAILBean>() {
            @Override
            public int compare(User.ROWSDETAILBean rowsdetailBean, User.ROWSDETAILBean t1) {
                return t1.getZd() - rowsdetailBean.getZd();
            }
        });

    }

    private void InitData() {

    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    private class UserAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return userarray2.size();
        }

        @Override
        public User.ROWSDETAILBean getItem(int i) {
            return userarray2.get(i);
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
            tv_4.setText(item.getPregisterdate());
            if (item.getZj() == 1) {
                tv_5.setText("一般管理员");
            } else {
                tv_5.setText("普通用户");
            }

            tv_6.setText("已收藏");

                tv_7.setText("取消收藏");

            if (item.getZd() == 1) {
                tv_8.setText("取消置顶");
                cv.setBackgroundColor(Color.parseColor("#cccccc"));
            } else {
                tv_8.setText("置顶");
                cv.setBackgroundColor(Color.parseColor("#ffffff"));
            }
            im1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), UserWzActivity.class);
                    intent.putExtra("pid", item.getPcardid());
                    startActivity(intent);
                }
            });
            tv_7.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (int j = 0; j < userarray.size(); j++) {
                        if (userarray.get(j).getUsername().equals(item.getUsername())) {
                            userarray.get(j).setSc(0);
                            userarray.get(j).setZd(0);
                            InitApp.edit.putString("users", InitApp.gson.toJson(userarray)).commit();
                            break;
                        }
                    }
                    update();
                    notifyDataSetChanged();
                    hz1.scrollTo(0,0);
                }
            });
            tv_8.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (item.getZd() == 1) {
                        for (int j = 0; j < userarray.size(); j++) {
                            if (userarray.get(j).getUsername().equals(item.getUsername())) {
                                userarray.get(j).setZd(0);
                                InitApp.edit.putString("users", InitApp.gson.toJson(userarray)).commit();
                                break;
                            }
                        }
                    } else {
                        for (int j = 0; j < userarray.size(); j++) {
                            if (userarray.get(j).getUsername().equals(item.getUsername())) {
                                userarray.get(j).setZd(1);
                                InitApp.edit.putString("users", InitApp.gson.toJson(userarray)).commit();
                                break;
                            }
                        }
                    }
                    update();
                    notifyDataSetChanged();
                    hz1.scrollTo(0,0);
                }
            });




            return cv;
        }
    }

}
