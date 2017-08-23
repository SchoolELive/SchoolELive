package com.xiaoyu.schoolelive.activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiaoyu.schoolelive.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ShopItemDetailActivity extends AppCompatActivity {

    @Bind(R.id.shop_detail_image)
    ImageView shop_detail_image;
    @Bind(R.id.shop_detail_price)
    TextView  shop_detail_price;
    @Bind(R.id.shop_detail_change)
    Button shop_detail_change;
    @Bind(R.id.shop_detail_name)
    TextView shop_detail_name;
    @Bind(R.id.shop_detail_content)
    TextView Shop_detail_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_detail);

        ButterKnife.bind(this);

        checkInToolBar();

        getShopIntent();

        shopInitEvent();
    }
    public void getShopIntent(){
        Intent intent = getIntent();

        //shop_detail_image.setImageResource(intent.getIntExtra("shop_image"));
        shop_detail_price.setText(intent.getStringExtra("shop_price"));
        shop_detail_name.setText(intent.getStringExtra("shop_name"));
    }
    //引入标题栏
    private void checkInToolBar() {
        //标题栏
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("物品详情");

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

    public void shopInitEvent(){
        shop_detail_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
