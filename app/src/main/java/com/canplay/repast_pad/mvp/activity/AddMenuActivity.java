package com.canplay.repast_pad.mvp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ListView;

import com.canplay.repast_pad.R;
import com.canplay.repast_pad.base.BaseActivity;
import com.canplay.repast_pad.base.RxBus;
import com.canplay.repast_pad.base.SubscriptionBean;
import com.canplay.repast_pad.mvp.adapter.TypesAdapter;
import com.canplay.repast_pad.mvp.model.BaseType;
import com.canplay.repast_pad.view.NavigationBar;
import com.canplay.repast_pad.view.RegularListView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscription;
import rx.functions.Action1;

public class AddMenuActivity extends BaseActivity  {


    @BindView(R.id.navigationbar)
    NavigationBar navigationbar;
    @BindView(R.id.rl_menu)
    ListView rlMenu;
    private TypesAdapter adapter;
    private Subscription mSubscription;
    @Override
    public void initViews() {
        setContentView(R.layout.activity_add_menu);
        ButterKnife.bind(this);
        adapter=new TypesAdapter(this);
        rlMenu.setAdapter(adapter);
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
        adapter.setClickListener(new TypesAdapter.ItemCliks() {
            @Override
            public void getItem(int poistioin) {
                Intent intent = new Intent(AddMenuActivity.this, MenuDetailActivity.class);
                intent.putExtra("type", poistioin+1);
                startActivity(intent);
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
