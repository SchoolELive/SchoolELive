package com.xiaoyu.schoolelive.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.xiaoyu.schoolelive.base.BaseSlideBack;

import java.util.ArrayList;
import java.util.List;

import com.xiaoyu.schoolelive.R;

public class SysSetAccountActivity extends BaseSlideBack {

    private ListView setAccount_listView;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sys_set_account);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("账号设置");

        setAccount_listView = (ListView)findViewById(R.id.sysset_account_listview);
        final List<String> list = new ArrayList<String>();
        list.add("密码修改");
        list.add("切换账号");
        list.add("账号绑定");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(SysSetAccountActivity.this,android.R.layout.simple_list_item_1,list);
        setAccount_listView.setAdapter(adapter);
        setAccount_listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0://密码修改
                        LayoutInflater layoutInflater = LayoutInflater.from(SysSetAccountActivity.this);
                        //自定义对话框标题栏
                        View mTitleView = layoutInflater.inflate(R.layout.custom_changepass_dialog, null);

                        View mView = layoutInflater.inflate(R.layout.change_password_content,null);
                        //创建对话框
                        AlertDialog.Builder builder = new AlertDialog.Builder(SysSetAccountActivity.this);

                        builder.setCustomTitle(mTitleView);
                        builder.setView(mView);
                        final AlertDialog dialog = builder.show();
                        //设置宽度和高度
                        WindowManager.LayoutParams params =
                                dialog.getWindow().getAttributes();
                        params.width = 1200;
                        params.height =800;
                        dialog.getWindow().setAttributes(params);

//                        Button true_button = (Button)findViewById(R.id.changepass_true_button);
//                        Button false_button = (Button)findViewById(R.id.changepass_false_button);
//                        true_button.setOnClickListener(new View.OnClickListener() {
//                            public void onClick(View v) {
//                            }
//                        });
//                        false_button.setOnClickListener(new View.OnClickListener() {
//                            public void onClick(View v) {
//                            }
//                        });
                        break;
                    case 1://切换账号
                        if (MainActivity.boo){
                            new AlertDialog.Builder(SysSetAccountActivity.this)
                                    .setTitle("是否切换用户？")
                                    .setIcon(R.drawable.side_nav_bar)
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            MainActivity.boo = false;
                                            Intent intent = new Intent(SysSetAccountActivity.this,LoginActivity.class);
                                            startActivity(intent);
                                        }
                                    })
                                    .setNegativeButton("取消", null)
                                    .show();
                        }else{
                            Toast.makeText(SysSetAccountActivity.this,"尚未登录！",Toast.LENGTH_LONG).show();
                        }
                        break;
                    case 2://账号绑定
                        Intent intent = new Intent(SysSetAccountActivity.this,SysSetBindActivity.class);
                        startActivity(intent);
                        break;
                }
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

