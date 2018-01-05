package com.canplay.repast_pad.mvp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.canplay.repast_pad.R;
import com.canplay.repast_pad.base.BaseActivity;
import com.canplay.repast_pad.view.NavigationBar;
import com.canplay.repast_pad.view.RegularListView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderDetailActivity extends BaseActivity {

    @BindView(R.id.navigationBar)
    NavigationBar navigationBar;
    @BindView(R.id.tv_order_number)
    TextView tvOrderNumber;
    @BindView(R.id.tv_pay_state)
    TextView tvPayState;
    @BindView(R.id.tv_table_code)
    TextView tvTableCode;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.lv_info)
    RegularListView lvInfo;
    @BindView(R.id.tv_remark)
    TextView tvRemark;
    @BindView(R.id.tv_pay_sure)
    TextView tvPaySure;


    @Override
    public void initViews() {
        setContentView(R.layout.activity_order_detail);
        ButterKnife.bind(this);
    }

    @Override
    public void bindEvents() {

    }

    @Override
    public void initData() {

    }
}
