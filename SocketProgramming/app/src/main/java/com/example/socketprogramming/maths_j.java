package com.example.socketprogramming;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Random;

public class maths_j extends AppCompatActivity {

    EditText ip;
    public int SERVER_PORT = 6000;
    Socket socket;
    public String SERVER_IP = "YOUR_SERVER_IP";
    Button connect;
    DataInputStream dis;
    DataOutputStream dos;
    Thread r;
    TextView ques;
    TextView op[]=new TextView[4];
    boolean isanswered=false;
    TextView textTimer;
    int count1=0,count2=0,answer,total;
    ProgressBar player1,player2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maths_j);
        setTitle("Join");

        player1=findViewById(R.id.player11);
        player2=findViewById(R.id.player21);
       ip=findViewById(R.id.ip);




        ques=findViewById(R.id.ques);
        op[0]=findViewById(R.id.op1);
        op[1]=findViewById(R.id.op2);
        op[2]=findViewById(R.id.op3);
        op[3]=findViewById(R.id.op4);
        textTimer= (TextView) findViewById(R.id.timer);
        connect=findViewById(R.id.connect);
        for (int i = 0; i < 4; i++) {
            op[i].setVisibility(View.INVISIBLE);
        }


        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            socket = new Socket(ip.getText().toString(), SERVER_PORT);
                            dis = new DataInputStream(socket.getInputStream());
                            dos = new DataOutputStream(socket.getOutputStream());
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ScrollView sv=findViewById(R.id.sv5);
                                    sv.setVisibility(View.VISIBLE);
                                    connect.setVisibility(View.INVISIBLE);
                                    ip.setVisibility(View.INVISIBLE);
                                    ques.setText("Joined...");
                                    player1.setVisibility(View.VISIBLE);
                                    player2.setVisibility(View.VISIBLE);
                                }
                            });

                            r = new reading(socket, dis, dos);
                            r.start();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    }
                }).start();

            }
        });

        for(int i=0;i<4;i++){
            op[i].setVisibility(View.INVISIBLE);
            final int finalI = i;
            op[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Integer.valueOf(op[finalI].getText().toString()) == answer) {
                        textTimer.setText("Correct !");
                        count1++;
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    dos.writeUTF("win");dos.flush();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                    } else {
                        count2++;
                        textTimer.setText("Wrong !");
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    dos.writeUTF("lose");dos.flush();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                    }

                    player1.setProgress(count1*10);
                    player2.setProgress(count2*10);


                    for (int i = 0; i < 4; i++) {
                        op[i].setVisibility(View.INVISIBLE);
                    }

                    check();
                }
            });
        }
    }


    class reading extends  Thread{

        private final Socket socket;
        DataOutputStream dos;
        DataInputStream dis;

        public reading(Socket socket, DataInputStream dis, DataOutputStream dos) {
            this.socket=socket;
            this.dis=dis;
            this.dos=dos;
        }

        @Override
        public void run() {

            while(!socket.isClosed()){
                try {
                    String reading = dis.readUTF();
                    if (reading.equals("ques")) {
                        final String question = dis.readUTF();
                        final String op1 = dis.readUTF();
                        final String op2 = dis.readUTF();
                        final String op3 = dis.readUTF();
                        final String op4 = dis.readUTF();
                        final String ans = dis.readUTF();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                for (int i = 0; i < 4; i++) {
                                    op[i].setVisibility(View.VISIBLE);
                                }

                                Random r = new Random();
                                int a = 0, b = 0, c = 0, d = 0;
                                while (a == b || b == c || c == d || d == a || a == c || b == d) {
                                    a = r.nextInt(4);
                                    b = r.nextInt(4);
                                    c = r.nextInt(4);
                                    d = r.nextInt(4);
                                }
                                answer = Integer.valueOf(ans);
                                ques.setText(question);
                                op[a].setText(op1);
                                op[b].setText(op2);
                                op[c].setText(op3);
                                op[d].setText(op4);

                            }
                        });
                    }

                    if (reading.equals("win")) {

                        count2++;
                        player2.setProgress(count2*10);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                for (int i = 0; i < 4; i++) {
                                    op[i].setVisibility(View.INVISIBLE);
                                }

                                textTimer.setText("You Lose !");
                                check();
                            }
                        });

                    }
                    if (reading.equals("lose")) {


                        count1++;
                        player1.setProgress(count1*10);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                for (int i = 0; i < 4; i++) {
                                    op[i].setVisibility(View.INVISIBLE);
                                }
                                textTimer.setText("You Win !");
                                check();
                            }
                        });


                    }
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    void check(){
        if(player1.getProgress()>=100 || count1>=10){
            ques.setText("You Win !\nRestart in 10 seconds");
            textTimer.setText("");
            for(int i=0;i<4;i++){
                op[i].setVisibility(View.INVISIBLE);
            }
            player1.setProgress(0);
            player2.setProgress(0);
            count1=0;
            count2=0;
        }
        else if(player2.getProgress()>=100 || count2>=10){
            ques.setText("You Lose !\nRestart in 10 seconds");
            textTimer.setText("");
            for(int i=0;i<4;i++){
                op[i].setVisibility(View.INVISIBLE);
            }
            player1.setProgress(0);
            player2.setProgress(0);
            count1=0;
            count2=0;
        }

    }

    @Override
    public void onBackPressed() {

        Exit();

    }
    public void Exit() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Do you want to Exit?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    if(dos!=null && dis!=null ){
                        new Thread(new Runnable() {
                            public void run() {
                                try {
                                    dos.writeUTF("-121");dos.flush();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();

                        dis.close();
                        dos.close();
                    }
                    if(socket!=null){
                        socket.close();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                finish();

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user select "No", just cancel this dialog and continue with app
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }


}
