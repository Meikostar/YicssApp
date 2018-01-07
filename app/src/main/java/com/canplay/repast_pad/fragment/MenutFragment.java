package com.canplay.repast_pad.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.canplay.repast_pad.R;
import com.canplay.repast_pad.base.BaseFragment;
import com.canplay.repast_pad.mvp.activity.AddMenuActivity;
import com.canplay.repast_pad.mvp.activity.MenuDetailEditorActivity;
import com.canplay.repast_pad.mvp.adapter.MenuAdapter;
import com.canplay.repast_pad.view.NavigationBar;
import com.canplay.repast_pad.view.PopView_NavigationBar;
import com.canplay.repast_pad.view.PopView_NavigationBar_Menu;
import com.canplay.repast_pad.view.RegularListView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by mykar on 17/4/10.
 */
public class MenutFragment extends BaseFragment implements View.OnClickListener {


    @BindView(R.id.navigationBar)
    NavigationBar navigationBar;
    @BindView(R.id.rl_menu)
    RegularListView rlMenu;
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
        initView();
        initListener();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void initListener() {
     navigationBar.setNavigationBarListener(new NavigationBar.NavigationBarListener() {
         @Override
         public void navigationLeft() {}
         @Override
         public void navigationRight() {
             popView_navigationBar.showPopView();
         }
         @Override
         public void navigationimg() {}
     });
        adapter.setClickListener(new MenuAdapter.ItemCliks() {
            @Override
            public void getItem(int poistion, String name, int id) {
                startActivity(new Intent(getActivity(), MenuDetailEditorActivity.class));
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
}
