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
import com.canplay.repast_pad.permission.PermissionConst;
import com.canplay.repast_pad.permission.PermissionGen;
import com.canplay.repast_pad.permission.PermissionSuccess;
import com.canplay.repast_pad.util.DensityUtil;
import com.canplay.repast_pad.view.Custom_TagBtn;
import com.canplay.repast_pad.view.FlexboxLayout;

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
    private Subscription mSubscription;
    @Override
    public void initViews() {
        setContentView(R.layout.activity_add_dishes);
        ButterKnife.bind(this);
    }

    @Override
    public void bindEvents() {
        tvAdd.setOnClickListener(this);
        tvAdd2.setOnClickListener(this);
        rlImg.setOnClickListener(this);
        mSubscription = RxBus.getInstance().toObserverable(SubscriptionBean.RxBusSendBean.class).subscribe(new Action1<SubscriptionBean.RxBusSendBean>() {
            @Override
            public void call(SubscriptionBean.RxBusSendBean bean) {
                if (bean == null) return;
                if(bean.type==SubscriptionBean.ADD_FEILEI){

                }else if(bean.type==SubscriptionBean.ADD_PEICAI){

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

    private ArrayList<String> tags = new ArrayList<>();//标签数据

    /**
     * 初始化标签适配器
     */
    private void setTagAdapter(FlexboxLayout fblGarnish) {
        fblGarnish.removeAllViews();
        if (tags.size() > 0) {
            for (int i = 0; i < tags.size(); i++) {
                final Custom_TagBtn tagBtn = createBaseFlexItemTextView(tags.get(i),1);
                final int position = i;
                tagBtn.setCustom_TagBtnListener(new Custom_TagBtn.Custom_TagBtnListener() {
                    @Override
                    public void clickDelete() {
                        for (int j = 0; j < tags.size(); j++) {

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
    public Custom_TagBtn createBaseFlexItemTextView(String content,int type) {
        FlexboxLayout.LayoutParams lp = new FlexboxLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.topMargin = DensityUtil.dip2px(this, 10);
        lp.leftMargin = DensityUtil.dip2px(this, 15);

        Custom_TagBtn view = (Custom_TagBtn) LayoutInflater.from(this).inflate(R.layout.dish_item, null);
        if(type==1){
            view.setBg(R.drawable.blue_regle);
        }else {
            view.setBg(R.drawable.white_regle);
        }

        view.setLayoutParams(lp);
        view.setCustomText(content);

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
                    Glide.with(this).load(path).asBitmap().into(ivImg);
                    break;

            }
        }
    }
}
