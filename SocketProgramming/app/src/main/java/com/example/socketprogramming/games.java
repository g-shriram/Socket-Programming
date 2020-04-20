package com.example.socketprogramming;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class games extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games);
    }

    public void math(View view) {
     startActivity(new Intent(this,Maths_option.class));
    }

    public void tic(View view) {
        startActivity(new Intent(this,XOX_option.class));
    }
}
