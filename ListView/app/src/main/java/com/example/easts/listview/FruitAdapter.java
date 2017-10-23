package com.example.easts.listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;

/**
 * Created by easts on 2017/10/19.
 */

public class FruitAdapter extends ArrayAdapter<Fruit> {

    private int resourceId;

    public View getView(int position, View convertView, ViewGroup parent) {
        Fruit fruit = getItem(position);
        View view;
        ViewHolder viewHolder = new ViewHolder();
        if(convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            viewHolder.imageView = (ImageView) view.findViewById(R.id.image);
            viewHolder.textView = (TextView)view.findViewById(R.id.name);
            view.setTag(viewHolder);
        }else{
            view = convertView;
            view.getTag();
        }
        viewHolder.imageView.setImageResource(fruit.getResourceId());
        viewHolder.textView.setText(fruit.getName());
        return view;
    }

    public FruitAdapter(Context context, int textViewResourceId, List<Fruit> objects) {
        super(context, textViewResourceId, objects);
        this.resourceId = textViewResourceId;
    }

    class ViewHolder{
        ImageView imageView;
        TextView textView;
    }
}
