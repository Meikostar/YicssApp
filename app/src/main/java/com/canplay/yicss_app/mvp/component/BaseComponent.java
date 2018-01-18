package com.canplay.yicss_app.mvp.component;


import com.canplay.yicss_app.base.AppComponent;
import com.canplay.yicss_app.fragment.DishManageFragment;
import com.canplay.yicss_app.fragment.MenutFragment;
import com.canplay.yicss_app.fragment.OrderMangerFragment;
import com.canplay.yicss_app.fragment.SetFragment;
import com.canplay.yicss_app.mvp.ActivityScope;
import com.canplay.yicss_app.mvp.activity.AddDishCategoryActivity;
import com.canplay.yicss_app.mvp.activity.AddDishesActivity;
import com.canplay.yicss_app.mvp.activity.AddMenueCategoryActivity;
import com.canplay.yicss_app.mvp.activity.ChooseFoodActivity;
import com.canplay.yicss_app.mvp.activity.LoginActivity;
import com.canplay.yicss_app.mvp.activity.MainActivity;
import com.canplay.yicss_app.mvp.activity.MenuDetailActivity;
import com.canplay.yicss_app.mvp.activity.MenuDetailEditorActivity;
import com.canplay.yicss_app.mvp.activity.OrderDetailActivity;
import com.canplay.yicss_app.mvp.activity.OrderDetailfatherActivity;

import dagger.Component;

/**
 * Created by leo on 2016/9/27.
 */
@ActivityScope
@Component(dependencies = AppComponent.class)
public interface BaseComponent{

    void inject(LoginActivity binderActivity);
    void inject(MainActivity binderActivity);
    void inject(DishManageFragment binderActivity);
    void inject(MenutFragment binderActivity);
    void inject(AddMenueCategoryActivity binderActivity);
    void inject(AddDishCategoryActivity binderActivity);
    void inject(AddDishesActivity binderActivity);
    void inject(MenuDetailEditorActivity binderActivity);
    void inject(ChooseFoodActivity binderActivity);
    void inject(MenuDetailActivity binderActivity);
    void inject(SetFragment binderActivity);
    void inject(OrderDetailActivity binderActivity);
    void inject(OrderMangerFragment binderActivity);
    void inject(OrderDetailfatherActivity binderActivity);

}
