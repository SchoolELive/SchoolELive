package com.xiaoyu.schoolelive.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.xiaoyu.schoolelive.R;
import com.xiaoyu.schoolelive.util.ConstantUtil;

/**
 * Created by lenovo on 2017/8/14.
 */

public class PartJobInfoActivity extends AppCompatActivity {
    //接收fragment传过来的数据
    TextView workType;
    TextView workName;
    TextView workPlace;
    TextView workWages;
    TextView wagesPay;
    TextView wagesType;
    TextView workStartDate;
    TextView workEndDate;

    //服务器拉下来的数据
    TextView infoPublishDate;
    TextView workManNeed;
    TextView workSexNeed;
    TextView workStartHours;
    TextView workEndHours;
    TextView pubWorkApartment;
    TextView workNeed;
    TextView workContactMan;
    TextView workContactNum;

    Intent intent;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partjob_info);
        intent = getIntent();
        findById();
        initData(intent);
    }

    private void initData(Intent intent) {
        setWorkType(intent.getIntExtra("tmp_workType", 0));
        setWagesType(intent.getIntExtra("tmp_wagesType", 0));
        setWagesPaid(intent.getIntExtra("tmp_wagesPay", 0));
        workName.setText(intent.getStringExtra("tmp_workName"));
        workPlace.setText(intent.getStringExtra("tmp_workPlace"));
        workStartDate.setText(intent.getStringExtra("tmp_workStartDate"));
        workEndDate.setText(intent.getStringExtra("tmp_workEndDate"));
        workWages.setText(intent.getStringExtra("tmp_workWages"));
        workNeed.setText(intent.getStringExtra("tmp_workNeed"));
        workContactMan.setText(intent.getStringExtra("tmp_work_content_man"));
        workContactNum.setText(intent.getStringExtra("tmp_work_content_num"));
        workStartHours.setText(intent.getStringExtra("tmp_workStartHours"));
        workEndHours.setText(intent.getStringExtra("tmp_workEndHours"));
        infoPublishDate.setText(intent.getStringExtra("tmp_post_time"));
    }

    private void findById() {
        infoPublishDate = (TextView)findViewById(R.id.infoPublishDate);
        workType = (TextView) findViewById(R.id.workType);
        workName = (TextView) findViewById(R.id.workName);
        workPlace = (TextView) findViewById(R.id.workPlace);
        workWages = (TextView) findViewById(R.id.workWages);
        wagesPay = (TextView) findViewById(R.id.wagesPay);
        wagesType = (TextView) findViewById(R.id.wagesType);
        workStartDate = (TextView) findViewById(R.id.workStartDate);
        workEndDate = (TextView) findViewById(R.id.workEndDate);
        workNeed = (TextView)findViewById(R.id.workNeed);
        workContactMan = (TextView)findViewById(R.id.workContactMan);
        workContactNum = (TextView)findViewById(R.id.workContactNum);
        workStartHours = (TextView)findViewById(R.id.workStartHours);
        workEndHours = (TextView)findViewById(R.id.workEndHours);
    }

    public void setWorkType(int type) {
        if (type == ConstantUtil.PartJob_BAOAN) {
            workType.setText(R.string.baoan);
            return;
        }
        if (type == ConstantUtil.PartJob_FACHUANDAN) {
            workType.setText(R.string.fachuandan);
            return;
        }
        if (type == ConstantUtil.PartJob_FUWUYUAN) {
            workType.setText(R.string.fuwuyuan);
            return;
        }
        if (type == ConstantUtil.PartJob_GONGCHANG) {
            workType.setText(R.string.gongchang);
            return;
        }
        if (type == ConstantUtil.PartJob_JIAJIAO) {
            workType.setText(R.string.jiajiao);
            return;
        }
        if (type == ConstantUtil.PartJob_KUAIDI) {
            workType.setText(R.string.kuaidi);
            return;
        }
        if (type == ConstantUtil.PartJob_TUIGUANG) {
            workType.setText(R.string.tuiguang);
            return;
        }
        if (type == ConstantUtil.PartJob_WAIMAI) {
            workType.setText(R.string.waimai);
            return;
        }
        if (type == ConstantUtil.PartJob_WANGLUO) {
            workType.setText(R.string.wangluo);
            return;
        }
        if (type == ConstantUtil.PartJob_XIAOSHOU) {
            workType.setText(R.string.xiaoshou);
            return;
        }
        if (type == ConstantUtil.PartJob_XIAOYUAN) {
            workType.setText(R.string.xiaoyuan);
            return;
        }
        if (type == ConstantUtil.PartJob_YANYUAN) {
            workType.setText(R.string.yanyuan);
            return;
        }
        if (type == ConstantUtil.PartJob_QITA) {
            workType.setText(R.string.qita);
            return;
        }
    }

    public void setWagesType(int type) {
        if (type == ConstantUtil.WagesType_PERHOUR) {
            wagesType.setText(R.string.perhour);
            return;
        }
        if (type == ConstantUtil.WagesType_PERDAY) {
            wagesType.setText(R.string.perday);
            return;
        }
        if (type == ConstantUtil.WagesType_PERMONTH) {
            wagesType.setText(R.string.permonth);
            return;
        }
        if (type == ConstantUtil.WagesType_PERITEM) {
            wagesType.setText(R.string.peritem);
            return;
        }
        if (type == ConstantUtil.WagesType_PERPIECE) {
            wagesType.setText(R.string.perpiece);
            return;
        }
    }

    public void setWagesPaid(int type) {
        if (type == ConstantUtil.WagesPay_DAY) {
            wagesPay.setText(R.string.daypaid);
            return;
        }
        if (type == ConstantUtil.WagesPay_MONTH) {
            wagesPay.setText(R.string.monthpaid);
            return;
        }
        if (type == ConstantUtil.WagesPay_WEEK) {
            wagesPay.setText(R.string.weekpaid);
            return;
        }
        if (type == ConstantUtil.WagesPay_AFTERWORK) {
            wagesPay.setText(R.string.afterworkpaid);
            return;
        }
    }
}
