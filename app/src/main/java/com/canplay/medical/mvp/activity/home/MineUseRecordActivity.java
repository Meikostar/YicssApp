package com.canplay.medical.mvp.activity.home;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.canplay.medical.R;
import com.canplay.medical.base.BaseActivity;
import com.canplay.medical.mvp.adapter.RemindMedicatAdapter;
import com.canplay.medical.mvp.adapter.UserRecordAdapter;
import com.canplay.medical.view.NavigationBar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 我的用药记录
 */
public class MineUseRecordActivity extends BaseActivity {


    @BindView(R.id.line)
    View line;
    @BindView(R.id.navigationBar)
    NavigationBar navigationBar;
    @BindView(R.id.rl_menu)
    ListView rlMenu;


    private UserRecordAdapter adapter;
    @Override
    public void initViews() {
        setContentView(R.layout.activity_mine_remind);
        ButterKnife.bind(this);
        navigationBar.setNavigationBarListener(this);
        adapter=new UserRecordAdapter(this);
        rlMenu.setAdapter(adapter);

    }


    @Override
    public void bindEvents() {


    }


    @Override
    public void initData() {

    }



}
