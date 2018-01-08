package com.canplay.repast_pad.mvp.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.canplay.repast_pad.R;
import com.canplay.repast_pad.base.BaseActivity;
import com.canplay.repast_pad.base.RxBus;
import com.canplay.repast_pad.base.SubscriptionBean;
import com.canplay.repast_pad.mvp.model.BaseType;
import com.canplay.repast_pad.permission.PermissionConst;
import com.canplay.repast_pad.permission.PermissionGen;
import com.canplay.repast_pad.permission.PermissionSuccess;
import com.canplay.repast_pad.util.DensityUtil;
import com.canplay.repast_pad.view.Custom_TagBtn;
import com.canplay.repast_pad.view.FlexboxLayout;
import com.canplay.repast_pad.view.NavigationBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.valuesfeng.picker.ImageSelectActivity;
import io.valuesfeng.picker.Picker;
import io.valuesfeng.picker.widget.ImageLoaderEngine;
import rx.Subscription;
import rx.functions.Action1;

public class AddDishesActivity extends BaseActivity implements View.OnClickListener{


    @BindView(R.id.rl_img)
    RelativeLayout rlImg;
    @BindView(R.id.iv_img)
    ImageView ivImg;
    @BindView(R.id.tv_chines)
    EditText tvChines;
    @BindView(R.id.tv_english)
    EditText tvEnglish;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.ll_type)
    LinearLayout llType;
    @BindView(R.id.tv_price)
    TextView tvPrice;
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
    @Override
    public void initViews() {
        setContentView(R.layout.activity_add_dishes);
        ButterKnife.bind(this);
        navigationBar.setNavigationBarListener(this);

    }

    @Override
    public void navigationRight() {
        super.navigationRight();
    }

    private List<BaseType> datas=new ArrayList<>();
    @Override
    public void bindEvents() {
        tvAdd.setOnClickListener(this);
        tvAdd2.setOnClickListener(this);
        rlImg.setOnClickListener(this);
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
                }else if(bean.type==SubscriptionBean.ADD_PEICAI){
                    content = (List<BaseType>) bean.content;
                    datas.addAll(content);
                    setTagAdapter(fblGarnish);
                }else if(bean.type==SubscriptionBean.ADD_MENU){
                    BaseType  beans = (BaseType) bean.content;
                    tvType.setText(beans.name);
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
        view.setSize(55,30,13);
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
                startActivity(intent);
                break;
            case R.id.tv_add2:
                Intent intent1 = new Intent(AddDishesActivity.this, AddDishCategoryActivity.class);
                intent1.putExtra("type",2);
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
                    break;

            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSubscription.unsubscribe();
    }
}
