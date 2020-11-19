package com.example.headphonewidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothAssignedNumbers;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothHeadset;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Parcel;
import android.os.ParcelUuid;
import android.os.Parcelable;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static android.bluetooth.BluetoothProfile.GATT;
import static android.content.Context.BLUETOOTH_SERVICE;


public class HeadphoneWidgetProvider extends android.appwidget.AppWidgetProvider {

    public static final UUID uuid_one = UUID.fromString("96cc203e-5068-46ad-b32d-e316f5e069ba");
    public static final UUID uuid_two = UUID.fromString("ba69e0f5-16e3-2db3-ad46-68503e20cc96");

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for(int appWidgetId : appWidgetIds) {
            Intent intent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.headphone_widget);
            views.setOnClickPendingIntent(R.id.button, pendingIntent);

            appWidgetManager.updateAppWidget(appWidgetId, views);
        }

        /*
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            BluetoothManager manager = (BluetoothManager) context.getSystemService(BLUETOOTH_SERVICE);
            List<BluetoothDevice> connected = manager.getConnectedDevices(GATT);
            Log.i("AppOut", "Connected Device!!" + connected.get(0).getName()+"");
        }*/


        BluetoothDevice headset = getBTDevice();





        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.headphone_widget);
        views.setTextViewText(R.id.infodump, headset.getName());
        views.setTextColor(R.id.infodump, Color.WHITE);

        appWidgetManager.updateAppWidget(appWidgetIds, views);


    }
    private BluetoothDevice getBTDevice() {
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if(mBluetoothAdapter == null) {
            Log.e("AppOut", "error: this device does not support bluetooth");
            return null;
        }
        if(!mBluetoothAdapter.isEnabled()) {
            Log.e("AppOut", "error: bluetooth is not enabled");
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            //startActivityForResult(enableBtIntent, 1);
            return null;
        }

        BluetoothDevice headset = null;


        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

        for(BluetoothDevice bt : pairedDevices) {
            ParcelUuid[] uuids = bt.getUuids();

            if(uuids == null) continue;
            for(ParcelUuid uuid : uuids) {
                if(uuid.toString().equals(uuid_one.toString()) || uuid.toString().equals(uuid_two.toString())) {
                    Log.i("Appout", uuid.toString());
                    headset = bt;
                    break;
                }
            }
            if(headset != null) {
                Log.i("AppOut", "CONNECTED DEVICE : " + headset.getName());
                break;
            }
        }
        return headset;
    }
    private PendingIntent getPendingSelfIntent(Context context, String action) {
        Intent intent = new Intent(context, getClass());
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent,0);

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

