package com.canplay.repast_pad.mvp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.canplay.repast_pad.R;
import com.canplay.repast_pad.base.BaseActivity;
import com.canplay.repast_pad.base.RxBus;
import com.canplay.repast_pad.base.SubscriptionBean;
import com.canplay.repast_pad.mvp.adapter.TypesAdapter;
import com.canplay.repast_pad.mvp.adapter.recycle.DishesRecycleAdapter;
import com.canplay.repast_pad.mvp.adapter.recycle.MenuRecycleAdapter;
import com.canplay.repast_pad.mvp.model.BaseType;
import com.canplay.repast_pad.view.DivItemDecoration;
import com.canplay.repast_pad.view.NavigationBar;
import com.canplay.repast_pad.view.RegularListView;
import com.malinskiy.superrecyclerview.SuperRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscription;
import rx.functions.Action1;

public class AddMenuActivity extends BaseActivity  {


    @BindView(R.id.navigationbar)
    NavigationBar navigationbar;
    @BindView(R.id.super_recycle_view)
    SuperRecyclerView mSuperRecyclerView;
    private MenuRecycleAdapter adapter;
    private Subscription mSubscription;

    private LinearLayoutManager mLinearLayoutManager;
    private int type;
    @Override
    public void initViews() {
        setContentView(R.layout.activity_add_menu);
        ButterKnife.bind(this);
        type=getIntent().getIntExtra("type",0);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mSuperRecyclerView.setLayoutManager(mLinearLayoutManager);
        mSuperRecyclerView.addItemDecoration(new DivItemDecoration(2, true));
        mSuperRecyclerView.getMoreProgressView().getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
        navigationbar.setNavigationBarListener(this);
        adapter=new MenuRecycleAdapter(this);
        mSuperRecyclerView.setAdapter(adapter);
        mSuperRecyclerView.setRefreshing(false);
        mSubscription = RxBus.getInstance().toObserverable(SubscriptionBean.RxBusSendBean.class).subscribe(new Action1<SubscriptionBean.RxBusSendBean>() {
            @Override
            public void call(SubscriptionBean.RxBusSendBean bean) {
                if (bean == null) return;

                if(bean.type==SubscriptionBean.FINISH){
                  finish();
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

    @Override
    public void bindEvents() {
        adapter.setItemCikcListener(new MenuRecycleAdapter.ItemClikcListener() {
            @Override
            public void itemClick(int poistioin) {
                if(type==1){
                    Intent intent = new Intent();
                    intent.putExtra("type", poistioin+1);
                    setResult(RESULT_OK,intent);
                    finish();
                }else {
                    Intent intent = new Intent(AddMenuActivity.this, MenuDetailActivity.class);
                    intent.putExtra("type", poistioin+1);
                    startActivity(intent);

                }

            }
        });

    }

    @Override
    public void initData() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSubscription.unsubscribe();
    }
}
