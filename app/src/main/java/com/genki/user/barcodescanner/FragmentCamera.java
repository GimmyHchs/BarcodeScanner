package com.genki.user.barcodescanner;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;


public class FragmentCamera extends Fragment {
    private TextView tv_test;
    private Button btn_start;



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_camera,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        findAllView();
        initFragment();
        setAllListener();


    }
    private void findAllView(){

        tv_test = (TextView) getActivity().findViewById(R.id.tv_fragment_camera_test);
        btn_start = (Button) getActivity().findViewById(R.id.btn_fragment_camera_start);

    }
    private void initFragment(){


    }
    private void setAllListener(){

        btn_start.setOnClickListener(btn_start_listener);

    }
    private View.OnClickListener btn_start_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            IntentIntegrator integrator = new IntentIntegrator(getActivity());
            integrator.initiateScan(IntentIntegrator.ALL_CODE_TYPES);
        }
    };

}
