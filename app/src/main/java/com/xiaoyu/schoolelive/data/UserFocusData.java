package com.xiaoyu.schoolelive.data;

import android.media.Image;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiaoyu.schoolelive.custom.CircleImageView;
/**
 * Created by Administrator on 2017/7/16.
 */
public class UserFocusData {
    private int user_focus_image;
    private String user_focus_fname;
    private int user_focus_sex;

    public UserFocusData(){

    }
    public int getUser_focus_image() {
        return user_focus_image;
    }

    public int getUser_focus_sex() {
        return user_focus_sex;
    }

    public String getUser_focus_fname() {
        return user_focus_fname;
    }

    public void setUser_focus_fname(String user_focus_fname) {
        this.user_focus_fname = user_focus_fname;
    }

    public void setUser_focus_image(int user_focus_image) {
        this.user_focus_image = user_focus_image;
    }

    public void setUser_focus_sex(int user_focus_sex) {
        this.user_focus_sex = user_focus_sex;
    }
}
