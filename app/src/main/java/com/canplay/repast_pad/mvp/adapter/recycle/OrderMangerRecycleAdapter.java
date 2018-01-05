package com.canplay.repast_pad.mvp.adapter.recycle;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.canplay.repast_pad.R;
import com.canplay.repast_pad.mvp.adapter.viewholder.OrderMangerViewHolder;

import butterknife.BindView;


/**
 * Created by mykar on 17/4/12.
 */
public class OrderMangerRecycleAdapter extends BaseRecycleViewAdapter {

    private Context context;

    public OrderMangerRecycleAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_manger_itemview, null);

        return new OrderMangerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        OrderMangerViewHolder holders = (OrderMangerViewHolder) holder;


    }

    @Override
    public int getItemCount() {
        int count = 8;

        if (datas != null && datas.size() > 0) {
            count = datas.size();
        }

        return count;
    }
}
