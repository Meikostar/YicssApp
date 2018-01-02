package com.canplay.repast_pad.mvp.adapter.recycle;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.canplay.repast_pad.R;
import com.canplay.repast_pad.mvp.adapter.viewholder.DishesViewHolder;


/**
 * Created by mykar on 17/4/12.
 */
public class DishesRecycleAdapter extends BaseRecycleViewAdapter {
    private Context context;

    public DishesRecycleAdapter(Context context){
        this.context=context;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dishes_itemview, null);

        return new DishesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        DishesViewHolder holders= (DishesViewHolder) holder;


    }

    @Override
    public int getItemCount() {
        int count=0;

        if(datas!=null && datas.size()>0)
        {
            count=datas.size();
        }

        return count;
    }
}
