package com.youli.zbetuch_huangpu.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.youli.zbetuch_huangpu.R;

/**
 * Created by liutao on 2018/1/5.
 *
 * 基本信息
 */

public class BaseInfoFragment extends BaseVpFragment{

    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view=LayoutInflater.from(getContext()).inflate(R.layout.fragment_base_info,container,false);

        isViewCreated=true;//标记不能少

        return view;
    }

    @Override
    public void lazyLoadData() {

        if(isViewCreated){//逻辑都写这个里面

            Log.e("2018-1-5","1111111111111111111111111111111111111111");

        }

    }
}
