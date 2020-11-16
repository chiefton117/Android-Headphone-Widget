package com.example.headphonewidget;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothHeadset;
import android.content.Intent;
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

public class MainActivity extends AppCompatActivity {

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



        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(mBluetoothAdapter == null) {
            Log.e("AppOut", "error: this device does not support bluetooth");
            return;
        }
        if(!mBluetoothAdapter.isEnabled()) {
            Log.e("AppOut", "error: bluetooth is not enabled");
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            //startActivityForResult(enableBtIntent, 1);
            return;
        }
        String btName = getBTName(mBluetoothAdapter);
        BluetoothDevice headset = null;


        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        //mBluetoothAdapter.get
        for(BluetoothDevice bt : pairedDevices) {
            ParcelUuid[] uuids = bt.getUuids();

            if(uuids == null) continue;
            for(ParcelUuid uuid : uuids) {
                if(uuid.toString().equals(uuid_one.toString()) || uuid.toString().equals(uuid_two.toString())) {
                    headset = bt;
                    break;
                }
            }
            if(headset != null) {
                Log.i("AppOut", "CONNECTED DEVICE : " + headset.getName());
                break;
            }
            Log.i("AppOut", "Device: " + bt.getName());
        }
        TextView txt = findViewById(R.id.infodump);
        txt.setText(headset.getName());
        //setListAdapter(new ArrayAdapter<String>(this, R.layout.list, s));


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