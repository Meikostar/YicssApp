package com.canplay.repast_pad.base;

/***
 * 功能描述:RxBus消息订阅
 * 作者:chenwei
 * 时间:2016/12/15
 * 版本:1.0
 ***/

public class SubscriptionBean {
    public static int TIME=111;
    public static int CHOOSE=112;
    public static int FINISH=211;
    public static int ADD_PEICAI=113;
    public static int ADD_FEILEI=114;
    public static int ADD_MENU=115;
    public static int MENU_REFASH=115;


    /**
     * 重新登入
     */

    public static <T>RxBusSendBean createSendBean(int type,T t){
        RxBusSendBean<T> busSendBean = new RxBusSendBean();
        busSendBean.type = type;
        busSendBean.content = t;
        return busSendBean;
    }

    public static class RxBusSendBean<T>{
        public int type;
        public T content;
    }

}
