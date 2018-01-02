package com.canplay.repast_pad.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.canplay.repast_pad.R;


/**
 * 自定义tag的点击条
 */


public class Custom_TagBtn extends RelativeLayout {

    private View selfView;

    //
    private View rl_bg;
    public View rl_delete;
    public TextView txt_content;
    private Custom_TagBtnListener listener;

    private LayoutInflater inflater;

    public interface Custom_TagBtnListener {
        void clickDelete();
    }

    public void setCustom_TagBtnListener(Custom_TagBtnListener listener) {
        this.listener = listener;
    }


    public Custom_TagBtn(Context context) {
        this(context, null);
    }

    public Custom_TagBtn(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Custom_TagBtn(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflater = LayoutInflater.from(context);
        selfView = inflater.inflate(R.layout.custom_tag, this);
        initView(selfView);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Custom_TagBtn, defStyleAttr, 0);
        //名字
        String nameStr = a.getString(R.styleable.Custom_TagBtn_btn_tag_txt);
        a.recycle();
        setCustomText(nameStr);
    }

    public void setBg(int resources) {
        txt_content.setBackgroundResource(resources);
    }


    public void setCustomText(String nameStr) {
        txt_content.setText(nameStr);
    }


    private void initView(View selfView) {

        txt_content = (TextView) selfView.findViewById(R.id.txt_content);


        rl_delete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.clickDelete();
                }
            }
        });
    }

}
