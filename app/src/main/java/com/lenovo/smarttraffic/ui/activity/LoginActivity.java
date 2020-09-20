package com.lenovo.smarttraffic.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.lenovo.smarttraffic.InitApp;
import com.lenovo.smarttraffic.R;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * @author Amoly
 * @date 2019/4/11.
 * description：
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private EditText mEditTextName;
    private EditText mEditTextPassword;
    private TextInputLayout mTextInputLayoutName;
    private TextInputLayout mTextInputLayoutPswd;
    private CheckBox jz;
    private CheckBox zd;


    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolBar(findViewById(R.id.toolbar), true, getString(R.string.login));

        mTextInputLayoutName = findViewById(R.id.textInputLayoutName);
        mTextInputLayoutPswd = findViewById(R.id.textInputLayoutPassword);
        jz = findViewById(R.id.jzpwdCB);
        mEditTextName = findViewById(R.id.editTextName);
        mTextInputLayoutName.setErrorEnabled(true);
        mEditTextPassword = findViewById(R.id.editTextPassword);
        mTextInputLayoutPswd.setErrorEnabled(true);
        Button loginButton = findViewById(R.id.loginBtn);
        boolean jzmm = InitApp.sp.getBoolean("jzmm", false);
        if (jzmm) {
            mEditTextName.setText(InitApp.sp.getString("UserName", null));
            mEditTextPassword.setText(InitApp.sp.getString("UserPwd",null));
        }
        jz.setChecked(jzmm);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.loginBtn:
                boolean name = isNull(mEditTextName.getText(), mTextInputLayoutName, getString(R.string.error_login_empty));
                boolean pwd = isNull(mEditTextPassword.getText(), mTextInputLayoutPswd, getString(R.string.error_pwd_empty));
                if (name && pwd) {
                    login();
                }
                break;
            case R.id.jzpwdCB:
                InitApp.edit.putBoolean("jzmm", jz.isChecked()).commit();
                break;
        }
    }

    private void login() {
        String username = mEditTextName.getText().toString().trim();
        String userpwd = mEditTextPassword.getText().toString().trim();
        HashMap hashMap = new HashMap();
        hashMap.put("UserName", username);
        hashMap.put("UserPwd", userpwd);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, InitApp.url + "user_login", new JSONObject(hashMap), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                InitApp.edit.putString("UserName", username);
                InitApp.edit.putString("UserPwd", userpwd);
                InitApp.edit.putBoolean("jzmm", jz.isChecked());
                InitApp.edit.commit();
                InitApp.isLogin = true;
                InitApp.toast("登录成功");
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (volleyError.networkResponse != null) {
                    if (volleyError.networkResponse.statusCode == 400) {
                        mEditTextName.setText(null);
                        mEditTextPassword.setText(null);
                        InitApp.toast("用户名或密码错误");
                    }
                } else {
                    InitApp.toast("网络连接故障");
                }
            }
        });
        InitApp.queue.add(request);
    }

    private boolean isNull(Editable text, TextInputLayout mTextInputLayoutName, String string) {
        if (TextUtils.isEmpty(text)) {
            mTextInputLayoutName.setError(string);
            return false;
        } else {
            mTextInputLayoutName.setError(null);
            return true;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.net_setting, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.net_setting) {
            startActivity(new Intent(this, IPSettingActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}
