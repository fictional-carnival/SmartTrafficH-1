package com.lenovo.smarttraffic.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.EditText;

import com.lenovo.smarttraffic.InitApp;
import com.lenovo.smarttraffic.R;

import butterknife.BindView;
import butterknife.OnClick;

public class IPSettingActivity extends BaseActivity {
    @BindView(R.id.ip1)
    EditText ip1;
    @BindView(R.id.ip2)
    EditText ip2;
    @BindView(R.id.ip3)
    EditText ip3;
    @BindView(R.id.ip4)
    EditText ip4;


    @Override
    protected int getLayout() {
        return R.layout.activity_ipsetting;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolBar(findViewById(R.id.toolbar), true, "IP设置");
        String[] ips = InitApp.sp.getString("ip", "192.168.1.120").split("\\.");

        ip1.setHint(ips[0]);
        ip2.setHint(ips[1]);
        ip3.setHint(ips[2]);
        ip4.setHint(ips[3]);

    }

    @OnClick(R.id.ipqd)
    public void ipdq() {
        String ip = ip1.getText() + "." + ip2.getText() + "." + ip3.getText() + "." + ip4.getText();
        String regex = "((2[0-4]\\d|25[0-5]|[0,1]?\\d\\d?)\\.){3}(2[0-4]\\d|25[0-5]|[0,1]?\\d\\d)";
//        String regex = "((2[0-4]\\d|25[0-5]|[0,1]?\\d\\d?)\\.){3}(2[0-4]\\d|25[0-5]|[0,1]?\\d\\d)";
        if (!ip.matches(regex)) {
            InitApp.toast("您输入的有误，请重新输入");
            ip1.setText(null);
            ip2.setText(null);
            ip3.setText(null);
            ip4.setText(null);
        } else {
            InitApp.edit.putString("ip", ip).commit();
            InitApp.toast("设置成功");
            finish();
        }
    }
}
