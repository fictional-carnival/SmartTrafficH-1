package com.lenovo.smarttraffic;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.lenovo.smarttraffic.bean.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

/**
 * @author Amoly
 * @date 2019/4/11.
 * email：caoxl@lenovo.com
 * description：
 */
public class InitApp extends MultiDexApplication {

    private static Handler mainHandler;
    private static Context AppContext;
    private Set<Activity> allActivities;

    private static InitApp instance = null;
    public static SharedPreferences sp;
    public static SharedPreferences.Editor edit;
    public static String url;
    public static RequestQueue queue;
    public static Gson gson;
    public static JSONObject user;
    public static ArrayList<String> label;
    public static boolean isLogin;
    private static Toast tos;


    public static synchronized InitApp getInstance() {
        synchronized (InitApp.class) {
            if (instance == null) {
                instance = new InitApp();
            }
        }
        return instance;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        AppContext = this;
        instance = this;
        mainHandler = new Handler();

        sp = getSharedPreferences("settings", MODE_PRIVATE);
        edit = sp.edit();
        url = sp.getString("url", "http://192.168.1.120:8080/api/v2/");
        queue = Volley.newRequestQueue(instance);
        gson = new Gson();
        HashMap hashMap = new HashMap();
        hashMap.put("UserName", "user1");
        user = new JSONObject(hashMap);
        label = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.mobile_news_name)));
    }

    public static void toast(String s) {
        if (tos != null) {
            tos.cancel();
        }
        tos = Toast.makeText(instance, s, Toast.LENGTH_LONG);
        tos.show();
    }

    public static int random(int a, int b) {
        return (int) Math.round(Math.random() * (a - b) + b);
    }

    public static String timeFormat(Date date, String Format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        return simpleDateFormat.format(date);
    }

    public static String getData(String path) {
        StringBuffer data = new StringBuffer();
        try {
            InputStream inputStream = instance.getAssets().open(path);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String len;
            while ((len = bufferedReader.readLine()) != null) {
                data.append(len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return String.valueOf(data);
    }

    public static void doPost(String path, Map map, Response.Listener<JSONObject> listener) {
        JSONObject object = user;
        if (map != null) {
            object = new JSONObject(map);
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url + path, object, listener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                toast("网络连接故障");
            }
        });
        queue.add(request);
    }

    public static User.ROWSDETAILBean getUser(String type, String name) {
        try {
            JSONArray array = new JSONObject(getData("User")).getJSONArray("ROWS_DETAIL");
            for (int i = 0, l = array.length(); i < l; i++) {
                JSONObject object = array.getJSONObject(i);
                if (name.equals(object.getString(type))) {
                    return gson.fromJson(String.valueOf(object), User.ROWSDETAILBean.class);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

        public static Context getContext(){
        return AppContext;
    }
    public static Handler getHandler(){
        return mainHandler;
    }

    public void addActivity(Activity act) {
        if (allActivities == null) {
            allActivities = new HashSet<>();
        }
        allActivities.add(act);
    }

    public void removeActivity(Activity act) {
        if (allActivities != null) {
            allActivities.remove(act);
        }
    }

    public void exitApp() {
        if (allActivities != null) {
            synchronized (allActivities) {
                for (Activity act : allActivities) {
                    act.finish();
                }
            }
        }
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

}
