package com.xiaoyu.schoolelive.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.xiaoyu.schoolelive.R;
import com.xiaoyu.schoolelive.util.ShowShareUtil;

/**
 * Created by NeekChaw on 2017-07-29.
 */

public class GoodsInfoActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView btn_more;
    private Button btn_pai;
    private Button btn_ykj;
    private Button btn_yj_mai;
    private Button btn_yj_chat;

    final String[] Items = new String[]{"卖家详情", "收藏", "分享", "举报"};
    final String[] againstItems = new String[]{"泄露隐私", "人身攻击", "淫秽色情", "垃圾广告", "敏感信息", "其他"};

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_info);

        initview();
    }

    public void initview() {
        btn_more = (ImageView) findViewById(R.id.goods_more);
        btn_pai = (Button) findViewById(R.id.btn_pai);
        btn_ykj = (Button) findViewById(R.id.btn_ykj);
        btn_yj_mai = (Button) findViewById(R.id.btn_yj_mai);
        btn_yj_chat = (Button) findViewById(R.id.btn_yj_chat);

        btn_more.setOnClickListener(this);
        btn_pai.setOnClickListener(this);
        btn_ykj.setOnClickListener(this);
        btn_yj_mai.setOnClickListener(this);
        btn_yj_chat.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.goods_more:

                LayoutInflater layoutInflater = LayoutInflater.from(this);
                //自定义对话框标题栏
                final View mTitleView = layoutInflater.inflate(R.layout.custom_user_center_dialog, null);

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setItems(Items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                Intent intent = new Intent(GoodsInfoActivity.this, UserCenterActivity.class);
                                startActivity(intent);
                                break;
                            case 1:
                                Toast.makeText(GoodsInfoActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
                                break;
                            case 2:
                                ShowShareUtil.showShare(GoodsInfoActivity.this);
                                break;
                            case 3:
                                AlertDialog.Builder builder = new AlertDialog.Builder(GoodsInfoActivity.this);
                                builder.setItems(againstItems, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        switch (which) {
                                            case 0://泄露隐私
                                                Toast.makeText(GoodsInfoActivity.this, "举报成功", Toast.LENGTH_SHORT).show();
                                                break;
                                            case 1://人身攻击
                                                Toast.makeText(GoodsInfoActivity.this, "举报成功", Toast.LENGTH_SHORT).show();
                                                break;
                                            case 2://淫秽色情
                                                Toast.makeText(GoodsInfoActivity.this, "举报成功", Toast.LENGTH_SHORT).show();
                                                break;
                                            case 3://垃圾广告
                                                Toast.makeText(GoodsInfoActivity.this, "举报成功", Toast.LENGTH_SHORT).show();
                                                break;
                                            case 4://敏感信息
                                                Toast.makeText(GoodsInfoActivity.this, "举报成功", Toast.LENGTH_SHORT).show();
                                                break;
                                            case 5://其他
                                                Toast.makeText(GoodsInfoActivity.this, "举报成功", Toast.LENGTH_SHORT).show();
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
                dialog.getWindow().setAttributes(params);
                break;
            case R.id.btn_pai:
                break;
            case R.id.btn_ykj:
                break;
            case R.id.btn_yj_mai:
                break;
            case R.id.btn_yj_chat:
                break;
        }
    }
//
//    public void showDialog(View CustomTitle) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(GoodsInfoActivity.this);
//        builder.setItems(againstItems, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                switch (which) {
//                    case 0://泄露隐私
//                        Toast.makeText(GoodsInfoActivity.this, "举报成功", Toast.LENGTH_SHORT).show();
//                        break;
//                    case 1://人身攻击
//                        Toast.makeText(GoodsInfoActivity.this, "举报成功", Toast.LENGTH_SHORT).show();
//                        break;
//                    case 2://淫秽色情
//                        Toast.makeText(GoodsInfoActivity.this, "举报成功", Toast.LENGTH_SHORT).show();
//                        break;
//                    case 3://垃圾广告
//                        Toast.makeText(GoodsInfoActivity.this, "举报成功", Toast.LENGTH_SHORT).show();
//                        break;
//                    case 4://敏感信息
//                        Toast.makeText(GoodsInfoActivity.this, "举报成功", Toast.LENGTH_SHORT).show();
//                        break;
//                    case 5://其他
//                        Toast.makeText(GoodsInfoActivity.this, "举报成功", Toast.LENGTH_SHORT).show();
//                        break;
//                }
//            }
//        });
//        builder.setCustomTitle(CustomTitle);
//        AlertDialog against = builder.show();
//        //设置宽度和高度
//        WindowManager.LayoutParams params =
//                against.getWindow().getAttributes();
//        params.width = 600;
//        params.height = 900;
//        params.y = -200;
//        params.alpha = 0.9f;
//        against.getWindow().setAttributes(params);
//    }
}
