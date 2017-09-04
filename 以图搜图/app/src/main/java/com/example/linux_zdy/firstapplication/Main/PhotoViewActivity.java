package com.example.linux_zdy.firstapplication.Main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.linux_zdy.firstapplication.R;

import uk.co.senab.photoview.PhotoView;

public class PhotoViewActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view);

        PhotoView photo = (PhotoView)findViewById(R.id.photo);

        String url = getIntent().getStringExtra("url");

        Glide.with(this)
                .load(url)
                .into(photo);
    }
}
