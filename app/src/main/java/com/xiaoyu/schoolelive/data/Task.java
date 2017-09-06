package com.xiaoyu.schoolelive.data;

/**
 * Created by Administrator on 2017/8/24.
 */

public class Task {
    int goldTask_image;
    String goldTask_name;
    String GoldTask_content;

    public int getGoldTask_image() {
        return goldTask_image;
    }

    public String getGoldTask_content() {
        return GoldTask_content;
    }

    public String getGoldTask_name() {
        return goldTask_name;
    }

    public void setGoldTask_content(String goldTask_content) {
        GoldTask_content = goldTask_content;
    }

    public void setGoldTask_image(int goldTask_image) {
        this.goldTask_image = goldTask_image;
    }

    public void setGoldTask_name(String goldTask_name) {
        this.goldTask_name = goldTask_name;
    }
}
