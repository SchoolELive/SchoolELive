package com.xiaoyu.schoolelive.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.xiaoyu.schoolelive.R;

/**
 * Created by Administrator on 2017/7/23.
 */

public class HomeHotFragment extends Fragment{
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_home_hot,container,false);
    }
}
