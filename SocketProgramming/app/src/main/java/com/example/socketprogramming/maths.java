package com.example.socketprogramming;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

public class maths extends AppCompatActivity {

    TextView ques;
    TextView op[]=new TextView[4];
    boolean isanswered=false;
    TextView textTimer;
    int time;
    CountDownTimer t;
    int count=0,answer,total;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maths);

        ques=findViewById(R.id.ques);
        op[0]=findViewById(R.id.op1);
        op[1]=findViewById(R.id.op2);
        op[2]=findViewById(R.id.op3);
        op[3]=findViewById(R.id.op4);
        textTimer= (TextView) findViewById(R.id.timer);



        for(int i=0;i<4;i++){
            op[i].setVisibility(View.INVISIBLE);
            final int finalI = i;
            op[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Integer.valueOf(op[finalI].getText().toString()) == answer) {
                        total++;
                        textTimer.setText("Correct !");
                    } else {
                        textTimer.setText("Wrong !");
                    }

                    isanswered = true;
                    for (int i = 0; i < 4; i++) {
                        op[i].setVisibility(View.INVISIBLE);
                    }
                    t.cancel();
                   new CountDownTimer(2000, 1000) {


                       @Override
                       public void onTick(long millisUntilFinished) {

                       }

                       public void onFinish() {
                            isanswered = false;
                            if (count <= 10)
                                setques();
                            else {
                                textTimer.setText(total + "\nPoints !");
                            }

                        }

                    }.start();
                }
            });
        }
        setques();


    }

    void setques(){
count++;
        for(int i=0;i<4;i++){
            op[i].setText("");
            op[i].setVisibility(View.VISIBLE);
        }
        Random r=new Random();
        int x=r.nextInt(999)+1;
        int y=r.nextInt(9)+1;
        ques.setText(String.valueOf(x)+'*'+String.valueOf(y) + " = ?");
        int a=0,b=0,c=0,d=0;
        while(a==b || b==c || c==d || d==a ||a==c || b==d)
        {
            a=r.nextInt(4);
            b=r.nextInt(4);
            c=r.nextInt(4);
            d=r.nextInt(4);
        }

        answer=x*y;
        op[a].setText(String.valueOf(x*y));
        op[b].setText(String.valueOf(x*y+x+10));
        op[c].setText(String.valueOf(x*y+y+(x*y)%9));
        op[d].setText(String.valueOf((x*y)+(x%10*10)));
        time=5;
        timer();
    }


    void timer() {


        t= new CountDownTimer(7000, 1000) {

            public void onTick(long millisUntilFinished) {
                if (time >= 1 && !isanswered) {
                    textTimer.setText(checkDigit(time % 10));

                } else {
                    if (!isanswered) {
                        textTimer.setText("Time UP");

                        for (int i = 0; i < 4; i++) {
                            op[i].setVisibility(View.INVISIBLE);
                        }
                    }
                }
                time--;
            }

            public void onFinish() {
                isanswered = false;
                if (count <= 10)
                    setques();
                else {
                    textTimer.setText(total + "\nPoints !");
                }

            }

        };
        t.start();





    }
    public String checkDigit ( int number){
        return number <= 9 ? "0" + number : String.valueOf(number);
    }
}
