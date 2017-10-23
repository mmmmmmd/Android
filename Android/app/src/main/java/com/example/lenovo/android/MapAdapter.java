package com.example.lenovo.android;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by lenovo on 2017/8/22.
 */



public class MapAdapter extends RecyclerView.Adapter<MapAdapter.ViewHolder> {
    private Context mcontext;
    private List<Map> mMapList;
    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView map;
        public ViewHolder(View view){
            super(view);
            cardView=(CardView)view;
            map=(ImageView)view.findViewById(R.id.map_image);

        }
    }
    public MapAdapter(List<Map> mapList){
       mMapList=mapList;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mcontext==null){
            mcontext=parent.getContext();
        }
        View view= LayoutInflater.from(mcontext).inflate(R.layout.map_item,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Map map=mMapList.get(position);
        Glide.with(mcontext).load(map.getPath()).into(holder.map);

    }

    @Override
    public int getItemCount() {
        return mMapList.size();
    }
}

