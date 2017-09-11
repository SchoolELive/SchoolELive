package com.xiaoyu.schoolelive.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xiaoyu.schoolelive.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import cn.bingoogolapple.bgabanner.BGABanner;

public class ShopItemDetailActivity extends AppCompatActivity {

    //@Bind(R.id.shop_detail_image)
    ImageView shop_detail_image;
    //@Bind(R.id.shop_detail_price)
    TextView shop_detail_price;
    //@Bind(R.id.shop_detail_change)
    Button shop_detail_change;
    // @Bind(R.id.shop_detail_name)
    TextView shop_detail_name;
    //@Bind(R.id.shop_detail_content)
    TextView Shop_detail_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_detail);

        ButterKnife.bind(this);

        checkInToolBar();

        getShopIntent();

        shopInitEvent();

        setGoodsImg();
    }

    public void getShopIntent() {
        //Intent intent = getIntent();

        //shop_detail_image.setImageResource(intent.getIntExtra("shop_image"));
        //shop_detail_price.setText(intent.getStringExtra("shop_price"));
        //shop_detail_name.setText(intent.getStringExtra("shop_name"));
    }

    //引入标题栏
    private void checkInToolBar() {
        //标题栏
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("积分兑换详情");

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

    public void shopInitEvent() {
//        shop_detail_change.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }

    public void setGoodsImg() {
        BGABanner mShopImages;
        mShopImages = (BGABanner) findViewById(R.id.shop_images);

        mShopImages.setAdapter(new BGABanner.Adapter<ImageView, String>() {
            @Override
            public void fillBannerItem(BGABanner banner, ImageView itemView, String model, int position) {
                Glide.with(ShopItemDetailActivity.this)
                        .load(model)
                        .placeholder(R.drawable.gif_ad_holder)
                        .error(R.mipmap.ic_launcher)
                        .centerCrop()
                        .dontAnimate()
                        .into(itemView);
            }
        });

        List<String> Image_List = new ArrayList<>();
        List<String> wordsList = new ArrayList<>();
        Image_List.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1504509481&di=90f71e005c64f0ce96a0c70a88ddda31&imgtype=jpg&er=1&src=http%3A%2F%2Fi.dimg.cc%2F98%2Fb6%2F89%2F5d%2F7b%2Fcc%2F5a%2F99%2Fa6%2F71%2F51%2F94%2Fdc%2Fdf%2Fd7%2Fa6.jpg");
        wordsList.add("虚位以待");

        mShopImages.setData(Image_List, wordsList);
    }
}
