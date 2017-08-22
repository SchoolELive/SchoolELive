package com.xiaoyu.schoolelive.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.format.Time;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.xiaoyu.schoolelive.R;
import com.xiaoyu.schoolelive.adapter.PartJobAdapter;
import com.xiaoyu.schoolelive.data.PartJob;
import com.xiaoyu.schoolelive.util.ConstantUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2017/8/16.
 */

public class UserAddPartJobActivity extends AppCompatActivity implements View.OnClickListener {
    //时间范围
    Button btn_workStartDate;
    Button btn_workEndDate;
    Button btn_workStartHours;
    Button btn_workEndHours;
    //获取系统时间
    Time t;
    int year;
    int month;
    int date;
    int hour;
    int minute;
    //获取发布界面数据
    TextView pub_workName;
    TextView pub_workWages;
    TextView pub_workPlace;
    TextView pub_workNeed;
    TextView pub_workManNeed;
    TextView pub_workContactMan;
    TextView pub_workContactNum;
    Spinner pub_workType;
    Spinner pub_wagesType;
    Spinner pub_wagesPay;
    Spinner pub_workSexNeed;
    CheckBox allowance_Meal;
    CheckBox allowance_Live;
    CheckBox allowance_Traffic;
    CheckBox allowance_Other;
    //发布
    public static PartJob partJob;
    RecyclerView mPratJobRV;
    List<PartJob> mData = new ArrayList<>();
    PartJobAdapter mPartJobAdapter = new PartJobAdapter(UserAddPartJobActivity.this, mData);
    Button pub_partJob;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partjob_publish);
        findById();
        setListener();
        getSysTime();
    }

    private void findById() {
        pub_partJob = (Button) findViewById(R.id.pub_partJob);

        btn_workStartDate = (Button) findViewById(R.id.pub_workStartDate);
        btn_workEndDate = (Button) findViewById(R.id.pub_workEndDate);
        btn_workStartHours = (Button) findViewById(R.id.pub_workStartHours);
        btn_workEndHours = (Button) findViewById(R.id.pub_workEndHours);

        pub_workName = (TextView) findViewById(R.id.pub_workName);
        pub_workWages = (TextView) findViewById(R.id.pub_workWages);
        pub_workPlace = (TextView) findViewById(R.id.pub_workPlace);
        pub_workNeed = (TextView) findViewById(R.id.pub_workNeed);
        pub_workManNeed = (TextView) findViewById(R.id.pub_workManNeed);
        pub_workContactMan = (TextView) findViewById(R.id.pub_workContactMan);
        pub_workContactNum = (TextView) findViewById(R.id.pub_workContactNum);
        pub_workType = (Spinner) findViewById(R.id.workTypeOptions);
        pub_wagesType = (Spinner) findViewById(R.id.wagesTypeOptions);
        pub_wagesPay = (Spinner) findViewById(R.id.wagesPayOptions);
        pub_workSexNeed = (Spinner) findViewById(R.id.pub_workSexNeed);
        allowance_Meal = (CheckBox) findViewById(R.id.allowance_Meal);
        allowance_Live = (CheckBox) findViewById(R.id.allowance_Live);
        allowance_Traffic = (CheckBox) findViewById(R.id.allowance_Traffic);
        allowance_Other = (CheckBox) findViewById(R.id.allowance_Other);

    }

    private void setListener() {
        btn_workStartDate.setOnClickListener(this);
        btn_workEndDate.setOnClickListener(this);
        btn_workStartHours.setOnClickListener(this);
        btn_workEndHours.setOnClickListener(this);
        pub_partJob.setOnClickListener(this);
    }

    private void getSysTime() {
        Time t = new Time(); // or Time t=new Time("GMT+8"); 加上Time Zone资料
        t.setToNow(); // 取得系统时间。
        year = t.year;
        month = t.month;
        date = t.monthDay;
        hour = t.hour;    // 0-23
        minute = t.minute;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pub_workStartDate:
                new DatePickerDialog(UserAddPartJobActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        btn_workStartDate.setText(String.format("%d.%d.%d", year, monthOfYear + 1, dayOfMonth) + "");
                    }
                }, year, month, date).show();
                break;
            case R.id.pub_workEndDate:
                new DatePickerDialog(UserAddPartJobActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        btn_workEndDate.setText(String.format("%d.%d.%d", year, monthOfYear + 1, dayOfMonth) + "");
                    }
                }, year, month, date).show();
                break;
            case R.id.pub_workStartHours:
                new TimePickerDialog(UserAddPartJobActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        btn_workStartHours.setText(String.format("%d:%d", hourOfDay, minute));
                    }
                    //0,0指的是时间，true表示是否为24小时，true为24小时制
                }, 0, 0, true).show();
                break;
            case R.id.pub_workEndHours:
                new TimePickerDialog(UserAddPartJobActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        btn_workEndHours.setText(String.format("%d:%d", hourOfDay, minute));
                    }
                    //0,0指的是时间，true表示是否为24小时，true为24小时制
                }, 0, 0, true).show();
                break;
            case R.id.pub_partJob:
                getInputInfo();
                break;
        }
    }

    private void getInputInfo() {
        int workType = getWorkType(pub_workType.getSelectedItem().toString());
        int wagesType = getWagesType(pub_wagesType.getSelectedItem().toString());
        int wagesPay = getWagesPay(pub_wagesPay.getSelectedItem().toString());
        int workSexNeed = getSexNeed(pub_workSexNeed.getSelectedItem().toString());
        int workAllowances = getAllowanceType();
        // int workManNeed = Integer.parseInt(pub_workManNeed.getText().toString());
        String workPlace = String.valueOf(pub_workPlace.getText());
        String workName = String.valueOf(pub_workName.getText());
        String workWages = String.valueOf(pub_workWages.getText());
        String workNeed = String.valueOf(pub_workNeed.getText());
        String workContactNum = String.valueOf(pub_workContactNum.getText());
        String workContactMan = String.valueOf(pub_workContactMan.getText());
        String workStartDate = String.valueOf(btn_workStartDate.getText());
        String workEndDate = String.valueOf(btn_workEndDate.getText());
        String workStartHours = String.valueOf(btn_workStartHours.getText());
        String workEndHours = String.valueOf(btn_workEndHours.getText());

        partJob = new PartJob();
        partJob.setWagesPay(wagesPay);
        partJob.setWagesType(wagesType);
        partJob.setWorkName(workName);
        partJob.setWorkPlace(workPlace);
        partJob.setWorkWages(workWages);
        partJob.setWorkStartDate(workStartDate);
        partJob.setWorkEndDate(workEndDate);
        partJob.setWorkType(workType);

        Intent intent = new Intent(UserAddPartJobActivity.this, MainActivity.class);
        intent.putExtra("toPartJob", 1);

        startActivity(intent);
    }

    private int getWorkType(String type) {
        if (type.equals("其他")) {
            return ConstantUtil.PartJob_QITA;
        } else if (type.equals("演员")) {
            return ConstantUtil.PartJob_YANYUAN;
        } else if (type.equals("销售")) {
            return ConstantUtil.PartJob_XIAOSHOU;
        } else if (type.equals("安保")) {
            return ConstantUtil.PartJob_BAOAN;
        } else if (type.equals("派单")) {
            return ConstantUtil.PartJob_FACHUANDAN;
        } else if (type.equals("外卖")) {
            return ConstantUtil.PartJob_WAIMAI;
        } else if (type.equals("快递")) {
            return ConstantUtil.PartJob_KUAIDI;
        } else if (type.equals("推广")) {
            return ConstantUtil.PartJob_TUIGUANG;
        } else if (type.equals("网络")) {
            return ConstantUtil.PartJob_WANGLUO;
        } else if (type.equals("家教")) {
            return ConstantUtil.PartJob_JIAJIAO;
        } else if (type.equals("工厂")) {
            return ConstantUtil.PartJob_GONGCHANG;
        } else if (type.equals("服务员")) {
            return ConstantUtil.PartJob_FUWUYUAN;
        } else if (type.equals("校内")) {
            return ConstantUtil.PartJob_XIAOYUAN;
        }
        return 0;
    }

    private int getWagesType(String type) {
        if (type.equals("元/小时")) {
            return ConstantUtil.WagesType_PERHOUR;
        } else if (type.equals("元/天")) {
            return ConstantUtil.WagesType_PERDAY;
        } else if (type.equals("元/月")) {
            return ConstantUtil.WagesType_PERMONTH;
        } else if (type.equals("元/单")) {
            return ConstantUtil.WagesType_PERITEM;
        } else if (type.equals("元/件")) {
            return ConstantUtil.WagesType_PERPIECE;
        }
        return 0;
    }

    private int getWagesPay(String type) {
        if (type.equals("日结")) {
            return ConstantUtil.WagesPay_DAY;
        } else if (type.equals("周结")) {
            return ConstantUtil.WagesPay_WEEK;
        } else if (type.equals("月结")) {
            return ConstantUtil.WagesPay_MONTH;
        } else if (type.equals("完工结算")) {
            return ConstantUtil.WagesPay_AFTERWORK;
        }
        return 0;
    }

    private int getSexNeed(String sex) {
        if (sex.equals("男")) {
            return ConstantUtil.MAN;
        } else if (sex.equals("女")) {
            return ConstantUtil.WOMEN;
        } else if (sex.equals("不限")) {
            return ConstantUtil.ALL_SEX;
        }
        return 0;
    }

    private int getAllowanceType() {
        if (allowance_Meal.isChecked()) {
            return ConstantUtil.ALLOWANCE_MEAL;
        } else if (allowance_Live.isChecked()) {
            return ConstantUtil.ALLOWANCE_LIVE;
        } else if (allowance_Traffic.isChecked()) {
            return ConstantUtil.ALLOWANCE_TRAFFIC;
        } else if (allowance_Other.isChecked()) {
            return ConstantUtil.ALLOWANCE_OTHER;

        }
        return 0;
    }
}
