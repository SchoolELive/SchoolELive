package com.xiaoyu.schoolelive.data;

/**
 * Created by Administrator on 2017/8/18.
 */

public class ShopData {
    int shop_image;
    String shop_name;
    String shop_price;

    public int getImage() {
        return shop_image;
    }

    public String getName() {
        return shop_name;
    }

    public String getPrice() {
        return shop_price;
    }

    public void setImage(int image) {
        this.shop_image = image;
    }

    public void setName(String name) {
        this.shop_name = name;
    }

    public void setPrice(String price) {
        this.shop_price = price;
    }

}
