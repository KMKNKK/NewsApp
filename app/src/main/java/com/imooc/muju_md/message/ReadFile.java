package com.imooc.muju_md.message;

import android.content.Context;

import java.io.InputStream;

/**
 * Created by MYT on 2017/9/21.
 */

public class ReadFile {

    public static String Read(Context context) throws Exception {
        InputStream inputStream = null;
        inputStream = context.getClassLoader().getResourceAsStream("assets/cityid.txt");
        int size = inputStream.available();
        int len = -1;
        byte[] bytes = new byte[size];
        inputStream.read(bytes);
        inputStream.close();
        String string = new String(bytes);
        return string;
    }
}
