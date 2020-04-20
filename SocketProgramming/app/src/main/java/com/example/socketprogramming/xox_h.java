package com.example.socketprogramming;



import android.content.DialogInterface;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;


public class xox_h extends AppCompatActivity {

    public static String SERVER_IP = "";
    public static final int SERVER_PORT = 6000;

    ServerSocket serverSocket;
    Socket socket;
    DataInputStream dis ;
    DataOutputStream dos;
    Thread r;
    ImageView[] img = new ImageView[9];
    int[] xox = new int[9];
    boolean[] sel = new boolean[9];
    private int yours = 1;
    int n;
    int toggle = 1;
    int count = 0;
    String val;
    TextView  result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xox_h);
        setTitle("Host");
        result=findViewById(R.id.result);
        img[0] = findViewById(R.id.a1);
        img[1] = findViewById(R.id.a2);
        img[2] = findViewById(R.id.a3);
        img[3] = findViewById(R.id.a4);
        img[4] = findViewById(R.id.a5);
        img[5] = findViewById(R.id.a6);
        img[6] = findViewById(R.id.a7);
        img[7] = findViewById(R.id.a8);
        img[8] = findViewById(R.id.a9);

        try {
            SERVER_IP = getLocalIpAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }



        result.setText("Waiting for player ...");
        try {
            result.setText(result.getText()+getLocalIpAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 9; i++) {
            img[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(xox_h.this, "Game Not Started !!!", Toast.LENGTH_LONG).show();
                }
            });
        }
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {

                    serverSocket = new ServerSocket(SERVER_PORT);
                    socket = serverSocket.accept();
                    dis = new DataInputStream(socket.getInputStream());
                    dos = new DataOutputStream(socket.getOutputStream()) ;
                   runOnUiThread(new Runnable() {
                       @Override
                       public void run() {
                           result.setText("Player 1 joined ...\nStarts in 5 seconds...");
                           new1();
                       }
                   });
                    r=new read(socket,dis,dos);
                    r.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();


    }

    @Override
    public void onBackPressed() {

            Exit();

    }


    private String getLocalIpAddress() throws UnknownHostException {
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        assert wifiManager != null;
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipInt = wifiInfo.getIpAddress();
        return InetAddress.getByAddress(ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(ipInt).array()).getHostAddress();
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
                        Toast.makeText(xox_h.this, "Game Over !!!", Toast.LENGTH_LONG).show();
                    }
                });
            }
            if (n == 1) {
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
                            Toast.makeText(xox_h.this, "Game Over !!!", Toast.LENGTH_LONG).show();
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
                result.setText("It's your turn !");
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
                            if (sel[finalI] != true && toggle==1) {
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

                                img[finalI].setImageResource(R.drawable.x);

                                toggle=toggle*-1;
                                result.setText("Player 2's turn !");
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


            while(true){
                try {
                    final int n=Integer.valueOf(dis.readUTF());

                    if(n!=-121) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                img[n].setImageResource(R.drawable.zero);
                                sel[n] = true;
                                xox[n] = -1;
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