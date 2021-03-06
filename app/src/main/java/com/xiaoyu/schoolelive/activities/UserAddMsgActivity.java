package com.xiaoyu.schoolelive.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xiaoyu.schoolelive.R;
import com.xiaoyu.schoolelive.adapter.PhotoAdapter;
import com.xiaoyu.schoolelive.base.BaseSlideBack;
import com.xiaoyu.schoolelive.util.RecyclerItemClickListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPreview;

public class UserAddMsgActivity extends BaseSlideBack {

    private EditText add_msg_textContent;
    private TextView add_msg_textNum;
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

        //底部图片选择
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
                //获取图片路径
                //Toast.makeText(UserAddMsgActivity.this, photos.get(0),Toast.LENGTH_SHORT).show();
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

    /**
     * 点击发送按钮返回result值，由homeFragment处理
     */
    //    public void onClick(View v) {
//        if (v.getId() == R.id.tv_moment_add_choice_photo) {
//            choicePhotoWrapper();
//        } else if (v.getId() == R.id.tv_moment_add_publish) {
//            String content = mContentEt.getText().toString().trim();
//            if (content.length() == 0 && mPhotosSnpl.getItemCount() == 0) {
//                Toast.makeText(this, "必须填写这一刻的想法或选择照片！", Toast.LENGTH_SHORT).show();
//                return;
//            }
//
//            Intent intent = new Intent();
//            intent.putExtra(EXTRA_MOMENT, new Moment(mContentEt.getText().toString().trim(), mPhotosSnpl.getData()));
//            setResult(RESULT_OK, intent);
//            finish();
//        }
//    }
    //发送数据
    public void sendUserMessage() {
//        if (add_msg_textContent.getText().toString().equals("")) {
//            Toast.makeText(getApplicationContext(), "动态不能为空！", Toast.LENGTH_SHORT).show();
//        } else {
//            final ProgressDialog progressDialog = new ProgressDialog(UserAddMsgActivity.this);
//            progressDialog.setMessage("发帖中...");
//            progressDialog.setCancelable(true);
//            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            progressDialog.show();
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    // upLoad_with_image();
//                    upload_with_text();
//                    progressDialog.dismiss();//发帖完成消除progressDialog
//                }
//            }).start();
//            Intent intent = new Intent(UserAddMsgActivity.this, MainActivity.class);
//            startActivity(intent);


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

//    private void upload_with_text() {
//        RequestBody requestBody = new FormBody.Builder()
//                .add("msg_id", "123456")
//                .add("msg_content", add_msg_textContent.getText().toString())
//
//                .build();
//        HttpUtil.sendHttpRequest(ConstantUtil.SERVICE_PATH + "get_msg_text.php", requestBody, new Callback() {
//            public void onFailure(Call call, IOException e) {
//
//            }
//
//            public void onResponse(Call call, Response response) throws IOException {
//                Log.i("aa", response.body().string());
//            }
//        });
//
//    }

//    private void upLoad_with_image() {
//        final String url = ConstantUtil.SERVICE_PATH + "upload.php";
//        MultipartBody.Builder mbody = new MultipartBody.Builder().setType(MultipartBody.FORM);
  //      for (int i = 0; i < photos.size(); i++) {
//            File file = new File(photos.get(i));//得到选择的图片
//            String str = BitmapUtils.compressImageUpload(file.getPath());//得到压缩过的文件路径
//            File compress_file = new File(str);//得到新文件
//            mbody.addFormDataPart("image" + i, compress_file.getName(), RequestBody.create(MediaType.parse("application/octet-stream"), compress_file));//添加到mbody中
//        }
//        RequestBody requestBody = mbody.build();
//        Request request = new Request.Builder()
//                .url(url)
//                .post(requestBody)
//                .build();
//        final okhttp3.OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();
//        OkHttpClient okHttpClient = httpBuilder
//                //设置超时
//                .connectTimeout(3000, TimeUnit.MINUTES)
//                .readTimeout(3000, TimeUnit.MINUTES)
//                .writeTimeout(3000, TimeUnit.MINUTES)
//                .build();
//        okHttpClient.newCall(request).enqueue(new Callback() {
//            public void onFailure(Call call, IOException e) {
//                Log.e("aa", "uploadMultiFile() e=" + e);
//            }
//
//            public void onResponse(Call call, Response response) throws IOException {
//                Log.i("bb", "uploadMultiFile() response=" + response.body().string());
//                BitmapUtils.deleteCacheFile();//清除缓存
//            }
//        });
//
//
//    }


