package com.canplay.repast_pad.mvp.adapter.viewholder;

/**
 * Created by Meiko on 2018/1/7.
 */
public class CardItem {

    private int mTextResource;
    private int mTitleResource;

    public CardItem(int title, int text) {
        mTitleResource = title;
        mTextResource = text;
    }

    public int getText() {
        return mTextResource;
    }

    public int getTitle() {
        return mTitleResource;
    }
}