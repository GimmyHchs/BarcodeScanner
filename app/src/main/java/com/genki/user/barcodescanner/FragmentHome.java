package com.genki.user.barcodescanner;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by User on 2015/4/13.
 */
public class FragmentHome extends Fragment {
    private ImageView iv_bluetooth;
    private ImageView iv_camera;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TextView tv=(TextView) getView().findViewById(R.id.tv_fragment_home_test);
        tv.setText("Home program +"+tv.getText().toString());
        iv_bluetooth=(ImageView)getActivity().findViewById(R.id.iv_fragment_home_bluetooth);
        iv_bluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame,new FragmentBluetooth()).commit();
            }
        });

        iv_camera=(ImageView)getActivity().findViewById(R.id.iv_fragment_home_camera);
        iv_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame,new FragmentCamera()).commit();
            }
        });

    }
}
