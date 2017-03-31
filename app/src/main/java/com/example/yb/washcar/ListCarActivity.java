package com.example.yb.washcar;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.yb.washcar.Bean.WashCarsBean;
import com.example.yb.washcar.Utils.WashCarUtils;
import com.example.yb.washcar.adapter.CarsAdapter;

import java.util.ArrayList;

public class ListCarActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private Context mcontext;
    private ListView lv_WashCarsList;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            ArrayList<WashCarsBean> allcars = (ArrayList<WashCarsBean>) msg.obj;
            //3创建一个adapter给listview
            CarsAdapter carsAdapter = new CarsAdapter(mcontext, allcars);
            lv_WashCarsList.setAdapter(carsAdapter);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_car);
        mcontext=this;

        //找到控件
        lv_WashCarsList = (ListView) findViewById(R.id.lv_WashCarsList);
        //二：通过网络获取服务器端的新闻数据用listview封装，显示在listview.用子线程操作

                new Thread(new Runnable() {  //获取网络数据在子线程中进行
                    @Override
                    public void run() {

                try {
                    Thread.sleep(4 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ArrayList<WashCarsBean> allcars = WashCarUtils.getAllNewsFromNetwork(mcontext);

                System.out.println("---》》》》》》》》" + allcars.size());
                //创建message，发送到主线程更新UI
                Message msg = Message.obtain();
                msg.obj = allcars;
                handler.sendMessage(msg);
            }
        }).start();

        //4 设置listview条目的 点击事件
        lv_WashCarsList.setOnItemClickListener(this);
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //需要获取条目上bean对象中url做跳转
        WashCarsBean bean = (WashCarsBean) parent.getItemAtPosition(position);
        String url = bean.pic_url;
        //跳转浏览器
        Intent i = new Intent();
        i.setAction(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
}
