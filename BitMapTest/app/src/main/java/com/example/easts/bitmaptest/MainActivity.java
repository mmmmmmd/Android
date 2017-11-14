package com.example.easts.bitmaptest;

import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private ImageView image;
    private Button button;

    String[] images = null;
    AssetManager assetManager = null;
    int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        image = (ImageView) findViewById(R.id.image);

        assetManager = getAssets();
        try {
            images = assetManager.list("");
        } catch (IOException e) {
            e.printStackTrace();
        }

        button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //数组越界的情况
                if(index >= images.length)
                    index = 0;
                //查找下一个图片
                while(!images[index].endsWith(".png")&&
                        !images[index].endsWith(".jpg")&&
                        !images[index].endsWith(".gif")){
                    index++;
                    if(index>=images.length)
                        index = 0;
                }

                InputStream assetFile = null;

                try{
                    //打开指定资源对应的输入流
                    assetFile = assetManager.open(images[index++]);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                BitmapDrawable bitmapDrawable = (BitmapDrawable) image.getDrawable();

                //如果图片还未进行回收则强制回收该图片
                if(bitmapDrawable!=null&&
                        !bitmapDrawable.getBitmap().isRecycled())
                    bitmapDrawable.getBitmap().recycle();
                image.setImageBitmap(BitmapFactory.decodeStream(assetFile));
            }
        });
    }
}
