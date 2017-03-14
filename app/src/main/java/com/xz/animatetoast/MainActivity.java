package com.xz.animatetoast;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import animatetoast.xz.com.animatetoastlibrary.AnimateToast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button btn1 = (Button) findViewById(R.id.btn1);


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimateToast.makeToast(MainActivity.this, "this warning message", Color.YELLOW,Color.BLACK).setTextSize(278).showTop(btn1);
            }
        });
        final Button btn2 = (Button) findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimateToast.makeToast(MainActivity.this, "this error message", Color.RED).showBottom(btn2);
            }
        });

        final Button btn3 = (Button) findViewById(R.id.btn3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] position = new int[2];
                btn3.getLocationOnScreen(position);
                AnimateToast.makeToast(MainActivity.this, "this success message", Color.BLUE).show(btn3,position[1],false);
            }
        });
    }
}
