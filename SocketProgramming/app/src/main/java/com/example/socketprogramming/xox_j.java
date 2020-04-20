package com.example.socketprogramming;


import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class xox_j extends AppCompatActivity {

    public int SERVER_PORT = 6000;
    Socket socket;
    public String SERVER_IP = "YOUR_SERVER_IP";
    TextView viewmessage;
    Button connect;
    DataInputStream dis;
    DataOutputStream dos;
    Thread r;


    ImageView[] img = new ImageView[9];
    TextView win;
    int[] xox = new int[9];
    boolean[] sel = new boolean[9];
    private int yours = 1;
    int n;
    int toggle = 1;
    int count = 0;
    String val;
    TextView  result;
    EditText ip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xox_j);

        setTitle("Join");

        viewmessage = findViewById(R.id.message);
        connect = findViewById(R.id.connect);
        result=findViewById(R.id.result);
        ip=findViewById(R.id.ip1);

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
                    if (sel[finalI] != true && toggle==-1) {
                        count++;
                        sel[finalI] = true;
                        xox[finalI] = toggle;
                        new Thread(new Runnable() {
                            public void run() {
                                try {
                                    dos.writeUTF(String.valueOf(finalI));
                                    dos.flush();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();

                        img[finalI].setImageResource(R.drawable.zero);

                        toggle=toggle*-1;
                        result.setText("Player 1's turn !");
                        check(xox);
                    }
                }
            });
        }

        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result.setText("Not connected.");
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
                                    result.setText("Joined...");
                                    connect.setVisibility(View.INVISIBLE);
                                    ip.setVisibility(View.INVISIBLE);
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

            for (int i = 0; i < 9; i++) {
                img[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(xox_j.this, "Game Over !!!", Toast.LENGTH_LONG).show();
                    }
                });
            }
            if (n == -1) {
                result.setText("You Won !\nRestart in 5 seconds");
            } else {
                result.setText("You Lost !\nRestart in 5 seconds");
            }
            new1();
        } else {
            if (count == 9) {
                for (int i = 0; i < 9; i++) {
                    img[i].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(xox_j.this, "Game Over !!!", Toast.LENGTH_LONG).show();
                        }
                    });
                }

                result.setText("Match Draw !\nRestart in 5 seconds");

            new1();

            }


        }

    }

    void new1(){
        Runnable r = new Runnable() {
            @Override
            public void run() {
                result.setText("Player 1's turn !");
                for(int i=0;i<9;i++){
                    sel[i]=false;
                    xox[i]=i*2;
                    toggle=1;
                    img[i].setImageDrawable(null);
                    img[i].setBackgroundResource(R.drawable.customborder);
                }
                for (int i = 0; i < 9; i++) {
                    sel[i] = false;
                    xox[i] = i * 7;
                    final int finalI = i;

                    img[i].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (sel[finalI] != true && toggle==-1) {
                                count++;
                                sel[finalI] = true;
                                xox[finalI] = toggle;
                                new Thread(new Runnable() {
                                    public void run() {
                                        try {
                                            dos.writeUTF(String.valueOf(finalI));
                                            dos.flush();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }).start();

                                img[finalI].setImageResource(R.drawable.zero);

                                toggle=toggle*-1;
                                result.setText("Player 1's turn !");
                                check(xox);
                            }
                        }
                    });
                }
            }
        };

        Handler h = new Handler();

        h.postDelayed(r, 5000);

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

            while(true){
                try {

                       final int n = Integer.valueOf(dis.readUTF());
                    if(n!=-121) {
                       runOnUiThread(new Runnable() {
                           @Override
                           public void run() {
                               img[n].setImageResource(R.drawable.x);
                               sel[n] = true;
                               xox[n] = 1;
                               toggle = toggle * -1;
                               result.setText("It's your turn !");
                               count++;
                               check(xox);
                           }
                       });
                   }
                   else{
                       runOnUiThread(new Runnable() {
                           @Override
                           public void run() {
                               result.setText("Player Left !");
                           }
                       });
                       if(dos!=null && dis!=null)
                       {
                           dos.close();
                           dis.close();
                       }
                       if(socket!=null){
                           socket.close();
                       }

                   }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
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


