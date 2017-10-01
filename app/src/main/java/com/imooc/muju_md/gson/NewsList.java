package com.imooc.muju_md.gson;

import com.google.gson.annotations.SerializedName;
import com.imooc.muju_md.Title;

import java.util.ArrayList;
import java.util.List;


public class NewsList {

    public int code;

    public String msg;

    @SerializedName("newslist")
    public List<News> newsList ;

    public NewsList(){
        newsList = new ArrayList<News>();
    }
}

