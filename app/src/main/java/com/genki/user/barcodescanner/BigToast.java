package com.genki.user.barcodescanner;

import android.app.Activity;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by User on 2015/4/17.
 */
public class BigToast extends Toast {

    private String name;
    private String arrived;
    private Context context;

    public BigToast(Context context, String name, String arrived) {
        super(context);
        this.context=context;
        this.name=name;
        this.arrived=arrived;
        initToastLayout();
    }
    private void initToastLayout(){

        LayoutInflater inflater = (LayoutInflater)
                context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.toast_bluetooth, (ViewGroup) ((Activity) context).findViewById(R.id.toast_bluetooth_root));
        //LinearLayout rootLinearComponent = (LinearLayout)view.findViewById(R.id.toast_bluetooth_root);
        TextView studentName = (TextView) view.findViewById(R.id.toast_bluetooth_name);
        TextView studentArrivedAt = (TextView) view.findViewById(R.id.toast_bluetooth_date);
        Display display = ((Activity)context).getWindowManager().getDefaultDisplay();
        int width = display.getWidth();  // deprecated
        int height = display.getHeight();  // deprecated
      // int screenWidth = rootLinearComponent.getWidth();


        if(name.length()>=4) {
            studentName.setWidth(width-16);
            studentName.setTextSize((width - 16) / 10);
        }
        studentName.setGravity(Gravity.CENTER_HORIZONTAL);
        //studentName.setText("測試軍君");
        studentName.setText(name);
        studentArrivedAt.setText(arrived);


        this.setView(view);
        this.setGravity(Gravity.TOP, 0, 80);
        this.setDuration(Toast.LENGTH_SHORT);

    }

}
