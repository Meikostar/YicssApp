package com.canplay.repast_wear.mvp.present;


import android.support.annotation.NonNull;


import com.canplay.repast_wear.base.manager.ApiManager;
import com.canplay.repast_wear.bean.BASEBEAN;
import com.canplay.repast_wear.bean.USER;
import com.canplay.repast_wear.mvp.http.BaseApi;

import com.canplay.repast_wear.net.MySubscriber;
import com.canplay.repast_wear.util.SpUtil;

import java.util.Map;
import java.util.TreeMap;

import javax.inject.Inject;

import rx.Subscription;


public class LoginPresenter implements LoginContract.Presenter {
    private Subscription subscription;

    private LoginContract.View mView;

    private BaseApi contactApi;

    @Inject
    LoginPresenter(ApiManager apiManager){
        contactApi = apiManager.createApi(BaseApi.class);
    }
    @Override
    public void goLogin(String account, String pwd) {
        Map<String, String> params = new TreeMap<>();
        params.put("account", account);
        params.put("pwd", pwd);
        subscription = ApiManager.setSubscribe(contactApi.Login(ApiManager.getParameters(params, true)), new MySubscriber<USER>(){
            @Override
            public void onError(Throwable e){
                super.onError(e);
                mView.showTomast(e.toString());
            }

            @Override
            public void onNext(USER entity){
                mView.toEntity(entity);
                SpUtil.getInstance().putString(SpUtil.USER_ID,entity.merchantId);
            }
        });
    }
    @Override
    public void getToken() {
        Map<String, String> params = new TreeMap<>();

        subscription = ApiManager.setSubscribe(contactApi.getToken(ApiManager.getParameters(params, true)), new MySubscriber<BASEBEAN>(){
            @Override
            public void onError(Throwable e){
                super.onError(e);
                mView.showTomast(e.toString());
            }

            @Override
            public void onNext(BASEBEAN entity){
                mView.toEntity(entity);

            }
        });
    }
    @Override
    public void attachView(@NonNull LoginContract.View view){
        mView = view;
    }


    @Override
    public void detachView(){
        if(subscription != null && !subscription.isUnsubscribed()){
            subscription.unsubscribe();
        }
        mView = null;
    }
}
