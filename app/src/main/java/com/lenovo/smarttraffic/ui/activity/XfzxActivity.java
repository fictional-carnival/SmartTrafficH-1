package com.lenovo.smarttraffic.ui.activity;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.lenovo.smarttraffic.InitApp;
import com.lenovo.smarttraffic.R;
import com.lenovo.smarttraffic.bean.Park;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class XfzxActivity extends BaseActivity {
    @BindView(R.id.mapview)
    MapView mapView;
    @BindView(R.id.lv_parks)
    ListView listView;
    private AMap map;
    private ArrayList<Marker> markers;
    private Park park;
    private List<Park.ROWSDETAILBean> beanList;

    @Override
    protected int getLayout() {
        return R.layout.activity_xfzx;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mapView.onCreate(savedInstanceState);
        map = mapView.getMap();
        initToolBar(findViewById(R.id.toolbar), true, "用户停车");
        InitMap();
    }

    private void InitMap() {
        markers = new ArrayList<>();
        ArrayList<Integer> icons = new ArrayList<Integer>(){{
            add(R.mipmap.marker_one);
            add(R.mipmap.marker_second);
            add(R.mipmap.marker_thrid);
            add(R.mipmap.marker_forth);
        }};
        park = InitApp.gson.fromJson(InitApp.getData("a"), Park.class);
        beanList = park.getROWS_DETAIL();
        for (Park.ROWSDETAILBean bean :
               beanList ) {
            String[] split = bean.getCoordinate().split(",");
            LatLng latLng = new LatLng(Float.parseFloat(split[1]), Float.parseFloat(split[0]));
            bean.setLatLng(latLng);
            bean.setDistance(AMapUtils.calculateLineDistance(new LatLng(40.046307, 116.273117), latLng));
        }
        Collections.sort(beanList, new Comparator<Park.ROWSDETAILBean>() {
            @Override
            public int compare(Park.ROWSDETAILBean t1, Park.ROWSDETAILBean t2) {
                if (t1.getDistance() > t2.getDistance()) {
                    return 1;
                }
                if (t1.getDistance() < t2.getDistance()) {
                    return -1;
                }
                return 0;
            }
        });

        for (int i = 0, l = beanList.size(); i < l; i++) {
            Park.ROWSDETAILBean bean = beanList.get(i);
            MarkerOptions options = new MarkerOptions();
            options.position(bean.getLatLng())
                    .visible(false)
                    .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), icons.get(i))));
            Marker marker = map.addMarker(options);
            markers.add(marker);
        }
        listView.setAdapter(new ParkAdapter());
    }

    @OnClick(R.id.lv_parks)
    public void park() {
        listView.setVisibility(View.VISIBLE);
        for (Marker marker :
                markers) {
            marker.setVisible(true);
        }
    }

    @OnClick(R.id.iv_location)
    public void location() {
        LatLng latLng = new LatLng(Float.parseFloat(park.getLatitude()), Float.parseFloat(park.getLongitude()));
        MarkerOptions options = new MarkerOptions();
        options.position(latLng);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13));
        options.position(latLng)
                .title("什刹海")
                .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.marker_self)));
        map.addMarker(options);
    }

    private class ParkAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            return null;
        }
    }
}
