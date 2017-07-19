package com.xiaoyu.schoolelive.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiaoyu.schoolelive.R;
import com.xiaoyu.schoolelive.data.Comment;

import java.util.List;

/**
 * Created by NeekChaw on 2017-07-14.
 */

public class CommentAdapter extends BaseAdapter {
    Context context;
    List<Comment> data;


    public CommentAdapter(Context c, List<Comment> data) {
        this.context = c;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {

        final ViewHolder holder;
        // 重用convertView
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.custom_user_cmt_msg, null);
            //初始化ImageButton
            holder.btn_cmt_comment = (ImageButton) convertView.findViewById(R.id.cmt_comment_icon);
            holder.btn_cmt_like = (ImageButton) convertView.findViewById(R.id.cmt_like_icon);

            holder.comment_name = (TextView) convertView.findViewById(R.id.cmt_nickname);
            holder.comment_content = (TextView) convertView.findViewById(R.id.comment_msg);
            holder.comment_head = (ImageView) convertView.findViewById(R.id.cmt_user_head);
            holder.comment_ymd = (TextView) convertView.findViewById(R.id.cmt_ymd);
            holder.comment_date = (TextView) convertView.findViewById(R.id.cmt_date);
            holder.cmt_comment_count = (TextView) convertView.findViewById(R.id.cmt_comment_count);
            holder.cmt_like_count = (TextView) convertView.findViewById(R.id.cmt_like_count);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        // 适配数据
        holder.comment_name.setText(data.get(i).getName());
        holder.comment_content.setText(data.get(i).getContent());
        holder.comment_head.setImageResource(data.get(i).getHead());
        holder.comment_date.setText(data.get(i).getDate());
        holder.comment_ymd.setText(data.get(i).getYmd());

        holder.btn_cmt_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.btn_cmt_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.CMT_LIKE_FLAG) {
                    holder.cmt_like_count.setText(Integer.valueOf(holder.cmt_like_count.getText().toString()) + 1 + "");
                    holder.CMT_LIKE_FLAG = false;
                } else {
                    holder.cmt_like_count.setText(Integer.valueOf(holder.cmt_like_count.getText().toString()) + -1 + "");
                    holder.CMT_LIKE_FLAG = true;
                }
            }
        });
        //注：这样能使所有listview的item都共用同一个listener，
        // 而不用为每个item都设置各自的listener！！！
        holder.btn_cmt_like.setTag(i);
        holder.btn_cmt_comment.setTag(i);
        return convertView;
    }

    /**
     * 添加一条评论,刷新列表
     *
     * @param comment
     */
    public void addComment(Comment comment) {
        data.add(comment);
        notifyDataSetChanged();
    }

    /**
     * 静态类，便于GC回收
     */
    public static class ViewHolder {
        TextView comment_name;
        TextView comment_content;
        ImageView comment_head;
        TextView comment_date;
        TextView comment_ymd;
        TextView cmt_comment_count;
        TextView cmt_like_count;
        ImageButton btn_cmt_like;
        ImageButton btn_cmt_comment;
        boolean CMT_LIKE_FLAG = true;
    }
}
