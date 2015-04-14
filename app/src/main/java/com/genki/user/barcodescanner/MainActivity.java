package com.genki.user.barcodescanner;


import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;

import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;


public class MainActivity extends ActionBarActivity {
    static public Handler mhandler;

    public Context context;
    private boolean threadSwitch;
    private Thread checkThread;
    private TextView tv_record;
    private EditText ed;
    private Button btn_start, btn_stop,btn_camerascan;


    private HttpRequestToSMSServer requestToSMSServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findAllView();
        initActivity();
        setAllListener();



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //初始化Activity
    private void initActivity() {
        context=this;

        mhandler = new Handler(){
            @Override
            public void handleMessage(Message msg){

                //這裡執行收到伺服器回應後的動作

                tv_record.setText(tv_record.getText().toString()+"\r\n"
                                  +"姓名 :"+requestToSMSServer.getName()
                                  +"   時間 :"+requestToSMSServer.getArrived_at());

            }

        };

        ed.setText("");
        ed.setEnabled(false);
        btn_stop.setEnabled(false);
        threadSwitch = false;

    }

    private void findAllView() {
        ed = (EditText) findViewById(R.id.myedittext);
        btn_start = (Button) findViewById(R.id.btn_start);
        btn_stop = (Button) findViewById(R.id.btn_stop);
        btn_camerascan = (Button) findViewById(R.id.btn_toCameraScan);
        tv_record = (TextView) findViewById(R.id.tv_record);

    }

    private void setAllListener() {
        btn_start.setOnClickListener(start_btn_listener);
        btn_stop.setOnClickListener(stop_btn_listener);
        btn_camerascan.setOnClickListener(camerascan_btn_listener);

    }

    private String getEditTextString() {
        if (ed.getText().toString() == null)
            return "";
        return ed.getText().toString();

    }

    private boolean isFormal(String str) {
        if (str.length() != 7 || !(str.contains("CC"))) {
            //Log.d("BluetoothInput App==>","錯誤的輸入"+str);
            return false;
        } else
            return true;
    }

    private void outputAPI() {

         //HttpRequestToSMSServer為自建Class 傳入(Context , Handler , ScanCode)    Handler必須複寫handlemessge(msg);
        //收到來自伺服器的回應時，自動調用Handler.handleMessage();

        requestToSMSServer=new HttpRequestToSMSServer(this,mhandler,getEditTextString());
        requestToSMSServer.httpRequest();

        ed.setText("");
        ed.requestFocus();


    }

    private View.OnClickListener start_btn_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //starting entering
            Log.d("BluetoothInput App==>", "Start listening enter words");
            threadSwitch = true;
            ed.setEnabled(true);
            ed.requestFocus();
            btn_start.setEnabled(false);
            btn_stop.setEnabled(true);
            checkThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    Looper.prepare();
                    try {
                        do {

                            Thread.sleep(1000);
                            if (isFormal(getEditTextString()))
                                mhandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        outputAPI();
                                    }
                                });
                        } while (threadSwitch);

                        Log.d("BluetoothInput App==>", "Thread already Stop!!!");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            checkThread.start();


        }
    };
    private View.OnClickListener stop_btn_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //stopping entering
            Log.d("BluetoothInput App==>", "Stop listening");
            threadSwitch = false;
            btn_stop.setEnabled(false);
            btn_start.setEnabled(true);
            ed.setEnabled(false);
        }
    };
    private View.OnClickListener camerascan_btn_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
           Log.d("CameraBtnClick","YouClick!");
            //Intent i=new Intent();

            //Using zXingLibs  To  Scan barcode
            IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);
            integrator.initiateScan(IntentIntegrator.ALL_CODE_TYPES);
        }
    };


    @Override
    protected void onStop() {
        super.onStop();

        //確保Thread確保Thread不再繼續執行
        threadSwitch=false;
    }
}




