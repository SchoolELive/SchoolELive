package com.xiaoyu.schoolelive.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.xiaoyu.schoolelive.R;
import com.xiaoyu.schoolelive.adapter.WaterFallAdapter;
import com.xiaoyu.schoolelive.data.Goods;
import com.xiaoyu.schoolelive.data.ImageBean;
import com.xiaoyu.schoolelive.util.BitmapSampleUtil;
import com.xiaoyu.schoolelive.util.ConstantUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/15.
 */
public class SecondHandFragment extends Fragment {

    @Bind(R.id.goods_recycler_view)
    RecyclerView mRecyclerView;
    private List<Goods> goodsList = new ArrayList<>();
    private WaterFallAdapter mAdapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity())
                .inflate(R.layout.activity_main_menu_secondhand, container, false);
        initView(view, savedInstanceState);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }


//    @Override
//    protected int getLayoutId() {
//        return R.layout.activity_main_menu_secondhand;
//    }


    //@Override
    protected void initView(View view, Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
    }

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    //@Override
    protected void initData() {

        mRecyclerView.setLayoutManager(new
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));


        mAdapter = new WaterFallAdapter(getActivity());
        mRecyclerView.setAdapter(mAdapter);
        //getJsonData();
        getGoodsData();
        mAdapter.setOnItemClickListener(new WaterFallAdapter.OnItemClickListener() {
            public void onItemClick(View view, int position) {
                Goods goods = new Goods();
                goods = goodsList.get(position);
                Intent intent = new Intent(getActivity(), GoodsInfoActivity.class);
                intent.putExtra("tmp_pageViews", String.valueOf(goods.getPageViews()));
                intent.putExtra("tmp_goodsStyle", goods.getGoodsStyle());
                intent.putExtra("tmp_goodsType", goods.getGoodsType());
                toIntentPrice(intent, goods);
                startActivity(intent);
            }

            public void onItemLongClick(View view, int position) {
                Toast.makeText(getContext(), position + " long click",
                        Toast.LENGTH_SHORT).show();
                //mAdapter.removeData(position);
            }
        });


    }

    private void toIntentPrice(Intent intent, Goods goods) {
        if (goods.getGoodsType() == ConstantUtil.Goods_Type_ykj) {
            intent.putExtra("tmp_ykjPrice", String.valueOf(goods.getPrice()));
        } else if (goods.getGoodsType() == ConstantUtil.Goods_Type_yj) {
            intent.putExtra("tmp_yjPrice", String.valueOf(goods.getRefPrice()));
        } else if (goods.getGoodsType() == ConstantUtil.Goods_Type_pai) {
            intent.putExtra("tmp_basePrice", String.valueOf(goods.getBasePrice()));
            intent.putExtra("tmp_nowPrice", String.valueOf(goods.getNowPrice()));
            intent.putExtra("tmp_minPrice", String.valueOf(goods.getMinPrice()));
        }
    }

    private void setPrice(Goods goods, int type) {
        if (type == ConstantUtil.Goods_Type_ykj) {
            goods.setPrice(10);
        } else if (type == ConstantUtil.Goods_Type_yj) {
            goods.setRefPrice(10);
        } else if (type == ConstantUtil.Goods_Type_pai) {
            goods.setBasePrice(10);
            goods.setNowPrice(10);
            goods.setMinPrice(1);
        }
    }

    private void getGoodsData() {
        for (int i = 0; i < 50; i++) {
            Goods goods = new Goods();
            String imgsrc = BitmapSampleUtil.getBmpUrl();
            ImageBean bean = new ImageBean();
            bean.setImgsrc(imgsrc);
            goods.setTopImage(bean);
            goods.setPageViews(58);
            goods.setGoodsStyle(ConstantUtil.Goods_New);
            goods.setGoodsType(ConstantUtil.Goods_Type_yj);
            setPrice(goods, goods.getGoodsType());
            goodsList.add(goods);
        }

        mAdapter.getList().addAll(goodsList);
        mAdapter.getRandomHeight(goodsList);
        mAdapter.notifyDataSetChanged();
    }


}


