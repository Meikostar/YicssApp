package com.canplay.repast_wear.mvp.activity;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.canplay.repast_wear.R;
import com.canplay.repast_wear.base.BaseActivity;
import com.canplay.repast_wear.base.BaseApplication;
import com.canplay.repast_wear.base.RxBus;
import com.canplay.repast_wear.base.SubscriptionBean;
import com.canplay.repast_wear.bean.BEAN;
import com.canplay.repast_wear.bean.ORDER;
import com.canplay.repast_wear.bean.PrintBean;
import com.canplay.repast_wear.mvp.adapter.OrderAdapter;
import com.canplay.repast_wear.mvp.component.DaggerBaseComponent;
import com.canplay.repast_wear.mvp.present.CookClassifyContract;
import com.canplay.repast_wear.mvp.present.CookClassifyPresenter;
import com.canplay.repast_wear.util.TextUtil;
import com.canplay.repast_wear.util.TimeUtil;
import com.canplay.repast_wear.view.NavigationBar;
import com.canplay.repast_wear.view.RegularListView;
import com.google.gson.Gson;


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
    @BindView(R.id.liness)
    View lines;


    @BindView(R.id.tv_remark)
    TextView tv_remark;
    @BindView(R.id.ll_remark)
    LinearLayout llRemark;
    @BindView(R.id.ll_total)
    LinearLayout llTotal;
    @BindView(R.id.ll_sure)
    LinearLayout ll_sure;
    @BindView(R.id.ll_all)
    LinearLayout ll_all;
    @BindView(R.id.ll_cancel)
    LinearLayout ll_cancel;

    private OrderAdapter adapter;
    private String orderNo;

    @Override
    public void initViews() {
        setContentView(R.layout.activity_order_detail);
        ButterKnife.bind(this);
        DaggerBaseComponent.builder().appComponent(((BaseApplication) getApplication()).getAppComponent()).build().inject(this);
        navigationBar.setNavigationBarListener(this);

        presenter.attachView(this);
        orderNo = getIntent().getStringExtra("order");
        presenter.getAppOrderInfo(orderNo);
        showProgress("加载中...");
        adapter = new OrderAdapter(this);
        regularListView.setAdapter(adapter);

        adapter.setClickListener(new OrderAdapter.ClickListener() {
            @Override
            public void clickListener(int type, String total) {
                tvMoney.setText("￥ " + total);
            }
        });
    }



    private int state;
    private List<BEAN> data=new ArrayList<>();
    @Override
    public void bindEvents() {
        ll_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status=1;
                presenter.updateOrderState(order.detailNo,3+"");
            }
        });
        ll_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(order.state==0){
                    List<ORDER> datas = adapter.getDatas();
                    data.clear();

                    for(ORDER order:datas){
                        BEAN be = new BEAN();
                        be.count=order.count;
                        be.detailId=order.detailId;
                        data.add(be);
                    }
                    //我们就需要用到这个属性，以及下面的代码
//                    GsonBuilder gsonBuilder = new GsonBuilder();
//                    gsonBuilder.excludeFieldsWithoutExposeAnnotation();// 不转换没有 @Expose 注解的字段
//                    Gson gson1 = gsonBuilder.create();
//                    String strUser2 = gson1.toJson(data);
//                    String string = data.toString();
                    String json = new Gson ().toJson(data);
                    presenter.updateDetailCount(json);
                    state=1;
                    presenter.updateOrderState(order.detailNo,(order.state+1)+"");
                }else {
                    state=2;
                    presenter.updateOrderState(order.detailNo,(order.state+1)+"");
                }
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



    @Override
    public void navigationRight() {
//        goPrint();
        int i=0;
        BaseApplication.adapter.context=this;
        if(BaseApplication.maps.size()>0&&BaseApplication.mBluetoothAdapter.isEnabled()){

            for (PrintBean printBean : BaseApplication.maps.values()) {

                if(printBean.isConnect){
                    i=1;
                    BaseApplication.getInstance().print(printBean,order);
                }
            }
        }else {
            i=1;
            goPrint();
        }
       if(i==0){
           goPrint();
       }


    }

    @Override
    public void navigationimg() {
        goPrint();
    }
    public void goPrint(){
        Intent intent6 = new Intent(this, PrintSetActivity.class);
        intent6.putExtra("order",order);
        intent6.putExtra("type",1);
        startActivity(intent6);
    }
    @Override
    public void initData() {

    }

    @Override
    public <T> void toList(List<T> list, int type) {

    }

    private List<ORDER> datas = new ArrayList<>();
    private ORDER order;
    private int status;
    @Override
    public <T> void toEntity(T entity, int type) {
        dimessProgress();
        if(type==6){
             showToasts("接单成功");
            order.state=1;
            presenter.getAppOrderInfo(orderNo);
            RxBus.getInstance().send(SubscriptionBean.createSendBean(SubscriptionBean.NOFIFY,""));
//            finish();
        }else if(type==-2){
            if(state==1){
                showToasts("接单成功");
                tvPaySure.setText("确认付款");
            }else if(state==2){
                showToasts("已确认付款");
                ll_all.setVisibility(View.GONE);
                lines.setVisibility(View.GONE);
            }

            if(status==1){
                status=0;
                showToasts("订单撤销成功");
                finish();
            }
            RxBus.getInstance().send(SubscriptionBean.createSendBean(SubscriptionBean.NOFIFY,""));
//            adapter.setType(0);
        }else {
            order = (ORDER) entity;
            datas.clear();
            adapter.setData(order.cookbookInfos,0);
            adapter.setState(order.state);
            if(order.state==1){
                tvPaySure.setText("确认付款");

            }else if(order.state==0){
                tvPaySure.setText("确认接单");

            }else {
                ll_all.setVisibility(View.GONE);
                lines.setVisibility(View.GONE);
            }
            if (TextUtil.isNotEmpty(order.orderNo)) {
                tvOrderNumber.setText("订单号: " + order.orderNo);
                tvOrderno.setText(order.orderNo);
            }
            if (TextUtil.isNotEmpty(order.remark)) {
                tv_remark.setText(order.remark);
            }
            if (TextUtil.isNotEmpty(order.tableNo)) {
                tvTableCode.setText("桌号："+order.tableNo);
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
