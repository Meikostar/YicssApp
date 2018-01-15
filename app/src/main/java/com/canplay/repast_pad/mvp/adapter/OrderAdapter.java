package com.canplay.repast_pad.mvp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.canplay.repast_pad.R;
import com.canplay.repast_pad.bean.ORDER;
import com.canplay.repast_pad.mvp.model.CONTURY;
import com.canplay.repast_pad.mvp.model.PROVINCE;
import com.canplay.repast_pad.util.TextUtil;
import com.canplay.repast_pad.util.TimeUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


public class OrderAdapter extends BaseAdapter {
    private Context mContext;
    private List<ORDER> list;

    public OrderAdapter(Context mContext) {

        this.mContext = mContext;
    }

    public interface ItemCliks {
        void getItem(int poistion, String name, int id);
    }
    private Map<Integer,Integer> map=new HashMap<>();
    public void setData(List<ORDER> list) {
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
        final ViewHolder holder;
        if (view == null) {

            view = LayoutInflater.from(mContext).inflate(R.layout.order_detail_item, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        if(position==0){
            holder.tv_time.setVisibility(View.VISIBLE);
            holder.tv_time.setText(TimeUtil.formatTime(list.get(position).createTime));
            if(position==list.size()){
             holder.ll_remark.setVisibility(View.VISIBLE);
            }else {
                if(list.get(position).status==list.get(position+1).status){
                    holder.ll_remark.setVisibility(View.GONE);
                    holder.ll_total.setVisibility(View.GONE);
                }else {
                    holder.ll_total.setVisibility(View.VISIBLE);
                    holder.ll_remark.setVisibility(View.VISIBLE);
                }
            }
        }else {
            if(position==list.size()){
                holder.ll_remark.setVisibility(View.VISIBLE);
                holder.ll_total.setVisibility(View.VISIBLE);
            }else {
                if(list.get(position).status==list.get(position+1).status){
                    holder.ll_remark.setVisibility(View.GONE);
                    holder.ll_total.setVisibility(View.GONE);
                }else {
                    holder.ll_remark.setVisibility(View.VISIBLE);
                    holder.ll_total.setVisibility(View.VISIBLE);
                }
            }
            if(list.get(position).status==list.get(position-1).status){
                holder.tv_time.setVisibility(View.GONE);
            }else {
                holder.tv_time.setVisibility(View.VISIBLE);
            }
        }
        Glide.with(mContext).load(list.get(position).imgUrl).asBitmap().placeholder(R.drawable.moren).into(holder.img);
        if(TextUtil.isNotEmpty(list.get(position).cnName)){
            holder.tvName.setText(list.get(position).cnName);
        }
        if(TextUtil.isNotEmpty(list.get(position).foodClassifyName)){
            holder.tvDetail.setText(list.get(position).foodClassifyName+list.get(position).recipesClassifyName==null?"":","+list.get(position).recipesClassifyName);
        }else {
            holder.tvDetail.setText(list.get(position).recipesClassifyName==null?"":","+list.get(position).recipesClassifyName);

        }
        if(TextUtil.isNotEmpty(list.get(position).price)){
            holder.tvPrice.setText("￥ "+list.get(position).price);
        }

        if(list.get(position).state==0){
            holder.llEditor.setVisibility(View.VISIBLE);
        }else if(list.get(position).state==1){
            holder.llEditor.setVisibility(View.GONE);
        }else if(list.get(position).state==2){
            holder.llEditor.setVisibility(View.GONE);
        }else if(list.get(position).state==3){
            holder.llEditor.setVisibility(View.GONE);
        }
        holder.tvCount.setText(list.get(position).count+"");

        holder.tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                list.get(position).count=list.get(position).count+1;
                holder.tvCount.setText(list.get(position).count+"");

            }
        });
        holder.tvLess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(list.get(position).count==0){
                    list.get(position).count=0;

                }else {
                    list.get(position).count=list.get(position).count-1;
                    holder.tvCount.setText(list.get(position).count-1+"");
                }

            }
        });
        return view;


    }
//0待接单，1待结账 2已完成，4已撤销
    public class ResultViewHolder {

        TextView name;
        TextView tv_count;

    }
    public void setClickListener(ClickListener listener){
        this.listener=listener;
    }
    private ClickListener listener;
    public interface ClickListener{
        void clickListener(int type,String id);
    }
    static class ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_remark)
        TextView tv_remark;
        @BindView(R.id.tv_time)
        TextView tv_time;
        @BindView(R.id.tv_detail)
        TextView tvDetail;
        @BindView(R.id.iv_img)
        ImageView img;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.tv_less)
        TextView tvLess;
        @BindView(R.id.tv_count)
        TextView tvCount;
        @BindView(R.id.tv_add)
        TextView tvAdd;
        @BindView(R.id.ll_editor)
        LinearLayout llEditor;
        @BindView(R.id.ll_remark)
        LinearLayout ll_remark;
        @BindView(R.id.ll_total)
        LinearLayout ll_total;
        @BindView(R.id.line)
        View line;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
