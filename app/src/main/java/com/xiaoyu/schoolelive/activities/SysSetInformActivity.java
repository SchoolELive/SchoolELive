package com.xiaoyu.schoolelive.activities;

import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.xiaoyu.schoolelive.base.BaseSlideBack;

import xiaoyu.com.schoolelive.R;


public class SysSetInformActivity extends BaseSlideBack {
    private CheckBox checkBox1,checkBox2,checkBox3;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sys_set_inform);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("消息推送");

        checkBox1 = (CheckBox)findViewById(R.id.checkbox1);
        checkBox2 = (CheckBox)findViewById(R.id.checkbox2);
        checkBox3 = (CheckBox)findViewById(R.id.checkbox3);

        checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkBox1.isChecked())
                    Toast.makeText(SysSetInformActivity.this,"选择了第1个",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(SysSetInformActivity.this,"取消了第1个",Toast.LENGTH_LONG).show();

            }
        });
        checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkBox2.isChecked())
                    Toast.makeText(SysSetInformActivity.this,"选择了第2个",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(SysSetInformActivity.this,"取消了第2个",Toast.LENGTH_LONG).show();

            }
        });
        checkBox3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkBox3.isChecked())
                    Toast.makeText(SysSetInformActivity.this,"选择了第三个",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(SysSetInformActivity.this,"取消了第三个",Toast.LENGTH_LONG).show();

            }
        });

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
}
