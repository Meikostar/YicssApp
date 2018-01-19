package com.canplay.repast_wear.view;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.canplay.repast_wear.R;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by syj on 2016/11/23.
 */
public class PopView_NavigationBar extends BasePopView {
    @BindView(R.id.tv_all)
    TextView tvAll;
    @BindView(R.id.tv_wait)
    TextView tvWait;
    @BindView(R.id.tv_wait_pay)
    TextView tvWaitPay;
    @BindView(R.id.tv_complete)
    TextView tvComplete;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;


    public PopView_NavigationBar(Activity activity,int type) {
        super(activity);
        this.type=type;
    }

    public ItemCliskListeners listeners;

    public interface ItemCliskListeners {
        void clickListener(int poistion);
    }

    public void setClickListener(ItemCliskListeners listener) {
        listeners = listener;
    }

    @Override
    protected View initPopView(LayoutInflater infalter) {
        View popView = infalter.inflate(R.layout.popview_navigationbar, null);
        ButterKnife.bind(this, popView);
        tvAll.setOnClickListener(this);
        tvWait.setOnClickListener(this);
        tvWaitPay.setOnClickListener(this);
        tvComplete.setOnClickListener(this);
        tvCancel.setOnClickListener(this);

        popView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return popView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_all:
                //
                listeners.clickListener(0);
                break;
            case R.id.tv_wait:
                listeners.clickListener(1);
                break;
            case R.id.tv_wait_pay:
                //
                listeners.clickListener(2);
                break;
            case R.id.tv_complete:
                listeners.clickListener(3);
                break;
            case R.id.tv_cancel:
                listeners.clickListener(4);
                break;

        }

    }
    public void setView(View view){
        line=view;
    }

}
