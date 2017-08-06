package com.xiaoyu.schoolelive.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thinkcool.circletextimageview.CircleTextImageView;
import com.xiaoyu.schoolelive.R;
import com.xiaoyu.schoolelive.data.PartJob;
import com.xiaoyu.schoolelive.util.ConstantUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by NeekChaw on 2017-08-02.
 */

public class PartJobAdapter extends RecyclerView.Adapter<PartJobAdapter.ViewHolder> {
    private Context mContext;
    private List<PartJob> mData;

    private PartJobAdapter.OnItemClickListener onItemClickListener;

    public PartJobAdapter(Context c, List<PartJob> data) {
        this.mContext = c;
        this.mData = data;
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
            ButterKnife.bind(this, itemView);
        }
    }

    public List<PartJob> getList() {
        return mData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 实例化展示的View
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_partjob_thum, parent, false);
        // 实例化viewholder
        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // 绑定数据
        setWorkType(holder, position, mData.get(position).getWorkType());
        setWagesType(holder, position, mData.get(position).getWagesType());
        setWagesPaid(holder, position, mData.get(position).getWagesPay());
        //holder.workType.setText(mData.get(position).getWorkType());
        holder.workName.setText(mData.get(position).getWorkName());
        holder.workPlace.setText(mData.get(position).getWorkPlace());
        holder.workWages.setText(mData.get(position).getWorkWages());
        holder.workStartDate.setText(mData.get(position).getWorkStartDate());
        holder.workEndDate.setText(mData.get(position).getWorkEndDate());
        //holder.wagesType.setText(mData.get(position).getWagesType());

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
