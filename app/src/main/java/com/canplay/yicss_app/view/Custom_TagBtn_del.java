package com.canplay.yicss_app.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.canplay.yicss_app.R;
import com.canplay.yicss_app.util.DensityUtil;


/**
 * 自定义tag的点击条
 */


public class Custom_TagBtn_del extends RelativeLayout {

    private View selfView;

    //

    public View rl_delete;
    public TextView txt_content;
    public ImageView iv_delelte;
    public RelativeLayout rl_bg;
    private Context context;
    private Custom_TagBtnListener listener;
    private boolean isClick;
    private LayoutInflater inflater;

    public interface Custom_TagBtnListener {
        void clickDelete(int type);
    }

    public void setCustom_TagBtnListener(Custom_TagBtnListener listener) {
        this.listener = listener;
    }


    public Custom_TagBtn_del(Context context) {
        this(context, null);
        this.context=context;
    }

    public Custom_TagBtn_del(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        this.context=context;
    }
    public void show(){
        iv_delelte.setVisibility(VISIBLE);
    }
    public Custom_TagBtn_del(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        inflater = LayoutInflater.from(context);
        selfView = inflater.inflate(R.layout.custom_tagbtn_del, this);
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
    public void setColors(int nameStr) {
        txt_content.setTextColor(getResources().getColor(nameStr));
    }
   public void setCannotClick(boolean isClicks){
       isClick=isClicks;
   }

    private void initView(View selfView) {

        txt_content = (TextView) selfView.findViewById(R.id.txt_content);
        iv_delelte = (ImageView) selfView.findViewById(R.id.iv_delelte);
        rl_bg = (RelativeLayout) selfView.findViewById(R.id.rl_bg);
        txt_content.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.clickDelete(1);
            }
        });
        iv_delelte.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.clickDelete(2);
            }
        });
        txt_content.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(isClick){
                    listener.clickDelete(3);
                }
                return true;
            }
        });

    }
    public void setSize(int with,int height,int size){

        ViewGroup.LayoutParams lp = (ViewGroup.LayoutParams) rl_bg.getLayoutParams();
        lp.width = DensityUtil.dip2px(context, with<=105?105:with);

        lp.height=DensityUtil.dip2px(context, height);
        txt_content.setTextSize(TypedValue.COMPLEX_UNIT_DIP,size);
        rl_bg.setLayoutParams(lp);

    }

}
