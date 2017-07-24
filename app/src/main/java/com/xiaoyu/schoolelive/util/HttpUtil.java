package com.xiaoyu.schoolelive.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;


public class HttpUtil {//发送Http请求类，每次发送Http请求调用该方法
    public static void sendHttpRequest(String address, RequestBody requestBody, okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(address)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(callback);
    }
}
