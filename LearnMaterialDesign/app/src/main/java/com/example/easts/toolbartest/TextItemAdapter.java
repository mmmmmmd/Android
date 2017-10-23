package com.example.easts.toolbartest;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.List;

/**
 * Created by easts on 2017/9/29.
 */

public class TextItemAdapter extends RecyclerView.Adapter<TextItemAdapter.ViewHolder> {

    private List<String> textViewList;

    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView itemText;

        public ViewHolder(View itemView) {
            super(itemView);
            itemText = (TextView)itemView.findViewById(R.id.item);
        }
    }

    public TextItemAdapter(List<String> textViewList){
        this.textViewList = textViewList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.text_item,parent,false));
    }

    @Override
    public void onBindViewHolder(TextItemAdapter.ViewHolder holder, int position) {
        String str = textViewList.get(position);
        holder.itemText.setText(str);
    }


    @Override
    public int getItemCount() {
        return textViewList.size();
    }
}