package com.youli.zbetuch_huangpu.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.youli.zbetuch_huangpu.R;

/**
 * 作者: zhengbin on 2017/9/21.
 * <p>
 * 邮箱:2381447237@qq.com
 * <p>
 * github:2381447237
 *
 * 自定义对话框工具类
 */

public class AlertDialogUtils{


    private Context context;
    private int layout;
    private View view;
    private AlertDialog dialog;
    private int style;


    public AlertDialogUtils(Context context, int layout,int style) {
        this.context = context;
        this.layout = layout;
        this.style=style;
    }


    public void showAlertDialog(){

        AlertDialog.Builder builder=new AlertDialog.Builder(context,style);

        view= LayoutInflater.from(context).inflate(layout,null);

        builder.setView(view);

        dialog=builder.create();

        dialog.setCancelable(false);

        dialog.show();

    }

   public View getAduView(){

        return view!=null?view:null;
    }

    public void dismissAlertDialog(){
        dialog.dismiss();
    }

}
