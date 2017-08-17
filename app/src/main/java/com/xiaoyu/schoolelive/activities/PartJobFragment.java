package com.xiaoyu.schoolelive.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xiaoyu.schoolelive.R;
import com.xiaoyu.schoolelive.adapter.PartJobAdapter;
import com.xiaoyu.schoolelive.data.PartJob;
import com.xiaoyu.schoolelive.util.ConstantUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/13.
 */

public class PartJobFragment extends Fragment {
    RecyclerView mRecyclerView;

    private List<PartJob> mData;
    private PartJobAdapter mAdapter;
    private View view;

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
        getPartJobData();
    }


    private void getPartJobData() {
        PartJob partJob = new PartJob();
        for (int i = 0; i < 10; i++) {
            partJob.setWorkType(ConstantUtil.PartJob_YANYUAN);
            partJob.setWagesType(ConstantUtil.WagesType_PERDAY);
            partJob.setWagesPay(ConstantUtil.WagesPay_DAY);
            partJob.setWorkName("脱口秀节目现场充场观众");
            partJob.setWorkWages("70");
            partJob.setWorkPlace("人民广场");
            partJob.setWorkStartDate("09.14");
            partJob.setWorkEndDate("09.24");
            mData.add(partJob);
        }
        mAdapter.getList().addAll(mData);
        mAdapter.notifyDataSetChanged();
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
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }
}

