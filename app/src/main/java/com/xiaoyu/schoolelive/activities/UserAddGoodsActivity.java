package com.xiaoyu.schoolelive.activities;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bumptech.glide.Glide;
import com.nostra13.universalimageloader.utils.L;
import com.xiaoyu.schoolelive.R;
import com.xiaoyu.schoolelive.adapter.PhotoAdapter;
import com.xiaoyu.schoolelive.base.BaseSlideBack;
import com.xiaoyu.schoolelive.util.RecyclerItemClickListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPreview;

/**
 * Created by NeekChaw on 2017-07-30.
 */
public class UserAddGoodsActivity extends BaseSlideBack implements RadioGroup.OnCheckedChangeListener{
    private RadioGroup radioGroup;
    private RadioButton fixed_price,auction,negotiable;
    private LinearLayout price_ll,auction_ll;

    private PhotoAdapter photoAdapter;
    private ArrayList<String> selectedPhotos = new ArrayList<>();
    //private ArrayList<String> onLongClickListData = new ArrayList<>();
    private ImageView iv_crop;
    private RecyclerView recyclerView;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    price_ll.setVisibility(View.VISIBLE);
                    auction_ll.setVisibility(View.GONE);
                    break;
                case 2:
                    price_ll.setVisibility(View.GONE);
                    auction_ll.setVisibility(View.VISIBLE);
                    break;
            }
            super.handleMessage(msg);
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_publish);

        //初始化控件
        initView();
        //方式选择
        initChoose();
    }

    //初始化控件
    public void initView() {
        iv_crop = (ImageView) findViewById(R.id.iv_crop);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        photoAdapter = new PhotoAdapter(this, selectedPhotos);

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, OrientationHelper.VERTICAL));
        recyclerView.setAdapter(photoAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if (photoAdapter.getItemViewType(position) == PhotoAdapter.TYPE_ADD) {
                            PhotoPicker.builder()
                                    .setPhotoCount(PhotoAdapter.MAX)
                                    .setShowCamera(true)
                                    .setPreviewEnabled(true)
                                    .setSelected(selectedPhotos)
                                    .start(UserAddGoodsActivity.this);
                        } else {
                            PhotoPreview.builder()
                                    .setPhotos(selectedPhotos)
                                    .setCurrentItem(position)
                                    .start(UserAddGoodsActivity.this);
                        }
                    }
                }));


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //选择返回
        if (resultCode == RESULT_OK &&
                (requestCode == PhotoPicker.REQUEST_CODE || requestCode == PhotoPreview.REQUEST_CODE)) {
            iv_crop.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);

            List<String> photos = null;
            if (data != null) {
                photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
            }
            selectedPhotos.clear();
            if (photos != null) {
                selectedPhotos.addAll(photos);
            }
            photoAdapter.notifyDataSetChanged();
        }
        //拍照功能或者裁剪功能返回
        if (resultCode == RESULT_OK && requestCode == PhotoPicker.CROP_CODE) {
            iv_crop.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            Glide.with(getApplicationContext()).load(Uri.fromFile(new File(data.getStringExtra(PhotoPicker.KEY_CAMEAR_PATH)))).into(iv_crop);
        }
    }
    //方式选择
    public void initChoose(){
        radioGroup = (RadioGroup)findViewById(R.id.radioGroup);

        fixed_price = (RadioButton)findViewById(R.id.fixed_price);
        negotiable = (RadioButton)findViewById(R.id.negotiable);
        price_ll = (LinearLayout)findViewById(R.id.price_ll);

        auction = (RadioButton)findViewById(R.id.auction);
        auction_ll = (LinearLayout)findViewById(R.id.auction_ll);

        radioGroup.setOnCheckedChangeListener(this);
    }
    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        Message message = new Message();
        if (checkedId == fixed_price.getId() || checkedId == negotiable.getId()){
            message.what = 1;
            handler.sendMessage(message);
        }else if(checkedId == auction.getId()){
            message.what = 2;
            handler.sendMessage(message);
        }
    }
}
