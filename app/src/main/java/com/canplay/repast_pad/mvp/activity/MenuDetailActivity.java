package com.canplay.repast_pad.mvp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.canplay.repast_pad.R;
import com.canplay.repast_pad.base.BaseActivity;
import com.canplay.repast_pad.base.RxBus;
import com.canplay.repast_pad.base.SubscriptionBean;
import com.canplay.repast_pad.bean.COOK;
import com.canplay.repast_pad.mvp.adapter.CountAdapter;
import com.canplay.repast_pad.mvp.model.BaseType;
import com.canplay.repast_pad.util.TextUtil;
import com.canplay.repast_pad.view.NavigationBar;
import com.canplay.repast_pad.view.RegularListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscription;
import rx.functions.Action1;

public class MenuDetailActivity extends BaseActivity {


    @BindView(R.id.navigationBar)
    NavigationBar navigationBar;
    @BindView(R.id.tv_style)
    TextView tvStyle;
    @BindView(R.id.ll_style)
    LinearLayout llStyle;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.ll_type)
    LinearLayout llType;
    @BindView(R.id.rl_menu)
    RegularListView rlMenu;
    @BindView(R.id.tv_pay_sure)
    TextView tvPaySure;
    @BindView(R.id.et_sort)
    EditText etSort;
    private CountAdapter adapter;
    public int cout;
    private List<BaseType> datas = new ArrayList<>();
    private COOK cook;
    private String classifyId;
    private String templateId;
    private Subscription mSubscription;
    @Override
    public void initViews() {
        setContentView(R.layout.activity_menu_detail);
        ButterKnife.bind(this);
        cout = getIntent().getIntExtra("type", 1);

        adapter = new CountAdapter(this);
        rlMenu.setAdapter(adapter);
        cook = (COOK) getIntent().getSerializableExtra("cook");
        mSubscription = RxBus.getInstance().toObserverable(SubscriptionBean.RxBusSendBean.class).subscribe(new Action1<SubscriptionBean.RxBusSendBean>() {
            @Override
            public void call(SubscriptionBean.RxBusSendBean bean) {
                if (bean == null) return;

                if(bean.type==SubscriptionBean.ADD_MENU){
                    BaseType  beans = (BaseType) bean.content;
                    classifyId=beans.cbClassifyId;
                    tvType.setText(beans.name);
                    tvType.setTextColor(getResources().getColor(R.color.slow_black));
                }

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                throwable.printStackTrace();
            }
        });
        RxBus.getInstance().addSubscription(mSubscription);
    }

    private int CHOOSE = 1;
    private int poistion = 0;
    private String cookbookIds;
    private String sort;
    @Override
    public void bindEvents() {
        adapter.setClickListener(new CountAdapter.ItemCliks() {
            @Override
            public void getItem(BaseType baseType, int poistioin) {
                poistion = poistioin;
                Intent intent = new Intent(MenuDetailActivity.this, ChooseFoodActivity.class);
                intent.putExtra("id", baseType.classifyId);
                startActivityForResult(intent, CHOOSE);
            }
        });
        llStyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(MenuDetailActivity.this, AddMenueCategoryActivity.class);
                intent3.putExtra("type",2);
                startActivity(intent3);
            }
        });
        tvPaySure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<BaseType> datas = adapter.getDatas();
                if(TextUtil.isEmpty(classifyId)){
                    showToasts("请选择菜单类型");
                    return;
                }
                int i=0;
                for(BaseType base:datas){

                    if(TextUtil.isEmpty(base.cookbookId)){
                        showToasts("请选择菜品");
                        return;
                    }
                    if(i==0){
                        cookbookIds=base.cookbookId;
                    }else {
                        cookbookIds=cookbookIds+","+base.cookbookId;
                    }
                }
               sort= etSort.getText().toString().trim();
                if(TextUtil.isEmpty(sort)){
                    showToasts("请填写序号");
                    return;
                }

            }
        });
        etSort.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void initData() {
        for (int i = 0; i < cout; i++) {
            BaseType baseType = new BaseType();
            datas.add(baseType);
        }
        if(cook!=null){
            classifyId=cook.classifyId;
            sort=cook.sort;
            templateId=cook.templateId;
            for (COOK ck : cook.cookbookInfo) {
                BaseType baseType = new BaseType();
                baseType.name = ck.cnName;
                baseType.cookbookId = ck.cookbookId;
                baseType.classifyId = classifyId;
                datas.add(baseType);
            }
        }

        if (cook != null) {
            cout = cook.cookbookInfo.size();
        }
        templateId=cout+"";
        tvStyle.setText("一屏" + cout + "道菜");
        adapter.setData(datas);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == CHOOSE) {
                COOK ck = (COOK) data.getSerializableExtra("cook");
                BaseType baseType = datas.get(poistion);
                baseType.name = ck.cnName;
                baseType.cookbookId = ck.cookbookId;
                baseType.classifyId = ck.classifyId;
                adapter.setData(datas);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSubscription.unsubscribe();
    }
}
