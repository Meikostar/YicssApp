package com.canplay.repast_pad.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.canplay.repast_pad.R;
import com.canplay.repast_pad.base.BaseApplication;
import com.canplay.repast_pad.base.BaseFragment;
import com.canplay.repast_pad.bean.COOK;
import com.canplay.repast_pad.mvp.activity.AddDishesActivity;
import com.canplay.repast_pad.mvp.adapter.recycle.DishesRecycleAdapter;
import com.canplay.repast_pad.mvp.component.DaggerBaseComponent;
import com.canplay.repast_pad.mvp.present.CookClassifyContract;
import com.canplay.repast_pad.mvp.present.CookClassifyPresenter;
import com.canplay.repast_pad.mvp.present.LoginPresenter;
import com.canplay.repast_pad.view.DivItemDecoration;
import com.canplay.repast_pad.view.PopView_NavigationBar;
import com.canplay.repast_pad.view.PopView_NavigationBars;
import com.malinskiy.superrecyclerview.SuperRecyclerView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.antfortune.freeline.FreelineCore.getApplication;


/**
 * Created by mykar on 17/4/10.
 */
public class DishManageFragment extends BaseFragment implements View.OnClickListener ,CookClassifyContract.View{

    @Inject
    CookClassifyPresenter presenter;
    @BindView(R.id.super_recycle_view)
    SuperRecyclerView mSuperRecyclerView;
    @BindView(R.id.tv_new)
    TextView tvNew;
    @BindView(R.id.iv_choose)
    ImageView ivChoose;
    private GridLayoutManager layoutManager;

    private SwipeRefreshLayout.OnRefreshListener refreshListener;
    private DishesRecycleAdapter adapter;
    Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_dishes_manage, null);
        DaggerBaseComponent.builder().appComponent(((BaseApplication) getActivity().getApplication()).getAppComponent()).build().inject(this);
        presenter.attachView(this);
        ButterKnife.bind(this, view);

        initView();
        initListener();
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void initListener() {

        ivChoose.setOnClickListener(this);
        tvNew.setOnClickListener(this);
    }

    private void initView() {

        layoutManager = new GridLayoutManager(this.getActivity(), 2);
        mSuperRecyclerView.setLayoutManager(layoutManager);
        mSuperRecyclerView.addItemDecoration(new DivItemDecoration(2, true));

        mSuperRecyclerView.getMoreProgressView().getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;

        adapter = new DishesRecycleAdapter(getActivity());
        mSuperRecyclerView.setAdapter(adapter);
        refreshListener = new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                //  mSuperRecyclerView.showMoreProgress();

            }
        };

        mSuperRecyclerView.setRefreshListener(refreshListener);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_new://新建
                startActivity(new Intent(getActivity(), AddDishesActivity.class));
                break;
            case R.id.iv_choose://赛选
                popView_navigationBar.showPopView();
                break;

        }
    }
    public List<String> list=new ArrayList<>();
    private PopView_NavigationBars popView_navigationBar;
    private void initPopView() {

        popView_navigationBar = new PopView_NavigationBars(getActivity(),1);

        popView_navigationBar.showData(datas);
        popView_navigationBar.setClickListener(new PopView_NavigationBars.ItemCliskListeners() {
            @Override
            public void clickListener(String poition) {

                popView_navigationBar.dismiss();
            }

        });
    }
    List<COOK> datas;
    @Override
    public <T> void toList(List<T> list, int type) {
        datas= (List<COOK>) list;
        initPopView();
    }

    @Override
    public <T> void toEntity(T entity, int type) {

    }

    @Override
    public void showTomast(String msg) {

    }
}
