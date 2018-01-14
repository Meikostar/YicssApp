package com.canplay.repast_pad.view;


import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.canplay.repast_pad.R;
import com.canplay.repast_pad.util.TextUtil;

/**
 * Created by qi_fu on 2017/12/18.
 */

public class AddmenuDialog {

    TextView tv_content;
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
    private View parentView;
    public AddmenuDialog(Context mContext, View parentView) {
        this.mContext = mContext;
        this.parentView = parentView;
        initView();
        mPopupWindow = new PopupWindow(mView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.setFocusable(true);

    }

    public AddmenuDialog setBindClickListener(BindClickListener mBindClickListener) {
        this.mBindClickListener = mBindClickListener;
        return this;
    }




    public interface BindClickListener {
        void teaMoney(String money);
    }

    public void setTitles(String name) {
        but_title.setVisibility(View.VISIBLE);
        but_title.setText(name);
    }
    public View getView(){
        return mView;
    }
    private void initView() {
        mView = View.inflate(mContext, R.layout.add_menu_dailog, null);
        mButtonCancel = (TextView) mView.findViewById(R.id.but_tsw_cancel);
        mButtonConfirm = (TextView) mView.findViewById(R.id.but_tsw_confirm);
        but_title = (TextView) mView.findViewById(R.id.select_title);
        tv_content = (TextView) mView.findViewById(R.id.tv_content);
        editText = (EditText) mView.findViewById(R.id.et_money);


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

                    mBindClickListener.teaMoney(editText.getText().toString().trim());
            }
        });
    }
    private void setRightorLeft(String right,String left,String content,String title){
        if(TextUtil.isNotEmpty(left)){
            mButtonCancel.setText(left);
        }
        if(TextUtil.isNotEmpty(right)){
            mButtonConfirm.setText(right);
        }
        if(TextUtil.isNotEmpty(content)){
            tv_content.setText(right);
        }

        if(TextUtil.isNotEmpty(title)){
            but_title.setText(title);
        }
    }
    public void show() {
     if(mPopupWindow!=null){
         editText.setText("");
         if (mPopupWindow.isShowing()) {
//             mPopupWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
//             mPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
             mPopupWindow.dismiss();
         } else {
             mPopupWindow.showAtLocation(parentView, Gravity.CENTER, 0, 0);
         }
     }
    }
    public void dismiss() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
    }
}

