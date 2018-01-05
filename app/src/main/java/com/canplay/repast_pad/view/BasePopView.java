package com.canplay.repast_pad.view;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;

public abstract class BasePopView implements View.OnClickListener{
    	protected Activity activity;
	public View popView;
	public PopupWindow pop;
	protected LayoutInflater infalter;

	public BasePopView(Activity activity) {
		this.activity = activity;
		infalter = LayoutInflater.from(activity);
		popView = initPopView(infalter);
	}

	protected abstract View initPopView(LayoutInflater infalter);

	// 显示选择图片的popview
	public void showPopView() {
		pop = new PopupWindow(popView, LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		pop.setFocusable(true);
		pop.setOutsideTouchable(true);
		ColorDrawable dw = new ColorDrawable(0xb0000000);
		// 设置SelectPicPopupWindow弹出窗体的背景
		pop.setBackgroundDrawable(dw);
		pop.showAtLocation(activity.getWindow().getDecorView(), Gravity.RIGHT
				| Gravity.BOTTOM, 0, 0);
	}
	public void dismiss() {
		if (pop != null && pop.isShowing()) {
			pop.dismiss();
		}
	}
}