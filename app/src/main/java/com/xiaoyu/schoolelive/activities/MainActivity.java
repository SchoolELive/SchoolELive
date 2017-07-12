package com.xiaoyu.schoolelive.activities;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.xiaoyu.schoolelive.base.BaseSlideBack;

import xiaoyu.com.schoolelive.R;

public class MainActivity extends BaseSlideBack {
    private Intent intent;
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private ScrollView container = null;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        //标题栏
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.icon_main_home);

        //悬浮按钮
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                intent = new Intent(MainActivity.this,UserReportActivity.class);
//                startActivity(intent);
//            }
//        });
        //获取侧滑栏的头部
        NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view);
        View view = navigationView.inflateHeaderView(R.layout.include_nav_header_main);
        ImageView imageView = (ImageView)view.findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                intent = new Intent(MainActivity.this,UserCenterActivity.class);
                startActivity(intent);
            }
        });
        //侧滑栏菜单点击逻辑
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.collect:
                        intent = new Intent();
                        intent.putExtra("str","collect");
                        intent.setClass(MainActivity.this,HistoryCollectActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.xiangce:
                        intent = new Intent(MainActivity.this,UserAlbumActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.history:
                        intent = new Intent();
                        intent.putExtra("str","history");
                        intent.setClass(MainActivity.this,HistoryCollectActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.report:
                        intent = new Intent(MainActivity.this,UserReportActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.SystemSetting:
                        intent = new Intent(MainActivity.this,SystemSettingActivity.class);
                        startActivity(intent);
                        break;
                }
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        //动态改变主界面

//        GroupActivity groupAc= new GroupActivity();
//        container = (ScrollView) findViewById(R.id.containerBody);
//        //模块1
//        ImageView btnModule1 = (ImageView) findViewById(R.id.btnModule1);
//        btnModule1.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                //移除所有子视图
//                container.removeAllViews();
//                //通过LocalActivityManager通过startAtivity(String id,Intent intent)可以于指定的Activity绑定
//                //并且返回一个Window。LocalActivityManager可以同时管理多个Activity
//                container.addView(getLocalActivityManager().startActivity(
//                        "Module1",
//                        new Intent(MainActivity.this, MainMenuView1.class)
//                                //Intent.FLAG_ACTIVITY_CLEAR_TOP:如果在当前的Task中，有要启动的Activity
//                                //那么就把该Activity之前的所有Activity都关掉，并把此Activity置前以避免创建Activity的实例
//                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
//                        //返回一个View，然后通过与指定容器的addView(View)方法，实现不同的效果
//                        .getDecorView());
//            }
//
//        });
//        // 模块2
//        ImageView btnModule2 = (ImageView) findViewById(R.id.btnModule2);
//        btnModule2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //移除所有子视图
//                container.removeAllViews();
//                container.addView(getLocalActivityManager().startActivity(
//                        "Module2",
//                        new Intent(MainActivity.this, MainMenuView2.class)
//                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
//                        .getDecorView());
//            }
//        });
//
//        // 模块3
//        ImageView btnModule3 = (ImageView) findViewById(R.id.btnModule3);
//        btnModule3.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                //移除所有子视图
//                container.removeAllViews();
//                container.addView(getLocalActivityManager().startActivity(
//                        "Module3",
//                        new Intent(MainActivity.this, MainMenuVie3.class)
//                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
//                        .getDecorView());
//            }
//        });
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    //导入菜单项
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    //标题栏菜单点击逻辑
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                drawer.openDrawer(GravityCompat.START);
                return true;
            case R.id.findButton:
                intent = new Intent(MainActivity.this,FindActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
