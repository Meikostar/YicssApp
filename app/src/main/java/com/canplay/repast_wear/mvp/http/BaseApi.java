package com.canplay.repast_wear.mvp.http;

import com.canplay.repast_wear.bean.BASEBEAN;
import com.canplay.repast_wear.bean.COOK;
import com.canplay.repast_wear.bean.MENU;
import com.canplay.repast_wear.bean.ORDER;
import com.canplay.repast_wear.bean.USER;
import com.canplay.repast_wear.mvp.model.BaseType;

import java.util.List;
import java.util.Map;

import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import rx.Observable;


public interface BaseApi {


    /**
     * Login
     * @param options
     * @return
     */
    @POST("merchant/merchantLogin")
    Observable<USER> Login(@QueryMap Map<String, String> options);

    /**
     * 完成
     * @param options 菜品分类列表 post /
     * @return
     */
    @POST("merchant/getCookBookClassifyList")
    Observable<List<BaseType>> getCookClassifyList(@QueryMap Map<String, String> options);

    /**
     *
     * @param options
     * @return
     */
    @POST("merchant/addCookBookClassify")
    Observable<BaseType> addBookClassify(@QueryMap Map<String, String> options);

    /**
     *
     * @param options
     * @return
     */
    @POST("merchant/delCookBookClassify")
    Observable<String> delCookClassify(@QueryMap Map<String, String> options);

    /**
     *
     * @param options
     * @return
     */
    @POST(" merchant/getFoodClassifyList")
    Observable<List<BaseType>> getFoodClassifyList(@QueryMap Map<String, String> options);


    /**
     * 绑定信息
     * @param options
     * @return
     */
    @POST("merchant/getRecipesClassifyList")
    Observable<List<BaseType>> getRecipesClassifyList(@QueryMap Map<String, String> options);

    /**
     * @param options
     * @return
     */
    @POST("merchant/addFoodClassify")
    Observable<BaseType> addFoodClassify(@QueryMap Map<String, String> options);

    /**
     * @param options
     * @return
     */
    @POST("merchant/delFoodClassify")
    Observable<String> delFoodClassify(@QueryMap Map<String, String> options);
    /**
     *   apk下载 POST
     * @return
     */
    @POST("merchant/addRecipesClassify")
    Observable<BaseType> addRecipesClassify(@QueryMap Map<String, String> options);

    /**
     *   apk下载 POSTmerchant/createOrEditCookbook
     * @return
     */
    @POST("merchant/delRecipesClassify")
    Observable<String> delRecipesClassify(@QueryMap Map<String, String> options);


    /**
     * POST
     * @return
     */
    @POST("merchant/createOrEditCookbook")
    Observable<String> createOrEditCookbook(@QueryMap Map<String, String> options);
    /**
     * POST
     * @return
     */
    @POST("merchant/getUpToken")
    Observable<BASEBEAN> getToken(@QueryMap Map<String, String> options);
    /**
     * POSTmerchant/getCookbookList
     * @return
     */
    @POST("merchant/getCookbookList")
    Observable<COOK> getCookbookList(@QueryMap Map<String, String> options);

    /**
     * POSTmerchant/getMenuList
     * @return
     */
    @POST("merchant/getCookbookInfoById")
    Observable<COOK> getCookbookInfo(@QueryMap Map<String, String> options);

    /**
     * POST
     * @return
     */
    @POST("merchant/getMenuList")
    Observable<List<MENU>> getMenuList(@QueryMap Map<String, String> options);
    /**
     * POST
     * @return
     */
    @POST("merchant/getMenuInfo")
    Observable<COOK> getMenuInfo(@QueryMap Map<String, String> options);


    /**
     * POST
     * @return
     */
    @POST("merchant/createOrEditMenu")
    Observable<String> createOrEditMenu(@QueryMap Map<String, String> options);


    /**
     * POST
     * @return
     */
    @POST("merchant/delMenuInfo")
    Observable<String> delMenuInfo(@QueryMap Map<String, String> options);
    /**
     * POST
     * @return
     */
    @POST("merchant/editCookbookState")
    Observable<String> editCookbookState(@QueryMap Map<String, String> options);


    /**
     * POST
     * @return
     */
    @POST("merchant/getSurcharge")
    Observable<BASEBEAN> getSurcharge(@QueryMap Map<String, String> options);

    /**
     * POST
     * @return
     */
    @POST("merchant/editSurcharge")
    Observable<String> editSurcharge(@QueryMap Map<String, String> options);



    /**
     * POST
     * @return
     */
    @POST("pad/getOrderInfoList")
    Observable<ORDER> getOrderInfoList(@QueryMap Map<String, String> options);

    /**
     * POST
     * @return
     */
    @POST("pad/getAppOrderInfo")
    Observable<ORDER> getAppOrderInfo(@QueryMap Map<String, String> options);



    /**
     * POST
     * @return
     */
    @POST("pad/getAppOrderList")
    Observable<ORDER> getAppOrderList(@QueryMap Map<String, String> options);

    /**
     * POST
     * @return
     */
    @POST("merchant/updateOrderState")
    Observable<String> updateOrderState(@QueryMap Map<String, String> options);


    /**
     * POST
     * @return
     */
    @POST("merchant/updateDetailCount")
    Observable<String> updateDetailCount(@QueryMap Map<String, String> options);


}
