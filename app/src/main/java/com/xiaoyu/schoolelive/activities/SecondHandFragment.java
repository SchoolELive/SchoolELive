package com.xiaoyu.schoolelive.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.xiaoyu.schoolelive.R;
import com.xiaoyu.schoolelive.adapter.WaterFallAdapter;
import com.xiaoyu.schoolelive.data.ImageBean;
import com.xiaoyu.schoolelive.util.BitmapSampleUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/15.
 */

public class SecondHandFragment extends Fragment {

    @Bind(R.id.goods_recycler_view)
    RecyclerView mRecyclerView;

    private WaterFallAdapter mAdapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity())
                .inflate(R.layout.activity_main_menu_secondhand, container, false);
        initView(view, savedInstanceState);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
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


//    @Override
//    protected int getLayoutId() {
//        return R.layout.activity_main_menu_secondhand;
//    }


    //@Override
    protected void initView(View view, Bundle savedInstanceState) {
        ButterKnife.bind(this, view);

    }

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    //@Override
    protected void initData() {

        mRecyclerView.setLayoutManager(new
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));


        mAdapter = new WaterFallAdapter(getActivity());
        mRecyclerView.setAdapter(mAdapter);
        //getJsonData();
        getGoodsData();
        mAdapter.setOnItemClickListener(new WaterFallAdapter.OnItemClickListener() {
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), GoodsInfoActivity.class);
                startActivity(intent);
//                Toast.makeText(getContext(), position + " click",
//                        Toast.LENGTH_SHORT).show();

            }

            public void onItemLongClick(View view, int position) {
                Toast.makeText(getContext(), position + " long click",
                        Toast.LENGTH_SHORT).show();
                //mAdapter.removeData(position);
            }
        });
    }


    private void getGoodsData() {
        List<ImageBean> list = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            String imgsrc=BitmapSampleUtil.getBmpUrl();
            ImageBean bean = new ImageBean();
            bean.setImgsrc(imgsrc);
            list.add(bean);
        }
        mAdapter.getList().addAll(list);
        mAdapter.getRandomHeight(list);
        mAdapter.notifyDataSetChanged();
    }

    private void getJsonData() {

        //网易的接口(可以浏览器直接访问)
        String url = "http://c.3g.163.com/recommend/getChanListNews?" +
                "channel=T1456112189138&size=20&passport=&devId=1uuFYbybIU2oqSRGyFrjCw%3D%3D" +
                "&lat=%2F%2FOm%2B%2F8ScD%2B9fX1D8bxYWg%3D%3D&lon=LY2l8sFCNzaGzqWEPPgmUw%3D%3D" +
                "&version=9.0&net=wifi&ts=1464769308" +
                "&sign=bOVsnQQ6gJamli6%2BfINh6fC%2Fi9ydsM5XXPKOGRto5G948ErR02zJ6%2FKXOnxX046I" +
                "&encryption=1&canal=meizu_store2014_news" +
                "&mac=sSduRYcChdp%2BBL1a9Xa%2F9TC0ruPUyXM4Jwce4E9oM30%3D";

        //
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    List<ImageBean> list = new ArrayList<>();
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray array = jsonObject.optJSONArray("美女");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject item = array.optJSONObject(i);
                        String imgsrc = item.optString("imgsrc");
                        String title = item.optString("title");
                        ImageBean bean = new ImageBean();
                        bean.setImgsrc(imgsrc);
                        bean.setTitle(title);
                        list.add(bean);
                        Log.d("img", "imgsrc=" + imgsrc);
                    }
                    mAdapter.getList().addAll(list);
                    mAdapter.getRandomHeight(list);
                    mAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
            }
        });
        requestQueue.add(request);

    }

}


