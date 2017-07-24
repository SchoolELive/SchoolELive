package com.xiaoyu.schoolelive.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xiaoyu.schoolelive.R;

/**
 * Created by Administrator on 2017/7/23.
 */

public class HomeNewFragment extends Fragment {
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home_new,container,false);
        return view;
    }
}
