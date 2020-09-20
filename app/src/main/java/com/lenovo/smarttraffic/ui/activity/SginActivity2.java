package com.lenovo.smarttraffic.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.lenovo.smarttraffic.InitApp;
import com.lenovo.smarttraffic.R;

import java.util.Timer;
import java.util.TimerTask;

public class SginActivity2 extends BaseActivity{
    @Override
    protected int getLayout() {
        return R.layout.activity_sgin2;
    }

    private ProgressBar progressBar;
    private ImageView img_sign;
    private Timer timer;
    private TimerTask timerTask;
    private int status;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            progressBar.setVisibility(View.GONE);
            img_sign.setVisibility(View.VISIBLE);
            click();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolBar(findViewById(R.id.toolbar),true,"签到");

        progressBar = findViewById(R.id.pBar);
        img_sign = findViewById(R.id.img_sign);
        init();
    }

    public void init(){
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                status = status + 5;
                progressBar.setProgress(status);
                if (status == 100){
                    progressBar.setProgress(status);
                    handler.sendEmptyMessage(0);
                }
            }
        };
    }

    public void click(){
        if (InitApp.sp.getBoolean("isQian",false)){
            InitApp.toast("您已签到!");
        }else {
            InitApp.toast("签到有奖励，积分+100!");
            InitApp.edit.putBoolean("isQian",true).commit();
        }
    }
}
