package com.lenovo.smarttraffic.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.lenovo.smarttraffic.InitApp;
import com.lenovo.smarttraffic.R;
import com.lenovo.smarttraffic.bean.News;
import com.lenovo.smarttraffic.ui.activity.NewsXqActivity;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class NewsFragment extends BaseFragment {


    private int flag;
    private View view;
    private int category;

    @BindView(R.id.lv_list)
    ListView listView;
    private ArrayList<News.ROWSDETAILBean> beanArrayList;

    @Override
    protected View getSuccessView() {
        view = View.inflate(getContext(), R.layout.fragmet_news, null);
        category = getArguments().getInt("category");
        initUI();
        return view;
    }

    private void initUI() {
        SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.swip);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                InitApp.getHandler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refresh();
                        flag = 2;
                        setNew();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 0);
            }
        });
    }

    public void setNew() {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, InitApp.url + "get_all_sense", InitApp.user, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                List<News.ROWSDETAILBean> beans = InitApp.gson.fromJson(InitApp.getData("News"), News.class).getROWS_DETAIL();
                beanArrayList = new ArrayList<>();
                for (int i = 0, l = beans.size(); i < l; i++) {
                    News.ROWSDETAILBean bean = beans.get(i);
                    if (category == bean.getCategory()) {
                        beanArrayList.add(bean);
                    }
                }
                if (flag == 2) {
                    refreshPage(beanArrayList);
                    Toast toast = new Toast(getContext());
                    View inflate = View.inflate(getContext(), R.layout.toast_yellow, null);
                    TextView tv_toast = inflate.findViewById(R.id.tv_toast);
                    tv_toast.setText("已为您添加一组数据");
                    toast.setGravity(Gravity.TOP, Gravity.CENTER, 150);
                    toast.setView(inflate);
                    toast.show();
                }
                flag = 1;
                if (beanArrayList == null) {
                    listView.setAdapter(null);
                } else {
                    listView.setAdapter(new NewSAdapter());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (volleyError.networkResponse == null) {
                    beanArrayList = null;
                    flag = 3;
                }
            }
        });
        InitApp.queue.add(request);
    }
    @Override
    protected Object requestData() {
        sleep();
        if (contentPage.error) {
            flag = 0;
            sleep();
            setNew();
            contentPage.error = false;
        }
        return beanArrayList;/*加载成功*/
    }

    private void sleep() {
        while (flag == 0) {
            SystemClock.sleep(1000);/*模拟请求服务器的延时过程*/
        }
    }

    @Override
    public void onClick(View view) {

    }

    /**
     * 类似于 Activity的 onNewIntent()
     */
    @Override
    public void onNewBundle(Bundle args) {
        super.onNewBundle(args);

        Toast.makeText(_mActivity, args.getString("from"), Toast.LENGTH_SHORT).show();
    }

    private class NewSAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return beanArrayList.size();
        }

        @Override
        public News.ROWSDETAILBean getItem(int i) {
            return beanArrayList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            if (view == null) {
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_news, viewGroup, false);
                viewHolder = new ViewHolder();
                viewHolder.tv_title = view.findViewById(R.id.tv_title);
                viewHolder.tv_time = view.findViewById(R.id.tv_time);
                viewHolder.tv_text = view.findViewById(R.id.tv_text);
                view.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder) view.getTag();
            }
            News.ROWSDETAILBean item = getItem(i);
            viewHolder.tv_title.setText(item.getTitle());
            viewHolder.tv_time.setText(item.getCreatetime());
            viewHolder.tv_text.setText(item.getContent());
            view.setOnClickListener(view1 ->{
                Intent intent = new Intent(getContext(), NewsXqActivity.class);
                intent.putExtra("item", InitApp.gson.toJson(item));
                startActivity(intent);
            } );
            return view;
        }

        class ViewHolder {
            TextView tv_title;
            TextView tv_time;
            TextView tv_text;
        }
    }
}
