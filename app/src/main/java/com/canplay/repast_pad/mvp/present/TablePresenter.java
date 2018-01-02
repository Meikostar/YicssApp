package com.canplay.repast_pad.mvp.present;

import android.content.Context;
import android.support.annotation.NonNull;

import com.canplay.repast_pad.base.manager.ApiManager;
import com.canplay.repast_pad.mvp.http.TableApi;
import com.canplay.repast_pad.mvp.model.PROVINCE;
import com.canplay.repast_pad.mvp.model.Table;
import com.canplay.repast_pad.net.MySubscriber;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.inject.Inject;

import rx.Subscription;


public class TablePresenter implements TableContract.Presenter {
    private Subscription subscription;

    private TableContract.View mView;

    private TableApi tableApi;

    @Inject
    TablePresenter(ApiManager apiManager) {
        tableApi = apiManager.createApi(TableApi.class);
    }

    //    @Override
//    public void getCityList() {
//        Map<String, String> params = new TreeMap<>();
//        subscription=ApiManager.setSubscribe(tableApi.getCityList(ApiManager.getParameters(params, false)), new MySubscriber<List<CITY>>() {
//            @Override
//            public void onError(Throwable e){
//                super.onError(e);
//            }
//            @Override
//            public void onNext(List<CITY> list) {
//                mView.toList(list, Contract.CITY_LIST);
//            }
//        });
//    }
    @Override
    public void getBusinessTableList(long businessId, String deviceCode, final Context context) {
        Map<String, String> params = new TreeMap<>();
        params.put("businessId", businessId + "");
        params.put("deviceCode", deviceCode + "");

        subscription = ApiManager.setSubscribe(tableApi.getBusinessTableList(ApiManager.getParameters(params, true)), new MySubscriber<List<Table>>() {
            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }

            @Override
            public void onNext(List<Table> entity) {
                mView.toList(entity, 1);
            }
        });
    }

    @Override
    public void getBusinessNameList(String areaCode) {
        Map<String, String> params = new TreeMap<>();
        params.put("areaCode", areaCode + "");
        subscription = ApiManager.setSubscribe(tableApi.getBusinessNameList(ApiManager.getParameters(params, true)), new MySubscriber<List<PROVINCE>>() {
            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }

            @Override
            public void onNext(List<PROVINCE> entity) {
                mView.toList(entity, 1);
            }
        });
    }



    @Override
    public void attachView(@NonNull TableContract.View view) {
        mView = view;
    }

    @Override
    public void bondBusiness(String deviceCode, long businessId, String tableIds,String name, Context context) {
        Map<String, String> params = new TreeMap<>();
        params.put("deviceCode", deviceCode + "");
        params.put("businessId", businessId + "");
        params.put("tableIds", tableIds + "");
        params.put("name", name + "");
        subscription = ApiManager.setSubscribe(tableApi.bondBusiness(ApiManager.getParameters(params, true)), new MySubscriber<String>() {
            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
            @Override
            public void onNext(String entity) {
                mView.toNextStep(2);
            }
        });
    }


    @Override
    public void detachView() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        mView = null;
    }
}
