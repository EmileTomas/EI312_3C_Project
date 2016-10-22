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

import com.example.console.AsrDemo;
import com.example.console.GesturesFragment;
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

    private BottomButton bottom_btn_simple;
    private BottomButton bottom_btn_gesture;
    private BottomButton bottom_btn_video;

    private SimpleFragment simpleFrag;
    private GesturesFragment gestureFrag;
    private VideoFragment videoFrag;
    
    private final int SIMPLE_FRAGMENT = 0;
    private final int GESTURES_FRAGMENT = 1;
    private final int VIDEO_FRAGMENT = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        askPermision();
        initializeWidgets();
        initializeWidgetsClickListener();

        showFragment(SIMPLE_FRAGMENT);
    }

    //Question: Is this a good way to splite one function like this into several functions?
    private void initializeWidgets(){
        bottom_btn_simple = (BottomButton) findViewById(R.id.btn_simple);
        bottom_btn_gesture = (BottomButton) findViewById(R.id.btn_gesture);
        bottom_btn_video = (BottomButton) findViewById(R.id.btn_video);

        initializeButtonImageSet();
    }

    private void initializeButtonImageSet(){
        bottom_btn_simple.setSelectedImage(R.drawable.simplewhite)
                .setUnselected_image(R.drawable.simplegray);
        bottom_btn_gesture.setSelectedImage(R.drawable.gesturewhite)
                .setUnselected_image(R.drawable.gesturegray);
        bottom_btn_video.setSelectedImage(R.drawable.videowhite)
                .setUnselected_image(R.drawable.videogray);
    }

    private void initializeWidgetsClickListener(){
        bottom_btn_simple.setOnClickListener(this);
        bottom_btn_gesture.setOnClickListener(this);
        bottom_btn_video.setOnClickListener(this);
    }

    private void showFragment(int index) {
        ArrayList<BottomButton> bottomButtons=getArrayList(bottom_btn_simple,bottom_btn_gesture,bottom_btn_video);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        hideFragement(fragmentTransaction);
        switch (index) {
            case SIMPLE_FRAGMENT:
                selectButton(bottomButtons,bottom_btn_simple.getId());
                if (simpleFrag == null) {
                    simpleFrag = new SimpleFragment();
                    fragmentTransaction.add(R.id.content, simpleFrag);
                } else
                    fragmentTransaction.show(simpleFrag);
                break;
            case GESTURES_FRAGMENT:
                selectButton(bottomButtons,bottom_btn_gesture.getId());
                if (gestureFrag == null) {
                    gestureFrag = new GesturesFragment();
                    fragmentTransaction.add(R.id.content, gestureFrag);
                } else
                    fragmentTransaction.show(gestureFrag);
                break;
            case VIDEO_FRAGMENT:
                selectButton(bottomButtons,bottom_btn_video.getId());
                if (videoFrag == null) {
                    videoFrag = new VideoFragment();
                    fragmentTransaction.add(R.id.content, videoFrag);
                } else
                    fragmentTransaction.show(videoFrag);
                break;
        }
        fragmentTransaction.commit();
    }

    private <T> ArrayList<T> getArrayList(T... elements){
        ArrayList<T> arrayList=new ArrayList<>();
        for(T element: elements){
            arrayList.add(element);
        }
        return arrayList;
    }


    private void selectButton(ArrayList<BottomButton> bottomButtons,int id){
        for(BottomButton element: bottomButtons){
            if( element.getId()==id)
                element.setSelected();
            else
                element.setUnselected();
        }
    }

    private void hideFragement(FragmentTransaction fragmentTransaction) {
        if (simpleFrag != null)
            fragmentTransaction.hide(simpleFrag);
        if (gestureFrag != null)
            fragmentTransaction.hide(gestureFrag);
        if (videoFrag != null)
            fragmentTransaction.hide(videoFrag);
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_simple:
                showFragment(SIMPLE_FRAGMENT);
                break;
            case R.id.btn_gesture:
                showFragment(GESTURES_FRAGMENT);
                break;
            case R.id.btn_video:
                showFragment(VIDEO_FRAGMENT);
                break;
        }
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
