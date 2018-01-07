package com.canplay.repast_pad.mvp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.canplay.repast_pad.R;
import com.canplay.repast_pad.base.BaseActivity;
import com.canplay.repast_pad.mvp.adapter.CardFragmentPagerAdapter;
import com.canplay.repast_pad.mvp.adapter.CardPagerAdapter;
import com.canplay.repast_pad.mvp.adapter.recycle.ShadowTransformer;
import com.canplay.repast_pad.mvp.adapter.viewholder.CardItem;
import com.canplay.repast_pad.util.DensityUtil;
import com.canplay.repast_pad.view.BaseSelectDialog;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MenuDetailEditorActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.top_view_back)
    ImageView topViewBack;
    @BindView(R.id.topview_left_layout)
    LinearLayout topviewLeftLayout;
    @BindView(R.id.navigationbar_title)
    TextView navigationbarTitle;
    @BindView(R.id.tv_delete)
    TextView tvDelete;
    @BindView(R.id.tv_new)
    TextView tvNew;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tv_chines)
    TextView tvChines;
    @BindView(R.id.tv_english)
    TextView tvEnglish;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.tv_specif)
    TextView tvSpecif;
    @BindView(R.id.tv_taste)
    TextView tvTaste;
    @BindView(R.id.tv_sauce)
    TextView tvSauce;
    @BindView(R.id.tv_staus)
    TextView tvStaus;
    @BindView(R.id.line)
    View line;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    private CardPagerAdapter mCardAdapter;
    private ShadowTransformer mCardShadowTransformer;
    private BaseSelectDialog dialog;
    @Override
    public void initViews() {
        setContentView(R.layout.activity_menu_detail_editor);
        ButterKnife.bind(this);
        dialog=new  BaseSelectDialog(this,line);

        mCardAdapter = new CardPagerAdapter(this);
        mCardAdapter.addCardItem("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1515327988995&di=394c714626599d4b37c857572e38ca5a&imgtype=0&src=http%3A%2F%2Fpic5.photophoto.cn%2F20071221%2F0042040377755194_b.jpg");
        mCardAdapter.addCardItem("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1515327988994&di=4f1e1ae0d6ec752d67a01eb97428b4fc&imgtype=0&src=http%3A%2F%2Fpic.58pic.com%2F00%2F89%2F10%2F78bOOOPIC8c.jpg");
        mCardAdapter.addCardItem("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1515327988992&di=63de448ee7de913ebd52dbb2ef5d0279&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2F9922720e0cf3d7ca4a050f58f91fbe096b63a928.jpg");
        mCardAdapter.addCardItem("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1515327988989&di=eed378dbfcf256f0966eec34c47c43ad&imgtype=0&src=http%3A%2F%2Fscimg.jb51.net%2Fallimg%2F140313%2F10-140313212146464.jpg");
        mCardAdapter.addCardItem("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1515328092604&di=afd69c87daae8d317d07effca0d00987&imgtype=0&src=http%3A%2F%2Fpic107.nipic.com%2Ffile%2F20160816%2F20860925_080643495000_2.jpg");


        mCardShadowTransformer = new ShadowTransformer(viewPager, mCardAdapter);
        viewPager.setAdapter(mCardAdapter);
        viewPager.setPageTransformer(false, mCardShadowTransformer);
        viewPager.setOffscreenPageLimit(3);
         viewPager.setCurrentItem(2);
        mCardShadowTransformer = new ShadowTransformer(viewPager, mCardAdapter);



    }

    @Override
    public void bindEvents() {
        dialog.setBindClickListener(new BaseSelectDialog.BindClickListener() {
            @Override
            public void tasteNum() {

            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_delete:
                dialog.show();
                break;
            case R.id.tv_new:
                Intent intent = new Intent(MenuDetailEditorActivity.this, MenuDetailActivity.class);
                startActivity(intent);
                break;

        }
    }
}
