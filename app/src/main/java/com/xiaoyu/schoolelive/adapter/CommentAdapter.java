package com.xiaoyu.schoolelive.adapter;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xiaoyu.schoolelive.R;
import com.xiaoyu.schoolelive.data.Comment;

import java.util.List;

/**
 * Created by NeekChaw on 2017-07-14.
 */
public class CommentAdapter extends BaseAdapter {
    Context context;
    List<Comment> data;

    final String[] baseItems = new String[]{"关注", "举报", "复制内容"};
    final String[] againstItems = new String[]{"泄露隐私", "人身攻击", "淫秽色情", "垃圾广告", "敏感信息", "其他"};

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
            holder.btn_cmt_more = (ImageButton) convertView.findViewById(R.id.cmt_icon_more);

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

        holder.btn_cmt_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(holder);
            }
        });
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
        holder.btn_cmt_more.setTag(i);
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
        ImageButton btn_cmt_more;
        boolean IS_AGAINST = true;//初始状态，还未举报
        boolean IS_FOCUS = true;//初始状态，还未关注
        boolean CMT_LIKE_FLAG = true;
    }

    private void showDialog(final ViewHolder holder) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        //自定义对话框标题栏
        final View mTitleView = layoutInflater.inflate(R.layout.custom_user_center_dialog, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setItems(baseItems, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        if (holder.IS_FOCUS) {
                            Toast.makeText(context, "关注成功", Toast.LENGTH_SHORT).show();
                            holder.IS_FOCUS = false;
                        } else {
                            Toast.makeText(context, "您已关注该用户", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 1:
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setItems(againstItems, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0://泄露隐私
                                        dealClick(holder, which);
                                        break;
                                    case 1://人身攻击
                                        dealClick(holder, which);
                                        break;
                                    case 2://淫秽色情
                                        dealClick(holder, which);
                                        break;
                                    case 3://垃圾广告
                                        dealClick(holder, which);
                                        break;
                                    case 4://敏感信息
                                        dealClick(holder, which);
                                        break;
                                    case 5://其他
                                        dealClick(holder, which);
                                        break;
                                }
                            }
                        });
                        AlertDialog against = builder.show();
                        //设置宽度和高度
                        WindowManager.LayoutParams params =
                                against.getWindow().getAttributes();
                        params.width = 600;
                        params.height = 900;
                        params.y = -200;
                        params.alpha = 0.9f;
                        against.getWindow().setAttributes(params);
                        break;
                    case 2:
                        //获取系统剪贴板
                        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                        cmb.setText(holder.comment_content.getText().toString().trim());
                        Toast.makeText(context, "复制成功，可以分享给朋友们了", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
        builder.setCustomTitle(mTitleView);
        AlertDialog dialog = builder.show();
        //设置宽度和高度
        WindowManager.LayoutParams params =
                dialog.getWindow().getAttributes();
        params.width = 600;
        params.alpha = 0.9f;
        params.y = -200;
        dialog.getWindow().setAttributes(params);
    }

    //判断是否重复举报
    public boolean isAgainst(ViewHolder holder) {
        if (holder.IS_AGAINST) {
            holder.IS_AGAINST = false;
            return true;
        } else {
            return false;
        }
    }

    //对举报信息进行处理
    public void dealClick(ViewHolder holder, int dealCode) {
        if (isAgainst(holder)) {
            Toast.makeText(context, "举报成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "您已经举报过了！管理员正在审核处理",
                    Toast.LENGTH_SHORT).show();
        }
    }

}
