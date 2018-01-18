package com.canplay.yicss_app.base;

import android.app.Application;
import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.os.Vibrator;
import android.support.multidex.MultiDex;

import com.canplay.yicss_app.base.manager.AppManager;
import com.canplay.yicss_app.util.ExceptionHandler;
import com.canplay.yicss_app.util.JPushUtils;

import java.util.HashMap;
import java.util.Map;

import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;
import io.valuesfeng.picker.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import io.valuesfeng.picker.universalimageloader.core.ImageLoader;
import io.valuesfeng.picker.universalimageloader.core.ImageLoaderConfiguration;
import io.valuesfeng.picker.universalimageloader.core.assist.QueueProcessingType;


/**
 * App基类
 * Created by peter on 2016/9/11.
 */

public class BaseApplication extends Application{
    //全局单例
    AppComponent mAppComponent;
    public static  BaseApplication cplayApplication;
    public static Map<String,String> map=new HashMap<>();
    public static BaseApplication getInstance() {
        if (cplayApplication == null) {
            cplayApplication = new BaseApplication();
        }
        return (BaseApplication) cplayApplication;
    }
    public  Vibrator mVibrator;
    @Override
    public void onCreate(){
        // TODO Auto-generated method stub
        super.onCreate();
        //生成全局单例
        mAppComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
        mAppComponent.inject(this);
        ApplicationConfig.setAppInfo(this);
        mVibrator = (Vibrator) getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);

        //全局异常处理
        initImageLoader(this);
        new ExceptionHandler().init(this);
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        JPushInterface.setLatestNotificationNumber(this, 1);
        String androidId = android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        JPushUtils.shareInstance().setAlias(androidId);
        BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(this);
        builder.notificationFlags = Notification.FLAG_AUTO_CANCEL
                | Notification.FLAG_SHOW_LIGHTS;  //设置为自动消失和呼吸灯闪烁
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
    public static void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you may tune some of them,
        // or you can create default configuration by
//          ImageLoaderConfiguration.createDefault(this);
        // method.
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory / 8;

        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.memoryCacheSize(cacheSize);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 10 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
//        config.memoryCache(new WeakMemoryCache()).threadPoolSize(1);
        config.memoryCacheExtraOptions(480, 800);
        config.writeDebugLogs(); // Remove for release app
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build());

    }
    public AppComponent getAppComponent(){
        return mAppComponent;
    }
}
