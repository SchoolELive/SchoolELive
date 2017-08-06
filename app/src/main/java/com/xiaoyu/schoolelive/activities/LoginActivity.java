package com.xiaoyu.schoolelive.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;



import java.io.IOException;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;
import com.xiaoyu.schoolelive.R;
import com.xiaoyu.schoolelive.base.BaseSlideBack;
import com.xiaoyu.schoolelive.util.ConstantUtil;
import com.xiaoyu.schoolelive.util.HttpUtil;


public class LoginActivity extends BaseSlideBack implements View.OnClickListener {
    class MyHandler extends Handler {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle b = msg.getData();
            String data = b.getString("data");
            long uid = b.getLong("uid");
            if(String.valueOf(-1).equals(data)){//如果返回-1 说明该用户不存在
                Toast.makeText(LoginActivity.this,"该用户不存在", Toast.LENGTH_SHORT).show();
            }else{
               if(password.equals(data)){
                   Toast.makeText(LoginActivity.this,"登陆中", Toast.LENGTH_SHORT).show();
                   if(remember_pass.isChecked()){//如果用户点击了记住密码
                       editor.putLong("uid",uid);//将用户的实际帐号密码存到shareperference中，
                       editor.putString("Password",data);//用来设置记录的帐号和密码
                       editor.putBoolean("isChecked",true);//将isChecked常量放到sharedperference中
                   }else{//没有点击记住密码，直接进入主界面
                       editor.putBoolean("isChecked",false);
                   }
                   editor.apply();//提交
                   Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                   intent.putExtra("uid",uid);
                   startActivity(intent);//跳转到主界面并传递id
               }else{
                   Toast.makeText(LoginActivity.this,"密码不正确", Toast.LENGTH_SHORT).show();
               }

            }
        }
    }
    public String getPwd = null;//判断用户是否存在，得到用户的真正密码
    private EditText maccount;//帐号
    private EditText mpassword;
    private Button login;
    private Button register;
    private CheckBox remember_pass;//记住密码按钮
    private RequestBody requestBody;//发送请求对象
    private SharedPreferences login_sp;
    private SharedPreferences.Editor editor;
    private static long UsernameValue;//将要存到sharedpreference中的帐号
    private static String PasswordValue;//将要存到sharedpreference中的密码
    private boolean choseRemember;//是否选择记住密码、login_status登录状态
    private Handler handler;
    private String password;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //标题栏
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("登录");

        maccount = (EditText) findViewById(R.id.maccount);
        mpassword = (EditText) findViewById(R.id.mpassword);
        login = (Button) findViewById(R.id.btn_login);
        register = (Button) findViewById(R.id.btn_regist);
        login_sp = getSharedPreferences("User_info", MODE_APPEND);
        choseRemember = login_sp.getBoolean("isChecked", false);
        editor = login_sp.edit();//实例化Editor对象
        handler = new MyHandler();
        remember_pass = (CheckBox) findViewById(R.id.mRememberCheck);
        if (choseRemember) {
            UsernameValue = login_sp.getLong("uid", 0);
            PasswordValue = login_sp.getString("Password", null);
            maccount.setText(String.valueOf(UsernameValue));
            mpassword.setText(PasswordValue);
            maccount.setSelection(String.valueOf(UsernameValue).length());//将帐号输入框的光标移到最后
            remember_pass.setChecked(true);
        }
        login.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                login();
                break;
            case R.id.btn_regist:
                 Intent intent = new Intent(LoginActivity.this, RegistActivity.class);
                  startActivity(intent);
                break;
        }
    }
    private void login(){

          final String uid =maccount.getText().toString();
            password = mpassword.getText().toString();
            if (maccount.getText().toString().isEmpty()||password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "你还没有输入帐号或密码", Toast.LENGTH_SHORT).show();
                return;
            }
            requestBody = new FormBody.Builder()
                    .add("uid", String.valueOf(uid))
                    .add("password",password)
                    .build();
            HttpUtil.sendHttpRequest(ConstantUtil.SERVICE_PATH+"login.php", requestBody, new okhttp3.Callback() {
                public void onFailure(Call call, IOException e) {
                    Log.e("error",e.getMessage());
                }
                public void onResponse(Call call, Response response) throws IOException {
                    String responseData = response.body().string();
                    getPwd = responseData;
                    Message msg = Message.obtain();
                    Bundle bundle = new Bundle();
                    bundle.putString("data",getPwd);
                    bundle.putLong("uid", Long.parseLong(uid));
                    msg.setData(bundle);
                    handler.sendMessage(msg);
                }
            });
        }
    //标题栏菜单点击逻辑
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

