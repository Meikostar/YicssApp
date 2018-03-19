package com.canplay.medical.mvp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.canplay.medical.R;
import com.canplay.medical.base.BaseActivity;
import com.canplay.medical.bean.LOCATION;
import com.canplay.medical.location.LocationUtil;
import com.canplay.medical.mvp.adapter.LocationListAdapter;
import com.canplay.medical.util.TextUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class LocationBdActivity extends BaseActivity {


    @BindView(R.id.line)
    View line;
    @BindView(R.id.top_view_back)
    ImageView topViewBack;
    @BindView(R.id.mapView)
    MapView mapView;
    @BindView(R.id.list)
    ListView listview;
    private int count = 3;
    private BaiduMap mapViewMap;
    private boolean ifFrist = true;
    private String url;

    private int id;
    private int type;


    private String ids = "";
    private String w;
    private String j;
    private String address;
    private double jd = 0.0;
    private double wd = 0.0;
    private List<LOCATION> list = new ArrayList<>();
    private List<LOCATION> lists = new ArrayList<>();
    private PoiSearch mPoiSearch;
    private LatLng point = null;
    private int i = 1;
    private List<File> files = new ArrayList<>();
    private GeoCoder geoCoder;
    private LocationListAdapter adapter;
    private String address_detial;
    private String citys;
    private boolean isFirst;

    @Override
    public void initViews() {

        setContentView(R.layout.activity_location_mediacl);
        ButterKnife.bind(this);
        isFirst = true;
//
        geoCoder = GeoCoder.newInstance();
//        address = ShareDataManager.getInstance().getPara(this, ShareDataManager.ADDRESS_DETAIL);
        w = LocationUtil.latitude + "";
        j = LocationUtil.longitude + "";
        mapViewMap = mapView.getMap();
        address_detial = LocationUtil.address + "";
        citys = LocationUtil.city + "";
        adapter = new LocationListAdapter(this);
        listview.setAdapter(adapter);


    }

    @Override
    public void bindEvents() {
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (lists.size() > 0) {
                    lists.clear();
                }

                int i = 0;
                lists.add(list.get(position));
                for (LOCATION location : list) {
                    if (i != position) {
                        lists.add(location);
                    }
                    i++;
                }
                list.clear();
                list.addAll(lists);
                adapter.setData(list);
            }
        });
        topViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                if (list.size() > 0) {
                    intent.putExtra("name", list.get(0));
                }
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        mPoiSearch = PoiSearch.newInstance();
        OnGetPoiSearchResultListener poiListener = new OnGetPoiSearchResultListener() {
            @Override
            public void onGetPoiResult(PoiResult poiResult) {


                List<PoiInfo> allPoi = poiResult.getAllPoi();
                list.clear();
                if (allPoi != null && allPoi.size() > 0) {
                    wd = allPoi.get(0).location.latitude;
                    jd = allPoi.get(0).location.longitude;
                    LatLng ll = new LatLng(wd,
                            jd);
                    MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(ll);
                    // 移动到某经纬度
                    mapViewMap.animateMapStatus(update);
                    if (mapViewMap != null) {
                        mapViewMap.clear();
                    }
                    point = new LatLng(wd, jd);
                    BitmapDescriptor bitmap = BitmapDescriptorFactory
                            .fromResource(R.drawable.pin_red);
                    OverlayOptions option = new MarkerOptions()
                            .position(point)
                            .icon(bitmap);
                    mapViewMap.addOverlay(option);
                    for (PoiInfo info : allPoi) {
                        LOCATION location = new LOCATION();
                        location.setAddress(info.address);
                        location.setName(info.name);
                        location.setLatui(info.location.longitude);
                        location.setWeidu(info.location.latitude);
                        list.add(location);
                    }
                } else {
                    mPoiSearch.searchInCity((new PoiCitySearchOption())
                            .city(citys != null ? citys : LocationUtil.city)
                            .keyword(LocationUtil.city)
                            .pageNum(10).pageCapacity(0));
                }
                adapter.setData(list);

            }

            @Override
            public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

            }

            @Override
            public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

            }
        };
        mPoiSearch.searchInCity((new PoiCitySearchOption())
                .city(citys != null ? citys : LocationUtil.city)
                .keyword(address_detial)
                .pageNum(10).pageCapacity(0));

        mPoiSearch.setOnGetPoiSearchResultListener(poiListener);

        OnGetGeoCoderResultListener listener = new OnGetGeoCoderResultListener() {
            public void onGetGeoCodeResult(GeoCodeResult result) {
                if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
//                    geoCoder.geocode(new GeoCodeOption()
//                            .city(TextUtil.isNotEmpty(citys)?citys:LocationUtil.city)
//                            .address(TextUtil.isNotEmpty(citys)?citys:LocationUtil.city));
                    mPoiSearch.searchInCity((new PoiCitySearchOption())
                            .city(citys != null ? citys : LocationUtil.city)
                            .keyword(citys != null ? citys : LocationUtil.city)
                            .pageNum(10));
                    return;
                }
                //获取反向地理编码结果
                LatLng location = result.getLocation();
                geoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(location));

            }

            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
                List<PoiInfo> poiList = result.getPoiList();

                if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {

                    if (TextUtil.isNotEmpty(j)) {
                        jd = Double.parseDouble(j);
                        wd = Double.parseDouble(w);
                        point = new LatLng(wd, jd);
                    } else {
                        point = new LatLng(22.963175, 113.400244);
                    }

                    navigateTo();

//构建Marker图标
                    BitmapDescriptor bitmap = BitmapDescriptorFactory
                            .fromResource(R.drawable.moren);
//构建MarkerOption，用于在地图上添加Marker
                    OverlayOptions option = new MarkerOptions()
                            .position(point)
                            .icon(bitmap);
//在地图上添加Marker，并显示
                    mapViewMap.addOverlay(option);
                    return;
                }
                dimessProgress();
                //获取反向地理编码结果
                list.clear();

                if (poiList != null) {
                    wd = poiList.get(0).location.latitude;
                    jd = poiList.get(0).location.longitude;
                    LatLng ll = new LatLng(wd,
                            jd);
                    MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(ll);
                    // 移动到某经纬度
                    mapViewMap.animateMapStatus(update);
                    if (mapViewMap != null) {
                        mapViewMap.clear();
                    }
                    point = new LatLng(wd, jd);
                    BitmapDescriptor bitmap = BitmapDescriptorFactory
                            .fromResource(R.drawable.pin_red);
                    OverlayOptions option = new MarkerOptions()
                            .position(point)
                            .icon(bitmap);
                    mapViewMap.addOverlay(option);
                    for (PoiInfo info : poiList) {
                        LOCATION location = new LOCATION();
                        location.setAddress(info.address);
                        location.setName(info.name);
                        location.setLatui(info.location.longitude);
                        location.setWeidu(info.location.latitude);
                        list.add(location);
                    }
                }


                adapter.setData(list);

            }
        };
        geoCoder.setOnGetGeoCodeResultListener(listener);
//        geoCoder.geocode(new GeoCodeOption()
//                .city(citys)
//                .address(address_detial));
//        geoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(point));


        //单击地图的监听
        mapViewMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
                                             //地图单击事件回调方法
                                             @Override
                                             public void onMapClick(LatLng latLng) {
                                                 mapViewMap.clear();
                                                 point = new LatLng(latLng.latitude, latLng.longitude);

                                                 BitmapDescriptor bitmap = BitmapDescriptorFactory
                                                         .fromResource(R.drawable.pin_red);

                                                 OverlayOptions option = new MarkerOptions()
                                                         .position(point)
                                                         .icon(bitmap);

                                                 mapViewMap.addOverlay(option);
                                                 geoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(point));
                                             }

                                             //Poi 单击事件回调方法，比如点击到地图上面的商店，公交车站，地铁站等等公共场所
                                             @Override
                                             public boolean onMapPoiClick(MapPoi mapPoi) {
//                                                Log.e("TAG", "点击到地图上的POI物体了！名称：" + mapPoi.getName() + ",Uid:" + mapPoi.getUid());
                                                 return true;
                                             }
                                         }
        );

    }

    private void navigateTo() {
        // 按照经纬度确定地图位置


        if (ifFrist) {
            LatLng ll = new LatLng(wd,
                    jd);
            MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(ll);
            // 移动到某经纬度
            mapViewMap.animateMapStatus(update);
//            update = MapStatusUpdateFactory.zoomBy(5f);
//            // 放大
//            mapViewMap.animateMapStatus(update);

            ifFrist = false;
        }
        // 显示个人位置图标
        MyLocationData.Builder builder = new MyLocationData.Builder();
        builder.latitude(wd);
        builder.longitude(jd);
        MyLocationData data = builder.build();
        mapViewMap.setMyLocationData(data);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent();
        if (list.size() > 0) {
            intent.putExtra("name", list.get(0));
        }

        setResult(RESULT_OK, intent);
    }

    @Override
    public void initData() {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        geoCoder.destroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}