package com.canplay.repast_pad.mvp.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.canplay.repast_pad.R;
import com.canplay.repast_pad.bean.COOK;
import com.canplay.repast_pad.mvp.model.BaseType;
import com.canplay.repast_pad.util.TextUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Meiko on 2018/1/7.
 */


public class CardPagerAdapter extends PagerAdapter implements CardAdapter {


    private List<CardView> mViews;
    private List<COOK> mData;
    private float mBaseElevation;
    private Context context;

    public CardPagerAdapter(Context context) {
        this.context = context;
        mData = new ArrayList<>();
        mViews = new ArrayList<>();
    }

    public void addCardItem(COOK item) {
        mViews.add(null);
        mData.add(item);
    }
    public void setDatas(List<COOK> list){
        mData=list;
    }
    public interface  ItemClickListener{
        void  ItemClick(String id,int state,int poistion);
    }
    private ItemClickListener listener;
    public void setClickListener(ItemClickListener listener){
        this.listener=listener;
    }
    public float getBaseElevation() {
        return mBaseElevation;
    }

    @Override
    public CardView getCardViewAt(int position) {
        return mViews.get(position);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.adapter, container, false);
        ButterKnife.bind(this, view);
        container.addView(view);
        final COOK cok=mData.get(position);
        CardView cardView = (CardView) view.findViewById(R.id.cardView);
        ImageView imgview = (ImageView) view.findViewById(R.id.iv_menu_imgs);
        TextView tv_number = (TextView) view.findViewById(R.id.tv_number);
        TextView tv_chines = (TextView) view.findViewById(R.id.tv_chines);
        TextView tv_english = (TextView) view.findViewById(R.id.tv_english);
        TextView tv_money = (TextView) view.findViewById(R.id.tv_money);
        TextView tv_specif = (TextView) view.findViewById(R.id.tv_specif);
        TextView tv_taste = (TextView) view.findViewById(R.id.tv_taste);
        TextView tv_sauce = (TextView) view.findViewById(R.id.tv_sauce);
        TextView tv_staus = (TextView) view.findViewById(R.id.tv_staus);
        tv_staus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.ItemClick(cok.cookbookId,cok.state,position);
            }
        });
        tv_chines.setText(cok.cnName);
        tv_money.setText("￥ "+cok.price);
        if(TextUtil.isNotEmpty(cok.enName)){
            tv_english.setText(cok.enName);
        }else {
            tv_english.setVisibility(View.GONE);
        }
        if(position<10){
            tv_number.setText("0"+(position+1));
        }else {
            tv_number.setText(""+(position+1));
        }

        List<BaseType> recipesClassifyInfos = cok.recipesClassifyInfos;
        List<BaseType> foodClassifyInfos = cok.foodClassifyInfos;
        if(recipesClassifyInfos!=null&&recipesClassifyInfos.size()>0){
            int i=0;
            String name="";
            for(BaseType base:recipesClassifyInfos){
                if(i==0){
                    name=base.name;
                }else {
                    name=name+","+ base.name;;
                }
                i++;
            }
            tv_taste.setText(name);
        }else {
            tv_taste.setVisibility(View.GONE);
        }  if(foodClassifyInfos!=null&&foodClassifyInfos.size()>0){
            int i=0;
            String name="";
            for(BaseType base:foodClassifyInfos){
                if(i==0){
                    name=base.name;
                }else {
                    name=name+","+ base.name;;
                }
                i++;
            }
            tv_sauce.setText(name);
        }else {
            tv_sauce.setVisibility(View.GONE);
        }
        if(cok.state==0){
            tv_staus.setText("在售");
        }else {
            tv_staus.setText("售空");
        }
        Glide.with(context).load(mData.get(position).imgUrl).placeholder(R.drawable.blue_regle).into(imgview);
        if (mBaseElevation ==0) {
            mBaseElevation = cardView.getCardElevation();
        }
        cardView.setMaxCardElevation(mBaseElevation * MAX_ELEVATION_FACTOR);
        mViews.set(position, cardView);
        return view;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        mViews.set(position, null);
    }






}