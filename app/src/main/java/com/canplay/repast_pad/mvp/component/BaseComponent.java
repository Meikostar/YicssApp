package com.canplay.repast_pad.mvp.component;


import com.canplay.repast_pad.base.AppComponent;
import com.canplay.repast_pad.mvp.ActivityScope;
import com.canplay.repast_pad.mvp.activity.MainActivity;

import dagger.Component;

/**
 * Created by leo on 2016/9/27.
 */
@ActivityScope
@Component(dependencies = AppComponent.class)
public interface BaseComponent{



}
