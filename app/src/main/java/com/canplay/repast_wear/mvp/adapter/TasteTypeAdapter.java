package com.canplay.repast_wear.mvp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.canplay.repast_wear.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TasteTypeAdapter extends BaseAdapter {

    private List<String> tableList;
    private Context context;
    private LayoutInflater layoutInflater;
    private setCloseClickListener colseClikeListener;
    private OnItemViewClickListener itemViewClickListener;


    public TasteTypeAdapter(Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        initDate();
    }

    public void setColseListener(setCloseClickListener colseClikeListener) {
        this.colseClikeListener = colseClikeListener;
    }
    public void setOnItemListener(OnItemViewClickListener itemViewClickListener) {
        this.itemViewClickListener = itemViewClickListener;
    }

    private void initDate() {
        tableList=new ArrayList<>();
        tableList.add("沙拉类");
        tableList.add("冷切类");
        tableList.add("热菜类");
        tableList.add("甜品类");
        tableList.add("汤类");
        tableList.add("饮品");
        tableList.add("调味酱");
    }

    @Override
    public int getCount() {
        return tableList.size();
    }

    @Override
    public String getItem(int position) {
        return tableList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_taste_type, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvName.setText(tableList.get(position));
        if (position == tableList.size()) {
            holder.contain.setVisibility(View.VISIBLE);
        }
        holder.contain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colseClikeListener.imageClike();
            }
        });
        holder.tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemViewClickListener.ItemClick(tableList.get(position));
            }
        });
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.taste_type_name)
        TextView tvName;
        @BindView(R.id.close_taste_list)
        ImageView contain;
        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public interface setCloseClickListener {
        void imageClike();
    }
    public interface OnItemViewClickListener {
        void ItemClick(String type);
    }
}
