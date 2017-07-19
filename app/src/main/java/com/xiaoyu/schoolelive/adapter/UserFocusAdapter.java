package com.xiaoyu.schoolelive.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.xiaoyu.schoolelive.custom.CircleImageView;
import com.xiaoyu.schoolelive.data.UserFocusData;
import java.util.List;

import com.xiaoyu.schoolelive.R;

/**
 * Created by Administrator on 2017/7/16.
 */

public class UserFocusAdapter extends BaseAdapter {
    Context context;
    List<UserFocusData> data;

    public UserFocusAdapter(Context c, List<UserFocusData> data) {
        this.context = c;
        this.data = data;
    }
    public int getCount() {
        return data.size();
    }
    @Override
    public Object getItem(int position) {
        return data.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.custom_user_focus,null);
            //初始化
            holder.user_focus_image = (ImageView) convertView.findViewById(R.id.user_focus_image);
            holder.user_focus_fname = (TextView) convertView.findViewById(R.id.user_focus_fname);
            holder.user_focus_sex = (ImageView)convertView.findViewById(R.id.user_focus_sex);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        holder.user_focus_image.setImageResource(data.get(position).getUser_focus_image());
        holder.user_focus_fname.setText(data.get(position).getUser_focus_fname());
        holder.user_focus_sex.setImageResource(data.get(position).getUser_focus_sex());
        return convertView;
    }
    public static class ViewHolder{
        ImageView user_focus_image;
        TextView user_focus_fname;
        ImageView user_focus_sex;

    }
    public void addFocus(UserFocusData userfocusdata){
        data.add(userfocusdata);
        notifyDataSetChanged();
    }
}
