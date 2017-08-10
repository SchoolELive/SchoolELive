package com.xiaoyu.schoolelive.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TabHost;

import com.xiaoyu.schoolelive.R;
import com.xiaoyu.schoolelive.adapter.PublishAdapter;
import com.xiaoyu.schoolelive.data.Image;
import com.xiaoyu.schoolelive.data.Publish;
import com.xiaoyu.schoolelive.util.ConstantUtil;
import com.xiaoyu.schoolelive.util.HttpUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class HomeFragment extends Fragment {

    private static PublishAdapter adapterPublish;
    private List<Publish> date;
    private int WIDTH=250;
    private int HEIGHT=250;
    private View view;
    private ListView pub_list;
    private List<List<Image>> imagesList;
    private SwipeRefreshLayout swipeRefresh;
    public static boolean isDis = true;
    private TabHost tabHost;

    private String[][] images = new String[][]{
              {"file:///android_asset/dw_1.jpg", "500", "609"}
            , {"file:///android_asset/dw_2.jpg", WIDTH+"", HEIGHT+""}
            , {"file:///android_asset/dw_3.jpg", WIDTH+"", HEIGHT+""}
            , {"file:///android_asset/dw_4.jpg", WIDTH+"", HEIGHT+""}
            , {"file:///android_asset/dw_5.jpg", WIDTH+"", HEIGHT+""}
            , {"file:///android_asset/dw_6.jpg", WIDTH+"", HEIGHT+""}
            , {"file:///android_asset/dw_7.jpg", WIDTH+"", HEIGHT+""}
            , {"file:///android_asset/dw_8.jpg", WIDTH+"", HEIGHT+""}
            , {"file:///android_asset/dw_9.jpg", WIDTH+"", HEIGHT+""}
    };

    Handler handler = new Handler() {//利用handler进行页面更新

        public void handleMessage(Message msg) {
            String jsonData = null;
            adapterPublish = new PublishAdapter(getContext(), date, imagesList);
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

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_main_menu_home, container, false);

        //设置"最火"分界面
        setHomeHotAll();
        //设置首页标题栏
        setHomeTab();

        date = new ArrayList<Publish>();


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
                //intent.putExtra("tmp_head", publish.getHead());
                startActivity(intent);
            }
        });

        return view;
    }

    //停止事务委托
    @Override
    public void onPause() {
        super.onPause();
    }
    //开启事务委托
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
        date = new ArrayList<>();
        imagesList = new ArrayList<>();
        adapterPublish = new PublishAdapter(getContext(), date, imagesList);

//        publish.setHead(R.drawable.dw_1);
//        publish.setName(bundle.getString("tmp_name"));
//        publish.setContent(bundle.getString("tmp_content"));
//        publish.setYmd(bundle.getString("tmp_ymd"));
//        publish.setDate(bundle.getString("tmp_date"));

        for (int i = 0; i < 9; i++) {
            ArrayList<Image> itemList = new ArrayList<>();
            //publish.setHead(R.drawable.dw_1);
            publish.setName("tmp_name");
            publish.setContent("tmp_content");
            publish.setYmd("tmp_ymd");
            publish.setDate("tmp_date");
            for (int j = 0; j <= i; j++) {

                itemList.add(new Image(images[j][0], Integer.parseInt(images[j][1]), Integer.parseInt(images[j][2])));
            }
            //在listView中添加数据
            adapterPublish.addPublish(publish);
            adapterPublish.addImages(itemList);
        }

        //绑定适配器
        pub_list.setAdapter(adapterPublish);

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
                // intent.putExtra("tmp_head", publish.getHead());
                Bundle bundle = new Bundle();
//                bundle.putInt("tmp_like_count", publish.getLike_count());
//                bundle.putInt("tmp_comment_count", publish.getComment_count());
//                bundle.putInt("tmp_share_count", publish.getShare_count());
//                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}

