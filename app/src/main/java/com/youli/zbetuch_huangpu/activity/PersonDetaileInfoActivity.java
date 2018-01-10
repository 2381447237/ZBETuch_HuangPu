package com.youli.zbetuch_huangpu.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.youli.zbetuch_huangpu.R;
import com.youli.zbetuch_huangpu.adapter.MyFragmentPagerAdapter;
import com.youli.zbetuch_huangpu.entity.PersonListInfo;
import com.youli.zbetuch_huangpu.fragment.BaseInfoFragment;
import com.youli.zbetuch_huangpu.fragment.BtInfoFragment;
import com.youli.zbetuch_huangpu.fragment.CyInfoFragment;
import com.youli.zbetuch_huangpu.fragment.LdjlFragment;
import com.youli.zbetuch_huangpu.fragment.PxInfoFragment;
import com.youli.zbetuch_huangpu.fragment.SameHjInfoFragment;
import com.youli.zbetuch_huangpu.fragment.SbInfoFragment;
import com.youli.zbetuch_huangpu.fragment.StudyJlFragment;
import com.youli.zbetuch_huangpu.fragment.YbInfoFragment;
import com.youli.zbetuch_huangpu.view.MyViewPager;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by liutao on 2018/1/5.
 *
 * 个人信息界面
 */

public class PersonDetaileInfoActivity extends FragmentActivity {


    private PersonListInfo pInfo;

    private MyViewPager viewPager;
    private List<Fragment> fragmentList;
    private TabLayout tl;
    private String [] title={"基本信息","同户籍信息","劳动经历","学习经历","补贴信息","医保信息","培训信息","创业信息","社保信息"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_detail_info);

        pInfo=(PersonListInfo) getIntent().getSerializableExtra("pInfo");



        viewPager= (MyViewPager) findViewById(R.id.vp_person_detail_info);
        tl= (TabLayout) findViewById(R.id.tl_person_detail_info);
        fragmentList=new ArrayList<>();
        fragmentList.add(new BaseInfoFragment());//基本信息
        fragmentList.add(new SameHjInfoFragment());//同户籍信息
        fragmentList.add(new LdjlFragment());//劳动经历
        fragmentList.add(StudyJlFragment.newInstance(pInfo));//学习经历
        fragmentList.add(BtInfoFragment.newInstance(pInfo));//补贴信息
        fragmentList.add(new YbInfoFragment());//医保信息
        fragmentList.add(new PxInfoFragment());//培训信息
        fragmentList.add(new CyInfoFragment());//创业信息
        fragmentList.add(new SbInfoFragment());//社保信息

        FragmentManager fm=getSupportFragmentManager();
        MyFragmentPagerAdapter fpAdapter=new MyFragmentPagerAdapter(fm,fragmentList);
        viewPager.setAdapter(fpAdapter);
        viewPager.setOffscreenPageLimit(9);

        tl.setupWithViewPager(viewPager);

        for(int i=0;i<tl.getTabCount();i++){
            TabLayout.Tab tab=tl.getTabAt(i);
            tab.setCustomView(getTabView(i));
        }
    }

    private View getTabView(int position){

        View view= LayoutInflater.from(this).inflate(R.layout.tab_item,null,false);

        ImageView iv= (ImageView) view.findViewById(R.id.tab_item_iv);
        TextView tv= (TextView) view.findViewById(R.id.tab_item_tv);
        //iv.setImageResource(R.drawable.sel_image);
        tv.setText(title[position]);
        return view;

    }

}
