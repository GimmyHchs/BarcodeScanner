package com.genki.user.barcodescanner;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
//import android.support.v7.app.ActionBarActivity;


public class FragmentHome extends Fragment {
    private ImageView iv_bluetooth;
    private ImageView iv_camera;
    private TextView tv_bluetooth_large,tv_bluetooth_small;
    private TextView tv_camera_large,tv_camera_small;
    private RelativeLayout rl_bluetooth_background;
    private RelativeLayout rl_camera_background;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



        findAllView();
        setAllListener();


    }
    private void findAllView(){

        iv_bluetooth=(ImageView)getActivity().findViewById(R.id.iv_fragment_home_bluetooth);
        iv_camera=(ImageView)getActivity().findViewById(R.id.iv_fragment_home_camera);
        tv_bluetooth_large = (TextView) getActivity().findViewById(R.id.tv_fragment_home_bluetooth_large);
        tv_bluetooth_small = (TextView) getActivity().findViewById(R.id.tv_fragment_home_bluetooth_small);
        tv_camera_large = (TextView) getActivity().findViewById(R.id.tv_fragment_home_camera_large);
        tv_camera_small = (TextView) getActivity().findViewById(R.id.tv_fragment_home_camera_small);
        rl_bluetooth_background=(RelativeLayout)getActivity().findViewById(R.id.rl_fragment_home_bluetooth_background);
        rl_camera_background=(RelativeLayout)getActivity().findViewById(R.id.rl_fragment_home_camera_background);

    }
    private void setAllListener(){

        iv_bluetooth.setOnClickListener(replaceToBluetooth);
        iv_camera.setOnClickListener(replaceToCamera);
        rl_bluetooth_background.setOnClickListener(replaceToBluetooth);
        rl_camera_background.setOnClickListener(replaceToCamera);

    }


    private View.OnClickListener replaceToBluetooth = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction().addToBackStack(null).replace(R.id.content_frame,new FragmentBluetooth()).commit();
        }
    };
    private View.OnClickListener replaceToCamera = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction().addToBackStack(null).replace(R.id.content_frame,new FragmentCamera()).commit();
        }
    };
}
