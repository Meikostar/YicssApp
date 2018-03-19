package com.canplay.medical.view;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;


import com.canplay.medical.R;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by syj on 2016/11/23.
 */
public class PopView_NavigationBar extends BasePopView {

    @BindView(R.id.tv_new)
    TextView tvNew;
    @BindView(R.id.tv_preview)
    TextView tvPreview;


    public PopView_NavigationBar(Activity activity, int type) {
        super(activity);
        this.type = type;
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
        View popView = infalter.inflate(R.layout.popview_navigationbar_menu, null);
        ButterKnife.bind(this, popView);
        tvNew.setOnClickListener(this);
        tvPreview.setOnClickListener(this);


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
            case R.id.tv_new:
                listeners.clickListener(0);
                break;
            case R.id.tv_preview:
                listeners.clickListener(1);
                break;


        }

    }
    public void setView(View view){
        line=view;
    }

    public void setName(String top,String bot){
        tvNew.setText(top);
        tvPreview.setText(bot);
    }
}
