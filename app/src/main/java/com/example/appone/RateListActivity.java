package com.example.appone;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import java.util.ArrayList;
import java.util.List;

public class RateListActivity extends ListActivity  {


    protected  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent getRate=getIntent();
        List<String> list1 = new ArrayList<String>();
        Bundle bundle=getRate.getExtras();
        int f=1;
        for(int i=0;i<bundle.size();i++){
            list1.add(bundle.getString(String.valueOf(f)));
            f++;
        }
        ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list1);
        setListAdapter(adapter);
       /* List<HashMap<String,String>> list2= new ArrayList<HashMap<String, String>>();
        Bundle bundle=getRate.getExtras();
        int f2=1;
        for(int i=0;i<bundle.size();i++){
            list2.add((HashMap)bundle.getSerializable(String.valueOf(f2)));
            f2++;
        }
        ListAdapter adapter= new SimpleAdapter(this,
                list2, // listItems 数据源
                android.R.layout.simple_expandable_list_item_2, // ListItem 的 XML 布局实现
                new String[] { "ItemTitle", "ItemDetail" },
                new String [] {android.R.id., R.id.itemDetail }
        );*/

    }

}
