package com.xiaoyu.schoolelive.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xiaoyu.schoolelive.R;
import com.xiaoyu.schoolelive.activities.ShopActivity;
import com.xiaoyu.schoolelive.data.ShopData;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;

public class ShopAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    //item类型
    public static final int ITEM_TYPE_HEADER = 0;
    public static final int ITEM_TYPE_CONTENT = 1;
    public static final int ITEM_TYPE_BOTTOM = 2;
    //item的高度
    private List<Integer> mHeights;

    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private List<ShopData> mShopData;
    private int mHeaderCount = 0;//头部View个数
    private int mBottomCount = 0;//底部View个数

    private Date date_check = null, ymd_check = null;
    private SimpleDateFormat ymd_now = null, date_now = null;
    private Button checkin;
    private TextView myCheckGold, intoShop;
    private Button qiandao;

    public ShopAdapter(Context context, List<ShopData> datas) {
        this.mContext = context;
        this.mShopData = datas;
        mLayoutInflater = LayoutInflater.from(context);
    }

    //判断当前item是否是HeadView
    public boolean isHeaderView(int position) {
        return mHeaderCount != 0 && position < mHeaderCount;
    }

    //内容长度
    public int getContentItemCount() {
        return mShopData == null ? 0 : mShopData.size() - mHeaderCount;
    }

    //判断当前item是否是FooterView
    public boolean isBottomView(int position) {
        return mBottomCount != 0 && position >= (mHeaderCount + getContentItemCount());
    }

    //判断当前item类型
    @Override
    public int getItemViewType(int position) {
        int dataItemCount = getContentItemCount();
        if (mHeaderCount != 0 && position < mHeaderCount) {
            //头部View
            return ITEM_TYPE_HEADER;
        } else if (mBottomCount != 0 && position >= (mHeaderCount + dataItemCount)) {
            //底部View
            return ITEM_TYPE_BOTTOM;
        } else {
            //内容View
            return ITEM_TYPE_CONTENT;
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_HEADER) {

        } else if (viewType == ITEM_TYPE_CONTENT) {
            return new ContentViewHolder(mLayoutInflater.inflate(R.layout.item_recycle_layout, parent, false));
        } else if (viewType == ITEM_TYPE_BOTTOM) {

            return null;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof HeaderViewHolder) {

        } else if (holder instanceof ContentViewHolder) {

            // 如果设置了回调，则设置点击事件
            if (mOnItemClickListener != null) {
                //itemView:ViewHolder的一个item
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = holder.getLayoutPosition();
                        mOnItemClickListener.onItemClick(holder.itemView, pos);
                    }
                });

                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        int pos = holder.getLayoutPosition();
                        mOnItemClickListener.onItemLongClick(holder.itemView, pos);
                        Toast.makeText(mContext, "长按", Toast.LENGTH_LONG).show();
                        return false;
                    }
                });
            }

        }
    }

    //初始化头部控件及事件
    private void checkInButton(View v, final Context context) {

        intoShop = (TextView) v.findViewById(R.id.intoShop);

        intoShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ShopActivity.class);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
//        return mHeaderCount + getContentItemCount() + mBottomCount;
        return mShopData == null ? 0 : mShopData.size();
    }

    //内容 ViewHolder
    public static class ContentViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name;
        TextView price;

        public ContentViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            name = (TextView) itemView.findViewById(R.id.name);
            price = (TextView) itemView.findViewById(R.id.price);

            ButterKnife.bind(this, itemView);

        }
    }

    //头部 ViewHolder
    public static class HeaderViewHolder extends RecyclerView.ViewHolder {
        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

    //底部 ViewHolder
    public static class BottomViewHolder extends RecyclerView.ViewHolder {
        public BottomViewHolder(View itemView) {
            super(itemView);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }
}
