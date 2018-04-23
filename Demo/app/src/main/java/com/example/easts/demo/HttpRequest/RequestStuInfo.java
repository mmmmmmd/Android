package com.example.easts.demo.HttpRequest;

import android.util.Log;

import com.example.easts.demo.Content.StuContent;
import com.example.easts.demo.Content.URLContent;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by easts on 2017/12/21.
 */

public class RequestStuInfo {

    private static final String TAG = "RequestStuInfo";

    public static void requestStuInfo(String mainUrl,String cookie){
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .addHeader("Host", "222.24.62.120")
                    .header("Connection", "keep-alive")
                    .header("Upgrade-Insecure-Requests", "1")
                    .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36")
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                    .header("Referer", mainUrl)
                    .header("Accept-Encoding", "gzip, deflate")
                    .header("Accept-Language", "zh-CN,zh;q=0.9")
                    .header("Cookie", cookie)
                    .url(URLContent.grxxUrl)
                    .build();
            Response response = client.newCall(request).execute();

            //分析HTML数据获取学生的相关信息
            Document doc = Jsoup.parse(response.body().string());
            StuContent.stuName = doc.getElementsByClass("formlist").select("span").select("#xm").html();
            StuContent.stuNumber = doc.getElementsByClass("formlist").select("span").select("#xh").html();
            StuContent.stuMajor = doc.getElementsByClass("formlist").select("span").select("#lbl_zymc").html();
            StuContent.stuAcademy = doc.getElementsByClass("formlist").select("span").select("#lbl_xy").html();
            StuContent.stuClass = doc.getElementsByClass("formlist").select("span").select("#lbl_xzb").html();
            Log.e(TAG, "欢迎" + StuContent.stuAcademy + "的" + StuContent.stuName + "使用本系统" + "您的行政班级为" + StuContent.stuClass);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
