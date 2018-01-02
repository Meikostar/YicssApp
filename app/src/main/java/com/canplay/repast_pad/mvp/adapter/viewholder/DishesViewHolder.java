package com.canplay.repast_pad.mvp.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.canplay.repast_pad.R;


/**
 * Created by mykar on 17/4/12.
 */
public class DishesViewHolder extends RecyclerView.ViewHolder  {

    public ImageView img;
    public TextView tvName;
    public TextView tvPrice;
    public TextView typeName;
    public LinearLayout ll_bg;
    public DishesViewHolder(View itemView) {
        super(itemView);
        img= (ImageView) itemView.findViewById(R.id.iv_img);
        tvName= (TextView) itemView.findViewById(R.id.tv_name);
        tvPrice= (TextView) itemView.findViewById(R.id.tv_price);
        typeName= (TextView) itemView.findViewById(R.id.tv_type);

    }
}
