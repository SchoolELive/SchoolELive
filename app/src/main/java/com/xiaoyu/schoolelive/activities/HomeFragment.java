package com.xiaoyu.schoolelive.activities;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TabHost;
import android.widget.Toast;

import com.xiaoyu.schoolelive.R;
import com.xiaoyu.schoolelive.adapter.PublishAdapter;
import com.xiaoyu.schoolelive.data.Publish;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemLongClickListener;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPreviewActivity;
import cn.bingoogolapple.photopicker.imageloader.BGARVOnScrollListener;
import cn.bingoogolapple.photopicker.widget.BGANinePhotoLayout;
import cn.bingoogolapple.photopicker.widget.BGANinePhotoLayout.Delegate;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;


public class HomeFragment extends Fragment implements EasyPermissions.PermissionCallbacks, Delegate, BGAOnRVItemClickListener, BGAOnRVItemLongClickListener {
    public static final int REQUEST_CODE_PERMISSION_PHOTO_PREVIEW = 1;
    private PublishAdapter mPublishAdapter;
    private RecyclerView mPublishRv;
    private BGANinePhotoLayout mCurrentClickNpl;
    public static CheckBox mDownLoadableCb;
    private List<Publish> publishes;
    public static boolean isDis = true;
    private View view;
    private TabHost tabHost;
    private SwipeRefreshLayout swipeRefresh;

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

    public void initRV() {
        mPublishRv = (RecyclerView) view.findViewById(R.id.rv_moment_list_moments);
        mDownLoadableCb = (CheckBox) view.findViewById(R.id.cb_moment_list_downloadable);
        //查看大图是否显示下载图标
        mDownLoadableCb.setChecked(true);
        mPublishAdapter = new PublishAdapter(getContext(), mPublishRv);
        mPublishAdapter.setOnRVItemClickListener(this);
        mPublishAdapter.setOnRVItemLongClickListener(this);
        mPublishAdapter.setDelegate(HomeFragment.this);
        mPublishRv.addOnScrollListener(new BGARVOnScrollListener(getActivity()));
        mPublishRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        mPublishRv.setAdapter(mPublishAdapter);
        addNetImageTestData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_main_menu_home, container, false);
        //初始化"最火"分界面及对应adapter
        initRV();
        //设置首页标题栏
        setHomeTab();

        /*
        * 刷新按钮
        * */
        swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
//        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            public void onRefresh() {
//
//                swipeRefresh.setRefreshing(false);
//            }
//        });

        return view;
    }

    public void setHomeTab() {
        //找到TabHost的标签集合
        tabHost = (TabHost) view.findViewById(R.id.home_tabhost);
        /*如果没有继承TabActivity时，通过下面这种方法加载启动tabHost.这一句在源代码中,
        会根据findviewbyId()找到对应的TabWidget,还需要根据findViewById()找到
        这个TabWidget下面对应的标签页的内容.也就是FrameLayout这个显示控件.*/
        tabHost.setup();

        //TabSpec这个是标签页对象.
        TabHost.TabSpec tabSpec = tabHost.newTabSpec("page1");//新建一个标签页对象.
        tabSpec.setIndicator(("最火"));//设置这个标签页的标题和图片
        tabSpec.setContent(R.id.page1);//指定标签页的内容页.
        tabHost.addTab(tabSpec);//把这个标签页,添加到标签对象tabHost中.

        tabSpec = tabHost.newTabSpec("page2");
        tabSpec.setIndicator("最新");
        tabSpec.setContent(R.id.page2);
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("page2");
        tabSpec.setIndicator("八卦");
        tabSpec.setContent(R.id.page3);
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("page2");
        tabSpec.setIndicator("瞎逼逼");
        tabSpec.setContent(R.id.page4);
        tabHost.addTab(tabSpec);

    }


//    Handler handler = new Handler() {//利用handler进行页面更新
//
//        public void handleMessage(Message msg) {
//            String jsonData = null;
//            adapterPublish = new PublishAdapter(getContext(), date, imagesList);
//            switch (msg.what) {
//                case 1:
////                    publish.setHead(getHead());
//                    jsonData = (String) msg.obj;//得到返回的数据
//                    try {
//                        JSONArray jsonArray = new JSONArray(jsonData);
//                        for (int i = 0; i < jsonArray.length(); i++) {
//                            JSONObject jsonObject = jsonArray.getJSONObject(i);
//                            Publish pub = new Publish();
//                            pub.setName(jsonObject.getString("msg_title"));
//                            pub.setContent(jsonObject.getString("msg_content"));
//                            pub.setYmd(jsonObject.getString("post_time").substring(5, 10));
//                            pub.setDate("  " + jsonObject.getString("post_time").substring(11, 16));
//                            date.add(pub);
//                        }
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    break;
//                case 2://下拉刷新的时候调用
////                    publish.setHead(getHead());
//                    jsonData = (String) msg.obj;//得到返回的数据
//                    try {
//                        JSONArray jsonArray = new JSONArray(jsonData);
//                        for (int i = 0; i < jsonArray.length(); i++) {//得到数据并添加到pub中
//                            JSONObject jsonObject = jsonArray.getJSONObject(i);
//                            Publish pub = new Publish();
//                            pub.setName(jsonObject.getString("msg_title"));
//                            pub.setContent(jsonObject.getString("msg_content"));
//                            pub.setYmd(jsonObject.getString("post_time").substring(5, 10));
//                            pub.setDate("  " + jsonObject.getString("post_time").substring(11, 16));
//                            adapterPublish.addPublish(pub);
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    break;
//
//            }
//            pub_list.setAdapter(adapterPublish);
//
//        }
//    };

    /**
     * 处理发送页面传过来的数据
     */
//    public void onClick(View v) {
//        if (v.getId() == R.id.tv_moment_list_add) {
//            startActivityForResult(new Intent(this, MomentAddActivity.class), REQUEST_CODE_ADD_MOMENT);
//        }
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_ADD_MOMENT) {
//            mPublishAdapter.addFirstItem(MomentAddActivity.getMoment(data));
//            mPublishRv.smoothScrollToPosition(0);
//        }
//    }

    /**
     * 添加网络图片测试数据
     */
    private void addNetImageTestData() {
        publishes = new ArrayList<>();
        //"http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered1.png"
        publishes.add(new Publish("NickChaw", "2017-08-08", "11:59", "1张网络图片", new ArrayList<>(Arrays.asList("file:///android_asset/dw_1.jpg"))));
        publishes.add(new Publish("NickChaw", "2017-08-08", "11:59", "2张网络图片", new ArrayList<>(Arrays.asList("http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered2.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered3.png"))));
        publishes.add(new Publish("NickChaw", "2017-08-08", "11:59", "9张网络图片", new ArrayList<>(Arrays.asList("http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered11.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered12.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered13.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered14.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered15.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered16.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered17.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered18.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered19.png"))));
        publishes.add(new Publish("NickChaw", "2017-08-08", "11:59", "5张网络图片", new ArrayList<>(Arrays.asList("http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered11.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered12.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered13.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered14.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered15.png"))));
        publishes.add(new Publish("NickChaw", "2017-08-08", "11:59", "3张网络图片", new ArrayList<>(Arrays.asList("http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered4.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered5.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered6.png"))));
        publishes.add(new Publish("NickChaw", "2017-08-08", "11:59", "8张网络图片", new ArrayList<>(Arrays.asList("http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered11.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered12.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered13.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered14.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered15.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered16.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered17.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered18.png"))));
        publishes.add(new Publish("NickChaw", "2017-08-08", "11:59", "4张网络图片", new ArrayList<>(Arrays.asList("http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered7.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered8.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered9.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered10.png"))));
        publishes.add(new Publish("NickChaw", "2017-08-08", "11:59", "2张网络图片", new ArrayList<>(Arrays.asList("http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered2.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered3.png"))));
        publishes.add(new Publish("NickChaw", "2017-08-08", "11:59", "3张网络图片", new ArrayList<>(Arrays.asList("http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered4.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered5.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered6.png"))));
        publishes.add(new Publish("NickChaw", "2017-08-08", "11:59", "4张网络图片", new ArrayList<>(Arrays.asList("http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered7.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered8.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered9.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered10.png"))));
        publishes.add(new Publish("NickChaw", "2017-08-08", "11:59", "9张网络图片", new ArrayList<>(Arrays.asList("http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered11.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered12.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered13.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered14.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered15.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered16.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered17.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered18.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered19.png"))));
        publishes.add(new Publish("NickChaw", "2017-08-08", "11:59", "1张网络图片", new ArrayList<>(Arrays.asList("http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered1.png"))));
        publishes.add(new Publish("NickChaw", "2017-08-08", "11:59", "5张网络图片", new ArrayList<>(Arrays.asList("http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered11.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered12.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered13.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered14.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered15.png"))));
        publishes.add(new Publish("NickChaw", "2017-08-08", "11:59", "6张网络图片", new ArrayList<>(Arrays.asList("http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered11.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered12.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered13.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered14.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered15.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered16.png"))));
        publishes.add(new Publish("NickChaw", "2017-08-08", "11:59", "7张网络图片", new ArrayList<>(Arrays.asList("http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered11.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered12.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered13.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered14.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered15.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered16.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered17.png"))));
        publishes.add(new Publish("NickChaw", "2017-08-08", "11:59", "8张网络图片", new ArrayList<>(Arrays.asList("http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered11.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered12.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered13.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered14.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered15.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered16.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered17.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered18.png"))));

        mPublishAdapter.setData(publishes);
    }

    /**
     * 图片预览，兼容6.0动态权限
     */
    @AfterPermissionGranted(REQUEST_CODE_PERMISSION_PHOTO_PREVIEW)
    private void photoPreviewWrapper() {
        if (mCurrentClickNpl == null) {
            return;
        }

        // 保存图片的目录，改成你自己要保存图片的目录。如果不传递该参数的话就不会显示右上角的保存按钮
        File downloadDir = new File(Environment.getExternalStorageDirectory(), "BGAPhotoPickerDownload");

        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(getActivity(), perms)) {
            if (mCurrentClickNpl.getItemCount() == 1) {
                // 预览单张图片

                startActivity(BGAPhotoPreviewActivity.newIntent(getActivity(), mDownLoadableCb.isChecked() ? downloadDir : null, mCurrentClickNpl.getCurrentClickItem()));
            } else if (mCurrentClickNpl.getItemCount() > 1) {
                // 预览多张图片

                startActivity(BGAPhotoPreviewActivity.newIntent(getActivity(), mDownLoadableCb.isChecked() ? downloadDir : null, mCurrentClickNpl.getData(), mCurrentClickNpl.getCurrentClickItemPosition()));
            }
        } else {
            EasyPermissions.requestPermissions(this, "图片预览需要以下权限:\n\n1.访问设备上的照片", REQUEST_CODE_PERMISSION_PHOTO_PREVIEW, perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onRVItemClick(ViewGroup parent, View itemView, int position) {
        Publish publish = publishes.get(position);
        Intent intent = new Intent(getContext(), UserPubMsgDetailActivity.class);
        intent.putExtra("tmp_name", publish.getName());
        intent.putExtra("tmp_ymd", publish.getYmd());
        intent.putExtra("tmp_date", publish.getDate());
        intent.putExtra("tmp_content", publish.getContent());
        intent.putExtra("tmp_photos", publish.getPhotos());
        intent.putExtra("tmp_isFocus", publish.IS_FOCUS());
        intent.putExtra("tmp_isAgainst", publish.IS_AGAINST());
        intent.putExtra("tmp_allLikeCount", publish.getLike_count());
        intent.putExtra("tmp_allCommentCount", publish.getComment_count());
        intent.putExtra("tmp_allShareCount", publish.getShare_count());
        intent.putExtra("tmp_isLike", publish.isPUB_LIKE_FLAG());
        startActivity(intent);
        //Toast.makeText(getContext(), "点击了item " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onRVItemLongClick(ViewGroup parent, View itemView, int position) {
        Toast.makeText(getContext(), "长按了item " + position, Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public void onClickNinePhotoItem(BGANinePhotoLayout ninePhotoLayout, View view, int position, String model, List<String> models) {
        mCurrentClickNpl = ninePhotoLayout;
        photoPreviewWrapper();
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (requestCode == REQUEST_CODE_PERMISSION_PHOTO_PREVIEW) {
            Toast.makeText(getContext(), "您拒绝了「图片预览」所需要的相关权限!", Toast.LENGTH_SHORT).show();
        }
    }
}

