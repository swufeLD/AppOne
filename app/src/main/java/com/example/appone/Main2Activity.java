package com.example.appone;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Main2Activity extends AppCompatActivity implements Runnable {
    public static final String TAG = "Main2Activity";

    Handler handle;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exchange);

        Thread t=new Thread(this);
        t.start();
        handle=new Handler(){
            public void handleMessage(Message msg){
                super.handleMessage(msg);
                if(msg.what==0){
                    String str=(String) msg.obj;
                    Log.i(TAG, "handleMessage:getMessage "+str);
                }
            }
        };


    }
    @Override
    public void run() {
       //从网络中获取数据
        URL url=null;
        try {
            url=new URL(getString(R.string.http));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection http=(HttpURLConnection) url.openConnection();
            InputStream in = http.getInputStream();

            String html = inputStream2String(in);
            Log.i(TAG, "onCreate: html="+ html);

            Message msg=handle.obtainMessage(0);
            msg.obj=html;
            handle.sendMessage(msg);

        } catch (IOException e) {
            e.printStackTrace();
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
