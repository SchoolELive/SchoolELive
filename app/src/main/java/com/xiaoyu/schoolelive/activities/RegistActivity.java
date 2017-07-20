package com.xiaoyu.schoolelive.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.xiaoyu.schoolelive.util.HttpUtil;
import com.xiaoyu.schoolelive.R;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;


public class RegistActivity extends AppCompatActivity {
    class MyHandler extends Handler {

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String result = (String) msg.obj;
            if(String.valueOf(1).equals(result)){
                Toast.makeText(RegistActivity.this,"该用户已经存在", Toast.LENGTH_SHORT).show();
            }else if(String.valueOf(2).equals(result)){
                new AlertDialog.Builder(RegistActivity.this)
                        .setTitle("恭喜您注册成功")
                        .setMessage("请牢记您的帐号与密码，点击确定将进入登录界面")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(RegistActivity.this,LoginActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("取消",null)
                        .show();
            }else if(String.valueOf(3).equals(result)){
                Toast.makeText(RegistActivity.this,"注册失败", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private ImageButton btn_back;
    private EditText rg_uid;//帐号
    private EditText rg_pwd;//密码
    private Button btn_regist;//注册按钮
    private long rg_uid_content;//rg_uid中的内容
    private String rg_pwd_content;//rg_pwd中的内容
    private Handler handler;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置无标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_regist);
        rg_uid = (EditText) findViewById(R.id.rg_uid);
        rg_pwd =  (EditText) findViewById(R.id.rg_pwd);

        handler = new MyHandler();
        btn_regist = (Button)findViewById(R.id.btn_regist);
        btn_back = (ImageButton) findViewById(R.id.btn_bar_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(RegistActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        btn_regist.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                register2();
            }
        });
    }
    private void register2(){
        if(rg_uid.getText().toString().isEmpty()||rg_pwd.getText().toString().isEmpty()){
            Toast.makeText(RegistActivity.this,"你还没有输入帐号或密码", Toast.LENGTH_SHORT).show();
            return;
        }
        if(rg_uid.getText().toString().length()<6||rg_pwd.getText().toString().length()<6){
            Toast.makeText(RegistActivity.this,"注册的帐号或密码不能小与6位", Toast.LENGTH_SHORT).show();
            return;
        }
        rg_uid_content = Long.parseLong(rg_uid.getText().toString());
        rg_pwd_content = rg_pwd.getText().toString();
        RequestBody requestBody = new FormBody.Builder()
                .add("uid", String.valueOf(rg_uid_content))
                .add("password",rg_pwd_content)
                .build();
        HttpUtil.sendHttpRequest("http://115.159.217.226/xiaoyu/register.php", requestBody, new okhttp3.Callback() {
            public void onFailure(Call call, IOException e) {
            }
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                String result= responseData;
                Message msg = Message.obtain();
                msg.obj = result;
                handler.sendMessage(msg);
            }
        });
    }

}



