package com.genki.user.barcodescanner;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.ActionBarDrawerToggle;

import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class NavigationDrawerActivity extends ActionBarActivity {

    private DrawerLayout layDrawer;
    private ListView lstDrawer;

    private ActionBarDrawerToggle drawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;


    private String[] drawer_menu=new String[]{"Home","Bluetooth","Camera"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.navigetiondraweractivity_main);

        initActionBar();
        initDrawer();
        initDrawerList();
        initHomeFragment();
        findAllView();//unused function
      //  initActivity();
      //  setAllListener();


    }
    private void initActionBar(){
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("BarcodeScanner");
    }
    private void initDrawer(){
        setContentView(R.layout.navigetiondraweractivity_main);

        layDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        lstDrawer = (ListView) findViewById(R.id.left_drawer);

        layDrawer.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        mTitle = mDrawerTitle = getSupportActionBar().getTitle();
        drawerToggle = new ActionBarDrawerToggle(
                this,
                layDrawer,
                R.drawable.ic_drawer,
                R.string.drawer_open,
                R.string.drawer_close) {

            @Override
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mTitle);
                //Toast.makeText(getApplicationContext(),"Drawer Closed",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(mDrawerTitle);
                //Toast.makeText(getApplicationContext(),"Drawer Open",Toast.LENGTH_SHORT).show();
            }
        };
        drawerToggle.syncState();

        layDrawer.setDrawerListener(drawerToggle);

    }
    private void initDrawerList(){
        String[] drawer_menu = this.getResources().getStringArray(R.array.drawer_menu);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.drawer_list_item, drawer_menu);
        lstDrawer.setAdapter(adapter);
        lstDrawer.setOnItemClickListener(new DrawerItemClickListener());
    }
    private void initHomeFragment(){

        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,new FragmentHome()).commit();

    }
    private void selectItem(int position) {
        Fragment fragment=null;

        switch (position) {
            case 0:
                //Toast.makeText(getApplicationContext(),"Home Click",Toast.LENGTH_SHORT).show();
                fragment = new FragmentHome();

                break;

            case 1:
                //Toast.makeText(getApplicationContext(),"Bluetooth Click",Toast.LENGTH_SHORT).show();
                fragment = new FragmentBluetooth();
                break;

            case 2:
                //Toast.makeText(getApplicationContext(),"Camera Click",Toast.LENGTH_SHORT).show();
                fragment=new FragmentCamera();
                break;

            default:
                //還沒製作的選項，fragment 是 null，直接返回
                return;
        }
        if(fragment!=null){
            replaceContentView(fragment);
        }
        // 更新被選擇項目，換標題文字，關閉選單
        lstDrawer.setItemChecked(position, true);
        setTitle(drawer_menu[position]);
        layDrawer.closeDrawer(lstDrawer);
    }
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }
    private void findAllView(){

    }
    private void replaceToHomePage(){

    }
    private void replaceToBluetoothPage(){

    }
    private void replaceToCameraPage(){

    }
    private void replaceContentView(Fragment fragment){
        FragmentManager fragmentManager=getSupportFragmentManager();
        fragmentManager.beginTransaction().addToBackStack(null).replace(R.id.content_frame, fragment).commit();
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

        //左上角 home 點擊跳出Draw
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        int fragmentBackCount=getSupportFragmentManager().getBackStackEntryCount();
        Log.d("BackStack=",""+fragmentBackCount);
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0 && fragmentBackCount==0 ) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("確認退出嗎?");
            builder.setTitle("提示");
            builder.setNegativeButton("確認", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    NavigationDrawerActivity.this.finish();

                }
            });
            builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();

                }
            });
            builder.create().show();
         }
        else
            getSupportFragmentManager().popBackStack();   //FragmentStack裡面有頁面時，POP出來
        return true;
    }


}
