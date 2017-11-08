package com.youli.zbetuch_huangpu.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.youli.zbetuch_huangpu.R;

/**
 * 作者: zhengbin on 2017/9/22.
 * <p>
 * 邮箱:2381447237@qq.com
 * <p>
 * github:2381447237
 */

public class AdminInfoActivity extends BaseActivity implements View.OnClickListener{

    private ImageView ivBack;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_info);

        initViews();
    }

    private void initViews(){

        ivBack= (ImageView) findViewById(R.id.iv_admin_info_back);
        ivBack.setOnClickListener(this);

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
