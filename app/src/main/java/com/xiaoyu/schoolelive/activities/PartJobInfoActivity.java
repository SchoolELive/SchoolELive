package com.xiaoyu.schoolelive.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.xiaoyu.schoolelive.R;
import com.xiaoyu.schoolelive.util.ConstantUtil;
import com.xiaoyu.schoolelive.util.ShowShareUtil;

/**
 * Created by lenovo on 2017/8/14.
 */

public class PartJobInfoActivity extends AppCompatActivity implements View.OnClickListener {
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
    TextView hasJoinMan;

    //底部按钮
    Button mBtnShare;
    Button mBtnConnect;
    Button mBtnEnroll;

    //是否报名过
    public static boolean IS_ENROLL = false;

    Intent intent;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partjob_info);
        intent = getIntent();
        findById();
        setListener();
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
        infoPublishDate = (TextView) findViewById(R.id.infoPublishDate);
        workType = (TextView) findViewById(R.id.workType);
        workName = (TextView) findViewById(R.id.workName);
        workPlace = (TextView) findViewById(R.id.workPlace);
        workWages = (TextView) findViewById(R.id.workWages);
        wagesPay = (TextView) findViewById(R.id.wagesPay);
        wagesType = (TextView) findViewById(R.id.wagesType);
        workStartDate = (TextView) findViewById(R.id.workStartDate);
        workEndDate = (TextView) findViewById(R.id.workEndDate);
        workNeed = (TextView) findViewById(R.id.workNeed);
        workContactMan = (TextView) findViewById(R.id.workContactMan);
        workContactNum = (TextView) findViewById(R.id.workContactNum);
        workStartHours = (TextView) findViewById(R.id.workStartHours);
        workManNeed = (TextView) findViewById(R.id.workManNeed);
        hasJoinMan = (TextView) findViewById(R.id.hasJoinMan);
        workEndHours = (TextView) findViewById(R.id.workEndHours);
        mBtnShare = (Button) findViewById(R.id.btn_sharejob);
        mBtnConnect = (Button) findViewById(R.id.btn_connectjob);
        mBtnEnroll = (Button) findViewById(R.id.btn_enrolljob);
    }

    public void setListener() {
        mBtnShare.setOnClickListener(this);
        mBtnConnect.setOnClickListener(this);
        mBtnEnroll.setOnClickListener(this);
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
        if (type == ConstantUtil.PartJob_SHITANG) {
            workType.setText(R.string.shitang);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sharejob:
                ShowShareUtil.showShare(this);
                break;
            case R.id.btn_connectjob:
                //告诉系统我要打开拨号界面，并把要拨的号显示在拨号界面上，由用户决定是否要拨打。
                call(Intent.ACTION_DIAL);
                break;
            case R.id.btn_enrolljob:
                //报名登记
                enroll();
                break;
        }
    }

    public void call(String action) {
        String phone = workContactNum.getText().toString();
        if (phone != null && phone.trim().length() > 0) {
            //这里"tel:"+电话号码 是固定格式，系统一看是以"tel:"开头的，就知道后面应该是电话号码。
            Intent intent = new Intent(action, Uri.parse("tel:" + phone.trim()));
            startActivity(intent);//调用上面这个intent实现拨号
        } else {
            Toast.makeText(this, "电话号码不能为空", Toast.LENGTH_LONG).show();
        }
    }

    public void enroll() {
        if (!IS_ENROLL) {
            String[] connect = {"电话号码:", "姓        名:"};
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater layoutInflater = LayoutInflater.from(this);
            //自定义对话框标题栏
            final View mTitleView = layoutInflater.inflate(R.layout.custom_partjob_dialog, null);
            builder.setCustomTitle(mTitleView)
                    .setItems(connect, null)
                    .setPositiveButton("返回修改", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //跳转至个人信息修改界面
                        }
                    })
                    .setNegativeButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(PartJobInfoActivity.this);
                            builder1.setMessage("报名成功!")
                                    .show();
                            IS_ENROLL = true;
                            hasJoinMan.setText(Integer.valueOf(hasJoinMan.getText().toString()) + 1 + "");
                        }
                    })
                    .show();
        } else {
            AlertDialog.Builder builder2 = new AlertDialog.Builder(PartJobInfoActivity.this);
            builder2.setMessage("您已经报名成功,请勿重复报名");
            AlertDialog alertDialog = builder2.show();
            WindowManager.LayoutParams params =
                    alertDialog.getWindow().getAttributes();
            params.alpha = 0.9f;
            alertDialog.getWindow().setAttributes(params);
        }
    }
}
