package com.xiaoyu.schoolelive.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.xiaoyu.schoolelive.base.BaseSlideBack;
import com.xiaoyu.schoolelive.custom.CustomBar;

import com.xiaoyu.schoolelive.R;

/**
 * Created by Administrator on 2017/7/11.
 */

public class UserInfoActivity extends BaseSlideBack implements View.OnClickListener{
    private CustomBar custom_photo,custom_fname,custom_about,custom_fav,custom_tname,custom_sex,custom_bri,custom_address;
    private Intent intent;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        custom_photo = (CustomBar)findViewById(R.id.custom_photo);
        custom_photo.setOnClickListener(this);
        custom_fname = (CustomBar)findViewById(R.id.custom_fname);
        custom_fname.setOnClickListener(this);
        custom_about = (CustomBar)findViewById(R.id.custom_about);
        custom_about.setOnClickListener(this);
        custom_fav = (CustomBar)findViewById(R.id.custom_fav);
        custom_fav.setOnClickListener(this);
        custom_tname = (CustomBar)findViewById(R.id.custom_tname);
        custom_tname.setOnClickListener(this);
        custom_sex = (CustomBar)findViewById(R.id.custom_sex);
        custom_sex.setOnClickListener(this);
        custom_bri = (CustomBar)findViewById(R.id.custom_bri);
        custom_bri.setOnClickListener(this);
        custom_address = (CustomBar)findViewById(R.id.custom_address);
        custom_address.setOnClickListener(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("编辑信息");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.custom_photo:
                Toast.makeText(UserInfoActivity.this,"点击了"+custom_photo.getInfo_menu_name(),Toast.LENGTH_LONG).show();
                break;
            case R.id.custom_fname:
                new AlertDialog.Builder(this)
                        .setTitle("请输入新的昵称")
                        .setIcon(R.drawable.side_nav_bar)
                        .setView(new EditText(this))
                        .setPositiveButton("确定",null)
                        .setNegativeButton("取消",null)
                        .show();
                break;
            case R.id.custom_about:
                new AlertDialog.Builder(this)
                        .setTitle("请输入新的介绍")
                        .setIcon(R.drawable.side_nav_bar)
                        .setView(new EditText(this))
                        .setPositiveButton("确定",null)
                        .setNegativeButton("取消",null)
                        .show();
                break;
            case R.id.custom_fav:
                new AlertDialog.Builder(this)
                        .setTitle("请选择你的兴趣")
                        .setIcon(R.drawable.side_nav_bar)
                        .setMultiChoiceItems(new String[]{"敲代码","写博客","查资料","？？？","其他"},null,null)
                        .setPositiveButton("确定",null)
                        .setNegativeButton("取消",null)
                        .show();
                break;
            case R.id.custom_tname:
                new AlertDialog.Builder(this)
                        .setTitle("请输入新的昵称")
                        .setIcon(R.drawable.side_nav_bar)
                        .setView(new EditText(this))
                        .setPositiveButton("确定",null)
                        .setNegativeButton("取消",null)
                        .show();
                break;
            case R.id.custom_sex:
                new AlertDialog.Builder(this)
                        .setTitle("------")
                        .setIcon(R.drawable.side_nav_bar)
                        .setSingleChoiceItems(new String[]{"男","女"},0,null)
                        .setPositiveButton("确定",null)
                        .setNegativeButton("取消",null)
                        .show();
                break;
            case R.id.custom_bri:
                new AlertDialog.Builder(this)
                        .setView(R.layout.datepicker)
                        .setPositiveButton("确定",null)
                        .setNegativeButton("取消",null)
                        .show();
                break;
            case R.id.custom_address:
                intent = new Intent(UserInfoActivity.this,MySpinnerActivity.class);
                startActivity(intent);
                break;
        }
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

