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
import android.widget.TextView;
import android.widget.Toast;

import com.xiaoyu.schoolelive.R;
import com.xiaoyu.schoolelive.adapter.PublishAdapter;
import com.xiaoyu.schoolelive.data.Publish;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Created by Administrator on 2017/7/13.
 */
public class HomeFragment extends Fragment {
    private PublishAdapter adapterPublish;
    private List<Publish> date;
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main_menu_home,container,false);
        ListView pub_list = (ListView)view.findViewById(R.id.pub_list);
        Bundle bundle = this.getArguments();
        Publish publish = new Publish();
        Publish pub = new Publish();
        if (bundle == null){
        }else{
            date = new ArrayList<Publish>();
            adapterPublish = new PublishAdapter(getContext(),date);

            publish.setHead(getHead());
            publish.setName(bundle.getString("tmp_name"));
            publish.setContent(bundle.getString("tmp_content"));
            publish.setYmd(bundle.getString("tmp_ymd"));
            publish.setDate(bundle.getString("tmp_date"));
            for (int i = 0; i < 5; i++){
                pub.setHead(getHead());
                pub.setName("asdasdsa");
                pub.setContent("adasdasdasdasdas");
                pub.setYmd("17-05-12");
                pub.setDate("16:34:45");
                adapterPublish.addPublish(pub);
            }
            adapterPublish.addPublish(publish);
            pub_list.setAdapter(adapterPublish);
        }
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


    public static int getHead() {
        int index = new Random().nextInt(IMAGES.length);
        return IMAGES[index];
    }
    //测试头像
    public static int[] IMAGES = new int[]{
            R.drawable.dw_1, R.drawable.dw_2, R.drawable.dw_3,
            R.drawable.dw_4, R.drawable.dw_5, R.drawable.dw_6,
            R.drawable.dw_7, R.drawable.dw_8, R.drawable.dw_9};
//    public void setListener() {
//        publish_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Publish publish = data.get(position);
//                Intent intent = new Intent(getApplication(), UserPubMsgDetailActivity.class);
//                intent.putExtra("tmp_name", publish.getName());
//                intent.putExtra("tmp_ymd", publish.getYmd());
//                intent.putExtra("tmp_date", publish.getDate());
//                intent.putExtra("tmp_content", publish.getContent());
//                intent.putExtra("tmp_head", publish.getHead());
////                Bundle bundle = new Bundle();
////                bundle.putInt("tmp_like_count", publish.getLike_count());
////                bundle.putInt("tmp_comment_count", publish.getComment_count());
////                bundle.putInt("tmp_share_count", publish.getShare_count());
////                intent.putExtras(bundle);
//                startActivity(intent);
//            }
//        });
//    }
}

