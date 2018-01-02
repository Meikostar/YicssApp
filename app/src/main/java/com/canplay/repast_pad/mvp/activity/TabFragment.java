package com.canplay.repast_pad.mvp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.canplay.repast_pad.R;
import com.canplay.repast_pad.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

//未应答
public class TabFragment extends BaseFragment {


    Unbinder unbinder;
    @BindView(R.id.icon)
    ImageView icon;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.name_english)
    TextView nameEnglish;
    @BindView(R.id.add)
    TextView add;
    @BindView(R.id.name_02)
    TextView name02;
    @BindView(R.id.name_english_02)
    TextView nameEnglish02;
    @BindView(R.id.add_02)
    TextView add02;
    @BindView(R.id.icon_02)
    ImageView icon02;

    public static TabFragment newInstance() {
        TabFragment fragment = new TabFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle bundle) {
        super.onCreateView(inflater, container, bundle);
        View view = inflater.inflate(R.layout.item_taste, null);
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
