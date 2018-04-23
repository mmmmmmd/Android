package com.example.easts.demo.Activty;

import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easts.demo.Content.StuContent;
import com.example.easts.demo.Content.URLContent;
import com.example.easts.demo.HttpRequest.RequestStuInfo;
import com.example.easts.demo.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {

    final String TAG = "com.MainActivity,tw";

    //控件
    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private View headerView;
    private NavigationView mNavigation;
    private Spinner spinnerXn;
    private Spinner spinnerXq;
    private Button btnQuery;

    //抽屉中的子控件
    //显示学生姓名的TextView
    private TextView stuName;
    //显示学生学号的TextView
    private TextView stuNumber;
    //显示学生班级的TextView
    private TextView stuClass;

    //上一个活动传进来的数据
    private String previousData;

    //使用SharedPreferences获取或保存需要持久化的信息
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    //更新UI使用的Handler
    private Handler mHandler = new Handler(Looper.getMainLooper());

    //课程的学年、学期
    private String[] course_xn;
    private String[] course_xq;
    //用户选择的学年、学期参数
    private String choose_xn;
    private String choose_xq;

    //---------------------------------------------------------------------------


    //初始化控件
    public void initView(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigation = (NavigationView) findViewById(R.id.nav_view);
        spinnerXn = (Spinner) findViewById(R.id.spinner_xn);
        spinnerXq = (Spinner) findViewById(R.id.spinner_xq);
        btnQuery = (Button) findViewById(R.id.btn_query);

        //获取NavigationView中控件
        headerView = mNavigation.getHeaderView(0);
        stuName = (TextView)headerView.findViewById(R.id.stu_name);
        stuNumber = (TextView)headerView.findViewById(R.id.stu_number);
        stuClass = (TextView)headerView.findViewById(R.id.stu_class);

        //设置spinner的监听事件，此时决定用户选择上传至服务器的学年和学期的参数
        spinnerXn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
                choose_xn = course_xn[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(MainActivity.this,"请选择学年的参数..",Toast.LENGTH_SHORT).show();
            }
        });
        spinnerXq.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                choose_xq = course_xq[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(MainActivity.this,"请选择学期的参数..",Toast.LENGTH_SHORT).show();
            }
        });

        //设置按钮的查询事件，当用户点击按钮后将Spinner中选择的参数上传至服务器
        //从服务器获得响应后利用爬虫拿到课程成绩数据然后放入RecyclerView中
        btnQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("TEST","测试用数据");
                        //首先检测用户是否选择了正确的学年学期参数
                        //如果选择错误的参数则进行提示，如果选择正确的参数则进行网络请求
                        if(choose_xn == "请选择学年" || choose_xq == "请选择学期") {
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MainActivity.this,"请选择正确的学年学期参数..",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }else{
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    OkHttpClient client = new OkHttpClient();
                                    RequestBody requestBody = new FormBody.Builder()
                                            .add("__EVENTARGUMENT","")
                                            .add("__EVENTTARGET","")
                                            .add("__VIEWSTATE","dDwtOTM3MjY4MTUzO3Q8cDxsPFNvcnRFeHByZXM7c2ZkY2JrO3p4Y2pjeHhzO2RnMztkeWJ5c2NqO1NvcnREaXJlO3hoO2NqY3hfbHNiO3N0cl90YWJfYmpnOz47bDxrY21jO1xlOzA7YmpnO1xlO2FzYzswNDE1MjAxNzs7emZfY3hjanRqXzA0MTUyMDE3Oz4+O2w8aTwxPjs+O2w8dDw7bDxpPDQ+O2k8MTA+O2k8MTk+O2k8MjQ+O2k8MzI+O2k8MzY+O2k8Mzg+O2k8NDA+O2k8NDI+O2k8NDQ+O2k8NDY+O2k8NDg+O2k8NTA+O2k8NTQ+O2k8NTY+O2k8NTg+Oz47bDx0PHQ8cDxwPGw8RGF0YVRleHRGaWVsZDtEYXRhVmFsdWVGaWVsZDs+O2w8WE47WE47Pj47Pjt0PGk8ND47QDxcZTsyMDE3LTIwMTg7MjAxNi0yMDE3OzIwMTUtMjAxNjs+O0A8XGU7MjAxNy0yMDE4OzIwMTYtMjAxNzsyMDE1LTIwMTY7Pj47Pjs7Pjt0PHQ8cDxwPGw8RGF0YVRleHRGaWVsZDtEYXRhVmFsdWVGaWVsZDs+O2w8a2N4em1jO2tjeHpkbTs+Pjs+O3Q8aTwxMj47QDzlv4Xkv67or7476ZmQ6YCJ6K++O+S7u+mAieivvjvor77lpJblrp7ot7XmlZnlraY76L6F5L+u6K++O+i3qOWtpuenkTvntKDotKjmi5PlsZU75YWs5YWx6YCJ5L+u6K++O+S6uuaWh+e0oOi0qOmZkOmAiTvpgInkv67or7476YCJ5L+u6K++KOWwlOmbhSk7XGU7PjtAPDAxOzAyOzAzOzA0OzA1OzA2OzA3OzA4OzA5OzEwOzExO1xlOz4+Oz47Oz47dDxwPHA8bDxWaXNpYmxlOz47bDxvPGY+Oz4+Oz47Oz47dDxwPHA8bDxWaXNpYmxlOz47bDxvPGY+Oz4+Oz47Oz47dDxwPHA8bDxWaXNpYmxlOz47bDxvPGY+Oz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDxcZTs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDtWaXNpYmxlOz47bDzlrablj7fvvJowNDE1MjAxNztvPHQ+Oz4+Oz47Oz47dDxwPHA8bDxUZXh0O1Zpc2libGU7PjtsPOWnk+WQje+8muW8oOS4nOmYsztvPHQ+Oz4+Oz47Oz47dDxwPHA8bDxUZXh0O1Zpc2libGU7PjtsPOWtpumZou+8muiuoeeul+acuuWtpumZojtvPHQ+Oz4+Oz47Oz47dDxwPHA8bDxUZXh0O1Zpc2libGU7PjtsPOS4k+S4mu+8mjtvPHQ+Oz4+Oz47Oz47dDxwPHA8bDxUZXh0O1Zpc2libGU7PjtsPOe9kee7nOW3peeoiztvPHQ+Oz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDzkuJPkuJrmlrnlkJE6Oz4+Oz47Oz47dDxwPHA8bDxUZXh0O1Zpc2libGU7PjtsPOihjOaUv+ePre+8mue9kee7nDE1MDE7bzx0Pjs+Pjs+Ozs+O3Q8QDA8cDxwPGw8VmlzaWJsZTs+O2w8bzxmPjs+Pjs+Ozs7Ozs7Ozs7Oz47Oz47dDw7bDxpPDE+O2k8Mz47aTw1PjtpPDc+O2k8OT47aTwxMz47aTwxNT47aTwxNz47aTwyMT47aTwyMz47aTwyND47aTwyNT47aTwyNz47aTwyOT47aTwzMT47aTwzMz47aTwzNT47aTw0Mz47aTw0OT47aTw1Mz47aTw1ND47PjtsPHQ8cDxwPGw8VmlzaWJsZTs+O2w8bzxmPjs+Pjs+Ozs+O3Q8QDA8cDxwPGw8VmlzaWJsZTs+O2w8bzxmPjs+PjtwPGw8c3R5bGU7PjtsPERJU1BMQVk6bm9uZTs+Pj47Ozs7Ozs7Ozs7Pjs7Pjt0PDtsPGk8MTM+Oz47bDx0PEAwPDs7Ozs7Ozs7Ozs+Ozs+Oz4+O3Q8cDxwPGw8VGV4dDtWaXNpYmxlOz47bDzoh7Pku4rmnKrpgJrov4for77nqIvmiJDnu6nvvJo7bzx0Pjs+Pjs+Ozs+O3Q8QDA8cDxwPGw8UGFnZUNvdW50O18hSXRlbUNvdW50O18hRGF0YVNvdXJjZUl0ZW1Db3VudDtEYXRhS2V5czs+O2w8aTwxPjtpPDM+O2k8Mz47bDw+Oz4+O3A8bDxzdHlsZTs+O2w8RElTUExBWTpibG9jazs+Pj47Ozs7Ozs7Ozs7PjtsPGk8MD47PjtsPHQ8O2w8aTwxPjtpPDI+O2k8Mz47PjtsPHQ8O2w8aTwwPjtpPDE+O2k8Mj47aTwzPjtpPDQ+O2k8NT47aTw2Pjs+O2w8dDxwPHA8bDxUZXh0Oz47bDxKUzEwMDMyMTs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w86K6h566X5py657uE5oiQ5Y6f55CGQTs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w86YCJ5L+u6K++Oz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDw0LjA7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPDA7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPCZuYnNwXDs7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPCZuYnNwXDs7Pj47Pjs7Pjs+Pjt0PDtsPGk8MD47aTwxPjtpPDI+O2k8Mz47aTw0PjtpPDU+O2k8Nj47PjtsPHQ8cDxwPGw8VGV4dDs+O2w8RFoxMTAyMjI7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPOaVsOWtl+eUtei3r+S4jumAu+i+keiuvuiuoUI7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPOmAieS/ruivvjs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w8My4wOz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDw1Njs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w8Jm5ic3BcOzs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w8Jm5ic3BcOzs+Pjs+Ozs+Oz4+O3Q8O2w8aTwwPjtpPDE+O2k8Mj47aTwzPjtpPDQ+O2k8NT47aTw2Pjs+O2w8dDxwPHA8bDxUZXh0Oz47bDxKUzEzMDA0MDs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w8572R57uc566h55CG5LiO57u05oqkOz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDzlv4Xkv67or747Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPDQuMDs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w8NTQ7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPCZuYnNwXDs7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPCZuYnNwXDs7Pj47Pjs7Pjs+Pjs+Pjs+Pjt0PEAwPHA8cDxsPFZpc2libGU7PjtsPG88Zj47Pj47cDxsPHN0eWxlOz47bDxESVNQTEFZOm5vbmU7Pj4+Ozs7Ozs7Ozs7Oz47Oz47dDxAMDw7Ozs7Ozs7Ozs7Pjs7Pjt0PEAwPHA8cDxsPFZpc2libGU7PjtsPG88Zj47Pj47cDxsPHN0eWxlOz47bDxESVNQTEFZOm5vbmU7Pj4+Ozs7Ozs7Ozs7Oz47Oz47dDxAMDw7Ozs7Ozs7Ozs7Pjs7Pjt0PEAwPHA8cDxsPFZpc2libGU7PjtsPG88Zj47Pj47cDxsPHN0eWxlOz47bDxESVNQTEFZOm5vbmU7Pj4+Ozs7Ozs7Ozs7Oz47Oz47dDxAMDxwPHA8bDxWaXNpYmxlOz47bDxvPGY+Oz4+O3A8bDxzdHlsZTs+O2w8RElTUExBWTpub25lOz4+Pjs7Ozs7Ozs7Ozs+Ozs+O3Q8QDA8cDxwPGw8VmlzaWJsZTs+O2w8bzxmPjs+Pjs+Ozs7Ozs7Ozs7Oz47Oz47dDxAMDxwPHA8bDxWaXNpYmxlOz47bDxvPGY+Oz4+O3A8bDxzdHlsZTs+O2w8RElTUExBWTpub25lOz4+Pjs7Ozs7Ozs7Ozs+Ozs+O3Q8QDA8cDxwPGw8VmlzaWJsZTs+O2w8bzxmPjs+PjtwPGw8c3R5bGU7PjtsPERJU1BMQVk6bm9uZTs+Pj47Ozs7Ozs7Ozs7Pjs7Pjt0PEAwPDtAMDw7O0AwPHA8bDxIZWFkZXJUZXh0Oz47bDzliJvmlrDlhoXlrrk7Pj47Ozs7PjtAMDxwPGw8SGVhZGVyVGV4dDs+O2w85Yib5paw5a2m5YiGOz4+Ozs7Oz47QDA8cDxsPEhlYWRlclRleHQ7PjtsPOWIm+aWsOasoeaVsDs+Pjs7Ozs+Ozs7Pjs7Ozs7Ozs7Oz47Oz47dDxwPHA8bDxUZXh0O1Zpc2libGU7PjtsPOacrOS4k+S4muWFsTEzM+S6ujtvPGY+Oz4+Oz47Oz47dDxwPHA8bDxWaXNpYmxlOz47bDxvPGY+Oz4+Oz47Oz47dDxwPHA8bDxWaXNpYmxlOz47bDxvPGY+Oz4+Oz47Oz47dDxwPHA8bDxWaXNpYmxlOz47bDxvPGY+Oz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDxYSVlPVTs+Pjs+Ozs+O3Q8cDxwPGw8SW1hZ2VVcmw7PjtsPC4vZXhjZWwvMDQxNTIwMTcuanBnOz4+Oz47Oz47Pj47dDw7bDxpPDM+Oz47bDx0PEAwPDs7Ozs7Ozs7Ozs+Ozs+Oz4+Oz4+Oz4+Oz5xZKuEd2Q60R2tchdyQ5QgICRylg==")
                                            .add("btn_xq","ѧ�ڳɼ�")
                                            .add("ddl_kcxz","")
                                            .add("ddlXN",choose_xn)
                                            .add("ddlXQ",choose_xq)
                                            .add("hidLanguage","")
                                            .build();
                                    //Request request = new Request.Builder()
                                    //.addHeader()
                                }
                            }).start();
                        }
                    }
                }).start();
            }
        });

    }

    //获取用户个人信息
    public void getUserInfo(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                RequestStuInfo.requestStuInfo(getMainUrl(),getCookie());

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        stuName.setText(StuContent.stuName);
                        stuNumber.setText(StuContent.stuNumber);
                        stuClass.setText(StuContent.stuClass);
                        Toast.makeText(MainActivity.this,"欢迎"+StuContent.stuAcademy+"的"+StuContent.stuName+"使用本系统",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).start();
    }

    public void getCourseInfo(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //获取课程学年及学期的分类
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                            .header("Accept-Encoding", "gzip, deflate")
                            .header("Accept-Language", "zh-CN,zh;q=0.9")
                            .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36")
                            .header("Cookie", getCookie())
                            .header("Referer", getMainUrl())
                            .header("Upgrade-Insecure-Requests", "1")
                            .header("Connection", "keep-alive")
                            .header("Host", "222.24.62.120")
                            .url(URLContent.cjcxUrl)
                            .build();
                    Response response = client.newCall(request).execute();
                    //从response中获取课程的学年及学期分类
                    String data = response.body().string();
                    Document doc = Jsoup.parse(data);
                    Elements elements_xn = doc.getElementById("ddlXN").select("option");
                    Elements elements_xq = doc.getElementById("ddlXQ").select("option");

                    //初始化Spring控件的数组，以数据长度为最大长度创建
                    course_xn = new String[elements_xn.size()];
                    course_xq = new String[elements_xq.size()];
                    //学年数组的临时索引
                    int index_xn = 0;
                    //为spring控件的数组赋值，将从html中分离出来的数据付给数组
                    for(Element element:elements_xn){
                        //分离出来的第一个值是空串，所以此处为用户添加提示信息
                        if(element.html() == "")
                            course_xn[index_xn]="请选择学年";
                        else {
                            course_xn[index_xn] = element.html();
                        }
                        index_xn++;
                    }
                    //学期数组的临时索引
                    int index_xq = 0;
                    //以下赋值同上
                    for(Element element:elements_xq){
                        if(element.html() == "")
                            course_xq[index_xq]="请选择学期";
                        else {
                            course_xq[index_xq]=element.html();
                        }
                        index_xq++;
                    }
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            ArrayAdapter<String> xnAdapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_spinner_item,course_xn);
                            ArrayAdapter<String> xqAdapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_spinner_item,course_xq);
                            xnAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            xqAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerXn.setAdapter(xnAdapter);
                            spinnerXq.setAdapter(xqAdapter);
                        }
                    });
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //解析html数据获取相应功能的URL
    public void paraseURL(){
        Document doc = Jsoup.parse(previousData);
        Elements elements = doc.select("a");
        for(Element element : elements){
            //从服务器回执的信息中分离出学生个人信息查询的url后缀
            if(element.attr("href").contains("xsgrxx")) {
                //将主机名和后缀拼接获取完整的个人信息查询url
                URLContent.grxxUrl = URLContent.host+element.attr("href").substring(0, element.attr("href").indexOf("&"));
            }
            //分离出学生成绩查询的url后缀
            else if(element.attr("href").contains("xscjcx")){
                //将主机名和后缀拼接获取完整的url
                URLContent.cjcxUrl = URLContent.host+element.attr("href").substring(0, element.attr("href").indexOf("&"));
                Log.d(TAG,URLContent.cjcxUrl);
            }
        }
        Log.e(TAG, URLContent.grxxUrl );
    }

    //获取教务处主页URL
    public String getMainUrl(){
        sharedPreferences = getSharedPreferences("user",MODE_PRIVATE);
        return sharedPreferences.getString("mainUrl","");
    }

    //获取用户cookie
    public String getCookie(){
        sharedPreferences = getSharedPreferences("user",MODE_PRIVATE);
        return sharedPreferences.getString("Cookie","");
    }

    //获取上一个活动传递过来的数据
    public void getPreviousdata(){
        previousData = getIntent().getStringExtra("data");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getPreviousdata();
        paraseURL();
        getUserInfo();
        getCourseInfo();
        initView();

        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        if(actionbar!=null){
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setHomeAsUpIndicator(R.drawable.navigation_button);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
        }
        return true;
    }
}
