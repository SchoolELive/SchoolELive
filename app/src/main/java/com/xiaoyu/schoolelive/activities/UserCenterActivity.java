package com.xiaoyu.schoolelive.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.xiaoyu.schoolelive.base.BaseSlideBack;
import com.xiaoyu.schoolelive.custom.CustomImageDialogView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import xiaoyu.com.schoolelive.R;

/**
 * Created by Administrator on 2017/7/11.
 */

public class UserCenterActivity extends BaseSlideBack {
    Bitmap bitmap = null;

    /* 头像文件 */
    private static final String IMAGE_FILE_NAME = "temp_head_image.jpg";

    /* 请求识别码 */
    private static final int CODE_GALLERY_REQUEST = 0xa0;//本地
    private static final int CODE_CAMERA_REQUEST = 0xa1;//拍照
    public static final int CODE_APP_REQUEST = 0xa2;//APP
    private static final int CODE_RESULT_REQUEST = 0xa3;//最终裁剪后的结果

    // 裁剪后图片的宽(X)和高(Y),480 X 480的正方形。
    private static int output_X = 600;
    private static int output_Y = 600;

    private ImageView imageView;
    final String[] items = new String[]{"拍照", "从手机相册选择",
            "从e生活相册选择", "查看头像","编辑资料"};
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_center);

        Toolbar toolbar = (Toolbar)findViewById(R.id.user_center_toolbar);
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout)findViewById(R.id.user_center_layout);
        imageView = (ImageView)findViewById(R.id.user_center_imageview);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        collapsingToolbar.setTitle("紫炎");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        collapsingToolbar.setExpandedTitleColor(Color.WHITE);
        collapsingToolbar.setCollapsedTitleTextColor(Color.BLACK);

        imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                LayoutInflater layoutInflater = LayoutInflater.from(UserCenterActivity.this);
                //自定义对话框标题栏
                View mTitleView = layoutInflater.inflate(R.layout.custom_user_center_dialog, null);
                //创建对话框
                AlertDialog.Builder builder = new AlertDialog.Builder(UserCenterActivity.this);
                builder.setTitle("选择");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    //注册点击事件
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choseHeadImageFromCameraCapture();
                                break;
                            case 1:
                                choseHeadImageFromGallery();
                                break;
                            case 2:
                                choseHeadImageFromApp();
                                break;
                            case 3:
                                bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
                                CustomImageDialogView.Builder dialogBuild = new CustomImageDialogView.Builder(UserCenterActivity.this);
                                dialogBuild.setImage(bitmap);
                                CustomImageDialogView img_dialog = dialogBuild.create();
                                img_dialog.setCanceledOnTouchOutside(true);// 点击外部区域关闭
                                img_dialog.show();
                                break;
                            case 4:
                                Intent intent = new Intent(UserCenterActivity.this,UserInfoActivity.class);
                                startActivity(intent);
                                break;
                        }
                    }
                });
                builder.setCustomTitle(mTitleView);
                AlertDialog dialog = builder.show();
                //设置宽度和高度
                WindowManager.LayoutParams params =
                        dialog.getWindow().getAttributes();
                params.width = 800;
                params.height =1000;
                dialog.getWindow().setAttributes(params);
            }
        });
    }
    // 从本地相册选取图片作为头像
    private void choseHeadImageFromGallery() {
        Intent intentFromGallery = new Intent();
        // 设置文件类型
        intentFromGallery.setType("image/*");//选择图片
        intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
        //如果你想在Activity中得到新打开Activity关闭后返回的数据，
        //你需要使用系统提供的startActivityForResult(Intent intent,int requestCode)方法打开新的Activity
        startActivityForResult(intentFromGallery, CODE_GALLERY_REQUEST);
    }

    // 启动手机相机拍摄照片作为头像
    private void choseHeadImageFromCameraCapture() {
        Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // 判断存储卡是否可用，存储照片文件
        if (hasSdcard()) {
            intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri
                    .fromFile(new File(Environment
                            .getExternalStorageDirectory(), IMAGE_FILE_NAME)));
        }

        startActivityForResult(intentFromCapture, CODE_CAMERA_REQUEST);
    }

    // 从APP用户相册中选择图片作为头像
    private void choseHeadImageFromApp() {
        Intent intentFromApp = new Intent();
        intentFromApp.setClass(getApplication(), UserAlbumActivity.class);
        //标记是从头像选择页面跳转过去的
        intentFromApp.putExtra("motive", CODE_APP_REQUEST);
        startActivity(intentFromApp);
        //startActivityForResult(intentFromApp, CODE_APP_REQUEST);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {
        // 用户没有进行有效的设置操作，返回
        if (resultCode == RESULT_CANCELED) {//取消
            Toast.makeText(getApplication(), "取消", Toast.LENGTH_LONG).show();
            return;
        }
        switch (requestCode) {
            case CODE_GALLERY_REQUEST://如果是来自本地的
                cropRawPhoto(intent.getData());//直接裁剪图片
                break;

            case CODE_CAMERA_REQUEST:
                if (hasSdcard()) {
                    File tempFile = new File(
                            Environment.getExternalStorageDirectory(),
                            IMAGE_FILE_NAME);
                    cropRawPhoto(Uri.fromFile(tempFile));
                } else {
                    Toast.makeText(getApplication(), "没有SDCard!", Toast.LENGTH_LONG)
                            .show();
                }

                break;

            case CODE_APP_REQUEST:
                Toast.makeText(getApplication(), "这是APP", Toast.LENGTH_SHORT).show();
                Uri imgUri = Uri.parse(intent.getStringExtra("image_uri"));
                cropRawPhoto(imgUri);

                break;
            case CODE_RESULT_REQUEST:
                if (intent != null) {
                    setImageToHeadView(intent);//设置图片框
                }
                break;
        }

        super.onActivityResult(requestCode, resultCode, intent);
    }

    /**
     * 裁剪原始的图片
     */
    public void cropRawPhoto(Uri uri) {

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");

        //把裁剪的数据填入里面

        // 设置裁剪
        intent.putExtra("crop", "true");

        // aspectX , aspectY :宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // outputX , outputY : 裁剪图片宽高
        intent.putExtra("outputX", output_X);
        intent.putExtra("outputY", output_Y);
        intent.putExtra("return-data", true);

        startActivityForResult(intent, CODE_RESULT_REQUEST);
    }

    /**
     * 提取保存裁剪之后的图片数据，并设置头像部分的View
     */
    private void setImageToHeadView(Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            imageView.setImageBitmap(photo);
            //新建文件夹 先选好路径 再调用mkdir函数 现在是根目录下面的Ask文件夹
            File nf = new File(Environment.getExternalStorageDirectory() + "/Ask");
            nf.mkdir();
            //在根目录下面的ASk文件夹下 创建okkk.jpg文件
            File f = new File(Environment.getExternalStorageDirectory() + "/Ask", "okkk.png");

            FileOutputStream out = null;
            try {//打开输出流 将图片数据填入文件中
                out = new FileOutputStream(f);
                photo.compress(Bitmap.CompressFormat.PNG, 90, out);

                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


        }
    }

    private Uri saveBitmap(Bitmap bm) {
        File tmpDir = new File(Environment.getExternalStorageDirectory() + "/Ask");
        if (!tmpDir.exists()) {
            tmpDir.mkdir();
        }
        File img = new File(tmpDir.getAbsolutePath() + "okkk.png");
        try {
            FileOutputStream fos = new FileOutputStream(img);
            bm.compress(Bitmap.CompressFormat.PNG, 85, fos);
            fos.flush();
            fos.close();
            return Uri.fromFile(img);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 检查设备是否存在SDCard的工具方法
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            // 有存储的SDCard
            return true;
        } else {
            return false;
        }
    }
    //导入菜单项
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_change_info, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

