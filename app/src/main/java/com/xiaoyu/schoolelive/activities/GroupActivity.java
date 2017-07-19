package com.xiaoyu.schoolelive.activities;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.xiaoyu.schoolelive.R;

/**
 * Created by Administrator on 2017/7/11.
 */
public class GroupActivity extends ActivityGroup {
    private ScrollView container = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置视
        setContentView(R.layout.include_content_main);

        container = (ScrollView) findViewById(R.id.containerBody);

        container.removeAllViews();
        container.addView(getLocalActivityManager().startActivity(
                "Module1",
                new Intent(GroupActivity.this, MainMenuView1.class)
                        //Intent.FLAG_ACTIVITY_CLEAR_TOP:如果在当前的Task中，有要启动的Activity
                        //那么就把该Activity之前的所有Activity都关掉，并把此Activity置前以避免创建Activity的实例
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                //返回一个View，然后通过与指定容器的addView(View)方法，实现不同的效果
                .getDecorView());

        //模块1
        ImageView btnModule1 = (ImageView) findViewById(R.id.btnModule1);
        btnModule1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //移除所有子视图
                container.removeAllViews();
                //通过LocalActivityManager通过startAtivity(String id,Intent intent)可以于指定的Activity绑定
                //并且返回一个Window。LocalActivityManager可以同时管理多个Activity
                container.addView(getLocalActivityManager().startActivity(
                        "Module1",
                        new Intent(GroupActivity.this, MainMenuView1.class)
                                //Intent.FLAG_ACTIVITY_CLEAR_TOP:如果在当前的Task中，有要启动的Activity
                                //那么就把该Activity之前的所有Activity都关掉，并把此Activity置前以避免创建Activity的实例
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        //返回一个View，然后通过与指定容器的addView(View)方法，实现不同的效果
                        .getDecorView());
            }

        });

        // 模块2
        ImageView btnModule2 = (ImageView) findViewById(R.id.btnModule2);
        btnModule2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                container.removeAllViews();
                container.addView(getLocalActivityManager().startActivity(
                        "Module2",
                        new Intent(GroupActivity.this, MainMenuView2.class)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        .getDecorView());
            }
        });

        // 模块3
        ImageView btnModule3 = (ImageView) findViewById(R.id.btnModule3);
        btnModule3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                container.removeAllViews();
                container.addView(getLocalActivityManager().startActivity(
                        "Module3",
                        new Intent(GroupActivity.this, MainMenuView3.class)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        .getDecorView());
            }
        });
    }
}
