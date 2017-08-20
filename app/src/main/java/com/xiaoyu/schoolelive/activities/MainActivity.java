package com.xiaoyu.schoolelive.activities;

import android.animation.ObjectAnimator;
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
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiaoyu.schoolelive.base.BaseMainSlide;
import com.xiaoyu.schoolelive.R;
import com.xiaoyu.schoolelive.custom.CustomFloatingDraftButton;
import com.xiaoyu.schoolelive.data.UserCenter;
import com.xiaoyu.schoolelive.util.AnimationUtil;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MainActivity extends BaseMainSlide{
    private Intent intent,intent_getUid;
    private long uid;//用户的id
    public static boolean boo = false;
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
    private boolean fabOpened = false;
    private TextView textViewCloud;

    @Bind(R.id.floatingActionButton)
    CustomFloatingDraftButton floatingDraftButton;
    @Bind(R.id.floatingActionButton_liveness)
    FloatingActionButton addMsgFloatingButton;
    @Bind(R.id.floatingActionButton_2)
    FloatingActionButton addGoodsFloatingButton;
    @Bind(R.id.floatingActionButton_3)
    FloatingActionButton addPartJobFloatingButton;
    @Bind(R.id.floatingActionButton_4)
    FloatingActionButton floatingActionButton4;
    @Bind(R.id.floatingActionButton_5)
    FloatingActionButton floatingActionButton5;

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
                    case R.id.checkin:
                        intent = new Intent(MainActivity.this,CheckInActivity.class);
                        startActivity(intent);
                }
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    //引入悬浮按钮
    public void mainInitFloatBar() {
        ButterKnife.bind(this);

        floatingDraftButton.registerButton(addMsgFloatingButton);
        floatingDraftButton.registerButton(addGoodsFloatingButton);
        floatingDraftButton.registerButton(addPartJobFloatingButton);
        floatingDraftButton.registerButton(floatingActionButton4);
        floatingDraftButton.registerButton(floatingActionButton5);

        floatingDraftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹出动态button
                AnimationUtil.slideButtons(MainActivity.this,floatingDraftButton);
            }
        });
        addMsgFloatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //关闭动态Button
                AnimationUtil.slideButtons(MainActivity.this,floatingDraftButton);
                Intent intent = new Intent(MainActivity.this, UserAddMsgActivity.class);
                startActivity(intent);
            }
        });
        addGoodsFloatingButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //关闭动态Button
                AnimationUtil.slideButtons(MainActivity.this,floatingDraftButton);
                Intent intent = new Intent(MainActivity.this, UserAddGoodsActivity.class);
                startActivity(intent);
            }
        });
        addPartJobFloatingButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //关闭动态Button
                AnimationUtil.slideButtons(MainActivity.this, floatingDraftButton);
                Intent intent = new Intent(MainActivity.this, UserAddPartJobActivity.class);
                startActivity(intent);
            }
        });

//        textViewCloud =  (TextView)findViewById(R.id.cloud);
//        textViewCloud.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                closeMenu(v);
//            }
//        });
//        //实例化悬浮按钮并绑定事件
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        assert fab != null;
//        fab.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                if (!fabOpened){
//                    openMenu(view);
//                }else{
//                    closeMenu(view);
//                }
//                intent = new Intent(MainActivity.this,UserAddMsgActivity.class);
//                startActivity(intent);
//            }
//        });
    }

    //    //打开蒙版
//    public void openMenu(View view){
//        ObjectAnimator animator = ObjectAnimator.ofFloat(view,"rotation",0,20,-135);
//        animator.setDuration(500);
//        animator.start();
//
//        textViewCloud.setVisibility(View.VISIBLE);
//        AlphaAnimation alphaAnimation = new AlphaAnimation(0,0.7f);
//        alphaAnimation.setDuration(500);
//        alphaAnimation.setFillAfter(true);
//        textViewCloud.startAnimation(alphaAnimation);
//
//        fabOpened = true;
//    }
//    //关闭蒙版
//    public void closeMenu(View view){
//        ObjectAnimator animator = ObjectAnimator.ofFloat(view,"rotation",-135,20,0);
//        animator.setDuration(500);
//        animator.start();
//
//        AlphaAnimation alphaAnimation = new AlphaAnimation(0.7f,0);
//        alphaAnimation.setDuration(500);
//        textViewCloud.startAnimation(alphaAnimation);
//        textViewCloud.setVisibility(View.GONE);
//        fabOpened = false;
//    }
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
                        mainSetHomeFrament();
                        return true;
                    case R.id.navigation_business:
                        mainSetBusinessFrament();
                        return true;
                    case R.id.navigation_secondhand:
                        mainSetSecondFrament();
                        return true;
                    case R.id.navigation_partjob:
                        mainSetPatjobFrament();
                        return true;
                    case R.id.navigation_sysinform:
                        mainSetSysinformFrament();
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

        int toWhere = intent.getIntExtra("toPartJob", 0);
        if (toWhere == 1) {
            // 根据flag来判断显示哪个Fragment
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.main_menu_content, new PartJobFragment());
            transaction.commit();
        }
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

    public void mainSetHomeFrament(){
        getSupportActionBar().setTitle("主页");
        HomeFragment.isDis = true;
        SysInformFragment.SysInformIsDisplay = false;
        fragmentTransaction.replace(R.id.main_menu_content, homeFragment,"home").commit();
    }
    public void mainSetPatjobFrament(){
        getSupportActionBar().setTitle("兼职");
        HomeFragment.isDis = false;
        SysInformFragment.SysInformIsDisplay = false;
        fragmentTransaction.replace(R.id.main_menu_content, partJobFragment,"partjob").commit();
    }
    public void mainSetSecondFrament(){
        getSupportActionBar().setTitle("旧货");
        HomeFragment.isDis = false;
        SysInformFragment.SysInformIsDisplay = false;
        fragmentTransaction.replace(R.id.main_menu_content, secondHandFragment,"secondhand").commit();

    }
    public void mainSetBusinessFrament(){
        getSupportActionBar().setTitle("商家");
        HomeFragment.isDis = false;
        fragmentTransaction.replace(R.id.main_menu_content, businessFragment,"business").commit();
    }
    public void mainSetSysinformFrament(){
        getSupportActionBar().setTitle("通知");
        HomeFragment.isDis = false;
        fragmentTransaction.replace(R.id.main_menu_content, sysInformFragment,"inform").commit();
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
