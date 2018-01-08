package com.canplay.repast_pad.mvp.http;

import com.canplay.repast_pad.bean.COOK;
import com.canplay.repast_pad.bean.USER;
import com.canplay.repast_pad.mvp.model.ApkUrl;
import com.canplay.repast_pad.mvp.model.DEVICE;
import com.canplay.repast_pad.mvp.model.Resps;
import com.canplay.repast_pad.mvp.model.RespsTable;
import com.canplay.repast_pad.mvp.model.Version;

import java.util.List;
import java.util.Map;

import retrofit2.http.GET;
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
    Observable<List<COOK>> getCookClassifyList(@QueryMap Map<String, String> options);

    /**
     * 手表端推送（自动转移和手动转移）
     * @param options
     * @return
     */
    @POST("wx/watchPushMessage")
    Observable<String> watchPushMessage(@QueryMap Map<String, String> options);

    /**
     * 重新绑定
     * @param options
     * @return
     */
    @POST("wx/deviceSignOut")
    Observable<String> deviceSignOut(@QueryMap Map<String, String> options);

    /**
     * 获取转移的其他手表
     * @param options
     * @return
     */
    @POST("wx/getWatchList")
    Observable<RespsTable> getWatchList(@QueryMap Map<String, String> options);


    /**
     * 绑定信息
     * @param options
     * @return
     */
    @POST("wx/deviceInfo")
    Observable<DEVICE> deviceInfo(@QueryMap Map<String, String> options);

    /**
     * @param options
     * @return
     */
    @GET("wx/getInit")
    Observable<Version> getInit(@QueryMap Map<String, String> options);

    /**
     * @param options
     * @return
     */
    @POST("wx/deletePushInfo")
    Observable<String> deletePushInfo(@QueryMap Map<String, String> options);
    /**
     *   apk下载 POST
     * @return
     */
    @POST("wx/getApkInfo")
    Observable<ApkUrl> getApkInfo(@QueryMap Map<String, String> options);
}
