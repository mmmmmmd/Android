package com.example.easts.demo.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by easts on 2018/1/27.
 */

public class AchievementAdapter extends RecyclerView.Adapter<AchievementAdapter.AchievementViewHolder> {

    @Override
    public AchievementViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(AchievementViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class AchievementViewHolder extends RecyclerView.ViewHolder{

        public AchievementViewHolder(View itemView) {
            super(itemView);
        }
    }
}
