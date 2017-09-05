package com.xiaoyu.schoolelive.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.xiaoyu.schoolelive.R;
import com.xiaoyu.schoolelive.adapter.PartJobAdapter;
import com.xiaoyu.schoolelive.data.PartJob;
import com.xiaoyu.schoolelive.util.Common_msg_cache;
import com.xiaoyu.schoolelive.util.ConstantUtil;
import com.xiaoyu.schoolelive.util.HttpUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/7/13.
 */

public class PartJobFragment extends Fragment {
    RecyclerView mRecyclerView;

    private  ArrayList<PartJob> mData;
    private PartJobAdapter mAdapter;
    private View view;
    public Handler handler = new Handler(){
        /*for (int i = 0; i < 10; i++) {
            partJob.setWorkType(ConstantUtil.PartJob_YANYUAN);
            partJob.setWagesType(ConstantUtil.WagesType_PERDAY);
            partJob.setWagesPay(ConstantUtil.WagesPay_DAY);
            partJob.setWorkName("脱口秀节目现场充场观众");
            partJob.setWorkWages("70");
            partJob.setWorkPlace("人民广场");
            partJob.setWorkStartDate("09.14");
            partJob.setWorkEndDate("09.24");
            mData.add(partJob);
        }*/
        public void handleMessage(Message msg) {
            String str = (String)msg.obj;
            switch (msg.what){
                case 1:
                    try{
                        JSONArray jsonArray = new JSONArray(str);
                        for (int i = 0; i < jsonArray.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            PartJob partJob = new PartJob();
                            String job_id = jsonObject.getString("job_id");
                            String work_name = jsonObject.getString("work_name");
                            int work_type = jsonObject.getInt("work_type");
                            int wage_type = jsonObject.getInt("wage_type");
                            int wage_pay = jsonObject.getInt("wage_pay");
                            String work_wage = jsonObject.getString("work_wage");
                            String work_place = jsonObject.getString("work_place");
                            String start_date = jsonObject.getString("start_date");
                            String end_date = jsonObject.getString("end_date");
                            String start_hours = jsonObject.getString("start_hours");
                            String end_hours = jsonObject.getString("end_hours");
                            String work_need = jsonObject.getString("work_need");
                            String work_content_man = jsonObject.getString("work_content_man");
                            String work_content_num = jsonObject.getString("work_content_num");
                            String post_time = jsonObject.getString("post_time");
                            partJob.setJob_id(job_id);
                            partJob.setWorkName(work_name);
                            partJob.setWagesType(wage_type);
                            partJob.setWorkType(work_type);
                            partJob.setWagesPay(wage_pay);
                            partJob.setWorkWages(work_wage);
                            partJob.setWorkPlace(work_place);
                            partJob.setWorkStartDate(start_date);
                            partJob.setWorkEndDate(end_date);
                            partJob.setWorkStartDate(start_hours);
                            partJob.setWorkEndDate(end_hours);
                            partJob.setWorkNeed(work_need);
                            partJob.setContactPerson(work_content_man);
                            partJob.setContactNum(work_content_num);
                            partJob.setPost_time(post_time);
                            mData.add(partJob);//添加兼职信息
                        }
                        Common_msg_cache.add_jobs_Cache(getContext(),mData);//将兼职信息放入缓存
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                    mAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = LayoutInflater.from(getActivity())
                .inflate(R.layout.activity_main_menu_partjob, container, false);
        initView(view, savedInstanceState);

        return view;
    }

    protected void initView(View view, Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
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


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        setItemListener();
        refresh_jobs_cache();
    }

    private void initData() {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.partjob_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        mData = new ArrayList<>();
        mAdapter = new PartJobAdapter(getActivity(), mData);
        mRecyclerView.setAdapter(mAdapter);
        if(Common_msg_cache.get_jobs_Cache(getContext())!=null){
            ArrayList<PartJob> partJobs = Common_msg_cache.get_jobs_Cache(getContext());
            mAdapter.getList().addAll(partJobs);
            //refresh_jobs_cache();//从缓存中读取之后更新缓存
            Toast.makeText(getContext(),"更新缓存成功",Toast.LENGTH_SHORT).show();
        }else {
            getPartJobData();
        }

    }


    private void getPartJobData() {

        HttpUtil.sendHttpRequest(ConstantUtil.SERVICE_PATH + "query_job_info.php", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
               Message msg = new Message();
                msg.what = 1;//查询兼职成功时返回1
                msg.obj = response.body().string();
                handler.sendMessage(msg);
            }
        });
        //mAdapter.getList().addAll(mData);
        //mAdapter.notifyDataSetChanged();
    }

    private void setItemListener() {
        mAdapter.setOnItemClickListener(new PartJobAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                PartJob partJob;
                partJob = mData.get(position);
                Intent intent = new Intent(getActivity(), PartJobInfoActivity.class);
                intent.putExtra("tmp_workType", partJob.getWorkType());
                intent.putExtra("tmp_wagesType", partJob.getWagesType());
                intent.putExtra("tmp_wagesPay", partJob.getWagesPay());
                intent.putExtra("tmp_workName", partJob.getWorkName());
                intent.putExtra("tmp_workWages", partJob.getWorkWages());
                intent.putExtra("tmp_workPlace", partJob.getWorkPlace());
                intent.putExtra("tmp_workStartDate", partJob.getWorkStartDate());
                intent.putExtra("tmp_workEndDate", partJob.getWorkEndDate());
                intent.putExtra("tmp_workNeed",partJob.getWorkNeed());
                intent.putExtra("tmp_work_content_man",partJob.getContactPerson());
                intent.putExtra("tmp_work_content_num",partJob.getContactNum());
                intent.putExtra("tmp_workStartHours", partJob.getWorkEndDate());
                intent.putExtra("tmp_workEndHours",partJob.getWorkNeed());
                intent.putExtra("tmp_post_time",partJob.getPost_time());
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }
    public void refresh_jobs_cache(){//每次加载之后更新缓存
        final ArrayList<PartJob> cache_jobs_refresh = new ArrayList<>();
        HttpUtil.sendHttpRequest(ConstantUtil.SERVICE_PATH + "query_job_info.php", new Callback() {
            public void onFailure(Call call, IOException e) {

            }
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    JSONArray jsonArray = new JSONArray(response.body().string());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        PartJob partJob = new PartJob();
                        String job_id = jsonObject.getString("job_id");
                        String work_name = jsonObject.getString("work_name");
                        int work_type = jsonObject.getInt("work_type");
                        int wage_type = jsonObject.getInt("wage_type");
                        int wage_pay = jsonObject.getInt("wage_pay");
                        String work_wage = jsonObject.getString("work_wage");
                        String work_place = jsonObject.getString("work_place");
                        String start_date = jsonObject.getString("start_date");
                        String end_date = jsonObject.getString("end_date");
                        String start_hours = jsonObject.getString("start_hours");
                        String end_hours = jsonObject.getString("end_hours");
                        String work_need = jsonObject.getString("work_need");
                        String work_content_man = jsonObject.getString("work_content_man");
                        String work_content_num = jsonObject.getString("work_content_num");
                        String post_time = jsonObject.getString("post_time");
                        partJob.setJob_id(job_id);
                        partJob.setWorkName(work_name);
                        partJob.setWagesType(wage_type);
                        partJob.setWorkType(work_type);
                        partJob.setWagesPay(wage_pay);
                        partJob.setWorkWages(work_wage);
                        partJob.setWorkPlace(work_place);
                        partJob.setWorkStartDate(start_date);
                        partJob.setWorkEndDate(end_date);
                        partJob.setWorkStartDate(start_hours);
                        partJob.setWorkEndDate(end_hours);
                        partJob.setWorkNeed(work_need);
                        partJob.setContactPerson(work_content_man);
                        partJob.setContactNum(work_content_num);
                        partJob.setPost_time(post_time);
                        cache_jobs_refresh.add(partJob);//添加兼职信息
                    }
                    Common_msg_cache.refresh_jobs_Caches(getContext(), cache_jobs_refresh);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}

