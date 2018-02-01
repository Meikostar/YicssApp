package com.canplay.repast_wear.mvp.adapter;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.canplay.repast_wear.R;
import com.canplay.repast_wear.base.BaseApplication;
import com.canplay.repast_wear.bean.ORDER;
import com.canplay.repast_wear.bean.PrintBean;
import com.canplay.repast_wear.util.Pos;
import com.canplay.repast_wear.util.TextUtil;
import com.canplay.repast_wear.util.TimeUtil;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.UUID;

import static com.canplay.repast_wear.bean.PrintBean.PRINT_TYPE;

/**
 * 类说明:蓝牙设备的适配器
 * 阳（360621904@qq.com）  2017/4/27  19:58
 */
public class PrintAdapter extends BaseAdapter {
    private ArrayList<PrintBean> mBluetoothDevicesDatas;
    private Context mContext;
    //蓝牙适配器
    private BluetoothAdapter mBluetoothAdapter;
    //蓝牙socket对象
    private BluetoothSocket mmSocket;
    private UUID uuid;
    //打印的输出流
    private static OutputStream outputStream = null;
    //搜索弹窗提示
    ProgressDialog progressDialog = null;
    private final int exceptionCod = 100;
    private final int dismiss = 101;
    //打印的内容
    private ORDER order;
    private int statu;
    //在打印异常时更新ui
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == exceptionCod) {
                Toast.makeText(mContext, "打印发送失败，请稍后再试", Toast.LENGTH_SHORT).show();
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
            }else {
                if(statu==0){
                    if (progressDialog != null) {
                        progressDialog.dismiss();
                        Toast.makeText(mContext, "打印发送失败，请稍后再试", Toast.LENGTH_SHORT).show();

                    }
                }else {
                    statu=0;
                }

            }
        }
    };

    /**
     * @param context                上下文
     * @param mBluetoothDevicesDatas 设备列表
     * @param printContent           打印的内容
     */
    public PrintAdapter(Context context, ArrayList<PrintBean> mBluetoothDevicesDatas, ORDER printContent,int type) {
        this.mBluetoothDevicesDatas = mBluetoothDevicesDatas;
        mContext = context;
        this.type=type;
        mBluetoothAdapter = BaseApplication.mBluetoothAdapter;
        order = printContent;
        uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    }
    private int type;
    public void setOrder( ORDER printContent){
        order = printContent;
    }
    public int getCount() {
        return mBluetoothDevicesDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(R.layout.itme, null);
        View icon = convertView.findViewById(R.id.icon);
        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView address = (TextView) convertView.findViewById(R.id.address);
        TextView start = (TextView) convertView.findViewById(R.id.start);

        final PrintBean dataBean = mBluetoothDevicesDatas.get(position);
        icon.setBackgroundResource(dataBean.getTypeIcon());
        name.setText(dataBean.name);
        if(dataBean.isConnect){
            BaseApplication.maps.put(dataBean.address,dataBean);
        }
        address.setText(dataBean.isConnect ? "已配对" : "未连接");
        start.setText(dataBean.getDeviceType(start));

        //点击连接与打印
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                print(dataBean,position,order);
            }
        });

        return convertView;
    }
    public Context context;

    public void print(PrintBean dataBean,int position,ORDER order){
        if(order!=null){

            this.order=order;
            try {
                //如果已经连接并且是打印机
                if (dataBean.isConnect && dataBean.getType() == PRINT_TYPE) {
                    if (mBluetoothAdapter.isEnabled()) {
                        new ConnectThread(mBluetoothAdapter.getRemoteDevice(dataBean.address)).start();
                        progressDialog = ProgressDialog.show(context, "提示", "正在打印...", false);
                        Message msg=new Message();
                        msg.what=dismiss;
                        handler.sendMessageDelayed(msg,6000);
                    } else {
                        Toast.makeText(mContext, "蓝牙没有打开", Toast.LENGTH_SHORT).show();
                    }
                    //没有连接
                } else {
                    //是打印机
                    if (dataBean.getType() == PRINT_TYPE) {
                        setConnect(mBluetoothAdapter.getRemoteDevice(dataBean.address), position);
                        //不是打印机
                    } else {
                        Toast.makeText(mContext, "该设备不是打印机", Toast.LENGTH_SHORT).show();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            Toast.makeText(mContext, "没有打印内容", Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * 匹配设备
     *
     * @param device 设备
     */
    private void setConnect(BluetoothDevice device, int position) {
        try {
            Method createBondMethod = BluetoothDevice.class.getMethod("createBond");
            createBondMethod.invoke(device);
            mBluetoothDevicesDatas.get(position).setConnect(true);
            notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送数据
     */
    public void send(String sendData) {
        try {
            byte[] data = sendData.getBytes("gbk");
            outputStream.write(data, 0, data.length);
            outputStream.flush();
            outputStream.close();
            progressDialog.dismiss();
        } catch (IOException e) {
            e.printStackTrace();
            handler.sendEmptyMessage(exceptionCod); // 向Handler发送消息,更新UI

        }
    }
   public void setListener(PrintListener listener){
       this.listener=listener;
   }
   private PrintListener listener;
   public interface PrintListener{
       void printListener(int type);
   }

    /**
     * 连接为客户端
     */
    private class ConnectThread extends Thread {
        public ConnectThread(BluetoothDevice device) {
            try {
                mmSocket = device.createRfcommSocketToServiceRecord(uuid);
            } catch (IOException e) {
                if(progressDialog!=null){
                    progressDialog.dismiss();
                }
                e.printStackTrace();
            }
        }

        public void run() {
            //取消的发现,因为它将减缓连接
            mBluetoothAdapter.cancelDiscovery();
            try {
                //连接socket
                mmSocket.connect();
                //连接成功获取输出流
                outputStream = mmSocket.getOutputStream();

//                listener.printListener(0);
                if(type==1){
                    pos(mmSocket);
                }else {
                    pos2(mmSocket);

                }
//                pos3(mmSocket);

//                send(mPrintContent);
            } catch (Exception connectException) {
                Log.e("test", "连接失败");
                connectException.printStackTrace();
                //异常时发消息更新UI
                Message msg = new Message();
                msg.what = exceptionCod;
                // 向Handler发送消息,更新UI
//                handler.sendMessage(msg);
                if(progressDialog!=null){
                    progressDialog.dismiss();
                }
                try {
                    mmSocket.close();
                } catch (Exception closeException) {
                    closeException.printStackTrace();
                }
                return;
            }
        }
    }
    private Pos pos;
    private void pos(final BluetoothSocket socket) {
        // 开启一个子线程

                try {
                    //初始化打印机
                    pos = new Pos(socket.getOutputStream(),"GBK");
//                    if (!TextUtils.isEmpty(objBean.getHead_img())){
//                        pos.printLocation(1);
//                        head_bitmap = pos.compressPic(returnBitMap(objBean.getHead_img()));
//                        pos.draw2PxPoint(head_bitmap);
//                    }
                    pos.printLocation(1);
                    pos.bold(true);
                    pos.printTabSpace(2);
                    pos.printWordSpace(1);
                    pos.printTextNewLine(""+(order.businessName==null?"壹成善水":order.businessName));
                    pos.printLine(1);
                    pos.printLocation(0);

                    pos.bold(false);
                    pos.printTextNewLine("桌号："+order.tableNo);
                    pos.printTextNewLine("下单时间："+ TimeUtil.formatTims(order.createTime));
                    pos.printTextNewLine("订单编号："+order.detailNo);
//                    pos.printTextNewLine("门店编号："+"LXM");
                    pos.printLine(1);
                    pos.printTextNewLine("————————————————");
                    pos.printText("菜名       单价     数量   小计");

                    if(TextUtil.isNotEmpty(order.serviceCharge)){
                        pos.printLocation(20, 1);
                        pos.printTextNewLine("————————————————");

                        pos.printTextNewLine("就餐人数"+"    "+order.surcharge+"      "+order.num+"    "+order.serviceCharge);

                    }
                  int i=0;
                    for (ORDER order1:order.cookbookInfos) {

                            pos.printTextNewLine("- - - - - - - - - - - - - - - - ");


                        i++;
                        pos.printText(order1.cnName);
                        switch (order1.cnName.length()){
                            case  1:
                                pos.printWordSpace(8);
                                break;
                            case  2:
                                pos.printWordSpace(7);
                                break;
                            case  3:
                                pos.printWordSpace(6);
                                break;
                            case  4:
                                pos.printWordSpace(5);
                                break;
                            case  5:
                                pos.printWordSpace(4);
                                break;
                            case  6:
                                pos.printWordSpace(3);
                                break;
                            default:
                                pos.printWordSpace(2);
                                break;
                        }

                        pos.printText(order1.price+"");
                        switch ((order1.price+"").length()){
                            case  1:
                                pos.printWordSpace(6);
                                break;
                            case  2:
                                pos.printWordSpace(5);
                                break;
                            case  3:
                                pos.printWordSpace(5);
                                break;
                            case  4:
                                pos.printWordSpace(5);
                                break;
                            default:
                                pos.printWordSpace(3);
                                break;
                        }
                        pos.printText(""+order1.count);
                        switch ((order1.count+"").length()){
                            case  1:
                                pos.printWordSpace(4);
                                break;
                            case  2:
                                pos.printWordSpace(3);
                                break;
                            case  3:
                                pos.printWordSpace(3);
                                break;

                            default:
                                pos.printWordSpace(3);
                                break;
                        }
                        pos.printText(""+(order1.price*order1.count));

//                        if(i+1==order.cookbookInfos.size()){
//                            pos.printTextNewLine("- - - - - - - - - - - - - - - -");
//                        }
                       i++;
                    }

                    pos.printTextNewLine("———————————————");
                    pos.printLocation(0);
                    pos.printLine(1);
                    pos.printTextNewLine("                 总计："+order.detailPrice);
                    pos.printLine(1);
                    pos.printTextNewLine(" 备注："+order.remark);
                    pos.printLine(2);
                    //打印二维码  -- 如果提供了二维码的地址则用该方法
//                  pos.qrCode(objBean.getQr_code());

                    //打印二维码的图片 -- 如果提供了二维码的截图则用该方法
//                    if (!TextUtils.isEmpty(objBean.getQr_code())){
//                        pos.printLocation(1);
//                        code_bitmap = pos.compressPic(returnBitMap(objBean.getQr_code()));//returnBitmap方法和上面WiFi的一样
//                        pos.draw2PxPoint(code_bitmap);
//                    }
                    pos.printLine(3);
                    //切纸
                    pos.feedAndCut();
//                  pos.closeIOAndSocket();
                    pos = null;
                    progressDialog.dismiss();
                    statu=1;
                } catch (UnknownHostException e) {
                    Log.d("tag", "错误信息1：" + e.toString());
                    handler.sendEmptyMessage(exceptionCod); // 向Handler发送消息,更新UI

                } catch (IOException e) {
                    Log.d("tag", "错误信息2：" + e.toString());
                    handler.sendEmptyMessage(exceptionCod); // 向Handler发送消息,更新UI

                }
            }
    private void pos2(final BluetoothSocket socket) {
        // 开启一个子线程

        try {
            //初始化打印机
            pos = new Pos(socket.getOutputStream(),"GBK");
//                    if (!TextUtils.isEmpty(objBean.getHead_img())){
//                        pos.printLocation(1);
//                        head_bitmap = pos.compressPic(returnBitMap(objBean.getHead_img()));
//                        pos.draw2PxPoint(head_bitmap);
//                    }
            pos.printLocation(1);
            pos.bold(true);
            pos.printTabSpace(2);
            pos.printWordSpace(1);
            pos.printTextNewLine("    "+(order.businessName==null?"Meiko Or LXM":order.businessName));
            pos.printLine(2);
            pos.printLocation(0);

            pos.bold(false);

            pos.printTextNewLine("桌号："+order.tableNo);
            int a=0;
            for (ORDER order1:order.orderRelations) {
                pos.printTextNewLine("下单时间："+ TimeUtil.formatTims(order1.createTime));
                pos.printTextNewLine("订单编号："+ (TextUtil.isNotEmpty(order1.detailNo)?order1.detailNo:"Lxm"));
//                    pos.printTextNewLine("门店编号："+"LXM");
                pos.printLine(1);
                pos.printTextNewLine("———————————————");
                pos.printText("菜名       单价     数量    小计");

                pos.printTextNewLine("———————————————");
                pos.printLine(1);
                pos.printTextNewLine("就餐人数"+"  "+5.00+"     "+2+"   "+10.00);
                int i=0;
                for (ORDER order2:order1.detailInfoResps) {
                    pos.printTextNewLine("- - - - - - - - - - - - - - - - ");

                    pos.printText(order2.cnName);
                    switch (order2.cnName.length()){
                        case  1:
                            pos.printWordSpace(8);
                            break;
                        case  2:
                            pos.printWordSpace(7);
                            break;
                        case  3:
                            pos.printWordSpace(6);
                            break;
                        case  4:
                            pos.printWordSpace(5);
                            break;
                        case  5:
                            pos.printWordSpace(4);
                            break;
                        case  6:
                            pos.printWordSpace(3);
                            break;
                        default:
                            pos.printWordSpace(2);
                            break;
                    }

                    pos.printText(order2.price+"");
                    switch ((order2.price+"").length()){
                        case  1:
                            pos.printWordSpace(6);
                            break;
                        case  2:
                            pos.printWordSpace(5);
                            break;
                        case  3:
                            pos.printWordSpace(5);
                            break;
                        case  4:
                            pos.printWordSpace(5);
                            break;
                        default:
                            pos.printWordSpace(3);
                            break;
                    }
                    pos.printText(""+order2.count);
                    switch ((order2.count+"").length()){
                        case  1:
                            pos.printWordSpace(4);
                            break;
                        case  2:
                            pos.printWordSpace(3);
                            break;
                        case  3:
                            pos.printWordSpace(3);
                            break;

                        default:
                            pos.printWordSpace(3);
                            break;
                    }
                    pos.printText(""+(order2.price*order2.count));

                    i++;
                }

                pos.printTextNewLine("———————————————");
                pos.printLocation(0);
                pos.printLine(1);
                pos.printTextNewLine("                   总计："+order1.detailPrice);
                pos.printLine(1);
                pos.printTextNewLine(" 备注："+order1.remark);
                if(a+1==order.orderRelations.size()){
                    pos.printTextNewLine("—————————————————");
                    pos.printLine(1);
                }
                a++;
            }

            pos.printLine(2);
            //打印二维码  -- 如果提供了二维码的地址则用该方法
//                  pos.qrCode(objBean.getQr_code());

            //打印二维码的图片 -- 如果提供了二维码的截图则用该方法
//                    if (!TextUtils.isEmpty(objBean.getQr_code())){
//                        pos.printLocation(1);
//                        code_bitmap = pos.compressPic(returnBitMap(objBean.getQr_code()));//returnBitmap方法和上面WiFi的一样
//                        pos.draw2PxPoint(code_bitmap);
//                    }
            pos.printLine(3);
            //切纸
            pos.feedAndCut();
//                  pos.closeIOAndSocket();
            pos = null;
        } catch (UnknownHostException e) {
            Log.d("tag", "错误信息1：" + e.toString());
        } catch (IOException e) {
            Log.d("tag", "错误信息2：" + e.toString());
        }
    }
    private void pos3(final BluetoothSocket socket) {
        // 开启一个子线程

        try {
            //初始化打印机
            pos = new Pos(socket.getOutputStream(),"GBK");
//                    if (!TextUtils.isEmpty(objBean.getHead_img())){
//                        pos.printLocation(1);
//                        head_bitmap = pos.compressPic(returnBitMap(objBean.getHead_img()));
//                        pos.draw2PxPoint(head_bitmap);
//                    }
//            pos.printLocation(1);
//            pos.bold(true);
//            pos.printTabSpace(2);
//            pos.printWordSpace(1);
//            pos.printTextNewLine("   "+(order.businessName==null?"Meiko Or LXM":order.businessName));
//            pos.printLine(2);
//            pos.printLocation(0);
//
//            pos.bold(false);
//            pos.printTextNewLine("桌号："+9999);
//            int a=0;
//
//                pos.printTextNewLine("下单时间："+ "13.14 5.20");
//                pos.printTextNewLine("订单编号："+9991314);
////                    pos.printTextNewLine("门店编号："+"LXM");
//                pos.printLine(1);
//                pos.printTextNewLine("————————————————");
//                pos.printText("菜名       单价     数量    小计");
//                pos.printLocation(20, 1);
//                pos.printTextNewLine("————————————————");
//                pos.printLine(1);
//                pos.printTextNewLine("就餐人数"+"    "+5.00+"      "+2+"   "+10.00);
//
//                for (int i=0;i<3;i++) {
//
//                        pos.printTextNewLine("- - - - - - - - - - - - - - -");
//
//                    pos.printTextNewLine("LXM"+"    "+"no price"+"  "+"999"+"  "+"priceless");
//                    pos.printTextNewLine("Meiko"+"  "+"no price"+"  "+"999"+"  "+"priceless");
//                    pos.printLocation(20, 1);
//                    if(i+1==3){
//                        pos.printTextNewLine("- - - - - - - - - - - - - - -");
//                    }
//                    i++;
//                }
//
//                pos.printTextNewLine("———————————————");
//                pos.printLocation(0);
//                pos.printLine(1);
//                pos.printTextNewLine("              总计："+"priceless");
//                pos.printLine(1);
//                pos.printTextNewLine(" 备注："+"I am looking forward to see you again");


            //打印二维码  -- 如果提供了二维码的地址则用该方法
//                  pos.qrCode(objBean.getQr_code());

            //打印二维码的图片 -- 如果提供了二维码的截图则用该方法
//                    if (!TextUtils.isEmpty(objBean.getQr_code())){
//                        pos.printLocation(1);
//                        code_bitmap = pos.compressPic(returnBitMap(objBean.getQr_code()));//returnBitmap方法和上面WiFi的一样
//                        pos.draw2PxPoint(code_bitmap);
//                    }
            pos.printLine(3);


            pos.printLine(1);
            pos.setSizes();
            pos.bold(true);
            pos.printTextNewLine(" Descansa temprano");
            pos.printLine(1);
            pos.printTextNewLine(" Buenas noches");


            pos.printLine(1);
            pos.bold(false);

            pos.printLine(3);
            //切纸
            pos.feedAndCut();
//                  pos.closeIOAndSocket();
            pos = null;
        } catch (UnknownHostException e) {
            Log.d("tag", "错误信息1：" + e.toString());
        } catch (IOException e) {
            Log.d("tag", "错误信息2：" + e.toString());
        }
    }
}