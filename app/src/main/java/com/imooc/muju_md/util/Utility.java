package com.imooc.muju_md.util;

import com.google.gson.Gson;
import com.imooc.muju_md.gson.NList;
import com.imooc.muju_md.gson.NewsList;
import com.imooc.muju_md.gson.QuestionsList;
import com.imooc.muju_md.gson.record;
import com.imooc.muju_md.gson.weather;
import com.imooc.muju_md.message.cityList;


public class Utility {
    public static NewsList parseJsonWithGson(final String requestText){   //解析新闻
        Gson gson = new Gson();
        return gson.fromJson(requestText, NewsList.class);
    }

    public static weather parseJsonWithGsonW(final String requestText){    //解析天气
        Gson gson = new Gson();
        return gson.fromJson(requestText, weather.class);
    }

    public static cityList parseJsonWithGsonweather(final String requestText){  //将城市名解析成对应的url
        Gson gson = new Gson();
        return gson.fromJson(requestText, cityList.class);
    }

    public static NList parseJsonWithGsonN(final String requestText){   //解析新的新闻
        Gson gson = new Gson();
        return gson.fromJson(requestText, NList.class);
    }
}
