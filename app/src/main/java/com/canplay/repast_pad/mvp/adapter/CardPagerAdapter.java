package com.canplay.repast_pad.mvp.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.canplay.repast_pad.R;
import com.canplay.repast_pad.mvp.adapter.viewholder.CardItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Meiko on 2018/1/7.
 */


public class CardPagerAdapter extends PagerAdapter implements CardAdapter {

    private List<CardView> mViews;
    private List<String> mData;
    private float mBaseElevation;
    private Context context;
    public CardPagerAdapter(Context context) {
        this.context=context;
        mData = new ArrayList<>();
        mViews = new ArrayList<>();
    }

    public void addCardItem(String item) {
        mViews.add(null);
        mData.add(item);
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
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.adapter, container, false);
        container.addView(view);

        CardView cardView = (CardView) view.findViewById(R.id.cardView);
        ImageView imgview = (ImageView) view.findViewById(R.id.iv_menu_imgs);
        TextView tv_number = (TextView) view.findViewById(R.id.tv_number);
        tv_number.setText("0"+position);
        Glide.with(context).load(mData.get(position)).placeholder(R.drawable.blue_regle).into(imgview);
        if (mBaseElevation == 0) {
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