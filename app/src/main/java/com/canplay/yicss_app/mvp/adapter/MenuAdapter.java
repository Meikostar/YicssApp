package com.canplay.yicss_app.mvp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.canplay.yicss_app.R;
import com.canplay.yicss_app.bean.MENU;
import com.canplay.yicss_app.util.TextUtil;

import java.util.List;


public class MenuAdapter extends BaseAdapter {
    private Context mContext;
    private List<MENU> list;

    public MenuAdapter(Context mContext) {

        this.mContext = mContext;
    }

    public interface ItemCliks{
        void getItem(MENU menu);
    }
    private ItemCliks listener;
    public void setClickListener(ItemCliks listener){
        this.listener=listener;
    }
    public void setData(List<MENU> list){
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
            view = LayoutInflater.from(mContext).inflate(R.layout.menu_item, parent, false);
            holder.name= (TextView) view.findViewById(R.id.tv_name);
            holder.tv_count= (TextView) view.findViewById(R.id.tv_count);
            holder.ll_item= (LinearLayout) view.findViewById(R.id.ll_item);
            view.setTag(holder);
        }else{
            holder = (ResultViewHolder) view.getTag();
        }
        if(TextUtil.isNotEmpty(list.get(position).classifyName)){
            holder.name.setText(list.get(position).classifyName);
        }
        if(TextUtil.isNotEmpty(list.get(position).sort)){
            holder.tv_count.setText(list.get(position).sort);
        }
//         if(position<10){
//             holder.tv_count.setText("0"+position+1);
//         }else {
//             holder.tv_count.setText(position+1);
//         }
         holder.ll_item.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 listener.getItem(list.get(position));
             }
         });
        return view;


    }

    public  class ResultViewHolder{

        TextView name;
        TextView tv_count;
        LinearLayout ll_item;

    }
}
