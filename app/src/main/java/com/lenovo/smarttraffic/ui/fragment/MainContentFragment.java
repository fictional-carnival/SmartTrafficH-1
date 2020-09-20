package com.lenovo.smarttraffic.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.lenovo.smarttraffic.Constant;
import com.lenovo.smarttraffic.InitApp;
import com.lenovo.smarttraffic.R;
import com.lenovo.smarttraffic.ui.activity.SbActivity;
import com.lenovo.smarttraffic.ui.activity.SginActivity;
import com.lenovo.smarttraffic.ui.activity.UserActivity;
import com.lenovo.smarttraffic.ui.activity.WeatherActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * @author Amoly
 * @date 2019/4/11.
 * description：主页面
 */
public class MainContentFragment extends BaseFragment {
    private static MainContentFragment instance = null;

    public static MainContentFragment getInstance() {
        if (instance == null) {
            instance = new MainContentFragment();
        }
        return instance;
    }

    private View view;
    private PieChart pieChart;
    private TextView tod_temp,tod_weather;
    private TextView tom_temp,tom_weather;
    private TextView tv_1,tv_2,tv_3,tv_4;
    private ImageView tod_img,tom_img;
    private LinearLayout ll_subway,ll_pay,ll_user,ll_sign,ll_weather;
    private List<PieEntry> entries;
    private TimerTask timerTask;
    private Timer timer;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            initChart();
            init2();
        }
    };
    private int x;

    @Override
    protected View getSuccessView() {
        view = View.inflate(getContext(),R.layout.fragment_home,null);

        pieChart = view.findViewById(R.id.pieChart);
        tod_temp = view.findViewById(R.id.today_temp);
        tod_weather = view.findViewById(R.id.today_weather);
        tod_img = view.findViewById(R.id.today_img);

        tom_img = view.findViewById(R.id.tom_img);
        tom_temp = view.findViewById(R.id.tom_temp);
        tom_weather = view.findViewById(R.id.tom_weather);

        ll_subway = view.findViewById(R.id.ll_subway);
        ll_pay = view.findViewById(R.id.ll_pay);
        ll_user = view.findViewById(R.id.ll_user);
        ll_sign = view.findViewById(R.id.ll_sign);
        ll_weather = view.findViewById(R.id.ll_weather);

        tv_1 = view.findViewById(R.id.tv_1);
        tv_2 = view.findViewById(R.id.tv_2);
        tv_3 = view.findViewById(R.id.tv_3);
        tv_4 = view.findViewById(R.id.tv_4);

        ll_weather.setOnClickListener(this::onClick);
        ll_sign.setOnClickListener(this::onClick);
        ll_user.setOnClickListener(this::onClick);
        ll_pay.setOnClickListener(this::onClick);
        ll_subway.setOnClickListener(this::onClick);

        initChart();
        refresh();
        init();
        init2();

        return view;
    }


    public void init(){
        tod_temp.setText("今日天气 22°");
        tod_weather.setText("晴");
        tod_img.setImageResource(R.mipmap.sun);

        tom_img.setImageResource(R.mipmap.cloudy);
        tom_temp.setText("明日天气 23~25°");
        tom_weather.setText("多云");

    }

    public void init2(){
        for (int i = 0; i < 4; i++) {
            switch (i){
                case 0:
                    x = InitApp.random(0,4000);
                    if (x > 0 && x < 1000){
                        tv_1.setText("弱");
                        tv_1.setTextColor(Color.parseColor("#4472c4"));
                    }else if (x >= 1000 && x <= 3000){
                        tv_1.setText("中等");
                        tv_1.setTextColor(Color.parseColor("#00b050"));
                    }else {
                        tv_1.setText("强");
                        tv_1.setTextColor(Color.parseColor("#ff0000"));
                    }
                    break;
                case 1:
                    x = InitApp.random(0,7000);
                    if (x > 0 && x < 3000){
                        tv_2.setText("适宜");
                        tv_2.setTextColor(Color.parseColor("#44dc68"));
                    }else if (x >= 3000 && x <= 6000){
                        tv_2.setText("中");
                        tv_2.setTextColor(Color.parseColor("#ffc000"));
                    }else {
                        tv_2.setText("较不宜");
                        tv_2.setTextColor(Color.parseColor("#8149ac"));
                    }
                    break;
                case 2:
                    x = InitApp.random(0,38);
                    if (x > 0 && x < 12){
                        tv_3.setText("冷");
                        tv_3.setTextColor(Color.parseColor("#3462f4"));
                    }else if (x >= 12 && x <= 21){
                        tv_3.setText("舒适");
                        tv_3.setTextColor(Color.parseColor("#92d050"));
                    }else if (x > 21 && x < 35){
                        tv_3.setText("温暖");
                        tv_3.setTextColor(Color.parseColor("#44dc68"));
                    }else {
                        tv_3.setText("热");
                        tv_3.setTextColor(Color.parseColor("#ff0000"));
                    }
                    break;
                case 3 :
                    x = InitApp.random(0,100);
                    if (x > 0 && x < 50){
                        tv_4.setText("较易发");
                        tv_4.setTextColor(Color.parseColor("#ff0000"));
                    }else {
                        tv_4.setText("少发");
                        tv_4.setTextColor(Color.parseColor("#ffff40"));
                    }
                    break;
            }
        }
    }

    public void refresh(){
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(0);
            }
        };
        timer.schedule(timerTask,0,3000);
    }

    public void initChart(){
        pieChart.clear();
        int x = InitApp.random(50,300);
        entries = new ArrayList<PieEntry>(){
            {
                add(new PieEntry(x,""));
                add(new PieEntry(300 - x,""));
            }
        };
        int[] colors = new int[]{Color.GREEN,Color.parseColor("#795548")};

        PieDataSet dataSet = new PieDataSet(entries,"");
        dataSet.setColors(colors);
        dataSet.setValueTextSize(0);

        PieData data = new PieData(dataSet);
        data.setDrawValues(true);
        pieChart.setData(data);
        pieChart.setHoleRadius(75);
        pieChart.setCenterTextColor(Color.BLACK);
        pieChart.setCenterTextSize(20f);
        pieChart.setTouchEnabled(false);
        pieChart.getLegend().setEnabled(false);
        pieChart.getDescription().setEnabled(false);
        if (x > 0 && x < 35){
            pieChart.setCenterText("优");
        }else if (x >= 35 && x <= 75){
            pieChart.setCenterText("良");
        }else if (x > 75 && x < 115){
            pieChart.setCenterText("轻度污染");
        }else if (x > 75 && x < 115){
            pieChart.setCenterText("中度污染");
        }else {
            pieChart.setCenterText("重度污染");
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        timer.cancel();
    }

    @Override
    protected Object requestData() {
        return Constant.STATE_SUCCEED;
    }

    @Override
    public void onClick(View view) {

        if (InitApp.isLogin){
            switch (view.getId()){
                case R.id.ll_weather:
                    startActivity(new Intent(getContext(), WeatherActivity.class));
                    break;
                case R.id.ll_pay:
                    startActivity(new Intent());
                    break;
                case R.id.ll_sign:
                    startActivity(new Intent(getContext(),SginActivity.class));
                    break;
                case R.id.ll_subway:
                    startActivity(new Intent(getContext(), SbActivity.class));

                    break;
                case R.id.ll_user:
                    startActivity(new Intent(getContext(), UserActivity.class));
                    break;
            }
        }else {
            InitApp.toast("您未登录，请登录后查看");
        }
    }

    @Override
    public void onDestroy() {
        if (instance != null) {
            instance = null;
        }
        super.onDestroy();
        timer.cancel();
    }


}
