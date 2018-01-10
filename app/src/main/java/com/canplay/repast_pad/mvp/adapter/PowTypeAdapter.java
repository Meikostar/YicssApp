package com.canplay.repast_pad.mvp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.canplay.repast_pad.R;
import com.canplay.repast_pad.bean.COOK;
import com.canplay.repast_pad.mvp.model.BaseType;
import com.canplay.repast_pad.mvp.model.CONTURY;
import com.canplay.repast_pad.mvp.model.PROVINCE;
import com.canplay.repast_pad.util.TextUtil;

import java.util.List;


public class PowTypeAdapter extends BaseAdapter {
    private Context mContext;
    private List<BaseType> list;
    public PowTypeAdapter(Context mContext) {

        this.mContext = mContext;
    }
    public interface ItemCliks{
        void getItem(int poistion, String id);
    }
    private ItemCliks listener;
    public void setClickListener(ItemCliks listener){
        this.listener=listener;
    }
    public void setData(List<BaseType> list){
        this.list=list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list!=null?list.size():0;
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
        if (view == null){
            holder = new ResultViewHolder();
             view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.dishes_item, parent, false);
            holder.name= (TextView) view.findViewById(R.id.tv_name);
            view.setTag(holder);
        }else{
            holder = (ResultViewHolder) view.getTag();
        }
          if(TextUtil.isNotEmpty(list.get(position).name)){
              holder.name.setText(list.get(position).name);
          }
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.getItem(position,list.get(position).cbClassifyId);
            }
        });
        return view;


    }

    public  class ResultViewHolder{

        TextView name;


    }
}
