package com.youli.zbetuch_huangpu.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.youli.zbetuch_huangpu.R;
import com.youli.zbetuch_huangpu.utils.AlertDialogUtils;

/**
 * 作者: zhengbin on 2017/11/13.
 * <p>
 * 邮箱:2381447237@qq.com
 * <p>
 * github:2381447237
 *
 * 登录超时的对话框
 */

public class OvertimeDialogActivity extends BaseActivity{

    private AlertDialogUtils adu;
    private Context mContext=OvertimeDialogActivity.this;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_overtime_dialog);

         initViews();
    }

    private void initViews(){

        adu=new AlertDialogUtils(this,R.layout.overtime_dialog,R.style.dialog);

        adu.showAlertDialog();

        Button btnSure= (Button) adu.getAduView().findViewById(R.id.btn_overtime_sure);
        btnSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adu.dismissAlertDialog();
                ActivityController.finishAll();
                Intent intent=new Intent(mContext,LoginActivity.class);
                startActivity(intent);
            }
        });

    }

}
