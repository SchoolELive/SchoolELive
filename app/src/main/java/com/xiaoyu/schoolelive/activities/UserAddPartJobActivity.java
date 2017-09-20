package com.xiaoyu.schoolelive.activities;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.xiaoyu.schoolelive.R;
import com.xiaoyu.schoolelive.adapter.PartJobAdapter;
import com.xiaoyu.schoolelive.data.PartJob;
import com.xiaoyu.schoolelive.util.ConstantUtil;
import com.xiaoyu.schoolelive.util.HttpUtil;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

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
    int[] mAllowances = {ConstantUtil.ALLOWANCE_NONE, ConstantUtil.ALLOWANCE_NONE,
            ConstantUtil.ALLOWANCE_NONE, ConstantUtil.ALLOWANCE_NONE};
    //发布
    public static PartJob partJob;
    RecyclerView mPratJobRV;
    List<PartJob> mData = new ArrayList<>();
    PartJobAdapter mPartJobAdapter = new PartJobAdapter(UserAddPartJobActivity.this, mData);
    Button pub_partJob;
    private ProgressDialog progressDialog;
    private Handler handler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    progressDialog.dismiss();
                    Intent intent = new Intent(UserAddPartJobActivity.this, MainActivity.class);
                    intent.putExtra("toPartJob", 1);
                    startActivity(intent);
                    break;
            }
        }
    };


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
        pub_workContactMan = (TextView) findViewById(R.id.pub_workContactMan);//联系人
        pub_workContactNum = (TextView) findViewById(R.id.pub_workContactNum);//联系方式
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
                progressDialog = new ProgressDialog(UserAddPartJobActivity.this);
                progressDialog.setMessage("上传中...");
                progressDialog.setCancelable(true);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();
                getInputInfo();
                break;
        }
    }

    private void getInputInfo() {
        int workType = getWorkType(pub_workType.getSelectedItem().toString());
        int wagesType = getWagesType(pub_wagesType.getSelectedItem().toString());
        int wagesPay = getWagesPay(pub_wagesPay.getSelectedItem().toString());
        int workSexNeed = getSexNeed(pub_workSexNeed.getSelectedItem().toString());
        int[] workAllowances = getAllowanceType(mAllowances);
        // int workManNeed = Integer.parseInt(pub_workManNeed.getText().toString());
        String workPlace = String.valueOf(pub_workPlace.getText());
        String workName = String.valueOf(pub_workName.getText());
        String workWages = String.valueOf(pub_workWages.getText());
        String workNeed = String.valueOf(pub_workNeed.getText());//工作需求
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
        partJob.setWorkAllowance(workAllowances);

        //Intent intent = new Intent(UserAddPartJobActivity.this, MainActivity.class);
        // intent.putExtra("toPartJob", 1);

        //startActivity(intent);
        //Toast.makeText(getApplicationContext(),"cc",Toast.LENGTH_SHORT).show();
        if (workPlace == "" || workName == "" || workWages == "" || workNeed == "" || workContactMan == "" || workContactNum == "" || workStartDate == "" || workEndDate == "" || workStartHours == "" || workEndHours == "") {
            Toast.makeText(getApplication(), "请将您的资料填写完整", Toast.LENGTH_SHORT).show();
            return;
        } else {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddhhmmss");

            String job_id = simpleDateFormat.format(new Date());//兼职id
            RequestBody requestBody = new FormBody.Builder()
                    .add("job_id", job_id)
                    .add("work_name", workName)
                    .add("work_type", workType + "")
                    .add("wage_type", wagesType + "")
                    .add("wage_pay", wagesPay + "")//工资结算方式
                    .add("work_wage", workWages)
                    .add("work_need", workNeed)//工作需求
                    .add("work_place", workPlace)
                    .add("start_date", workStartDate)
                    .add("end_date", workEndDate)
                    .add("start_hours", workStartHours)
                    .add("end_hours", workEndHours)
                    .add("work_content_man", workContactMan)
                    .add("work_content_num", workContactNum)
                    .build();
            HttpUtil.sendHttpRequest(ConstantUtil.SERVICE_PATH + "get_job_info.php", requestBody, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.i("iii", e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Message message = new Message();
                    message.what = 1;//发布兼职信息成功
                    handler.sendMessage(message);

                }
            });


        }

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
        } else if (type.equals("食堂")) {
            return ConstantUtil.PartJob_SHITANG;
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

    private int[] getAllowanceType(int[] allowances) {
        for (int index = 0; index < mAllowances.length; index++) {
            if (allowance_Meal.isChecked()) {
                allowances[index] = ConstantUtil.ALLOWANCE_MEAL;
            } else if (allowance_Live.isChecked()) {
                allowances[index] = ConstantUtil.ALLOWANCE_LIVE;
            } else if (allowance_Traffic.isChecked()) {
                allowances[index] = ConstantUtil.ALLOWANCE_TRAFFIC;
            } else if (allowance_Other.isChecked()) {
                allowances[index] = ConstantUtil.ALLOWANCE_OTHER;
            }
        }
        return allowances;
    }

}
