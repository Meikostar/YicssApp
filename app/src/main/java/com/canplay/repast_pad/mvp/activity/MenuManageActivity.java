package com.canplay.repast_pad.mvp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.canplay.repast_pad.R;
import com.canplay.repast_pad.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MenuManageActivity extends BaseActivity {

    @BindView(R.id.list_view)
    ListView listView;



    @Override
    public void initViews() {
        setContentView(R.layout.activity_menu_manage);
        ButterKnife.bind(this);
    }

    @Override
    public void bindEvents() {

    }

    @Override
    public void initData() {

    }
}
