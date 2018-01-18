package com.canplay.yicss_app.mvp.present;

import com.canplay.yicss_app.base.BasePresenter;
import com.canplay.yicss_app.base.BaseView;

public class LoginContract {
    public    interface View extends BaseView {

//        <T> void toList(List<T> list, int type, int... refreshType);
        <T> void toEntity(T entity);

//        void toNextStep(int type);

        void showTomast(String msg);
    }

    public  interface Presenter extends BasePresenter<View> {

        /**
         * 获得联系人列表
         */
        void goLogin(String account, String pwd);

        /**
         * 获取token
         */
        void getToken();

    }
}
