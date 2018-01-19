package com.canplay.repast_wear.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.canplay.repast_wear.R;
import com.canplay.repast_wear.base.BaseApplication;
import com.canplay.repast_wear.base.BaseFragment;
import com.canplay.repast_wear.mvp.activity.AddMenueCategoryActivity;
import com.canplay.repast_wear.mvp.activity.PrintSetActivity;
import com.canplay.repast_wear.mvp.component.DaggerBaseComponent;
import com.canplay.repast_wear.mvp.present.CookClassifyContract;
import com.canplay.repast_wear.mvp.present.CookClassifyPresenter;
import com.canplay.repast_wear.util.DensityUtil;
import com.canplay.repast_wear.util.TextUtil;
import com.canplay.repast_wear.view.BaseTeatDialog;
import com.canplay.repast_wear.view.PhotoPopupWindow;
import com.canplay.repast_wear.view.SoftKeyBoardListener;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by mykar on 17/4/10.
 */
public class SetFragment extends BaseFragment implements View.OnClickListener ,CookClassifyContract.View{

    @Inject
    CookClassifyPresenter presenter;
    @BindView(R.id.ll_dishe)
    LinearLayout llDishe;
    @BindView(R.id.ll_practice)
    LinearLayout llPractice;
    @BindView(R.id.ll_garnish)
    LinearLayout llGarnish;
    @BindView(R.id.ll_tea)
    LinearLayout llTea;
    @BindView(R.id.ll_print)
    LinearLayout llPrint;
    @BindView(R.id.ll_exit)
    LinearLayout llExit;
    Unbinder unbinder;
    @BindView(R.id.tv_tea_money)
    TextView teaMoney;
    @BindView(R.id.line)
    View line;

    private PhotoPopupWindow mWindowAddPhoto;
    private BaseTeatDialog dialog;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_set, null);
        unbinder = ButterKnife.bind(this, view);
        DaggerBaseComponent.builder().appComponent(((BaseApplication) getActivity().getApplication()).getAppComponent()).build().inject(this);
        presenter.attachView(this);
        presenter.getSurcharge();
                mWindowAddPhoto = new PhotoPopupWindow(getActivity());
               dialog = new BaseTeatDialog(getActivity(),line);
        initListener();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }
    private String money;
    private void initListener() {
        llDishe.setOnClickListener(this);
        llPractice.setOnClickListener(this);
        llGarnish.setOnClickListener(this);
        llTea.setOnClickListener(this);
        llPrint.setOnClickListener(this);
        llExit.setOnClickListener(this);
        mWindowAddPhoto.setSureListener(new PhotoPopupWindow.ClickListener() {
            @Override
            public void clickListener() {
                   getActivity().finish();
            }
        });
        dialog.setBindClickListener(new BaseTeatDialog.BindClickListener() {
            @Override
            public void teaMoney(String moneys) {
                money=moneys;
                if (TextUtil.isNotEmpty(moneys)) {
                    presenter.editSurcharge(moneys);
                    dialog.dismiss();
                }else {
                    showToast("茶位费不允许为空");
                }


            }
        });

        //当键盘弹起的时候用屏幕的高度减去布局的高度，同时获取到键盘的高度，用键盘的高度和剩余的高度做对比
        SoftKeyBoardListener.setListener(getActivity(), new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {

            @Override
            public void keyBoardShow(int height) {
                //键盘弹起回调
                if(dialog!=null){
                    View views = dialog.getView();
                    views.setScrollY(DensityUtil.dip2px(85));
                }


            }

            @Override
            public void keyBoardHide(int height) {
                //键盘隐藏回调

                if(dialog!=null){
                    View views = dialog.getView();
                    views.setScrollY(0);
                }


            }
        });
    }

    private void initView() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_dishe://菜品
                Intent intent3 = new Intent(getActivity(), AddMenueCategoryActivity.class);
                intent3.putExtra("type",1);
                intent3.putExtra("name","菜品分类");
                startActivity(intent3);
                break;
            case R.id.ll_practice://做法
                Intent intent4 = new Intent(getActivity(), AddMenueCategoryActivity.class);
                intent4.putExtra("type",3);
                intent4.putExtra("name","做法分类");
                startActivity(intent4);
                break;
            case R.id.ll_garnish://配菜
                Intent intent5 = new Intent(getActivity(), AddMenueCategoryActivity.class);
                intent5.putExtra("type",4);
                intent5.putExtra("name","配菜分类");
                startActivity(intent5);
                break;
            case R.id.ll_tea://茶位
                dialog.show();

                break;
            case R.id.ll_print://打印
                Intent intent6 = new Intent(getActivity(), PrintSetActivity.class);
                intent6.putExtra("type",3);
//                intent5.putExtra("name","配菜分类");
                startActivity(intent6);

                break;
            case R.id.ll_exit://退出
                mWindowAddPhoto.showAsDropDown(llDishe);
                break;
        }
    }

    @Override
    public <T> void toList(List<T> list, int type) {

    }

    @Override
    public <T> void toEntity(T entity, int type) {
        if(type==6){
            teaMoney.setText(money+"元/位");
        }else {
            String tea= (String) entity;
            if(!tea.equals("0")){
                teaMoney.setText(tea+"元/位");
            }
        }

    }

    @Override
    public void showTomast(String msg) {

    }
}
