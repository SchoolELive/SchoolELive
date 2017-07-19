package com.xiaoyu.schoolelive.data;

/**
 * Created by NeekChaw on 2017-07-16.
 */

public class Publish {
    String name; //评论者
    String content; //发布内容
    int head;//头像
    String ymd;//年月日
    String date;//小时分钟

    int like_count, comment_count, share_count;//点赞评论数量

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getHead() {
        return head;
    }

    public void setHead(int head) {
        this.head = head;
    }

    public String getYmd() {
        return ymd;
    }

    public void setYmd(String ymd) {
        this.ymd = ymd;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getLike_count() {
        return like_count;
    }

    public void setLike_count(int like_count) {
        this.like_count = like_count;
    }

    public int getComment_count() {
        return comment_count;
    }

    public void setComment_count(int comment_count) {
        this.comment_count = comment_count;
    }

    public int getShare_count() {
        return share_count;
    }

    public void setShare_count(int share_count) {
        this.share_count = share_count;
    }


}
