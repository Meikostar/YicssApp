package com.canplay.repast_pad.mvp.adapter.recycle;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.canplay.repast_pad.R;
import com.canplay.repast_pad.bean.COOK;
import com.canplay.repast_pad.mvp.adapter.viewholder.DishesViewHolder;
import com.canplay.repast_pad.util.TextUtil;


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
        final COOK data = (COOK) datas.get(position);
        if(TextUtil.isNotEmpty(data.classifyName)){
            holders.typeName.setText(data.classifyName);
        }   if(TextUtil.isNotEmpty(data.cnName)){
            holders.tvName.setText(data.cnName);
        }
        holders.tvPrice.setText(data.price);
        Glide.with(context).load(data.imgUrl).asBitmap().placeholder(R.drawable.moren).into(holders.img);
        holders.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.itemClick(data.cookbookId);
            }
        });
    }
    public interface  ItemClikcListener{
        void itemClick(String classifyId);
    }
    public ItemClikcListener listener;
    public void setItemCikcListener (ItemClikcListener listener){
        this.listener=listener;
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
