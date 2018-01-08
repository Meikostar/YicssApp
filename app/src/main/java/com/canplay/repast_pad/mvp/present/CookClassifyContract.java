package com.canplay.repast_pad.mvp.present;

import com.canplay.repast_pad.base.BasePresenter;
import com.canplay.repast_pad.base.BaseView;

import java.util.List;

public class CookClassifyContract {
    public    interface View extends BaseView {

        <T> void toList(List<T> list, int type);
        <T> void toEntity(T entity, int type);

//        void toNextStep(int type);

        void showTomast(String msg);
    }

    public  interface Presenter extends BasePresenter<View> {

        /**
         * 菜品分类列表
         */
        void getCookClassifyList();

    }
}
