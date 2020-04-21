package com.example.socketprogramming;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Random;

public class maths_h extends AppCompatActivity {

    TextView ques;
    TextView op[]=new TextView[4];
    boolean isanswered=true;
    TextView textTimer;
    int count1=0,count2=0,answer,total;

    public static String SERVER_IP = "";
    public static final int SERVER_PORT = 6000;

    ServerSocket serverSocket;
    Socket socket;
    DataInputStream dis ;
    DataOutputStream dos;
    Thread rthread;
    ProgressBar player1,player2;
    private boolean isrun=true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maths_h);


        player1=findViewById(R.id.player11);
        player2=findViewById(R.id.player21);


        ques=findViewById(R.id.ques);
        op[0]=findViewById(R.id.op1);
        op[1]=findViewById(R.id.op2);
        op[2]=findViewById(R.id.op3);
        op[3]=findViewById(R.id.op4);
        textTimer= (TextView) findViewById(R.id.timer);

        try {
            ques.setText(ques.getText()+getLocalIpAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }


        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    for (int i = 0; i < 4; i++) {
                        op[i].setVisibility(View.INVISIBLE);
                    }
                    serverSocket = new ServerSocket(SERVER_PORT);
                    socket = serverSocket.accept();
                    dis = new DataInputStream(socket.getInputStream());
                    dos = new DataOutputStream(socket.getOutputStream()) ;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ques.setText("Player 1 joined ...");
                            player1.setVisibility(View.VISIBLE);
                            player2.setVisibility(View.VISIBLE);

                            Runnable go=new Runnable() {
                                @Override
                                public void run() {
                                    setques();
                                }
                            };
                            Handler h = new Handler();

                            h.postDelayed(go, 2000);

                        }
                    });
                    rthread=new read(socket,dis,dos);
                    rthread.start();

                    rthread.interrupt();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();

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

                    Runnable go=new Runnable() {
                        @Override
                        public void run() {
                            setques();
                        }
                    };
                    Handler h = new Handler();

                    h.postDelayed(go, 2000);

                    check();
                }
            });
        }
    }


    void setques(){

        if(isanswered) {
            Random r = new Random();
            final int x = r.nextInt(999) + 1;
            final int y = r.nextInt(9) + 1;
            ques.setText(String.valueOf(x) + '*' + String.valueOf(y) + " = ?");
            int a = 0, b = 0, c = 0, d = 0;
            while (a == b || b == c || c == d || d == a || a == c || b == d) {
                a = r.nextInt(4);
                b = r.nextInt(4);
                c = r.nextInt(4);
                d = r.nextInt(4);
            }

            answer = x * y;
            op[a].setText(String.valueOf(x * y));
            op[b].setText(String.valueOf(x * y + x + 10));
            op[c].setText(String.valueOf(x * y + y + (x * y) % 9));
            op[d].setText(String.valueOf((x * y) + (x % 10 * 10)));

            final int finalA = a;
            final int finalB = b;
            final int finalC = c;
            final int finalD = d;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {

                        dos.writeUTF("ques");
                        dos.flush();

                        dos.writeUTF(String.valueOf(x) + '*' + String.valueOf(y) + " = ?");
                        dos.flush();

                        dos.writeUTF(op[finalA].getText().toString());
                        dos.flush();

                        dos.writeUTF(op[finalB].getText().toString());
                        dos.flush();

                        dos.writeUTF(op[finalC].getText().toString());
                        dos.flush();

                        dos.writeUTF(op[finalD].getText().toString());
                        dos.flush();

                        dos.writeUTF(String.valueOf(answer));
                        dos.flush();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            for (int i = 0; i < 4; i++) {
                                op[i].setVisibility(View.VISIBLE);
                            }


                        }
                    });
                }
            }).start();

        }
    }



    @Override
    public void onBackPressed() {

        Exit();

    }
    class read extends  Thread{

        private final Socket socket;
        DataOutputStream dos;
        DataInputStream dis;

        public read(Socket socket, DataInputStream dis, DataOutputStream dos) {
            this.socket=socket;
            this.dis=dis;
            this.dos=dos;
        }

        @Override
        public void run() {


            while(!socket.isClosed()){
                if(isrun) {
                    try {
                        String reading = dis.readUTF();



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
                                    Runnable go=new Runnable() {
                                        @Override
                                        public void run() {
                                            setques();
                                        }
                                    };
                                    Handler h = new Handler();

                                    h.postDelayed(go, 2000);

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
                                    Runnable go=new Runnable() {
                                        @Override
                                        public void run() {
                                            setques();
                                        }
                                    };
                                    Handler h = new Handler();

                                    h.postDelayed(go, 2000);

                                    check();
                                }
                            });

                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    private String getLocalIpAddress() throws UnknownHostException {
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        assert wifiManager != null;
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipInt = wifiInfo.getIpAddress();
        return InetAddress.getByAddress(ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(ipInt).array()).getHostAddress();
    }


    void check(){
        if(player1.getProgress()>=100 || count1>=10){
            isanswered=false;
            ques.setText("You Win !\nRestart in 10 seconds");
            textTimer.setText("Game Over !");
            for(int i=0;i<4;i++){
                op[i].setVisibility(View.INVISIBLE);
            }
            restart();
        }
        else if(player2.getProgress()>=100 || count2>=10){
            isanswered=false;
            ques.setText("You Lose !\nRestart in 10 seconds");
            textTimer.setText("Game Over !");
            for(int i=0;i<4;i++){
                op[i].setVisibility(View.INVISIBLE);
            }
            restart();
        }

    }

    void restart(){
        Handler h = new Handler();

        Runnable qq=new Runnable() {
            @Override
            public void run() {
                isanswered=true;
                textTimer.setText("new game");
                player1.setProgress(0);
                player2.setProgress(0);
                count1=0;
                count2=0;
                setques();
            }
        };
        h.postDelayed(qq, 10000);

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