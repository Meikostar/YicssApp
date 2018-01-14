package com.canplay.repast_pad.mvp.adapter.recycle;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.canplay.repast_pad.R;
import com.canplay.repast_pad.bean.ORDER;
import com.canplay.repast_pad.mvp.adapter.viewholder.OrderMangerViewHolder;
import com.canplay.repast_pad.util.TextUtil;
import com.canplay.repast_pad.util.TimeUtil;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by mykar on 17/4/12.
 */
public class OrderMangerRecycleAdapter extends BaseRecycleViewAdapter {

    private Context context;

    public OrderMangerRecycleAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_manger_itemview, null);

        return new OrderMangerViewHolder(view);
    }
//0待接单，1待结账 2已完成，4已撤销
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        OrderMangerViewHolder holders = (OrderMangerViewHolder) holder;
      ORDER data= (ORDER) datas.get(position);
        holders.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.clickListener(position,"");
            }
        });
          if(TextUtil.isNotEmpty(data.cookbookName)){
              holders.tvName.setText(data.cookbookName);
          } if(TextUtil.isNotEmpty(data.totalPrice)){
            holders.tvMoney.setText("￥ "+data.totalPrice);
        }
          if(data.createTime!=0){
              holders.tvTime.setText(TimeUtil.formatTimes(data.createTime));
          }
          if(position<9){
              holders.tv_number.setText("00"+(position+1));
          }else if(position>9&&position<99){
              holders.tv_number.setText("0"+(position+1));
          }else {
              holders.tv_number.setText(""+(position+1));
          }
        if(data.state==0){
            holders.tvMoney.setTextColor(context.getResources().getColor(R.color.yellows));
            holders.flBg.setBackgroundResource(R.drawable.yuan_y);
        }else if(data.state==1){
            holders.tvMoney.setTextColor(context.getResources().getColor(R.color.color9));
            holders.flBg.setBackgroundResource(R.drawable.yuan_b);
        }else if(data.state==2){
            holders.tvMoney.setTextColor(context.getResources().getColor(R.color.color9));
            holders.flBg.setBackgroundResource(R.drawable.yuan_g);
        }else if(data.state==4){
            holders.tvMoney.setTextColor(context.getResources().getColor(R.color.color9));
            holders.flBg.setBackgroundResource(R.drawable.yuan_f);
        }

    }

    @Override
    public int getItemCount() {
        int count = 0;

        if (datas != null && datas.size() > 0) {
            count = datas.size();
        }

        return count;
    }
    public static class OrderMangerViewHolder extends RecyclerView.ViewHolder  {

        @BindView(R.id.fl_bg)
        FrameLayout flBg;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_status)
        TextView tvStatus;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_money)
        TextView tvMoney;
        @BindView(R.id.tv_number)
        TextView tv_number;
        @BindView(R.id.card)
        CardView cardView;
        public OrderMangerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
    public void setClickListener(OnItemClickListener listener){
        this.listener=listener;
    }
    public OnItemClickListener listener;
    public interface  OnItemClickListener{
        void clickListener(int poiston,String id);
    }
}
