package com.canplay.repast_wear.mvp.activity;

import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.canplay.repast_wear.R;
import com.canplay.repast_wear.base.BaseActivity;
import com.canplay.repast_wear.base.BaseApplication;
import com.canplay.repast_wear.base.RxBus;
import com.canplay.repast_wear.base.SubscriptionBean;
import com.canplay.repast_wear.mvp.component.DaggerBaseComponent;
import com.canplay.repast_wear.mvp.model.BaseType;
import com.canplay.repast_wear.mvp.present.CookClassifyContract;
import com.canplay.repast_wear.mvp.present.CookClassifyPresenter;
import com.canplay.repast_wear.util.DensityUtil;
import com.canplay.repast_wear.util.TextUtil;
import com.canplay.repast_wear.view.AddmenuDialog;
import com.canplay.repast_wear.view.Custom_TagBtn_del;
import com.canplay.repast_wear.view.FlexboxLayout;
import com.canplay.repast_wear.view.NavigationBar;
import com.canplay.repast_wear.view.SoftKeyBoardListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddMenueCategoryActivity extends BaseActivity implements CookClassifyContract.View {

    @Inject
    CookClassifyPresenter presenter;
    @BindView(R.id.navigationbar)
    NavigationBar navigationbar;
    @BindView(R.id.fbl_tag)
    FlexboxLayout fblTag;
    @BindView(R.id.line)
    View line;
    @BindView(R.id.iv_img)
    ImageView ivImg;
    @BindView(R.id.tv_text)
    TextView tvText;
    @BindView(R.id.ll_bg)
    LinearLayout llBg;
    private int type = 1;//1代表从设置进来可以删除2代表菜品进来只能增加
    private List<BaseType> tags = new ArrayList<>();//标签数据
    private AddmenuDialog dialog;
    private String title;
    private int sh;
    private int layoutH;
    private int status;//0表示列表1表示添加2表示删除

    @Override
    public void initViews() {
        setContentView(R.layout.activity_add_dish_category);
        ButterKnife.bind(this);
        DaggerBaseComponent.builder().appComponent(((BaseApplication) getApplication()).getAppComponent()).build().inject(this);
        presenter.attachView(this);

        navigationbar.setNavigationBarListener(this);
        type = getIntent().getIntExtra("type", 1);
        title = getIntent().getStringExtra("name");
        if (TextUtil.isNotEmpty(title)) {
            navigationbar.setNaviTitle(title);
        } else {
            navigationbar.setNaviTitle("菜品分类");
        }

        if (type == 1) {
            presenter.getCookClassifyList();
        } else if (type == 2) {
            presenter.getCookClassifyList();
            navigationbar.hide();
        } else if (type == 3) {
            presenter.getRecipesClassifyList();

        } else if (type == 4) {
            presenter.getFoodClassifyList();
        }


        dialog = new AddmenuDialog(this, line);


        //当键盘弹起的时候用屏幕的高度减去布局的高度，同时获取到键盘的高度，用键盘的高度和剩余的高度做对比
        SoftKeyBoardListener.setListener(AddMenueCategoryActivity.this, new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {

            @Override
            public void keyBoardShow(int height) {
                //键盘弹起回调
                if (dialog != null) {
                    View views = dialog.getView();
                    views.setScrollY(DensityUtil.dip2px(85));
                }


            }

            @Override
            public void keyBoardHide(int height) {
                //键盘隐藏回调

                if (dialog != null) {
                    View views = dialog.getView();
                    views.setScrollY(0);
                }


            }
        });


    }

    private String content;

    @Override
    public void bindEvents() {
        dialog.setBindClickListener(new AddmenuDialog.BindClickListener() {
            @Override
            public void teaMoney(String money) {
                status = 1;
                content = money;
                if (type == 1) {
                    presenter.addBookClassfy(money);
                } else if (type == 3) {
                    presenter.addRecipesClassify(money);
                } else if (type == 4) {
                    presenter.addFoodClassify(money);
                }

            }
        });
    }

    @Override
    public void initData() {

    }

    private List<BaseType> list = new ArrayList<>();

    @Override
    public void navigationRight() {
        super.navigationRight();
        list.clear();
        for (BaseType baseType : tags) {
            if (baseType.isChoos) {
                list.add(baseType);
            }
        }
        dialog.show();

    }

    private boolean isShow;
    private int del;
    /**
     * 初始化标签适配器
     */
    private int poistion;

    private void setTagAdapter() {
        fblTag.removeAllViews();
        if (tags.size() > 0) {
            for (int i = 0; i < tags.size(); i++) {
                final Custom_TagBtn_del tagBtn = createBaseFlexItemTextView(tags.get(i));
                final int position = i;
                tagBtn.setCustom_TagBtnListener(new Custom_TagBtn_del.Custom_TagBtnListener() {
                    @Override
                    public void clickDelete(int types) {

                        if (types == 1) {
                            for (int j = 0; j < tags.size(); j++) {
                                if (position == j) {
                                    if (type == 2) {
                                        RxBus.getInstance().send(SubscriptionBean.createSendBean(SubscriptionBean.ADD_MENU, tags.get(j)));
                                        finish();
                                    }

                                }
                            }
                        } else if (types == 2) {
                            if (del == 0) {
                                for (int j = 0; j < tags.size(); j++) {

                                    if (position == j) {
                                        del = 1;
                                        status = 2;
                                        poistion = j;
                                        if (type == 1) {
                                            presenter.delBookClassfy(tags.get(j).cbClassifyId);
                                        } else if (type == 3) {
                                            presenter.delRecipesClassify(tags.get(j).classifyId);
                                        } else if (type == 4) {
                                            presenter.delFoodClassify(tags.get(j).classifyId);
                                        }
                                    }
                                }

                            }

                        } else if (types == 3) {
                            BaseApplication.getInstance().mVibrator.vibrate(new long[]{0, 100}, 1);
                            for (int j = 0; j < tags.size(); j++) {
                                if (position == j) {
                                    tags.get(j).status = 1;
                                }
                            }

                        }

                        setTagAdapter();
                    }
                });
                fblTag.addView(tagBtn, i);
            }
        }

    }

    /**
     * 创建流式布局item
     *
     * @param content
     * @return
     */
    public Custom_TagBtn_del createBaseFlexItemTextView(BaseType content) {
        FlexboxLayout.LayoutParams lp = new FlexboxLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.topMargin = DensityUtil.dip2px(this, 10);
        lp.leftMargin = DensityUtil.dip2px(this, 6);
        lp.rightMargin = DensityUtil.dip2px(this, 6);


        Custom_TagBtn_del view = (Custom_TagBtn_del) LayoutInflater.from(this).inflate(R.layout.dish_item_del, null);

        if (type != 2) {
            view.setCannotClick(true);
        } else {
            view.setCannotClick(false);
        }
        if (content.status == 1) {
            view.show();
        }
        int width = (int) DensityUtil.getWidth(this) / 3;
        String name = content.name;
        TextPaint textPaint = new TextPaint();
        textPaint.setTextSize(15);
        int with = (int) textPaint.measureText(name);
        view.setSize(with + 22, 60, 15);
        view.setLayoutParams(lp);
        view.setCustomText(content.name);

        return view;
    }


    @Override
    public <T> void toList(List<T> list, int type) {
        tags = (List<BaseType>) list;
        if(tags.size()==0){
            llBg.setVisibility(View.VISIBLE);
            fblTag.setVisibility(View.GONE);
            if (type == 1) {
                ivImg.setImageResource(R.drawable.nocaipin);
                tvText.setText("暂无菜品分类");
            } else if (type == 2) {
                ivImg.setImageResource(R.drawable.nocaipin);
                tvText.setText("暂无菜品分类");
            } else if (type == 3) {
                ivImg.setImageResource(R.drawable.nozuofa);
                tvText.setText("暂无做法分类");
            } else if (type == 4) {
                ivImg.setImageResource(R.drawable.nopeicai);
                tvText.setText("暂无配菜分类");
            }
        }else {
            llBg.setVisibility(View.GONE);
            fblTag.setVisibility(View.VISIBLE);
        }
        setTagAdapter();
    }

    @Override
    public <T> void toEntity(T entity, int types) {
        if (status == 0) {
            tags = (List<BaseType>) entity;
            if(tags.size()==0){
                llBg.setVisibility(View.VISIBLE);
                fblTag.setVisibility(View.GONE);
                if (type == 1) {
                    ivImg.setImageResource(R.drawable.nocaipin);
                    tvText.setText("暂无菜品分类");
                } else if (type == 2) {
                    ivImg.setImageResource(R.drawable.nocaipin);
                    tvText.setText("暂无菜品分类");
                } else if (type == 3) {
                    ivImg.setImageResource(R.drawable.nozuofa);
                    tvText.setText("暂无做法分类");
                } else if (type == 4) {
                    ivImg.setImageResource(R.drawable.nopeicai);
                    tvText.setText("暂无配菜分类");
                }
            }else {
                llBg.setVisibility(View.GONE);
                fblTag.setVisibility(View.VISIBLE);
            }
            setTagAdapter();
        } else if (status == 1) {
            status=0;
            if (type == 1) {
                presenter.getCookClassifyList();
            } else if (type == 2) {
                presenter.getCookClassifyList();
                navigationbar.hide();
            } else if (type == 3) {
                presenter.getRecipesClassifyList();
            } else if (type == 4) {
                presenter.getFoodClassifyList();
            }
        } else if (status == 2) {
            del = 0;
            tags.remove(poistion);
            if(tags.size()==0){
                llBg.setVisibility(View.VISIBLE);
                if (type == 1) {
                    ivImg.setImageResource(R.drawable.nocaipin);
                    tvText.setText("暂无菜品分类");
                } else if (type == 2) {
                    ivImg.setImageResource(R.drawable.nocaipin);
                    tvText.setText("暂无菜品分类");
                } else if (type == 3) {
                    ivImg.setImageResource(R.drawable.nozuofa);
                    tvText.setText("暂无做法分类");
                } else if (type == 4) {
                    ivImg.setImageResource(R.drawable.nopeicai);
                    tvText.setText("暂无配菜分类");
                }
                fblTag.setVisibility(View.GONE);

            }else {
                llBg.setVisibility(View.GONE);
                fblTag.setVisibility(View.VISIBLE);
            }
            setTagAdapter();
        }

    }

    @Override
    public void showTomast(String msg) {

    }


}
