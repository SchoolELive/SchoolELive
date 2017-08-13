package com.xiaoyu.schoolelive.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.cache.memory.MemoryCache;
import com.nostra13.universalimageloader.cache.memory.impl.LRULimitedMemoryCache;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.xiaoyu.schoolelive.R;
import com.xiaoyu.schoolelive.data.Goods;
import com.xiaoyu.schoolelive.data.ImageBean;
import com.xiaoyu.schoolelive.util.ConstantUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.xiaoyu.schoolelive.activities.UserAlbumActivity.mNormalImageOptions;

/**
 * Created by NeekChaw on 2017-07-28.
 */
public class WaterFallAdapter extends RecyclerView.Adapter<WaterFallAdapter.ViewHolder> {

    private Context mContext;
    //private List<ImageBean> mList = new ArrayList<>();
    private List<Goods> mData = new ArrayList<>();
    private List<Integer> mHeights;

    private String[] mItems = {"关注卖家", "收藏宝贝", "举报"};
    final String[] mAgainstItems = new String[]{"泄露隐私", "人身攻击", "淫秽色情", "垃圾广告", "敏感信息", "其他"};

    public WaterFallAdapter(Context context) {
        this.mContext = context;
    }

    public void getRandomHeight(List<Goods> mList) {
        mHeights = new ArrayList<>();
        for (int i = 0; i < mList.size(); i++) {
            //随机的获取一个范围为200-600直接的高度
            mHeights.add((int) (500 + Math.random() * 400));
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.item_image_text, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.imageview)
        ImageView mImageView;
        @Bind(R.id.goods_top)
        TextView mGoodsTop;
        @Bind(R.id.goods_hot)
        TextView mGoodsHot;
        @Bind(R.id.goods_new)
        TextView mGoodsNew;
        @Bind(R.id.goods_attention_count)
        TextView mGoodsPageViews;

        public ViewHolder(View view) {
            //需要设置super
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public List<Goods> getList() {
        return mData;
    }

    public static DisplayImageOptions NORMAL_OPTION = new DisplayImageOptions.Builder()
            .cacheInMemory(true)
            .showImageOnLoading(R.mipmap.ic_launcher)
            .showImageForEmptyUri(R.mipmap.ic_launcher)
            .showImageOnFail(R.mipmap.ic_launcher)
            .cacheOnDisc(true)
            .build();

//    public void addGoods(Goods goods) {
//        mData.add(goods);
//        notifyDataSetChanged();
//    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        layoutParams.height = mHeights.get(position);
        holder.itemView.setLayoutParams(layoutParams);

        initImageLoader(mContext);
        Goods goods = mData.get(position);
        //获取封面图片
        ImageBean bean = goods.getTopImages();
        //获取商品类型
        int GoodsStyle = goods.getGoodsStyle();
        setGoodsStyle(holder, GoodsStyle);
        //获取商品浏览量
        holder.mGoodsPageViews.setText(goods.getPageViews() + "");
        //显示图片
        ImageLoader.getInstance().displayImage(bean.getImgsrc(),
                holder.mImageView, NORMAL_OPTION);

        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getLayoutPosition();
                    onItemClickListener.onItemClick(holder.itemView, position);

                }
            });
            //LongClick
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = holder.getLayoutPosition();
                    onItemClickListener.onItemLongClick(holder.itemView, position);
                    return false;
                }
            });
        }
    }

    private void setGoodsStyle(ViewHolder holder, int type) {
        if (type == ConstantUtil.Goods_All) {
            holder.mGoodsHot.setVisibility(View.VISIBLE);
            holder.mGoodsNew.setVisibility(View.VISIBLE);
            holder.mGoodsTop.setVisibility(View.VISIBLE);
        } else if (type == ConstantUtil.Goods_Hot) {
            holder.mGoodsHot.setVisibility(View.VISIBLE);
        } else if (type == ConstantUtil.Goods_New) {
            holder.mGoodsNew.setVisibility(View.VISIBLE);
        } else if (type == ConstantUtil.Goods_Top) {
            holder.mGoodsTop.setVisibility(View.VISIBLE);
        } else if (type == ConstantUtil.Goods_Top_Hot) {
            holder.mGoodsTop.setVisibility(View.VISIBLE);
            holder.mGoodsHot.setVisibility(View.VISIBLE);
        } else if (type == ConstantUtil.Goods_Hot_New) {
            holder.mGoodsHot.setVisibility(View.VISIBLE);
            holder.mGoodsNew.setVisibility(View.VISIBLE);
        } else if (type == ConstantUtil.Goods_Top_New) {
            holder.mGoodsTop.setVisibility(View.VISIBLE);
            holder.mGoodsNew.setVisibility(View.VISIBLE);
        }
    }

    private void initImageLoader(Context context) {
        int memoryCacheSize = (int) (Runtime.getRuntime().maxMemory() / 5);
        MemoryCache memoryCache;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            memoryCache = new LruMemoryCache(memoryCacheSize);
        } else {
            memoryCache = new LRULimitedMemoryCache(memoryCacheSize);
        }

        mNormalImageOptions = new DisplayImageOptions.Builder().bitmapConfig(Bitmap.Config.RGB_565).cacheInMemory(true).cacheOnDisc(true)
                .resetViewBeforeLoading(true).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context).defaultDisplayImageOptions(mNormalImageOptions)
                //.denyCacheImageMultipleSizesInMemory().discCache(new UnlimitedDiskCache(new File(IMAGES_FOLDER)))
                // .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .memoryCache(memoryCache)
                // .memoryCacheSize(memoryCacheSize)
                .tasksProcessingOrder(QueueProcessingType.LIFO).threadPriority(Thread.NORM_PRIORITY - 2).threadPoolSize(3).build();

        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }


}