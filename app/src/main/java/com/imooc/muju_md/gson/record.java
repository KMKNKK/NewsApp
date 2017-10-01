package com.imooc.muju_md.gson;

/**
 * Created by admini on 2017/9/10.
 */

public class record {

    private int id;
    private String password;
    private String[] url;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String[] getUrl() {
        return url;
    }

    public void setUrl(String[] url) {
        this.url = url;
    }
}
