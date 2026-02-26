package com.example.interiordesign;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class BedImageAdapter extends BaseAdapter {
    private Context mmContext;
    public int[] imageArray3={
            R.drawable.bed1, R.drawable.bed2, R.drawable.bed3, R.drawable.bed4, R.drawable.bed5, R.drawable.bed6, R.drawable.bed7,
            R.drawable.bed8, R.drawable.bed10,R.drawable.bed9,R.drawable.bed13,R.drawable.bed14,R.drawable.bed15,R.drawable.bed16,
            R.drawable.bed17,R.drawable.bed19

    };

    public BedImageAdapter(Context mmContext) {
        this.mmContext = mmContext;
    }

    @Override
    public int getCount() {
        return imageArray3.length;
    }

    @Override
    public Object getItem(int position) {
        return imageArray3[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView imageView=new ImageView(mmContext);
        imageView.setImageResource(imageArray3[position]);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(310,300));

        return imageView;
    }
}