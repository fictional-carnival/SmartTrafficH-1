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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.lenovo.smarttraffic.InitApp;
import com.lenovo.smarttraffic.R;
import com.lenovo.smarttraffic.bean.B;
import com.lenovo.smarttraffic.bean.C;
import com.lenovo.smarttraffic.bean.User;
import com.lenovo.smarttraffic.bean.car;
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

public class UserWzActivity extends BaseActivity {

    private String pid;

    @BindView(R.id.im1)
    ImageView im1;
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.tv3)
    TextView tv3;
    @BindView(R.id.lv1)
    ListView lv1;

    @BindView(R.id.ll1)
    LinearLayout ll1;
    @BindView(R.id.tvtext)
    TextView tvtext;
    private ArrayList<C.ROWSDETAILBean> wzs;
    private WzAdapter wzAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pid = getIntent().getStringExtra("pid");
        InitView();
        InitData();
    }
    @Override
    protected int getLayout() {
        return R.layout.activity_userwz;
    }

    private void InitView() {
        initToolBar(findViewById(R.id.toolbar), true, "违章详情");

    }

    private void InitData() {

        String string = InitApp.sp.getString(pid, "");

        List<User.ROWSDETAILBean> users = InitApp.gson.fromJson(InitApp.sp.getString("users", ""), new TypeToken<List<User.ROWSDETAILBean>>() {
        }.getType());
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getPcardid().equals(pid)) {
                tv1.setText("姓名："+users.get(i).getPname());
                tv2.setText("性别："+users.get(i).getPsex());
                tv3.setText("手机号码："+users.get(i).getPtel());
                if (users.get(i).getPsex().equals("男")) {
                    im1.setImageResource(R.mipmap.touxiang_2);
                } else {
                    im1.setImageResource(R.mipmap.touxiang_1);
                }
            }
        }
        List<car.ROWSDETAILBean> cars = InitApp.gson.fromJson(InitApp.getData("car"), car.class).getROWS_DETAIL();
        List<B.ROWSDETAILBean> BSS = InitApp.gson.fromJson(InitApp.getData("cartype"), B.class).getROWS_DETAIL();
        List<C.ROWSDETAILBean> CSS = InitApp.gson.fromJson(InitApp.getData("carwz"), C.class).getROWS_DETAIL();

        List<car.ROWSDETAILBean> carsS = new ArrayList<>();
        for (int i = 0; i < cars.size(); i++) {
            if (pid.equals(cars.get(i).getPcardid())) {
                carsS.add(cars.get(i));
            }
        }
        if (carsS.size() == 0) {
            ll1.setVisibility(View.GONE);
            tvtext.setVisibility(View.VISIBLE);
            tvtext.setText("该用户无车信息");
        } else {
            wzs = new ArrayList<>();
            if (string.equals("")) {
                for (int i = 0; i < carsS.size(); i++) {
                    for (int j = 0; j < CSS.size(); j++) {
                        if (CSS.get(j).getCarnumber().equals(carsS.get(i).getCarnumber())) {
                            CSS.get(j).setCarband(carsS.get(i).getCarbrand());
                            for (int k = 0; k < BSS.size(); k++) {
                                if (CSS.get(j).getPcode().equals(BSS.get(k).getPcode())) {
                                    CSS.get(j).setB(BSS.get(k));
                                    wzs.add(CSS.get(j));
                                    break;
                                }
                            }
                        }
                    }
                }
                InitApp.edit.putString(pid, InitApp.gson.toJson(wzs)).commit();
            } else {
                wzs = InitApp.gson.fromJson(string, new TypeToken<List<C.ROWSDETAILBean>>() {
                }.getType());
            }

            if (wzs.size() != 0) {
                wzAdapter = new WzAdapter();
                lv1.setAdapter(wzAdapter);
            } else {
                ll1.setVisibility(View.GONE);
                tvtext.setVisibility(View.VISIBLE);
                tvtext.setText("该用户无违章信息");
            }
        }

    }


    @Override
    protected void onResume() {
        InitData();
        super.onResume();
    }

    private class WzAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return wzs.size();
        }

        @Override
        public C.ROWSDETAILBean getItem(int i) {
            return wzs.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            View cv;
            if (view == null) {
                cv = View.inflate(getApplicationContext(), R.layout.wz_list, null);
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

            C.ROWSDETAILBean item = getItem(i);
            int mipmap = getResources().getIdentifier(item.getCarband(), "mipmap", getPackageName());
            im1.setImageResource(mipmap);
            tv_1.setText(i + 1 + "");

            tv_2.setText(item.getCarnumber());
            tv_3.setText(item.getPaddr());
            String premarks = item.getB().getPremarks();
            tv_4.setText(premarks.replaceAll(premarks.substring(2,premarks.length()-3),"..."));
            if (item.getB().getPscore() == 0) {
                tv_5.setText("无");
            } else {
                tv_5.setText(item.getB().getPscore()+"");
            }
            if (item.getB().getPmoney() == 0) {
                tv_6.setText("无");
            } else {
                tv_6.setText(item.getB().getPmoney()+"");
            }
            tv_7.setText(item.getPdatetime().substring(0,19));
            if (item.getIsOk() == 1) {
                tv_8.setText("已处理");
                tv_8.setTextColor(Color.GREEN);
            }else{
                tv_8.setText("未处理");
                tv_8.setTextColor(Color.RED);
            }

            tv_8.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (item.getIsOk() == 0) {
                        Intent intent = new Intent(getApplicationContext(), UserZfActivity.class);
                        intent.putExtra("index", i);
                        intent.putExtra("pid", pid);
                        startActivity(intent);
                    } else {
                        InitApp.toast("已处理");
                    }
                }
            });


            return cv;
        }
    }
}
