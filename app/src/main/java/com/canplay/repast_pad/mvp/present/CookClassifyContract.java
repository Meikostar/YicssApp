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

        /**
         * 添加菜品分类
         */
        void addBookClassfy(String content);

        /**
         * 删除菜品分类
         */
        void delBookClassfy(String content);
        /**
         * 配菜分类列表
         */
        void getFoodClassifyList();
        /**
         * 做法分类列表
         */
        void getRecipesClassifyList();


        /**
         * 添加菜品分类
         */
        void addFoodClassify(String content);

        /**
         * 删除菜品分类
         */
        void delFoodClassify(String content);
        /**
         * 添加菜品分类
         */
        void addRecipesClassify(String content);

        /**
         * 删除菜品分类
         */
        void delRecipesClassify(String content);



    }
}
