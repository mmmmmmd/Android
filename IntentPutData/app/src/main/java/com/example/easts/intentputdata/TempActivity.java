package com.example.easts.intentputdata;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class TempActivity extends AppCompatActivity {

    private TextView username;
    private TextView password;
    private TextView sex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);

        username = (TextView) findViewById(R.id.username);
        password = (TextView) findViewById(R.id.password);
        sex = (TextView) findViewById(R.id.sex);

        Intent intent = getIntent();

        Person p = (Person)intent.getSerializableExtra("person");

        username.setText(p.getUsername());
        password.setText(p.getPassword());
        sex.setText(p.getSex());
    }
}
