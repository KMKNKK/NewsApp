package com.imooc.muju_md.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by admini on 2017/9/3.
 */

public class QuestionsList {
    public int code;

    public String msg;

    @SerializedName("newslist")
    public List<Questions> questionsList ;
}
