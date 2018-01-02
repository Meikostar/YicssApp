package com.canplay.repast_pad.mvp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.canplay.repast_pad.R;
import com.canplay.repast_pad.mvp.model.CONTURY;
import com.canplay.repast_pad.mvp.model.PROVINCE;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class OrderAdapter extends BaseAdapter {
    private Context mContext;
    private List<CONTURY> list;

    public OrderAdapter(Context mContext) {

        this.mContext = mContext;
    }

    public interface ItemCliks {
        void getItem(int poistion, String name, int id);
    }

    public void setData(List<PROVINCE> list) {
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list != null ? list.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        ResultViewHolder holder;
        if (view == null) {
            holder = new ResultViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.order_detail_item, parent, false);
            view.setTag(holder);
        } else {
            holder = (ResultViewHolder) view.getTag();
        }
        return view;


    }

    public class ResultViewHolder {

        TextView name;
        TextView tv_count;

    }

    static class ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_detail)
        TextView tvDetail;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.tv_less)
        TextView tvLess;
        @BindView(R.id.tv_count)
        TextView tvCount;
        @BindView(R.id.tv_add)
        TextView tvAdd;
        @BindView(R.id.ll_editor)
        LinearLayout llEditor;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
