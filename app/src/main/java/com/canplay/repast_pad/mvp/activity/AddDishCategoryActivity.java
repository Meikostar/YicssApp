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
import com.canplay.repast_pad.base.RxBus;
import com.canplay.repast_pad.base.SubscriptionBean;
import com.canplay.repast_pad.mvp.model.BaseType;
import com.canplay.repast_pad.util.DensityUtil;
import com.canplay.repast_pad.view.Custom_TagBtn;
import com.canplay.repast_pad.view.FlexboxLayout;
import com.canplay.repast_pad.view.NavigationBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddDishCategoryActivity extends BaseActivity {


    @BindView(R.id.navigationbar)
    NavigationBar navigationbar;
    @BindView(R.id.fbl_tag)
    FlexboxLayout fblTag;

    private int type=1;//1代表做法分类2代表菜品
    private List<BaseType> tags = new ArrayList<>();//标签数据
    @Override
    public void initViews() {
        setContentView(R.layout.activity_add_dish_category);
        ButterKnife.bind(this);
        navigationbar.setNavigationBarListener(this);
        type=getIntent().getIntExtra("type",1);
        if(type==1){
           navigationbar.setNaviTitle("添加做法分类");
            BaseType baseType = new BaseType();
            baseType.name="Meiko";
            BaseType baseType1 = new BaseType();
            baseType1.name="Hello";
            BaseType baseType2 = new BaseType();
            baseType2.name="Good";
            BaseType baseType3 = new BaseType();
            baseType3.name="Case";
            BaseType baseType4 = new BaseType();
            baseType4.name="Sun";
            BaseType baseType5 = new BaseType();
            baseType5.name="Moon";
            tags.add(baseType);
            tags.add(baseType1);
            tags.add(baseType2);
            tags.add(baseType3);
            tags.add(baseType4);
            setTagAdapter();
        }else if(type==2){
            BaseType baseType = new BaseType();
            baseType.name="土豆";
            BaseType baseType1 = new BaseType();
            baseType1.name="经典基底";
            BaseType baseType2 = new BaseType();
            baseType2.name="蔬菜基底";
            BaseType baseType3 = new BaseType();
            baseType3.name="萝卜";
            BaseType baseType4 = new BaseType();
            baseType4.name="腐竹";
            BaseType baseType5 = new BaseType();
            baseType5.name="生菜";
            tags.add(baseType);
            tags.add(baseType1);
            tags.add(baseType2);
            tags.add(baseType3);
            tags.add(baseType4);
            tags.add(baseType5);
            setTagAdapter();
        }else if(type==3){
            navigationbar.setNaviTitle("菜品分类");
            BaseType baseType = new BaseType();
            baseType.name="土豆";
            BaseType baseType1 = new BaseType();
            baseType1.name="经典基底";
            BaseType baseType2 = new BaseType();
            baseType2.name="蔬菜基底";
            BaseType baseType3 = new BaseType();
            baseType3.name="萝卜";
            BaseType baseType4 = new BaseType();
            baseType4.name="腐竹";
            BaseType baseType5 = new BaseType();
            baseType5.name="生菜";
            tags.add(baseType);
            tags.add(baseType1);
            tags.add(baseType2);
            tags.add(baseType3);
            tags.add(baseType4);
            tags.add(baseType5);
            setTagAdapter();
        }
    }


    @Override
    public void bindEvents() {

    }

    @Override
    public void initData() {

    }
    private List<BaseType> list=new ArrayList<>();
    @Override
    public void navigationRight() {
        super.navigationRight();
        list.clear();
        for(BaseType baseType:tags){
            if(baseType.isChoos){
                list.add(baseType);
            }
        }
        if(type==1){
            if(list.size()>0){
                RxBus.getInstance().send(SubscriptionBean.createSendBean(SubscriptionBean.ADD_FEILEI,list));
                finish();
            }else {
                showToast("请选择做法分类");
            }
        }else {
            if(list.size()>0){
                RxBus.getInstance().send(SubscriptionBean.createSendBean(SubscriptionBean.ADD_PEICAI,list));
                finish();
            }else {
                showToast("请选择配菜分类");
            }
        }

    }

    /**
     * 初始化标签适配器
     */
    private void setTagAdapter() {
        fblTag.removeAllViews();
        if (tags.size() > 0) {
            for (int i = 0; i < tags.size(); i++) {
                final Custom_TagBtn tagBtn = createBaseFlexItemTextView(tags.get(i));
                final int position = i;
                tagBtn.setCustom_TagBtnListener(new Custom_TagBtn.Custom_TagBtnListener() {
                    @Override
                    public void clickDelete(int type) {

                        if(type==1){
                            for (int j = 0; j < tags.size(); j++) {
                                if(position==j){
                                    tags.get(j).isChoos=true;
                                }
                            }
                        }else if(type==2){
                            for (int j = 0; j < tags.size(); j++) {
                                if(position==j){
                                    tags.remove(j);
                                }
                            }

                        }else if(type==3){
                            for (int j = 0; j < tags.size(); j++) {
                                if(position==j){
                                    tags.remove(j);
                                }
                            }

                        }

                        setTagAdapter();
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
    public Custom_TagBtn createBaseFlexItemTextView(BaseType content) {
        FlexboxLayout.LayoutParams lp = new FlexboxLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.topMargin = DensityUtil.dip2px(this, 10);
        lp.leftMargin = DensityUtil.dip2px(this, 7);
        lp.rightMargin = DensityUtil.dip2px(this, 7);


        Custom_TagBtn view = (Custom_TagBtn) LayoutInflater.from(this).inflate(R.layout.dish_item, null);
        if(content.isChoos){
            view.setBg(R.drawable.blue_regle);
            view.setColors(R.color.white);
        }else {
            view.setBg(R.drawable.white_regle);
            view.setColors(R.color.slow_black);
        }
        int width=(int) DensityUtil.getWidth(this)/3;
        view.setSize(DensityUtil.px2dip(this, width)-20,60,15);
        view.setLayoutParams(lp);
        view.setCustomText(content.name);

        return view;
    }



}
