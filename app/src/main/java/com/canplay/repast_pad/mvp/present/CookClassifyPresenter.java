package com.canplay.repast_pad.mvp.present;


import android.support.annotation.NonNull;

import com.canplay.repast_pad.base.manager.ApiManager;
import com.canplay.repast_pad.bean.COOK;
import com.canplay.repast_pad.bean.USER;
import com.canplay.repast_pad.mvp.http.BaseApi;
import com.canplay.repast_pad.net.MySubscriber;
import com.canplay.repast_pad.util.SpUtil;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.inject.Inject;

import rx.Subscription;


public class CookClassifyPresenter implements CookClassifyContract.Presenter {
    private Subscription subscription;

    private CookClassifyContract.View mView;

    private BaseApi contactApi;

    @Inject
    CookClassifyPresenter(ApiManager apiManager){
        contactApi = apiManager.createApi(BaseApi.class);
    }
    @Override
    public void getCookClassifyList() {
        Map<String, String> params = new TreeMap<>();
        params.put("merchantId", SpUtil.getInstance().getUserId());

        subscription = ApiManager.setSubscribe(contactApi.getCookClassifyList(ApiManager.getParameters(params, false)), new MySubscriber<List<COOK>>(){
            @Override
            public void onError(Throwable e){
                super.onError(e);
                mView.showTomast(e.toString());
            }

            @Override
            public void onNext(List<COOK> entity){
                mView.toEntity(entity,1);

            }
        });
    }

    @Override
    public void attachView(@NonNull CookClassifyContract.View view){
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
