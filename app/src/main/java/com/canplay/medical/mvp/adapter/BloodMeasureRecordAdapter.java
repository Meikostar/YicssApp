package com.canplay.medical.mvp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.canplay.medical.R;
import com.canplay.medical.bean.ORDER;
import com.canplay.medical.view.DashView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


public class BloodMeasureRecordAdapter extends BaseAdapter {
    private Context mContext;
    private List<ORDER> list;

    public BloodMeasureRecordAdapter(Context mContext) {

        this.mContext = mContext;
    }

    public interface addListener {
        void getItem(String total);
    }

    public List<ORDER> getDatas() {
        return list;
    }

    private Map<Integer, Integer> map = new HashMap<>();
    private int type;
    private int state = -1;

    public void setData(List<ORDER> list, int type) {
        this.list = list;
        this.type = type;
        notifyDataSetChanged();
    }

    public void setState(int state) {
        this.state = state;
        notifyDataSetChanged();
    }

    public void setType(int type) {
        this.type = type;
        notifyDataSetChanged();
    }

    private double totalMoney;

    @Override
    public int getCount() {
        return list != null ? list.size() : 6;
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
        final ViewHolder holder;
        if (view == null) {

            view = LayoutInflater.from(mContext).inflate(R.layout.item_blooe_measure, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        if (position != 0) {
            holder.line1.setVisibility(View.VISIBLE);
        } else {
            holder.line1.setVisibility(View.GONE);

        }
        if (position % 2 == 0) {

            holder.tvTime.setVisibility(View.VISIBLE);
        } else {

            holder.tvTime.setVisibility(View.INVISIBLE);
        }
        return view;


    }

    //0待接单，1待结账 2已完成，4已撤销
    public class ResultViewHolder {

        TextView name;
        TextView tv_count;

    }

    public void setClickListener(ClickListener listener) {
        this.listener = listener;
    }

    private ClickListener listener;

    public interface ClickListener {
        void clickListener(int type, String id);
    }

    static class ViewHolder {
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.line1)
        DashView line1;
        @BindView(R.id.line2)
        DashView line2;
        @BindView(R.id.iv_cyc)
        ImageView ivCyc;
        @BindView(R.id.tv_times)
        TextView tvTimes;
        @BindView(R.id.tv_data)
        TextView tvData;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
