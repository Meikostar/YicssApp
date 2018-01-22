package com.canplay.repast_wear.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.canplay.repast_wear.R;
import com.canplay.repast_wear.base.BaseApplication;
import com.canplay.repast_wear.base.BaseFragment;
import com.canplay.repast_wear.base.RxBus;
import com.canplay.repast_wear.base.SubscriptionBean;
import com.canplay.repast_wear.bean.MENU;
import com.canplay.repast_wear.mvp.activity.AddMenuActivity;
import com.canplay.repast_wear.mvp.activity.MenuDetailEditorActivity;
import com.canplay.repast_wear.mvp.adapter.MenuAdapter;
import com.canplay.repast_wear.mvp.component.DaggerBaseComponent;
import com.canplay.repast_wear.mvp.present.CookClassifyContract;
import com.canplay.repast_wear.mvp.present.CookClassifyPresenter;
import com.canplay.repast_wear.view.NavigationBar;
import com.canplay.repast_wear.view.PopView_NavigationBar_Menu;
import com.canplay.repast_wear.view.RegularListView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Subscription;
import rx.functions.Action1;


/**
 * Created by mykar on 17/4/10.
 */
public class MenutFragment extends BaseFragment implements View.OnClickListener  ,CookClassifyContract.View{


    @Inject
    CookClassifyPresenter presenter;
    @BindView(R.id.navigationBar)
    NavigationBar navigationBar;
    @BindView(R.id.rl_menu)
    RegularListView rlMenu;
    @BindView(R.id.line)
    View line;
    Unbinder unbinder;
     private MenuAdapter adapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, null);
        unbinder = ButterKnife.bind(this, view);
        DaggerBaseComponent.builder().appComponent(((BaseApplication) getActivity().getApplication()).getAppComponent()).build().inject(this);
        presenter.attachView(this);
        presenter.getMenuList();
        initView();
        initListener();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }
    private Subscription mSubscription;
    private void initListener() {
        mSubscription = RxBus.getInstance().toObserverable(SubscriptionBean.RxBusSendBean.class).subscribe(new Action1<SubscriptionBean.RxBusSendBean>() {
            @Override
            public void call(SubscriptionBean.RxBusSendBean bean) {
                if (bean == null) return;

                if(bean.type==SubscriptionBean.MENU_REFASHS){
                    presenter.getMenuList();;
                }

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                throwable.printStackTrace();
            }
        });
        RxBus.getInstance().addSubscription(mSubscription);
     navigationBar.setNavigationBarListener(new NavigationBar.NavigationBarListener() {
         @Override
         public void navigationLeft() {}
         @Override
         public void navigationRight() {
             startActivity(new Intent(getActivity(),AddMenuActivity.class));
//             popView_navigationBar.showPopView();
         }
         @Override
         public void navigationimg() {}
     });
        adapter.setClickListener(new MenuAdapter.ItemCliks() {
            @Override
            public void getItem(MENU menu) {
                Intent intent = new Intent(getActivity(), MenuDetailEditorActivity.class);
                intent.putExtra("id",menu.menuId);
                intent.putExtra("sort",menu.sort);
                startActivity(intent);
            }
        });

    }

    private void initView() {
        adapter=new MenuAdapter(getActivity());
        rlMenu.setAdapter(adapter);
        initPopView();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_dishe://菜品
                break;

        }
    }

    /**
     * popwindow
     */
    private PopView_NavigationBar_Menu popView_navigationBar;
    private void initPopView() {
//        mWindowAddPhoto = new PhotoPopupWindow(getActivity());
        popView_navigationBar = new PopView_NavigationBar_Menu(getActivity(),1);
        popView_navigationBar.setView(line);

        popView_navigationBar.setClickListener(new PopView_NavigationBar_Menu.ItemCliskListeners() {
            @Override
            public void clickListener(int poition) {
                switch (poition) {
                    case 0://新建菜单
                        startActivity(new Intent(getActivity(),AddMenuActivity.class));
                        break;
                    case 1://预览菜单
                        break;

                }
                popView_navigationBar.dismiss();
            }

        });
    }
   private List<MENU> menus;
    @Override
    public <T> void toList(List<T> list, int type) {
        menus= (List<MENU>) list;
        adapter.setData(menus);
        BaseApplication.map.clear();
        for(MENU menu:menus){
            BaseApplication.map.put(menu.sort,menu.sort);
        }
    }

    @Override
    public <T> void toEntity(T entity, int type) {

    }

    @Override
    public void showTomast(String msg) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSubscription.unsubscribe();
    }
}
