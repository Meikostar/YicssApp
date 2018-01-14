package com.canplay.repast_pad.fragment;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.canplay.repast_pad.R;
import com.canplay.repast_pad.base.BaseApplication;
import com.canplay.repast_pad.base.BaseFragment;
import com.canplay.repast_pad.bean.ORDER;
import com.canplay.repast_pad.mvp.activity.OrderDetailActivity;
import com.canplay.repast_pad.mvp.adapter.recycle.OrderMangerRecycleAdapter;
import com.canplay.repast_pad.mvp.component.DaggerBaseComponent;
import com.canplay.repast_pad.mvp.present.CookClassifyContract;
import com.canplay.repast_pad.mvp.present.CookClassifyPresenter;
import com.canplay.repast_pad.view.DivItemDecoration;
import com.canplay.repast_pad.view.PhotoPopupWindow;
import com.canplay.repast_pad.view.PopView_NavigationBar;
import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by mykar on 17/4/10.
 */
public class OrderMangerFragment extends BaseFragment implements View.OnClickListener ,CookClassifyContract.View{

    @Inject
    CookClassifyPresenter presenter;
    @BindView(R.id.fl_choose)
    FrameLayout flChoose;
    @BindView(R.id.ll_dishe)
    RelativeLayout llDishe;
    @BindView(R.id.super_recycle_view)
    SuperRecyclerView mSuperRecyclerView;
    Unbinder unbinder;
    @BindView(R.id.line)
    View line;
//    private List<AD> list = new ArrayList<>();
    private SwipeRefreshLayout.OnRefreshListener refreshListener;
    private OrderMangerRecycleAdapter adapter;
    private LinearLayoutManager mLinearLayoutManager;
    private final int TYPE_PULL_REFRESH = 1;
    private final int TYPE_PULL_MORE = 2;
    private final int TYPE_REMOVE = 3;
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_manger, null);
        unbinder = ButterKnife.bind(this, view);
        DaggerBaseComponent.builder().appComponent(((BaseApplication) getActivity().getApplication()).getAppComponent()).build().inject(this);
        presenter.attachView(this);
        initView();
        initListener();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void initListener() {
        initPopView();
        flChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popView_navigationBar.showPopView();
            }
        });

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
   public int currpage=1;
    private void initView() {

        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mSuperRecyclerView.setLayoutManager(mLinearLayoutManager);
        mSuperRecyclerView.addItemDecoration(new DivItemDecoration(2, true));
        mSuperRecyclerView.getMoreProgressView().getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
        adapter=new OrderMangerRecycleAdapter(getActivity());
        mSuperRecyclerView.setAdapter(adapter);
        reflash();
        // mSuperRecyclerView.setRefreshing(false);
        refreshListener = new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                // mSuperRecyclerView.showMoreProgress();
                presenter.getAppOrderList("",currpage,state,TYPE_PULL_REFRESH);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSuperRecyclerView.hideMoreProgress();
                    }
                }, 2000);
            }
        };
        mSuperRecyclerView.setRefreshListener(refreshListener);
        adapter.setClickListener(new OrderMangerRecycleAdapter.OnItemClickListener() {
            @Override
            public void clickListener(int poiston, String id) {
                Intent intent = new Intent(getActivity(), OrderDetailActivity.class);
                startActivity(intent);
            }
        });
    }
    public List<ORDER> list=new ArrayList<>();
    public void onDataLoaded(int loadtype,final boolean haveNext, List<ORDER> datas) {

        if (loadtype == TYPE_PULL_REFRESH) {
            currpage=1;
            list.clear();
            for (ORDER info : datas) {
                list.add(info);
            }
        } else {
            for (ORDER info : datas) {
                list.add(info);
            }
        }
        adapter.setDatas(list);
        adapter.notifyDataSetChanged();
//        mSuperRecyclerView.setLoadingMore(false);
        mSuperRecyclerView.hideMoreProgress();
        /**
         * 判断是否需要加载更多，与服务器的总条数比
         */
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
                                presenter.getAppOrderList("",currpage,state,TYPE_PULL_MORE);

                        }
                    }, 2000);
                }
            }, 1);
        } else {
            mSuperRecyclerView.removeMoreListener();
            mSuperRecyclerView.hideMoreProgress();

        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

        }
    }
    private int state=-1;
    private PopView_NavigationBar popView_navigationBar;
    private void initPopView() {
//        mWindowAddPhoto = new PhotoPopupWindow(getActivity());
        popView_navigationBar = new PopView_NavigationBar(getActivity(),1);

        popView_navigationBar.setView(line);
        popView_navigationBar.setClickListener(new PopView_NavigationBar.ItemCliskListeners() {
            @Override
            public void clickListener(int poition) {
                switch (poition) {
                    case 0://全部
                        state=-1;
                        break;
                    case 1://待接单
                        state=0;
                        break;
                    case 2://待结账
                        state=1;
                        break;
                    case 3://已完成
                        state=2;
                        break;
                    case 4://已撤销
                        state=4;
                        break;

                }
                popView_navigationBar.dismiss();
            }

        });
    }

    @Override
    public <T> void toList(List<T> list, int type) {

    }
   private ORDER data;
    @Override
    public <T> void toEntity(T entity, int type) {
        data= (ORDER) entity;
        onDataLoaded(type,data.hasNext!=0,data.list);
    }

    @Override
    public void showTomast(String msg) {

    }
}
