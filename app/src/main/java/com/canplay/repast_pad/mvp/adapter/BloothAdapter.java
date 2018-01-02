package com.canplay.repast_pad.mvp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.canplay.repast_pad.R;
import com.canplay.repast_pad.mvp.model.CONTURY;
import com.canplay.repast_pad.mvp.model.PROVINCE;

import java.util.List;


public class BloothAdapter extends BaseAdapter {
    private Context mContext;
    private List<CONTURY> list;
    public BloothAdapter(Context mContext) {

        this.mContext = mContext;
    }
    public interface ItemCliks{
        void getItem(int poistion, String name, int id);
    }
    public void setData(List<PROVINCE> list){
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
            view = LayoutInflater.from(mContext).inflate(R.layout.blooth_itme_view, parent, false);
            holder.name= (TextView) view.findViewById(R.id.tv_name);
            holder.tvDetail= (TextView) view.findViewById(R.id.tv_detail);
            holder.tvStatus= (TextView) view.findViewById(R.id.tv_status);
            view.setTag(holder);
        }else{
            holder = (ResultViewHolder) view.getTag();
        }
        return view;


    }

    public  class ResultViewHolder{

        TextView name;
        TextView tvDetail;
        TextView tvStatus;

    }
}
