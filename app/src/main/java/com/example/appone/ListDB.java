package com.example.appone;

import android.app.ListActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ListDB extends ListActivity implements  Runnable {
    public static final String TAG = "ListDB";

    private String logDate = "";
    private final String DATE_SP_KEY = "localDate";

    Handler handler;
    List<String> list;

    protected  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sp = getSharedPreferences("AllRate", Context.MODE_PRIVATE);
        logDate = sp.getString(DATE_SP_KEY, "");
        Log.i("List","localDate=" + logDate);

        Thread t = new Thread(this);
        t.start();

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 5) {
                  list=(ArrayList<String>)msg.obj;
                }
            }
        };
        ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        setListAdapter(adapter);
    }

    @Override
    public void run() {
        Log.i("List","run...");
        List<String> retList = new ArrayList<String>();
        String curDateStr = (new SimpleDateFormat("yyyy-MM-dd")).format(new Date());
        Log.i("run","curDateStr:" + curDateStr + " logDate:" + logDate);
        if(curDateStr.equals(logDate)){
            //如果相等，则不从网络中获取数据
            Log.i("run","日期相等，从数据库中获取数据");
           RateManager dbManager = new RateManager(this);
            for(RateItem rateItem : dbManager.listAll()){
                retList.add(rateItem.getCurName() + "=>" + rateItem.getCurRate());
            }
        }else{
            Log.i("run","日期不相等，从网络中获取在线数据");
            //获取网络数据
            try {
                List<RateItem> rateList = new ArrayList<RateItem>();
                String url2 = "http://www.usd-cny.com/bankofchina.htm";
                Document doc = null;

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

                    RateItem rateItem = new RateItem(str1,val);
                    rateList.add(rateItem);

                    retList.add(rateItem.getCurName() + "=>" + rateItem.getCurRate());
                }

               RateManager dbManager = new RateManager(this);
                dbManager.deleteAll();
                Log.i("db","删除所有记录");
                dbManager.addAll(rateList);
                Log.i("db","添加新记录集");

            } catch (Exception e) {
                e.printStackTrace();
            }

            //更新记录日期
            SharedPreferences sp = getSharedPreferences("Allate", Context.MODE_PRIVATE);
            Editor edit = sp.edit();
            edit.putString(DATE_SP_KEY, curDateStr);
            edit.commit();
            Log.i("run","更新日期结束：" + curDateStr);
        }
        Message msg = handler.obtainMessage(5);
        msg.obj = retList;
        handler.sendMessage(msg);
    }
}
