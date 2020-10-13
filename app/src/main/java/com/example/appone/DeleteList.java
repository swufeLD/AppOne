package com.example.appone;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DeleteList extends AppCompatActivity implements Runnable, AdapterView.OnItemClickListener {
    private static final String TAG = "DeleteList";

    Handler handler;
    List<HashMap<String, String>> listItems;
    List<HashMap<String, String>> listData;
    GridView gridView;
    MyAdapter myAdapter;

    public void run() {
        String url2 = "http://www.usd-cny.com/bankofchina.htm";
        Document doc = null;
        List<HashMap<String, String>> listData = new ArrayList<HashMap<String, String>>();
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

            HashMap<String, String> map = new HashMap<String, String>();
            map.put("ItemTitle", "Rate:" + str1);
            map.put("ItemDetail", "Detail:" + val);
            Log.i(TAG, "run: " + str1+ "==>" + val);
            listData.add(map);

        }


        Message msg = handler.obtainMessage(4);
        msg.obj = listData;
        handler.sendMessage(msg);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_grid);

        gridView =  findViewById(R.id.gridview);
        gridView.setOnItemClickListener(this);

        listItems = new ArrayList<HashMap<String, String>>();

        Thread t = new Thread(DeleteList.this);
        t.start();

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 4) {
                    listData = (ArrayList<HashMap<String, String>>) msg.obj;
                    myAdapter = new MyAdapter(DeleteList.this,
                            R.layout.list_item,
                            (ArrayList<HashMap<String, String>>) listData);
                    gridView.setAdapter(myAdapter);
                }
                super.handleMessage(msg);
            }
        };

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
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                     Log.i(TAG, "onItemClick: "+ gridView.getItemAtPosition(position));
                     myAdapter.remove(gridView.getItemAtPosition(position));
                     myAdapter.notifyDataSetChanged();
                     Log.i(TAG, "onItemClick: "+position);
                     if(myAdapter.isEmpty()){
                         gridView.setEmptyView(findViewById(R.id.nodata));
        }
    }
}
