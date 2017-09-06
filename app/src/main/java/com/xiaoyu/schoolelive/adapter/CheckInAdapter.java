package com.xiaoyu.schoolelive.adapter;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xiaoyu.schoolelive.R;
import com.xiaoyu.schoolelive.activities.ShopActivity;
import com.xiaoyu.schoolelive.activities.ShopItemDetailActivity;
import com.xiaoyu.schoolelive.data.ShopData;
import com.xiaoyu.schoolelive.data.SysInform;
import com.xiaoyu.schoolelive.data.Task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;

import static java.lang.System.currentTimeMillis;
import static java.lang.System.in;


public class HeaderBottomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    //item类型
    //头部
    public static final int ITEM_TYPE_HEADER = 0;
    //金币商城
    public static final int ITEM_TYPE_CONTENT = 1;
    //底部
    public static final int ITEM_TYPE_BOTTOM = 2;
    //金币任务
    public static final int ITEM_TYPE_HEADER_TWO = 3;
    //item的高度
    private List<Integer> mHeights;

    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private List<ShopData> mShopData;
    private List<Task> mData;
    private GoldTaskAdapter goldTaskAdapter;
    private int mHeaderCount = 2;//头部View个数
    private int mBottomCount = 0;//底部View个数
    public HeaderBottomAdapter(Context context,List<ShopData> datas) {
        this.mContext = context;
        this.mShopData = datas;
        mLayoutInflater = LayoutInflater.from(context);

    }
    //判断当前item是否是HeadView
    public boolean isHeaderView(int position) {
        return mHeaderCount != 0 && position < mHeaderCount;
    }
    //内容长度
    public int getContentItemCount(){
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
        if (mHeaderCount != 0 && position < mHeaderCount && position == 0) {
            //第一个头部View
            return ITEM_TYPE_HEADER;
        }else if(mHeaderCount != 0 && position < mHeaderCount && position == 1){
            //第二个头部View(金币任务)
            return ITEM_TYPE_HEADER_TWO;
        } else if (mBottomCount != 0 && position >= (mHeaderCount + dataItemCount)) {
            //底部View
            return ITEM_TYPE_BOTTOM;
        } else {
            //内容View
            return ITEM_TYPE_CONTENT;
        }
    }

    private Date date_check = null, ymd_check = null;
    private SimpleDateFormat ymd_now = null, date_now = null;
    private Button checkInButton;
    private TextView myCheckGold, intoShop, intoTask;
    private RecyclerView twoRecycler;

    private LinearLayout shop_ll, task_ll;
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType ==ITEM_TYPE_HEADER) {
            View v = mLayoutInflater.inflate(R.layout.include_recycle_header, parent, false);
            //初始化头部控件及事件(签到)
            checkInButton(v,mContext);

            return new HeaderViewHolder(v);
        } else if(viewType == ITEM_TYPE_HEADER_TWO){
            View v = mLayoutInflater.inflate(R.layout.include_recycle_two, parent, false);
            //初始化金币任务控件及事件
            InitEvent(v,mContext);

            return new HeaderTwoHolder(v);
        }else if (viewType == ITEM_TYPE_CONTENT) {
            return new ContentViewHolder(mLayoutInflater.inflate(R.layout.item_recycle_layout, parent, false));
        }else if(viewType == ITEM_TYPE_BOTTOM){
            //return new ContentViewHolder(mLayoutInflater.inflate(R.layout.include_recycle_bottom, parent, false));
            return null;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder,final int position) {
        if (holder instanceof HeaderViewHolder) {

        } else if (holder instanceof ContentViewHolder) {
            ((ContentViewHolder)holder).image.setImageResource(mShopData.get(position).getImage());
            ((ContentViewHolder)holder).name.setText(mShopData.get(position).getName());
            ((ContentViewHolder)holder).price.setText(mShopData.get(position).getPrice());

            // 如果设置了回调，则设置点击事件
            if (mOnItemClickListener != null) {
                //itemView:ViewHolder的一个item
                ((ContentViewHolder)holder).itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = ((ContentViewHolder)holder).getLayoutPosition();
                        mOnItemClickListener.onItemClick(((ContentViewHolder)holder).itemView, pos);
                        Intent intent = new Intent(mContext, ShopItemDetailActivity.class);
                        //intent.putExtra("shop_image",mShopData.get(position).getImage());
                        intent.putExtra("shop_name",mShopData.get(position).getName());
                        intent.putExtra("shop_price",mShopData.get(position).getPrice());
                        mContext.startActivity(intent);
                    }
                });

                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        int pos = ((ContentViewHolder)holder).getLayoutPosition();
                        mOnItemClickListener.onItemLongClick(((ContentViewHolder)holder).itemView, pos);
                        Toast.makeText(mContext,"长按",Toast.LENGTH_LONG).show();
                        return false;
                    }
                });
            }
//        } else if (holder instanceof BottomViewHolder) {
//
        }
    }

    //初始化头部控件及事件
    private void checkInButton(View v, final Context context){
        myCheckGold = (TextView)v.findViewById(R.id.myCheckGold);

        checkInButton = (Button)v.findViewById(R.id.checkInButton);
        intoTask = (TextView)v.findViewById(R.id.intoTask);
        shop_ll = (LinearLayout)v.findViewById(R.id.shop_ll);

        checkInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date ymd = new Date(currentTimeMillis());
                if (ymd_check == null){
                    myCheckGold.setText(String.valueOf(Integer.parseInt(myCheckGold.getText().toString()) + 2));
                    ymd_check = ymd;
                }else{
                    Long time = ymd.getTime() - ymd_check.getTime();
                    if (time < 5000){
                        new AlertDialog.Builder(context)
                                .setTitle("您今天已经签到过了！！！")
                                .setPositiveButton("确定",null)
                                .show();
                    }else if(time >= 5000){
                        ymd_check = ymd;
                        int as = Integer.parseInt(myCheckGold.getText().toString()) + 2;
                        String str = String.valueOf(as);
                        myCheckGold.setText(str);
                    }
                }
            }
        });


    }
    //初始化金币任务控件及事件
    private void InitEvent(View v,final Context context){
        twoRecycler = (RecyclerView)v.findViewById(R.id.recyclerView_two);

        task_ll = (LinearLayout)v.findViewById(R.id.task_ll);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        twoRecycler.setLayoutManager(layoutManager);
        twoRecycler.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));
        mData = new ArrayList<>();
        goldTaskAdapter = new GoldTaskAdapter(mContext , mData);
        twoRecycler.setAdapter(goldTaskAdapter);
        getTaskData();

        intoShop = (TextView)v.findViewById(R.id.intoShop);
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

            ButterKnife.bind(this,itemView);

        }
    }
    //头部 ViewHolder
    public static class HeaderViewHolder extends RecyclerView.ViewHolder {
        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }
    //金币任务
    public static class HeaderTwoHolder extends RecyclerView.ViewHolder{
        public HeaderTwoHolder(View itemView){
            super(itemView);
        }
    }
    //底部 ViewHolder
    public static class BottomViewHolder extends RecyclerView.ViewHolder {
        public BottomViewHolder(View itemView) {
            super(itemView);
        }
    }
    //初始化金币任务控件及事件
    public void getTaskData(){
        for (int i = 0; i < 4; i++) {
            Task inform = new Task();
            inform.setGoldTask_image(R.drawable.ic_home_black_24dp);
            inform.setGoldTask_name("在线奖励");
            inform.setGoldTask_content("登录在线5,10,15,30,45,60分钟可 分别奖励1,2,3,4,5,6个金币");
            mData.add(inform);
        }
//        mAdapter.getList().addAll(mData);
        goldTaskAdapter.notifyDataSetChanged();
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }
}