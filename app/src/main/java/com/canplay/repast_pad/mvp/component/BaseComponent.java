package com.canplay.repast_pad.mvp.component;


import com.canplay.repast_pad.base.AppComponent;
import com.canplay.repast_pad.mvp.ActivityScope;
import com.canplay.repast_pad.mvp.activity.BaseAdressActivity;
import com.canplay.repast_pad.mvp.activity.BinderActivity;
import com.canplay.repast_pad.mvp.activity.BinderTabeActivity;
import com.canplay.repast_pad.mvp.activity.HaveRespondFragment;
import com.canplay.repast_pad.mvp.activity.MainActivity;
import com.canplay.repast_pad.mvp.activity.NoRespondFragment;
import com.canplay.repast_pad.mvp.activity.RespondActivity;
import com.canplay.repast_pad.mvp.activity.ToContactActivity;

import dagger.Component;

/**
 * Created by leo on 2016/9/27.
 */
@ActivityScope
@Component(dependencies = AppComponent.class)
public interface BaseComponent{

    void inject(BinderActivity binderActivity);

    void inject(BinderTabeActivity binderTabeActivity);

    void inject(BaseAdressActivity baseAdressActivity);

    void inject(MainActivity mainActivity);

    void inject(ToContactActivity toContactActivity);

    void inject(RespondActivity respondActivity);

    void inject(NoRespondFragment noRespondFragment);

    void inject(HaveRespondFragment haveRespondFragment);

}
