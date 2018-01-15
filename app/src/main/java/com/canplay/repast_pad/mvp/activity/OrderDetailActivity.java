package com.canplay.repast_pad.mvp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.canplay.repast_pad.R;
import com.canplay.repast_pad.base.BaseActivity;
import com.canplay.repast_pad.base.BaseApplication;
import com.canplay.repast_pad.bean.ORDER;
import com.canplay.repast_pad.mvp.adapter.OrderAdapter;
import com.canplay.repast_pad.mvp.component.DaggerBaseComponent;
import com.canplay.repast_pad.mvp.present.CookClassifyContract;
import com.canplay.repast_pad.mvp.present.CookClassifyPresenter;
import com.canplay.repast_pad.util.TextUtil;
import com.canplay.repast_pad.util.TimeUtil;
import com.canplay.repast_pad.view.NavigationBar;
import com.canplay.repast_pad.view.RegularListView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderDetailActivity extends BaseActivity implements CookClassifyContract.View {
    @Inject
    CookClassifyPresenter presenter;
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
    @BindView(R.id.lv_info)
    RegularListView regularListView;
    @BindView(R.id.tv_pay_sure)
    TextView tvPaySure;
    @BindView(R.id.ll_bg)
    LinearLayout llBg;
    @BindView(R.id.rl_go)
    RelativeLayout rlGo;
    @BindView(R.id.tv_orderno)
    TextView tvOrderno;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_pay_cancel)
    TextView tvPayCancel;
    @BindView(R.id.line)
    View line;

    @BindView(R.id.tv_remark)
    TextView tv_remark;
    @BindView(R.id.ll_remark)
    LinearLayout llRemark;
    @BindView(R.id.ll_total)
    LinearLayout llTotal;

    private OrderAdapter adapter;
    private String orderNo;

    @Override
    public void initViews() {
        setContentView(R.layout.activity_order_detail);
        ButterKnife.bind(this);
        DaggerBaseComponent.builder().appComponent(((BaseApplication) getApplication()).getAppComponent()).build().inject(this);

        presenter.attachView(this);
        orderNo = getIntent().getStringExtra("order");
        presenter.getAppOrderInfo(orderNo);
        adapter = new OrderAdapter(this);
        regularListView.setAdapter(adapter);
        navigationBar.setNavigationBarListener(this);
        adapter.setClickListener(new OrderAdapter.ClickListener() {
            @Override
            public void clickListener(int type, String id) {

            }
        });
    }

    @Override
    public void bindEvents() {
        rlGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderDetailActivity.this, OrderDetailActivity.class);
                intent.putExtra("order", order.detailNo);
                startActivity(intent);
            }
        });

    }

    public void navigationLeft() {

    }

    @Override
    public void navigationRight() {

    }

    @Override
    public void navigationimg() {

    }

    @Override
    public void initData() {

    }

    @Override
    public <T> void toList(List<T> list, int type) {

    }

    private List<ORDER> datas = new ArrayList<>();
    private ORDER order;

    @Override
    public <T> void toEntity(T entity, int type) {
        order = (ORDER) entity;
        datas.clear();
        adapter.setData(order.cookbookInfos);
        if (TextUtil.isNotEmpty(order.orderNo)) {
            tvOrderNumber.setText("订单号: " + order.orderNo);
            tvOrderno.setText(order.orderNo);
        }
        if (TextUtil.isNotEmpty(order.remark)) {
            tv_remark.setText(order.remark);

        }
        tvMoney.setText("￥ " + order.totalPrice);
        tvPayState.setText("￥ " + order.detailPrice);

        tvTime.setText(TimeUtil.formatTime(order.createTime));


    }

    @Override
    public void showTomast(String msg) {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
