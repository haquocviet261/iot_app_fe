package com.project.smartfrigde.adapter;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.Observable;
import androidx.recyclerview.widget.RecyclerView;

import com.project.smartfrigde.R;
import com.project.smartfrigde.bluetooth.BluetoothService;
import com.project.smartfrigde.databinding.ItemDeviceScanBinding;
import com.project.smartfrigde.model.BluetoothDevice;
import com.project.smartfrigde.utils.ProgressDialog;
import com.project.smartfrigde.view.DashboardActivity;

import java.util.ArrayList;
import java.util.List;

public class DeviceScanAdapter extends RecyclerView.Adapter<DeviceScanAdapter.DeviceScanViewHolder> {
    List<BluetoothDevice> list = new ArrayList<>();
    private Context context;
    private BluetoothAdapter bluetoothAdapter;

    public DeviceScanAdapter(List<BluetoothDevice> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<BluetoothDevice> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void setBluetoothAdapter(BluetoothAdapter bluetoothAdapter) {
        this.bluetoothAdapter = bluetoothAdapter;
    }

    @NonNull
    @Override
    public DeviceScanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemDeviceScanBinding itemDeviceScanBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_device_scan, parent, false);
        return new DeviceScanViewHolder(itemDeviceScanBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull DeviceScanViewHolder holder, int position) {
        BluetoothDevice bluetoothDevice = list.get(position);
        if (bluetoothDevice.getName() == null) {
            bluetoothDevice.setName("Anonymous Device");
        }
        holder.itemDeviceScanBinding.setBluetoothDevice(bluetoothDevice);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                            Intent intent = new Intent(context, DashboardActivity.class);
                            intent.putExtra("device", bluetoothDevice);
                            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
                                return;
                            }
                            bluetoothDevice.getAddDeviceViewModel().addDeviceItem(bluetoothDevice.getAddDeviceViewModel().getDevice_id(),bluetoothDevice.getName(), bluetoothDevice.getMac());
                            bluetoothAdapter.cancelDiscovery();
                            context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class DeviceScanViewHolder extends RecyclerView.ViewHolder {
        private ItemDeviceScanBinding itemDeviceScanBinding;
        public DeviceScanViewHolder(@NonNull ItemDeviceScanBinding itemView) {
            super(itemView.getRoot());
            this.itemDeviceScanBinding = itemView;
        }
    }
}
