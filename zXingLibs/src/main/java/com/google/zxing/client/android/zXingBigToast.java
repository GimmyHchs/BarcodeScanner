package com.google.zxing.client.android;

import android.app.Activity;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


public class zXingBigToast extends Toast {

    private String name;
    private String arrived;
    private Context context;

    public zXingBigToast(Context context, String name, String arrived) {
        super(context);
        this.context=context;
        this.name=name;
        this.arrived=arrived;
        initToastLayout();
    }
    private void initToastLayout(){

        LayoutInflater inflater = (LayoutInflater)
                context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.toast_layout, (ViewGroup) ((Activity) context).findViewById(R.id.toast_layout_root));
        //LinearLayout rootLinearComponent = (LinearLayout)view.findViewById(R.id.toast_bluetooth_root);
        TextView studentName = (TextView) view.findViewById(R.id.name);
        TextView studentArrivedAt = (TextView) view.findViewById(R.id.date);
        Display display = ((Activity)context).getWindowManager().getDefaultDisplay();
        int width = display.getWidth();  // deprecated

      // int screenWidth = rootLinearComponent.getWidth();



        if(name.length()>=4) {
            studentName.setWidth(width-16);
            studentName.setTextSize((width - 16) / 11);
        }
        studentName.setGravity(Gravity.CENTER_HORIZONTAL);
        //studentName.setText("測試軍君");
        studentName.setText(name);
        studentArrivedAt.setText(arrived);


        this.setView(view);
        this.setGravity(Gravity.TOP, 0, 0);
        this.setDuration(Toast.LENGTH_SHORT);

    }

}
