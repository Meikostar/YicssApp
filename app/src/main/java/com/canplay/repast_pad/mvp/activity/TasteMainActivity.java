package com.canplay.repast_pad.mvp.activity;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.canplay.repast_pad.R;
import com.canplay.repast_pad.base.BaseActivity;
import com.canplay.repast_pad.mvp.adapter.TasteTypeAdapter;
import com.canplay.repast_pad.mvp.adapter.ViewPagerAdapter;
import com.canplay.repast_pad.view.CircleImageView;
import com.canplay.repast_pad.view.SlidingMenuRight;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class TasteMainActivity extends BaseActivity {

    @BindView(R.id.taste_car)
    TextView tasteCar;
    @BindView(R.id.taste_item)
    TextView tasteItem;
    @BindView(R.id.splash)
    TextView splash;
    @BindView(R.id.taste_select_list)
    ListView tasteSelectList;
    @BindView(R.id.all_price)
    TextView allPrice;
    @BindView(R.id.btn_sure)
    TextView btnSure;
    @BindView(R.id.btn_clear)
    TextView btnClear;
    @BindView(R.id.fl_time_select)
    LinearLayout flTimeSelect;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.fragment_contain)
    FrameLayout fragmentContain;
    @BindView(R.id.tab_food_list)
    TextView tabFoodList;
    @BindView(R.id.tab_main)
    TextView tabMain;
    @BindView(R.id.tab_user)
    CircleImageView tabUser;
    @BindView(R.id.tv_page)
    TextView tvPage;
    @BindView(R.id.taste_type)
    ImageView tasteType;
    @BindView(R.id.taste_type_list)
    ListView tasteTypeList;
    @BindView(R.id.slideMenu1)
    SlidingMenuRight slideMenu1;
    private TasteTypeAdapter typeadapter;

    private ViewPagerAdapter adapter;
    private List<Fragment> fragmentList;
    private FragmentManager fm;

    @Override
    public void initInjector() {
//        requestWindowFeature(Window.FEATURE_NO_TITLE);

    }

    @Override
    public void initCustomerUI() {
        initBodyViewNoTile(R.layout.taste_main);
    }


    @Override
    public void initOther() {
        fm = getSupportFragmentManager();
        fragmentList = new ArrayList<>();
        fragmentList.add(TabMainFragment.newInstance());
        fragmentList.add(TabFragment.newInstance());
        adapter = new ViewPagerAdapter(fm, fragmentList);
        viewpager.setPageTransformer(true, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View view, float position) {
                if(position>=-1&&position<=1){
                    view.setPivotX(position<0?view.getWidth():0);//设置要旋转的Y轴的位置
                    view.setRotationY(90*position);//开始设置属性动画值
                }
            }
        });
        viewpager.setAdapter(adapter);
        viewpager.setOnPageChangeListener(new TabOnPageChangeListener(this, viewpager,  fragmentList.size()));
        slideMenu1.setOnStatusListener(new SlidingMenuRight.OnStatusListener() {

            @Override
            public void statusChanged(SlidingMenuRight.Status status) {
                if (status == SlidingMenuRight.Status.Open) {
                    showToast("Open");
                } else {
                    showToast("Close");
                }

            }
        });
    }

    @OnClick({R.id.tab_food_list, R.id.tab_main, R.id.tab_user, R.id.taste_type})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tab_food_list://我的菜品
                slideMenu1.toggle();
                break;
            case R.id.tab_main:
                break;
            case R.id.tab_user:
                break;
            case R.id.taste_type:
                showTasteType();
                break;
        }
    }

    private void showTasteType() {
        if (tasteTypeList.isShown()) {
            tasteTypeList.setVisibility(View.GONE);
        } else {
            tasteTypeList.setVisibility(View.VISIBLE);
            if (typeadapter == null) {
                typeadapter = new TasteTypeAdapter(this);
            }
            tasteTypeList.setAdapter(typeadapter);
            typeadapter.setColseListener(new TasteTypeAdapter.setCloseClickListener() {
                @Override
                public void imageClike() {
                    tasteTypeList.setVisibility(View.GONE);
                }
            });
            typeadapter.setOnItemListener(new TasteTypeAdapter.OnItemViewClickListener() {
                @Override
                public void ItemClick(String type) {
                    showToast(type);
                }
            });
        }
    }
    /**
     * 功能：Fragment页面改变事件
     */
    public class TabOnPageChangeListener implements ViewPager.OnPageChangeListener {
        private Context context;
        private ViewPager viewPager;
        private TextView pageNum;
        private int size;//需要点的个数

        public TabOnPageChangeListener(Context context, ViewPager viewPager,  int size) {
            this.context = context;
            this.viewPager = viewPager;
//            this.pageNum = pagenum;
            this.size = size;

        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
//            pageNum.setText(pageNum + "/" + size);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

}
