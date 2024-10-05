package com.project.smartfrigde.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.Observable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.project.smartfrigde.R;
import com.project.smartfrigde.bluetooth.BluetoothService;
import com.project.smartfrigde.bluetooth.ConnectedThread;
import com.project.smartfrigde.databinding.ItemDeviceBinding;
import com.project.smartfrigde.model.BluetoothDevice;
import com.project.smartfrigde.model.Device;
import com.project.smartfrigde.model.Wifi;
import com.project.smartfrigde.utils.ProgressDialog;
import com.project.smartfrigde.view.DetailDeviceActivity;
import com.project.smartfrigde.viewmodel.HomeViewmodel;

import java.util.ArrayList;
import java.util.List;

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.DeviceViewHolder>{
    private List<BluetoothDevice> list = new ArrayList<>();
    private Context context;
    private BluetoothService bluetoothService;
    private HomeViewmodel homeViewmodel;
    private ProgressDialog progressDialog;
    public DeviceAdapter(List<BluetoothDevice> list,Context context,BluetoothService bluetoothService,HomeViewmodel homeViewmodel) {
        this.list = list;
        this.context = context;
        this.bluetoothService = bluetoothService;
        this.homeViewmodel = homeViewmodel;
        progressDialog = new ProgressDialog(context,R.layout.progressdialog);

    }

    public BluetoothService getBluetoothService() {
        return bluetoothService;
    }

    public void setBluetoothService(BluetoothService bluetoothService) {
        this.bluetoothService = bluetoothService;
    }

    public  void setData(List<BluetoothDevice> list){
        this.list = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public DeviceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemDeviceBinding itemDeviceBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_device,parent,false);
        itemDeviceBinding.setHomeViewModel(homeViewmodel);
        return new DeviceViewHolder(itemDeviceBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull DeviceViewHolder holder, int position) {
        BluetoothDevice device = list.get(position);
        holder.itemDeviceBinding.setDevice(device);

        holder.itemDeviceBinding.getHomeViewModel().getIsDetailDevice().addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                progressDialog.show();
                if (Boolean.TRUE.equals(holder.itemDeviceBinding.getHomeViewModel().getIsDetailDevice().get())) {
                    Intent intent = new Intent(context, DetailDeviceActivity.class);
                    progressDialog.dismiss();
                    context.startActivity(intent);
                }
            }
        });

        holder.itemDeviceBinding.getHomeViewModel().getIsDetailDevice().set(false);
        holder.itemDeviceBinding.detailDevice.setOnClickListener(view -> {
            openWifiSettingDialog(Gravity.CENTER);
        });
    }
    private void openWifiSettingDialog(int gravity){
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.wifi_dialog);
        Window window = dialog.getWindow();
        if (window == null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttribite = window.getAttributes();
        windowAttribite.gravity =gravity;
        window.setAttributes(windowAttribite);
        if (Gravity.BOTTOM == gravity){
            dialog.setCancelable(true);
        }else {
            dialog.setCancelable(false);
        }
        EditText ssid = dialog.findViewById(R.id.ssid);
        EditText password = dialog.findViewById(R.id.password);
        Button cancel = dialog.findViewById(R.id.cancel);
        Button save = dialog.findViewById(R.id.save);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectedThread connectedThread = bluetoothService.manageMyConnectedSocket(bluetoothService.getMmSocket());
                if (connectedThread != null) {
                    Wifi wifi = new Wifi(ssid.getText().toString(),password.getText().toString());
                    Gson gson = new Gson();
                    byte[] data = gson.toJson(wifi).getBytes();
                    connectedThread.write(data);
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class DeviceViewHolder extends RecyclerView.ViewHolder {
        private ItemDeviceBinding itemDeviceBinding;
        public DeviceViewHolder(@NonNull ItemDeviceBinding itemView) {
            super(itemView.getRoot());
            this.itemDeviceBinding = itemView;
        }
    }
    private void checkAndNavigate(HomeViewmodel viewModel, Context context) {

    }
}
