package com.canplay.repast_pad.mvp.activity;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.canplay.repast_pad.R;
import com.canplay.repast_pad.base.BaseActivity;
import com.canplay.repast_pad.base.BaseApplication;
import com.canplay.repast_pad.base.RxBus;
import com.canplay.repast_pad.base.SubscriptionBean;
import com.canplay.repast_pad.mvp.component.DaggerBaseComponent;
import com.canplay.repast_pad.mvp.model.BaseType;
import com.canplay.repast_pad.mvp.present.CookClassifyContract;
import com.canplay.repast_pad.mvp.present.CookClassifyPresenter;
import com.canplay.repast_pad.util.DensityUtil;
import com.canplay.repast_pad.util.TextUtil;
import com.canplay.repast_pad.view.AddmenuDialog;
import com.canplay.repast_pad.view.Custom_TagBtn;
import com.canplay.repast_pad.view.Custom_TagBtn_del;
import com.canplay.repast_pad.view.FlexboxLayout;
import com.canplay.repast_pad.view.NavigationBar;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddMenueCategoryActivity extends BaseActivity  implements CookClassifyContract.View {

    @Inject
    CookClassifyPresenter presenter;
    @BindView(R.id.navigationbar)
    NavigationBar navigationbar;
    @BindView(R.id.fbl_tag)
    FlexboxLayout fblTag;
    @BindView(R.id.line)
    View line;
    private int type=1;//1代表从设置进来可以删除2代表菜品进来只能增加
    private List<BaseType> tags = new ArrayList<>();//标签数据
    private AddmenuDialog dialog;
    private String title;
    private int status;//0表示列表1表示添加2表示删除
    @Override
    public void initViews() {
        setContentView(R.layout.activity_add_dish_category);
        ButterKnife.bind(this);
        DaggerBaseComponent.builder().appComponent(((BaseApplication) getApplication()).getAppComponent()).build().inject(this);
        presenter.attachView(this);
        navigationbar.setNavigationBarListener(this);
        type=getIntent().getIntExtra("type",1);
        title=getIntent().getStringExtra("name");
        if(TextUtil.isNotEmpty(title)){
            navigationbar.setNaviTitle(title);
        }else {
            navigationbar.setNaviTitle("菜品分类");
        }

        if(type==1){
            presenter.getCookClassifyList();
        }else if(type==2){
            navigationbar.hide();
        }else if(type==3){
            presenter.getFoodClassifyList();
        }else if(type==4){
            presenter.getRecipesClassifyList();
        }


        dialog = new AddmenuDialog(this,line);
    }


    @Override
    public void bindEvents() {
        dialog.setBindClickListener(new AddmenuDialog.BindClickListener() {
            @Override
            public void teaMoney(String money) {
                status=1;
                if(type==1){
                    presenter.addBookClassfy(money);
                }else if(type==3){
                    presenter.addRecipesClassify(money);
                }else if(type==4){
                    presenter.addFoodClassify(money);
                }

            }
        });
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
        dialog.show();

    }

    /**
     * 初始化标签适配器
     */
    private int poistion;
    private void setTagAdapter() {
        fblTag.removeAllViews();
        if (tags.size() > 0) {
            for (int i = 0; i < tags.size(); i++) {
                final Custom_TagBtn_del tagBtn = createBaseFlexItemTextView(tags.get(i));
                final int position = i;
                tagBtn.setCustom_TagBtnListener(new Custom_TagBtn_del.Custom_TagBtnListener() {
                    @Override
                    public void clickDelete(int type) {

                        if(type==1){
                            for (int j = 0; j < tags.size(); j++) {
                                if(position==j){
                                   if(type==2){
                                       RxBus.getInstance().send(SubscriptionBean.createSendBean(SubscriptionBean.ADD_MENU,tags.get(j)));
                                      finish();
                                   }

                                }
                            }
                        }else if(type==2){
                            for (int j = 0; j < tags.size(); j++) {

                                if(position==j){
                                    status=2;
                                    poistion=j;
                                    if(type==1){
                                        presenter.delBookClassfy(tags.get(j).cbClassifyId);
                                    }else if(type==3){
                                        presenter.delRecipesClassify(tags.get(j).classifyId);
                                    }else if(type==4){
                                        presenter.delFoodClassify(tags.get(j).classifyId);
                                    }
                                }
                            }

                        }else if(type==3){
                            for (int j = 0; j < tags.size(); j++) {
                                if(position==j){
                                    tags.get(j).status=1;
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
    public Custom_TagBtn_del createBaseFlexItemTextView(BaseType content) {
        FlexboxLayout.LayoutParams lp = new FlexboxLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.topMargin = DensityUtil.dip2px(this, 10);
        lp.leftMargin = DensityUtil.dip2px(this, 6);
        lp.rightMargin = DensityUtil.dip2px(this, 6);


        Custom_TagBtn_del view = (Custom_TagBtn_del) LayoutInflater.from(this).inflate(R.layout.dish_item_del, null);

        if(type==1){
            view.setCannotClick(true);
        }else {
            view.setCannotClick(false);
        }
        if(content.status==1){
            view.show();
        }
        int width=(int) DensityUtil.getWidth(this)/3;
        view.setSize(DensityUtil.px2dip(this, width)-14,60,15);
        view.setLayoutParams(lp);
        view.setCustomText(content.name);

        return view;
    }


    @Override
    public <T> void toList(List<T> list, int type) {

    }
    @Override
    public <T> void toEntity(T entity, int type) {
        if(status==0){
            tags= (List<BaseType>) entity;
            setTagAdapter();
        }else if(status==1){
            BaseType  tag= (BaseType) entity;
            tags.add(tag);
        }else if(status==2){
            tags.remove(poistion);
        }

    }

    @Override
    public void showTomast(String msg) {

    }
}
