package com.canplay.repast_pad.base;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.canplay.repast_pad.base.manager.AppManager;
import com.canplay.repast_pad.util.ExceptionHandler;


/**
 * App基类
 * Created by peter on 2016/9/11.
 */

public class BaseApplication extends Application{
    //全局单例
    AppComponent mAppComponent;
    public static  BaseApplication cplayApplication;
    public static BaseApplication getInstance() {
        if (cplayApplication == null) {
            cplayApplication = new BaseApplication();
        }
        return (BaseApplication) cplayApplication;
    }
    @Override
    public void onCreate(){
        // TODO Auto-generated method stub
        super.onCreate();
        //生成全局单例
        mAppComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
        mAppComponent.inject(this);
        ApplicationConfig.setAppInfo(this);
        //全局异常处理
        new ExceptionHandler().init(this);
//        JPushInterface.setDebugMode(true);
//        JPushInterface.init(this);
//        JPushInterface.setLatestNotificationNumber(this, 1);
//        String androidId = android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
//        JPushUtils.shareInstance().setAlias(androidId);
//        BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(this);
//        builder.notificationFlags = Notification.FLAG_AUTO_CANCEL
//                | Notification.FLAG_SHOW_LIGHTS;  //设置为自动消失和呼吸灯闪烁
//        Log.e("---androidId----",androidId);
        this.cplayApplication = this;
    }

    /**
     * 退出应用
     */
    public void exit(){
        AppManager.getInstance(this).exitAPP(this);
    }

    @Override
    protected void attachBaseContext(Context base){
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public AppComponent getAppComponent(){
        return mAppComponent;
    }
}
