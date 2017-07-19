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
import com.xiaoyu.schoolelive.data.Publish;

import java.util.List;

import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by NeekChaw on 2017-07-16.
 */

public class PublishAdapter extends BaseAdapter {

    Context context;
    List<Publish> data;


    final String[] baseItems = new String[]{"关注", "举报", "复制内容"};
    final String[] againstItems = new String[]{"泄露隐私", "人身攻击", "淫秽色情", "垃圾广告", "敏感信息", "其他"};

    public PublishAdapter(Context c, List<Publish> data) {
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
    public View getView(int i, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        // 重用convertView
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.custom_user_pub_msg, null);

            //初始化按钮
            holder.btn_pub_share = (ImageButton) convertView.findViewById(R.id.pub_zhuanfa_icon);
            holder.btn_pub_comment = (ImageButton) convertView.findViewById(R.id.pub_comment_icon);
            holder.btn_pub_like = (ImageButton) convertView.findViewById(R.id.pub_like_icon);
            holder.btn_pub_more = (ImageButton) convertView.findViewById(R.id.pub_icon_more);
            //初始化信息
            holder.publish_head = (ImageView) convertView.findViewById(R.id.user_head);
            holder.publish_name = (TextView) convertView.findViewById(R.id.pub_nickname);
            holder.publish_ymd = (TextView) convertView.findViewById(R.id.pub_ymd);
            holder.publish_date = (TextView) convertView.findViewById(R.id.pub_date);
            holder.pub_comment_count = (TextView) convertView.findViewById(R.id.pub_comment_count);
            holder.pub_like_count = (TextView) convertView.findViewById(R.id.pub_like_count);
            holder.pub_share_count = (TextView) convertView.findViewById(R.id.pub_zhuanfa_count);
            holder.publish_content = (TextView) convertView.findViewById(R.id.words_msg);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // 适配数据
        holder.publish_name.setText(data.get(i).getName());
        holder.publish_content.setText(data.get(i).getContent());
        holder.publish_head.setImageResource(data.get(i).getHead());
        holder.publish_date.setText(data.get(i).getDate());
        holder.publish_ymd.setText(data.get(i).getYmd());


        //实现监听事件
        //点赞
        holder.btn_pub_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.PUB_LIKE_FLAG) {
                    holder.pub_like_count.setText(Integer.valueOf(holder.pub_like_count.getText().toString()) + 1 + "");
                    holder.PUB_LIKE_FLAG = false;
                } else {
                    holder.pub_like_count.setText(Integer.valueOf(holder.pub_like_count.getText().toString()) + -1 + "");
                    holder.PUB_LIKE_FLAG = true;
                }
            }
        });

        //评论
        holder.btn_pub_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.btn_pub_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showShare();
            }
        });
        //更多
        holder.btn_pub_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(holder);
            }
        });

        holder.btn_pub_like.setTag(i);
        holder.btn_pub_comment.setTag(i);
        holder.btn_pub_share.setTag(i);
        holder.btn_pub_more.setTag(i);
        return convertView;
    }


    //发布动态
    public void addPublish(Publish publish) {
        data.add(publish);
        notifyDataSetChanged();
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
                        cmb.setText(holder.publish_content.getText().toString().trim());
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

    /**
     * 静态类，便于GC回收
     */
    public static class ViewHolder {
        TextView publish_name;
        TextView publish_content;
        ImageView publish_head;
        TextView publish_date;
        TextView publish_ymd;
        TextView pub_comment_count;
        TextView pub_like_count;
        TextView pub_share_count;
        ImageButton btn_pub_like;
        ImageButton btn_pub_comment;
        ImageButton btn_pub_share;
        ImageButton btn_pub_more;
        boolean PUB_LIKE_FLAG = true;//初始状态，还未点赞
        boolean IS_AGAINST = true;//初始状态，还未举报
        boolean IS_FOCUS = true;//初始状态，还未关注
    }

    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // 分享时Notification的图标和文字  2.5.9以后的版本不     调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(context.getString(R.string.app_name));
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(context.getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

        // 启动分享GUI
        oks.show(context);
    }
}