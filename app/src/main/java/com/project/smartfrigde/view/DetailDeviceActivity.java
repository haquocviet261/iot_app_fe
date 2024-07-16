package com.project.smartfrigde.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.Observable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.project.smartfrigde.R;
import com.project.smartfrigde.adapter.FoodItemAdapter;
import com.project.smartfrigde.data.dto.request.DeviceRequest;
import com.project.smartfrigde.data.dto.response.FoodItemResponse;
import com.project.smartfrigde.databinding.ActivityDetailDeviceBinding;
import com.project.smartfrigde.model.DeviceItem;
import com.project.smartfrigde.model.Food;
import com.project.smartfrigde.model.FoodConsum;
import com.project.smartfrigde.model.FoodItem;
import com.project.smartfrigde.model.User;
import com.project.smartfrigde.utils.ProgressDialog;
import com.project.smartfrigde.utils.UserSecurePreferencesManager;
import com.project.smartfrigde.utils.Validation;
import com.project.smartfrigde.viewmodel.DetailDeviceViewModel;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DetailDeviceActivity extends AppCompatActivity {
    private ActivityDetailDeviceBinding activityDetailDeviceBinding;
    private DetailDeviceViewModel detailDeviceViewModel;
    private User user = UserSecurePreferencesManager.getUser();
    private final Gson gson = new Gson();
    private FoodItemAdapter foodItemAdapter;
    private RecyclerView recyclerView;
    ArrayList<FoodItem> list = new ArrayList<>();
    private ArrayList<Food> foodList = new ArrayList<>();
    private List<DeviceRequest> deviceRequests = new ArrayList<>();
    List<DeviceItem> deviceItemList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProgressDialog progressDialog = new ProgressDialog(this,R.layout.progressdialog);
        SharedPreferences sharedPreferences = this.getSharedPreferences(Validation.PREF_NAME, this.MODE_PRIVATE);
        activityDetailDeviceBinding = DataBindingUtil.setContentView(this,R.layout.activity_detail_device);
        String jsonDevices = sharedPreferences.getString(Validation.KEY_DEVICE, null);
        String jsonFood = sharedPreferences.getString(Validation.KEY_FOOD_LIST, null);
        String jsonFoodConSump = sharedPreferences.getString(Validation.KEY_FOOD_CONSUMED, null);
        String jsonDevice = sharedPreferences.getString(Validation.KEY_DEVICE_ITEMS, null);
        String jsonfoodItems = sharedPreferences.getString(Validation.KEY_DEVICE_ITEMS, null);

        if (jsonDevice != null){
            Type type = new TypeToken<List<DeviceItem>>() {}.getType();
            List<DeviceItem> deviceItemList = gson.fromJson(jsonDevice, type);
            detailDeviceViewModel = new DetailDeviceViewModel(deviceItemList.get(0).getDevice_name());
            activityDetailDeviceBinding.setDetailDeviceViewModel(detailDeviceViewModel);
        }
        if (jsonDevices != null){
            Type type = new TypeToken<List<DeviceRequest>>() {}.getType();
            deviceRequests.addAll(gson.fromJson(jsonDevices, type));
        }
        if (jsonFood != null) {
            Type type = new TypeToken<List<Food>>() {}.getType();
            foodList.addAll(gson.fromJson(jsonFood, type));
        }
        if (jsonfoodItems != null || jsonFoodConSump != null){
            Type type = new TypeToken<List<FoodItemResponse>>() {}.getType();
            List<FoodItemResponse> deviceItemList = gson.fromJson(jsonfoodItems, type);
            type = new TypeToken<FoodConsum>() {}.getType();
            FoodConsum foodConsum =  gson.fromJson(jsonFoodConSump, type);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            list.add(new FoodItem(deviceItemList.get(0).getFood_name(),String.valueOf(foodConsum.getWeightOfMeat()),simpleDateFormat.format(deviceItemList.get(0).getExpiration_date())));
            list.add(new FoodItem(deviceItemList.get(0).getFood_name(),"",simpleDateFormat.format(deviceItemList.get(0).getExpiration_date())));
        }else {
            list.add(new FoodItem());
            list.add(new FoodItem());
        }
        detailDeviceViewModel.getAllFoodItem(deviceItemList.get(0).getDevice_item_id());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView = activityDetailDeviceBinding.listFoodItems;

        foodItemAdapter = new FoodItemAdapter(this,list);
        foodItemAdapter.setFood(foodList);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(foodItemAdapter);
        detailDeviceViewModel.getOnclickBack().addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                if (Boolean.TRUE.equals(detailDeviceViewModel.getOnclickBack().get())){
                    startActivity(new Intent(DetailDeviceActivity.this,DashboardActivity.class));
                }
            }
        });
    }
}