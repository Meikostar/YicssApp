package com.canplay.yicss_app.mvp.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.canplay.yicss_app.R;
import com.canplay.yicss_app.base.BaseActivity;
import com.canplay.yicss_app.base.BaseApplication;
import com.canplay.yicss_app.mvp.component.DaggerBaseComponent;
import com.canplay.yicss_app.mvp.present.LoginContract;
import com.canplay.yicss_app.mvp.present.LoginPresenter;
import com.canplay.yicss_app.util.SpUtil;
import com.canplay.yicss_app.util.StringUtil;
import com.canplay.yicss_app.util.TextUtil;
import com.canplay.yicss_app.view.ClearEditText;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends BaseActivity implements LoginContract.View {
    @Inject
    LoginPresenter presenter;
    @BindView(R.id.tv_logo)
    ImageView tvLogo;
    @BindView(R.id.et_user)
    ClearEditText etUser;
    @BindView(R.id.et_pws)
    ClearEditText etPws;
    @BindView(R.id.tv_login)
    TextView tvLogin;



    @Override
    public void initViews() {
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        DaggerBaseComponent.builder().appComponent(((BaseApplication) getApplication()).getAppComponent()).build().inject(this);
        presenter.attachView(this);
        String userId = SpUtil.getInstance().getUserId();
        if(TextUtil.isNotEmpty(userId)){
            startActivity(new Intent(this,MainActivity.class));
            finish();
        }
    }

    @Override
    public void bindEvents() {
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = etUser.getText().toString();
                String password = etPws.getText().toString();

                if (TextUtil.isEmpty(user)) {
                    showToasts(getString(R.string.qingshurusjh));
                    return;
                }
                if (TextUtil.isEmpty(password)) {
                    showToasts(getString(R.string.mimanull));
                    return;
                }
                showProgress("登录中...");
                presenter.goLogin(user,StringUtil.md5(password));
            }
        });
    }

    @Override
    public void initData() {

    }



    @Override
    public <T> void toEntity(T entity) {
        startActivity(new Intent(this,MainActivity.class));
        finish();
        dimessProgress();
    }

    @Override
    public void showTomast(String msg) {
        showToasts(msg);
        dimessProgress();
    }
}
