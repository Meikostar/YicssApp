package com.canplay.repast_pad.view;


import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.canplay.repast_pad.R;
import com.canplay.repast_pad.util.StringUtil;

/**
 * Created by qi_fu on 2017/12/18.
 */

public class PersonDialog {
    private Context mContext;
    private View mView;
    private PopupWindow mPopupWindow;
    private TextView mButtonCancel;
    private TextView mButtonConfirm;
    private TextView but_title;
    private EditText editText;

    private BindClickListener mBindClickListener;

    public PersonDialog(Context mContext) {
        this.mContext = mContext;
    }
    public PersonDialog setBindClickListener(BindClickListener mBindClickListener) {
        this.mBindClickListener = mBindClickListener;
        return this;
    }


    public interface BindClickListener {
        void personNum(String count);
    }

    public void setTitles(String name) {
        but_title.setVisibility(View.VISIBLE);
        but_title.setText(name);
    }
    private void initView() {
        mView = View.inflate(mContext, R.layout.dialog_person_selector, null);
        mButtonCancel = (TextView) mView.findViewById(R.id.but_tsw_cancel);
        mButtonConfirm = (TextView) mView.findViewById(R.id.but_tsw_confirm);
        but_title = (TextView) mView.findViewById(R.id.select_title);
        editText = (EditText) mView.findViewById(R.id.et_person_count);
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
                String count = editText.getText().toString();
                if(StringUtil.isEmpty(count)){
                    Toast.makeText(mContext,"请选择就餐人数",Toast.LENGTH_SHORT).show();
                }else
                mBindClickListener.personNum(count);
            }
        });
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

