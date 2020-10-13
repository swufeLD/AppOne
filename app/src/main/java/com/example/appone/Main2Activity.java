package com.example.appone;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ListAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main2Activity extends ListActivity implements Runnable {
    public static final String TAG = "Main2Activity";

    float dollarRate;
    float euroRate;
    float wonRate;

    Handler handle;
    String str;
    ListAdapter adapter;
    SharedPreferences share;
    List<String> list1;
    List<HashMap<String,String>> list2;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.exchange);

        Thread t=new Thread(this);
        t.start();

         // adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list1);
         // setListAdapter(adapter);

        handle=new Handler(){
            public void handleMessage(Message msg){
                super.handleMessage(msg);
                if(msg.what==0){
                    str=(String) msg.obj;
                    Log.i(TAG, "handleMessage:getMessage "+str);

                }

               /* //解析数据
                Document doc= Jsoup.parse(str);
                Elements tables=doc.getElementsByTag("table");

                Element table6=tables.get(5);

                //获取数据
                Elements tds=table6.getElementsByTag("td");
                for(int i=0;i< tds.size();i+=8){
                    Element td1=tds.get(i);
                    Element td2=tds.get(i+5);

                    String str1=td1.text();
                    String val=td2.text();

                    Log.i(TAG, "getDate: "+str1+"==>"+val);

                    float v=100f/Float.parseFloat(val);
                }
                */

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

        //解析数据
        share=getSharedPreferences("AllRate", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor=share.edit();
        list1 = new ArrayList<String>();
        list2= new ArrayList<HashMap<String, String>>();

        Intent get=new Intent(this,RateListActivity.class);
        Bundle bdl=new Bundle();
      //  Bundle bdl2=new Bundle();
        int f=1;
       // int f2=1;

        String url2="http://www.usd-cny.com/bankofchina.htm";
        Document doc= null;
        try {
            doc = Jsoup.connect(url2).get();
            Log.d(TAG, "run:"+doc.title());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Elements tables=doc.getElementsByTag("table");
        Log.i(TAG, "run: "+tables.size());
        Element table6=tables.get(0);

        //获取数据
        Elements tds=table6.getElementsByTag("td");
        for(int i=0;i< tds.size();i+=6){
            Element td1=tds.get(i);
            Element td2=tds.get(i+5);

            String str1=td1.text();
            String val=td2.text();

            Log.i(TAG, "getDate: "+str1+"==>"+val);
            String data=str1+"==>"+val ;
            editor.putString(str1,data);
            list1.add(data);
            bdl.putString(String.valueOf(f),data);
            f++;

           /* HashMap<String,String>map=new HashMap<String,String>();
            map.put(str1,"city"+f2);
            map.put("Rate",val);
            list2.add(map);
            bdl2.putSerializable(String.valueOf(f2),map);
            f2++;*/

            if(str1.equals("韩元")){
                if(!val.equals("-")){
                    wonRate= 100f/Float.parseFloat(val);
                }else{
                    wonRate=0;
                }
            }else if(str1.equals("美元")){
                if(!val.equals("-")){
                    dollarRate= 100f/Float.parseFloat(val);
                }else{
                    dollarRate=0;
                }

            }else if(str1.equals("欧元")){
                if(!val.equals("-")){
                   euroRate= 100f/Float.parseFloat(val);
                }else{
                    euroRate=0;
                }
            }
//            float v = 100f/Float.parseFloat(val);
        }
        /*for(int i=0;i<list2.size();i++){
            bdl2.putStringArrayList(String.valueOf(f2),list2.get(i));
            f2 ++;
        }*/

        editor.apply();
        get.putExtras(bdl);
        startActivity(get);

       /*  Message msg=handle.obtainMessage(1);
         msg.obj=list1;
         handle.sendMessage(msg);*/

        Log.i(TAG, "dollarRate: "+dollarRate);
        Log.i(TAG, "euroRate: "+euroRate);
        Log.i(TAG, "wonRate: "+wonRate);
       /* exchange ex=new exchange();
        boolean flag=ex.saveInfo(dollarRate,euroRate,wonRate);*/
        /*//保存数据
        SharedPreferences share=getSharedPreferences("myrate", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor=share.edit();
        editor.putFloat("dollar_rate", (float) dollarRate);
        editor.putFloat("euro_rate", (float) euroRate);
        editor.putFloat("won_rate", (float) wonRate);
        editor.apply();     */
        //存储所有的数据



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
