package com.xiaoyu.schoolelive.activities;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
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
import android.widget.Button;
import android.widget.ImageView;
import com.xiaoyu.schoolelive.base.BaseMainSlide;
import com.xiaoyu.schoolelive.R;
import com.xiaoyu.schoolelive.data.UserCenter;


public class MainActivity extends BaseMainSlide{
    private Intent intent,intent_getUid;
    private long uid;//用户的id
    private static boolean boo = false;
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private HomeFragment homeFragment = new HomeFragment();
    private BusinessFragment businessFragment = new BusinessFragment();
    private PartJobFragment partJobFragment = new PartJobFragment();
    private SecondHandFragment secondHandFragment = new SecondHandFragment();
    private SysInformFragment sysInformFragment = new SysInformFragment();
    private BottomNavigationView navigation;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private String str_name,str_ymd,str_date,str_content;
    //引入侧滑栏布局
    public void mainInitSlidView(){
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
    //引入悬浮按钮
    public void mainInitFloatBar(){
        //实例化悬浮按钮并绑定事件
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                intent = new Intent(MainActivity.this,UserAddMsgActivity.class);
                startActivity(intent);
            }
        });
    }
    //引入标题栏
    private void mainInitToolBar(){
        //标题栏
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        //判断用户是否登录，
        if (!boo){
            //显示未登录图像
            actionBar.setHomeAsUpIndicator(R.drawable.icon_main_home);
        }else{
            //显示已登录用户的头像
        }
    }
    //在程序中加入默认Fragment
    public void mainAddFragment(){
        fragmentManager = getSupportFragmentManager();
        //开启一个Fragment事务
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.main_menu_content, homeFragment,"home");
        fragmentTransaction.commit();
    }
    //引入底部菜单
    public void mainInitBottomBar(){
        //实例化BottomNavigationView菜单
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        //BottomNavigationView菜单点击逻辑
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                mainFragmentAllRemove();
                //在程序中加入Fragment
                fragmentManager = getSupportFragmentManager();
                //开启一个Fragment事务
                fragmentTransaction = fragmentManager.beginTransaction();
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        getSupportActionBar().setTitle("主页");
                        HomeFragment.isDis = true;
                        fragmentTransaction.replace(R.id.main_menu_content, homeFragment,"home").commit();
                        return true;
                    case R.id.navigation_business:
                        getSupportActionBar().setTitle("商家");
                        HomeFragment.isDis = false;
                        fragmentTransaction.replace(R.id.main_menu_content, businessFragment,"business").commit();
                        return true;
                    case R.id.navigation_secondhand:
                        getSupportActionBar().setTitle("旧货");
                        HomeFragment.isDis = false;
                        fragmentTransaction.replace(R.id.main_menu_content, secondHandFragment,"secondhand").commit();
                        return true;
                    case R.id.navigation_partjob:
                        getSupportActionBar().setTitle("兼职");
                        HomeFragment.isDis = false;
                        fragmentTransaction.replace(R.id.main_menu_content, partJobFragment,"partjob").commit();
                        return true;
                    case R.id.navigation_sysinform:
                        getSupportActionBar().setTitle("通知");
                        HomeFragment.isDis = false;
                        fragmentTransaction.replace(R.id.main_menu_content, sysInformFragment,"inform").commit();
                        return true;
                }
                return false;
            }
        });
    }
    //得到所有Intent过来的数据
    public void mainGetAllIntent(){
        intent = getIntent();
        str_name = intent.getStringExtra("tmp_name");
        str_ymd = intent.getStringExtra("tmp_ymd");
        str_date = intent.getStringExtra("tmp_date");
        str_content = intent.getStringExtra("tmp_content");

        //得到用户的Id
        intent_getUid = getIntent();
        uid = intent_getUid.getLongExtra("uid",0);
    }
    //处理获取的Intent数据
    public void mainDealIntent(){
        //判断Intent
        if (intent != null ){
            Bundle bundle = new Bundle();
            bundle.putString("tmp_name",str_name);
            bundle.putString("tmp_ymd",str_ymd);
            bundle.putString("tmp_date",str_date);
            bundle.putString("tmp_content",str_content);
            homeFragment.setArguments(bundle);
        }
    }
    //隐藏所有的Fragment
    public void mainFragmentAllRemove(){
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

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        //引入侧滑栏布局
        mainInitSlidView();
        //引入悬浮按钮
        mainInitFloatBar();
        //引入标题栏
        mainInitToolBar();
        //在程序中加入默认Fragment
        mainAddFragment();
        //引入底部菜单
        mainInitBottomBar();
        //得到所有Intent过来的数据
        mainGetAllIntent();
        //处理获取的Intent数据
        mainDealIntent();

    }
}
