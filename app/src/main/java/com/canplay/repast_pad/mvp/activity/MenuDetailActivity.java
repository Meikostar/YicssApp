package com.canplay.repast_pad.mvp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.canplay.repast_pad.R;
import com.canplay.repast_pad.base.BaseActivity;
import com.canplay.repast_pad.base.RxBus;
import com.canplay.repast_pad.base.SubscriptionBean;
import com.canplay.repast_pad.mvp.adapter.CountAdapter;
import com.canplay.repast_pad.mvp.model.BaseType;
import com.canplay.repast_pad.view.NavigationBar;
import com.canplay.repast_pad.view.RegularListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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
    private CountAdapter adapter;
    public int cout;
    private List<BaseType> datas=new ArrayList<>();
    @Override
    public void initViews() {
        setContentView(R.layout.activity_menu_detail);
        ButterKnife.bind(this);
        cout=getIntent().getIntExtra("type",1);
        tvStyle.setText("一屏"+cout+"道菜");
        adapter=new CountAdapter(this);
        rlMenu.setAdapter(adapter);

    }

    @Override
    public void bindEvents() {
        adapter.setClickListener(new CountAdapter.ItemCliks() {
            @Override
            public void getItem(BaseType baseType, int poistioin) {

            }
        });
        llStyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RxBus.getInstance().send(SubscriptionBean.createSendBean(SubscriptionBean.FINISH,""));
                startActivity(new Intent(MenuDetailActivity.this,AddMenuActivity.class));
            }
        });
    }

    @Override
    public void initData() {
        for(int i=0;i<cout;i++){
            BaseType baseType = new BaseType();
            datas.add(baseType);
        }
        adapter.setData(datas);
    }


}
