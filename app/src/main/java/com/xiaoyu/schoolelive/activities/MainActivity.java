package com.xiaoyu.schoolelive.activities;

import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;



import com.xiaoyu.schoolelive.adapter.PublishAdapter;
import com.xiaoyu.schoolelive.base.BaseMainSlide;

import com.xiaoyu.schoolelive.R;
import com.xiaoyu.schoolelive.data.Publish;


import java.util.List;


public class MainActivity extends BaseMainSlide{
    private Intent intent,intent_getUid;
    private long uid;//用户的id
    private static boolean boo = false;
    //侧滑栏
    private DrawerLayout drawer;
    //标题栏
    private Toolbar toolbar;
    //底部菜单的fragment
    private HomeFragment homeFragment;
    private BusinessFragment businessFragment = new BusinessFragment();
    private PartJobFragment partJobFragment = new PartJobFragment();
    private SecondHandFragment secondHandFragment = new SecondHandFragment();
    private SysInformFragment sysInformFragment = new SysInformFragment();

    private BottomNavigationView navigation;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    private ListView publish_list;
    private List<Publish> data;
    private PublishAdapter adapterPublish;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intent = getIntent();

        String str_name = intent.getStringExtra("tmp_name");
        String str_ymd = intent.getStringExtra("tmp_ymd");
        String str_date = intent.getStringExtra("tmp_date");
        String str_content = intent.getStringExtra("tmp_content");

        //在程序中加入Fragment
        fragmentManager = getSupportFragmentManager();
        //开启一个Fragment事务
        fragmentTransaction = fragmentManager.beginTransaction();
        homeFragment = new HomeFragment();

        if (intent != null ){
            Bundle bundle = new Bundle();
            bundle.putString("tmp_name",str_name);
            bundle.putString("tmp_ymd",str_ymd);
            bundle.putString("tmp_date",str_date);
            bundle.putString("tmp_content",str_content);
            homeFragment.setArguments(bundle);
        }

        fragmentTransaction.add(R.id.main_menu_content, homeFragment,"home");
        fragmentTransaction.commit();
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        //标题栏
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        if (!boo){
            actionBar.setHomeAsUpIndicator(R.drawable.icon_main_home);
        }else{
            //显示已登录用户的头像
        }
        //悬浮按钮
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                intent = new Intent(MainActivity.this,UserAddMsgActivity.class);
                startActivity(intent);
            }
        });
        //获取侧滑栏的头部
        NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view);
        View view = navigationView.inflateHeaderView(R.layout.include_nav_header_main);
        ImageView imageView = (ImageView)view.findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //drawer.closeDrawer(GravityCompat.START);
                intent = new Intent(MainActivity.this,UserCenterActivity.class);
                intent.putExtra("uid",uid);
                startActivity(intent);
            }
        });

        intent_getUid = getIntent();
        uid = intent_getUid.getLongExtra("uid",0);

        //侧滑栏菜单点击逻辑
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            public boolean onNavigationItemSelected(MenuItem i) {
                switch (i.getItemId()){
                    case R.id.focus:
                        intent = new Intent(MainActivity.this,UserFocusActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.collect:
                        intent = new Intent();
                        intent.putExtra("str","collect");
                        intent.setClass(MainActivity.this,HistoryCollectActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.gallery:
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
    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            fragmentAllRemove();
            //在程序中加入Fragment
            fragmentManager = getSupportFragmentManager();
            //开启一个Fragment事务
            fragmentTransaction = fragmentManager.beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    getSupportActionBar().setTitle("主页");
                    fragmentTransaction.replace(R.id.main_menu_content, homeFragment,"home").commit();
                    return true;
                case R.id.navigation_business:
                    getSupportActionBar().setTitle("商家");
                    fragmentTransaction.replace(R.id.main_menu_content, businessFragment,"business").commit();
                    return true;
                case R.id.navigation_secondhand:
                    getSupportActionBar().setTitle("旧货");
                    fragmentTransaction.replace(R.id.main_menu_content, secondHandFragment,"secondhand").commit();
                    return true;
                case R.id.navigation_partjob:
                    getSupportActionBar().setTitle("兼职");
                    fragmentTransaction.replace(R.id.main_menu_content, partJobFragment,"partjob").commit();
                    return true;
                case R.id.navigation_sysinform:
                    getSupportActionBar().setTitle("通知");
                    fragmentTransaction.replace(R.id.main_menu_content, sysInformFragment,"inform").commit();
                    return true;
            }
            return false;
        }

    };
    public void fragmentAllRemove(){
        if (homeFragment != null){
              fragmentTransaction.hide(homeFragment);
        }
        if (businessFragment != null){
            fragmentTransaction.hide(businessFragment);
        }
        if (secondHandFragment != null){
            fragmentTransaction.hide(secondHandFragment);
        }
        if (partJobFragment != null){
            fragmentTransaction.hide(partJobFragment);
        }
        if (sysInformFragment != null){
            fragmentTransaction.hide(sysInformFragment);
        }
    }
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
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
                if (!boo){
                    intent = new Intent(MainActivity.this,LoginActivity.class);
                    startActivity(intent);
                }else{
                    drawer.openDrawer(GravityCompat.START);
                }
                return true;
            case R.id.findButton:
                intent = new Intent(MainActivity.this,FindActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
