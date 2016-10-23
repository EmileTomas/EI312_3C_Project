package com.example.console.Activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.example.console.Adapter.ClickTabsPager;
import com.example.console.AsrDemo;
import com.example.console.Fragment.GestureFragment.GesturesFragment;
import com.example.console.IPAddressActivity;
import com.example.console.Pub;
import com.example.console.R;
import com.example.console.Fragment.SimpleFragment.SimpleFragment;
import com.example.console.VideoFragment;

public class MainActivity extends AppCompatActivity implements OnClickListener {
    //Hardware Connect
    private BluetoothAdapter mBluetoothAdapter = null;
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static final String MSPMAC = "98:D3:31:70:9F:C2";
    private BluetoothDevice mDevice;

    private SelectableButton bottom_btn_simple;
    private SelectableButton bottom_btn_gesture;
    private SelectableButton bottom_btn_video;

    private SimpleFragment simpleFrag;
    private GesturesFragment gestureFrag;
    private VideoFragment videoFrag;
    ClickTabsPager clickTabsPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        askPermision();
        initializeWidgets();
        initializeWidgetsClickListener();
        initializeClickTabsPager();

        clickTabsPager.setItemSelected(bottom_btn_simple.getId());
    }

    //Question: Is this a good way to splite one function like this into several functions?
    private void initializeWidgets(){
        bottom_btn_simple = (SelectableButton) findViewById(R.id.btn_simple);
        bottom_btn_gesture = (SelectableButton) findViewById(R.id.btn_gesture);
        bottom_btn_video = (SelectableButton) findViewById(R.id.btn_video);
        simpleFrag=new SimpleFragment();
        gestureFrag=new GesturesFragment();
        videoFrag=new VideoFragment();
    }


    private void initializeWidgetsClickListener(){
        bottom_btn_simple.setOnClickListener(this);
        bottom_btn_gesture.setOnClickListener(this);
        bottom_btn_video.setOnClickListener(this);
    }

    private void initializeClickTabsPager(){
        SelectableButtonList selectableButtons = new SelectableButtonList(bottom_btn_simple,bottom_btn_gesture,bottom_btn_video);
        selectableButtons.setSelectedColor(R.color.white).setUnselectedColor(R.color.sub_theme);
        initializeButtonImageSet();

        ArrayList<Fragment> fragments=new ArrayList<>();
        fragments.add(simpleFrag);
        fragments.add(gestureFrag);
        fragments.add(videoFrag);

        clickTabsPager=new ClickTabsPager(selectableButtons,fragments,getSupportFragmentManager(),R.id.content);
    }

    private void initializeButtonImageSet(){
        bottom_btn_simple.setSelectedImage(R.drawable.simplewhite)
                .setUnselected_image(R.drawable.simplegray);
        bottom_btn_gesture.setSelectedImage(R.drawable.gesturewhite)
                .setUnselected_image(R.drawable.gesturegray);
        bottom_btn_video.setSelectedImage(R.drawable.videowhite)
                .setUnselected_image(R.drawable.videogray);
    }


    @Override
    public void onClick(View view) {
        clickTabsPager.setItemSelected(view.getId());
    }

    protected void onDestroy() {
        super.onDestroy();
        Log.d("fuck", "Destroy");
        Pub.outStream = null;
        if (Pub.mSocket != null) {
            try {
                Pub.mSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Pub.mSocket = null;
        }
        Pub.mSocket = null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.bluetooth_connect) {
            Log.d("fuck", "start connect");
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if (mBluetoothAdapter == null) {
                Toast.makeText(this, "Bluetooth is not available.", Toast.LENGTH_SHORT).show();
                return true;
            }

            if (!mBluetoothAdapter.isEnabled()) {
                mBluetoothAdapter.enable();
                while (!mBluetoothAdapter.isEnabled()) ;
            }

            mDevice = mBluetoothAdapter.getRemoteDevice(MSPMAC);
            Log.d("fuck", "get remote device");
            try {
                Pub.mSocket = mDevice.createRfcommSocketToServiceRecord(MY_UUID);
            } catch (IOException e) {
                Toast.makeText(this, "Fail to create socket", Toast.LENGTH_SHORT).show();
            }
            mBluetoothAdapter.cancelDiscovery();

            try {
                Pub.mSocket.connect();
                Toast.makeText(this, "Connect success!", Toast.LENGTH_SHORT).show();
            } catch (IOException connectException) {
                try {
                    Toast.makeText(this, "Connect fail", Toast.LENGTH_SHORT).show();
                    Pub.mSocket.close();
                } catch (IOException closeException) {
                }
            }

            try {
                Pub.outStream = Pub.mSocket.getOutputStream();
            } catch (IOException e) {
                Toast.makeText(this, "Fail to get outStream", Toast.LENGTH_SHORT).show();
            }
//        	for(int i=0; i<5; ++i){
//        		Pub.sendMessage("udp");
//        	}
            return true;
        } else if (id == R.id.ip_address_set) {
            Intent ip_address_intent = new Intent(getBaseContext(), IPAddressActivity.class);
            Log.d("fuck", "start get IP");
            startActivity(ip_address_intent);
            return true;
        } else if (id == R.id.voice_test) {
            Intent voice_test = new Intent(getBaseContext(), AsrDemo.class);
            startActivity(voice_test);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public android.support.v4.app.FragmentManager getMainActivityFragmentManager() {
        return this.getSupportFragmentManager();
    }

    private void askPermision() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                //Explain?
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                } else {
                    ActivityCompat.requestPermissions(this, new String[]{
                            Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS,
                            Manifest.permission.READ_PHONE_STATE,
                            Manifest.permission.RECORD_AUDIO,
                            Manifest.permission.BLUETOOTH_PRIVILEGED,
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                    }, 10001);
                }
            }
        }
    }
}
