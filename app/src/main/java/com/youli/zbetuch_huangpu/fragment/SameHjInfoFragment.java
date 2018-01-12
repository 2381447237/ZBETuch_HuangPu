package com.youli.zbetuch_huangpu.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.youli.zbetuch_huangpu.R;
import com.youli.zbetuch_huangpu.adapter.CommonAdapter;
import com.youli.zbetuch_huangpu.entity.CommonViewHolder;
import com.youli.zbetuch_huangpu.entity.HouseholdInfo;
import com.youli.zbetuch_huangpu.entity.PersonListInfo;
import com.youli.zbetuch_huangpu.utils.MyOkHttpUtils;
import com.youli.zbetuch_huangpu.view.MyListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

/**
 * Created by liutao on 2018/1/5.
 *
 * 同户籍信息
 */

public class SameHjInfoFragment extends BaseVpFragment{
    private PersonListInfo pInfo;
    private ListView lv;
    private View view;
    private CommonAdapter adapderm;

    private List<HouseholdInfo> data=new ArrayList<HouseholdInfo>();
    private List<HouseholdInfo.FamilyAddressInfoList> hujiData=new ArrayList<>();
    private List<HouseholdInfo.FamilyAddressInfoList> lianxiData=new ArrayList<>();

    private final int SUCCESS_HUJI=10000;
    private final int SUCCESS_JUZHU=10001;
    private final int SUCCESS_PINFO=10002;
    private  final int SUCCESS_NODATA=10003;
    private final int  PROBLEM=10004;

    private TextView tv_wu;



    public static final SameHjInfoFragment newInstance(PersonListInfo zjhm){
        SameHjInfoFragment fragment=new SameHjInfoFragment();
        Bundle bundle=new Bundle();
        bundle.putSerializable("zjhm",zjhm);
        fragment.setArguments(bundle);
        return  fragment;
    }


    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case SUCCESS_HUJI:
                    hujiData.addAll((List<HouseholdInfo.FamilyAddressInfoList>)msg.obj);
                    data.add(new HouseholdInfo(true, "户籍地址:", hujiData));
                     getAdapder(data);
                    getJuzhuInfo();
                break;

                case SUCCESS_JUZHU:
                    lianxiData.addAll((List<HouseholdInfo.FamilyAddressInfoList>)msg.obj);
                    data.add(new HouseholdInfo(false, "居住地址:", lianxiData));
                    if(getActivity()!=null) {
                        getAdapder(data);
                    }
                    break;
                case PROBLEM:

                    if(getActivity()!=null) {
                        Toast.makeText(getActivity(), "网络不给力", Toast.LENGTH_SHORT).show();
                    }
                    break;

            }
        }
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=LayoutInflater.from(getContext()).inflate(R.layout.fragment_same_hj_info,container,false);
        isViewCreated=true;//标记不能少
        pInfo=(PersonListInfo) getArguments().getSerializable("zjhm");
        Log.e("--1--",""+pInfo);
        return view;
    }

    @Override
    public void lazyLoadData() {
        if(isViewCreated){//逻辑都写这个里面
            if(pInfo!=null) {
                initView(view);
            }
        }

    }

    private void initView(View view){

        lv= (ListView) view.findViewById(R.id.lv_fmt_family_info);
        tv_wu= (TextView) view.findViewById(R.id.tv_wu);


        if (TextUtils.equals(pInfo.getHJDZ(),"") && TextUtils.equals(pInfo.getLXDZ(),"")){
            tv_wu.setVisibility(View.VISIBLE);
        }else {
            networkData();
            tv_wu.setVisibility(View.GONE);

        }

        getAdapder(data);
    }


    //获取网络数据  请求户籍地址
    private void networkData(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                //户籍地址
                //http://web.youli.pw:8088/json/Get_HJDZ.aspx?sfz=000102199604061613
                String url= MyOkHttpUtils.BaseUrl+"/Json/Get_HJDZ.aspx?sfz="+pInfo.getZJHM();
                Response response=MyOkHttpUtils.okHttpGet(url);
                Log.e("aaaa---",""+response);

                Message msg=Message.obtain();
                if (response !=null){
                    try {
                        String resStr=response.body().string();
                        if (!TextUtils.equals(resStr,"")&&!TextUtils.equals(resStr,"[]")){
                            Gson gson=new Gson();
                            msg.obj=gson.fromJson(resStr,new TypeToken<List<HouseholdInfo.FamilyAddressInfoList>>(){}.getType());
                            msg.what=SUCCESS_HUJI;
                        }else{
                            msg.what=SUCCESS_NODATA;
                        }
                        mHandler.sendMessage(msg);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else {
                    msg.what=PROBLEM;
                    mHandler.sendMessage(msg);
                }
            }
        }).start();


    }

    //请求网络  获取联系地址
    private void getJuzhuInfo() {

        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        //联系地址
                        //http://web.youli.pw:8088/json/Get_LXDZ.aspx?sfz=000102199604061613
                        String url= MyOkHttpUtils.BaseUrl+"/Json/Get_LXDZ.aspx?sfz="+pInfo.getZJHM();
                        Response response=MyOkHttpUtils.okHttpGet(url);
                        Log.e("--00--",""+response);
                        Message msg=Message.obtain();
                        if(response!=null){
                            try {
                                String resStr=response.body().string();
                                if(!TextUtils.equals(resStr,"")&&!TextUtils.equals(resStr,"[]")){
                                    Gson gson=new Gson();
                                    msg.obj=gson.fromJson(resStr,new TypeToken<List<HouseholdInfo.FamilyAddressInfoList>>(){}.getType());
                                    msg.what=SUCCESS_JUZHU;
                                }else{
                                    msg.what=SUCCESS_NODATA;
                                }
                                mHandler.sendMessage(msg);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }else{
                            msg.what=PROBLEM;
                            mHandler.sendMessage(msg);
                        }
                    }
                }
        ).start();
    }


//外部适配器
    private void getAdapder(final List<HouseholdInfo> mlist){
     if (adapderm ==null){
         Log.e("---","联系地址");
         adapderm=new CommonAdapter<HouseholdInfo>(getActivity(),mlist,R.layout.item_family_info_lv) {
             @Override
             public void convert(CommonViewHolder holder, HouseholdInfo item, final int position) {
                 ImageView iv=holder.getView(R.id.iv_item_family_info_title);
                 TextView title=holder.getView(R.id.tv_item_family_info_title);
                 title.setText(item.getTitle());
                 MyListView childLv=holder.getView(R.id.lv_item_fmt_family_info);
                 if(data.get(position).isChecked()){
                     iv.setImageResource(R.drawable.sj1);
                     childLv.setVisibility(View.VISIBLE);
                 }else{
                     iv.setImageResource(R.drawable.sj);
                     childLv.setVisibility(View.GONE);
                 }
                 LinearLayout ll=holder.getView(R.id.ll_item_family_info_title);
                 ll.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         if(data.get(position).isChecked()){
                             data.get(position).setChecked(false);
                         }else{
                             for(HouseholdInfo fai:data){
                                 fai.setChecked(false);
                             }
                             data.get(position).setChecked(!data.get(position).isChecked());
                         }
                         adapderm.notifyDataSetChanged();
                     }
                 });


                 //内部适配器
                 childLv.setAdapter(new CommonAdapter<HouseholdInfo.FamilyAddressInfoList>(getActivity(),data.get(position).getList(),R.layout.item_item_family_info_lv) {
                     @Override
                     public void convert(CommonViewHolder holder, HouseholdInfo.FamilyAddressInfoList item, int position) {
                         final ImageView head=holder.getView(R.id.iv_item_item_family_info_head);
                         TextView name=holder.getView(R.id.tv_item_item_family_info_name);
                         name.setText(item.getXM());
                         TextView sex=holder.getView(R.id.tv_item_item_family_info_sex);
                         sex.setText(item.getGENDER());
                         TextView birthday=holder.getView(R.id.tv_item_item_family_info_birthday);
                         birthday.setText(item.getCSDATE1().split("T")[0]);
                         TextView idCard=holder.getView(R.id.tv_item_item_family_info_idcard);
                         idCard.setText(item.getZJHM());
                     }
                 });

                 childLv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                     @Override
                     public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                         showFamilyDialog(position);
                         return false;
                     }
                 });
             }
         };
         lv.setAdapter(adapderm);
     }else{
         adapderm.notifyDataSetChanged();
     }
    }

    private void showFamilyDialog(final int p){
        if(getActivity()==null){
            return;
        }
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setTitle("查看详细信息");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                getPersonInfo(p);
            }
        });
        builder.setNegativeButton("取消",null);
        builder.show();
    }






}
