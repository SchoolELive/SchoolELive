package com.xiaoyu.schoolelive.activities;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xiaoyu.schoolelive.R;
import com.xiaoyu.schoolelive.adapter.CommentAdapter;
import com.xiaoyu.schoolelive.base.BaseSlideBack;
import com.xiaoyu.schoolelive.data.Comment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by NeekChaw on 2017-07-13.
 */

public class UserPubMsgDetailActivity extends BaseSlideBack implements View.OnClickListener {
    private int DEFAULT_HEAD = R.drawable.icon_default_head;

    final String[] baseItems = new String[]{"关注", "举报", "复制内容"};
    final String[] againstItems = new String[]{"泄露隐私", "人身攻击", "淫秽色情", "垃圾广告", "敏感信息", "其他"};

    boolean IS_AGAINST = true;//初始状态，还未举报
    boolean IS_FOCUS = true;//初始状态，还未关注
    //底部评论控件
    private ImageView comment;
    private TextView hide_down;
    private EditText comment_content;
    private Button comment_send;
    private LinearLayout rl_enroll;
    private RelativeLayout rl_comment;
    //主题与评论相关控件
    private ImageButton btn_pub_comment, btn_pub_like, btn_pub_share;
    private TextView pub_comment_count, pub_like_count, pub_share_count;
    private TextView pub_content, pub_nickname;
    private TextView all_like_count, all_cmt_count;
    private TextView pub_ymd, pub_date;
    private ImageView pub_head;
    private ImageButton btn_pub_more;
    //
    private ListView comment_list;
    private CommentAdapter adapterComment;
    private List<Comment> data;
    //
    private int INIT_COUNT = 0;
    private boolean CMT_LIKE_FLAG = true;
    private boolean PUB_LIKE_FLAG = true;

    private boolean PUB_COMMENT_FLAG = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user_pub_msg_detail);
        initView();
        initBar();
    }

    private void initBar() {
        all_cmt_count = (TextView) findViewById(R.id.all_cmt_count);
        all_like_count = (TextView) findViewById(R.id.all_like_count);
        //初始化全部点赞、评论数量

        all_like_count.setText(pub_like_count.getText());
        all_cmt_count.setText(pub_comment_count.getText());
    }

    private void initView() {

        initPublish();

        // 初始化评论列表
        comment_list = (ListView) findViewById(R.id.comment_list);
        // 初始化数据
        data = new ArrayList<>();
        // 初始化适配器
        adapterComment = new CommentAdapter(this, data);
        // 为评论列表设置适配器
        comment_list.setAdapter(adapterComment);

        //初始化底部控件
        comment = (ImageView) findViewById(R.id.comment);
        hide_down = (TextView) findViewById(R.id.hide_down);
        comment_content = (EditText) findViewById(R.id.comment_content);
        comment_send = (Button) findViewById(R.id.comment_send);
        rl_enroll = (LinearLayout) findViewById(R.id.rl_enroll);
        rl_comment = (RelativeLayout) findViewById(R.id.rl_comment);

        setListener();
    }

    public void initPublish() {
        Intent intent = getIntent();

        //初始化主题发布者信息
        pub_content = (TextView) findViewById(R.id.words_msg);
        btn_pub_share = (ImageButton) findViewById(R.id.pub_zhuanfa_icon);
        btn_pub_comment = (ImageButton) findViewById(R.id.pub_comment_icon);
        btn_pub_like = (ImageButton) findViewById(R.id.pub_like_icon);
        btn_pub_more = (ImageButton) findViewById(R.id.pub_icon_more);
        pub_like_count = (TextView) findViewById(R.id.pub_like_count);
        pub_comment_count = (TextView) findViewById(R.id.pub_comment_count);
        pub_share_count = (TextView) findViewById(R.id.pub_zhuanfa_count);
        pub_head = (ImageView) findViewById(R.id.user_head);
        pub_ymd = (TextView) findViewById(R.id.pub_ymd);
        pub_date = (TextView) findViewById(R.id.pub_date);
        pub_nickname = (TextView) findViewById(R.id.pub_nickname);

//        Bundle bundle = getIntent().getExtras();
//        pub_share_count.setText(bundle.getInt("tmp_share"));
//        pub_comment_count.setText(bundle.getInt("tmp_comment"));
//        pub_like_count.setText(bundle.getInt("tmp_like"));

        pub_content.setText(intent.getStringExtra("tmp_content"));
        pub_nickname.setText(intent.getStringExtra("tmp_name"));
        pub_head.setImageResource(intent.getIntExtra("tmp_head", DEFAULT_HEAD));
        pub_ymd.setText(intent.getStringExtra("tmp_ymd"));
        pub_date.setText(intent.getStringExtra("tmp_date"));

    }

    /**
     * 设置监听
     */
    public void setListener() {
        comment.setOnClickListener(this);
        hide_down.setOnClickListener(this);
        comment_send.setOnClickListener(this);

        btn_pub_like.setOnClickListener(this);
        btn_pub_comment.setOnClickListener(this);
        btn_pub_share.setOnClickListener(this);
        btn_pub_more.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pub_comment_icon:
            case R.id.comment:
                // 弹出输入法
                InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                // 显示评论框
                rl_enroll.setVisibility(View.GONE);
                rl_comment.setVisibility(View.VISIBLE);
                break;
            case R.id.hide_down:
                // 隐藏评论框
                rl_enroll.setVisibility(View.VISIBLE);
                rl_comment.setVisibility(View.GONE);
                // 隐藏输入法，然后暂存当前输入框的内容，方便下次使用
                InputMethodManager im = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                im.hideSoftInputFromWindow(comment_content.getWindowToken(), 0);
                break;
            case R.id.comment_send:
                sendComment();
                break;
            case R.id.pub_like_icon:
                if (PUB_LIKE_FLAG) {
                    pub_like_count.setText(Integer.valueOf(pub_like_count.getText().toString()) + 1 + "");
                    all_like_count.setText(pub_like_count.getText());
                    PUB_LIKE_FLAG = false;
                } else {
                    pub_like_count.setText(Integer.valueOf(pub_like_count.getText().toString()) + -1 + "");
                    all_like_count.setText(pub_like_count.getText());
                    PUB_LIKE_FLAG = true;
                }
                break;
            case R.id.pub_zhuanfa_icon:
                showShare();
                break;
            case R.id.pub_icon_more:
                showDialog();
                break;
            default:
                break;
        }
    }

    /**
     * 发送评论
     */
    public void sendComment() {
        if (comment_content.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "评论不能为空！", Toast.LENGTH_SHORT).show();
        } else {
            // 生成评论数据
            Comment comment = new Comment();
            comment.setHead(R.drawable.icon_default_head);
            comment.setName("评论者" + (data.size() + 1) + "：");
            comment.setContent(comment_content.getText().toString());

            //获取年月日
            SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
            Date curYMD = new Date(System.currentTimeMillis());
            String str = ymd.format(curYMD);
            comment.setYmd(str);
            //获取时间
            SimpleDateFormat date = new SimpleDateFormat("  HH:mm");
            Date curDate = new Date(System.currentTimeMillis());
            String str2 = date.format(curDate);
            comment.setDate(str2);

            adapterComment.addComment(comment);
            // 发送完，清空输入框
            comment_content.setText("");

            Toast.makeText(getApplicationContext(), "评论成功！", Toast.LENGTH_SHORT).show();
            pub_comment_count.setText(Integer.valueOf(pub_comment_count.getText().toString()) + 1 + "");
            all_cmt_count.setText(pub_comment_count.getText());
        }
    }

    //分享界面
    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // 分享时Notification的图标和文字  2.5.9以后的版本不     调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(this.getString(R.string.app_name));
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
        oks.setSite(this.getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

        // 启动分享GUI
        oks.show(this);
    }

    private void showDialog() {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        //自定义对话框标题栏
        final View mTitleView = layoutInflater.inflate(R.layout.custom_user_center_dialog, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems(baseItems, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        //不完整，需要服务器配合
                        Toast.makeText(UserPubMsgDetailActivity.this, "关注成功", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        AlertDialog.Builder builder = new AlertDialog.Builder(UserPubMsgDetailActivity.this);
                        builder.setItems(againstItems, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0://泄露隐私
                                        Toast.makeText(UserPubMsgDetailActivity.this, "举报成功", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 1://人身攻击
                                        Toast.makeText(UserPubMsgDetailActivity.this, "举报成功", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 2://淫秽色情
                                        Toast.makeText(UserPubMsgDetailActivity.this, "举报成功", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 3://垃圾广告
                                        Toast.makeText(UserPubMsgDetailActivity.this, "举报成功", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 4://敏感信息
                                        Toast.makeText(UserPubMsgDetailActivity.this, "举报成功", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 5://其他
                                        Toast.makeText(UserPubMsgDetailActivity.this, "举报成功", Toast.LENGTH_SHORT).show();
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
                        ClipboardManager cmb = (ClipboardManager) UserPubMsgDetailActivity.this.getSystemService(Context.CLIPBOARD_SERVICE);
                        cmb.setText(pub_content.getText().toString().trim());
                        Toast.makeText(UserPubMsgDetailActivity.this, "复制成功，可以分享给朋友们了", Toast.LENGTH_SHORT).show();
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

//    //判断是否重复举报
//    public boolean isAgainst(CommentAdapter.ViewHolder holder) {
//        if (IS_AGAINST) {
//            IS_AGAINST = false;
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//    //对举报信息进行处理
//    public void dealClick(CommentAdapter.ViewHolder holder, int dealCode) {
//        if (isAgainst(holder)) {
//            Toast.makeText(this, "举报成功", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(this, "您已经举报过了！管理员正在审核处理",
//                    Toast.LENGTH_SHORT).show();
//        }
//    }
}

