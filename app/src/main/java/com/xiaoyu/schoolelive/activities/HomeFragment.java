package com.xiaoyu.schoolelive.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.xiaoyu.schoolelive.R;
import com.xiaoyu.schoolelive.adapter.PublishAdapter;
import com.xiaoyu.schoolelive.data.Publish;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/13.
 */
public class HomeFragment extends Fragment {
    private PublishAdapter adapterPublish;
    private List<Publish> date;
    private View view;
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_main_menu_home,container,false);
        //设置"最火"分界面
        setHomeHotAll();

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
    public void setHomeHotAll(){
        ListView pub_list = (ListView)view.findViewById(R.id.pub_list);
        //获取从Activity传递的数据源
        //Bundle bundle = this.getArguments();
        Publish publish = new Publish();
        date = new ArrayList<Publish>();
        adapterPublish = new PublishAdapter(getContext(),date);

//        publish.setHead(R.drawable.dw_1);
//        publish.setName(bundle.getString("tmp_name"));
//        publish.setContent(bundle.getString("tmp_content"));
//        publish.setYmd(bundle.getString("tmp_ymd"));
//        publish.setDate(bundle.getString("tmp_date"));

        for (int i =0 ;i < 3;i++){
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

        //listView点击逻辑
        pub_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Publish publish = date.get(position);
                Intent intent = new Intent(getContext(),UserPubMsgDetailActivity.class);
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
}

