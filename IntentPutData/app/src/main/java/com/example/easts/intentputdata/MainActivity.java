package com.example.easts.intentputdata;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity {

    private EditText input_username;
    private EditText input_password;
    private RadioButton male;
    private Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input_password = (EditText) findViewById(R.id.password);
        input_username = (EditText) findViewById(R.id.username);
        male = (RadioButton) findViewById(R.id.male);

        send = (Button) findViewById(R.id.send);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sex = male.isChecked()?"男":"女";
                Person p = new Person(input_username.getText().toString(),input_password.getText().toString(),sex);

                Bundle data = new Bundle();
                data.putSerializable("person",p);

                Intent intent = new Intent(MainActivity.this,TempActivity.class);

                intent.putExtras(data);

                startActivity(intent);
            }
        });
    }
}
