package com.example.socketprogramming;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class XOX extends AppCompatActivity {

    ImageView[] img = new ImageView[9];
    TextView win;
    int[] xox = new int[9];
    boolean[] sel = new boolean[9];
    private int yours = 1;
    int n;
    int toggle = 1;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xox);
        win = findViewById(R.id.win);
        img[0] = findViewById(R.id.a1);
        img[1] = findViewById(R.id.a2);
        img[2] = findViewById(R.id.a3);
        img[3] = findViewById(R.id.a4);
        img[4] = findViewById(R.id.a5);
        img[5] = findViewById(R.id.a6);
        img[6] = findViewById(R.id.a7);
        img[7] = findViewById(R.id.a8);
        img[8] = findViewById(R.id.a9);

        for (int i = 0; i < 9; i++) {
            sel[i] = false;
            xox[i] = i * 7;
            final int finalI = i;

            img[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (sel[finalI] != true) {
                        count++;
                        sel[finalI] = true;
                        xox[finalI] = toggle;
                        if (toggle == 1)
                            img[finalI].setImageResource(R.drawable.x);
                        else
                            img[finalI].setImageResource(R.drawable.zero);
                        check(xox);
                        toggle = toggle * -1;
                    }

                }
            });
        }
    }

    void check(int[] x) {
        int f = 0;
        if (x[0] == x[1] && x[1] == x[2]) {
            f = 1;
            n = x[0];
            img[0].setBackgroundResource(R.drawable.customborder1);
            img[1].setBackgroundResource(R.drawable.customborder1);
            img[2].setBackgroundResource(R.drawable.customborder1);
        }
        if (x[3] == x[4] && x[4] == x[5]) {
            f = 1;
            n = x[3];
            img[3].setBackgroundResource(R.drawable.customborder1);
            img[4].setBackgroundResource(R.drawable.customborder1);
            img[5].setBackgroundResource(R.drawable.customborder1);
        }
        if (x[6] == x[7] && x[7] == x[8]) {
            f = 1;
            n = x[6];
            img[6].setBackgroundResource(R.drawable.customborder1);
            img[7].setBackgroundResource(R.drawable.customborder1);
            img[8].setBackgroundResource(R.drawable.customborder1);
        }
        if (x[0] == x[3] && x[3] == x[6]) {
            f = 1;
            n = x[0];
            img[0].setBackgroundResource(R.drawable.customborder1);
            img[3].setBackgroundResource(R.drawable.customborder1);
            img[6].setBackgroundResource(R.drawable.customborder1);
        }
        if (x[1] == x[4] && x[1] == x[7]) {
            f = 1;
            n = x[1];
            img[1].setBackgroundResource(R.drawable.customborder1);
            img[4].setBackgroundResource(R.drawable.customborder1);
            img[7].setBackgroundResource(R.drawable.customborder1);
        }
        if (x[2] == x[5] && x[5] == x[8]) {
            f = 1;
            n = x[2];
            img[2].setBackgroundResource(R.drawable.customborder1);
            img[5].setBackgroundResource(R.drawable.customborder1);
            img[8].setBackgroundResource(R.drawable.customborder1);
        }
        if (x[0] == x[4] && x[4] == x[8]) {
            f = 1;
            n = x[0];
            img[0].setBackgroundResource(R.drawable.customborder1);
            img[4].setBackgroundResource(R.drawable.customborder1);
            img[8].setBackgroundResource(R.drawable.customborder1);
        }
        if (x[2] == x[4] && x[4] == x[6]) {
            f = 1;
            n = x[2];
            img[2].setBackgroundResource(R.drawable.customborder1);
            img[4].setBackgroundResource(R.drawable.customborder1);
            img[6].setBackgroundResource(R.drawable.customborder1);
        }

        if (f == 1) {
            new1();
            for (int i = 0; i < 9; i++) {
                img[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(XOX.this, "Game Over !!!", Toast.LENGTH_LONG).show();
                    }
                });
            }
            if (n == 1) {
                win.setText("X wins");
            } else
                win.setText("O wins");
        } else {
            if (count == 9) {
                for (int i = 0; i < 9; i++) {
                    img[i].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(XOX.this, "Game Over !!!", Toast.LENGTH_LONG).show();
                        }
                    });
                }

                win.setText("Match Draw !");
                new1();


            }


        }

    }
    void new1(){
        Runnable r = new Runnable() {
            @Override
            public void run() {
                // if you are redirecting from a fragment then use getActivity() as the context.
                startActivity(new Intent(XOX.this, XOX.class));
                // To close the CurrentActitity, r.g. SpalshActivity
                finish();
            }
        };
        TextView t=(TextView) findViewById(R.id.s);
        t.setText("Restarts in 5 seconds ...");
        Handler h = new Handler();
// The Runnable will be executed after the given delay time
        h.postDelayed(r, 5000); // will be delayed for 1.5 seconds

    }
}
