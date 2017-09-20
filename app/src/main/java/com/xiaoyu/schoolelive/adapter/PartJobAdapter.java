package com.xiaoyu.schoolelive.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.thinkcool.circletextimageview.CircleTextImageView;
import com.xiaoyu.schoolelive.R;
import com.xiaoyu.schoolelive.custom.ExpandableView;
import com.xiaoyu.schoolelive.custom.ExpandleItemView;
import com.xiaoyu.schoolelive.data.PartJob;
import com.xiaoyu.schoolelive.util.ConstantUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * Created by NeekChaw on 2017-08-02.
 */


public class PartJobAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<PartJob> mData;
    private static final int ITEM_TYPE_HEADER = 0;
    private static final int ITEM_TYPE_CONTENT = 1;
    private int mHeaderCount = 1;//头部View个数

    private PartJobAdapter.OnItemClickListener onItemClickListener;

    public PartJobAdapter(Context c, List<PartJob> data) {
        this.mContext = c;
        this.mData = data;
    }

    //头部 ViewHolder
    public static class HeaderViewHolder extends RecyclerView.ViewHolder {
        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        CircleTextImageView workType;
        TextView wagesPay;
        TextView wagesType;
        TextView workWages;
        TextView workPlace;
        TextView workName;
        TextView workStartDate;
        TextView workEndDate;
        ImageView allowance_eat;
        ImageView allowance_sleep;
        ImageView allowance_traffic;
        ImageView allowance_other;

        public ViewHolder(View itemView) {
            super(itemView);
            workType = (CircleTextImageView) itemView.findViewById(R.id.workType);
            wagesPay = (TextView) itemView.findViewById(R.id.wagesPay);
            wagesType = (TextView) itemView.findViewById(R.id.wagesType);
            workWages = (TextView) itemView.findViewById(R.id.workWages);
            workPlace = (TextView) itemView.findViewById(R.id.workPlace);
            workName = (TextView) itemView.findViewById(R.id.workName);
            workStartDate = (TextView) itemView.findViewById(R.id.workStartDate);
            workEndDate = (TextView) itemView.findViewById(R.id.workEndDate);

            allowance_eat = (ImageView) itemView.findViewById(R.id.img_eat);
            allowance_sleep = (ImageView) itemView.findViewById(R.id.img_sleep);
            allowance_traffic = (ImageView) itemView.findViewById(R.id.img_traffic);
            allowance_other = (ImageView) itemView.findViewById(R.id.img_other);
            ButterKnife.bind(this, itemView);
        }
    }

    public List<PartJob> getList() {
        return mData;
    }

    //内容长度
    public int getContentItemCount() {
        return mData == null ? 0 : mData.size() - mHeaderCount;
    }

    //判断当前item类型
    @Override
    public int getItemViewType(int position) {
        int dataItemCount = getContentItemCount();
        if (mHeaderCount != 0 && position < mHeaderCount) {
            //头部View
            return ITEM_TYPE_HEADER;
        } else {
            //内容View
            return ITEM_TYPE_CONTENT;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_HEADER) {
            //分类列表
            ExpandableView mExpandableView;
            Map<String, ExpandleItemView> mExpandleItemViews;
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            String[] mWorkTypeOptions = mContext.getResources().getStringArray(R.array.workTypeOptions);
            String[] mWorkAllowance = mContext.getResources().getStringArray(R.array.workAllowance);
            String[] mWorkPayOptions = mContext.getResources().getStringArray(R.array.workPayOptions);

            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.include_partjob_head, parent, false);
            mExpandableView = new ExpandableView(mContext);
            mExpandableView = (ExpandableView) v.findViewById(R.id.expandview);
            mExpandleItemViews = new LinkedHashMap<>();
            mExpandleItemViews.put("工作类型", new ExpandleItemView("工作类型", mContext, Arrays.asList(mWorkTypeOptions)));
            mExpandleItemViews.put("待遇福利", new ExpandleItemView("待遇福利", mContext, Arrays.asList(mWorkAllowance)));
            mExpandleItemViews.put("结算方式", new ExpandleItemView("结算方式", mContext, Arrays.asList(mWorkPayOptions)));
            mExpandableView.initViews(new ArrayList<>(mExpandleItemViews.values()));

            //View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.include_partjob_head, parent, false);
            BGABanner mAdImages;
            mAdImages = (BGABanner) v.findViewById(R.id.partjob_Ad_Banner);

            mAdImages.setAdapter(new BGABanner.Adapter<ImageView, String>() {
                @Override
                public void fillBannerItem(BGABanner banner, ImageView itemView, String model, int position) {
                    Glide.with(mContext)
                            .load(model)
                            .placeholder(R.drawable.gif_ad_holder)
                            .error(R.mipmap.ic_launcher)
                            .centerCrop()
                            .dontAnimate()
                            .into(itemView);
                }
            });

            List<String> Image_List = new ArrayList<>();
            List<String> wordsList = new ArrayList<>();
            Image_List.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1504509481&di=90f71e005c64f0ce96a0c70a88ddda31&imgtype=jpg&er=1&src=http%3A%2F%2Fi.dimg.cc%2F98%2Fb6%2F89%2F5d%2F7b%2Fcc%2F5a%2F99%2Fa6%2F71%2F51%2F94%2Fdc%2Fdf%2Fd7%2Fa6.jpg");
            wordsList.add("虚位以待");

            mAdImages.setData(Image_List, wordsList);
            // 实例化viewholder
            return new HeaderViewHolder(v);

        } else if (viewType == ITEM_TYPE_CONTENT) {
            // 实例化展示的View
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_partjob_thum, parent, false);
            return new ViewHolder(v);
        }
        return null;
    }


    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {

        } else if (holder instanceof ViewHolder) {
            // 绑定数据
            //设置工作类型
            setWorkType((ViewHolder) holder, position - 1, mData.get(position - 1).getWorkType());
            //设置工资单位
            setWagesType((ViewHolder) holder, position - 1, mData.get(position - 1).getWagesType());
            //设置结算方式
            setWagesPaid((ViewHolder) holder, position - 1, mData.get(position - 1).getWagesPay());
            //设置待遇福利
            setWorkAllowance((ViewHolder) holder, position - 1, mData.get(position - 1).getWorkAllowance());
            //holder.workType.setText(mData.get(position).getWorkType());
            ((ViewHolder) holder).workName.setText(mData.get(position - 1).getWorkName());
            ((ViewHolder) holder).workPlace.setText(mData.get(position - 1).getWorkPlace());
            ((ViewHolder) holder).workWages.setText(mData.get(position - 1).getWorkWages());
            ((ViewHolder) holder).workStartDate.setText(mData.get(position - 1).getWorkStartDate());
            ((ViewHolder) holder).workEndDate.setText(mData.get(position - 1).getWorkEndDate());
            //holder.wagesType.setText(mData.get(position).getWagesType());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    if (onItemClickListener != null) {
                        int pos = holder.getLayoutPosition() - 1;
                        onItemClickListener.onItemClick(holder.itemView, pos);
                    }
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (onItemClickListener != null) {
                        int pos = holder.getLayoutPosition() - 1;
                        onItemClickListener.onItemLongClick(holder.itemView, pos);
                    } //表示此事件已经消费，不会触发单击事件
                    return true;
                }
            });
        }
    }

    public void setWorkAllowance(final ViewHolder holder, int i, int[] allowances) {
        if (allowances != null) {
            for (int index = 0; index < allowances.length; index++) {
                //餐补  住宿  交通  其他
                if (allowances[index] == ConstantUtil.ALLOWANCE_MEAL) {
                    holder.allowance_eat.setVisibility(View.VISIBLE);
                } else if (allowances[index] == ConstantUtil.ALLOWANCE_LIVE) {
                    holder.allowance_sleep.setVisibility(View.VISIBLE);
                } else if (allowances[index] == ConstantUtil.ALLOWANCE_TRAFFIC) {
                    holder.allowance_traffic.setVisibility(View.VISIBLE);
                } else if (allowances[index] == ConstantUtil.ALLOWANCE_OTHER) {
                    holder.allowance_other.setVisibility(View.VISIBLE);
                }
            }
        } else {
            return;
        }
    }

    public void setWorkType(final ViewHolder holder, int position, int type) {
        if (type == ConstantUtil.PartJob_BAOAN) {
            holder.workType.setText(R.string.baoan);
            holder.workType.setBorderColorResource(R.color.baoan);
            holder.workType.setTextColorResource(R.color.baoan);
            return;
        }
        if (type == ConstantUtil.PartJob_FACHUANDAN) {
            holder.workType.setText(R.string.fachuandan);
            holder.workType.setBorderColorResource(R.color.fachuandan);
            holder.workType.setTextColorResource(R.color.fachuandan);
            return;
        }
        if (type == ConstantUtil.PartJob_FUWUYUAN) {
            holder.workType.setText(R.string.fuwuyuan);
            holder.workType.setBorderColorResource(R.color.fuwuyuan);
            holder.workType.setTextColorResource(R.color.fuwuyuan);
            return;
        }
        if (type == ConstantUtil.PartJob_GONGCHANG) {
            holder.workType.setText(R.string.gongchang);
            holder.workType.setBorderColorResource(R.color.gongchang);
            holder.workType.setTextColorResource(R.color.gongchang);
            return;
        }
        if (type == ConstantUtil.PartJob_JIAJIAO) {
            holder.workType.setText(R.string.jiajiao);
            holder.workType.setBorderColorResource(R.color.jiajiao);
            holder.workType.setTextColorResource(R.color.jiajiao);
            return;
        }
        if (type == ConstantUtil.PartJob_KUAIDI) {
            holder.workType.setText(R.string.kuaidi);
            holder.workType.setBorderColorResource(R.color.kuaidi);
            holder.workType.setTextColorResource(R.color.kuaidi);
            return;
        }
        if (type == ConstantUtil.PartJob_TUIGUANG) {
            holder.workType.setText(R.string.tuiguang);
            holder.workType.setBorderColorResource(R.color.tuiguang);
            holder.workType.setTextColorResource(R.color.tuiguang);
            return;
        }
        if (type == ConstantUtil.PartJob_WAIMAI) {
            holder.workType.setText(R.string.waimai);
            holder.workType.setBorderColorResource(R.color.waimai);
            holder.workType.setTextColorResource(R.color.waimai);
            return;
        }
        if (type == ConstantUtil.PartJob_WANGLUO) {
            holder.workType.setText(R.string.wangluo);
            holder.workType.setBorderColorResource(R.color.wangluo);
            holder.workType.setTextColorResource(R.color.wangluo);
            return;
        }
        if (type == ConstantUtil.PartJob_XIAOSHOU) {
            holder.workType.setText(R.string.xiaoshou);
            holder.workType.setBorderColorResource(R.color.xiaoshou);
            holder.workType.setTextColorResource(R.color.xiaoshou);
            return;
        }
        if (type == ConstantUtil.PartJob_XIAOYUAN) {
            holder.workType.setText(R.string.xiaoyuan);
            holder.workType.setBorderColorResource(R.color.xiaoyuan);
            holder.workType.setTextColorResource(R.color.xiaoyuan);
            return;
        }
        if (type == ConstantUtil.PartJob_YANYUAN) {
            holder.workType.setText(R.string.yanyuan);
            holder.workType.setBorderColorResource(R.color.yanyuan);
            holder.workType.setTextColorResource(R.color.yanyuan);
            return;
        }
        if (type == ConstantUtil.PartJob_SHITANG) {
            holder.workType.setText(R.string.shitang);
            holder.workType.setBorderColorResource(R.color.shitang);
            holder.workType.setTextColorResource(R.color.shitang);
            return;
        }
        if (type == ConstantUtil.PartJob_QITA) {
            holder.workType.setText(R.string.qita);
            holder.workType.setBorderColorResource(R.color.qita);
            holder.workType.setTextColorResource(R.color.qita);
            return;
        }
    }

    public void setWagesType(final ViewHolder holder, int position, int type) {
        if (type == ConstantUtil.WagesType_PERHOUR) {
            holder.wagesType.setText(R.string.perhour);
            return;
        }
        if (type == ConstantUtil.WagesType_PERDAY) {
            holder.wagesType.setText(R.string.perday);
            return;
        }
        if (type == ConstantUtil.WagesType_PERMONTH) {
            holder.wagesType.setText(R.string.permonth);
            return;
        }
        if (type == ConstantUtil.WagesType_PERITEM) {
            holder.wagesType.setText(R.string.peritem);
            return;
        }
        if (type == ConstantUtil.WagesType_PERPIECE) {
            holder.wagesType.setText(R.string.perpiece);
            return;
        }
    }

    public void setWagesPaid(final ViewHolder holder, int position, int type) {
        if (type == ConstantUtil.WagesPay_DAY) {
            holder.wagesPay.setText(R.string.daypaid);
            holder.wagesPay.setBackgroundResource(R.color.daypaid);
            return;
        }
        if (type == ConstantUtil.WagesPay_MONTH) {
            holder.wagesPay.setText(R.string.monthpaid);
            holder.wagesPay.setBackgroundResource(R.color.monthpaid);
            return;
        }
        if (type == ConstantUtil.WagesPay_WEEK) {
            holder.wagesPay.setText(R.string.weekpaid);
            holder.wagesPay.setBackgroundResource(R.color.weekpaid);
            return;
        }
        if (type == ConstantUtil.WagesPay_AFTERWORK) {
            holder.wagesPay.setText(R.string.afterworkpaid);
            holder.wagesPay.setBackgroundResource(R.color.afterworkpaid);
            return;
        }
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public void addNewJob(PartJob partJob) {
        if (mData == null) {
            mData = new ArrayList<>();
        }
        mData.add(partJob);
        notifyItemInserted(0);
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
    public void setOnItemClickListener(PartJobAdapter.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

}
