package com.xiaoyu.schoolelive.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2017/7/12.
 */

public class HomeContentAdapter extends PagerAdapter {
    private List<View> views;
    public HomeContentAdapter(List<View> views) {
        this.views = views;
    }

    //返回要滑动的View的个数
    public int getCount() {
        return views.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    //做了2件事，第一，将当前试图添加到container中，dier，返回当前View
    public Object instantiateItem(ViewGroup container, int position) {
        View view = views.get(position);
        container.addView(view);
        return view;
    }

    //从当前container中删除指定位置position的View
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(views.get(position));
    }
}

