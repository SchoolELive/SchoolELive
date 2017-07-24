package com.xiaoyu.schoolelive.activities;

import android.content.Intent;
import android.content.res.TypedArray;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.xiaoyu.schoolelive.R;
import com.xiaoyu.schoolelive.adapter.PublishAdapter;
import com.xiaoyu.schoolelive.base.BaseSlideBack;
import com.xiaoyu.schoolelive.data.Publish;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class UserAddMsgActivity extends BaseSlideBack {

    private EditText add_msg_textContent;
    private TextView add_msg_textNum;
    private Publish publish;
    private Button postButton;
    private PublishAdapter adapterPublish;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_add_msg);

        //标题栏
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        initView();
        textNumEvent();
    }
    //初始化控件
    public void initView(){
        add_msg_textContent = (EditText)findViewById(R.id.add_msg_textContent);
        add_msg_textNum = (TextView)findViewById(R.id.add_msg_textNum);
        postButton = (Button)findViewById(R.id.postButton);
    }
    //数字显示事件处理
    public void textNumEvent(){
        add_msg_textContent.addTextChangedListener(new TextWatcher() {
            private CharSequence temp;
            private int editStart;
            private int editEnd;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                temp = s;
            }
            @Override
            public void afterTextChanged(Editable s) {
                editStart = add_msg_textContent.getSelectionStart();
                editEnd = add_msg_textContent.getSelectionEnd();
                add_msg_textNum.setText(String.valueOf(140 - temp.length()));
            }
        });
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendUserMessage();
            }
        });
    }

    //导入菜单项
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.add_msg_menu, menu);
//        return true;
//    }
    //标题栏菜单点击逻辑
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    //发送数据
    public void sendUserMessage() {
        if (add_msg_textContent.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "动态不能为空！", Toast.LENGTH_SHORT).show();
        } else {
            // 生成数据
            publish = new Publish();
            //获取年月日
            SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
            Date curYMD = new Date(System.currentTimeMillis());
            String str = ymd.format(curYMD);
            //获取时间
            SimpleDateFormat date = new SimpleDateFormat("  HH:mm");
            Date curDate = new Date(System.currentTimeMillis());
            String str2 = date.format(curDate);

            Intent intent =new Intent (UserAddMsgActivity.this, MainActivity.class);

            intent.putExtra("tmp_name", "发布者：***");
            intent.putExtra("tmp_ymd", str);
            intent.putExtra("tmp_date",str2);
            intent.putExtra("tmp_content",add_msg_textContent.getText().toString());
            startActivity(intent);

            new Thread(new Runnable(){
                @Override
                public void run() {
                    finish();
                }
            }).start();


        }
    }
}


