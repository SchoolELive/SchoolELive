package com.xiaoyu.schoolelive.base;

import android.graphics.Color;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiaoyu.schoolelive.R;
/**
 * Created by Administrator on 2017/7/11.
 */
public class BaseMainSlide extends AppCompatActivity implements View.OnClickListener{

    //手指上下滑动时的最小速度
    private static final int YSPEED_MIN = 1000;

    //手指左右滑动时的最小距离
    private static final int XDISTANCE_MIN = 200;

    //手指向上滑或下滑时的最小距离
    private static final int YDISTANCE_MIN = 300;

    //记录手指按下时的横坐标。
    private float xDown;

    //记录手指按下时的纵坐标。
    private float yDown;

    //记录手指移动时的横坐标。
    private float xMove;

    //记录手指移动时的纵坐标。
    private float yMove;

    //用于计算手指滑动的速度。
    private VelocityTracker mVelocityTracker;

    private LinearLayout home_hot_content;
    private LinearLayout home_new_content;
    private LinearLayout home_know_content;
    private LinearLayout home_story_content;

    private TextView home_hot_tv;
    private TextView home_new_tv;
    private TextView home_know_tv;
    private TextView home_story_tv;

    private View home_hot_view;
    private View home_new_view;
    private View home_know_view;
    private View home_story_view;


    private static int count = 0;
    //初始化定义首页导航栏
    public void homeCreate(){
        home_hot_tv = (TextView)findViewById(R.id.home_hot_tv);
        home_new_tv = (TextView)findViewById(R.id.home_new_tv);
        home_know_tv = (TextView)findViewById(R.id.home_know_tv);
        home_story_tv = (TextView)findViewById(R.id.home_story_tv);

//        home_hot_tv.setOnClickListener(this);
//        home_new_tv.setOnClickListener(this);
//        home_know_tv.setOnClickListener(this);
//        home_story_tv.setOnClickListener(this);

        home_hot_view = (View)findViewById(R.id.home_hot_view);
        home_new_view = (View)findViewById(R.id.home_new_view);
        home_know_view = (View)findViewById(R.id.home_know_view);
        home_story_view = (View)findViewById(R.id.home_story_view);

        home_hot_content = (LinearLayout)findViewById(R.id.home_hot_content);
        home_new_content = (LinearLayout)findViewById(R.id.home_new_content);
        home_know_content = (LinearLayout)findViewById(R.id.home_know_content);
        home_story_content = (LinearLayout)findViewById(R.id.home_story_content);
    }
    //初始化首页导航栏style
    public void inithome(){

        home_hot_tv.setTextColor(Color.BLUE);
        home_hot_view.setBackgroundColor(Color.WHITE);

        home_new_tv.setTextColor(Color.BLUE);
        home_new_view.setBackgroundColor(Color.WHITE);

        home_know_tv.setTextColor(Color.BLUE);
        home_know_view.setBackgroundColor(Color.WHITE);

        home_story_tv.setTextColor(Color.BLUE);
        home_story_view.setBackgroundColor(Color.WHITE);
    }
    //隐藏首页导航栏分页内容
    public void hidehomeall(){
        home_hot_content.setVisibility(View.GONE);
        home_new_content.setVisibility(View.GONE);
        home_know_content.setVisibility(View.GONE);
        home_story_content.setVisibility(View.GONE);
    }

    public boolean dispatchTouchEvent(MotionEvent event) {
        homeCreate();
        createVelocityTracker(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xDown = event.getRawX();
                yDown = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                xMove = event.getRawX();
                yMove = event.getRawY();
                //滑动的距离
                final int distanceX = (int) (xMove - xDown);
                int distanceFX = (int)(xDown - xMove);
                int distanceY = (int) (yMove - yDown);
                //获取顺时速度
                int ySpeed = getScrollVelocity();
                //关闭Activity需满足以下条件：
                //1.x轴滑动的距离>XDISTANCE_MIN
                //2.y轴滑动的距离在YDISTANCE_MIN范围内
                //3.y轴上（即上下滑动的速度）<XSPEED_MIN，如果大于，则认为用户意图是在上下滑动而非左滑结束Activity
                if (distanceX > XDISTANCE_MIN && (distanceY < YDISTANCE_MIN && distanceY > -YDISTANCE_MIN) && ySpeed < YSPEED_MIN) {
                    if(count == 3){
                        hidehomeall();
                        inithome();
                        home_know_content.setVisibility(View.VISIBLE);
                        home_know_tv.setTextColor(Color.GREEN);
                        home_know_view.setBackgroundColor(Color.BLUE);
                        count--;
                    }else if(count == 2){
                        hidehomeall();
                        inithome();
                        home_new_content.setVisibility(View.VISIBLE);
                        home_new_tv.setTextColor(Color.GREEN);
                        home_new_view.setBackgroundColor(Color.BLUE);
                        count--;
                    }else if(count == 1){
                        hidehomeall();
                        inithome();
                        home_hot_content.setVisibility(View.VISIBLE);
                        home_hot_tv.setTextColor(Color.GREEN);
                        home_hot_view.setBackgroundColor(Color.BLUE);
                        count--;
                    }else if (count == 0){
                        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
                        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
                        }else{
                            drawerLayout.openDrawer(GravityCompat.START);
                        }
                    }
                }
                if (distanceFX > XDISTANCE_MIN && (distanceY < YDISTANCE_MIN && distanceY > -YDISTANCE_MIN) && ySpeed < YSPEED_MIN) {
                    if (count == 0){
                        hidehomeall();
                        inithome();
                        home_new_content.setVisibility(View.VISIBLE);
                        home_new_tv.setTextColor(Color.GREEN);
                        home_new_view.setBackgroundColor(Color.BLUE);
                        count++;
                    }else if(count == 1){
                        hidehomeall();
                        inithome();
                        home_know_content.setVisibility(View.VISIBLE);
                        home_know_tv.setTextColor(Color.GREEN);
                        home_know_view.setBackgroundColor(Color.BLUE);
                        count++;
                    }else if(count == 2){
                        hidehomeall();
                        inithome();
                        home_story_content.setVisibility(View.VISIBLE);
                        home_story_tv.setTextColor(Color.GREEN);
                        home_story_view.setBackgroundColor(Color.BLUE);
                        count++;
                    }else if(count == 3){
                    }
                }
                recycleVelocityTracker();
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(event);
    }
    /**
     * 创建VelocityTracker对象，并将触摸界面的滑动事件加入到VelocityTracker当中。
     *
     * @param event
     */
    private void createVelocityTracker(MotionEvent event) {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
    }
    /**
     * 回收VelocityTracker对象。
     */
    private void recycleVelocityTracker() {
        mVelocityTracker.recycle();
        mVelocityTracker = null;
    }
    /**
     * @return 滑动速度，以每秒钟移动了多少像素值为单位。
     */
    private int getScrollVelocity() {
        mVelocityTracker.computeCurrentVelocity(1000);
        int velocity = (int) mVelocityTracker.getYVelocity();
        return Math.abs(velocity);
    }
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.home_hot_tv:
                hidehomeall();
                inithome();
                home_hot_content.setVisibility(View.VISIBLE);
                home_hot_tv.setTextColor(Color.GREEN);
                home_hot_view.setBackgroundColor(Color.BLUE);
                break;
            case R.id.home_new_tv:
                hidehomeall();
                inithome();
                home_new_content.setVisibility(View.VISIBLE);
                home_new_tv.setTextColor(Color.GREEN);
                home_new_view.setBackgroundColor(Color.BLUE);
                break;
            case R.id.home_know_tv:
                inithome();
                hidehomeall();
                home_know_content.setVisibility(View.VISIBLE);
                home_know_tv.setTextColor(Color.GREEN);
                home_know_view.setBackgroundColor(Color.BLUE);
                break;
            case R.id.home_story_tv:
                inithome();
                hidehomeall();
                home_story_content.setVisibility(View.VISIBLE);
                home_story_tv.setTextColor(Color.GREEN);
                home_story_view.setBackgroundColor(Color.BLUE);
                break;
        }
    }
}
