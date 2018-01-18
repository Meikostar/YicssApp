package com.canplay.yicss_app.mvp.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.canplay.yicss_app.R;
import com.canplay.yicss_app.mvp.model.BaseType;

import java.util.List;


public class TypesAdapter extends BaseAdapter {
    private Context mContext;
    private List<BaseType> list;

    public TypesAdapter(Context mContext) {

        this.mContext = mContext;
    }

    public interface ItemCliks {
        void getItem( int poistioin);
    }

    private ItemCliks listener;

    public void setClickListener(ItemCliks listener) {
        this.listener = listener;
    }

    public void setData(List<BaseType> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return  8;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public int[] imgs={R.drawable.tp1,R.drawable.tp2,R.drawable.tp3,R.drawable.tp4,
            R.drawable.tp5,R.drawable.tp6,R.drawable.tp7,R.drawable.tp8};
    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view == null) {
            holder=new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.type_menu_item, parent, false);

            holder.card1 = (CardView) view.findViewById(R.id.card1);
            holder.img = (ImageView) view.findViewById(R.id.img);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
         holder.img.setImageResource(imgs[position]);

        holder.card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.getItem(position);
            }
        });
        return view;


    }





    static  class ViewHolder {

       public ImageView img;

        public CardView card1;


    }
}
