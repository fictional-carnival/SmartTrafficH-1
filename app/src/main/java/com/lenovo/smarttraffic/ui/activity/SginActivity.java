package com.lenovo.smarttraffic.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.lenovo.smarttraffic.R;

import java.util.Timer;
import java.util.TimerTask;

public class SginActivity extends BaseActivity{
    @Override
    protected int getLayout() {
        return R.layout.activity_sgin;
    }

    private ProgressBar progressBar;
    private WebView webView;
    private Timer timer;
    private TimerTask timerTask;
    private int status;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            progressBar.setVisibility(View.GONE);
            timer.cancel();
            initWeb();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolBar(findViewById(R.id.toolbar),true,"用户签到");

        progressBar = findViewById(R.id.pBar);
        webView = findViewById(R.id.webView);

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

    public void initWeb(){
        webView.addJavascriptInterface(new App(),"app");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
                return false;
            }
        });
        webView.loadUrl("file:///android_asset/www/index.html");
    }

    class App{
        @JavascriptInterface
        public void jump(){
            startActivity(new Intent(getApplicationContext(),SginActivity2.class));
        }
    }
}
