package com.example.yb.washcar;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {

    LocationManager locationManager;
    LocationListener locationListener;
    Location location;
    String contextService = Context.LOCATION_SERVICE;
    String provider;
    double lat;
    double lon;
    private Button bt_getlalo;
    private EditText ed_lo;
    private EditText ed_la;
    private Button bt_showCar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
//        getLoc();
        init();
    }

    private void init() {
        ed_lo = (EditText) findViewById(R.id.ed_lo);
        ed_la = (EditText) findViewById(R.id.ed_la);
        bt_getlalo = (Button) findViewById(R.id.bt_lola);
        bt_showCar = (Button) findViewById(R.id.bt_showCar);
        bt_getlalo.setOnClickListener(this);
        bt_showCar.setOnClickListener(this);
    }
    //    HashMap<String,String> map=new HashMap<String, String>();
//    String[] str = new String[2];

    //H.1在主线程中创建一个handler对象 ，这里是匿名内部类
    Handler handler = new Handler() {
        //H.2重新handleMessage方法
        @Override
        public void handleMessage(Message msg) {
            //H.5 接受子线程发来的 数据，处理数据
            Bundle b = msg.getData();
            Double lo = b.getDouble("lo");
            Double la = b.getDouble("la");
            //5显示到TextView
            ed_lo.setText(lo + "");
            ed_la.setText(la + "");
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_lola:
                getLoc();
                break;
            case R.id.bt_showCar:
                Intent i2 = new Intent(Main2Activity.this, ListCarActivity.class);
                startActivity(i2);
                break;
        }
    }

    private void getLoc() {
        // 位置
        locationManager = (LocationManager) getSystemService(contextService);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);// 高精度
        criteria.setAltitudeRequired(false);// 不要求海拔
        criteria.setBearingRequired(false);// 不要求方位
        criteria.setCostAllowed(true);// 允许有花费
        criteria.setPowerRequirement(Criteria.POWER_LOW);// 低功耗
        // 从可用的位置提供器中，匹配以上标准的最佳提供器
        provider = locationManager.getBestProvider(criteria, true);
        // 获得最后一次变化的位置
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        location = locationManager.getLastKnownLocation(provider);
        locationListener = new LocationListener() {
            public void onStatusChanged(String provider, int status,
                                        Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }

            public void onLocationChanged(Location location) {
                getLocation(location);
               /* lat = location.getLatitude();
                lon = location.getLongitude();
                Log.e("android_lat", String.valueOf(lat));
                Log.e("android_lon", String.valueOf(lon));*/
            }

        };
        // 监听位置变化，2秒一次，距离10米以上
        locationManager.requestLocationUpdates(provider, 2000, 10, locationListener);
    }

    private void getLocation(final Location location) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                double lo = location.getLongitude();
                double la = location.getLatitude();
                Message msg = new Message();
                Bundle b = new Bundle();
                b.putDouble("lo", lo);
                b.putDouble("la", la);
                msg.setData(b);
                handler.sendMessage(msg);
            }
        }).start();
    }


}
