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
import com.canplay.repast_pad.util.TextUtil;

import java.util.List;


public class PowTypeAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> list;
    public PowTypeAdapter(Context mContext) {

        this.mContext = mContext;
    }
    public interface ItemCliks{
        void getItem(int poistion, int id);
    }
    public void setData(List<String> list){
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
            view =View.inflate(mContext,R.layout.list_pop_itemview, null);
            holder.name= (TextView) view.findViewById(R.id.tv_name);

            view.setTag(holder);
        }else{
            holder = (ResultViewHolder) view.getTag();
        }
          if(TextUtil.isNotEmpty(list.get(position))){
              holder.name.setText(list.get(position));
          }
        return view;


    }

    public  class ResultViewHolder{

        TextView name;


    }
}
