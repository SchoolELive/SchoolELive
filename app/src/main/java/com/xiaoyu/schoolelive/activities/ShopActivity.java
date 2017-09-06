package com.xiaoyu.schoolelive.activities;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.xiaoyu.schoolelive.R;
import com.xiaoyu.schoolelive.adapter.MyPagerAdapter;
import com.xiaoyu.schoolelive.adapter.ShopAdapter;
import com.xiaoyu.schoolelive.data.ShopData;
import com.xiaoyu.schoolelive.util.ShopAdvanceUtil;

import java.util.ArrayList;
import java.util.List;


public class ShopActivity extends AppCompatActivity {

    class shopHandler extends Handler {

        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    shop_header_one.setBackgroundColor(Color.BLUE);
                    shop_header_two.setBackgroundColor(Color.WHITE);
                    break;
                case 2:
                    shop_header_one.setBackgroundColor(Color.WHITE);
                    shop_header_two.setBackgroundColor(Color.BLUE);
                    break;
            }
        }
    }
    private LayoutInflater inflater;
    private List<ShopData> mData;
    private ShopAdapter mAdapter;
    private ViewPager viewPager;
    private View view1, view2;
    private List<View> viewList;
    private TextView shop_header_one,shop_header_two;
    private RecyclerView shop_one_recyclerView,shop_two_recyclerView;

    private Handler handler = new shopHandler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        //引入标题栏
        checkInToolBar();
        //初始化界面
        checkIninit();
    }
    //引入标题栏
    private void checkInToolBar() {
        //标题栏
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("金币商城");

    }
    //初始化界面
    private void checkIninit(){
        viewPager = (ViewPager)findViewById(R.id.shop_viewPager);

        inflater = LayoutInflater.from(this);

        view1 = inflater.inflate(R.layout.include_shop_one, null);
        shop_one_recyclerView = (RecyclerView)view1.findViewById(R.id.shop_one_recyclerView);

        view2 = inflater.inflate(R.layout.include_shop_two, null);
        shop_two_recyclerView = (RecyclerView)view1.findViewById(R.id.shop_two_recyclerView);

        mData = new ArrayList<ShopData>();
        mAdapter = new ShopAdapter(this, mData);
        getdata();
        //设置分割线
        shop_one_recyclerView.addItemDecoration(new ShopAdvanceUtil(this));
        //设置布局
        GridLayoutManager gridLayoutManager = new GridLayoutManager(ShopActivity.this, 2);
        shop_one_recyclerView.setLayoutManager(gridLayoutManager);//这里用线性宫格显示 类似于grid view
        shop_one_recyclerView.setAdapter(mAdapter);
        initEvent();

        shop_header_one = (TextView) findViewById(R.id.shop_header_one);
        shop_header_two = (TextView) findViewById(R.id.shop_header_two);

        viewList = new ArrayList<View>();// 将要分页显示的View装入数组中
        viewList.add(view1);
        viewList.add(view2);

        viewPager.setAdapter(new MyPagerAdapter(viewList));
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                if (viewPager.getCurrentItem() == 0) {
                    handler.sendEmptyMessage(1);
                }else if(viewPager.getCurrentItem() == 1){
                    handler.sendEmptyMessage(2);
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }
    //标题栏菜单点击逻辑
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void initEvent() {
        mAdapter.setOnItemClickListener(new ShopAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }
            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }
    public void getdata(){
        for (int i = 0; i < 20; i++) {
            ShopData inform = new ShopData();
            inform.setImage(R.drawable.back);
            inform.setName("好东西");
            inform.setPrice(i+"");
            mData.add(inform);
        }
        mAdapter.notifyDataSetChanged();
    }
}
