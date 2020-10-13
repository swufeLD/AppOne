package com.example.appone;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ShowRate extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "ShowRate";
    String title;
    String detail;
    TextView t1;
    TextView t2;
    TextView t3;
    TextView t4;
    Button b1;

    protected  void  onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showrate);
        Intent get=getIntent();
        Bundle bdl=get.getExtras();
        title=bdl.getString("title","");
        detail=bdl.getString("detail","");
        String ss[]=title.split(":");
        t1=findViewById(R.id.text1);
        t2=findViewById(R.id.text2);
        t3=findViewById(R.id.text3);
        t4=findViewById(R.id.editText);
        b1=findViewById(R.id.bb);
        t1.setText(ss[2]);
        String sss[]=detail.split(":");
        t2.setText(sss[2]);
        b1.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        String input=t4.getText().toString();
        String s[]=detail.split(":");
        float rate=Float.parseFloat(s[2]);
        float value=Float.parseFloat(input);
        float conver=(rate*value)/100;
        Log.i(TAG, "onClick: "+conver);
        t3.setText(String.valueOf(conver));
    }
}
