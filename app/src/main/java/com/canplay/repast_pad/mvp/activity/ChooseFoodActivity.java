package com.canplay.repast_pad.mvp.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.canplay.repast_pad.R;
import com.canplay.repast_pad.base.BaseActivity;
import com.canplay.repast_pad.base.BaseApplication;
import com.canplay.repast_pad.base.RxBus;
import com.canplay.repast_pad.base.SubscriptionBean;
import com.canplay.repast_pad.bean.COOK;
import com.canplay.repast_pad.mvp.adapter.recycle.DishesRecycleAdapter;
import com.canplay.repast_pad.mvp.component.DaggerBaseComponent;
import com.canplay.repast_pad.mvp.model.BaseType;
import com.canplay.repast_pad.mvp.present.CookClassifyContract;
import com.canplay.repast_pad.mvp.present.CookClassifyPresenter;
import com.canplay.repast_pad.view.DivItemDecoration;
import com.canplay.repast_pad.view.PopView_NavigationBars;
import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Subscription;
import rx.functions.Action1;

public class ChooseFoodActivity extends BaseActivity implements View.OnClickListener,CookClassifyContract.View{

    @Inject
    CookClassifyPresenter presenter;
    @BindView(R.id.super_recycle_view)
    SuperRecyclerView mSuperRecyclerView;
    @BindView(R.id.tv_new)
    TextView tvNew;
    @BindView(R.id.iv_choose)
    ImageView ivChoose;
    @BindView(R.id.top_view_back)
    ImageView top_view_back;
    private GridLayoutManager layoutManager;

    private SwipeRefreshLayout.OnRefreshListener refreshListener;
    private DishesRecycleAdapter adapter;
    Unbinder unbinder;
    private final int TYPE_PULL_REFRESH = 1;
    private final int TYPE_PULL_MORE = 2;
    private int currpage = 0;//第几页
    private Subscription mSubscription;
    private String id;
    @Override
    public void initViews() {
        setContentView(R.layout.activity_choose_food);
        ButterKnife.bind(this);
        DaggerBaseComponent.builder().appComponent(((BaseApplication) getApplication()).getAppComponent()).build().inject(this);
        presenter.attachView(this);
        classifyId= getIntent().getStringExtra("id");
        presenter.getCookClassifyList();
        initView();
        reflash();
    }

    @Override
    public void bindEvents() {
        top_view_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ivChoose.setOnClickListener(this);
        tvNew.setOnClickListener(this);
        adapter.setItemCikcListener(new DishesRecycleAdapter.ItemClikcListener() {
            @Override
            public void itemClick(COOK data) {
                Intent intent = new Intent();
                intent.putExtra("cook",data);
                setResult(RESULT_OK,intent);
                finish();
    }
});
    }

    @Override
    public void initData() {

    }


    @Override
    public void onResume() {
        super.onResume();

    }
    private boolean isFirst=true;


    private String classifyId;
    private void initView() {

        layoutManager = new GridLayoutManager(this, 2);
        mSuperRecyclerView.setLayoutManager(layoutManager);
        mSuperRecyclerView.addItemDecoration(new DivItemDecoration(2, true));

        mSuperRecyclerView.getMoreProgressView().getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;

        adapter = new DishesRecycleAdapter(this);
        mSuperRecyclerView.setAdapter(adapter);
        refreshListener = new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                //  mSuperRecyclerView.showMoreProgress();
                presenter.getCookbookList(classifyId,1,TYPE_PULL_REFRESH);

            }
        };

        mSuperRecyclerView.setRefreshListener(refreshListener);

    }
    private void reflash(){
        if(mSuperRecyclerView!=null) {
            //实现自动下拉刷新功能
            mSuperRecyclerView.getSwipeToRefresh().post(new Runnable(){
                @Override
                public void run() {
                    mSuperRecyclerView.setRefreshing(true);//执行下拉刷新的动画
                    refreshListener.onRefresh();//执行数据加载操作
                }
            });
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_new://新建
//                startActivity(new Intent(getActivity(), AddDishesActivity.class));
                break;
            case R.id.iv_choose://赛选
//                popView_navigationBar.showPopView();
                break;

        }
    }
    public List<String> list=new ArrayList<>();
    private PopView_NavigationBars popView_navigationBar;
    private void initPopView() {

        popView_navigationBar = new PopView_NavigationBars(this,1);

        popView_navigationBar.showData(datas);
        popView_navigationBar.setClickListener(new PopView_NavigationBars.ItemCliskListeners() {
            @Override
            public void clickListener(String id) {

                popView_navigationBar.dismiss();
            }

        });
    }


    private List<COOK> cooks=new ArrayList<>();
    public void onDataLoaded(int loadType, final boolean haveNext, List<COOK> list) {

        if (loadType == TYPE_PULL_REFRESH) {
            currpage = 1;
            cooks.clear();
            for (COOK info : list) {
                cooks.add(info);
            }
        } else {
            for (COOK info : list) {
                cooks.add(info);
            }
        }

        adapter.setDatas(cooks);
        adapter.notifyDataSetChanged();


        mSuperRecyclerView.hideMoreProgress();

        if (haveNext) {
            mSuperRecyclerView.setupMoreListener(new OnMoreListener() {
                @Override
                public void onMoreAsked(int overallItemsCount, int itemsBeforeMore, int maxLastVisiblePosition) {
                    currpage++;
                    mSuperRecyclerView.showMoreProgress();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (haveNext)
                                mSuperRecyclerView.hideMoreProgress();
                            presenter.getCookbookList(classifyId,currpage,TYPE_PULL_MORE);

                        }
                    }, 2000);
                }
            }, 1);
        } else {
            mSuperRecyclerView.removeMoreListener();
            mSuperRecyclerView.hideMoreProgress();

        }


    }
    List<BaseType> datas;
    @Override
    public <T> void toList(List<T> list, int type) {
        datas= (List<BaseType>) list;
        BaseType baseType = new BaseType();
        baseType.name="全部";
        baseType.cbClassifyId="";
        datas.add(0,baseType);
        initPopView();
    }

    @Override
    public <T> void toEntity(T entity, int type) {
        COOK data= (COOK) entity;
        onDataLoaded(type,data.hasNext!=0,data.cookbookInfos);
    }

    @Override
    public void showTomast(String msg) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }



}
