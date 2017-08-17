package com.xiaoyu.schoolelive.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.xiaoyu.schoolelive.R;
import com.xiaoyu.schoolelive.custom.CustomDialog;
import com.xiaoyu.schoolelive.data.Goods;
import com.xiaoyu.schoolelive.util.ConstantUtil;
import com.xiaoyu.schoolelive.util.ShowShareUtil;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * Created by NeekChaw on 2017-07-29.
 */


public class GoodsInfoActivity extends AppCompatActivity implements View.OnClickListener {
    //竞拍
    private TextView mBasePrice;
    private TextView mNowPrice;
    private TextView mMinPrice;
    //一口价
    private TextView mPrice;
    //可议价
    private TextView mRefPrice;

    private ImageView btn_more;
    private Button btn_pai;
    private Button btn_ykj;
    private Button btn_yj_mai;
    private Button btn_yj_chat;
    private Goods mGoods;
    private TextView mGoodsPageViews;
    private TextView mGoodsHot;
    private TextView mGoodsNew;
    private TextView mGoodsTop;
    private View mGoodsPai;
    private View mGoodsYKJ;
    private View mGoodsYJ;
    private BGABanner mGoodsImages;
    final String[] mItems = new String[]{"卖家详情", "关注卖家", "收藏宝贝", "分享宝贝", "举报"};
    final String[] mAgainstItems = new String[]{"泄露隐私", "人身攻击", "淫秽色情", "垃圾广告", "敏感信息", "其他"};

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_info);
        initView();
        setGoodsImages();
    }

    public void initView() {
        Intent intent = getIntent();
        mGoods = new Goods();

        findById();
        //设置商品类型
        setGoodsType(intent.getIntExtra("tmp_goodsType", 0));
        //设置浏览量
        mGoodsPageViews.setText(intent.getStringExtra("tmp_pageViews"));
        //设置商品标签
        setGoodsStyle(intent.getIntExtra("tmp_goodsStyle", 0));
        //设置商品价格
        setGoodsPrice(intent, intent.getIntExtra("tmp_goodsType", 0));

        btn_more.setOnClickListener(this);
        btn_pai.setOnClickListener(this);
        btn_ykj.setOnClickListener(this);
        btn_yj_mai.setOnClickListener(this);
        btn_yj_chat.setOnClickListener(this);
    }

    public void findById() {
        btn_more = (ImageView) findViewById(R.id.goods_more);
        btn_pai = (Button) findViewById(R.id.btn_pai);
        btn_ykj = (Button) findViewById(R.id.btn_ykj);
        btn_yj_mai = (Button) findViewById(R.id.btn_yj_mai);
        btn_yj_chat = (Button) findViewById(R.id.btn_yj_chat);
        mGoodsImages = (BGABanner) findViewById(R.id.goods_images_banner);
        mGoodsPageViews = (TextView) findViewById(R.id.goods_attention_count);
        mGoodsHot = (TextView) findViewById(R.id.goods_hot);
        mGoodsNew = (TextView) findViewById(R.id.goods_new);
        mGoodsTop = (TextView) findViewById(R.id.goods_top);
        mGoodsPai = findViewById(R.id.goodsType_pai);
        mGoodsYKJ = findViewById(R.id.goodsType_ykj);
        mGoodsYJ = findViewById(R.id.goodsType_yj);

        //竞拍价格
        mBasePrice = (TextView) findViewById(R.id.pai_base_price);
        mNowPrice = (TextView) findViewById(R.id.pai_now_price);
        mMinPrice = (TextView) findViewById(R.id.pai_min_price);
        //一口价价格
        mPrice = (TextView) findViewById(R.id.yikoujia_price);
        //可议价价格
        mRefPrice = (TextView) findViewById(R.id.yj_price);
    }

    public void setGoodsImages() {
        mGoodsImages.setAdapter(new BGABanner.Adapter<ImageView, String>() {
            @Override
            public void fillBannerItem(BGABanner banner, ImageView itemView, String model, int position) {
                Glide.with(GoodsInfoActivity.this)
                        .load(model)
                        .placeholder(R.drawable.ic_imgloader)
                        .error(R.drawable.ic_imgloader)
                        .centerCrop()
                        .dontAnimate()
                        .into(itemView);
            }
        });

        List<String> imgList = new ArrayList<>();
        imgList.add("http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered11.png");
        imgList.add("http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered12.png");
        imgList.add("http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered13.png");
        List<String> wordsList = new ArrayList<>();
        wordsList.add("提示文字1");
        wordsList.add("提示文字2");
        wordsList.add("提示文字3");
        mGoodsImages.setData(imgList, wordsList);
    }

    public void setGoodsStyle(int type) {
        if (type == ConstantUtil.Goods_All) {
            mGoodsHot.setVisibility(View.VISIBLE);
            mGoodsNew.setVisibility(View.VISIBLE);
            mGoodsTop.setVisibility(View.VISIBLE);
        } else if (type == ConstantUtil.Goods_Hot) {
            mGoodsHot.setVisibility(View.VISIBLE);
        } else if (type == ConstantUtil.Goods_New) {
            mGoodsNew.setVisibility(View.VISIBLE);
        } else if (type == ConstantUtil.Goods_Top) {
            mGoodsTop.setVisibility(View.VISIBLE);
        } else if (type == ConstantUtil.Goods_Top_Hot) {
            mGoodsTop.setVisibility(View.VISIBLE);
            mGoodsHot.setVisibility(View.VISIBLE);
        } else if (type == ConstantUtil.Goods_Hot_New) {
            mGoodsHot.setVisibility(View.VISIBLE);
            mGoodsNew.setVisibility(View.VISIBLE);
        } else if (type == ConstantUtil.Goods_Top_New) {
            mGoodsTop.setVisibility(View.VISIBLE);
            mGoodsNew.setVisibility(View.VISIBLE);
        }
    }

    public void setGoodsType(int type) {
        if (type == ConstantUtil.Goods_Type_pai) {
            mGoodsPai.setVisibility(View.VISIBLE);
        } else if (type == ConstantUtil.Goods_Type_yj) {
            mGoodsYJ.setVisibility(View.VISIBLE);
        } else if (type == ConstantUtil.Goods_Type_ykj) {
            mGoodsYKJ.setVisibility(View.VISIBLE);
        }
    }

    public void setGoodsPrice(Intent intent, int type) {
        if (type == ConstantUtil.Goods_Type_pai) {
            mMinPrice.setText(intent.getStringExtra("tmp_minPrice"));
            mBasePrice.setText(intent.getStringExtra("tmp_basePrice"));
            mNowPrice.setText(intent.getStringExtra("tmp_nowPrice"));
        } else if (type == ConstantUtil.Goods_Type_yj) {
            mRefPrice.setText(intent.getStringExtra("tmp_yjPrice"));
        } else if (type == ConstantUtil.Goods_Type_ykj) {
            mPrice.setText(intent.getStringExtra("tmp_ykjPrice"));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.goods_more:
                showDialog(mGoods);
                break;
            case R.id.btn_pai:
                showPaiDialog();
                break;
            case R.id.btn_ykj:
                showYKJDialog();
                break;
            case R.id.btn_yj_chat:
                showYJDialog();
                break;
            case R.id.btn_yj_mai:
                showYJ_Mai_Dialog();
                break;
        }
    }
    //

    private void showDialog(final Goods goods) {
        LayoutInflater layoutInflater = LayoutInflater.from(GoodsInfoActivity.this);
        //自定义对话框标题栏
        final View mTitleView = layoutInflater.inflate(R.layout.custom_user_center_dialog, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(GoodsInfoActivity.this);
        builder.setItems(mItems, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        Intent intent = new Intent(GoodsInfoActivity.this, UserCenterActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        if (goods.isFocus()) {
                            Toast.makeText(GoodsInfoActivity.this, "您已关注该用户", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(GoodsInfoActivity.this, "关注成功", Toast.LENGTH_SHORT).show();
                            goods.setFocus(true);
                        }
                        break;
                    case 2:
                        Toast.makeText(GoodsInfoActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        ShowShareUtil.showShare(GoodsInfoActivity.this);
                        break;
                    case 4:
                        AlertDialog.Builder builder = new AlertDialog.Builder(GoodsInfoActivity.this);
                        builder.setItems(mAgainstItems, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0://泄露隐私
                                        dealClick(goods, which);
                                        break;
                                    case 1://人身攻击
                                        dealClick(goods, which);
                                        break;
                                    case 2://淫秽色情
                                        dealClick(goods, which);
                                        break;
                                    case 3://垃圾广告
                                        dealClick(goods, which);
                                        break;
                                    case 4://敏感信息
                                        dealClick(goods, which);
                                        break;
                                    case 5://其他
                                        dealClick(goods, which);
                                        break;
                                }
                            }
                        });
                        AlertDialog against = builder.show();
                        //设置宽度和高度
                        WindowManager.LayoutParams params =
                                against.getWindow().getAttributes();
                        params.width = 600;
                        params.height = 900;
                        params.y = -200;
                        params.alpha = 0.9f;
                        against.getWindow().setAttributes(params);
                        break;
                }
            }
        });
        builder.setCustomTitle(mTitleView);
        AlertDialog dialog = builder.show();
        //设置宽度和高度
        WindowManager.LayoutParams params =
                dialog.getWindow().getAttributes();
        params.width = 600;
        params.alpha = 0.9f;
        params.y = -200;
        dialog.getWindow().setAttributes(params);
    }

    //判断是否重复举报
    public boolean isAgainst(Goods goods) {
        if (goods.isAgainst()) {
            goods.setAgainst(false);
            return true;
        } else {
            return false;
        }
    }

    //对举报信息进行处理
    public void dealClick(Goods goods, int dealCode) {
        if (isAgainst(goods)) {
            Toast.makeText(GoodsInfoActivity.this, "您已经举报过了！管理员正在审核处理", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(GoodsInfoActivity.this, "举报成功",
                    Toast.LENGTH_SHORT).show();
            goods.setAgainst(true);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void showPaiDialog() {
        View view = View.inflate(this, R.layout.custom_dialog_pai, null);
        TextView basePrice = (TextView) view.findViewById(R.id.basePrice);
        TextView nowPrice = (TextView) view.findViewById(R.id.nowPrice);
        TextView addPrice = (TextView) view.findViewById(R.id.addPrice);
        basePrice.setText(mBasePrice.getText());
        nowPrice.setText(mNowPrice.getText());
        addPrice.setText(mMinPrice.getText());
        final CustomDialog.Builder builder = new CustomDialog.Builder(GoodsInfoActivity.this);
        builder.setTitle("竞拍").
                setContentView(view).
                setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).create().show();
    }

    public void showYKJDialog() {
        View view = View.inflate(this, R.layout.custom_dialog_ykj, null);
        final CustomDialog.Builder builder = new CustomDialog.Builder(GoodsInfoActivity.this);
        builder.setTitle("一口价").
                setContentView(view).
                setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).create().show();
    }

    public void showYJ_Mai_Dialog() {
        View view = View.inflate(this, R.layout.custom_dialog_ykj, null);
        final CustomDialog.Builder builder = new CustomDialog.Builder(GoodsInfoActivity.this);
        builder.setTitle("确认购买").
                setContentView(view).
                setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).create().show();
    }

    public void showYJDialog() {
        View view = View.inflate(this, R.layout.custom_dialog_yj, null);
        TextView refPrice = (TextView) view.findViewById(R.id.refPrice);
        refPrice.setText(mRefPrice.getText());
        final CustomDialog.Builder builder = new CustomDialog.Builder(GoodsInfoActivity.this);
        builder.setTitle("可议价").
                setContentView(view).
                setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).create().show();
    }

}