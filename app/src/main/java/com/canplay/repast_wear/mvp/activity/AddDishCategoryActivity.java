package com.canplay.repast_wear.mvp.activity;

import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.canplay.repast_wear.R;
import com.canplay.repast_wear.base.BaseActivity;
import com.canplay.repast_wear.base.BaseApplication;
import com.canplay.repast_wear.base.RxBus;
import com.canplay.repast_wear.base.SubscriptionBean;
import com.canplay.repast_wear.mvp.component.DaggerBaseComponent;
import com.canplay.repast_wear.mvp.model.BaseType;
import com.canplay.repast_wear.mvp.present.CookClassifyContract;
import com.canplay.repast_wear.mvp.present.CookClassifyPresenter;
import com.canplay.repast_wear.util.DensityUtil;
import com.canplay.repast_wear.util.TextUtil;
import com.canplay.repast_wear.view.Custom_TagBtn;
import com.canplay.repast_wear.view.FlexboxLayout;
import com.canplay.repast_wear.view.NavigationBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddDishCategoryActivity extends BaseActivity implements CookClassifyContract.View {

    @Inject
    CookClassifyPresenter presenter;
    @BindView(R.id.navigationbar)
    NavigationBar navigationbar;
    @BindView(R.id.fbl_tag)
    FlexboxLayout fblTag;
    @BindView(R.id.line)
    View line;
    @BindView(R.id.iv_img)
    ImageView ivImg;
    @BindView(R.id.tv_text)
    TextView tvText;
    @BindView(R.id.ll_bg)
    LinearLayout llBg;

    private int type = 1;//1代表做法分类2代表菜品
    private String ids;
    private List<BaseType> tags = new ArrayList<>();//标签数据
    private Map<String, String> map = new HashMap<>();

    @Override
    public void initViews() {
        setContentView(R.layout.activity_add_dish_category);
        ButterKnife.bind(this);
        DaggerBaseComponent.builder().appComponent(((BaseApplication) getApplication()).getAppComponent()).build().inject(this);
        presenter.attachView(this);
        navigationbar.setNavigationBarListener(this);
        navigationbar.setRightTxt("保存");
        type = getIntent().getIntExtra("type", 1);
        ids = getIntent().getStringExtra("ids");
        if (TextUtil.isNotEmpty(ids)) {
            String[] split = ids.split(",");
            map.clear();
            for (String id : split) {
                map.put(id, id);
            }
        }

        if (type == 1) {
            navigationbar.setNaviTitle("添加做法分类");
            presenter.getRecipesClassifyList();
        } else if (type == 2) {
            presenter.getFoodClassifyList();


        } else if (type == 3) {
            navigationbar.setNaviTitle("添加菜品分类");
            BaseType baseType = new BaseType();
            baseType.name = "土豆";
            BaseType baseType1 = new BaseType();
            baseType1.name = "经典基底";
            BaseType baseType2 = new BaseType();
            baseType2.name = "蔬菜基底";
            BaseType baseType3 = new BaseType();
            baseType3.name = "萝卜";
            BaseType baseType4 = new BaseType();
            baseType4.name = "腐竹";
            BaseType baseType5 = new BaseType();
            baseType5.name = "生菜";
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

    private List<BaseType> list = new ArrayList<>();

    @Override
    public void navigationRight() {
        super.navigationRight();
        list.clear();
        for (BaseType baseType : tags) {
            if (baseType.isChoos) {
                list.add(baseType);
            }
        }
        if (type == 1) {

                RxBus.getInstance().send(SubscriptionBean.createSendBean(SubscriptionBean.ADD_FEILEI, list));
                finish();

        } else {

                RxBus.getInstance().send(SubscriptionBean.createSendBean(SubscriptionBean.ADD_PEICAI, list));
                finish();

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

                        if (type == 1) {
                            for (int j = 0; j < tags.size(); j++) {
                                if (position == j) {
                                    if (tags.get(j).isChoos) {
                                        tags.get(j).isChoos = false;
                                    } else {
                                        tags.get(j).isChoos = true;
                                    }

                                }
                            }
                        } else if (type == 2) {
                            for (int j = 0; j < tags.size(); j++) {
                                if (position == j) {
                                    tags.remove(j);
                                }
                            }

                        } else if (type == 3) {
                            for (int j = 0; j < tags.size(); j++) {
                                if (position == j) {
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
        if (content.isChoos) {
            view.setBg(R.drawable.blue_regle);
            view.setColors(R.color.white);
        } else {
            view.setBg(R.drawable.white_regle);
            view.setColors(R.color.slow_black);
        }
        int width = (int) DensityUtil.getWidth(this) / 3;
        String name = content.name;
        TextPaint textPaint = new TextPaint();
        textPaint.setTextSize(15);
        int with = (int) textPaint.measureText(name);
        view.setSize(with + 22, 60, 15, 1);
        view.setLayoutParams(lp);
        view.setCustomText(content.name);

        return view;
    }

    @Override
    public <T> void toList(List<T> list, int type) {

    }

    @Override
    public <T> void toEntity(T entity, int type) {
        tags = (List<BaseType>) entity;
        if(tags.size()==0){
            llBg.setVisibility(View.VISIBLE);
            fblTag.setVisibility(View.GONE);
            if (type == 1) {
                ivImg.setImageResource(R.drawable.nocaipin);
                tvText.setText("暂无做法分类");
            } else {
                ivImg.setImageResource(R.drawable.nocaipin);
                tvText.setText("暂无配菜分类");
            }

        }else {
            llBg.setVisibility(View.GONE);
            fblTag.setVisibility(View.VISIBLE);
        }
        for (BaseType base : tags) {
            String id = map.get(base.classifyId);
            if (TextUtil.isNotEmpty(id)) {
                base.isChoos = true;
            }
        }
        setTagAdapter();
    }

    @Override
    public void showTomast(String msg) {

    }



}

