package com.xiaoyu.schoolelive.util;

import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


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
    public static void uploadMultiFile(long uid,File file) {//将图片发送到服务器,一张图片(处理头像)
        final String url = ConstantUtil.SERVICE_PATH + "photo_design.php";;
        RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
        final RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("uid",uid+"")//将用户的uid发送过去
                .addFormDataPart("image1", file.getName(), fileBody)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        final okhttp3.OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();
        OkHttpClient okHttpClient  = httpBuilder
                //设置超时
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            public void onFailure(Call call, IOException e) {
                Log.e("aa", "uploadMultiFile() e=" + e);
            }
            public void onResponse(Call call, Response response) throws IOException {
                Log.i("bb", "uploadMultiFile() response=" + response.body().string());
            }
        });
    }
}
