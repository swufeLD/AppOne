package com.example.appone;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
public class count extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.count);
        //实现功能
        TextView btn1=findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 getScore(3);
            }
        });
        TextView btn2=findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getScore(2);
            }
        });
        TextView btn3=findViewById(R.id.btn3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getScore(1);
            }
        });
        TextView btn4=findViewById(R.id.btn4);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView score=findViewById(R.id.score);
                score.setText("0");
            }
        });
    }
    public void getScore(int socre){
        int sum=0;
        TextView score=findViewById(R.id.score);
        String three =score.getText().toString();
        int num=Integer.parseInt(three);
        sum = num+socre;
        String text= String.valueOf(sum);
        score.setText(text);
    }
}
