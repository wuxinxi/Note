package com.szxb.tangren.myapplication.activity;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.compress.CompressConfig;
import com.szxb.tangren.myapplication.Adapter.ImageAdapter;
import com.szxb.tangren.myapplication.R;
import com.szxb.tangren.myapplication.utils.Control;
import com.szxb.tangren.myapplication.utils.PerformEdit;
import com.szxb.tangren.myapplication.utils.SildingFinishLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by TangRen on 2016/7/25.
 */
public class MoodActivity2 extends TakePhotoActivity {
    @InjectView(R.id.mtoolbar)
    Toolbar mtoolbar;
    @InjectView(R.id.title)
    EditText title;
    @InjectView(R.id.content)
    EditText content;
    @InjectView(R.id.postion_text)
    TextView postionText;
    RecyclerView gradView;
    @InjectView(R.id.camera)
    TextView camera;
    @InjectView(R.id.photo)
    TextView photo;
    @InjectView(R.id.postion)
    TextView postion;
    @InjectView(R.id.label)
    TextView label;
    @InjectView(R.id.layout)
    SildingFinishLayout layout;
    @InjectView(R.id.image)
    RecyclerView image;

    private PerformEdit per_title;

    private PerformEdit per_content;


    private Control control;

    private int flag = 0;

    private String labelString = "生活";

    private String imagePath;

    private long id;

    private String postionString = "未知地点";

    private Bitmap bitmap;

    private LocalBroadcastManager manager;

    private IntentFilter fileFilter;

    private BroadcastReceiver receiver;

    private LocationClient locationClient = null;

    private static final int UPDATE_TIME = 5000;

    private static int LOCATION_COUTNS = 0;

    private ImageAdapter mAdapter;

    private List<String>pathString=new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mood_main2);
        ButterKnife.inject(this);
        init();
        setFinish();

    }

    private void init() {
        mtoolbar.setTitle("心情日记");
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        GridLayoutManager layoutManager=new GridLayoutManager(this,3);
        image.setLayoutManager(layoutManager);
        mAdapter=new ImageAdapter(this,pathString);
        image.setAdapter(mAdapter);




//        Intent intent = getIntent();
//        flag = intent.getFlags();
//        if (flag == 1) {
//            id = intent.getLongExtra("id", 0);
//            title.setText(intent.getStringExtra("title"));
//            content.setText(intent.getStringExtra("content"));
//            postionText.setText(intent.getStringExtra("postion"));
//            imagePath = intent.getStringExtra("imagepath");
//
//            BitmapFactory.Options options = new BitmapFactory.Options();
//            options.inSampleSize = 1;
//            Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options);
//            if (bitmap != null) {
////                image.setImageBitmap(bitmap);
//            }
//
//
//        }

        layout.setOnSildingFinishListener(new SildingFinishLayout.OnSildingFinishListener() {
            @Override
            public void onSildingFinish() {
                finish();
                overridePendingTransition(0, R.anim.base_slide_right_out);
            }
        });

        layout.setTouchView(layout);

        control = new Control(this);
        per_title = new PerformEdit(title);
        per_content = new PerformEdit(content);
    }

    private void setFinish() {
        manager = LocalBroadcastManager.getInstance(MoodActivity2.this);
        fileFilter = new IntentFilter();
        fileFilter.addAction("exit_mood");
        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                finish();
                overridePendingTransition(0, R.anim.base_slide_right_out);
            }
        };
        manager.registerReceiver(receiver, fileFilter);

    }

    public void onclick(View view) {
        File file = new File(Environment.getExternalStorageDirectory(), "/tangren_images/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
        final Uri imageUri = Uri.fromFile(file);
        switch (view.getId()) {
            case R.id.camera:
                getTakePhoto().onEnableCompress(new CompressConfig.Builder()
                        .setMaxSize(50 * 1024)
                        .setMaxPixel(800).create(), true)
                        .onPicTakeOriginal(imageUri);
                break;
            case R.id.photo:
                getTakePhoto().onEnableCompress(new CompressConfig.Builder()
                        .setMaxSize(50 * 1024)
                        .setMaxPixel(800).create(), true)
                        .onPicSelectOriginal();
                break;
            case R.id.postion:
                getPostion();
                locationClient.start();
                locationClient.requestLocation();
                break;
            case R.id.label:
                choseItem();
                break;
            default:
                break;
        }
    }


    private void getPostion() {

        locationClient = new LocationClient(this);
        //设置定位条件
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);        //是否打开GPS
        option.setCoorType("bd09ll");       //设置返回值的坐标类型。
        option.setProdName("LocationDemo"); //设置产品线名称。强烈建议您使用自定义的产品线名称，方便我们以后为您提供更高效准确的定位服务。
        option.setScanSpan(UPDATE_TIME);    //设置定时定位的时间间隔。单位毫秒
        option.setIsNeedAddress(true);
        locationClient.setLocOption(option);


        //注册位置监听器
        locationClient.registerLocationListener(new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation location) {
                // TODO Auto-generated method stub
                if (location == null) {
                    return;
                }
                StringBuffer sb = new StringBuffer(256);
                sb.append("Time : ");
                sb.append(location.getTime());
                sb.append("\nError code : ");
                sb.append(location.getLocType());
                sb.append("\nLatitude : ");
                sb.append(location.getLatitude());
                sb.append("\nLontitude : ");
                sb.append(location.getLongitude());
                sb.append("\nRadius : ");
                sb.append(location.getRadius());
                if (location.getLocType() == BDLocation.TypeGpsLocation) {
                    sb.append("\nSpeed : ");
                    sb.append(location.getSpeed());
                    sb.append("\nSatellite : ");
                    sb.append(location.getSatelliteNumber());
                } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
                    sb.append("\nAddress : ");
                    sb.append(location.getAddrStr());
                }
                LOCATION_COUTNS++;
                sb.append("\n检查位置更新次数：");
                sb.append(String.valueOf(LOCATION_COUTNS));

                Log.d("sss", sb.toString());

                if (location.getAddress() == null) {
                    postionText.setText("未知位置");
                } else {
                    postionText.setText(location.getAddrStr());
                    postionString = location.getAddrStr();
                    if (locationClient != null && locationClient.isStarted()) {
                        locationClient.stop();
                        locationClient = null;

                    }
                }
//
            }

            public void onReceivePoi(BDLocation location) {

            }

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.psw_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                overridePendingTransition(0, R.anim.base_slide_right_out);
                break;
            case R.id.remoke:
                if (title.hasFocus()) per_title.undo();
                else if (content.hasFocus()) per_content.undo();
                break;
            case R.id.redo:
                if (title.hasFocus()) per_title.redo();
                else if (content.hasFocus()) per_content.redo();
                break;
            case R.id.save:
                if (flag == 0)
                    control.addMood(title, content, imagePath, labelString, postionString);
                else {
                    control.updatMood(id, title, content, labelString, postionString, imagePath);
//                    Toast.makeText(MoodActivity.this,title.getText().toString()+"\n"+content.getText().toString()+"\n"+imagePath,Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void takeCancel() {
        super.takeCancel();
    }

    @Override
    public void takeFail(String msg) {
        super.takeFail(msg);
        Snackbar.make(mtoolbar, "失败", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void takeSuccess(String imagePath) {
        this.imagePath = imagePath;
        super.takeSuccess(imagePath);
        pathString.addAll(pathString);
//        mAdapter.notifyDataSetChanged();
        Snackbar.make(mtoolbar, "添加成功", Snackbar.LENGTH_SHORT).show();
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inSampleSize = 1;
//        bitmap = BitmapFactory.decodeFile(imagePath, options);
//        image.setImageBitmap(bitmap);

//        ScannerByReceiver(MoodActivity2.this, imagePath);
    }

    private static void ScannerByReceiver(Context context, String path) {
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                Uri.parse("file://" + path)));
    }

    private void choseItem() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.ic_loyalty_white_24dp);
        builder.setTitle("请选择您的标签：");
        final String type[] = {"生活", "学习", "工作", "情感", "八卦", "娱乐", "其他"};
        builder.setItems(type, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                labelString = type[which];
            }
        });
        builder.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bitmap != null) {
            bitmap.recycle();
        }
        if (locationClient != null && locationClient.isStarted()) {
            locationClient.stop();
            locationClient = null;
        }
        manager.unregisterReceiver(receiver);
    }
}
