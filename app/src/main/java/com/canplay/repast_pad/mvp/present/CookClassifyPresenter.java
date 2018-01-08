package com.canplay.repast_pad.mvp.present;


import android.support.annotation.NonNull;

import com.canplay.repast_pad.base.manager.ApiManager;
import com.canplay.repast_pad.bean.COOK;
import com.canplay.repast_pad.bean.USER;
import com.canplay.repast_pad.mvp.http.BaseApi;
import com.canplay.repast_pad.mvp.model.BaseType;
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

        subscription = ApiManager.setSubscribe(contactApi.getCookClassifyList(ApiManager.getParameters(params, true)), new MySubscriber<List<BaseType>>(){
            @Override
            public void onError(Throwable e){
                super.onError(e);
                mView.showTomast(e.toString());
            }

            @Override
            public void onNext(List<BaseType> entity){
                mView.toEntity(entity,1);

            }
        });

    }

    @Override
    public void getFoodClassifyList() {
        Map<String, String> params = new TreeMap<>();
        params.put("merchantId", SpUtil.getInstance().getUserId());

        subscription = ApiManager.setSubscribe(contactApi.getFoodClassifyList(ApiManager.getParameters(params, true)), new MySubscriber<List<BaseType>>(){
            @Override
            public void onError(Throwable e){
                super.onError(e);
                mView.showTomast(e.toString());
            }

            @Override
            public void onNext(List<BaseType> entity){
                mView.toEntity(entity,2);

            }
        });

    }
    @Override
    public void getRecipesClassifyList() {
        Map<String, String> params = new TreeMap<>();
        params.put("merchantId", SpUtil.getInstance().getUserId());

        subscription = ApiManager.setSubscribe(contactApi.getRecipesClassifyList(ApiManager.getParameters(params, true)), new MySubscriber<List<BaseType>>(){
            @Override
            public void onError(Throwable e){
                super.onError(e);
                mView.showTomast(e.toString());
            }

            @Override
            public void onNext(List<BaseType> entity){
                mView.toEntity(entity,3);

            }
        });

    }
    @Override
    public void addBookClassfy(String content) {
        Map<String, String> params = new TreeMap<>();
        params.put("merchantId", SpUtil.getInstance().getUserId());
        params.put("name", content);

        subscription = ApiManager.setSubscribe(contactApi.addBookClassify(ApiManager.getParameters(params, true)), new MySubscriber<BaseType>(){
            @Override
            public void onError(Throwable e){
                super.onError(e);
                mView.showTomast(e.toString());
            }

            @Override
            public void onNext(BaseType entity){
                mView.toEntity(entity,1);

            }
        });

    }
    @Override
    public void delBookClassfy(String cbClassifyId) {
        Map<String, String> params = new TreeMap<>();
        params.put("cbClassifyId", cbClassifyId);

        subscription = ApiManager.setSubscribe(contactApi.delCookClassify(ApiManager.getParameters(params, true)), new MySubscriber<String>(){
            @Override
            public void onError(Throwable e){
                super.onError(e);
                mView.showTomast(e.toString());
            }

            @Override
            public void onNext(String entity){
                mView.toEntity(entity,1);

            }
        });

    }
    @Override
    public void addFoodClassify(String content) {
        Map<String, String> params = new TreeMap<>();
        params.put("merchantId", SpUtil.getInstance().getUserId());
        params.put("name", content);

        subscription = ApiManager.setSubscribe(contactApi.addFoodClassify(ApiManager.getParameters(params, true)), new MySubscriber<BaseType>(){
            @Override
            public void onError(Throwable e){
                super.onError(e);
                mView.showTomast(e.toString());
            }

            @Override
            public void onNext(BaseType entity){
                mView.toEntity(entity,4);

            }
        });

    }
    @Override
    public void delFoodClassify(String cbClassifyId) {
        Map<String, String> params = new TreeMap<>();
        params.put("classifyId", cbClassifyId);

        subscription = ApiManager.setSubscribe(contactApi.delFoodClassify(ApiManager.getParameters(params, true)), new MySubscriber<String>(){
            @Override
            public void onError(Throwable e){
                super.onError(e);
                mView.showTomast(e.toString());
            }

            @Override
            public void onNext(String entity){
                mView.toEntity(entity,4);

            }
        });

    }
    @Override
    public void addRecipesClassify(String content) {
        Map<String, String> params = new TreeMap<>();
        params.put("merchantId", SpUtil.getInstance().getUserId());
        params.put("name", content);

        subscription = ApiManager.setSubscribe(contactApi.addRecipesClassify(ApiManager.getParameters(params, true)), new MySubscriber<BaseType>(){
            @Override
            public void onError(Throwable e){
                super.onError(e);
                mView.showTomast(e.toString());
            }

            @Override
            public void onNext(BaseType entity){
                mView.toEntity(entity,4);

            }
        });

    }
    @Override
    public void delRecipesClassify(String cbClassifyId) {
        Map<String, String> params = new TreeMap<>();
        params.put("classifyId", cbClassifyId);

        subscription = ApiManager.setSubscribe(contactApi.delRecipesClassify(ApiManager.getParameters(params, true)), new MySubscriber<String>(){
            @Override
            public void onError(Throwable e){
                super.onError(e);
                mView.showTomast(e.toString());
            }

            @Override
            public void onNext(String entity){
                mView.toEntity(entity,3);

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
