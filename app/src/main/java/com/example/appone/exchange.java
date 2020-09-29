package com.example.appone;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class exchange extends AppCompatActivity implements View.OnClickListener {
    double  dollar_rate; //=0.1472;
    double  euro_rate; // =0.1251;
    double  won_rate; //= 171.3427;

    Button btn1;
    Button btn2;
    Button btn3;
    Button btn4;
    TextView ex;
    TextView get;

    int request;
    int result;
    Intent da;

    public static final String TAG = "exchange";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exchange);

        btn1=(Button)findViewById(R.id.button8);
        btn1.setOnClickListener(this);
        btn2=(Button)findViewById(R.id.button9);
        btn2.setOnClickListener(this);
        btn3=(Button)findViewById(R.id.button10);
        btn3.setOnClickListener(this);
        btn4=(Button)findViewById(R.id.button11);
        ex=findViewById(R.id.exchange);
        get = findViewById(R.id.input);

        //线程

    }
    public String input(double n) {
        TextView get = findViewById(R.id.input);
        String text=get.getText().toString();
        if(text==null){
            Toast.makeText(this, "请输入正确的金额，不能为空", Toast.LENGTH_SHORT).show();
            return "0";
        }else{
            double num = Double.parseDouble(text);
            double sum = n * num;
            String s=String.valueOf(sum);
            return s;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        request=requestCode;
        result=resultCode;
        da=data;

        if(requestCode==1 && resultCode==2){
            Bundle bundle=data.getExtras();
            dollar_rate=bundle.getFloat("key_dollar",0.1f);
            euro_rate=bundle.getFloat("key_euro",0.1f);
            won_rate=bundle.getFloat("key_won",0.1f);

            Log.i(TAG, "onClick: dollar_rate"+dollar_rate);
            Log.i(TAG, "onClick: euro_rate"+euro_rate);
            Log.i(TAG, "onClick: won_rate"+won_rate);
        }

        saveInfo((float) dollar_rate,(float) euro_rate,(float) won_rate);

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View view) {
               boolean flag= getInfo();
        Log.i(TAG, "onClick: +flag"+flag);
              if(view.getId()==R.id.button8){
                  String text=input(dollar_rate);
                  ex.setText(text);
              }else if(view.getId()==R.id.button9){
                  String text=input(euro_rate);
                 ex.setText(text);
              }else if(view.getId()==R.id.button10){
                  String text=input(won_rate);
                  ex.setText(text);
              }
    }
    public void openOne(View btn){
        Intent get=new Intent(this,getRate.class);
        get.putExtra("dollar_rate",dollar_rate);
        get.putExtra("euro_rate",euro_rate);
        get.putExtra("won_rate",won_rate);

        Log.i(TAG, "onClick: dollar_rate"+dollar_rate);
        Log.i(TAG, "onClick: euro_rate"+euro_rate);
        Log.i(TAG, "onClick: won_rate"+won_rate);

        startActivity(get);
    }

public boolean saveInfo(float n1,float n2,float n3){
    SharedPreferences share=getSharedPreferences("myrate", Activity.MODE_PRIVATE);
    SharedPreferences.Editor editor=share.edit();
    editor.putFloat("dollar_rate", (float) n1);
    editor.putFloat("euro_rate", (float) n2);
    editor.putFloat("won_rate", (float) n3);
    editor.apply();
        return true;
}
    public boolean getInfo(){
        SharedPreferences getShare=getSharedPreferences("myrate",Activity.MODE_PRIVATE);
        dollar_rate=getShare.getFloat("dollar_rate",0.0f);
        euro_rate=getShare.getFloat("euro_rate",0.0f);
        won_rate=getShare.getFloat("won_rate",0.0f);
        return true;
    }
}
