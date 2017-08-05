package com.xiaoyu.schoolelive.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayout;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;

import com.xiaoyu.schoolelive.R;
import com.xiaoyu.schoolelive.adapter.PublishAdapter;
import com.xiaoyu.schoolelive.data.Publish;
import com.xiaoyu.schoolelive.util.BitmapSampleUtil;
import com.xiaoyu.schoolelive.util.ConstantUtil;
import com.xiaoyu.schoolelive.util.HttpUtil;
import com.xiaoyu.schoolelive.util.PixelUtils;
import com.xiaoyu.schoolelive.util.SquareImageViewUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class HomeFragment extends Fragment {

    public static boolean isDis = true;
    private static PublishAdapter adapterPublish;
    private List<Publish> date;
    private View view,mainView;
    private ListView pub_list;
    private SwipeRefreshLayout swipeRefresh;
    private GridLayout gridlayoutPost;
    private TabHost tabHost;

    Handler handler = new Handler() {//利用handler进行页面更新

        public void handleMessage(Message msg) {
            String jsonData = null;
            adapterPublish = new PublishAdapter(getContext(), date);
            switch (msg.what) {
                case 1:
//                    publish.setHead(getHead());
                    jsonData = (String) msg.obj;//得到返回的数据
                    try {
                        JSONArray jsonArray = new JSONArray(jsonData);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Publish pub = new Publish();
                            pub.setName(jsonObject.getString("msg_title"));
                            pub.setContent(jsonObject.getString("msg_content"));
                            pub.setYmd(jsonObject.getString("post_time").substring(5, 10));
                            pub.setDate("  " + jsonObject.getString("post_time").substring(11, 16));
                            date.add(pub);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 2://下拉刷新的时候调用
//                    publish.setHead(getHead());
                    jsonData = (String) msg.obj;//得到返回的数据
                    try {
                        JSONArray jsonArray = new JSONArray(jsonData);
                        for (int i = 0; i < jsonArray.length(); i++) {//得到数据并添加到pub中
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Publish pub = new Publish();
                            pub.setName(jsonObject.getString("msg_title"));
                            pub.setContent(jsonObject.getString("msg_content"));
                            pub.setYmd(jsonObject.getString("post_time").substring(5, 10));
                            pub.setDate("  " + jsonObject.getString("post_time").substring(11, 16));
                            adapterPublish.addPublish(pub);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

            }
            pub_list.setAdapter(adapterPublish);
        }
    };
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        gridlayoutPost = (GridLayout) view.findViewById(R.id.user_img_gridlayout);
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_main_menu_home, container, false);
        mainView = inflater.inflate(R.layout.activity_main,container,false);

        //找到TabHost的标签集合
        tabHost = (TabHost)view.findViewById(R.id.home_tabhost);
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

        //设置"最火"分界面
        setHomeHotAll();
        date = new ArrayList<>();

        pub_list = (ListView) view.findViewById(R.id.pub_list);

        HttpUtil.sendHttpRequest(ConstantUtil.SERVICE_PATH + "hot_msg.php", new Callback() {
            public void onFailure(Call call, IOException e) {
            }

            public void onResponse(Call call, Response response) throws IOException {
                Message msg = new Message();
                msg.what = 1;
                msg.obj = response.body().string();
                handler.sendMessage(msg);
            }
        });
        /*
        * 刷新按钮
        * */
        swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            public void onRefresh() {
                HttpUtil.sendHttpRequest(ConstantUtil.SERVICE_PATH + "common_msg.php", new Callback() {
                    public void onFailure(Call call, IOException e) {
                    }
                    public void onResponse(Call call, Response response) throws IOException {
                        Message msg = new Message();
                        msg.what = 2;
                        msg.obj = response.body().string();
                        handler.sendMessage(msg);
                    }
                });
                swipeRefresh.setRefreshing(false);
            }
        });
        pub_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Publish publish = date.get(position);
                Intent intent = new Intent(getContext(), UserPubMsgDetailActivity.class);
                intent.putExtra("tmp_name", publish.getName());
                intent.putExtra("tmp_ymd", publish.getYmd());
                intent.putExtra("tmp_date", publish.getDate());
                intent.putExtra("tmp_content", publish.getContent());
                intent.putExtra("tmp_head", publish.getHead());
                startActivity(intent);
            }
        });

        return view;
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

    //设置"最火"分界面
    public void setHomeHotAll() {
        ListView pub_list = (ListView) view.findViewById(R.id.pub_list);
        //获取从Activity传递的数据源
        //Bundle bundle = this.getArguments();
        Publish publish = new Publish();
        date = new ArrayList<Publish>();
        adapterPublish = new PublishAdapter(getContext(), date);

//        publish.setHead(R.drawable.dw_1);
//        publish.setName(bundle.getString("tmp_name"));
//        publish.setContent(bundle.getString("tmp_content"));
//        publish.setYmd(bundle.getString("tmp_ymd"));
//        publish.setDate(bundle.getString("tmp_date"));

        for (int i = 0; i < 3; i++) {
            publish.setHead(R.drawable.dw_1);
            publish.setName("tmp_name");
            publish.setContent("tmp_content");
            publish.setYmd("tmp_ymd");
            publish.setDate("tmp_date");
            //在listView中添加数据
            adapterPublish.addPublish(publish);
        }
        //绑定适配器
        pub_list.setAdapter(adapterPublish);
        //updateViewGroup();
        //listView点击逻辑
        pub_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Publish publish = date.get(position);
                Intent intent = new Intent(getContext(), UserPubMsgDetailActivity.class);
                intent.putExtra("tmp_name", publish.getName());
                intent.putExtra("tmp_ymd", publish.getYmd());
                intent.putExtra("tmp_date", publish.getDate());
                intent.putExtra("tmp_content", publish.getContent());
                intent.putExtra("tmp_head", publish.getHead());
                Bundle bundle = new Bundle();
//                bundle.putInt("tmp_like_count", publish.getLike_count());
//                bundle.putInt("tmp_comment_count", publish.getComment_count());
//                bundle.putInt("tmp_share_count", publish.getShare_count());
//                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    /**
     * 动态添加控件
     * 图片集合
     *
     */
    public void updateViewGroup() {

        gridlayoutPost.removeAllViews();//清空子视图 防止原有的子视图影响
        int columnCount = gridlayoutPost.getColumnCount();//得到列数
        int marginSize = PixelUtils.dp2px(getContext(), 4);//得到经过dp转化的margin值
        //遍历集合 动态添加
        for (int i = 0, size = 3; i < size; i++) {
            GridLayout.Spec rowSpec = GridLayout.spec(i / columnCount);//行数
            GridLayout.Spec columnSpec = GridLayout.spec(i % columnCount, 1.0f);//列数 列宽的比例 weight=1
            ImageView imageView = new SquareImageViewUtil(getContext());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

            String imgsrc = BitmapSampleUtil.getBmpUrl();
            Uri u = Uri.parse(imgsrc);
            imageView.setImageURI(u);

            //由于宽（即列）已经定义权重比例 宽设置为0 保证均分
            GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(new ViewGroup.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT));
            layoutParams.rowSpec = rowSpec;
            layoutParams.columnSpec = columnSpec;

            layoutParams.setMargins(marginSize, marginSize, marginSize, marginSize);

            gridlayoutPost.addView(imageView, layoutParams);
        }
    }
}

