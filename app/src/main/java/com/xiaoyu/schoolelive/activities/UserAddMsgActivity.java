package com.xiaoyu.schoolelive.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xiaoyu.schoolelive.R;
import com.xiaoyu.schoolelive.adapter.PublishAdapter;
import com.xiaoyu.schoolelive.base.BaseSlideBack;
import com.xiaoyu.schoolelive.data.Publish;
import com.xiaoyu.schoolelive.util.BitmapUtils;
import com.xiaoyu.schoolelive.util.ConstantUtil;
import com.xiaoyu.schoolelive.util.ImageTool;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.mob.tools.utils.ResHelper.getFileSize;
import static java.lang.System.currentTimeMillis;
import static java.lang.System.in;

public class UserAddMsgActivity extends BaseSlideBack {

    private EditText add_msg_textContent;
    private TextView add_msg_textNum;
    private Publish publish;
    private Button postButton;
    private PublishAdapter adapterPublish;

    private static final int IMG_COUNT = 10;
    private static final String IMG_ADD_TAG = "a";
    private GridView add_msg_pic;
    private GVAdapter adapter;
    private List<String> list;

    public static long getFileSize(File file) throws Exception {
        long size = 0;
        if (file.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(file);
            size = fis.available();
        } else {
            file.createNewFile();
            Log.e("获取文件大小", "文件不存在!");
        }
        return size;
    }




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_add_msg);
        //标题栏
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        initView();
        initData();
        textNumEvent();
    }

    //初始化控件
    public void initView() {
        add_msg_textContent = (EditText) findViewById(R.id.add_msg_textContent);
        add_msg_textNum = (TextView) findViewById(R.id.add_msg_textNum);
        add_msg_pic = (GridView) findViewById(R.id.pic_gridview);
        postButton = (Button) findViewById(R.id.postButton);
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
//            // 生成数据
//            publish = new Publish();
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

         }

    }

    /**
     * 图片上传函数
     */
    private void upLoad() {

        final String url = ConstantUtil.SERVICE_PATH + "upload.php";
        MultipartBody.Builder mbody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        Bitmap bitmap = null;
        for (int i = 0; i < list.size() - 1; i++) {
            File file = new File(list.get(i));//得到选择的图片
            String str =   BitmapUtils.compressImageUpload(file.getPath());//得到压缩过的文件路径
            File compress_file = new File(str);//得到新文件
            mbody.addFormDataPart("image"+i,file.getName(),RequestBody.create(MediaType.parse("application/octet-stream"),compress_file));//添加到mbody中
        }
        RequestBody requestBody = mbody.build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        final okhttp3.OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();
        OkHttpClient okHttpClient  = httpBuilder
                //设置超时
                .connectTimeout(1000, TimeUnit.MINUTES)
                .readTimeout(1000, TimeUnit.MINUTES)
                .writeTimeout(1000, TimeUnit.MINUTES)
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

    /**
     * 图片选择，数据初始化
     */
    private void initData() {
        if (list == null) {
            list = new ArrayList<>();
            list.add(IMG_ADD_TAG);
        }
        adapter = new GVAdapter();
        add_msg_pic.setAdapter(adapter);
        add_msg_pic.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (list.get(position).equals(IMG_ADD_TAG)) {
                    if (list.size() < IMG_COUNT) {
                        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(i, 0);
                    } else
                        Toast.makeText(UserAddMsgActivity.this, "最多只能选择9张照片！", Toast.LENGTH_SHORT).show();
                }
            }
        });
        refreshAdapter();
    }

    /**
     * 刷新Adapter
     */
    private void refreshAdapter() {
        if (list == null) {
            list = new ArrayList<>();
        }
        if (adapter == null) {
            adapter = new GVAdapter();
        }
        adapter.notifyDataSetChanged();
    }

    /**
     * 适用于gridview的适配器
     */
    private class GVAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(UserAddMsgActivity.this).inflate(R.layout.activity_add_photo_gv_items, parent, false);
                holder = new ViewHolder();
                holder.imageView = (ImageView) convertView.findViewById(R.id.main_gridView_item_photo);
                holder.checkBox = (CheckBox) convertView.findViewById(R.id.main_gridView_item_cb);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            String s = list.get(position);
            if (!s.equals(IMG_ADD_TAG)) {
                holder.checkBox.setVisibility(View.VISIBLE);
                holder.imageView.setImageBitmap(ImageTool.createImageThumbnail(s));
            } else {
                holder.checkBox.setVisibility(View.GONE);
                if (list.size() < IMG_COUNT) {
                    holder.imageView.setImageResource(R.drawable.ic_photo_upload);
                } else {
                    holder.imageView.setVisibility(View.GONE);
                }
            }

            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    list.remove(position);
                    refreshAdapter();
                }
            });

            return convertView;
        }

        private class ViewHolder {
            ImageView imageView;
            CheckBox checkBox;
            Bitmap bmp;
            Uri uri;
        }

    }

    /**
     * 进入相册选择相片后，回调函数的处理
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null || data.getData() == null) {
            System.out.println("data null");
            return;
        }
        if (requestCode == 0) {

            final Uri uri = data.getData();
            String path = ImageTool.getImageAbsolutePath(UserAddMsgActivity.this, uri);
            //System.out.println(path);
            if (list.size() == IMG_COUNT) {
                removeItem();
                refreshAdapter();
                return;
            }
            removeItem();
            list.add(path);

            list.add(IMG_ADD_TAG);
            refreshAdapter();
        }
    }

    /**
     * 删除已选择图片
     */
    private void removeItem() {
        if (list.size() != IMG_COUNT) {
            if (list.size() != 0) {
                list.remove(list.size() - 1);
            }
        }
    }

}


