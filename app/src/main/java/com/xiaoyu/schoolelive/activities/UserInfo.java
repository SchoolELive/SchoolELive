package com.xiaoyu.schoolelive.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.xiaoyu.schoolelive.base.BaseSlideBack;
import com.xiaoyu.schoolelive.custom.CustomBar;
import com.xiaoyu.schoolelive.util.HttpUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;
import com.xiaoyu.schoolelive.R;


public  class UserInfo extends BaseSlideBack implements View.OnClickListener {
    class MyHandler extends Handler {

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle b = msg.getData();
            int index = b.getInt("index");
            String info = b.getString("info");
            String data = b.getString("data");
            String sex = b.getString("sex");
            switch (index){
                case 0://初始化
                    String name = b.getString("name");
                    String real_name = b.getString("real_name");
                    String signature = b.getString("signature");
                    custom_fname.setInfo_menu_info(name);
                    custom_about.setInfo_menu_info(signature);
                    custom_tname.setInfo_menu_info(real_name);
                    if (String.valueOf("0").equals(sex)) {
                        custom_sex.setInfo_menu_info("男");
                    } else if (String.valueOf("1").equals(sex)) {
                        custom_sex.setInfo_menu_info("女");
                    }
                    Toast.makeText(UserInfo.this,"初始化信息成功", Toast.LENGTH_SHORT).show();
                    break;
                case 1://昵称
                    if(String.valueOf("226").equals(data)){
                        custom_fname.setInfo_menu_info(info);
                        Toast.makeText(UserInfo.this,"修改昵称成功", Toast.LENGTH_SHORT).show();
                        break;
                    }else {
                        Toast.makeText(UserInfo.this,data, Toast.LENGTH_LONG).show();
                        break;
                    }
                case 2://个性签名
                    if(String.valueOf("226").equals(data)){
                        custom_about.setInfo_menu_info(info);
                        Toast.makeText(UserInfo.this,"修改个性签名成功", Toast.LENGTH_SHORT).show();
                        break;
                    }else {
                        Toast.makeText(UserInfo.this,data, Toast.LENGTH_LONG).show();
                        break;
                    }
                case 3://真实姓名
                    if(String.valueOf("226").equals(data)){
                        custom_tname.setInfo_menu_info(info);

                        Toast.makeText(UserInfo.this,"修改真实成功", Toast.LENGTH_SHORT).show();
                        break;
                    }else {
                        Toast.makeText(UserInfo.this,data, Toast.LENGTH_LONG).show();
                        break;
                    }
                case 4://性别
                    if(String.valueOf("226").equals(data)){
                        if (String.valueOf("0").equals(sex)) {
                            custom_sex.setInfo_menu_info("男");
                        } else if (String.valueOf("1").equals(sex)) {
                           custom_sex.setInfo_menu_info("女");
                        }
                        break;
                    }else {
                        Toast.makeText(UserInfo.this,data, Toast.LENGTH_LONG).show();
                        break;
                    }

            }
        }
    }
    public long UID ;//用户的id
    private CustomBar custom_photo, custom_fname, custom_about, custom_fav, custom_tname, custom_sex, custom_bri, custom_address;
    private Intent intent;
    private Intent get_uid;
    private  int sex_status = -1;
    private long Uid;
    private Handler handler;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // ActionBar actionBar = getSupportActionBar();
        //actionBar.setDisplayHomeAsUpEnabled(true);
        //actionBar.setIcon(R.drawable.side_nav_bar);
        setContentView(R.layout.activity_user_info);
        handler = new MyHandler();
        custom_photo = (CustomBar) findViewById(R.id.custom_photo);//图像对象
        custom_photo.setOnClickListener(this);
        custom_fname = (CustomBar) findViewById(R.id.custom_fname);//昵称对象
        custom_fname.setOnClickListener(this);
        custom_about = (CustomBar) findViewById(R.id.custom_about);//个性签名
        custom_about.setOnClickListener(this);
        custom_tname = (CustomBar) findViewById(R.id.custom_tname);//真实姓名
        custom_tname.setOnClickListener(this);
        custom_sex = (CustomBar) findViewById(R.id.custom_sex);//性别
        custom_sex.setOnClickListener(this);
        custom_bri = (CustomBar) findViewById(R.id.custom_bri);//生日
        custom_bri.setOnClickListener(this);
        custom_address = (CustomBar) findViewById(R.id.custom_address);//地址
        custom_address.setOnClickListener(this);
        get_uid = getIntent();
        Uid = get_uid.getLongExtra("uid",0);

        init();//进入个人信息界面的时候初始话信息
    }
    public void onClick(View v) {
       switch (v.getId()) {
            case R.id.custom_photo:
                Toast.makeText(UserInfo.this, "点击了" + custom_photo.getInfo_menu_info(), Toast.LENGTH_LONG).show();
                break;
            case R.id.custom_fname:
                final EditText name_text = new EditText(this);
                new AlertDialog.Builder(this)
                        .setTitle("请输入新的昵称")
                        .setIcon(R.drawable.side_nav_bar)
                        .setView(name_text)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                                final String str = name_text.getText().toString();
                                RequestBody requestBody = new FormBody.Builder()
                                        .add("uid", String.valueOf(Uid))//uid
                                        .add("field","name")//字段
                                        .add("values",str)//值
                                        .build();
                                HttpUtil.sendHttpRequest("http://115.159.217.226/xiaoyu/update_info.php", requestBody, new Callback() {
                                    public void onFailure(Call call, IOException e) {
                                    }
                                    public void onResponse(Call call, Response response) throws IOException {
                                            String data = response.body().string();
                                            Message msg = Message.obtain();
                                            Bundle bundle = new Bundle();
                                            bundle.putString("data",data);
                                            bundle.putString("info",str);
                                            bundle.putInt("index",1);
                                            msg.setData(bundle);
                                            handler.sendMessage(msg);
                                    }
                                });

                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
                break;
            case R.id.custom_about:
                final EditText signature_text = new EditText(this);
                new AlertDialog.Builder(this)
                        .setTitle("请输入新的个性签名")
                        .setIcon(R.drawable.side_nav_bar)
                        .setView(signature_text)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                              final String signature = signature_text.getText().toString();
                                RequestBody requestBody = new FormBody.Builder()
                                        .add("uid", String.valueOf(Uid))//uid
                                        .add("field","signature")//字段
                                        .add("values",signature)//值
                                        .build();
                                HttpUtil.sendHttpRequest("http://115.159.217.226/xiaoyu/update_info.php", requestBody, new Callback() {
                                    public void onFailure(Call call, IOException e) {
                                    }
                                    public void onResponse(Call call, Response response) throws IOException {
                                        String data = response.body().string();
                                        Message msg = Message.obtain();
                                        Bundle bundle = new Bundle();
                                        bundle.putString("data",data);
                                        bundle.putString("info",signature);
                                        bundle.putInt("index",2);
                                        msg.setData(bundle);
                                        handler.sendMessage(msg);
                                    }
                                });
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
                break;
            case R.id.custom_tname:
                final EditText rname_text = new EditText(this);
                new AlertDialog.Builder(this)
                        .setTitle("请输入真实信息，方便同学联系你")
                        .setIcon(R.drawable.side_nav_bar)
                        .setView(rname_text)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                                final String real_name = rname_text.getText().toString();
                                RequestBody requestBody = new FormBody.Builder()
                                        .add("uid", String.valueOf(Uid))//uid
                                        .add("field","real_name")//字段
                                        .add("values",real_name)//值
                                        .build();
                                HttpUtil.sendHttpRequest("http://115.159.217.226/xiaoyu/update_info.php", requestBody, new Callback() {
                                    public void onFailure(Call call, IOException e) {
                                    }
                                    public void onResponse(Call call, Response response) throws IOException {
                                        String data = response.body().string();
                                        Message msg = Message.obtain();
                                        Bundle bundle = new Bundle();
                                        bundle.putString("data",data);
                                        bundle.putString("info",real_name);
                                        bundle.putInt("index",3);
                                        msg.setData(bundle);
                                        handler.sendMessage(msg);
                                    }
                                });
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
                break;
           case R.id.custom_sex:

               new AlertDialog.Builder(this)
                        .setTitle("请选择你的性别")
                        .setIcon(R.drawable.side_nav_bar)
                        .setSingleChoiceItems(new String[]{"男", "女"}, -1, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                                sex_status = i;
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                if (sex_status == -1) {
                                    Toast.makeText(UserInfo.this, "你还没有选择性别", Toast.LENGTH_SHORT);
                                } else {
                                    RequestBody requestBody = new FormBody.Builder()
                                            .add("uid", String.valueOf(Uid))//uid
                                            .add("field","sex")//字段
                                            .add("values", String.valueOf(sex_status))//值
                                            .build();
                                    HttpUtil.sendHttpRequest("http://115.159.217.226/xiaoyu/update_info.php", requestBody, new Callback() {
                                        public void onFailure(Call call, IOException e) {
                                        }
                                        public void onResponse(Call call, Response response) throws IOException {
                                            String data = response.body().string();
                                            Message msg = Message.obtain();
                                            Bundle bundle = new Bundle();
                                            bundle.putString("sex", String.valueOf(sex_status));
                                            bundle.putInt("index",4);
                                            bundle.putString("data",data);
                                            msg.setData(bundle);
                                            handler.sendMessage(msg);
                                        }
                                    });
                                }
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                                sex_status = i;
                            }
                        })
                        .show();
                break;
            case R.id.custom_bri:
                new AlertDialog.Builder(this)
                        .setView(R.layout.datepicker)
                        .setPositiveButton("确定", null)
                        .setNegativeButton("取消", null)
                        .show();
                break;
            case R.id.custom_address:
                intent = new Intent(UserInfo.this, MySpinnerActivity.class);
                startActivity(intent);
               break;

        }
    }
    private void init(){//进入界面更新数据
        RequestBody requestBody = new FormBody.Builder()
                .add("uid", String.valueOf(Uid))//uid
                .build();
        HttpUtil.sendHttpRequest("http://115.159.217.226/xiaoyu/query_data.php", requestBody, new Callback() {
            public void onFailure(Call call, IOException e) {
            }
            public void onResponse(Call call, Response response) throws IOException {
           String info = response.body().string();
                try{
                    JSONArray jsonArray = new JSONArray(info);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    String name = jsonObject.getString("name");
                    String signature = jsonObject.getString("signature");
                    String real_name = jsonObject.getString("real_name");
                    String sex = jsonObject.getString("sex");
                    Message msg = Message.obtain();
                    Bundle bundle = new Bundle();
                    bundle.putInt("index",0);//0代表初始话数据
                    bundle.putString("name",name);
                    bundle.putString("signature",signature);
                    bundle.putString("real_name",real_name);
                    bundle.putString("sex",sex);
                    msg.setData(bundle);
                    handler.sendMessage(msg);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

        });

    }
}
