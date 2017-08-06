package com.xiaoyu.schoolelive.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.xiaoyu.schoolelive.adapter.UserFocusAdapter;
import com.xiaoyu.schoolelive.base.BaseSlideBack;
import com.xiaoyu.schoolelive.custom.CircleImageView;
import com.xiaoyu.schoolelive.data.UserFocusData;

import java.util.ArrayList;
import java.util.List;

import com.xiaoyu.schoolelive.R;

/**
 * Created by Administrator on 2017/7/16.
 */
public class UserFocusActivity extends BaseSlideBack {
    private CircleImageView user_focus_image;
    private TextView user_focus_fname;
    private ImageView user_focus_sex;
    private ListView user_focus_listview;
    private UserFocusAdapter focusAdapter;
    private UserFocusData userfocus;
    private List<UserFocusData> data;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_focus);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("我的关注");

        user_focus_image = (CircleImageView)findViewById(R.id.user_focus_image);
        user_focus_fname = (TextView)findViewById(R.id.user_focus_fname);
        user_focus_sex = (ImageView)findViewById(R.id.user_focus_sex);

        user_focus_listview = (ListView)findViewById(R.id.user_focus_listview);

        userfocus = new UserFocusData();

        userfocus.setUser_focus_image(R.drawable.nav_photo);
        userfocus.setUser_focus_fname("紫炎");
        userfocus.setUser_focus_sex(R.drawable.icon_user_male);

        data = new ArrayList<>();

        focusAdapter = new UserFocusAdapter(getApplicationContext(), data);
        focusAdapter.addFocus(userfocus);
        user_focus_listview.setAdapter(focusAdapter);
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
