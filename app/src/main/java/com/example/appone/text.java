package com.example.appone;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class text extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.text);

        TextView btn1=findViewById(R.id.button);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getScoreA(3);
            }
        });
        TextView btn2=findViewById(R.id.button2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getScoreA(2);
            }
        });
        TextView btn3=findViewById(R.id.button3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getScoreA(1);
            }
        });
        TextView btn4=findViewById(R.id.button4);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getScoreB(3);
            }
        });
        TextView btn5=findViewById(R.id.button5);
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getScoreB(2);
            }
        });
        TextView btn6=findViewById(R.id.button6);
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getScoreB(1);
            }
        });
        TextView btn7=findViewById(R.id.button7);
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView score=findViewById(R.id.scoreA);
                score.setText("0");
                TextView scoreB=findViewById(R.id.scoreB);
                scoreB.setText("0");
            }
        });
    }
    public void getScoreA(int socre){
        int sum=0;
        TextView score=findViewById(R.id.scoreA);
        String three =score.getText().toString();
        int num=Integer.parseInt(three);
        sum = num+socre;
        String text= String.valueOf(sum);
        score.setText(text);
    }
    public void getScoreB(int socre){
        int sum=0;
        TextView score=findViewById(R.id.scoreB);
        String three =score.getText().toString();
        int num=Integer.parseInt(three);
        sum = num+socre;
        String text= String.valueOf(sum);
        score.setText(text);
    }
}