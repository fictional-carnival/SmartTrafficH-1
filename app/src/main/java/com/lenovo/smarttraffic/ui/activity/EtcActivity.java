package com.lenovo.smarttraffic.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.lenovo.smarttraffic.InitApp;
import com.lenovo.smarttraffic.R;
import com.lenovo.smarttraffic.bean.User;
import com.lenovo.smarttraffic.ui.adapter.BasePagerAdapter;
import com.lenovo.smarttraffic.util.CommonUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;

/**
 * @author Amoly
 * @date 2019/4/11.
 * description：
 */

public class EtcActivity extends BaseActivity {

    private JSONArray etcArray;
    private ArrayList<TextView> textViews;
    private RadioButton rb1;
    private Spinner sp3;
    private Spinner sp2;
    private Spinner sp1;
    private int index = 0;
    private int mm = 50;
    private int index2 = 0;

    @BindView(R.id.lv1)
    ListView lv1;
    private JSONArray his;
    private int index3;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        InitView();
        InitData();
    }
    @Override
    protected int getLayout() {
        return R.layout.activity_etc;
    }

    private void InitView() {
        initToolBar(findViewById(R.id.toolbar), true, getString(R.string.item1));
        textViews = new ArrayList<TextView>() {{
            add(findViewById(R.id.tv1));
            add(findViewById(R.id.tv2));
            add(findViewById(R.id.tv3));
            add(findViewById(R.id.tv4));
            add(findViewById(R.id.tv5));
            add(findViewById(R.id.tv6));
            add(findViewById(R.id.tv7));
        }};
        sp1 = findViewById(R.id.sp1);
        sp2 = findViewById(R.id.sp2);
        sp3 = findViewById(R.id.sp3);
        rb1 = findViewById(R.id.rb1);
    }


    private void InitData() {
        String etcc = InitApp.sp.getString("etcc", "");
        if (etcc.equals("")) {
            etcArray = new JSONArray();
            for (int i = 0; i < 4; i++) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("money", 0);
                    jsonObject.put("his", new JSONArray());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                etcArray.put(jsonObject);
            }
            InitApp.edit.putString("etcc", etcArray.toString()).commit();
        } else {
            try {
                etcArray = new JSONArray(etcc);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        String[] strings = {"1", "2", "3", "4"};
        String[] strings2 = {"50", "100", "150", "200"};
        sp1.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, strings));
        sp2.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, strings2));
        sp3.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, strings));
        sp1.setSelection(0);
        sp2.setSelection(0);
        sp3.setSelection(0);

       sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               index = i;
           }

           @Override
           public void onNothingSelected(AdapterView<?> adapterView) {

           }
       });

       textViews.get(1).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               try {
                   String money = etcArray.getJSONObject(index).getString("money");
                   textViews.get(0).setText(money);
                   textViews.get(2).setVisibility(View.VISIBLE);

               } catch (JSONException e) {
                   e.printStackTrace();
               }
           }
       });

       sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               mm = i * 50;
           }

           @Override
           public void onNothingSelected(AdapterView<?> adapterView) {
           }
       });
        sp3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                index2 = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
       textViews.get(3).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               textViews.get(4).setVisibility(View.VISIBLE);
               try {
                   JSONObject jsonObject = new JSONObject();
                   jsonObject.put("index", index + 1);
                   jsonObject.put("money", mm);
                   jsonObject.put("user",InitApp.getUser("username", InitApp.sp.getString("UserName", null)).getUsername());
                   jsonObject.put("time",InitApp.timeFormat(new Date(),"yyyy.MM.dd HH:ss"));
                   etcArray.getJSONObject(index).getJSONArray("his").put(jsonObject);
                   int money = Integer.parseInt(etcArray.getJSONObject(index).getString("money"));
                   money += mm;
                   textViews.get(0).setText(money);
                   etcArray.getJSONObject(index).put("money", money + "");
                   InitApp.edit.putString("etcc", etcArray.toString()).commit();
               } catch (JSONException e) {
                   e.printStackTrace();
               }
           }
       });
        textViews.get(5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                     his = etcArray.getJSONObject(index2).getJSONArray("his");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (rb1.isChecked()) {
                    lv1.setAdapter(new AAdapter());
                    index3 = 1;
                } else {
                    index3 = 2;
                }

            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    private class AAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return his.length();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View cv;
            if (view == null) {
                cv = View.inflate(getApplicationContext(), R.layout.etttt, null);
            } else {
                cv = view;
            }
            TextView tv_1 = cv.findViewById(R.id.tv_1);
            TextView tv_2 = cv.findViewById(R.id.tv_2);
            TextView tv_3 = cv.findViewById(R.id.tv_3);
            TextView tv_4 = cv.findViewById(R.id.tv_4);
            TextView tv_5 = cv.findViewById(R.id.tv_5);

            tv_1.setText(i + 1 + "");

            if (index3 == 1) {
                try {
                    tv_2.setText(his.getJSONObject(his.length() - i).getInt("index"));
                    tv_3.setText(his.getJSONObject(his.length() - i).getInt("money"));
                    tv_4.setText(his.getJSONObject(his.length() - i).getInt("user"));
                    tv_5.setText(his.getJSONObject(his.length() - i).getInt("time"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    tv_2.setText(his.getJSONObject(i).getInt("index"));
                    tv_3.setText(his.getJSONObject(i).getInt("money"));
                    tv_4.setText(his.getJSONObject(i).getInt("user"));
                    tv_5.setText(his.getJSONObject(i).getInt("time"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return cv;
        }
    }
}
