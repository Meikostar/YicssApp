package com.canplay.repast_wear.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mykar on 17/4/26.
 */
public class ORDER implements Serializable{
// "orderId": 2,
//         "orderNo": "1062159523076112385",
//         "tableNo": "测试桌号",
//         "state": 0,
//         "createTime": 1515513764287,
//         "totalPrice": 660,
//         "orderRelations": [
//    {
//        "detailNo": "1062159523076112386",
//            "createTime": 1515513764344,
//            "state": 0,
//            "detailInfoResps": [
//        {
//            "detailId": 1,
//                "cookbookId": 1,
//                "imgUrl": "http://oud4e96lb.bkt.clouddn.com/test.png",
//                "cnName": "秘制烤鸭",
//                "price": 60,
//                "foodClassifyName": "土豆",
//                "recipesClassifyName": "原味",
//                "desc": " 土豆   原味",
//                "count": 1
//        },
//        {
//        }
//                ]
//    },
//    {
//        "detailNo": "1062159957555675138",
//            "createTime": 1515513867859,
//            "state": 0,
//            "detailInfoResps": [
//        {
//
//        },
//        {
//            "detailId": 4,
//                "cookbookId": 3,
//                "imgUrl": "http://oud4e96lb.bkt.clouddn.com/1.png",
//                "cnName": "测试菜",
//                "enName": "test3",
//                "price": 34,
//                "recipesClassifyName": "中辣",
//                "desc": " 中辣",
//                "count": 4
//        }
//                ]	"hasNext": 0,
//		"list": [{
//        "tableNo": "测试桌号",
//                "cookbookName": "测试菜、烧鸡",
//                "state": 0,
//                "createTime": 1515684532066,
//                "totalPrice": 72.0,
//                "detailNo": "1062875775121752066"
//    }
//        ]
//}
    public String orderNo;
    public String tableNo;
    public int state;
    public int count;
    public int hasNext;
    public int status;

    public long createTime;
    public String businessName;
    public String totalPrice;
    public String serviceCharge;
    public String detailPrice;
    public String remark;
    public String cookbookName;
    public String detailNo;
    public String detailId;
    public String cookbookId;
    public String imgUrl;
    public String cnName;
    public double price;
    public String foodClassifyName;
    public String recipesClassifyName;
    public String desc;
    public List<ORDER> orderRelations;
    public List<ORDER> detailInfoResps;
    public List<ORDER> cookbookInfos;
    public List<ORDER> list;





}
