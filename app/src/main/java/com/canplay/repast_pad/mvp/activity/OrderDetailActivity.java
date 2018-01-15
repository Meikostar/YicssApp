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
import com.canplay.repast_pad.bean.BEAN;
import com.canplay.repast_pad.bean.ORDER;
import com.canplay.repast_pad.mvp.adapter.OrderAdapter;
import com.canplay.repast_pad.mvp.component.DaggerBaseComponent;
import com.canplay.repast_pad.mvp.present.CookClassifyContract;
import com.canplay.repast_pad.mvp.present.CookClassifyPresenter;
import com.canplay.repast_pad.util.TextUtil;
import com.canplay.repast_pad.util.TimeUtil;
import com.canplay.repast_pad.view.NavigationBar;
import com.canplay.repast_pad.view.RegularListView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
    @BindView(R.id.ll_sure)
    LinearLayout ll_sure;

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
        showProgress("加载中...");
        adapter = new OrderAdapter(this);
        regularListView.setAdapter(adapter);
        navigationBar.setNavigationBarListener(this);
        adapter.setClickListener(new OrderAdapter.ClickListener() {
            @Override
            public void clickListener(int type, String id) {

            }
        });
    }
   private List<BEAN> data=new ArrayList<>();
    @Override
    public void bindEvents() {

        ll_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<ORDER> datas = adapter.getData();
                datas.clear();
                for(ORDER order:datas){
                    BEAN be = new BEAN();
                    be.count=order.count;
                    be.detaiId=order.detailId;
                    data.add(be);
                }
                //我们就需要用到这个属性，以及下面的代码
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.excludeFieldsWithoutExposeAnnotation();// 不转换没有 @Expose 注解的字段
                Gson gson1 = gsonBuilder.create();
                String strUser2 = gson1.toJson(data);
                presenter.updateDetailCount(strUser2);
            }
        });
        rlGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderDetailActivity.this, OrderDetailfatherActivity.class);
                intent.putExtra("order", order.orderNo);
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
        dimessProgress();
        if(type==6){
             showToasts("");
            finish();
        }else {
            order = (ORDER) entity;
            datas.clear();
            adapter.setData(order.cookbookInfos,0);
            if (TextUtil.isNotEmpty(order.orderNo)) {
                tvOrderNumber.setText("订单号: " + order.orderNo);
                tvOrderno.setText(order.orderNo);
            }
            if (TextUtil.isNotEmpty(order.remark)) {
                tv_remark.setText(order.remark);
            }
            tvPayState.setText("￥ " + order.totalPrice);
            tvMoney.setText("￥ " + order.detailPrice);
            tvTime.setText(TimeUtil.formatTime(order.createTime));
        }



    }

    @Override
    public void showTomast(String msg) {
        dimessProgress();
    }



}
