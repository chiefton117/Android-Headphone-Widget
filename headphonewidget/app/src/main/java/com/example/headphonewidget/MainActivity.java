package com.example.headphonewidget;

import android.bluetooth.*;
/*
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothAssignedNumbers;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;

 */
import android.bluetooth.BluetoothHeadset;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.ParcelUuid;
import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static android.content.Intent.ACTION_HEADSET_PLUG;

public class MainActivity extends AppCompatActivity {

    public static final String ACTION_HF_INDICATORS_VALUE_CHANGED = "android.bluetooth.headset.action.HF_INDICATORS_VALUE_CHANGED";
    private BroadcastReceiver receiver;

    private static final UUID Battery_Service_UUID = UUID.fromString("0000180F-0000-1000-8000-00805f9b34fb");
    private static final UUID Battery_Level_UUID = UUID.fromString("00002a19-0000-1000-8000-00805f9b34fb");


    public static final UUID uuid_one = UUID.fromString("96cc203e-5068-46ad-b32d-e316f5e069ba");
    public static final UUID uuid_two = UUID.fromString("ba69e0f5-16e3-2db3-ad46-68503e20cc96");


    private static final UUID[] UUID_list = {
            UUID.fromString("8901dfa8-5c7e-4d8f-9f0c-c2b70683f5f0"),
            UUID.fromString("f8d1fbe4-7966-4334-8024-ff96c9330e15"), UUID.fromString("81c2e72a-0591-443e-a1ff-05f988593351"),
            UUID.fromString("931c7e8a-540f-4686-b798-e8df0a2ad9f7"), UUID.fromString("0000111e-0000-1000-8000-00805f9b34fb"),
            UUID.fromString("0000110b-0000-1000-8000-00805f9b34fb"), UUID.fromString("0000110e-0000-1000-8000-00805f9b34fb"),
            UUID.fromString("00000000-deca-fade-deca-deafdecacaff"), UUID.fromString("b9b213ce-eeab-49e4-8fd9-aa478ed1b26b") };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.headphone_widget);




        IntentFilter filter = new IntentFilter();
        filter.addAction("android.bluetooth.device.action.BATTERY_LEVEL_CHANGED");
        filter.addAction(ACTION_HF_INDICATORS_VALUE_CHANGED);

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                final String action = intent.getAction();
                Log.i("BRECEIVER", action);
                Bundle extras = intent.getExtras();
                Log.i("BluetoothR 1", extras.toString());
                int blevel = extras.getInt("android.bluetooth.device.extra.BATTERY_LEVEL");
                Log.i("BluetoothR 2", Integer.toString(blevel));
                int blextra = extras.getInt("android.bluetooth.device.extra.PREV_BATTERY_LEVEL");
                Log.i("BluetoothR 3", Integer.toString(blextra));
            }
        };

        registerReceiver(receiver, filter);

    }
    @Override
    protected void onDestroy() {
        if (receiver != null) {
            unregisterReceiver(receiver);
            receiver = null;
        }
        super.onDestroy();
    }
    public void getBattery() {
        //BluetoothGattCharacteristic battery = mbluetoothadapter;
    }
    public String getBTName(BluetoothAdapter btAdapter) {
        String name = null;
        if(btAdapter != null) {
            name = btAdapter.getName();
            if(name != null) {
            }
        }
        return name;
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}