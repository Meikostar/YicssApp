package com.canplay.repast_wear.mvp.component;


import com.canplay.repast_wear.base.AppComponent;
import com.canplay.repast_wear.fragment.DishManageFragment;
import com.canplay.repast_wear.fragment.MenutFragment;
import com.canplay.repast_wear.fragment.OrderMangerFragment;
import com.canplay.repast_wear.fragment.SetFragment;
import com.canplay.repast_wear.mvp.ActivityScope;
import com.canplay.repast_wear.mvp.activity.AddDishCategoryActivity;
import com.canplay.repast_wear.mvp.activity.AddDishesActivity;
import com.canplay.repast_wear.mvp.activity.AddMenueCategoryActivity;
import com.canplay.repast_wear.mvp.activity.ChooseFoodActivity;
import com.canplay.repast_wear.mvp.activity.LoginActivity;
import com.canplay.repast_wear.mvp.activity.MainActivity;
import com.canplay.repast_wear.mvp.activity.MenuDetailActivity;
import com.canplay.repast_wear.mvp.activity.MenuDetailEditorActivity;
import com.canplay.repast_wear.mvp.activity.OrderDetailActivity;
import com.canplay.repast_wear.mvp.activity.OrderDetailfatherActivity;

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
