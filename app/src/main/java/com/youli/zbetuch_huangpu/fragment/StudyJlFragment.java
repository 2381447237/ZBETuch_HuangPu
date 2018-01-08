package com.youli.zbetuch_huangpu.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.youli.zbetuch_huangpu.R;
import com.youli.zbetuch_huangpu.adapter.CommonAdapter;
import com.youli.zbetuch_huangpu.entity.CommonViewHolder;
import com.youli.zbetuch_huangpu.entity.StudyJlInfo;
import com.youli.zbetuch_huangpu.view.MyHorizontalScrollView;
import com.youli.zbetuch_huangpu.view.MyListView;
import com.youli.zbetuch_huangpu.view.ScrollViewListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liutao on 2018/1/5.
 *
 * 学习经历
 */

public class StudyJlFragment extends BaseVpFragment implements ScrollViewListener{

    private View view;

    private MyHorizontalScrollView titleHsv,contentHsv;
    private List<StudyJlInfo> data=new ArrayList<>();
    private CommonAdapter adapter;
    private MyListView lv;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view=LayoutInflater.from(getContext()).inflate(R.layout.fragment_study_jl,container,false);

        isViewCreated=true;//标记不能少

        return view;
    }

    @Override
    public void lazyLoadData() {

        if(isViewCreated){//逻辑都写这个里面

            initViews();


        }

    }

    private void initViews() {

        titleHsv= (MyHorizontalScrollView) view.findViewById(R.id.hsv_fragment_study_jl_title);
        contentHsv= (MyHorizontalScrollView) view.findViewById(R.id.hsv_fragment_study_jl_content);
        titleHsv.setOnScrollViewListener(this);
        contentHsv.setOnScrollViewListener(this);

        lv= (MyListView) view.findViewById(R.id.lv_fragment_study_jl);
        for(int i=0;i<40;i++){

            data.add(new StudyJlInfo("2018-01-05","2018-1-07","学校"+i,"大学"+i,"计算机","毕业","全日制","备注"+i));

        }

        LvSetAdapter(data);
    }

    private void LvSetAdapter(List<StudyJlInfo> list){

        if(adapter==null){

            adapter=new CommonAdapter<StudyJlInfo>(getContext(),list,R.layout.item_fragment_study_jl) {
                @Override
                public void convert(CommonViewHolder holder, StudyJlInfo item, int position) {

                    TextView tvNo=holder.getView(R.id.tv_item_fragment_study_jl_no);//编号
                    tvNo.setText((position+1)+"");
                    TextView tvStartTime=holder.getView(R.id.tv_item_fragment_study_jl_start_time);//起始日期
                    tvStartTime.setText(item.getStartTime());
                    TextView tvEndTime=holder.getView(R.id.tv_item_fragment_study_jl_end_time);//终止日期
                    tvEndTime.setText(item.getEndTime());
                    TextView tvShool=holder.getView(R.id.tv_item_fragment_study_shool_name);//学校名称
                    tvShool.setText(item.getShool());
                    TextView tvEdu=holder.getView(R.id.tv_item_fragment_study_edu);//文化程度
                    tvEdu.setText(item.getEdu());
                    TextView tvMajor=holder.getView(R.id.tv_item_fragment_study_major);//所学专业
                    tvMajor.setText(item.getMajor());
                    TextView tvGrad=holder.getView(R.id.tv_item_fragment_study_is_grad);//是否毕业
                    tvGrad.setText(item.getGrdu());
                    TextView tvFullTime=holder.getView(R.id.tv_item_fragment_study_full_time);//是否全日制
                    tvFullTime.setText(item.getFullTime());
                    TextView tvMark=holder.getView(R.id.tv_item_fragment_study_mark);//备注
                    tvMark.setText(item.getMark());

                }
            };

            lv.setAdapter(adapter);

        }else{
            adapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onScrollChanged(MyHorizontalScrollView scrollView, int x, int y, int oldx, int oldy) {
        if (scrollView == titleHsv) {
            contentHsv.scrollTo(x, y);

        } else if (scrollView == contentHsv) {
            titleHsv.scrollTo(x, y);

        }
    }
}
