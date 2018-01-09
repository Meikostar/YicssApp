package com.canplay.repast_pad.mvp.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.canplay.repast_pad.R;
import com.canplay.repast_pad.base.BaseActivity;
import com.canplay.repast_pad.base.BaseApplication;
import com.canplay.repast_pad.bean.COOK;
import com.canplay.repast_pad.mvp.adapter.CardFragmentPagerAdapter;
import com.canplay.repast_pad.mvp.adapter.CardPagerAdapter;
import com.canplay.repast_pad.mvp.adapter.recycle.ShadowTransformer;
import com.canplay.repast_pad.mvp.adapter.viewholder.CardItem;
import com.canplay.repast_pad.mvp.component.DaggerBaseComponent;
import com.canplay.repast_pad.mvp.model.BaseType;
import com.canplay.repast_pad.mvp.present.CookClassifyContract;
import com.canplay.repast_pad.mvp.present.CookClassifyPresenter;
import com.canplay.repast_pad.util.DensityUtil;
import com.canplay.repast_pad.util.TextUtil;
import com.canplay.repast_pad.view.BaseSelectDialog;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MenuDetailEditorActivity extends BaseActivity  implements View.OnClickListener, CookClassifyContract.View {
    @Inject
    CookClassifyPresenter presenter;
    @BindView(R.id.top_view_back)
    ImageView topViewBack;
    @BindView(R.id.topview_left_layout)
    LinearLayout topviewLeftLayout;
    @BindView(R.id.navigationbar_title)
    TextView navigationbarTitle;
    @BindView(R.id.tv_delete)
    TextView tvDelete;
    @BindView(R.id.tv_new)
    TextView tvNew;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tv_chines)
    TextView tvChines;
    @BindView(R.id.tv_english)
    TextView tvEnglish;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.tv_specif)
    TextView tvSpecif;
    @BindView(R.id.tv_taste)
    TextView tvTaste;
    @BindView(R.id.tv_sauce)
    TextView tvSauce;
    @BindView(R.id.tv_staus)
    TextView tvStaus;
    @BindView(R.id.line)
    View line;

    private String id;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    private CardPagerAdapter mCardAdapter;
    private ShadowTransformer mCardShadowTransformer;
    private BaseSelectDialog dialog;
    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    @Override
    public void initViews() {
        setContentView(R.layout.activity_menu_detail_editor);
        ButterKnife.bind(this);
        DaggerBaseComponent.builder().appComponent(((BaseApplication) getApplication()).getAppComponent()).build().inject(this);

        presenter.attachView(this);


        id=getIntent().getStringExtra("id");

        if(TextUtil.isNotEmpty(id)){
            presenter.getMenuInfo(id);
        }
        dialog=new  BaseSelectDialog(this,line);

        mCardAdapter = new CardPagerAdapter(this);




    }
    public void initDats(List<COOK> cooks){
        for(COOK cook:cooks){
            mCardAdapter.addCardItem(cook.imgUrl);
        }

        mCardShadowTransformer = new ShadowTransformer(viewPager, mCardAdapter);
        viewPager.setAdapter(mCardAdapter);
        viewPager.setPageTransformer(false, mCardShadowTransformer);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setCurrentItem(0);

        mCardShadowTransformer = new ShadowTransformer(viewPager, mCardAdapter);
    }
    @Override
    public void bindEvents() {
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                   setInfo(datas.get(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        dialog.setBindClickListener(new BaseSelectDialog.BindClickListener() {
            @Override
            public void tasteNum() {

            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_delete:
                dialog.show();
                break;
            case R.id.tv_new:
                Intent intent = new Intent(MenuDetailEditorActivity.this, MenuDetailActivity.class);
                startActivity(intent);
                break;

        }
    }

    @Override
    public <T> void toList(List<T> list, int type) {

    }
    private List<COOK> datas;
    @Override
    public <T> void toEntity(T entity, int type) {
        COOK cook= (COOK) entity;
        datas=cook.cookbookInfo;
        initDats(datas);
    }

    @Override
    public void showTomast(String msg) {

    }

    public void setInfo(COOK cok){
        tvChines.setText(cok.cnName);
        tvMoney.setText(cok.price);
        List<BaseType> recipesClassifyInfos = cok.recipesClassifyInfos;
        List<BaseType> foodClassifyInfos = cok.foodClassifyInfos;
        if(recipesClassifyInfos!=null&&recipesClassifyInfos.size()>0){
            int i=0;
            String name="";
            for(BaseType base:recipesClassifyInfos){
                if(i==0){
                    name=base.name;
                }else {
                    name=name+","+ base.name;;
                }
            }
            tvTaste.setText(name);
        }  if(foodClassifyInfos!=null&&foodClassifyInfos.size()>0){
            int i=0;
            String name="";
            for(BaseType base:foodClassifyInfos){
                if(i==0){
                    name=base.name;
                }else {
                    name=name+","+ base.name;;
                }
            }
            tvSauce.setText(name);
        }
       if(cok.state==0){
           tvStaus.setText("在售");
       }else {
           tvStaus.setText("售空");
       }
    }
}
