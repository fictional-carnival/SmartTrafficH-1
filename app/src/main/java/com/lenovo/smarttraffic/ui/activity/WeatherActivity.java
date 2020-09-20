package com.lenovo.smarttraffic.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.lenovo.smarttraffic.InitApp;
import com.lenovo.smarttraffic.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class WeatherActivity extends BaseActivity{
    @Override
    protected int getLayout() {
        return R.layout.activity_weather;
    }

    private TextView month,week,sun,temp;
    private ImageView img;
    private GridView top,bom;
    private LineChart lineChart;
    private List<Entry> entries1,entries2;
    private long time;
    private int x;
    private Timer timer;
    private TimerTask timerTask;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            bom.setAdapter(new Bottom());
        }
    };

    private String[] strs = new String[]{"紫外线指数","空气污染指数","紫运动指数","紫穿衣指数","感冒指数","洗车指数"};
    private int[] imgs = new int[]{R.mipmap.w_1,R.mipmap.w_2,R.mipmap.w_3,
            R.mipmap.w_4,R.mipmap.w_5,R.mipmap.w_6};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolBar(findViewById(R.id.toolbar),true,"天气预报");

        month = findViewById(R.id.tv_month);
        week = findViewById(R.id.tv_week);
        temp = findViewById(R.id.tv_temp);
        sun = findViewById(R.id.tv_sun);
        img = findViewById(R.id.img);

        top = findViewById(R.id.top);
        bom = findViewById(R.id.bom);

        lineChart = findViewById(R.id.lineChart);

        init();
        initChart();
        refresh();
        top.setAdapter(new Top());
        bom.setAdapter(new Bottom());
        
    }

    public void init(){
        month.setText(InitApp.timeFormat(new Date(),"yyyy年MM月dd日"));
        week.setText("星期" + getWeek(new Date()));
        temp.setText(22 + "度");
        sun.setText("晴");
        img.setImageResource(R.mipmap.sun);
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
        lineChart.clear();
        entries1 = new ArrayList<>();
        entries2 = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            entries1.add(new Entry(i,InitApp.random(26,38)));
            entries2.add(new Entry(i,InitApp.random(16,24)));
        }

        LineDataSet dataSet1 = new LineDataSet(entries1,"");
        LineDataSet dataSet2 = new LineDataSet(entries2,"");

        dataSet1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        dataSet2.setMode(LineDataSet.Mode.CUBIC_BEZIER);

        dataSet1.setColor(Color.parseColor("#ffc000"));
        dataSet2.setColor(Color.parseColor("#3462f4"));

        dataSet1.setDrawCircleHole(false);
        dataSet2.setDrawCircleHole(false);

        dataSet1.setCircleColor(Color.parseColor("#ffc000"));
        dataSet2.setCircleColor(Color.parseColor("#ffc000"));

        dataSet1.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return (int) value + "°";
            }
        });
        dataSet2.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return (int) value + "°";
            }
        });

        LineData data = new LineData(dataSet1,dataSet2);
        lineChart.getAxisRight().setEnabled(false);
        lineChart.getAxisLeft().setEnabled(false);
        lineChart.setTouchEnabled(false);
        lineChart.getLegend().setEnabled(false);
        lineChart.getDescription().setEnabled(false);
        lineChart.getXAxis().setEnabled(false);
        lineChart.setData(data);
    }

    class Top extends BaseAdapter{

        @Override
        public int getCount() {
            return 6;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            if (convertView == null){
                view = View.inflate(getApplicationContext(),R.layout.grid_top,null);
            }else {
                view = convertView;
            }
            TextView month = view.findViewById(R.id.t_month);
            TextView week = view.findViewById(R.id.t_week);
            TextView sun = view.findViewById(R.id.t_sun);
            ImageView img = view.findViewById(R.id.t_img);

            if (position == 0){
                time = new Date().getTime();
                week.setText("(今日周" + getWeek(new Date(time)) + ")");
            }else {
                time = 1000 * 60 * 60 * 24;
                week.setText("周" + getWeek(new Date(time)));
            }
            month.setText(InitApp.timeFormat(new Date(time),"MM月dd日"));
            switch (position){
                case 0:
                    sun.setText("晴");
                    img.setImageResource(R.mipmap.sun);
                    break;
                case 1:
                    sun.setText("多云");
                    img.setImageResource(R.mipmap.cloudy);
                    break;
                case 2:
                    sun.setText("小雨");
                    img.setImageResource(R.mipmap.rain);
                    break;
                case 3:
                    sun.setText("小雨");
                    img.setImageResource(R.mipmap.rain);
                    break;
                case 4:
                    sun.setText("阴");
                    img.setImageResource(R.mipmap.cloudy_);
                    break;
                case 5:
                    sun.setText("阴");
                    img.setImageResource(R.mipmap.cloudy_);
                    break;
            }
            return view;
        }
    }

    class Bottom extends BaseAdapter{

        @Override
        public int getCount() {
            return 6;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            if (convertView == null){
                view = View.inflate(getApplicationContext(),R.layout.grid_bottom,null);
            }else {
                view = convertView;
            }
            TextView name = view.findViewById(R.id.b_1);
            TextView num = view.findViewById(R.id.b_2);
            TextView sta = view.findViewById(R.id.b_3);
            ImageView img = view.findViewById(R.id.b_img);

            name.setText(strs[position]);
            img.setImageResource(imgs[position]);

            switch (position){
                case 0:
                    x = InitApp.random(0,4000);
                    if (x > 0 && x < 1000){
                        num.setText(x + "");
                        sta.setText("弱");
                        sta.setBackgroundColor(Color.parseColor("#4472c4"));
                    }else if (x >= 1000 && x <= 3000){
                        num.setText(x + "");
                        sta.setText("中等");
                        sta.setBackgroundColor(Color.parseColor("#00b050"));
                    }else {
                        num.setText(x + "");
                        sta.setText("强");
                        sta.setBackgroundColor(Color.parseColor("#ff0000"));
                    }
                    break;
                case 1:
                    x = InitApp.random(0,200);
                    if (x > 0 && x < 35){
                        num.setText(x + "");
                        sta.setText("优");
                        sta.setBackgroundColor(Color.parseColor("#44dc68"));
                    }else if (x >= 35 && x <= 75){
                        num.setText(x + "");
                        sta.setText("良");
                        sta.setBackgroundColor(Color.parseColor("#92d050"));
                    }else if (x > 75 && x < 115){
                        num.setText(x + "");
                        sta.setText("轻度污染");
                        sta.setBackgroundColor(Color.parseColor("#ffff40"));
                    }else if (x > 75 && x < 115){
                        num.setText(x + "");
                        sta.setText("中度污染");
                        sta.setBackgroundColor(Color.parseColor("#bf9000"));
                    }else {
                        num.setText(x + "");
                        sta.setText("重度污染");
                        sta.setBackgroundColor(Color.parseColor("#993300"));
                    }
                    break;
                case 2:
                    x = InitApp.random(0,7000);
                    if (x > 0 && x < 3000){
                        num.setText(x + "");
                        sta.setText("适宜");
                        sta.setBackgroundColor(Color.parseColor("#44dc68"));
                    }else if (x >= 3000 && x <= 6000){
                        num.setText(x + "");
                        sta.setText("中");
                        sta.setBackgroundColor(Color.parseColor("#ffc000"));
                    }else {
                        num.setText(x + "");
                        sta.setText("较不宜");
                        sta.setBackgroundColor(Color.parseColor("#8149ac"));
                    }
                    break;
                case 3:
                    x = InitApp.random(0,38);
                    if (x > 0 && x < 12){
                        num.setText(x + "");
                        sta.setText("冷");
                        sta.setBackgroundColor(Color.parseColor("#3462f4"));
                    }else if (x >= 12 && x <= 21){
                        num.setText(x + "");
                        sta.setText("舒适");
                        sta.setBackgroundColor(Color.parseColor("#92d050"));
                    }else if (x > 21 && x < 35){
                        num.setText(x + "");
                        sta.setText("温暖");
                        sta.setBackgroundColor(Color.parseColor("#44dc68"));
                    }else {
                        num.setText(x + "");
                        sta.setText("热");
                        sta.setBackgroundColor(Color.parseColor("#ff0000"));
                    }
                    break;
                case 4 :
                    x = InitApp.random(0,100);
                    if (x > 0 && x < 50){
                        num.setText(x + "");
                        sta.setText("较易发");
                        sta.setBackgroundColor(Color.parseColor("#ff0000"));
                    }else {
                        num.setText(x + "");
                        sta.setText("少发");
                        sta.setBackgroundColor(Color.parseColor("#ffff40"));
                    }
                    break;
                case 5:
                    num.setText("");
                    sta.setText("不太适宜");
                    break;
            }

            return view;
        }
    }

    public String getWeek(Date date){
        String[] strs = new String[]{"日","一","二","三","四","五","六"};
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return strs[calendar.get(Calendar.DAY_OF_WEEK) - 1];
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }
}
