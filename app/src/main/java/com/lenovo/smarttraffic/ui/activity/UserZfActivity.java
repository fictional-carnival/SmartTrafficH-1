package com.lenovo.smarttraffic.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.lenovo.smarttraffic.InitApp;
import com.lenovo.smarttraffic.R;
import com.lenovo.smarttraffic.bean.C;
import com.lenovo.smarttraffic.ui.adapter.BasePagerAdapter;
import com.lenovo.smarttraffic.util.CommonUtil;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;

/**
 * @author Amoly
 * @date 2019/4/11.
 * description：
 */

public class UserZfActivity extends BaseActivity {

    private int index;
    private String pid;
    private List<C.ROWSDETAILBean> ccs;


    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.im1)
    ImageView im1;
    private Timer timer;
    private int index2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        index = getIntent().getIntExtra("index", 0);
        pid = getIntent().getStringExtra("pid");

        ccs = InitApp.gson.fromJson(InitApp.sp.getString(pid, ""), new TypeToken<List<C.ROWSDETAILBean>>() {
        }.getType());
        InitView();
    }
    @Override
    protected int getLayout() {
        return R.layout.activity_userzf;
    }

    private void InitView() {
        initToolBar(findViewById(R.id.toolbar), true, "违章支付");
        im1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                tv1.setVisibility(View.VISIBLE);
                tv1.setText("车牌号："+ccs.get(index).getCarnumber()+" 支付金额："+ccs.get(index).getB().getPmoney()+"元");
                ccs.get(index).setIsOk(1);
                InitApp.edit.putString(pid, InitApp.gson.toJson(ccs)).commit();
                finish();

                return false;
            }
        });


        timer = new Timer();
        index2 = 0;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        index2++;
                        if (index2 % 2 == 1) {
                            im1.setImageResource(R.drawable.erweima2);
                        } else {
                            im1.setImageResource(R.mipmap.icon_erweima);

                        }
                    }
                });
            }
        },0,5000);

    }

    @Override
    protected void onDestroy() {
        timer.cancel();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}
