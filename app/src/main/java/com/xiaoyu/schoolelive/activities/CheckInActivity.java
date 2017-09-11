package com.xiaoyu.schoolelive.activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.xiaoyu.schoolelive.R;
import com.xiaoyu.schoolelive.adapter.CheckInAdapter;
import com.xiaoyu.schoolelive.data.ShopData;
import com.xiaoyu.schoolelive.util.ShopAdvanceUtil;
import java.util.ArrayList;
import java.util.List;

public class CheckInActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private CheckInAdapter adapter;
    private List<ShopData> mShopData;
    private GridLayoutManager gridLayoutManager;
    private LinearLayout shop_ll,task_ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.include_shop_around);
        //标题栏
        checkInToolBar();
        //初始化界面
//        checkIninit();

        //初始化界面
        shopItem();
    }
    //商城
    public void shopItem(){
        task_ll = (LinearLayout)findViewById(R.id.task_ll);
        shop_ll = (LinearLayout)findViewById(R.id.shop_ll);

        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerView_one);

        mShopData = new ArrayList<ShopData>();

        adapter = new CheckInAdapter(this, mShopData);

        getdata();

        gridLayoutManager = new GridLayoutManager(CheckInActivity.this, 2);
        //设置分割线
        mRecyclerView.addItemDecoration(new ShopAdvanceUtil(this));

        mRecyclerView.setLayoutManager(gridLayoutManager);//这里用线性宫格显示 类似于grid view
        //设置头部和底部的item单独占据一行
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {

                return (adapter.isHeaderView(position) || adapter.isBottomView(position)) ? gridLayoutManager.getSpanCount() : 1;
            }
        });

        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > task_ll.getHeight() && dy < 300){
                    task_ll.setVisibility(View.VISIBLE);
                    shop_ll.setVisibility(View.INVISIBLE);
                }else if(dy > 325){
                    task_ll.setVisibility(View.INVISIBLE);
                    shop_ll.setVisibility(View.VISIBLE);
                }
            }
        });
        mRecyclerView.setAdapter(adapter);
        initEvent();
    }
    //引入标题栏
    private void checkInToolBar() {
        //标题栏
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("签到中心");

    }
    //初始化界面
    private void checkIninit(){

//        viewPager = (ViewPager) findViewById(R.id.viewpager);
//        LayoutInflater inflater = getLayoutInflater();
//
//        view1 = inflater.inflate(R.layout.include_shop_around, null);
//        view2 = inflater.inflate(R.layout.activity_home_hot, null);
//
//        store_view1 = (TextView) findViewById(R.id.store_view1);
//        store_view2 = (TextView) findViewById(R.id.store_view2);

//        viewList = new ArrayList<View>();// 将要分页显示的View装入数组中
//        viewList.add(view1);
//        viewList.add(view2);

//        viewPager.setAdapter(new MyPagerAdapter(viewList));
//        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//            }
//            @Override
//            public void onPageSelected(int position) {
//                if (viewPager.getCurrentItem() == 0) {
//                    handler.sendEmptyMessage(1);
//                }else if(viewPager.getCurrentItem() == 1){
//                    handler.sendEmptyMessage(2);
//                }
//            }
//            @Override
//            public void onPageScrollStateChanged(int state) {
//            }
//        });
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
        adapter.setOnItemClickListener(new CheckInAdapter.OnItemClickListener() {
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
            inform.setImage(R.drawable.dw_1);
            inform.setName("好东西");
            inform.setPrice(i+"");
            mShopData.add(inform);
        }
        adapter.notifyDataSetChanged();
    }
}