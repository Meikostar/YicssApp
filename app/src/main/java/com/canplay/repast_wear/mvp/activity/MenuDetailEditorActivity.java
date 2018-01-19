package com.canplay.repast_wear.mvp.activity;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.canplay.repast_wear.R;
import com.canplay.repast_wear.base.BaseActivity;
import com.canplay.repast_wear.base.BaseApplication;
import com.canplay.repast_wear.base.RxBus;
import com.canplay.repast_wear.base.SubscriptionBean;
import com.canplay.repast_wear.bean.COOK;
import com.canplay.repast_wear.mvp.adapter.CardPagerAdapter;
import com.canplay.repast_wear.mvp.adapter.recycle.ShadowTransformer;
import com.canplay.repast_wear.mvp.component.DaggerBaseComponent;
import com.canplay.repast_wear.mvp.present.CookClassifyContract;
import com.canplay.repast_wear.mvp.present.CookClassifyPresenter;
import com.canplay.repast_wear.util.TextUtil;
import com.canplay.repast_wear.view.BaseSelectDialog;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MenuDetailEditorActivity extends BaseActivity implements View.OnClickListener, CookClassifyContract.View {
    @Inject
    CookClassifyPresenter presenter;
    @BindView(R.id.line)
    View line;
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
    @BindView(R.id.relativeLayout)
    RelativeLayout relativeLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;






    private CardPagerAdapter mCardAdapter;
    private ShadowTransformer mCardShadowTransformer;
    private BaseSelectDialog dialog;

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    private int poistion;
    private int state;
    @Override
    public void initViews() {
        setContentView(R.layout.activity_menu_detail_editor);
        ButterKnife.bind(this);
        DaggerBaseComponent.builder().appComponent(((BaseApplication) getApplication()).getAppComponent()).build().inject(this);

        presenter.attachView(this);


        menuId = getIntent().getStringExtra("id");
        sort = getIntent().getStringExtra("sort");

        if (TextUtil.isNotEmpty(menuId)) {
            presenter.getMenuInfo(menuId);
        }
        dialog = new BaseSelectDialog(this, line);

        mCardAdapter = new CardPagerAdapter(this);
        mCardAdapter.setClickListener(new CardPagerAdapter.ItemClickListener() {
            @Override
            public void ItemClick(String id,int states,int poistions) {
                poistion=poistions;

                String st="";
                if(states==1){
                    st="0";
                    state=0;
                }else {
                    st="1";
                    state=1;
                }
            presenter.editCookbookState(id,st);
            }
        });

    }

    public void initDats(List<COOK> cooks) {
        for (COOK cook : cooks) {
            mCardAdapter.addCardItem(cook);
        }
        mCardShadowTransformer = new ShadowTransformer(viewPager, mCardAdapter);
        viewPager.setAdapter(mCardAdapter);
        viewPager.setPageTransformer(false, mCardShadowTransformer);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setCurrentItem(0);

//        mCardShadowTransformer = new ShadowTransformer(viewPager, mCardAdapter);
    }
    private int poistions;
    @Override
    public void bindEvents() {

        tvDelete.setOnClickListener(this);
        tvNew.setOnClickListener(this);
        topviewLeftLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                poistions=position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        dialog.setBindClickListener(new BaseSelectDialog.BindClickListener() {
            @Override
            public void tasteNum() {
                presenter.delMenuInfo(menuId);
            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_delete:
                dialog.show();
                break;
            case R.id.tv_new:
                Intent intent = new Intent(MenuDetailEditorActivity.this, MenuDetailActivity.class);
                intent.putExtra("cook",cook);
                intent.putExtra("sort",sort);
                startActivityForResult(intent,1);
                break;

        }
    }

    @Override
    public <T> void toList(List<T> list, int type) {

    }

    private List<COOK> datas;
    private COOK cook;
    private String menuId;
    private String sort;
    @Override
    public <T> void toEntity(T entity, int type) {
        if(type==8){
            RxBus.getInstance().send(SubscriptionBean.createSendBean(SubscriptionBean.MENU_REFASHS,""));
            showToasts("删除成功");
            finish();
        }else if(type==-1) {
            showToasts("设置成功");
            datas.get(poistion).state=state;
            mCardAdapter.setDatas(datas);
            mCardAdapter.notifyDataSetChanged();
            viewPager.removeAllViews();
            mCardShadowTransformer.setmAdapter(mCardAdapter);
            viewPager.setAdapter(mCardAdapter);
            viewPager.setPageTransformer(false, mCardShadowTransformer);
            viewPager.setCurrentItem(poistion);
        }else{
            cook = (COOK) entity;
            datas = cook.cookbookInfo;

            initDats(datas);
        }

    }

    @Override
    public void showTomast(String msg) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            presenter.getMenuInfo(menuId);
        }
    }
}
