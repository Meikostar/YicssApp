package com.canplay.medical.mvp.activity.home;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.canplay.medical.BuildConfig;
import com.canplay.medical.R;
import com.canplay.medical.base.BaseActivity;
import com.canplay.medical.base.BaseDailogManager;
import com.canplay.medical.mvp.activity.account.LoginActivity;
import com.canplay.medical.mvp.activity.mine.BindPhoneActivity;
import com.canplay.medical.mvp.activity.mine.EditorPwsActivity;
import com.canplay.medical.util.SpUtil;
import com.canplay.medical.util.StringUtil;
import com.canplay.medical.view.ClearEditText;
import com.canplay.medical.view.MarkaBaseDialog;
import com.canplay.medical.view.NavigationBar;
import com.canplay.medical.view.ProgressDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 添加血压测试值/添加血糖测试值
 */
public class AddBloodDataActivity extends BaseActivity {


    @BindView(R.id.line)
    View line;
    @BindView(R.id.navigationBar)
    NavigationBar navigationBar;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.ll_change_psw)
    LinearLayout llChangePsw;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.et_one)
    ClearEditText etOne;
    @BindView(R.id.et_two)
    ClearEditText etTwo;
    @BindView(R.id.et_three)
    ClearEditText etThree;
    @BindView(R.id.ll_type)
    LinearLayout llType;


    private int type;//0添加血压测试值   1 添加血糖测试值
    @Override
    public void initViews() {
        setContentView(R.layout.activity_add_data);
        ButterKnife.bind(this);
        type=getIntent().getIntExtra("type",0);
        navigationBar.setNavigationBarListener(this);

        if(type!=0){
            navigationBar.setNaviTitle("添加血糖测试值");
            llType.setVisibility(View.GONE);
        }

//        mWindowAddPhoto = new PhotoPopupWindow(this);
    }

    @Override
    public void bindEvents() {

       navigationBar.setNavigationBarListener(new NavigationBar.NavigationBarListener() {
           @Override
           public void navigationLeft() {
               finish();
           }

           @Override
           public void navigationRight() {
               finish();
           }

           @Override
           public void navigationimg() {
               finish();
           }
       });

    }


    @Override
    public void initData() {

    }


    private void processAppVersion(String version, final String url) {


        String newVersion = version;

        String oldVersion = StringUtil.getVersion(this);//"0.17"

        try {
            if (newVersion.compareTo(oldVersion) > 0) {
                View view = View.inflate(this, R.layout.base_dailog_view, null);
                TextView sure = (TextView) view.findViewById(R.id.txt_sure);
                TextView cancel = (TextView) view.findViewById(R.id.txt_cancel);
                TextView title = (TextView) view.findViewById(R.id.txt_title);
                title.setText(getString(R.string.check_versions));
                cancel.setText(R.string.yihougx);
                sure.setText(R.string.wozhidol);
                final MarkaBaseDialog dialog = BaseDailogManager.getInstance().getBuilder(this).setMessageView(view).create();
                dialog.show();
                sure.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new Thread(new DownloadApk(url)).start();
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            } else {
                View view = View.inflate(this, R.layout.base_dailog_view, null);
                TextView sure = (TextView) view.findViewById(R.id.txt_sure);
                TextView cancel = (TextView) view.findViewById(R.id.txt_cancel);
                TextView title = (TextView) view.findViewById(R.id.txt_title);
                title.setText(getString(R.string.check_version));
                cancel.setVisibility(View.GONE);
                sure.setText(R.string.wozhidol);
                final MarkaBaseDialog dialog = BaseDailogManager.getInstance().getBuilder(this).setMessageView(view).create();
                dialog.show();
                sure.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
//                float newV = Float.parseFloat(newVersion);
//
//                float oldV = Float.parseFloat(oldVersion);
//
//                if (newV > oldV) {
//                    hasNew=true;
//                }
//                else {
//                    hasNew=false;
//                }
        } catch (Exception e) {
//            if (!StringUtil.equals(newVersion, oldVersion)) {
//                hasNew=true;
//            }else {
//                hasNew=false;
//            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    public class DownloadApk implements Runnable {
        private ProgressDialog dialog;
        InputStream is;
        FileOutputStream fos;
        private Context context;

        public DownloadApk(String url) {
            this.url = url;
        }

        private String url;

        /**
         * 下载完成,提示用户安装
         */
        private void installApk(File file) {

            //调用系统安装程序
//            Intent intent = new Intent();
//            intent.setAction("android.intent.action.VIEW");
//            intent.addCategory("android.intent.category.DEFAULT");
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            Uri photoURI = FileProvider.getUriForFile(MainActivity.this, MainActivity.this.getApplicationContext().getPackageName() + ".provider", file);
//            intent.setDataAndType(photoURI, "application/vnd.android.package-archive");
//            MainActivity.this.startActivityForResult(intent, 0);
//
            Intent intent = new Intent(Intent.ACTION_VIEW);
            //判断是否是AndroidN以及更高的版本
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                Uri contentUri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", file);
                intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
            } else {
                intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            startActivityForResult(intent, 0);
        }

        @Override
        public void run() {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder().get().url(url).build();
            try {
                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {

                    //获取内容总长度
                    long contentLength = response.body().contentLength();
                    //设置最大值
                    //保存到sd卡
                    String apkName = url.substring(url.lastIndexOf("/") + 1, url.length());
                    File apkFile = new File(Environment.getExternalStorageDirectory(), apkName);
                    fos = new FileOutputStream(apkFile);
                    //获得输入流
                    is = response.body().byteStream();
                    //定义缓冲区大小
                    byte[] bys = new byte[1024];
                    int progress = 0;
                    int len = -1;
                    while ((len = is.read(bys)) != -1) {
                        try {
                            Thread.sleep(1);
                            fos.write(bys, 0, len);
                            fos.flush();
                            progress += len;
                            //设置进度

                        } catch (InterruptedException e) {
                            return;
                        }
                    }
                    //下载完成,提示用户安装
                    installApk(apkFile);
                }
            } catch (IOException e) {
                return;
            } finally {
                //关闭io流
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    is = null;
                }
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    fos = null;
                }
            }

        }
    }


}
