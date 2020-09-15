package com.example.appone;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
public class count extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.count);
        TextView btn1=findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView score=findViewById(R.id.score);
                String three =score.getText().toString();
                int num=Integer.parseInt(three);
                int sum = num+3;
                String text= String.valueOf(sum);
                score.setText(text);
            }
        });
        TextView btn2=findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView score=findViewById(R.id.score);
                String three =score.getText().toString();
                int num=Integer.parseInt(three);
                int sum = num+2;
                String text= String.valueOf(sum);
                score.setText(text);
            }
        });
        TextView btn3=findViewById(R.id.btn3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView score=findViewById(R.id.score);
                String three =score.getText().toString();
                int num=Integer.parseInt(three);
                int sum = num+1;
                String text= String.valueOf(sum);
                score.setText(text);
            }
        });
    }
}
