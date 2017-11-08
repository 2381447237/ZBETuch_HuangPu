package com.youli.zbetuch_huangpu.activity;

import android.os.Bundle;
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
    private TextView tvName;
    private TextView tvEmail;
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
        tvName= (TextView) findViewById(R.id.tv_admin_info_name);
        tvName.setText(aInfo.getNAME());
        tvEmail= (TextView) findViewById(R.id.tv_admin_info_email);
        tvEmail.setText(aInfo.getEMAIL());
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
