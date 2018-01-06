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
import com.canplay.repast_pad.base.BaseFragment;
import com.canplay.repast_pad.mvp.activity.OrderDetailActivity;
import com.canplay.repast_pad.mvp.adapter.recycle.OrderMangerRecycleAdapter;
import com.canplay.repast_pad.view.DivItemDecoration;
import com.canplay.repast_pad.view.PhotoPopupWindow;
import com.canplay.repast_pad.view.PopView_NavigationBar;
import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by mykar on 17/4/10.
 */
public class OrderMangerFragment extends BaseFragment implements View.OnClickListener {


    @BindView(R.id.fl_choose)
    FrameLayout flChoose;
    @BindView(R.id.ll_dishe)
    RelativeLayout llDishe;
    @BindView(R.id.super_recycle_view)
    SuperRecyclerView mSuperRecyclerView;
    Unbinder unbinder;

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

    private void initView() {

        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mSuperRecyclerView.setLayoutManager(mLinearLayoutManager);
        mSuperRecyclerView.addItemDecoration(new DivItemDecoration(2, true));
        mSuperRecyclerView.getMoreProgressView().getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
        adapter=new OrderMangerRecycleAdapter(getActivity());
        mSuperRecyclerView.setAdapter(adapter);
        // mSuperRecyclerView.setRefreshing(false);
        refreshListener = new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                // mSuperRecyclerView.showMoreProgress();



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


    public void onDataLoaded(int loadtype, long serverSize, List<String> datas) {

        if (loadtype == TYPE_PULL_REFRESH) {

//            for (AD info : datas) {
//                list.add(info);
//            }
        } else {
//            for (AD info : datas) {
//                list.add(info);
//            }
        }

        mSuperRecyclerView.setLoadingMore(false);
        mSuperRecyclerView.hideMoreProgress();
        /**
         * 判断是否需要加载更多，与服务器的总条数比
         */
        if (2 < serverSize) {
            mSuperRecyclerView.setupMoreListener(new OnMoreListener() {
                @Override
                public void onMoreAsked(int overallItemsCount, int itemsBeforeMore, int maxLastVisiblePosition) {

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
//                            model.getFirstHome(currpage,TYPE_PULL_MORE,HomeFragment.this);
                        }
                    }, 2000);

                }
            }, 1);
        } else {

            mSuperRecyclerView.setRefreshing(false);
            mSuperRecyclerView.setLoadingMore(false);
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
    private PopView_NavigationBar popView_navigationBar;
    private void initPopView() {
//        mWindowAddPhoto = new PhotoPopupWindow(getActivity());
        popView_navigationBar = new PopView_NavigationBar(getActivity());


        popView_navigationBar.setClickListener(new PopView_NavigationBar.ItemCliskListeners() {
            @Override
            public void clickListener(int poition) {
                switch (poition) {
                    case 0://全部
                        break;
                    case 1://待接单
                        break;
                    case 2://待结账
                        break;
                    case 3://已完成
                        break;
                    case 4://已撤销
                        break;

                }
                popView_navigationBar.dismiss();
            }

        });
    }
}
