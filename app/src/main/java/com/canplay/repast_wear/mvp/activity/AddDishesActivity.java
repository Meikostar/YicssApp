package com.canplay.repast_wear.mvp.activity;

import android.Manifest;
import android.content.Intent;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.canplay.repast_wear.R;
import com.canplay.repast_wear.base.BaseActivity;
import com.canplay.repast_wear.base.BaseApplication;
import com.canplay.repast_wear.base.RxBus;
import com.canplay.repast_wear.base.SubscriptionBean;
import com.canplay.repast_wear.bean.COOK;
import com.canplay.repast_wear.mvp.component.DaggerBaseComponent;
import com.canplay.repast_wear.mvp.model.BaseType;
import com.canplay.repast_wear.mvp.present.CookClassifyContract;
import com.canplay.repast_wear.mvp.present.CookClassifyPresenter;
import com.canplay.repast_wear.permission.PermissionConst;
import com.canplay.repast_wear.permission.PermissionGen;
import com.canplay.repast_wear.permission.PermissionSuccess;
import com.canplay.repast_wear.util.DensityUtil;
import com.canplay.repast_wear.util.QiniuUtils;
import com.canplay.repast_wear.util.TextUtil;
import com.canplay.repast_wear.view.Custom_TagBtn;
import com.canplay.repast_wear.view.FlexboxLayout;
import com.canplay.repast_wear.view.NavigationBar;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.valuesfeng.picker.ImageSelectActivity;
import io.valuesfeng.picker.Picker;
import io.valuesfeng.picker.widget.ImageLoaderEngine;
import rx.Subscription;
import rx.functions.Action1;

public class AddDishesActivity extends BaseActivity implements View.OnClickListener, CookClassifyContract.View{

    @Inject
    CookClassifyPresenter presenter;
    @BindView(R.id.rl_img)
    RelativeLayout rlImg;
    @BindView(R.id.iv_img)
    ImageView ivImg;
    @BindView(R.id.tv_chines)
    EditText tvChines;
    @BindView(R.id.tv_english)
    EditText tvEnglish;
    @BindView(R.id.et_price)
    EditText et_price;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.ll_type)
    LinearLayout llType;

    @BindView(R.id.tv_add)
    TextView tvAdd;
    @BindView(R.id.fbl_practice)
    FlexboxLayout fblPractice;
    @BindView(R.id.tv_add2)
    TextView tvAdd2;
    @BindView(R.id.fbl_garnish)
    FlexboxLayout fblGarnish;
    @BindView(R.id.navigationBar)
    NavigationBar navigationBar;
    private Subscription mSubscription;
    private String cookbookId;
    private String resourceKey;
    private String cnName;
    private String enName;
    private String classifyId;
    private String price;
    private String foodIds;
    private String recipesIds;

    @Override
    public void initViews() {
        setContentView(R.layout.activity_add_dishes);
        ButterKnife.bind(this);
        DaggerBaseComponent.builder().appComponent(((BaseApplication) getApplication()).getAppComponent()).build().inject(this);

        presenter.attachView(this);
        navigationBar.setNavigationBarListener(this);
        cookbookId= getIntent().getStringExtra("id");
        if(TextUtil.isNotEmpty(cookbookId)){
            presenter.getCookbookInfo(cookbookId);
        }

    }

    @Override
    public void navigationRight() {
        super.navigationRight();
        price=  et_price.getText().toString().trim();
        cnName=  tvChines.getText().toString().trim();
        enName=  tvEnglish.getText().toString().trim();
//        resourceKey="https://gss0.bdstatic.com/94o3dSag_xI4khGkpoWK1HF6hhy/baike/crop%3D0%2C3%2C1000%2C660%3Bc0%3Dbaike116%2C5%2C5%2C116%2C38/sign=06593452ba1c8701c2f9e8a61a4fb21c/d01373f082025aaf8c25f47df2edab64034f1a74.jpg";

        if(TextUtil.isEmpty(resourceKey)){
            showToasts("请上传菜品图片");
            return;
        }if(TextUtil.isEmpty(cnName)){
            showToasts("请填写中文名");
            return;
        }   if(TextUtil.isEmpty(classifyId)){
            showToasts("请选择菜品类型");
            return;
        }   if(TextUtil.isEmpty(price)){
            showToasts("请填写菜品价格");
            return;
        }
        showProgress("添加中...");
        presenter.createOrEditCookbook(cookbookId,resourceKey,cnName,enName,classifyId,price,foodIds,recipesIds);
    }

    private List<BaseType> datas=new ArrayList<>();
    @Override
    public void bindEvents() {
        tvAdd.setOnClickListener(this);
        tvAdd2.setOnClickListener(this);
        llType.setOnClickListener(this);
        rlImg.setOnClickListener(this);
        ivImg.setOnClickListener(this);
        mSubscription = RxBus.getInstance().toObserverable(SubscriptionBean.RxBusSendBean.class).subscribe(new Action1<SubscriptionBean.RxBusSendBean>() {
            @Override
            public void call(SubscriptionBean.RxBusSendBean bean) {
                if (bean == null) return;
                 datas.clear();
                List<BaseType> content ;
                if(bean.type==SubscriptionBean.ADD_FEILEI){
                    content = (List<BaseType>) bean.content;
                    datas.addAll(content);
                    setTagAdapter(fblPractice);
                    int i=0;
                    for(BaseType base:content){
                        if(i==0){
                            recipesIds=base.classifyId;
                        }else {
                            recipesIds=recipesIds+","+base.classifyId;
                        }
                        i++;
                    }
                }else if(bean.type==SubscriptionBean.ADD_PEICAI){
                    content = (List<BaseType>) bean.content;
                    datas.addAll(content);
                    setTagAdapter(fblGarnish);
                    int i=0;
                    for(BaseType base:content){
                        if(i==0){
                            foodIds=base.classifyId;
                        }else {
                            foodIds=foodIds+","+base.classifyId;
                        }
                        i++;
                    }
                }else if(bean.type==SubscriptionBean.ADD_MENU){
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

    @Override
    public void initData() {

    }

    /**
     * 初始化标签适配器
     */
    private void setTagAdapter(FlexboxLayout fblGarnish) {
        fblGarnish.removeAllViews();
        if (datas.size() > 0) {
            for (int i = 0; i < datas.size(); i++) {
                final Custom_TagBtn tagBtn = createBaseFlexItemTextView(datas.get(i));
                final int position = i;
                tagBtn.setCustom_TagBtnListener(new Custom_TagBtn.Custom_TagBtnListener() {
                    @Override
                    public void clickDelete(int type) {
                        for (int j = 0; j < datas.size(); j++) {

                        }
                    }
                });
                fblGarnish.addView(tagBtn, i);
            }
        }
    }
    /**
     * 创建流式布局item
     *
     * @param content
     * @return
     */
    public Custom_TagBtn createBaseFlexItemTextView(BaseType content) {
        FlexboxLayout.LayoutParams lp = new FlexboxLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.topMargin = DensityUtil.dip2px(this, 10);
        lp.leftMargin = DensityUtil.dip2px(this, 15);


        Custom_TagBtn view = (Custom_TagBtn) LayoutInflater.from(this).inflate(R.layout.dish_item, null);
        view.setBg(R.drawable.hui_regle);
        view.setColors(R.color.slow_black);
        String name = content.name;
        TextPaint textPaint = new TextPaint();
        textPaint.setTextSize(15);
        int  with = (int) textPaint.measureText(name);
        view.setSize(with,30,13,0);
        view.setLayoutParams(lp);
        view.setCustomText(content.name);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_add:
                Intent intent = new Intent(AddDishesActivity.this, AddDishCategoryActivity.class);
                intent.putExtra("type",1);
                intent.putExtra("ids",recipesIds);
                startActivity(intent);
                break;
            case R.id.tv_add2:
                Intent intent1 = new Intent(AddDishesActivity.this, AddDishCategoryActivity.class);
                intent1.putExtra("type",2);
                intent1.putExtra("ids",foodIds);
                startActivity(intent1);
                break;
            case R.id.ll_type:
                Intent intent3 = new Intent(AddDishesActivity.this, AddMenueCategoryActivity.class);
                intent3.putExtra("type",2);
                startActivity(intent3);

                break;
            case R.id.rl_img:
                PermissionGen.with(AddDishesActivity.this)
                        .addRequestCode(PermissionConst.REQUECT_CODE_CAMERA)
                        .permissions(Manifest.permission.CAMERA,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE)
                        .request();
                break;
            case R.id.iv_img:
                Picker.from(this)
                        .count(1)
                        .enableCamera(true)
                        .setEngine(new ImageLoaderEngine())
                        .setAdd_watermark(false)
                        .forResult(4);
                break;

        }
    }

    @PermissionSuccess(requestCode = PermissionConst.REQUECT_CODE_CAMERA)
    public void requestSdcardSuccess() {
        Picker.from(this)
                .count(1)
                .enableCamera(true)
                .setEngine(new ImageLoaderEngine())
                .setAdd_watermark(false)
                .forResult(4);

    }
    public String path;
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                //上传照片
                case 4:
                    List<String> imgs = data.getStringArrayListExtra(ImageSelectActivity.EXTRA_RESULT_SELECTION);
                    path = imgs.get(0);
                    ivImg.setVisibility(View.VISIBLE);
                    rlImg.setVisibility(View.GONE);
                    Glide.with(this).load(path).asBitmap().into(ivImg);
                    presenter.getToken(path);
                    break;

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
    private COOK cook;
    private String token;
    @Override
    public <T> void toEntity(T entity, int type) {
        if(type==2){
            token= (String) entity;
            showProgress("上传中...");
            QiniuUtils.getInstance().upFile(path, token, new QiniuUtils.CompleteListener() {
                @Override
                public void completeListener(String url) {
                    showTomast("上传成功");
                    dimessProgress();
                    resourceKey=url;
                }
            });
        }else if(type==3){
            dimessProgress();
            if(TextUtil.isNotEmpty(cookbookId)){
                showTomast("编辑成功");
                RxBus.getInstance().send(SubscriptionBean.createSendBean(SubscriptionBean.MENU_REFASH,""));
            }else {
                showTomast("添加成功");
                RxBus.getInstance().send(SubscriptionBean.createSendBean(SubscriptionBean.MENU_REFASH,""));
            }

            finish();
        }else {
            cook= (COOK) entity;

            if(cook.recipesClassifyInfos!=null&&cook.recipesClassifyInfos.size()>0){
                datas.addAll(cook.recipesClassifyInfos);
                setTagAdapter(fblPractice);
                int i=0;
                for(BaseType base:cook.recipesClassifyInfos){
                    if(i==0){
                        recipesIds=base.classifyId;
                    }else {
                        recipesIds=recipesIds+","+base.classifyId;
                    }
                    i++;
                }
            }


            datas.clear();
            if(cook.foodClassifyInfos!=null&&cook.foodClassifyInfos.size()>0){
                datas.addAll(cook.foodClassifyInfos);
                setTagAdapter(fblGarnish);
                int a=0;
                for(BaseType base:cook.foodClassifyInfos){
                    if(a==0){
                        foodIds=base.classifyId;
                    }else {
                        foodIds=foodIds+","+base.classifyId;
                    }
                    a++;
                }
            }


            et_price.setText(cook.price);
            tvChines.setText(cook.cnName);
            if(!TextUtil.isEmpty(cook.enName)){
                tvEnglish.setText(cook.enName);
            }if(!TextUtil.isEmpty(cook.classifyName)){
                tvType.setText(cook.classifyName);
                classifyId=cook.classifyId;
                tvType.setTextColor(getResources().getColor(R.color.slow_black));
            }
            if(!TextUtil.isEmpty(cook.imgUrl)){
                resourceKey=cook.imgUrl;
            }

            ivImg.setVisibility(View.VISIBLE);
            rlImg.setVisibility(View.GONE);
            Glide.with(this).load(cook.imgUrl).asBitmap().placeholder(R.drawable.moren).into(ivImg);
//            resourceKey="https://gss0.bdstatic.com/94o3dSag_xI4khGkpoWK1HF6hhy/baike/crop%3D0%2C3%2C1000%2C660%3Bc0%3Dbaike116%2C5%2C5%2C116%2C38/sign=06593452ba1c8701c2f9e8a61a4fb21c/d01373f082025aaf8c25f47df2edab64034f1a74.jpg";

        }


    }

    @Override
    public void showTomast(String msg) {
       dimessProgress();
    }
}
