package com.xiaoyu.schoolelive.util;

import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;


public class HttpUtil {//发送Http请求类，每次发送Http请求调用该方法
    /*
    * 通过okhttp3发送请求(用来请求纯文本比较方便)
    * */

    /*
    * 不发送数据
    * */
    public static void sendHttpRequest(String address, okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(address)
                .build();
        client.newCall(request).enqueue(callback);
    }

    /*
    * 发送数据
    * */
    public static void sendHttpRequest(String address, RequestBody requestBody, okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(address)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(callback);
    }



}
