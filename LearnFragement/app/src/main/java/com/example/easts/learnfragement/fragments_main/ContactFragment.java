package com.example.easts.learnfragement.fragments_main;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.easts.learnfragement.R;

/**
 * Created by easts on 2017/9/7.
 */

public class ContactFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Context context = getActivity();
        return inflater.inflate(R.layout.contact_layout,container,false);
    }
}
