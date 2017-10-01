package com.imooc.muju_md.message;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admini on 2017/9/21.
 */

public class cityList {

    @SerializedName("citylist")
    public List<city> cityList ;



    public cityList(){

        cityList = new ArrayList<city>();
    }



}
