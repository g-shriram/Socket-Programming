package com.example.socketprogramming;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Maths_option extends AppCompatActivity {

    Button h,j,l;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maths_option);

        h=findViewById(R.id.mhost);
        j=findViewById(R.id.mjoin);
        l=findViewById(R.id.mlocal);

        h.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Maths_option.this,maths_h.class));
            }
        });
        j.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Maths_option.this,maths_j.class));
            }
        });
        l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Maths_option.this,maths.class));
            }
        });
    }
}
