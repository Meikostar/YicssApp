package com.canplay.repast_wear.bean;

import com.canplay.repast_wear.mvp.model.BaseType;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mykar on 17/4/26.
 */
public class COOK implements Serializable {
//  "menuId": 2,
//          "merchantId": 1,
//          "classifyId": 1,
//          "templetId": 3,
//          "cookbookInfo": [
//    {
//        "cookbookId": 1,
//            "resourceKey": "test.png",
//            "cnName": "秘制烤鸭",
//            "classifyId": 1,
//            "price": 60,
//            "merchantId": 1,
//            "state": 1,
//            "imgUrl": "http://ota4xg688.bkt.clouddn.com/test.png",
//            "foodClassifyInfos": [
    public String cbClassifyId;
    public String enName;

    public int state;
    public String sort;
    public int hasNext;
    public String price;
    public String classifyName;
    public String cnName;
    public String imgUrl;
    public String menuId;
    public String merchantId;
    public String resourceKey;
    public String classifyId;
    public String templateId;
    public String cookbookId;
    public String name;
    public List<COOK> cookbookInfos;
    public List<COOK> cookbookInfo;
    public List<BaseType> foodClassifyInfos;
    public List<BaseType> recipesClassifyInfos;

}
