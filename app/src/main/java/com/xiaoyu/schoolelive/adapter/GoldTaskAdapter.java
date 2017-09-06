package com.xiaoyu.schoolelive.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.thinkcool.circletextimageview.CircleTextImageView;
import com.xiaoyu.schoolelive.R;
import com.xiaoyu.schoolelive.data.Task;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/8/24.
 */

public class GoldTaskAdapter extends RecyclerView.Adapter<GoldTaskAdapter.ViewHolder> {
    private Context mContext;
    private List<Task> mData;

    private SysInformAdapter.OnItemClickListener onItemClickListener;

    public GoldTaskAdapter(Context c, List<Task> data) {
        this.mContext = c;
        this.mData = data;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        CircleTextImageView goldTask_image;
        TextView goldTask_name;
        TextView goldTask_content;
        Button goldTask_get;

        public ViewHolder(View itemView) {
            super(itemView);
            goldTask_image = (CircleTextImageView) itemView.findViewById(R.id.goldTask_image);
            goldTask_name = (TextView) itemView.findViewById(R.id.goldTask_name);
            goldTask_content = (TextView) itemView.findViewById(R.id.goldTask_content);
            goldTask_get = (Button)itemView.findViewById(R.id.goldTask_get);

            ButterKnife.bind(this, itemView);
        }
    }

    public List<Task> getList() {
        return mData;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 实例化展示的View
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_goldtask, parent, false);
        // 实例化viewholder
        GoldTaskAdapter.ViewHolder viewHolder = new GoldTaskAdapter.ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final GoldTaskAdapter.ViewHolder holder, int position) {

        holder.goldTask_image.setImageResource(mData.get(position).getGoldTask_image());
        holder.goldTask_name.setText(mData.get(position).getGoldTask_name());
        holder.goldTask_content.setText(mData.get(position).getGoldTask_content());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (onItemClickListener != null) {
                    int pos = holder.getLayoutPosition();
                    onItemClickListener.onItemClick(holder.itemView, pos);
                }
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onItemClickListener != null) {
                    int pos = holder.getLayoutPosition();
                    onItemClickListener.onItemLongClick(holder.itemView, pos);
                } //表示此事件已经消费，不会触发单击事件
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    /**
     * 设置回调监听
     *
     * @param listener
     */
    public void setOnItemClickListener(SysInformAdapter.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }
}
