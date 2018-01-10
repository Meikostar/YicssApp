package com.canplay.repast_pad.mvp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.canplay.repast_pad.R;
import com.canplay.repast_pad.mvp.model.BaseType;
import com.canplay.repast_pad.mvp.model.PROVINCE;
import com.canplay.repast_pad.util.TextUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CountAdapter extends BaseAdapter {
    private Context mContext;
    private List<BaseType> list;

    public CountAdapter(Context mContext) {

        this.mContext = mContext;
    }
    public List<BaseType> getDatas(){
        return list;
    }
    public interface ItemCliks {
        void getItem(BaseType baseType, int poistioin);
    }
    private ItemCliks listener;
    public void setClickListener(ItemCliks listener){
        this.listener=listener;
    }
    public void setData(List<BaseType> list) {
        this.list=list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list != null ? list.size() : 0;
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
        ViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.menucout_item, parent, false);
            holder = new ViewHolder(view);
            holder.tvName = (TextView) view.findViewById(R.id.tv_name);
            holder.tvCount = (TextView) view.findViewById(R.id.tv_count);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
            if(TextUtil.isNotEmpty(list.get(position).name)){
                holder.tvName.setTextColor(mContext.getResources().getColor(R.color.slow_black));
                holder.tvName.setText(list.get(position).name);
            }else {
                holder.tvName.setTextColor(mContext.getResources().getColor(R.color.color9));
                holder.tvName.setText("请选择菜品");
            }
            holder.tvCount.setText("添加菜品"+"0" +( position+1));
        holder.llStyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.getItem(list.get(position),position);
            }
        });
        return view;


    }



    static class ViewHolder {
        @BindView(R.id.tv_count)
        TextView tvCount;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.ll_style)
        LinearLayout llStyle;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
