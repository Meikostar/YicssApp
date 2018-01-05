package com.canplay.repast_pad.mvp.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.canplay.repast_pad.R;
import com.canplay.repast_pad.base.BaseActivity;
import com.canplay.repast_pad.util.DensityUtil;
import com.canplay.repast_pad.view.Custom_TagBtn;
import com.canplay.repast_pad.view.FlexboxLayout;
import com.canplay.repast_pad.view.NavigationBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddDishCategoryActivity extends BaseActivity {


    @BindView(R.id.navigationbar)
    NavigationBar navigationbar;
    @BindView(R.id.fbl_tag)
    FlexboxLayout fblTag;


    private ArrayList<String> tags = new ArrayList<>();//标签数据

    /**
     * 初始化标签适配器
     */
    private void setTagAdapter() {
        fblTag.removeAllViews();
        if (tags.size() > 0) {
            for (int i = 0; i < tags.size(); i++) {
                final Custom_TagBtn tagBtn = createBaseFlexItemTextView(tags.get(i),1);
                final int position = i;
                tagBtn.setCustom_TagBtnListener(new Custom_TagBtn.Custom_TagBtnListener() {
                    @Override
                    public void clickDelete() {
                        for (int j = 0; j < tags.size(); j++) {

                        }
                    }
                });
                fblTag.addView(tagBtn, i);
            }
        }

    }




    /**
     * 创建流式布局item
     *
     * @param content
     * @return
     */
    public Custom_TagBtn createBaseFlexItemTextView(String content,int type) {
        FlexboxLayout.LayoutParams lp = new FlexboxLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.topMargin = DensityUtil.dip2px(this, 10);
        lp.leftMargin = DensityUtil.dip2px(this, 15);

        Custom_TagBtn view = (Custom_TagBtn) LayoutInflater.from(this).inflate(R.layout.dish_item, null);
        if(type==1){
            view.setBg(R.drawable.blue_regle);
        }else {
            view.setBg(R.drawable.white_regle);
        }

        view.setLayoutParams(lp);
        view.setCustomText(content);

        return view;
    }


    @Override
    public void initViews() {
        setContentView(R.layout.activity_add_dish_category);
        ButterKnife.bind(this);
    }

    @Override
    public void bindEvents() {

    }

    @Override
    public void initData() {

    }
}
