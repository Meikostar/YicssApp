package com.canplay.repast_pad.mvp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.canplay.repast_pad.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PrintSetActivity extends AppCompatActivity {

    @BindView(R.id.iv_star)
    ImageView ivStar;
    @BindView(R.id.ll_reflash)
    LinearLayout llReflash;
    @BindView(R.id.tv_contact)
    TextView tvContact;
    @BindView(R.id.ll_contact)
    LinearLayout llContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_set);
        ButterKnife.bind(this);
    }
}
