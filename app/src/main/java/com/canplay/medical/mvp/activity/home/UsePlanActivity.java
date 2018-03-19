package com.canplay.medical.mvp.activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.canplay.medical.R;
import com.canplay.medical.base.BaseActivity;
import com.canplay.medical.mvp.adapter.UsePlanAdapter;
import com.canplay.medical.view.NavigationBar;
import com.canplay.medical.view.PopView_NavigationBar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 用药计划
 */
public class UsePlanActivity extends BaseActivity {


    @BindView(R.id.line)
    View line;
    @BindView(R.id.navigationBar)
    NavigationBar navigationBar;
    @BindView(R.id.tv_hour)
    TextView tvHour;
    @BindView(R.id.tv_minter)
    TextView tvMinter;
    @BindView(R.id.tv_second)
    TextView tvSecond;
    @BindView(R.id.rl_menu)
    ListView rlMenu;
    private UsePlanAdapter adapter;

    @Override
    public void initViews() {
        setContentView(R.layout.activity_use_plan);
        ButterKnife.bind(this);
        navigationBar.setNavigationBarListener(this);
        adapter = new UsePlanAdapter(this);
        rlMenu.setAdapter(adapter);
        initPopView();

    }


    @Override
    public void bindEvents() {


        navigationBar.setNavigationBarListener(new NavigationBar.NavigationBarListener() {
            @Override
            public void navigationLeft() {
                finish();
            }

            @Override
            public void navigationRight() {
                popView_navigationBar.showPopView();
            }

            @Override
            public void navigationimg() {

            }
        });


    }

    private PopView_NavigationBar popView_navigationBar;

    private void initPopView() {

        popView_navigationBar = new PopView_NavigationBar(this, 1);
        popView_navigationBar.setView(line);

        popView_navigationBar.setClickListener(new PopView_NavigationBar.ItemCliskListeners() {
            @Override
            public void clickListener(int poition) {
                switch (poition) {
                    case 0://我的用药提醒
                       startActivity(new Intent(UsePlanActivity.this,MinePlanRemindActivity.class));
                        break;
                    case 1://我的用药记录
                        startActivity(new Intent(UsePlanActivity.this,MineUseRecordActivity.class));
                        break;

                }
                popView_navigationBar.dismiss();
            }

        });
    }

    @Override
    public void initData() {

    }



}
