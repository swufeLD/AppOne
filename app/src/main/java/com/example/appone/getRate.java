package com.example.appone;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class getRate extends AppCompatActivity implements View.OnClickListener{
    TextView t1;
    TextView t2;
    TextView t3;
    Button b1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get);

        Intent getRate=getIntent();
        double dollar_rate=getRate.getDoubleExtra("dollar_rate",0.0f);
        double euro_rate=getRate.getDoubleExtra("euro_rate",0.0f);
        double won_rate=getRate.getDoubleExtra("won_rate",0.0f);
        t1=findViewById(R.id.t1);
        t2=findViewById(R.id.t2);
        t3=findViewById(R.id.t3);
        t1.setText(String.valueOf(dollar_rate));
        t2.setText(String.valueOf(euro_rate));
        t3.setText(String.valueOf(won_rate));
        b1=findViewById(R.id.save);
        b1.setOnClickListener(this);

        //Log.i(TAG, "onClick: dollar_rate"+dollar_rate);
      //  Log.i(TAG, "onClick: euro_rate"+euro_rate);
       // Log.i(TAG, "onClick: won_rate"+won_rate);
    }

    @Override
    public void onClick(View view) {
        Intent intent=new Intent(this,exchange.class);
        Bundle bdl=new Bundle();
        TextView text1=findViewById(R.id.t1);
        TextView text2=findViewById(R.id.t2);
        TextView text3=findViewById(R.id.t3);
        float newDollar=Float.parseFloat(text1.getText().toString());
        float newEuro=Float.parseFloat(text2.getText().toString());
        float newWon=Float.parseFloat(text3.getText().toString());
        bdl.putFloat("key_dollar",newDollar);
        bdl.putFloat("key_euro",newEuro);
        bdl.putFloat("key_won",newWon);

        SharedPreferences share=getSharedPreferences("myrate", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor=share.edit();

        editor.putFloat("dollar_rate", (float) newDollar);
        editor.putFloat("euro_rate", (float) newEuro);
        editor.putFloat("won_rate", (float) newWon);

        Date current = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String current_format = format.format(current);
        editor.putString("update_date",current_format);
        editor.apply();

        intent.putExtras(bdl);
        setResult(2,intent);
        finish();
    }
}
