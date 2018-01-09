package com.canplay.repast_pad.bean;

import com.canplay.repast_pad.mvp.model.BaseType;

import java.util.List;

/**
 * Created by mykar on 17/4/26.
 */
public class COOK {
//       "cookbookId": 3,
//               "resourceKey": "1.png",
//               "cnName": "测试菜",
//               "enName": "test3",
//               "classifyId": 1,
//               "price": 34,
//               "state": 0,
//               "classifyName": "冷饮类",
//               "imgUrl": "http://ota4xg688.bkt.clouddn.com/1.png",
//               "foodClassifyInfos": [
//    {
//        "classifyId": 1,
//            "name": "土豆",
//            "state": 1
//    },
//    {
//        "classifyId": 2,
//            "name": "鸭血",
//            "state": 1
//    }
//        ],
//                "recipesClassifyInfos": [
//    {
//        "classifyId": 1,
//            "name": "原味",
//            "state": 1
//    },
    public String cbClassifyId;
    public String enName;
    public int state;
    public int hasNext;
    public String price;
    public String classifyName;
    public String cnName;
    public String imgUrl;
    public String merchantId;
    public String resourceKey;
    public String classifyId;
    public String cookbookId;
    public String name;
    public List<COOK> cookbookInfos;
    public List<BaseType> foodClassifyInfos;
    public List<BaseType> recipesClassifyInfos;

}
