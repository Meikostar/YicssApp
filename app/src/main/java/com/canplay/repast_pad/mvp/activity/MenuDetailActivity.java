package com.canplay.repast_pad.mvp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.canplay.repast_pad.R;
import com.canplay.repast_pad.base.BaseActivity;
import com.canplay.repast_pad.base.BaseApplication;
import com.canplay.repast_pad.base.RxBus;
import com.canplay.repast_pad.base.SubscriptionBean;
import com.canplay.repast_pad.bean.COOK;
import com.canplay.repast_pad.mvp.adapter.CountAdapter;
import com.canplay.repast_pad.mvp.component.DaggerBaseComponent;
import com.canplay.repast_pad.mvp.model.BaseType;
import com.canplay.repast_pad.mvp.present.CookClassifyContract;
import com.canplay.repast_pad.mvp.present.CookClassifyPresenter;
import com.canplay.repast_pad.util.TextUtil;
import com.canplay.repast_pad.view.NavigationBar;
import com.canplay.repast_pad.view.RegularListView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscription;
import rx.functions.Action1;

public class MenuDetailActivity extends BaseActivity  implements CookClassifyContract.View {


    @Inject
    CookClassifyPresenter presenter;
    @BindView(R.id.navigationBar)
    NavigationBar navigationBar;
    @BindView(R.id.tv_style)
    TextView tvStyle;
    @BindView(R.id.ll_style)
    LinearLayout llStyle;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.ll_type)
    LinearLayout llType;
    @BindView(R.id.rl_menu)
    RegularListView rlMenu;
    @BindView(R.id.tv_pay_sure)
    TextView tvPaySure;
    @BindView(R.id.tv_hint)
    TextView tvHint;
    @BindView(R.id.et_sort)
    EditText etSort;
    private CountAdapter adapter;
    public int cout;
    private List<BaseType> datas = new ArrayList<>();
    private COOK cook;
    private String classifyId;
    private String templateId;
    private Subscription mSubscription;
    @Override
    public void initViews() {
        setContentView(R.layout.activity_menu_detail);
        ButterKnife.bind(this);
        DaggerBaseComponent.builder().appComponent(((BaseApplication)getApplication()).getAppComponent()).build().inject(this);
        presenter.attachView(this);
        navigationBar.setNavigationBarListener(this);
        cout = getIntent().getIntExtra("type", 0);

        adapter = new CountAdapter(this);
        rlMenu.setAdapter(adapter);
        cook = (COOK) getIntent().getSerializableExtra("cook");
        sort =  getIntent().getStringExtra("sort");
        mSubscription = RxBus.getInstance().toObserverable(SubscriptionBean.RxBusSendBean.class).subscribe(new Action1<SubscriptionBean.RxBusSendBean>() {
            @Override
            public void call(SubscriptionBean.RxBusSendBean bean) {
                if (bean == null) return;

                if(bean.type==SubscriptionBean.ADD_MENU){
                    BaseType  beans = (BaseType) bean.content;
                    classifyId=beans.cbClassifyId;
                    tvType.setText(beans.name);
                    tvType.setTextColor(getResources().getColor(R.color.slow_black));
                }

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                throwable.printStackTrace();
            }
        });
        RxBus.getInstance().addSubscription(mSubscription);
    }

    private int CHOOSE = 1;
    private int TYPES = 2;
    private int poistion = 0;
    private String cookbookIds;
    private String sort;
    @Override
    public void bindEvents() {
        llStyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuDetailActivity.this, AddMenuActivity.class);
                intent.putExtra("type", 1);
                startActivityForResult(intent, TYPES);
            }
        });
        adapter.setClickListener(new CountAdapter.ItemCliks() {
            @Override
            public void getItem(BaseType baseType, int poistioin) {
                poistion = poistioin;
                if(TextUtil.isNotEmpty(classifyId)){
                    Intent intent = new Intent(MenuDetailActivity.this, ChooseFoodActivity.class);
                    intent.putExtra("id", classifyId);
                    startActivityForResult(intent, CHOOSE);
                }else {
                    showToasts("请先选菜单类型");
                }

            }
        });
        llType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(MenuDetailActivity.this, AddMenueCategoryActivity.class);
                intent3.putExtra("type",2);
                startActivity(intent3);
            }
        });
        tvPaySure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<BaseType> datas = adapter.getDatas();
                if(TextUtil.isEmpty(classifyId)){
                    showToasts("请选择菜品类型");
                    return;
                }
                int i=0;
                for(BaseType base:datas){

                    if(TextUtil.isEmpty(base.cookbookId)){
                        showToasts("请选择菜品");
                        return;
                    }
                    if(i==0){
                        cookbookIds=base.cookbookId;
                    }else {
                        cookbookIds=cookbookIds+","+base.cookbookId;
                    }
                }
               sort= etSort.getText().toString().trim();
                if(TextUtil.isEmpty(sort)){
                    showToasts("请填写序号");
                    return;
                }
                if(!TextUtil.isEmpty(tvHint.getText().toString())){
                    showToasts("该菜单号已经存在，请重新输入");
                    return;
                }


                presenter.createOrEditMenu(menuId,templateId,classifyId,cookbookIds,sort);
            }
        });
        etSort.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(TextUtil.isNotEmpty(s.toString())){

                    String sorts = BaseApplication.map.get(s.toString());
                    if(TextUtil.isNotEmpty(sorts)){
                        if(TextUtil.isNotEmpty(sort)){
                            if(!sort.equals(sorts)){
                                tvHint.setVisibility(View.VISIBLE);
                                tvHint.setText("提示: 序号"+sort+"菜单已经存在，请重新输入菜单序列号。");
                            }
                        }else {
                            tvHint.setVisibility(View.VISIBLE);
                            tvHint.setText("提示: 序号"+sort+"菜单已经存在，请重新输入菜单序列号。");
                        }

                   }else {
                        tvHint.setVisibility(View.GONE);
                        tvHint.setText("");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    private String menuId;
    @Override
    public void initData() {
        for (int i = 0; i < cout; i++) {
            BaseType baseType = new BaseType();
            datas.add(baseType);
        }
        if(cook!=null){
            classifyId=cook.classifyId;
            sort=cook.sort;
            templateId=cook.templateId;
            menuId=cook.menuId;
            if(TextUtil.isNotEmpty(cook.classifyName)){
                tvType.setText(cook.classifyName);
                tvType.setTextColor(getResources().getColor(R.color.slow_black));
            }
            if(TextUtil.isNotEmpty(sort)){
                etSort.setText(sort);
            }


            for (COOK ck : cook.cookbookInfo) {
                BaseType baseType = new BaseType();
                baseType.name = ck.cnName;
                baseType.cookbookId = ck.cookbookId;
                baseType.classifyId = classifyId;
                datas.add(baseType);
            }
        }

        if (cook != null) {
            cout = cook.cookbookInfo.size();
        }
        templateId=cout+"";
        tvStyle.setText("一屏" + cout + "道菜");
        adapter.setData(datas);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == CHOOSE) {
                COOK ck = (COOK) data.getSerializableExtra("cook");
                BaseType baseType = datas.get(poistion);
                baseType.name = ck.cnName;
                baseType.cookbookId = ck.cookbookId;
                baseType.classifyId = ck.classifyId;
                adapter.setData(datas);
            }else {
                cout = data.getIntExtra("type", 0);
                for (int i = 0; i < cout; i++) {
                    BaseType baseType = new BaseType();
                    datas.add(baseType);
                    templateId=cout+"";
                    tvStyle.setText("一屏" + cout + "道菜");
                    adapter.setData(datas);
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSubscription.unsubscribe();
    }

    @Override
    public <T> void toList(List<T> list, int type) {

    }

    @Override
    public <T> void toEntity(T entity, int type) {
       if(cook!=null){
           showToasts("编辑成功");
           Intent intent = new Intent();
           setResult(RESULT_OK,intent);

       }else {
           showToasts("添加成功");
       }
        RxBus.getInstance().send(SubscriptionBean.createSendBean(SubscriptionBean.MENU_REFASHS,""));
        RxBus.getInstance().send(SubscriptionBean.createSendBean(SubscriptionBean.FINISH,""));
       finish();

    }

    @Override
    public void showTomast(String msg) {

    }
}
