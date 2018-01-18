package com.canplay.yicss_app.mvp.activity;


import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;


import com.canplay.yicss_app.R;
import com.canplay.yicss_app.base.BaseActivity;
import com.canplay.yicss_app.base.RxBus;
import com.canplay.yicss_app.base.SubscriptionBean;
import com.canplay.yicss_app.fragment.DishManageFragment;
import com.canplay.yicss_app.fragment.MenutFragment;
import com.canplay.yicss_app.fragment.OrderMangerFragment;
import com.canplay.yicss_app.fragment.SetFragment;
import com.canplay.yicss_app.mvp.adapter.FragmentViewPagerAdapter;
import com.canplay.yicss_app.mvp.component.OnChangeListener;
import com.canplay.yicss_app.view.BottonNevgBar;
import com.canplay.yicss_app.view.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;


import rx.Subscription;
import rx.functions.Action1;

public class MainActivity extends BaseActivity {


    NoScrollViewPager viewpagerMain;
    BottonNevgBar bnbHome;
    private Subscription mSubscription;
    private FragmentViewPagerAdapter mainViewPagerAdapter;
    private List<Fragment> mFragments;
    private int current = 0;
    private long firstTime = 0l;
    private OrderMangerFragment orderMangerFragment;
    private DishManageFragment dishManageFragment;
    private MenutFragment menutFragment;
    private SetFragment setFragment;


    @Override
    public void initViews() {
        setContentView(R.layout.activity_main);
        bnbHome = (BottonNevgBar) findViewById(R.id.bnb_home);
        viewpagerMain = (NoScrollViewPager) findViewById(R.id.viewpager_main);
        viewpagerMain.setScanScroll(false);

    }

    @Override
    public void bindEvents() {

        setViewPagerListener();
        setNevgBarChangeListener();

        mSubscription = RxBus.getInstance().toObserverable(SubscriptionBean.RxBusSendBean.class).subscribe(new Action1<SubscriptionBean.RxBusSendBean>() {
            @Override
            public void call(SubscriptionBean.RxBusSendBean bean) {
                if (bean == null) return;


            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                throwable.printStackTrace();
            }
        });
        RxBus.getInstance().addSubscription(mSubscription);


    }
    @Override
    public void initData() {
        addFragment();
        mainViewPagerAdapter = new FragmentViewPagerAdapter(getSupportFragmentManager(), mFragments);
        viewpagerMain.setAdapter(mainViewPagerAdapter);
        viewpagerMain.setOffscreenPageLimit(3);//设置缓存view 的个数
        viewpagerMain.setCurrentItem(current);
        bnbHome.setSelect(current);
    }

    private void setViewPagerListener() {
        viewpagerMain.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                bnbHome.setSelect(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void setNevgBarChangeListener() {
        bnbHome.setOnChangeListener(new OnChangeListener() {
            @Override
            public void onChagne(int currentIndex) {
                current = currentIndex;
                bnbHome.setSelect(currentIndex);
                viewpagerMain.setCurrentItem(currentIndex);
            }
        });
    }

    private void addFragment() {
        mFragments = new ArrayList<>();
        orderMangerFragment=new OrderMangerFragment();
        dishManageFragment=new DishManageFragment();
        menutFragment=new MenutFragment();
        setFragment=new SetFragment();
        mFragments.add(orderMangerFragment);
        mFragments.add(dishManageFragment);
        mFragments.add(menutFragment);
        mFragments.add(setFragment);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSubscription != null) {
            RxBus.getInstance().unSub(mSubscription);
        }
    }
//
//    //屏蔽返回键的代码:
//    public boolean onKeyDown(int keyCode,KeyEvent event)
//    {
//        switch(keyCode)
//        {
//            case KeyEvent.KEYCODE_BACK:return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }


}
