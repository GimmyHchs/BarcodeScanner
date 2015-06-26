package com.google.zxing;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/*
   Class Extends Volley Class

   Need Volley libs

   Original depend to http://nov.mynet.com.tw:9095/notify

   Constructor Use Parameters : Context from activity ,Handler from activity(must override handleMessage) , string for url extension

   HCHS from Genki Design Solutions     www.mynet.com.tw


 */

public class HttpRequestToSMSServer extends Volley {
//    private String url;
    private Context context;
    private String str_scan;
    private String name="";
    private String arrived_at="";
    private Handler handler;
    static String SMS_SERVER_INFOADDRESS="http://s1.send2me.cc/mobilejson?";
    static  String SMS_SERVER_ADDRESS="http://s1.send2me.cc/mobile?";


    public HttpRequestToSMSServer(Context context, Handler handler, String str_scan){

        this.context=context;
        this.handler=handler;
        this.str_scan=str_scan;
    }

    public void httpRequest() {

        RequestQueue queue = Volley.newRequestQueue(context);
        String url =SMS_SERVER_INFOADDRESS+"barcode="+str_scan;
        // Request a string response from the provided URL.

        StringRequest stringRequest = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.i("Success", "Response is: " + response.toString());

                        try {
                            name = new JSONObject(response.toString()).getString("name");
                            arrived_at = new JSONObject(response.toString()).getString("arrived_at");
                            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            Date date = new Date(System.currentTimeMillis());
                            arrived_at = dateFormat.format(date);

                            //傳送MessageH，觸發handler
                            //Toast.makeText(context,name,Toast.LENGTH_SHORT).show();
                            handler.sendMessage(new Message());


                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                3000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);

        String send_url =SMS_SERVER_ADDRESS+"barcode="+str_scan;
        StringRequest sendRequest = new StringRequest(send_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.i("Success", "Response is: " + response.toString());

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
            }
        });
        sendRequest.setRetryPolicy(new DefaultRetryPolicy(
                3000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(sendRequest);

    }
    public String getName(){

        if(this.name.length()>=1)
            return this.name;
        else
            return "null";

    }

    public String getArrived_at(){

        if(this.arrived_at.length()>=1)
            return this.arrived_at;
        else
            return "null";

    }
}
