package com.youli.zbetuch_huangpu.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.youli.zbetuch_huangpu.R;
import com.youli.zbetuch_huangpu.entity.AdminInfo;
import com.youli.zbetuch_huangpu.view.CircleImageView;

/**
 * 作者: zhengbin on 2017/9/22.
 * <p>
 * 邮箱:2381447237@qq.com
 * <p>
 * github:2381447237
 */

public class AdminInfoActivity extends BaseActivity implements View.OnClickListener{

    private ImageView ivBack;
    private CircleImageView ivHead;//头像
    private TextView tvOperatorNo;//操作员工号
    private TextView tvName;//姓名
    private TextView tvPhone;//联系电话
    private TextView tvEmail;//电子邮箱
    private TextView tvDeviceNo;//设备号
    private TextView tvState;//状态
    private TextView tvSfz;//身份证
    private TextView tvDepart;//所属部门
    private TextView tvStreet;//街道
    private TextView tvIMEI;//IMEI

    private AdminInfo aInfo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_info);

        aInfo=(AdminInfo)getIntent().getSerializableExtra("ADMININFO");

        initViews();
    }

    private void initViews(){

        ivBack= (ImageView) findViewById(R.id.iv_admin_info_back);
        ivBack.setOnClickListener(this);
        ivHead= (CircleImageView) findViewById(R.id.iv_admin_info_head);
        tvOperatorNo= (TextView) findViewById(R.id.tv_admin_info_operator_no);
        tvOperatorNo.setText(aInfo.getINPUT_CODE());
        tvName= (TextView) findViewById(R.id.tv_admin_info_name);
        tvName.setText(aInfo.getNAME());
        tvPhone= (TextView) findViewById(R.id.tv_admin_info_phone);
        tvPhone.setText(aInfo.getPHONE());
        tvEmail= (TextView) findViewById(R.id.tv_admin_info_email);
        tvEmail.setText(aInfo.getEMAIL());

        tvDeviceNo= (TextView) findViewById(R.id.tv_admin_info_device_no);
        tvDeviceNo.setText(aInfo.getDEVICE_NUMBER());
        tvState= (TextView) findViewById(R.id.tv_admin_info_state);

        if(!aInfo.isSTOP()){
            tvState.setText("启用");
        }else{
            tvState.setText("停用");
        }

        tvSfz= (TextView) findViewById(R.id.tv_admin_info_sfz);
        tvSfz.setText(aInfo.getSFZ());
        tvDepart= (TextView) findViewById(R.id.tv_admin_info_department);
        tvDepart.setText(aInfo.getDEPT());
        tvStreet= (TextView) findViewById(R.id.tv_admin_info_street);
        tvStreet.setText(aInfo.getJD());
        tvIMEI= (TextView) findViewById(R.id.tv_admin_info_imei);
        tvIMEI.setText(aInfo.getIMEI());
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.iv_admin_info_back:

                finish();

                break;


        }

    }
}
