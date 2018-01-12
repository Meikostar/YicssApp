package com.canplay.repast_pad.mvp.activity;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.canplay.repast_pad.R;
import com.canplay.repast_pad.base.BaseActivity;
import com.canplay.repast_pad.bean.PrintBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PrintSetActivity extends BaseActivity {

    @BindView(R.id.iv_star)
    ImageView ivStar;
    @BindView(R.id.ll_reflash)
    LinearLayout llReflash;
    @BindView(R.id.tv_contact)
    TextView tvContact;
    @BindView(R.id.ll_contact)
    LinearLayout llContact;
    @BindView(R.id.switch1)
    Switch mSwitch;
    public static final int REQUEST_ENABLE_BT = 1;
    //蓝牙适配器
    private BluetoothAdapter mBluetoothAdapter;
    @Override
    public void initViews() {
        setContentView(R.layout.activity_print_set);
        ButterKnife.bind(this);
        //初始化
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        chechBluetooth();
        addViewListener();
    }

    @Override
    public void bindEvents() {

    }

    @Override
    public void initData() {

    }

    /**
     * 判断有没有开启蓝牙
     */
    private void chechBluetooth() {
        //没有开启蓝牙
        if (mBluetoothAdapter != null) {
            if (!mBluetoothAdapter.isEnabled()) {
                Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE); // 设置蓝牙可见性，最多300秒
                intent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 20);
                startActivityForResult(intent, REQUEST_ENABLE_BT);
                setViewStatus(true);
                //开启蓝牙
            } else {
                searchDevices();
                setViewStatus(true);
                mSwitch.setChecked(true);
            }
        }
    }
    private ArrayList<PrintBean> mBluetoothDevicesDatas;
    /**
     * 搜索状态调整
     *
     * @param isSearch 是否开始搜索
     */
    private void setViewStatus(boolean isSearch) {

//        if (isSearch) {
//            mFloatingActionButton.setVisibility(View.GONE);
//            searchHint.setVisibility(View.VISIBLE);
//            mProgressBar.setVisibility(View.VISIBLE);
//        } else {
//            mFloatingActionButton.setVisibility(View.VISIBLE);
//            mProgressBar.setVisibility(View.GONE);
//            searchHint.setVisibility(View.GONE);
//        }
    }
    /**
     * 搜索蓝牙设备
     */
    public void searchDevices() {
        mBluetoothDevicesDatas.clear();
//        adapter.notifyDataSetChanged();
        //开始搜索蓝牙设备
        mBluetoothAdapter.startDiscovery();
    }
    /**
     * 添加View的监听
     */
    private void addViewListener() {
        //蓝牙的状态
        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    openBluetooth();
                    setViewStatus(true);
                } else {
                    closeBluetooth();
                }
            }
        });
//        //重新搜索
//        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mSwitch.isChecked()) {
//                    searchDevices();
//                    setViewStatus(true);
//                } else {
//                    openBluetooth();
//                    setViewStatus(true);
//                }
//            }
//        });

    }
    /**
     * 打开蓝牙
     */
    public void openBluetooth() {
        Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE); // 设置蓝牙可见性，最多300秒
        intent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 20);
        startActivityForResult(intent, REQUEST_ENABLE_BT);

    }

    /**
     * 关闭蓝牙
     */
    public void closeBluetooth() {
        mBluetoothAdapter.disable();
    }
}
