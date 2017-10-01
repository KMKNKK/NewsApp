package com.imooc.muju_md.gson;

import com.google.gson.annotations.SerializedName;



public class News {



    @SerializedName("ctime")
    public String time;

    public String title;

    public String description;

    public String picUrl;

    public String url;

    public News(String T,String U,String P){
        title = T;
        picUrl = P;
        url = U;
    }
}
