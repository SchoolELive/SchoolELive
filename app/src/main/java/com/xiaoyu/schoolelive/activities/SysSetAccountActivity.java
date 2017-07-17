package com.xiaoyu.schoolelive.activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.xiaoyu.schoolelive.base.BaseSlideBack;

import java.util.ArrayList;
import java.util.List;

import xiaoyu.com.schoolelive.R;

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

                        break;
                    case 1://切换账号

                        break;
                    case 2://账号绑定

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

