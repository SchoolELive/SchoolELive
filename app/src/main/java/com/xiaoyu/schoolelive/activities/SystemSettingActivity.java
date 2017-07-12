package com.xiaoyu.schoolelive.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import xiaoyu.com.schoolelive.R;
import com.xiaoyu.schoolelive.base.BaseSlideBack;

/**
 * Created by Administrator on 2017/7/11.
 */

public class SystemSettingActivity extends BaseSlideBack {

    private ListView listView;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_setting);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("系统设置");

        listView = (ListView)findViewById(R.id.listview);
        final List<String> list = new ArrayList<String>();
        list.add("账号设置");
        list.add("消息推送");
        list.add("主题设置");
        list.add("推荐分享");
        list.add("用户使用协议说明");
        list.add("关于我们");
        list.add("清除缓存");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(SystemSettingActivity.this,android.R.layout.simple_list_item_1,list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
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

