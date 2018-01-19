package com.canplay.repast_wear.view;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.canplay.repast_wear.R;
import com.canplay.repast_wear.mvp.adapter.PowTypeAdapter;
import com.canplay.repast_wear.mvp.model.BaseType;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by syj on 2016/11/23.
 */
public class PopView_NavigationBars extends BasePopView {
    @BindView(R.id.list_view)
    ListView listView;


   public Context activity;
    public PopView_NavigationBars(Activity activity,int type) {
        super(activity);
        this.type=type;
        this.activity=activity;
    }

    public ItemCliskListeners listeners;

    @Override
    public void onClick(View view) {

    }

    public interface ItemCliskListeners {
        void clickListener(String id);
    }

    public void setClickListener(ItemCliskListeners listener) {
        listeners = listener;
    }
     private PowTypeAdapter adapter;
    @Override
    protected View initPopView(LayoutInflater infalter) {
        View popView = infalter.inflate(R.layout.popview_navigationbars, null);
        ButterKnife.bind(this, popView);
        adapter=new PowTypeAdapter(activity);
        listView.setAdapter(adapter);

        adapter.setClickListener(new PowTypeAdapter.ItemCliks() {
            @Override
            public void getItem(int poistion, String id) {
                listeners.clickListener(id);
            }
        });
        popView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return popView;
    }

   public void showData(List<BaseType> list){
       if(list!=null&&list.size()>0){
           adapter.setData(list);
       }
   }

    public void setView(View view){
        line=view;
    }
}
