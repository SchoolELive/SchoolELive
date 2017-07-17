package com.xiaoyu.schoolelive.activities;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.xiaoyu.schoolelive.base.BaseSlideBack;

import xiaoyu.com.schoolelive.R;

/**
 * Created by Administrator on 2017/7/11.
 */

public class FindActivity extends BaseSlideBack {
    private EditText editText;
    private ImageButton backButton;
    private Button findButton;
    private boolean isFirst = true;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);

        editText = (EditText)findViewById(R.id.find_edit);
        backButton = (ImageButton)findViewById(R.id.find_back);
        findButton = (Button)findViewById(R.id.find_button);

        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        findButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String str = editText.getText().toString();
                Toast.makeText(FindActivity.this,str,Toast.LENGTH_LONG).show();
            }
        });
        editText.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (isFirst){
                    editText.setText("");
                    isFirst = !isFirst ;
                }
                return false;
            }
        });
    }
}