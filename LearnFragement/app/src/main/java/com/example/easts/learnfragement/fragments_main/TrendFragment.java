package com.example.easts.learnfragement.fragments_main;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.easts.learnfragement.R;

/**
 * Created by easts on 2017/9/7.
 */

public class TrendFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.trend_layout,container,false);
    }
}
