package com.example.easts.rwsdcard;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.media.ExifInterface;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.MOUNT_UNMOUNT_FILESYSTEMS",
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE" };

    private String TAG = "MainActivity";

    private EditText input;
    private Button write;
    private TextView text;
    private Button read;

    public final String FILE_NAME = "/zdy.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        verifyStoragePermissions(this);

        input = (EditText) findViewById(R.id.input);
        write = (Button) findViewById(R.id.write);
        text = (TextView) findViewById(R.id.text);
        read = (Button) findViewById(R.id.read);

        write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(write(input.getText().toString())){
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                            .setTitle("提示信息")
                            .setMessage("写入成功")
                            .setPositiveButton("确认",null);
                    builder.show();
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                            .setTitle("提示信息")
                            .setMessage("写入失败")
                            .setPositiveButton("确认",null);
                    builder.show();
                }
            }
        });

        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String data = read();
                if(data != null){
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                            .setTitle("提示信息")
                            .setMessage("信息读取成功")
                            .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            text.setText(data);
                                        }
                                    });
                                }
                            });
                    builder.show();
                }
            }
        });
    }

    private String read(){
        try {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                Log.e(TAG,Environment.getExternalStorageState());
                Log.e(TAG,Environment.MEDIA_MOUNTED);
                File sdCardDir = Environment.getExternalStorageDirectory();
                FileInputStream fis = new FileInputStream(sdCardDir.getCanonicalPath() + FILE_NAME);
                BufferedReader bf = new BufferedReader(new InputStreamReader(fis));

                StringBuilder sb = new StringBuilder("");
                String line = null;
                while((line = bf.readLine())!=null){
                    sb.append(line);
                }
                bf.close();
                return sb.toString();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private boolean write(String data){
        try{

            if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                File sdCardDir = Environment.getExternalStorageDirectory();
                File targetFile = new File(sdCardDir.getCanonicalPath()+FILE_NAME);
                RandomAccessFile raf = new RandomAccessFile(targetFile,"rw");
                Log.e(TAG,"947519418@qq.com");
                raf.seek(targetFile.length());
                raf.write(data.getBytes());
                raf.close();
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
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
