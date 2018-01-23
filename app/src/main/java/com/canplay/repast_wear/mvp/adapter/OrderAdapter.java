package com.canplay.repast_wear.mvp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.canplay.repast_wear.R;
import com.canplay.repast_wear.bean.ORDER;
import com.canplay.repast_wear.util.TextUtil;
import com.canplay.repast_wear.util.TimeUtil;

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
    public List<ORDER> getDatas(){
        return list;
    }
    private Map<Integer, Integer> map = new HashMap<>();
    private int type;
    private int state=-1;
    public void setData(List<ORDER> list,int type) {
        this.list = list;
        this.type = type;
        notifyDataSetChanged();
    }
    public void setState(int state) {
        this.state = state;
        notifyDataSetChanged();
    }
    public void setType(int type) {
        this.type = type;
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
        if(state!=-1){
            list.get(position).state=state;
        }
        if(type!=0){

            if (position == 0) {
                holder.ll_order.setVisibility(View.VISIBLE);
                holder.lines.setVisibility(View.VISIBLE);
                holder.liness.setVisibility(View.VISIBLE);

                if (list.size()==1) {
                    holder.llRemark.setVisibility(View.VISIBLE);
                    holder.llTotal.setVisibility(View.VISIBLE);

                }else {
                    if(list.get(0).status!=list.get(1).status){
                        holder.llRemark.setVisibility(View.VISIBLE);
                        holder.llTotal.setVisibility(View.VISIBLE);
                    }else {
                        holder.llRemark.setVisibility(View.GONE);
                        holder.llTotal.setVisibility(View.GONE);
                    }

                }
            } else {
                if (position+1 == list.size()) {
                    holder.llRemark.setVisibility(View.VISIBLE);
                    holder.llTotal.setVisibility(View.VISIBLE);
                } else {
                    if (list.get(position).status == list.get(position + 1).status) {
                        holder.llRemark.setVisibility(View.GONE);
                        holder.llTotal.setVisibility(View.GONE);
                    } else {
                        holder.llRemark.setVisibility(View.VISIBLE);

                        holder.llTotal.setVisibility(View.VISIBLE);
                    }
                }
                if (list.get(position).status == list.get(position - 1).status) {
                    holder.ll_order.setVisibility(View.GONE);
                    holder.lines.setVisibility(View.GONE);
                } else {
                    holder.ll_order.setVisibility(View.VISIBLE);
                    holder.lines.setVisibility(View.VISIBLE);
                    holder.liness.setVisibility(View.VISIBLE);

                }
            }
        }else {
            if(position==0){
                holder.lines.setVisibility(View.GONE);
                holder.liness.setVisibility(View.VISIBLE);
            }
            holder.tvCounts.setVisibility(View.GONE);
        }


        Glide.with(mContext).load(list.get(position).imgUrl).asBitmap().placeholder(R.drawable.moren).into(holder.ivImg);
        if (TextUtil.isNotEmpty(list.get(position).cnName)) {
            holder.tvName.setText(list.get(position).cnName);
        }
        holder.tvTime.setText(TimeUtil.formatTime(list.get(position).createTime));
        if (TextUtil.isNotEmpty(list.get(position).remark)) {
            holder.tvRemark.setText(list.get(position).remark);
        }
        if (TextUtil.isNotEmpty(list.get(position).detailNo)) {
            holder.tv_orderno.setText(list.get(position).detailNo);
        }
        if (TextUtil.isNotEmpty(list.get(position).detailPrice)) {
            holder.tvMoney.setText("￥"+list.get(position).detailPrice);
        }
        if (TextUtil.isNotEmpty(list.get(position).foodClassifyName)) {
            holder.tvDetail.setText(list.get(position).foodClassifyName + list.get(position).recipesClassifyName == null ? "" : "," + list.get(position).recipesClassifyName);
        } else {
            holder.tvDetail.setText(list.get(position).recipesClassifyName == null ? "" : "," + list.get(position).recipesClassifyName);

        }
        if (list.get(position).price!=0) {
            holder.tvPrice.setText("￥ " + list.get(position).price);
        }
        if (list.get(position).state == 0) {
            if(type!=0){
                holder.llEditor.setVisibility(View.GONE);
            }else {
                holder.llEditor.setVisibility(View.VISIBLE);
            }

        } else if (list.get(position).state == 1) {
            holder.llEditor.setVisibility(View.GONE);
            holder.tvCounts.setVisibility(View.VISIBLE);

        } else if (list.get(position).state == 2) {
            holder.llEditor.setVisibility(View.GONE);
            holder.tvCounts.setVisibility(View.VISIBLE);

        } else if (list.get(position).state == 3) {
            holder.tvCounts.setVisibility(View.VISIBLE);
            holder.llEditor.setVisibility(View.GONE);
        }
        if(type!=0){
            holder.tvCounts.setVisibility(View.VISIBLE);
        }
        holder.tvCount.setText(list.get(position).count + "");
        holder.tvCounts.setText("x"+list.get(position).count + "");

        holder.tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                list.get(position).count = list.get(position).count + 1;
                holder.tvCount.setText(list.get(position).count + "");

            }
        });
        holder.tvLess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (list.get(position).count == 0) {
                    list.get(position).count = 0;
                } else {
                    list.get(position).count = list.get(position).count - 1;
                    holder.tvCount.setText(list.get(position).count + "");
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

    public void setClickListener(ClickListener listener) {
        this.listener = listener;
    }

    private ClickListener listener;

    public interface ClickListener {
        void clickListener(int type, String id);
    }



  static   class ViewHolder {
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.iv_img)
        ImageView ivImg;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_detail)
        TextView tvDetail;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.tv_less)
        ImageView tvLess;
        @BindView(R.id.tv_count)
        TextView tvCount;
        @BindView(R.id.tv_counts)
        TextView tvCounts;
        @BindView(R.id.tv_add)
        ImageView tvAdd;
        @BindView(R.id.ll_editor)
        LinearLayout llEditor;
        @BindView(R.id.tv_remark)
        TextView tvRemark;
        @BindView(R.id.ll_remark)
        LinearLayout llRemark;
        @BindView(R.id.line)
        View line;
       @BindView(R.id.lines)
        View lines;
       @BindView(R.id.liness)
       View liness;
       @BindView(R.id.tv_orderno)
        TextView tv_orderno;
        @BindView(R.id.tv_money)
        TextView tvMoney;
        @BindView(R.id.ll_total)
        LinearLayout llTotal;
       @BindView(R.id.ll_order)
       LinearLayout ll_order;
        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
