package com.canplay.repast_pad.view;


import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.canplay.repast_pad.R;

/**
 * Created by qi_fu on 2017/12/18.
 */

public class TasteSelectDialog implements View.OnClickListener{
    TextView tvGg01;
    TextView tvGg02;
    TextView tvGg03;
    TextView tvJl01;
    TextView tvJl02;
    TextView tvJl03;
    TextView tvKw01;
    TextView tvKw02;
    TextView tvKw03;
    TextView tvKw04;
    private Context mContext;
    private View mView;
    private PopupWindow mPopupWindow;
    private TextView mButtonCancel;
    private TextView mButtonConfirm;
    private TextView but_title;
    private EditText editText;

    private BindClickListener mBindClickListener;
    private TextView[] views;
    private TextView[] views2;
    private TextView[] views3;
    private int gg;
    private int jl;
    private int kw;

    public TasteSelectDialog(Context mContext) {
        this.mContext = mContext;
    }

    public TasteSelectDialog setBindClickListener(BindClickListener mBindClickListener) {
        this.mBindClickListener = mBindClickListener;
        return this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_gg_01:
                setText(tvGg01,views);
                gg=1;
                break;
            case R.id.tv_gg_02:
                setText(tvGg02,views);
                gg=2;
                break;
            case R.id.tv_gg_03:
                setText(tvGg03,views);
                gg=3;
                break;
            case R.id.tv_jl_01:
                setText(tvJl01,views2);
                jl=1;
                break;
            case R.id.tv_jl_02:
                setText(tvJl02,views2);
                jl=2;
                break;
            case R.id.tv_jl_03:
                setText(tvJl03,views2);
                jl=3;
                break;
            case R.id.tv_kw_01:
                setText(tvKw01,views3);
                kw=1;
                break;
            case R.id.tv_kw_02:
                setText(tvKw02,views3);
                kw=2;
                break;
            case R.id.tv_kw_03:
                setText(tvKw03,views3);
                kw=3;
                break;
            case R.id.tv_kw_04:
                setText(tvKw04,views3);
                kw=4;
                break;
        }
    }


    public interface BindClickListener {
        void tasteNum(int gg,int jl,int kw);
    }

    public void setTitles(String name) {
        but_title.setVisibility(View.VISIBLE);
        but_title.setText(name);
    }

    private void initView() {
        mView = View.inflate(mContext, R.layout.dialog_taste_selector, null);
        mButtonCancel = (TextView) mView.findViewById(R.id.but_tsw_cancel);
        mButtonConfirm = (TextView) mView.findViewById(R.id.but_tsw_confirm);
        but_title = (TextView) mView.findViewById(R.id.select_title);
        tvGg01 = (TextView) mView.findViewById(R.id.tv_gg_01);
        tvGg02 = (TextView) mView.findViewById(R.id.tv_gg_02);
        tvGg03 = (TextView) mView.findViewById(R.id.tv_gg_03);
        tvJl01 = (TextView) mView.findViewById(R.id.tv_jl_01);
        tvJl02 = (TextView) mView.findViewById(R.id.tv_jl_02);
        tvJl03 = (TextView) mView.findViewById(R.id.tv_jl_03);
        tvKw01 = (TextView) mView.findViewById(R.id.tv_kw_01);
        tvKw02 = (TextView) mView.findViewById(R.id.tv_kw_02);
        tvKw03 = (TextView) mView.findViewById(R.id.tv_kw_03);
        tvKw04 = (TextView) mView.findViewById(R.id.tv_kw_04);
        tvGg01.setSelected(true);
        tvJl01.setSelected(true);
        tvKw01.setSelected(true);
        views = new TextView[]{tvGg01,tvGg02,tvGg03};
        views2 = new TextView[]{tvJl01,tvJl02,tvJl03};
        views3 = new TextView[]{tvKw01,tvKw02,tvKw03,tvKw04};
        mButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mButtonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                    mBindClickListener.tasteNum(gg,jl,kw);
            }
        });
    }
    private void setText(TextView tv,TextView[] textViews){
        for (int i = 0; i < textViews.length; i++) {
            if(textViews[i]==tv){
                textViews[i].setSelected(true);
            }else {
                textViews[i].setSelected(false);
            }
        }
    }

    public void show(View parentView) {
        initView();
        mPopupWindow = new PopupWindow(mView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.setFocusable(true);
        if (mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        } else {
            mPopupWindow.showAtLocation(parentView, Gravity.CENTER, 0, 0);
        }
    }
    public void dismiss() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
    }
}

