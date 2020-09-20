package com.lenovo.smarttraffic.ui.fragment;

import android.graphics.Color;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.charts.RadarChart;
import com.lenovo.smarttraffic.Constant;
import com.lenovo.smarttraffic.R;


/**
 * @author Amoly
 * @date 2019/4/11.
 * description：设计页面
 */
public class DesignFragment extends BaseFragment {
    private static DesignFragment instance = null;

    public static DesignFragment getInstance() {
        if (instance == null) {
            instance = new DesignFragment();
        }
        return instance;
    }

    private View view;
    private Spinner spinner;
    private RadarChart radarChart;
    private String[] strs = new String[]{"幸福路","联想路","学院路","医院路","幸福路"};
    private String[] strs2 = new String[]{"超速行驶","非法便道","闯红绿灯","驾驶期间玩手机"};


    @Override
    protected View getSuccessView() {
        view = View.inflate(getContext(), R.layout.fragment_design,null);
        return view;
    }

    public void initSpinner(){
        spinner.setAdapter(new ArrayAdapter<String>(getContext(),R.layout.f_spinner,strs));


    }

    @Override
    protected Object requestData() {
        return Constant.STATE_SUCCEED;
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onDestroy() {
        if (instance != null) {
            instance = null;
        }
        super.onDestroy();
    }

}
