package com.xiaoyu.schoolelive.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
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

/**
 * Created by NeekChaw on 2017-07-13.
 */

public class UserPubMsgDetailActivity extends BaseSlideBack implements View.OnClickListener {
    private int DEFAULT_HEAD = R.drawable.icon_default_head;

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
        adapterComment = new CommentAdapter(getApplicationContext(), data);
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
}

