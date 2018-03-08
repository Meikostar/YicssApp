package com.canplay.repast_wear.mvp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.canplay.repast_wear.R;
import com.canplay.repast_wear.base.BaseActivity;
import com.canplay.repast_wear.base.BaseApplication;
import com.canplay.repast_wear.bean.ORDER;
import com.canplay.repast_wear.bean.PrintBean;
import com.canplay.repast_wear.mvp.adapter.OrderAdapter;
import com.canplay.repast_wear.mvp.component.DaggerBaseComponent;
import com.canplay.repast_wear.mvp.present.CookClassifyContract;
import com.canplay.repast_wear.mvp.present.CookClassifyPresenter;
import com.canplay.repast_wear.util.TextUtil;
import com.canplay.repast_wear.view.NavigationBar;
import com.canplay.repast_wear.view.RegularListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderDetailfatherActivity extends BaseActivity implements CookClassifyContract.View {
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
    @BindView(R.id.tv_remark)
    TextView tvRemark;
    @BindView(R.id.ll_remark)
    LinearLayout llRemark;
    @BindView(R.id.ll_total)
    LinearLayout llTotal;
    @BindView(R.id.ll_all)
    LinearLayout ll_all;
    @BindView(R.id.ll_order)
    LinearLayout ll_order;

    @BindView(R.id.tv_pay_cancel)
    TextView tvPayCancel;
    @BindView(R.id.line)
    View line;
    @BindView(R.id.lines)
    View lines;
    @BindView(R.id.liness)
    View liness;
    @BindView(R.id.iv_arrow)
    ImageView iv_arrow;
    @BindView(R.id.lis)
    View lis;
    @BindView(R.id.liss)
    View liss;
    @BindView(R.id.tv_server)
    TextView tvServer;
    @BindView(R.id.ll_server)
    LinearLayout llServer;
    @BindView(R.id.iv_cancel)
    ImageView ivCancel;
    @BindView(R.id.ll_cancel)
    LinearLayout llCancel;
    @BindView(R.id.iv_sure)
    ImageView ivSure;
    @BindView(R.id.ll_sure)
    LinearLayout llSure;
    @BindView(R.id.tv_tea)
    TextView tvTea;
    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.tv_comment)
    TextView tv_comment;

    private OrderAdapter adapter;
    private String orderNo;

    @Override
    public void initViews() {
        setContentView(R.layout.activity_order_detail);
        ButterKnife.bind(this);
        DaggerBaseComponent.builder().appComponent(((BaseApplication) getApplication()).getAppComponent()).build().inject(this);
        navigationBar.setNavigationBarListener(this);
        showProgress("加载中...");
        presenter.attachView(this);
        orderNo = getIntent().getStringExtra("order");
        presenter.getOrderInfoList(orderNo);
        adapter = new OrderAdapter(this);
        regularListView.setAdapter(adapter);

        adapter.setClickListener(new OrderAdapter.ClickListener() {
            @Override
            public void clickListener(int type, String id) {

            }
        });
        llTotal.setVisibility(View.GONE);
        llRemark.setVisibility(View.GONE);
        lines.setVisibility(View.GONE);
        iv_arrow.setVisibility(View.GONE);
        ll_all.setVisibility(View.GONE);
        lis.setVisibility(View.GONE);
        liss.setVisibility(View.VISIBLE);
        liness.setVisibility(View.GONE);
        ll_order.setVisibility(View.GONE);
        llServer.setVisibility(View.VISIBLE);
    }

    @Override
    public void bindEvents() {

    }

   private List<ORDER> list=new ArrayList<>();
    @Override
    public void navigationRight() {
        BaseApplication.adapter.context=this;
        int i=0;
        if(BaseApplication.maps.size()>0&&BaseApplication.mBluetoothAdapter.isEnabled()){

            for (PrintBean printBean : BaseApplication.maps.values()) {
                if(printBean.isConnect){
                    i=1;
                    ORDER orde = new ORDER();
                    orde.detailNo=order.orderNo;
                    orde.tableNo=order.tableNo;
                    orde.createTime=order.createTime;
                    orde.totalPrice=order.totalPrice;
                    orde.remark=order.remark;
                    orde.serviceCharge=order.serviceCharge;
                    orde.num=order.num;
                    orde.surcharge=order.surcharge;
                    list.clear();
                    for(ORDER order1:map.values()){
                        list.add(order1);
                    }
                    orde.cookbookInfos=list;
                    BaseApplication.getInstance().print(printBean,orde);
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
       ORDER orde = new ORDER();
       orde.detailNo=order.orderNo;
       orde.tableNo=order.tableNo;
       orde.createTime=order.createTime;
       orde.totalPrice=order.totalPrice;
       orde.remark=order.remark;
       orde.serviceCharge=order.serviceCharge;
       orde.num=order.num;
       orde.surcharge=order.surcharge;
       list.clear();
       for(ORDER order1:map.values()){
           list.add(order1);
       }
       orde.cookbookInfos=list;
       intent6.putExtra("order",orde);
       intent6.putExtra("type", 1);
       startActivity(intent6);
   }
    @Override
    public void initData() {

    }

    @Override
    public <T> void toList(List<T> list, int type) {

    }

    private List<ORDER> datas = new ArrayList<>();
    private List<ORDER> data = new ArrayList<>();
    private Map<String ,ORDER> map=new HashMap<>();
    private ORDER order;

    @Override
    public <T> void toEntity(T entity, int type) {
        order = (ORDER) entity;
        dimessProgress();
        datas.clear();
        int i = 0;
        if (TextUtil.isNotEmpty(order.orderNo)) {
            tvOrderNumber.setText("订单号: " + order.orderNo);
            tvOrderno.setText(order.orderNo);
        }
        if (TextUtil.isNotEmpty(order.tableNo)) {
            tvTableCode.setText("桌号：" + order.tableNo);
        }
        tvTea.setText(order.surcharge+"元/位");
        tvNumber.setText(order.num+"");
        if(order.commentInfo!=null){
            if(!TextUtil.isEmpty(order.commentInfo.content)){
                tv_comment.setText(order.commentInfo.content+"");
            }

        }

        tvServer.setText("￥ " + order.serviceCharge);
        tvPayState.setText("￥ " + order.totalPrice);
        for (ORDER der : order.orderRelations) {
            for (ORDER dr : der.detailInfoResps) {


                dr.createTime = der.createTime;
                dr.detailNo = der.detailNo;
                dr.state = der.state;
                dr.detailPrice = der.detailPrice;
                dr.remark = der.remark;
                dr.status = i;
                dr.counts = dr.count;
                ORDER order = map.get(dr.cookbookId);
                if(order==null){
                    map.put(dr.cookbookId,dr);
                }else {
                    dr.counts=dr.counts+order.counts;
                    map.put(dr.cookbookId,dr);
                }
                datas.add(dr);
            }
            i++;
            adapter.setData(datas, 1);
        }

    }

    @Override
    public void showTomast(String msg) {
        dimessProgress();
    }



}
