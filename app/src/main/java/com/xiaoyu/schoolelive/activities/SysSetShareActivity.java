package com.xiaoyu.schoolelive.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.LinearLayout;

import xiaoyu.com.schoolelive.R;

public class SysSetShareActivity extends AppCompatActivity {
    static final int ITEM_OPTION = 0x0001;
    static final int ITEM_ORDER = 0x0002;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sys_set_share);

        LinearLayout layout = (LinearLayout)findViewById(R.id.llll);
        registerForContextMenu(layout);


    }
        //标题栏菜单点击逻辑
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    //建立上下文菜单   长按屏幕显示
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfot){
        //菜单项   可添加子菜单项
        SubMenu subMenu = menu.addSubMenu("设置");
        subMenu.add(0,ITEM_OPTION,1,"设置参数");
        subMenu.add(0,ITEM_ORDER,2,"排行榜");
        super.onCreateContextMenu(menu,v,menuInfot);
    }
    //为上下文菜单绑定事件
    public boolean onContextItemSelected(MenuItem item){
        switch(item.getItemId()){
            case ITEM_OPTION:

                break;
            case ITEM_ORDER:

                break;
        }
        return super.onContextItemSelected(item);
    }
}
