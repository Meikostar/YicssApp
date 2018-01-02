package com.canplay.repast_pad.mvp.activity;


import android.os.PowerManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.canplay.repast_pad.R;
import com.canplay.repast_pad.base.BaseActivity;
import com.canplay.repast_pad.base.BaseApplication;

import com.canplay.repast_pad.mvp.model.Message;
import com.canplay.repast_pad.mvp.present.MessagePresenter;
import com.canplay.repast_pad.util.SpUtil;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscription;
import rx.functions.Action1;


public class MainActivity extends BaseActivity {

    @Inject
    MessagePresenter messagePresenter;

    private Subscription mSubscription;
    private long showtime;//覆盖的时间
    private SpUtil sp;
    private long tableId;//桌子id （30s后自动转移情况传0，手动转移传桌子id）
    private long pushId;//推送记录id
    private boolean isShow = false;
    private Message passMessage;
    private String androidId;
    private String tableNo;
    private PopupWindow popSignOut;
    private EditText editText;
    private PowerManager.WakeLock wakeLock;

    @Override
    public void initInjector() {

    }

    @Override
    public void initCustomerUI() {
        initUI(R.layout.activity_main);
        ButterKnife.bind(this);

    }

    @Override
    public void initOther() {

    }

    @Override
    public void onBackClick(View v) {
        if (tableNo != null) {
            Log.e("backclick  ", "点击了显示桌号");
            toast = Toast.makeText(this, "当前绑定的桌号为：" + tableNo, Toast.LENGTH_SHORT);
            toast.show();
        }
    }


}
