package com.example.appone;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Timeout extends AppCompatActivity implements  Runnable {
    float dollarRate;
    float euroRate;
    float wonRate;

    Handler handle;
    SharedPreferences shared;

    String current_format;
    String pass_format;
    public static final String TAG = "Timeout";

    @Override
    public void run() {
        String url2 = "http://www.usd-cny.com/bankofchina.htm";
        Document doc = null;
        Bundle bundle = new Bundle();
        try {
            doc = Jsoup.connect(url2).get();
            Log.d(TAG, "run:" + doc.title());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Elements tables = doc.getElementsByTag("table");
        Log.i(TAG, "run: " + tables.size());
        Element table6 = tables.get(0);

        //获取数据
        Elements tds = table6.getElementsByTag("td");
        for (int i = 0; i < tds.size(); i += 6) {
            Element td1 = tds.get(i);
            Element td2 = tds.get(i + 5);

            String str1 = td1.text();
            String val = td2.text();

            Log.i(TAG, "getDate: " + str1 + "==>" + val);
            if (str1.equals("韩元")) {
                if (!val.equals("-")) {
                    wonRate = 100f / Float.parseFloat(val);
                } else {
                    wonRate = 0;
                }
            } else if (str1.equals("美元")) {
                if (!val.equals("-")) {
                    dollarRate = 100f / Float.parseFloat(val);
                } else {
                    dollarRate = 0;
                }

            } else if (str1.equals("欧元")) {
                if (!val.equals("-")) {
                    euroRate = 100f / Float.parseFloat(val);
                } else {
                    euroRate = 0;
                }
            }
        }
        bundle.putFloat("dollar_Rate", dollarRate);
        bundle.putFloat("euro_Rate", euroRate);
        bundle.putFloat("won_Rate", wonRate);

        Message msg = handle.obtainMessage(3);
        msg.obj = bundle;
        handle.sendMessage(msg);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shared = getSharedPreferences("myrate", Activity.MODE_PRIVATE);

        Date current = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        current_format = format.format(current);
        pass_format = shared.getString("update_date", "");
        if(pass_format.equals("")){
            pass_format=  current_format;
        }
        if (!current_format.equals(pass_format)) {
            Log.i(TAG, "updata: 需要更新, 上次更新日期" + current_format);
            Thread t = new Thread(this);
            t.start();

            handle = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    if (msg.what == 3) {
                        SharedPreferences.Editor editor = shared.edit();
                        Bundle bundle = (Bundle) msg.obj;
                        dollarRate = bundle.getFloat("dollar_Rate");
                        euroRate = bundle.getFloat("euro_Rate");
                        wonRate = bundle.getFloat("won_Rate");
                        editor.putFloat("dollar_rate", dollarRate);
                        editor.putFloat("euro_rate", euroRate);
                        editor.putFloat("won_rate", wonRate);
                        editor.putString("update_date", current_format);
                        editor.apply();
                    }
                }
            };
        }else{
            Log.i(TAG, "no-updata: 不需要更新, 更新日期为" + current_format);
        }
    }
    private String inputStream2String(InputStream inputStream) throws IOException {
        final int bufferSize=1024;
        final char[] buffer=new char[bufferSize];
        final StringBuilder  out =new StringBuilder();
        Reader in =new InputStreamReader(inputStream,"gb2312");
        while(true){
            int rsz=in.read(buffer,0,buffer.length);
            if(rsz<0){
                break;
            }
            out.append(buffer,0,rsz);
        }
        return out.toString();
    }

}
