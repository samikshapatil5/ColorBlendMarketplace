package com.example.interiordesign;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class KidImageAdapter extends BaseAdapter {
    private Context mmContext;
    public int[] imageArray2={
            R.drawable.kid1, R.drawable.kid2, R.drawable.kid3, R.drawable.kid4, R.drawable.kid5, R.drawable.kid6, R.drawable.kid7, R.drawable.kid8,
            R.drawable.kid10,R.drawable.kid9,R.drawable.kid13,R.drawable.kid14,R.drawable.kid15,R.drawable.kid16,R.drawable.kid17,R.drawable.kid10,R.drawable.kid19,R.drawable.kid20
            ,R.drawable.kid22,R.drawable.kid23,R.drawable.kid24,R.drawable.kid,R.drawable.kid11

    };

    public KidImageAdapter(Context mmContext) {
        this.mmContext = mmContext;
    }

    @Override
    public int getCount() {
        return imageArray2.length;
    }

    @Override
    public Object getItem(int position) {
        return imageArray2[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView imageView=new ImageView(mmContext);
        imageView.setImageResource(imageArray2[position]);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(310,300));

        return imageView;
    }
}
