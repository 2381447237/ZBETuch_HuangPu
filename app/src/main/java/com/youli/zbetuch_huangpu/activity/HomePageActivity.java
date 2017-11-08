package com.youli.zbetuch_huangpu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.youli.zbetuch_huangpu.R;


//首页
public class HomePageActivity extends BaseActivity implements View.OnClickListener{

    Intent intent;
    private ImageView ivHead;
    private Button workBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        initViews();
        workBtn = (Button) findViewById(R.id.main_layout_work_btn);
        workBtn.setOnClickListener(this);

    }


    private void initViews(){

        ivHead= (ImageView) findViewById(R.id.iv_activity_homepage_head);
        ivHead.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.iv_activity_homepage_head:
                intent=new Intent(this,AdminInfoActivity.class);
                startActivity(intent);
                break;

            //九宫格的Button按钮点击
            case R.id.main_layout_work_btn:
                intent=new Intent(this,FunctionListActivity.class);
                startActivity(intent);

                break;

        }

    }


}
