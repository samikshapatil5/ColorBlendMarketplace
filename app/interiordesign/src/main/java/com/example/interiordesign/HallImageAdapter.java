package com.example.interiordesign;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class HallImageAdapter extends BaseAdapter {
    private Context mmContext;
    public int[] imageArray1={
            R.drawable.hall1, R.drawable.hall2, R.drawable.hall3, R.drawable.hall4, R.drawable.hall5, R.drawable.hall6, R.drawable.hall7, R.drawable.hall8,
            R.drawable.hall10,R.drawable.hall9,R.drawable.hall13,R.drawable.hall14,R.drawable.hall15,R.drawable.hall16,R.drawable.hall17,R.drawable.hall18,R.drawable.hall19,R.drawable.hall20
            ,R.drawable.hall21,R.drawable.hall22,R.drawable.hall23,R.drawable.hall11

    };

    public HallImageAdapter(Context mmContext) {
        this.mmContext = mmContext;
    }

    @Override
    public int getCount() {
        return imageArray1.length;
    }

    @Override
    public Object getItem(int position) {
        return imageArray1[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView imageView=new ImageView(mmContext);
        imageView.setImageResource(imageArray1[position]);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(310,300));

        return imageView;
    }
}
