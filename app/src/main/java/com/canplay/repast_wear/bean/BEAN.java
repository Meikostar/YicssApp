package com.canplay.repast_wear.bean;

/**
 * Created by mykar on 17/4/26.
 */
public class BEAN {
//    "id": "535",
//            "name": "爱尔巴桑",
//            "parent_id": "534",
//            "type": "city"

    public String detailId;
    public String menuId;
    public int count;

    public String getDetaiId() {
        return detailId;
    }

    public void setDetaiId(String detaiId) {
        this.detailId = detaiId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        String id=detailId+"";
        String cous=count+"";
        return "{" +
                "detaiId"+ ":"+ id + '\'' + ","+
                " count" + ":"+ cous +
                '}';
    }
}
