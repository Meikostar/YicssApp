package com.canplay.repast_pad.view;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.canplay.repast_pad.R;
import com.canplay.repast_pad.mvp.adapter.PowTypeAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by syj on 2016/11/23.
 */
public class PopView_NavigationBars extends BasePopView {
    @BindView(R.id.list_view)
    ListView listView;


   public Activity activity;
    public PopView_NavigationBars(Activity activity) {
        super(activity);
        this.activity=activity;
    }

    public ItemCliskListeners listeners;

    @Override
    public void onClick(View view) {

    }

    public interface ItemCliskListeners {
        void clickListener(int poistion);
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
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                listeners.clickListener(i);
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

   public void showData(List<String> list){
       if(list!=null&&list.size()>0){
           adapter.setData(list);
       }
   }
}
