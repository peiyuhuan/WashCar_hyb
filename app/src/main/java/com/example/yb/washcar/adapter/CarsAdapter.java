package com.example.yb.washcar.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.yb.washcar.Bean.WashCarsBean;
import com.example.yb.washcar.R;
import com.loopj.android.image.SmartImageView;

import java.util.ArrayList;

/**
 * Created by yb on 2016/12/17.
 */
public class CarsAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<WashCarsBean> list;

    public CarsAdapter(Context context, ArrayList<WashCarsBean> list) {
        this.list = list;
        this.context = context;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //1复用convertView
        View view = null;
        if (convertView != null) {
            view = convertView;
        } else {
            view = View.inflate(context, R.layout.item_lv_layout, null);
        }
        TextView item_tv_name = (TextView) view.findViewById(R.id.item_tv_name);
        TextView item_tv_address = (TextView) view.findViewById(R.id.item_tv_address);
        TextView item_tv_distance = (TextView) view.findViewById(R.id.item_tv_distance);
        SmartImageView item_img_icon = (SmartImageView) view.findViewById(R.id.item_img_icon);
        WashCarsBean washCarBean = list.get(position);
        item_tv_name.setText(washCarBean.name);
        item_tv_address.setText(washCarBean.address);
        item_tv_distance.setText(washCarBean.distance + "");
        item_img_icon.setImageUrl(washCarBean.pic_url);
        return view;
}
}
