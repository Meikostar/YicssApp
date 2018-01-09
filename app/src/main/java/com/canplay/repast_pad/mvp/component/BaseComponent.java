package com.canplay.repast_pad.mvp.component;


import com.canplay.repast_pad.base.AppComponent;
import com.canplay.repast_pad.fragment.DishManageFragment;
import com.canplay.repast_pad.fragment.MenutFragment;
import com.canplay.repast_pad.mvp.ActivityScope;
import com.canplay.repast_pad.mvp.activity.AddDishCategoryActivity;
import com.canplay.repast_pad.mvp.activity.AddDishesActivity;
import com.canplay.repast_pad.mvp.activity.AddMenueCategoryActivity;
import com.canplay.repast_pad.mvp.activity.LoginActivity;
import com.canplay.repast_pad.mvp.activity.MainActivity;

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

}
