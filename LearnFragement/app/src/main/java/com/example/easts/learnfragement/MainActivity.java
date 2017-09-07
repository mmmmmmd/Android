package com.example.easts.learnfragement;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.easts.learnfragement.fragments_main.ContactFragment;
import com.example.easts.learnfragement.fragments_main.MessageFragment;
import com.example.easts.learnfragement.fragments_main.SettingFragment;
import com.example.easts.learnfragement.fragments_main.TrendFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private MessageFragment messageFragment;
    private ContactFragment contactFragment;
    private TrendFragment trendFragment;
    private SettingFragment settingFragment;

    private View messageLayout;
    private View contactLayout;
    private View trendLayout;
    private View settingLayout;

    private ImageView messageImage;
    private ImageView contactImage;
    private ImageView trendImage;
    private ImageView settingImage;

    private TextView messageText;
    private TextView contactText;
    private TextView trendText;
    private TextView settingText;

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        fragmentManager = getFragmentManager();

        setTabSelection(0);

    }

    private void initViews(){
        messageLayout = findViewById(R.id.message_layout);
        contactLayout = findViewById(R.id.contact_layout);
        trendLayout = findViewById(R.id.trend_layout);
        settingLayout = findViewById(R.id.setting_layout);

        messageImage = (ImageView)findViewById(R.id.message_image);
        contactImage = (ImageView)findViewById(R.id.contact_image);
        trendImage = (ImageView)findViewById(R.id.trend_image);
        settingImage = (ImageView)findViewById(R.id.setting_image);

        messageText = (TextView)findViewById(R.id.message_text);
        contactText = (TextView)findViewById(R.id.contact_text);
        trendText = (TextView)findViewById(R.id.trend_text);
        settingText = (TextView)findViewById(R.id.setting_text);

        messageLayout.setOnClickListener(this);
        contactLayout.setOnClickListener(this);
        trendLayout.setOnClickListener(this);
        settingLayout.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.message_layout:
                setTabSelection(0);
                break;
            case R.id.contact_layout:
                setTabSelection(1);
                break;
            case R.id.trend_layout:
                setTabSelection(2);
                break;
            case R.id.setting_layout:
                setTabSelection(3);
                break;
            default:
                break;
        }

    }

    private void setTabSelection(int index){
        //选中之前先清楚掉之前的状态
        clearSelection();

        //开启一个Fragment事务
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        hideFragments(fragmentTransaction);
        switch(index){
            case 0:
                messageImage.setImageResource(R.drawable.message_select);
                messageText.setTextColor(Color.BLACK);
                if(messageFragment == null){
                    messageFragment = new MessageFragment();
                    fragmentTransaction.add(R.id.content,messageFragment);
                }else{
                    fragmentTransaction.show(messageFragment);
                }
                fragmentTransaction.commit();
                break;
            case 1:
                contactImage.setImageResource(R.drawable.contact_select);
                contactText.setTextColor(Color.BLACK);
                if(contactFragment == null){
                    contactFragment = new ContactFragment();
                    fragmentTransaction.add(R.id.content,contactFragment);
                }else{
                    fragmentTransaction.show(contactFragment);
                }
                fragmentTransaction.commit();
                break;

            case 2:
                trendImage.setImageResource(R.drawable.trend_select);
                trendText.setTextColor(Color.BLACK);
                if(trendFragment == null){
                    trendFragment = new TrendFragment();
                    fragmentTransaction.add(R.id.content,trendFragment);
                }else{
                    fragmentTransaction.show(trendFragment);
                }
                fragmentTransaction.commit();
                break;

            case 3:
                settingImage.setImageResource(R.drawable.setting_select);
                settingText.setTextColor(Color.BLACK);
                if(settingFragment == null){
                    settingFragment = new SettingFragment();
                    fragmentTransaction.add(R.id.content,settingFragment);
                }else{
                    fragmentTransaction.show(settingFragment);
                }
                fragmentTransaction.commit();
                break;
            default:
                break;
        }
    }

    private void clearSelection(){
        messageImage.setImageResource(R.drawable.message);
        messageText.setTextColor(Color.WHITE);
        contactImage.setImageResource(R.drawable.contact);
        contactText.setTextColor(Color.WHITE);
        trendImage.setImageResource(R.drawable.trend);
        trendText.setTextColor(Color.WHITE);
        settingImage.setImageResource(R.drawable.setting);
        settingText.setTextColor(Color.WHITE);

    }

    private void hideFragments(FragmentTransaction fragmentTransaction){
        if(messageFragment != null){
            fragmentTransaction.hide(messageFragment);
        }
        if(contactFragment != null){
            fragmentTransaction.hide(contactFragment);
        }
        if(trendFragment != null){
            fragmentTransaction.hide(trendFragment);
        }
        if(settingFragment != null){
            fragmentTransaction.hide(settingFragment);
        }
    }
}
