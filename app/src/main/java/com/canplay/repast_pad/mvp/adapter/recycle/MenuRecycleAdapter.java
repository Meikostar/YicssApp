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
import com.canplay.repast_pad.mvp.adapter.viewholder.MenuViewHolder;
import com.canplay.repast_pad.util.TextUtil;


/**
 * Created by mykar on 17/4/12.
 */
public class MenuRecycleAdapter extends BaseRecycleViewAdapter {
    private Context context;
    public int[] imgs={R.drawable.tp1,R.drawable.tp2,R.drawable.tp3,R.drawable.tp4,
            R.drawable.tp5,R.drawable.tp6,R.drawable.tp7,R.drawable.tp8};
    public MenuRecycleAdapter(Context context){
        this.context=context;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.type_menu_item, null);

        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        MenuViewHolder holders= (MenuViewHolder) holder;
        holders.img.setImageResource(imgs[position]);
        holders.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.itemClick(position);
            }
        });
    }
    public interface  ItemClikcListener{
        void itemClick(int data);
    }
    public ItemClikcListener listener;
    public void setItemCikcListener (ItemClikcListener listener){
        this.listener=listener;
    }
    @Override
    public int getItemCount() {
        int count=8;
//        if(datas!=null && datas.size()>0)
//        {
//            count=datas.size();
//        }

        return count;
    }
}
