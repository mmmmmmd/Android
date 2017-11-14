package com.example.easts.databasetest;

import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "Zh.EastSun";

    //控件
    private Button create_table;
    private Button add_data;
    private Button update_data;
    private Button delete_data;
    private Button query_data;

    //数据库操作类
    private MyDataBaseHelper helper;

    //动态申请权限的描述符
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.MOUNT_UNMOUNT_FILESYSTEMS",
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        verifyStoragePermissions(this);

        helper = new MyDataBaseHelper(MainActivity.this,"Book_Store",null,2);

        create_table = (Button) findViewById(R.id.create_table);
        add_data = (Button) findViewById(R.id.add_data);
        update_data = (Button) findViewById(R.id.update_data);
        delete_data = (Button) findViewById(R.id.delete_data);
        query_data = (Button) findViewById(R.id.query);

        create_table.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 helper.getWritableDatabase();
            }
        });

        add_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = helper.getWritableDatabase();
                ContentValues values = new ContentValues();

                values.put("name","Linux");
                values.put("author","Zh");
                values.put("pages",300);
                values.put("price",20.50);
                db.insert("Book",null,values);

                values.clear();

                values.put("name","Android");
                values.put("author","Guo");
                values.put("pages",500);
                values.put("price",30.80);
                db.insert("Book",null,values);

                db.close();
                values.clear();


                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("信息")
                        .setMessage("插入信息成功")
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(MainActivity.this,"点击了确认按钮",Toast.LENGTH_SHORT).show();
                            }
                        });
                builder.show();
            }
        });

        update_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = helper.getWritableDatabase();
                ContentValues values = new ContentValues();

                values.put("price",29.9);
                db.update("Book",values,"name = ?",new String[] {"Android"});

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("提示信息")
                        .setMessage("信息更新成功")
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(MainActivity.this,"您点击了确认按钮",Toast.LENGTH_SHORT).show();
                            }
                        });
                builder.show();
            }
        });

        delete_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = helper.getWritableDatabase();
                db.delete("Book","pages > ?",new String[] {"400"});

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("提示信息")
                        .setMessage("页数大于400的信息删除成功")
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(MainActivity.this,"您点击了确认按钮",Toast.LENGTH_SHORT).show();
                            }
                        });
                builder.show();
            }
        });

        query_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = helper.getWritableDatabase();

                Cursor cursor = db.query("Book",null,null,null,null,null,null);
                if(cursor.moveToFirst()){
                    do{
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String author = cursor.getString(cursor.getColumnIndex("author"));
                        int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                        double price = cursor.getDouble(cursor.getColumnIndex("price"));

                        Log.e(TAG,"Book name is :"+name);
                        Log.e(TAG,"Book author is :"+author);
                        Log.e(TAG,"Book pages is :"+pages);
                        Log.e(TAG,"Book price is :"+price);
                    }while(cursor.moveToNext());
                }
            }
        });
    }

    public static void verifyStoragePermissions(Activity activity) {

        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
