package com.youli.zbetuch_huangpu.activity;


import android.app.ProgressDialog;
import android.content.Context;

import android.os.Looper;
import android.os.Message;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.google.gson.Gson;
import com.youli.zbetuch_huangpu.R;
import com.youli.zbetuch_huangpu.utils.MyOkHttpUtils;
import java.util.ArrayList;
import java.util.List;
import okhttp3.Response;

public class PersonalInfoQuery extends BaseActivity implements View.OnClickListener{
    private static final int REQUEST_CODE_CAMERA = 9999;

    private final int SUCCEED=10000;
    private final int  PROBLEM=10001;
//    private List<PersonInfo> personList=new ArrayList<>();

    private Button btn_scanning;
    private EditText et_id_num;
    private Button btn_query_id_num;
    private EditText et_personName;
    private Spinner spinner_sex;
    private Spinner spinner_country;
    private Spinner spinner_street;
    private Spinner spinner_neighborhood_committee;
    private EditText et_age_from;
    private EditText et_age_to;
    private Spinner spinner_identifying;
    private Spinner spinner_status;
    private Spinner spinner_situation;
    private Spinner spinner_current_intent;
    private String[] personSex;
    private String[] spinner_sex_array;
    private Context mContext = this;
    private ProgressDialog progressDialog;
    private List<String> streetNameList = new ArrayList<String>();
    private List<String> committeeNameList = new ArrayList<String>();
    private CheckBox cb_resource;
    private Button btn_condition_query;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_info_query_layout);
        initView();
    }



    private void initView() {
        et_id_num = (EditText) findViewById(R.id.et_id_num);
        btn_scanning = (Button) findViewById(R.id.btn_scanning);
        btn_scanning.setOnClickListener(this);
        btn_query_id_num = (Button) findViewById(R.id.btn_query_id_num);
        et_personName = (EditText) findViewById(R.id.et_personal_name);
        spinner_sex = (Spinner) findViewById(R.id.spinner_sex);
        spinner_country = (Spinner) findViewById(R.id.spinner_country);
        spinner_street = (Spinner) findViewById(R.id.spinner_street);
        spinner_neighborhood_committee = (Spinner) findViewById(R.id
                .spinner_neighborhood_committee);
        et_age_from = (EditText) findViewById(R.id.et_age_from);
        et_age_to = (EditText) findViewById(R.id.et_age_to);
        spinner_identifying = (Spinner) findViewById(R.id.spinner_identifying);
        spinner_status = (Spinner) findViewById(R.id.spinner_status);
        spinner_current_intent = (Spinner) findViewById(R.id.spinner_current_intent);
        spinner_situation = (Spinner) findViewById(R.id.spinner_current_situation);
        cb_resource = (CheckBox) findViewById(R.id.cb_resources);
        btn_condition_query = (Button) findViewById(R.id.btn_condition_query);
        btn_condition_query.setOnClickListener(this);
        initSpinner();
    }

    /**
     * 初始化 Spinner
     */
    private void initSpinner() {
        btn_query_id_num.setOnClickListener(this);
        //性别 Spinner
        setSpinner(spinner_sex, R.array.spinner_sex);
        //标识 Spinner
        setSpinner(spinner_identifying, R.array.spinner_identifying);
        //状态 Spinner
        setSpinner(spinner_status, R.array.spinner_status);
        //当前意向
        setSpinner(spinner_current_intent, R.array.spinner_current_intent);

        //区县
        setSpinner(spinner_country, R.array.quxian);
        setSpinner(spinner_street, R.array.jiedao);
        setSpinner(spinner_neighborhood_committee, R.array.juwei);
        setSpinner(spinner_situation, R.array.modi);
    }
    /**
     * 传入 String 类型的 list ，将内容填充到 spinner 中
     *
     * @param stringList
     */
    private void showSpinner(final List<String> stringList, final Spinner spinner) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ArrayAdapter adapter = new ArrayAdapter<String>(mContext, R
                        .layout.simple_spinner_item, stringList);
                adapter.setDropDownViewResource(android.R.layout
                        .simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
                if (spinner.getAdapter().getCount() > 1) {
                    spinner.setSelection(0);
                }
            }
        });
    }

    /**
     * Spinner Utils
     *
     * @param spinner 控件名
     * @param resId   数组资源 Id
     */
    private void setSpinner(Spinner spinner, int resId) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, resId, R
                .layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);
    }

    //按钮的点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_query_id_num://查询
                final String id_card_num = et_id_num.getText().toString().trim();
                if (id_card_num.isEmpty()) {
                    Toast.makeText(this, "身份证号码不能为空", Toast.LENGTH_SHORT).show();
                    //et_id_num.setText("");
                    return;
                } else if (id_card_num.length() < 18) {
                    Toast.makeText(this, "对不起，您所输入的身份证号码有误，请重新输入", Toast.LENGTH_SHORT).show();
                    // et_id_num.setText("");
                    return;
                } else {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String url = MyOkHttpUtils.BaseUrl + "/Json/Get_BASIC_INFORMATION" +
                                    ".aspx?sfz=" + id_card_num;
                            Response response = MyOkHttpUtils.okHttpGet(url);
                            try {
                                String result = response.body().string().trim();
                                if (result.equals("[null]")) {
                                    Looper.prepare();
                                    Toast.makeText(mContext, "对不起，查无此人", Toast.LENGTH_SHORT).show();
                                    Looper.loop();
                                } else {
                                    // TODO 解析数据并传递给下一个Activity
                                    Gson gson=new Gson();
                                    Message msg=Message.obtain();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
                break;
        }
    }
    }