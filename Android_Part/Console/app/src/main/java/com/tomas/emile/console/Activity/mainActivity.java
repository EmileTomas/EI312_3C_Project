package com.tomas.emile.console.Activity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.tomas.emile.console.R;

import java.util.UUID;

/**
 * Created by Administrator on 2016/10/21.
 */
public class mainActivity extends AppCompatActivity {
    //Connect Info
    private BluetoothAdapter mBluetoothAdapter = null;
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static final String MSP430_MAC = "98:D3:31:70:9F:C2";

    // Widgets
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private BluetoothDevice mDevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Ask CameraPermission???

        tabLayout = (TabLayout) findViewById(R.id.main_activity_tablayout);
        viewPager = (ViewPager) findViewById(R.id.main_activity_viewpager);

    }


}
