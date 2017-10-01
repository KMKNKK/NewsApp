package com.imooc.muju_md.gson;

import android.util.Log;


import com.imooc.muju_md.util.HttpUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by MYT on 2017/9/20.
 */

public class GsonServer {

    private String responseText;
    private String wea = "https://free-api.heweather.com/v5/weather?";
    private String key = "key=8eee8c62ada141bea65f3420bd2c2553";

    public void getWeatherResponse(String weatherId,final String[] s){
        long clocks = 0;
        final String Url = wea + "city=" + weatherId + "&" + key;
        System.out.println(Url);
        responseText = null;
        HttpUtil.sendOkHttpRequest(Url,new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Error!\nonFailure: Load Error!");
                call.cancel();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                s[0] = response.body().string();
                call.cancel();
            }
        });
        try {
            new Thread().sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
