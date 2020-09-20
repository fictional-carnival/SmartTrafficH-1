package com.lenovo.smarttraffic.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.lenovo.smarttraffic.InitApp;
import com.lenovo.smarttraffic.R;

import butterknife.BindView;

public class SettingsActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.bt_exit)
    Button bt_exit;
    @Override
    protected int getLayout() {
        return R.layout.activity_settings;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolBar(findViewById(R.id.toolbar), true, getString(R.string.nav_setting));
        if (!InitApp.isLogin) {
            bt_exit.setBackgroundResource(R.drawable.exit_no);
        }
    }

    @Override
    public void onClick(View view) {
        if (InitApp.isLogin) {
            switch (view.getId()) {
                case R.id.ll_etc:
//                    startActivity(new Intent(this, ETCActivty.class));
                    break;
                case R.id.ll_qd:
//                    startActivity(new Intent(this, SignActivity.class));
                    break;
                case R.id.bt_exit:
                    InitApp.isLogin = false;
                    bt_exit.setBackgroundResource(R.drawable.exit_no);
                    InitApp.toast("退出成功");
                    break;
            }
        }else {
            InitApp.toast("您未登录，请登录后查看");
        }
    }
}
