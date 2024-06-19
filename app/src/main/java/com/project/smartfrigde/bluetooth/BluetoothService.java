package com.project.smartfrigde.bluetooth;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.UUID;

public class BluetoothService extends Thread {
    private static final String TAG = "BluetoothService";
    private  BluetoothSocket mmSocket;
    private final BluetoothDevice mmDevice;
    private Context context;

    private static final String NAME = "SFridge";
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); // Replace with your UUID

    public BluetoothService(Context context, BluetoothDevice device) {
        this.context = context;
        this.mmDevice = device;
        BluetoothSocket tmp = null;
        try {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                Log.e(TAG, "Bluetooth permissions are not granted");
                return;
            }
            tmp = device.createRfcommSocketToServiceRecord(MY_UUID);

        } catch (IOException e) {
            Log.e(TAG, "Socket's create() method failed", e);
        }
        this.mmSocket = tmp;
    }

    public void run() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
            Log.e(TAG, "Bluetooth scan permission not granted");
            return;
        }

        if (mmSocket == null) {
            Log.e(TAG, "Socket is null, cannot connect");
            return;
        }

        try {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                Log.e(TAG, "Bluetooth connect permission not granted");
                return;
            }
            mmSocket.connect();
        } catch (IOException connectException) {
            Log.e(TAG, "Unable to connect; closing the socket and returning", connectException);
            try {
                mmSocket.close();
            } catch (IOException closeException) {
                Log.e(TAG, "Could not close the client socket", closeException);
            }
            return;
        }

        manageMyConnectedSocket(mmSocket);
    }

    public ConnectedThread manageMyConnectedSocket(BluetoothSocket socket) {
        ConnectedThread connectedThread = new ConnectedThread(socket);
        connectedThread.start();
        return connectedThread;
    }

    // Closes the connect socket and causes the thread to finish.
    public void cancel() {
        try {
            if (mmSocket != null) {
                mmSocket.close();
            }
        } catch (IOException e) {
            Log.e(TAG, "Could not close the client socket", e);
        }
    }

    public BluetoothSocket getMmSocket() {
        return mmSocket;
    }

    public void setMmSocket(BluetoothSocket mmSocket) {
        this.mmSocket = mmSocket;
    }
}