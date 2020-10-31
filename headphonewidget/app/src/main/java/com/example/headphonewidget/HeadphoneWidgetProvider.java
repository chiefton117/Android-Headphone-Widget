package com.example.headphonewidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadset;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.RemoteViews;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class HeadphoneWidgetProvider extends android.appwidget.AppWidgetProvider {
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for(int appWidgetId : appWidgetIds) {
            Intent intent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.headphone_widget);
            views.setOnClickPendingIntent(R.id.button, pendingIntent);

            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
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
        Log.i("AppOut", btName);
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

        List<String> s = new ArrayList<String>();
        for(BluetoothDevice bt : pairedDevices) {
            Log.i("AppOut", bt.getBluetoothClass().toString());
            s.add(bt.getName());
            Log.i("AppOut", "Device: " + bt.getName());
        }
        BluetoothHeadset btHeadset;
        //setListAdapter(new ArrayAdapter<String>(this, R.layout.list, s));
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

}

