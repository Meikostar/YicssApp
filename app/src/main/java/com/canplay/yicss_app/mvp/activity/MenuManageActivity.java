package com.canplay.yicss_app.mvp.activity;

import android.widget.ListView;

import com.canplay.yicss_app.R;
import com.canplay.yicss_app.base.BaseActivity;

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
