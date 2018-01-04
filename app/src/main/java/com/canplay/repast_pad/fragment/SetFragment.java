package com.canplay.repast_pad.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.canplay.repast_pad.R;
import com.canplay.repast_pad.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * Created by mykar on 17/4/10.
 */
public class SetFragment extends BaseFragment implements View.OnClickListener{


    @BindView(R.id.ll_dishe)
    LinearLayout llDishe;
    @BindView(R.id.ll_practice)
    LinearLayout llPractice;
    @BindView(R.id.ll_garnish)
    LinearLayout llGarnish;
    @BindView(R.id.ll_tea)
    LinearLayout llTea;
    @BindView(R.id.ll_print)
    LinearLayout llPrint;
    @BindView(R.id.ll_exit)
    LinearLayout llExit;
    Unbinder unbinder;
    @BindView(R.id.tv_tea_money)
    TextView teaMoney;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_set, null);
        unbinder = ButterKnife.bind(this, view);
        initListener();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void initListener() {
        llDishe.setOnClickListener(this);
        llPractice.setOnClickListener(this);
        llGarnish.setOnClickListener(this);
        llTea.setOnClickListener(this);
        llPrint.setOnClickListener(this);
        llExit.setOnClickListener(this);

    }

    private void initView() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_dishe://菜品
                break;
            case R.id.ll_practice://做法
                break;
            case R.id.ll_garnish://配菜
                break;
            case R.id.ll_tea://茶位
                break;
            case R.id.ll_print://打印
                break;
            case R.id.ll_exit://退出
                break;
        }
    }
}
