package com.example.lenovo.android;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import me.nereo.multi_image_selector.MultiImageSelector;

//public class MainActivity extends AppCompatActivity {
//    private static final int REQUEST_IMAGE = 2;
//    protected static final int REQUEST_STORAGE_READ_ACCESS_PERMISSION = 101;
//    protected static final int REQUEST_STORAGE_WRITE_ACCESS_PERMISSION = 102;
//    protected ArrayList<String> defaultDataArray;
//    TextView textView;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        Button button=(Button)findViewById(R.id.Select);
//        textView=(TextView)findViewById(R.id.text);
//
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, MultiImageSelectorActivity.class);
//// whether show camera
//                intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
//// max select image amount
//                intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 9);
//// select mode (MultiImageSelectorActivity.MODE_SINGLE OR MultiImageSelectorActivity.MODE_MULTI)
//                intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_MULTI);
//// default select images (support array list)
//                intent.putStringArrayListExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST,defaultDataArray);
//                startActivityForResult(intent, REQUEST_IMAGE);
//            }
//        });
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode == REQUEST_IMAGE){
//            if(resultCode == RESULT_OK){
//                // Get the result list of select image paths
//                List<String> path = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
//                textView.setText(path.get(0).toString());
//            }
//        }
//    }
//}
public class MainActivity extends AppCompatActivity {
    Socket socket = null;
    DataOutputStream output = null;
    DataInputStream input = null;

    private static final String HOME = "192.168.1.110";
    private static final String BUFF = "--";

    private DrawerLayout mDrawerLayout;

    private  MapAdapter mapAdapter;
    private  List<Map> mapList=new ArrayList<>();
    private RecyclerView recyclerView;
    private static final int REQUEST_IMAGE = 2;
    protected static final int REQUEST_STORAGE_READ_ACCESS_PERMISSION = 101;
    protected static final int REQUEST_STORAGE_WRITE_ACCESS_PERMISSION = 102;

    private TextView mResultText;
    private RadioGroup mChoiceMode, mShowCamera;
    private EditText mRequestNum;
    private Button upload;
    private Button download_show;

    private ArrayList<String> mSelectPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        mResultText = (TextView) findViewById(R.id.result);
        mChoiceMode = (RadioGroup) findViewById(R.id.choice_mode);
        mShowCamera = (RadioGroup) findViewById(R.id.show_camera);
        mRequestNum = (EditText) findViewById(R.id.request_num);
        upload=(Button)findViewById(R.id.upload);
        download_show=(Button)findViewById(R.id.down_load_show);
        recyclerView=(RecyclerView)findViewById(R.id.recycler_view);
        mChoiceMode.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if(checkedId == R.id.multi){
                    mRequestNum.setEnabled(true);
                }else{
                    mRequestNum.setEnabled(false);
                    mRequestNum.setText("");
                }
            }
        });

        View button = findViewById(R.id.button);
        if (button != null) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pickImage();
                }
            });
        }
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("777","waiting.....");
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                    // 详见StrictMode文档
                    StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                            .detectDiskReads().detectDiskWrites().detectNetwork()
                            .penaltyLog().build());
                    StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                            .detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
                            .penaltyLog().penaltyDeath().build());
                }
                try {
                    Log.d("777","执行到。。。。");
                    socket = new Socket(HOME, 8989);
                    Log.d("777","连接成功！......");
                    // 得到输出流
                    output = new DataOutputStream(socket.getOutputStream());
                    // 得到如入流
                    input = new DataInputStream(socket.getInputStream());
                }catch (Exception e){
                    e.printStackTrace();
                }

                for(String p:mSelectPath){
                        Log.d("999",p.toString());
                    try {
                        File file = new File(p.toString());
//                        Upload upload = new Upload();

//                        String  ServerStr= upload.uploadFile(file);
                        /* 取得文件的FileInputStream */
                        FileInputStream fStream = new FileInputStream(file);

                        String[] fileEnd = file.getName().split("\\\\");
                        output.writeUTF(fileEnd[fileEnd.length-1].toString());
                        output.writeLong(file.length());
                        Log.d("999",file.length()+"");
                        System.out.println("buffer------------------" + BUFF
                                + fileEnd[fileEnd.length-1].toString());

                        //设置每次写入1024bytes
                        int bufferSize = 1;
                        byte[] buffer = new byte[bufferSize];
                        int length = -1;
                        // 从文件读取数据至缓冲区（值为-1说明已经读完）
                        while ((length = fStream.read(buffer)) != -1) {
                /* 将资料写入DataOutputStream中 */
                            output.write(buffer, 0, length);
                            if(length!=bufferSize){
                                byte[] b=new byte[bufferSize-length];
                                output.write(b,0,b.length);
                            }

                        }


                        fStream.close();

                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                       e.printStackTrace();
                        Toast.makeText(MainActivity.this,"图片全部上传失败！",Toast.LENGTH_LONG).show();

                    }
                }

                try {
                    Log.d("999","图片上传成功！");
                    output.writeUTF("#$");


                    // 一定要加上这句，否则收不到来自服务器端的消息返回
                    Log.d("999","图片上传成功！123");
                    socket.shutdownOutput();

                    String msg;
                    msg = input.readUTF();
                    Log.d("999","图片上传成功！"+msg);
                    Toast.makeText(MainActivity.this,"图片成功上传至服务器文件"+msg,Toast.LENGTH_LONG).show();

                    output.close();
                    input.close();

                }catch (Exception e){
                    e.printStackTrace();
                }


//                Toast.makeText(MainActivity.this,"图片全部上传成功！",Toast.LENGTH_LONG).show();

            }
        });
        download_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        try{
//                            Socket socket = new Socket("192.168.1.112",8181);
//                            Log.d("5555","执行了！");
//                            PrintWriter out = new PrintWriter(new BufferedOutputStream(socket.getOutputStream()));
//                            DataInputStream inputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
//                            System.out.println("start client....");
//                            out.println("sendImage");
//                            out.flush();
//                            System.out.println("sendImage....");
//                            byte[] buffer = new byte[2 * 1024];
//                            int len = -1;
//                            String imageName = null;
//                            FileOutputStream fos = null;
//                            File file = null;
//                            long fileLength = -1;
//                            while(true) {
//                                imageName = inputStream.readUTF();
//                                if(imageName != null && imageName.trim().equals("$")) {
////                                if(imageName.trim().equals("end")) {
//                                    break;
//                                }
//                                // String fileName="map";
////                                String direcyory= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
////                                file = new File("/sdcard/Test/"+filename);
//
//                                file = new File("/sdcard/storage/"+imageName);
//
//
//                                Log.d("11111",file.getPath());
//                                if(!file.exists()) {
//                                    file.createNewFile();
//                                } else if(file.isFile()) {
//                                    file.delete();
//                                    file.createNewFile();
//                                } else if(file.isDirectory()) {
//                                    continue;
//                                }
//                                fileLength = inputStream.readLong();
//                                len = -1;
//                                fos = new FileOutputStream(file);
////                                FilePath.add(file.getPath());
////                                Log.d("9999",file.getPath());
//                                while(fileLength>0) {///fileLength为负退出读取下一个文件
//                                    len = inputStream.read(buffer);
//                                    fos.write(buffer, 0, len);
//                                    if(len != -1) {
//                                        fileLength -= len;
//                                    }
//
//                                }
//
//
////                                while((len=inputStream.read(buffer))!=-1){
////                                    fos.write(buffer,0,len);
////                                }
//                                fos.flush();
//                                fos.close();
//                            }
//                            Toast.makeText(getApplicationContext(),"文件读取完毕",Toast.LENGTH_LONG).show();
//                            out.close();
//                            socket.close();
//                        } catch(Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                }).start();
//            }
                GridLayoutManager layoutManager = new GridLayoutManager(MainActivity.this, 3);
                recyclerView.setLayoutManager(layoutManager);
                mapAdapter = new MapAdapter(mapList);
                recyclerView.setAdapter(mapAdapter);
                mapList.clear();

                File file = new File("/sdcard/storage/map");
                File[] files = file.listFiles();
                for (File f : files) {
                    mapList.add(new Map(f.getPath().toString()));
                    Log.d("7777",f.getPath().toString());

                }

        }
        });
    }


            private void pickImage() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN // Permission was added in API Level 16
                        && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE,
                            getString(R.string.mis_permission_rationale),
                            REQUEST_STORAGE_READ_ACCESS_PERMISSION);
                } else {
                    boolean showCamera = mShowCamera.getCheckedRadioButtonId() == R.id.show;
                    int maxNum = 9;

                    if (!TextUtils.isEmpty(mRequestNum.getText())) {
                        try {
                            maxNum = Integer.valueOf(mRequestNum.getText().toString());
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }
                    MultiImageSelector selector = MultiImageSelector.create(MainActivity.this);
                    selector.showCamera(showCamera);
                    selector.count(maxNum);
                    if (mChoiceMode.getCheckedRadioButtonId() == R.id.single) {
                        selector.single();
                    } else {
                        selector.multi();
                    }
                    selector.origin(mSelectPath);
                    selector.start(MainActivity.this, REQUEST_IMAGE);
                }
            }

            private void requestPermission(final String permission, String rationale, final int requestCode) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                    new AlertDialog.Builder(this)
                            .setTitle(R.string.mis_permission_dialog_title)
                            .setMessage(rationale)
                            .setPositiveButton(R.string.mis_permission_dialog_ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission}, requestCode);
                                }
                            })
                            .setNegativeButton(R.string.mis_permission_dialog_cancel, null)
                            .create().show();
                } else {
                    ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
                }
            }

            @Override
            public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
                if (requestCode == REQUEST_STORAGE_READ_ACCESS_PERMISSION) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        pickImage();
                    }
                } else {
                    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                }
            }

            @Override
            protected void onActivityResult(int requestCode, int resultCode, Intent data) {
                super.onActivityResult(requestCode, resultCode, data);
                if (requestCode == REQUEST_IMAGE) {
                    if (resultCode == RESULT_OK) {
                        mSelectPath = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);
//                StringBuilder sb = new StringBuilder();
//                for(String p: mSelectPath){
//                    sb.append(p);
//                    sb.append("\n");
//                }
////                mResultText.setText(sb.toString());


//                        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
                        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
                        recyclerView.setLayoutManager(layoutManager);
                        mapAdapter = new MapAdapter(mapList);
                        recyclerView.setAdapter(mapAdapter);

                        mapList.clear();
                        for (String p : mSelectPath) {
                            mapList.add(new Map(p.toString()));

                        }
                    }
                }
            }

            @Override
            public boolean onCreateOptionsMenu(Menu menu) {
                // Inflate the menu; this adds items to the action bar if it is present.
                getMenuInflater().inflate(R.menu.menu_main, menu);
                return true;
            }

            @Override
            public boolean onOptionsItemSelected(MenuItem item) {
                // Handle action bar item clicks here. The action bar will
                // automatically handle clicks on the Home/Up button, so long
                // as you specify a parent activity in AndroidManifest.xml.
                int id = item.getItemId();

                //noinspection SimplifiableIfStatement
                if (id == R.id.action_settings) {
                    return true;
                }

                return super.onOptionsItemSelected(item);
            }

}
