package com.canplay.repast_pad.mvp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.canplay.repast_pad.R;
import com.canplay.repast_pad.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

//未应答
public class TabMainFragment extends BaseFragment {


    @BindView(R.id.taste_name)
    TextView tasteName;
    @BindView(R.id.taste_name_english)
    TextView tasteNameEnglish;
    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.add)
    TextView add;
    Unbinder unbinder;

    public static TabMainFragment newInstance() {
        TabMainFragment fragment = new TabMainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle bundle) {
        super.onCreateView(inflater, container, bundle);
        View view = inflater.inflate(R.layout.fragment_tab_main, null);
        initView();
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    private void initView() {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void alert(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

}
