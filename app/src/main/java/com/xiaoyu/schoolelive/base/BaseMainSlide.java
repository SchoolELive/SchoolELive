package com.xiaoyu.schoolelive.base;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.widget.TabHost;

import com.xiaoyu.schoolelive.R;
import com.xiaoyu.schoolelive.activities.HomeFragment;
import com.xiaoyu.schoolelive.activities.MainActivity;
import com.xiaoyu.schoolelive.activities.SysInformFragment;

/**
 * Created by Administrator on 2017/7/11.
 */
public class BaseMainSlide extends AppCompatActivity{

    //手指上下滑动时的最小速度
    private static final int YSPEED_MIN = 2000;
    //手指左右滑动时的最小距离
    private static final int XDISTANCE_MIN = 150;
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

    //手势事件
    public boolean dispatchTouchEvent(MotionEvent event) {
        createVelocityTracker(event);
        if (HomeFragment.isDis) {
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

                    mainTabChange();

                    recycleVelocityTracker();
                    break;
                default:
                    break;
            }
        }else if(SysInformFragment.SysInformIsDisplay){
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
                    SysInformTabChange();
                    recycleVelocityTracker();
                    break;
                default:
                    break;
            }
        }
        return super.dispatchTouchEvent(event);
    }
    //主页分页滑动
    public void mainTabChange(){
        //滑动的距离
        final int distanceX = (int) (xMove - xDown);
        int distanceFX = (int) (xDown - xMove);
        int distanceY = (int) (yMove - yDown);
        //获取顺时速度
        int ySpeed = getScrollVelocity();
        //关闭Activity需满足以下条件：
        //1.x轴滑动的距离>XDISTANCE_MIN
        //2.y轴滑动的距离在YDISTANCE_MIN范围内
        //3.y轴上（即上下滑动的速度）<XSPEED_MIN，如果大于，则认为用户意图是在上下滑动而非左滑结束Activity
        TabHost tabHost = (TabHost) findViewById(R.id.home_tabhost);
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        if ( distanceX > XDISTANCE_MIN && (distanceY < YDISTANCE_MIN && distanceY > -YDISTANCE_MIN) && ySpeed < YSPEED_MIN) {
            if (tabHost.getCurrentTab() == 3) {
                tabHost.setCurrentTab(2);
            } else if (tabHost.getCurrentTab() == 2) {
                tabHost.setCurrentTab(1);
            } else if (tabHost.getCurrentTab() == 1) {
                tabHost.setCurrentTab(0);
            } else if (tabHost.getCurrentTab() == 0) {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        }
        if (distanceFX > XDISTANCE_MIN && (distanceY < YDISTANCE_MIN && distanceY > -YDISTANCE_MIN) && ySpeed < YSPEED_MIN) {
            if (tabHost.getCurrentTab() == 0) {
                tabHost.setCurrentTab(1);
            } else if (tabHost.getCurrentTab() == 1) {
                tabHost.setCurrentTab(2);
            } else if (tabHost.getCurrentTab() == 2) {
                tabHost.setCurrentTab(3);
            }
        }
    }
    //消息通知界面分页滑动
    public void SysInformTabChange(){
        //滑动的距离
        final int distanceX = (int) (xMove - xDown);
        int distanceFX = (int) (xDown - xMove);
        int distanceY = (int) (yMove - yDown);
        //获取顺时速度
        int ySpeed = getScrollVelocity();
        //关闭Activity需满足以下条件：
        //1.x轴滑动的距离>XDISTANCE_MIN
        //2.y轴滑动的距离在YDISTANCE_MIN范围内
        //3.y轴上（即上下滑动的速度）<XSPEED_MIN，如果大于，则认为用户意图是在上下滑动而非左滑结束Activity
        TabHost tabHost = (TabHost) findViewById(R.id.sysinform_tabhost);
        if ( distanceX > XDISTANCE_MIN && (distanceY < YDISTANCE_MIN && distanceY > -YDISTANCE_MIN) && ySpeed < YSPEED_MIN) {
            if (tabHost.getCurrentTab() == 1) {
                tabHost.setCurrentTab(0);
            }
        }
        if (distanceFX > XDISTANCE_MIN && (distanceY < YDISTANCE_MIN && distanceY > -YDISTANCE_MIN) && ySpeed < YSPEED_MIN) {
            if (tabHost.getCurrentTab() == 0) {
                tabHost.setCurrentTab(1);
            }
        }
    }
    //创建VelocityTracker对象，并将触摸界面的滑动事件加入到VelocityTracker当中。@param event
    private void createVelocityTracker(MotionEvent event) {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
    }
    //回收VelocityTracker对象。
    private void recycleVelocityTracker() {
        mVelocityTracker.recycle();
        mVelocityTracker = null;
    }
    //@return 滑动速度，以每秒钟移动了多少像素值为单位。
    private int getScrollVelocity() {
        mVelocityTracker.computeCurrentVelocity(1000);
        int velocity = (int) mVelocityTracker.getYVelocity();
        return Math.abs(velocity);
    }
}

