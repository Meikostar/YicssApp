package com.canplay.repast_pad.mvp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.canplay.repast_pad.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MenuManageActivity extends AppCompatActivity {

    @BindView(R.id.list_view)
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_manage);
        ButterKnife.bind(this);


    }
}
