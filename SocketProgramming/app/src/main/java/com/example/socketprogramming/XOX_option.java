package com.example.socketprogramming;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class XOX_option extends AppCompatActivity {

    Button h,j,l;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xox_option);
        h=findViewById(R.id.host);
        j=findViewById(R.id.join);
        l=findViewById(R.id.local);

        h.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(XOX_option.this,xox_h.class));
            }
        });
        j.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(XOX_option.this,xox_j.class));
            }
        });
        l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(XOX_option.this,XOX.class));
                }
        });
    }
}
