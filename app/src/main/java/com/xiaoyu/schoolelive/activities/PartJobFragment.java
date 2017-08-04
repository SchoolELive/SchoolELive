package com.xiaoyu.schoolelive.activities;

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
    //@Bind(R.id.partjob_recycler_view)
    RecyclerView mRecyclerView;

    private List<PartJob> mData;
    private PartJobAdapter mAdapter;
    private View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //initData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = LayoutInflater.from(getActivity())
                .inflate(R.layout.activity_main_menu_partjob, container, false);
        //mRecyclerView=(RecyclerView)view.findViewById(R.id.partjob_recycler_view);
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

        mAdapter.setOnItemClickListener(new PartJobAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }


    private void getPartJobData() {
        PartJob partJob = new PartJob();
        for (int i = 0; i < 10; i++) {
            partJob.setWorkType(ConstantUtil.PartJob_QITA);
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

}

