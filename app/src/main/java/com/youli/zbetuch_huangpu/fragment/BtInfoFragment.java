package com.youli.zbetuch_huangpu.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.youli.zbetuch_huangpu.R;
import com.youli.zbetuch_huangpu.adapter.CommonAdapter;
import com.youli.zbetuch_huangpu.entity.BtInfo;
import com.youli.zbetuch_huangpu.entity.CommonViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liutao on 2018/1/5.
 *
 * 补贴信息
 */

public class BtInfoFragment extends BaseVpFragment{

    private View view;
    private List<BtInfo> data=new ArrayList<>();
    private CommonAdapter adapter;
    private ListView lv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view=LayoutInflater.from(getContext()).inflate(R.layout.fragment_bt_info,container,false);

        isViewCreated=true;//标记不能少

        return view;
    }

    @Override
    public void lazyLoadData() {

        if(isViewCreated){//逻辑都写这个里面

            initViews();

        }

    }

    private void initViews(){

        lv= (ListView) view.findViewById(R.id.fragment_bt_info_lv);

        for(int i=0;i<20;i++){

            data.add(new BtInfo("2018-1-5","名称"+i,"5000"+i,"性质"+i));

        }

        LvSetAdapter(data);

    }

    private void LvSetAdapter(List<BtInfo> list){

        if(adapter==null){

            adapter=new CommonAdapter<BtInfo>(getContext(),list,R.layout.item_fragment_bt_info) {
                @Override
                public void convert(CommonViewHolder holder, BtInfo item, int position) {

                    TextView tvNo=holder.getView(R.id.tv_item_fragment_bt_info_no);//编号
                    tvNo.setText((position+1)+"");
                    TextView tvTime=holder.getView(R.id.tv_item_fragment_bt_info_time);//支付年月
                    tvTime.setText(item.getTime());
                    TextView tvName=holder.getView(R.id.tv_item_fragment_bt_info_name);//补贴项目名称
                    tvName.setText(item.getName());
                    TextView tvMoney=holder.getView(R.id.tv_item_fragment_bt_info_money);//补贴金额
                    tvMoney.setText(item.getMoney());
                    TextView tvNature=holder.getView(R.id.tv_item_fragment_bt_info_nature);//补贴性质
                    tvNature.setText(item.getNature());
                }
            };

            lv.setAdapter(adapter);

        }else{
            adapter.notifyDataSetChanged();
        }

    }

}
