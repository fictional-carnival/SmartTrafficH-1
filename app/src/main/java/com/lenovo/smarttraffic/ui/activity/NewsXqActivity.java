package com.lenovo.smarttraffic.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.lenovo.smarttraffic.InitApp;
import com.lenovo.smarttraffic.R;
import com.lenovo.smarttraffic.bean.News;

import butterknife.BindView;

public class NewsXqActivity extends BaseActivity {

    @BindView(R.id.tv_name)
    TextView tv_naem;
    @BindView(R.id.tv_time)
    TextView tv_time;
    @BindView(R.id.tv_text)
    TextView tv_text;
    @Override
    protected int getLayout() {
        return R.layout.activity_newsxq;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        News.ROWSDETAILBean item = InitApp.gson.fromJson(getIntent().getStringExtra("item"), News.ROWSDETAILBean.class);
        initToolBar(findViewById(R.id.toolbar), true, item.getTitle());
        tv_naem.setText("分类：" + item.getTitle());
        tv_time.setText("时间：" + item.getCreatetime());
        tv_text.setText("\u3000\u3000"+item.getContent());
    }
}
