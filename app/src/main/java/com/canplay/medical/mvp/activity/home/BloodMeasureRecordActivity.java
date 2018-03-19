package com.canplay.medical.mvp.activity.home;

import android.content.Intent;
import android.view.View;
import android.widget.ListView;

import com.canplay.medical.R;
import com.canplay.medical.base.BaseActivity;
import com.canplay.medical.mvp.adapter.BloodMeasureRecordAdapter;
import com.canplay.medical.mvp.adapter.UserRecordAdapter;
import com.canplay.medical.view.NavigationBar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 血压测量记录/血糖测量记录
 */
public class BloodMeasureRecordActivity extends BaseActivity {


    @BindView(R.id.line)
    View line;
    @BindView(R.id.navigationBar)
    NavigationBar navigationBar;
    @BindView(R.id.rl_menu)
    ListView rlMenu;

    private int type=0;//0 血压测量记录 ,1 血糖测量记录
    private BloodMeasureRecordAdapter adapter;
    @Override
    public void initViews() {
        setContentView(R.layout.activity_blood_mesure);
        ButterKnife.bind(this);
        type=getIntent().getIntExtra("type",0);
        navigationBar.setNavigationBarListener(this);
        adapter=new BloodMeasureRecordAdapter(this);

        rlMenu.setAdapter(adapter);
        if(type!=0){
            navigationBar.setNaviTitle("血糖测量记录");
        }

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
                Intent intent = new Intent(BloodMeasureRecordActivity.this, AddBloodDataActivity.class);
                intent.putExtra("type",type) ;
                startActivity(intent);
            }

            @Override
            public void navigationimg() {

            }
        });

    }


    @Override
    public void initData() {

    }



}
