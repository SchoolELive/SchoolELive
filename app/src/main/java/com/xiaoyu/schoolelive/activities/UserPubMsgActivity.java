package com.xiaoyu.schoolelive.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xiaoyu.schoolelive.R;
import com.xiaoyu.schoolelive.adapter.PublishAdapter;
import com.xiaoyu.schoolelive.base.BaseSlideBack;
import com.xiaoyu.schoolelive.data.Publish;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by NeekChaw on 2017-07-13.
 */

public class UserPubMsgActivity extends BaseSlideBack implements View.OnClickListener {

    //底部评论控件
    private ImageView chat;
    private ImageView comment;
    private TextView hide_down;
    private EditText comment_content;
    private Button comment_send;
    private LinearLayout rl_enroll;
    private RelativeLayout rl_comment;

    private ListView publish_list;
    private PublishAdapter adapterPublish;
    private List<Publish> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_pub_msg);
        initView();
    }

    private void initView() {
        //初始化底部控件
        comment = (ImageView) findViewById(R.id.comment);
        hide_down = (TextView) findViewById(R.id.hide_down);
        comment_content = (EditText) findViewById(R.id.comment_content);
        comment_send = (Button) findViewById(R.id.comment_send);
        rl_enroll = (LinearLayout) findViewById(R.id.rl_enroll);
        rl_comment = (RelativeLayout) findViewById(R.id.rl_comment);
        chat = (ImageView) findViewById(R.id.chat);


        // 初始化评论列表
        publish_list = (ListView) findViewById(R.id.pub_list);
        // 初始化数据
        data = new ArrayList<>();
        // 初始化适配器
        adapterPublish = new PublishAdapter(this, data);
        // 为评论列表设置适配器
        publish_list.setAdapter(adapterPublish);

        setListener();

    }

    /**
     * 设置监听
     */
    public void setListener() {
        comment.setOnClickListener(this);
        hide_down.setOnClickListener(this);
        comment_send.setOnClickListener(this);


        publish_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Publish publish = data.get(position);
                Intent intent = new Intent(UserPubMsgActivity.this, UserPubMsgDetailActivity.class);
                intent.putExtra("tmp_name", publish.getName());
                intent.putExtra("tmp_ymd", publish.getYmd());
                intent.putExtra("tmp_date", publish.getDate());
                intent.putExtra("tmp_content", publish.getContent());
                intent.putExtra("tmp_head", publish.getHead());
                intent.putExtra("tmp_like_count", publish.getLike_count());
                intent.putExtra("tmp_comment_count", publish.getComment_count());
                intent.putExtra("tmp_share_count", publish.getShare_count());

                startActivity(intent);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.chat:
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
                sendMessage();
                break;
            default:
                break;
        }
    }

    //测试头像
    public static int[] IMAGES = new int[]{
            R.drawable.dw_1, R.drawable.dw_2, R.drawable.dw_3,
            R.drawable.dw_4, R.drawable.dw_5, R.drawable.dw_6,
            R.drawable.dw_7, R.drawable.dw_8, R.drawable.dw_9};

    /**
     * 随机产生的一个图片
     *
     * @return
     */
    public static int getHead() {
        int index = new Random().nextInt(IMAGES.length);
        return IMAGES[index];
    }

    /**
     * 发送评论
     */
    public void sendMessage() {
        if (comment_content.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "动态不能为空！", Toast.LENGTH_SHORT).show();
        } else {
            // 生成数据
            Publish publish = new Publish();

            publish.setHead(getHead());
            publish.setName("发布者" + (data.size() + 1) + "：");
            publish.setContent(comment_content.getText().toString());

            //获取年月日
            SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
            Date curYMD = new Date(System.currentTimeMillis());
            String str = ymd.format(curYMD);
            publish.setYmd(str);
            //获取时间
            SimpleDateFormat date = new SimpleDateFormat("  HH:mm");
            Date curDate = new Date(System.currentTimeMillis());
            String str2 = date.format(curDate);
            publish.setDate(str2);

            adapterPublish.addPublish(publish);
            // 发送完，清空输入框
            comment_content.setText("");

            Toast.makeText(getApplicationContext(), "发布成功！", Toast.LENGTH_SHORT).show();
        }
    }


}
