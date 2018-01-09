package com.canplay.repast_pad.util;

import android.icu.util.ULocale;

import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCancellationSignal;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;

import org.json.JSONObject;

import java.io.File;

/**
 * Created by Administrator on 2018/1/9.
 */

public class QiniuUtils {
    private static UploadManager uploadManager;
    private QiniuUtils(){

    }
    private static QiniuUtils qiniu;
    public static QiniuUtils getInstance(){
        if(qiniu==null){
            qiniu=new QiniuUtils();
            uploadManager = new UploadManager();
        }
        return qiniu;
    }
    public void setCompleteLlistener(CompleteListener listener){
        this.listener=listener;
    }
    private CompleteListener listener;
    public interface CompleteListener{
        void completeListener(String url);
    }
    public void  upFile(String path,String token,final CompleteListener listener){
        uploadManager.put(path, new File(path).getName(), token,
                new UpCompletionHandler() {
                    @Override
                    public void complete(String key, ResponseInfo info, JSONObject response) {
                        if(info!=null&&info.isOK()) {

                            listener.completeListener(key);
                        }
                    }
                },new UploadOptions(null, null, false, new UpProgressHandler() {
                    @Override
                    public void progress(String key, double percent) {

                    }
                }, new UpCancellationSignal() {
                    @Override
                    public boolean isCancelled() {
                        return false;
                    }
                }));
    }
}
