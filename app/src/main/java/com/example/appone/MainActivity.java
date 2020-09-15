package com.example.appone;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity  {
    @Override
    //second submit
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn=findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView input = findViewById(R.id.input);
                String str=input.getText().toString();
                double  num=Double.parseDouble(str);
                double sum=num *33.8;
                String s=String.valueOf(sum);
                TextView res =findViewById(R.id.res);
                res.setText(s);
            }
        });
    }

}













