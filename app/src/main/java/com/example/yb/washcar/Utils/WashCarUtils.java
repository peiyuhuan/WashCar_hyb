package com.example.yb.washcar.Utils;

import android.content.Context;

import com.example.yb.washcar.Bean.WashCarsBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by yb on 2017/3/10.
 */
public class WashCarUtils {
    /**
     * 从网络获取数据,和从本地获取数据
     * @param newslist
     * @return
     */
    public  static  String newPath_url="http://localhost:8082/work/test6.php?lo=121.443948&la=31.288837&nn=4";

    //从网络请求数据 并封装新闻数据到list中返回
    public static ArrayList<WashCarsBean> getAllNewsFromNetwork(Context context) {

        ArrayList<WashCarsBean> arraylist=new ArrayList<WashCarsBean>();
        try {
            //1获取服务器数据
            URL url=new URL(newPath_url);
            HttpURLConnection conn= (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setConnectTimeout(10*1000);
            //响应码
            int code=conn.getResponseCode();
            if(code==200){
                InputStream inputStream = conn.getInputStream();
                String result = StreamUtil.streamToString(inputStream);
                System.out.println("获取数据ok:::"+result);

                //2解析获取的数据保存到list集合中

                JSONObject root_json = new JSONObject(result);  //将一个字符串封装成jsOn
                JSONArray jsonarray=root_json.getJSONArray("user"); //获取root_json中的newss作为jsonArray对象
                for (int i = 0; i < jsonarray.length(); i++) {
                    JSONObject cars_json = jsonarray.getJSONObject(i);  //一条的json

                    WashCarsBean washCarBean = new WashCarsBean();
                    washCarBean.id=cars_json.getInt("id");
                    washCarBean.distance=cars_json.getDouble("distance");
                    washCarBean.name=cars_json.getString("name");
                    washCarBean.address=cars_json.getString("address");
                    washCarBean.pic_url=cars_json.getString("pic_url");
                    arraylist.add(washCarBean);
                }
                //3请删除数据库酒的数据，将数据缓存到数据库，
                //new NewsDaoUtils(context).delete();//清除旧数据

                //new NewsDaoUtils(context).saveNews(arraylist);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arraylist;

    }
}
