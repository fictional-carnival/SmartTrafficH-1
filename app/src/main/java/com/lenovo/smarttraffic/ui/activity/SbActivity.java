package com.lenovo.smarttraffic.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.lenovo.smarttraffic.InitApp;
import com.lenovo.smarttraffic.R;
import com.lenovo.smarttraffic.bean.D;
import com.lenovo.smarttraffic.ui.adapter.BasePagerAdapter;
import com.lenovo.smarttraffic.util.CommonUtil;

import java.util.List;

import butterknife.BindView;

/**
 * @author Amoly
 * @date 2019/4/11.
 * description：
 */

public class SbActivity extends BaseActivity {


    @BindView(R.id.gv1)
    GridView gv1;
    @BindView(R.id.gv_2)
    GridView gv2;
    @BindView(R.id.ll1)
    LinearLayout ll1;
    @BindView(R.id.ll2)
    LinearLayout ll2;
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.tv3)
    TextView tv3;
    @BindView(R.id.tv4)
    TextView tv4;

    int index = -1;
    int index2 = -1;
    int site = -1;
    int status = -1;


    private List<D.ROWSDETAILBean> subways;
    private String[] loads;
    private int[] colors;
    private List<String> route;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InitView();
        InitData();
    }
    @Override
    protected int getLayout() {
        return R.layout.activity_subway;
    }
    private void InitView() {
        initToolBar(findViewById(R.id.toolbar), true, "地铁线路查询");
        subways = InitApp.gson.fromJson(InitApp.getData("subway"), D.class).getROWS_DETAIL();
        loads = new String[]{"1", "2", "4", "5", "6", "7", "8北", "8南"};
        colors = new int[]{
                Color.parseColor("#36A9CE"),
                Color.parseColor("#FF0000"),
                Color.parseColor("#00904B"),
                Color.parseColor("#7D6AA2"),
                Color.parseColor("#B0A766"),
                Color.parseColor("#45BDB3"),
                Color.parseColor("#DB5FCD"),
                Color.parseColor("#E69B98")
        };
        gv1.setAdapter(new cAdapter());
        gv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (status != 2) {

                    Intent intent = new Intent(getApplicationContext(), Sb2Activity.class);
                    intent.putExtra("index", i);
                    startActivity(intent);
                } else {
                    ff2();

                    D.ROWSDETAILBean bean = subways.get(i);
                    route = bean.getRoute();
                    index2 = i;

                    gv2.setNumColumns(route.size());
                    ViewGroup.LayoutParams layoutParams = gv2.getLayoutParams();
                    layoutParams.width = route.size() * 200;
                    gv2.setLayoutParams(layoutParams);
                    gv2.setAdapter(new BAdapter());


                }
            }
        });

        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ff1();
                status = 2;
                site = 1;
            }
        });
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ff1();
                status = 2;
                site = 2;
            }
        });

        ll1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ff3();
            }
        });
    }

    private void InitData() {
        BasePagerAdapter basePagerAdapter = new BasePagerAdapter(getSupportFragmentManager());

    }


    public void ff1() {
        gv1.setTranslationY(30);
        ll1.setBackgroundColor(Color.parseColor("#cccccc"));
    }
    public void ff2() {
        gv1.setTranslationY(0);
        ll1.setBackgroundColor(Color.parseColor("#cccccc"));
        gv1.setBackgroundColor(Color.parseColor("#cccccc"));
        ll2.setVisibility(View.VISIBLE);

    }
    public void ff3() {
        gv1.setTranslationY(0);
        ll2.setVisibility(View.GONE);
        gv1.setBackgroundColor(Color.parseColor("#FFFFFF"));
        ll1.setBackgroundColor(Color.parseColor("#FFFFFF"));
        index = -1;
        index2 = -1;
        status = -1;
        site = -1;

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private class cAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return colors.length;
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
            view = View.inflate(getApplicationContext(), R.layout.sb_gv1, null);
            TextView tv_1 = view.findViewById(R.id.tv_1);
            tv_1.setText(loads[i]);
            GradientDrawable gdOne = (GradientDrawable)tv_1.getBackground();
            gdOne.setColor(colors[i]);
            return view;
        }
    }

    private class BAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return route.size();
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
                cv = View.inflate(getApplicationContext(), R.layout.sb2_list, null);
            } else {
                cv = view;
            }
            TextView tv_1 = cv.findViewById(R.id.tv_1);
            TextView tv_2 = cv.findViewById(R.id.tv_2);
            TextView tv_3 = cv.findViewById(R.id.tv_3);

            tv_3.setText(route.get(i));

            if (index == i) {
                tv_2.setBackgroundResource(R.drawable.border_ff2);
                String querys = query(route.get(i));
                if (!querys.equals("")) {
                    tv_1.setVisibility(View.VISIBLE);
                    tv_1.setText("可转乘" + querys);
                } else {
                    tv_1.setVisibility(View.INVISIBLE);
                }
            } else {
                tv_2.setBackgroundResource(R.drawable.border_ff);
                tv_1.setVisibility(View.INVISIBLE);
            }
            tv_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    index = i;
                    InitApp.toast("长按选择");
                    notifyDataSetChanged();
                }
            });

            tv_2.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    String trim;
                    if (site == 1) {
                        trim = tv2.getText().toString();
                        if (!trim.equals(route.get(i))) {
                            tv1.setText(route.get(i));
                        } else {
                            InitApp.toast("终点起点不可选择同站点");
                        }
                    } else {
                        trim = tv1.getText().toString();
                        if (!trim.equals(route.get(i))) {
                            tv2.setText(route.get(i));
                        } else {
                            InitApp.toast("终点起点不可选择同站点");
                        }
                    }
                    if (!trim.equals("")) {
                        tv3.setBackgroundColor(Color.parseColor("#2196F3"));
                    }
                    ff3();
                    return true;
                }
            });

            return cv;
        }
    }

    private String query(String s) {

        for (int i = 0; i < subways.size(); i++) {
            if (i != index2 && subways.get(i).getRoute().toString().indexOf(s) != -1) {
                return subways.get(i).getMetro_code();
            }
        }

        return "";
    }
}
