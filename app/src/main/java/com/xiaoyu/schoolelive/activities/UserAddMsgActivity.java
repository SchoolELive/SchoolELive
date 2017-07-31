package com.xiaoyu.schoolelive.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.xiaoyu.schoolelive.R;
import com.xiaoyu.schoolelive.adapter.PhotoAdapter;
import com.xiaoyu.schoolelive.base.BaseSlideBack;
import com.xiaoyu.schoolelive.data.Publish;
import com.xiaoyu.schoolelive.util.BitmapUtils;
import com.xiaoyu.schoolelive.util.ConstantUtil;
import com.xiaoyu.schoolelive.util.RecyclerItemClickListener;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPreview;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static java.lang.System.currentTimeMillis;

public class UserAddMsgActivity extends BaseSlideBack {

    private EditText add_msg_textContent;
    private TextView add_msg_textNum;
    private Publish publish;
    private Button postButton;

    private PhotoAdapter photoAdapter;

    private ArrayList<String> selectedPhotos = new ArrayList<>();
    //private ArrayList<String> onLongClickListData = new ArrayList<>();

    private ImageView iv_crop;
    private RecyclerView recyclerView;
    private List<String> photos;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_add_msg);
        //标题栏
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        initView();

        textNumEvent();
    }


    //初始化控件
    public void initView() {
        add_msg_textContent = (EditText) findViewById(R.id.add_msg_textContent);
        add_msg_textNum = (TextView) findViewById(R.id.add_msg_textNum);
        //add_msg_pic = (GridView) findViewById(R.id.pic_gridview);
        postButton = (Button) findViewById(R.id.postButton);

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
                                    .start(UserAddMsgActivity.this);
                        } else {
                            PhotoPreview.builder()
                                    .setPhotos(selectedPhotos)
                                    .setCurrentItem(position)
                                    .start(UserAddMsgActivity.this);
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

            photos = null;
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

    //数字显示事件处理
    public void textNumEvent() {
        add_msg_textContent.addTextChangedListener(new TextWatcher() {
            private CharSequence temp;
            private int editStart;
            private int editEnd;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                temp = s;
            }

            @Override
            public void afterTextChanged(Editable s) {
                editStart = add_msg_textContent.getSelectionStart();
                editEnd = add_msg_textContent.getSelectionEnd();
                add_msg_textNum.setText(String.valueOf(140 - temp.length()));
            }
        });
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendUserMessage();
            }
        });
    }

    //导入菜单项
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.add_msg_menu, menu);
//        return true;
//    }
    //标题栏菜单点击逻辑
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //发送数据
    public void sendUserMessage() {
        if (add_msg_textContent.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "动态不能为空！", Toast.LENGTH_SHORT).show();
        } else {
            final ProgressDialog progressDialog = new ProgressDialog(UserAddMsgActivity.this);
            progressDialog.setMessage("发帖中...");
            progressDialog.setCancelable(true);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    upLoad();
                    progressDialog.dismiss();//发帖完成消除progressDialog
                }
            }).start();
            Intent intent = new Intent(UserAddMsgActivity.this,MainActivity.class);
            startActivity(intent);
            // 生成数据
//                       publish = new Publish();
//            //获取年月日
//            SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
//            Date curYMD = new Date(currentTimeMillis());
//            String str = ymd.format(curYMD);
//            //获取时间
//            SimpleDateFormat date = new SimpleDateFormat("  HH:mm");
//            Date curDate = new Date(currentTimeMillis());
//            String str2 = date.format(curDate);
//
//            Intent intent = new Intent(UserAddMsgActivity.this, MainActivity.class);
//
//            intent.putExtra("tmp_name", "发布者：***");
//            intent.putExtra("tmp_ymd", str);
//            intent.putExtra("tmp_date", str2);
//            intent.putExtra("tmp_content", add_msg_textContent.getText().toString());
//            startActivity(intent);
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    finish();
//                }
//            }).start();


        }
    }

    private void upLoad() {

        final String url = ConstantUtil.SERVICE_PATH + "upload.php";
        MultipartBody.Builder mbody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        for (int i = 0; i < photos.size(); i++) {
            File file = new File(photos.get(i));//得到选择的图片
            String str = BitmapUtils.compressImageUpload(file.getPath());//得到压缩过的文件路径
            File compress_file = new File(str);//得到新文件
            mbody.addFormDataPart("image" + i, compress_file.getName(), RequestBody.create(MediaType.parse("application/octet-stream"), compress_file));//添加到mbody中
        }
        RequestBody requestBody = mbody.build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        final okhttp3.OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();
        OkHttpClient okHttpClient = httpBuilder
                //设置超时
                .connectTimeout(3000, TimeUnit.MINUTES)
                .readTimeout(3000, TimeUnit.MINUTES)
                .writeTimeout(3000, TimeUnit.MINUTES)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("aa", "uploadMultiFile() e=" + e);
            }

            public void onResponse(Call call, Response response) throws IOException {
                Log.i("bb", "uploadMultiFile() response=" + response.body().string());
                BitmapUtils.deleteCacheFile();//清除缓存
            }
        });


    }


}


