package com.canplay.medical.mvp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.canplay.medical.R;
import com.canplay.medical.bean.LOCATION;

import java.util.List;



/**
 * Created by mykar on 161/4/13.
 */

public class LocationListAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<LOCATION> list;
    private int type=0;//0 表示默认使用list数据


    public void setType(int type){
        this.type=type;
        notifyDataSetChanged();
    }
    public LocationListAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }
   public void setData( List<LOCATION> list){
          this.list = list;
          notifyDataSetChanged();
   }
    @Override
    public int getCount() {
        return type==0?(list!=null?list.size():0):8;
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.item_location_list, null);
            holder.txt_name = (TextView) view.findViewById(R.id.txt_name);
            holder.txt_detail = (TextView) view.findViewById(R.id.txt_detail);
            holder.img_icon = (ImageView) view.findViewById(R.id.img);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        if(i==0){
        holder.txt_name.setText(list.get(i).getName());
        holder.txt_detail.setText(list.get(i).getAddress());
            holder.img_icon.setVisibility(View.VISIBLE);
        }else {
            holder.txt_name.setText(list.get(i).getName());
            holder.txt_detail.setText(list.get(i).getAddress());
            holder.img_icon.setVisibility(View.GONE);
        }
        
       // PROFILE_ITEM item = list.get(i);

        return view;
    }

    class ViewHolder {
        TextView txt_name;
        TextView txt_detail;
        ImageView img_icon;
    }
}
