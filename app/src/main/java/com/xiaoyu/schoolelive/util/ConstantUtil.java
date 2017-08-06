package com.xiaoyu.schoolelive.util;

/**
 * Created by NeekChaw on 2017-07-31.
 */

public class ConstantUtil {
    public static String SERVICE_PATH = "http://139.199.181.68/xiaoyu/";

    /**
     * 兼职类别
     */
    public static int PartJob_YANYUAN = 1;
    public static int PartJob_XIAOSHOU = 2;
    public static int PartJob_BAOAN = 3;
    public static int PartJob_FACHUANDAN = 4;
    public static int PartJob_WAIMAI = 5;
    public static int PartJob_KUAIDI = 6;
    public static int PartJob_TUIGUANG = 7;
    public static int PartJob_WANGLUO = 8;
    public static int PartJob_JIAJIAO = 9;
    public static int PartJob_GONGCHANG = 10;
    public static int PartJob_FUWUYUAN = 11;
    public static int PartJob_XIAOYUAN = 12;
    public static int PartJob_QITA = 13;

    /**
     * 工资计算方式
     */
    //每小时
    public static int WagesType_PERHOUR = 0;
    //每天
    public static int WagesType_PERDAY = 1;
    //每月
    public static int WagesType_PERMONTH = 2;
    //每件
    public static int WagesType_PERPIECE = 3;
    //每单
    public static int WagesType_PERITEM = 4;


    /**
     * 工资结算方式
     */
    //日结
    public static int WagesPay_DAY = 1;
    //周结
    public static int WagesPay_WEEK = 2;
    //月结
    public static int WagesPay_MONTH = 3;
    //完工结算
    public static int WagesPay_AFTERWORK = 4;
}
