package com.project.smartfrigde.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

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
import com.project.smartfrigde.data.dto.request.FoodItemRequest;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
        ProgressDialog progressDialog = new ProgressDialog(this, R.layout.progressdialog);
        SharedPreferences sharedPreferences = this.getSharedPreferences(Validation.PREF_NAME, this.MODE_PRIVATE);
        activityDetailDeviceBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail_device);
        String jsonDevices = sharedPreferences.getString(Validation.KEY_DEVICE, null);
        String jsonFood = sharedPreferences.getString(Validation.KEY_FOOD_LIST, null);
        String jsonFoodConSump = sharedPreferences.getString(Validation.KEY_FOOD_CONSUMED, null);
        String jsonDevice = sharedPreferences.getString(Validation.KEY_DEVICE_ITEMS, null);
        String jsonFoodItems = sharedPreferences.getString(Validation.KEY_FOOD_ITEMS, null);

        if (jsonDevice != null && !jsonDevice.equals("[]")) {
            Type type = new TypeToken<List<DeviceItem>>() {}.getType();
            deviceItemList = gson.fromJson(jsonDevice, type);
            detailDeviceViewModel = new DetailDeviceViewModel(deviceItemList.get(0).getDevice_name());
            detailDeviceViewModel.setDevice_item_id(deviceItemList.get(0).getDevice_item_id());
            activityDetailDeviceBinding.setDetailDeviceViewModel(detailDeviceViewModel);
        }

        if (jsonDevices != null && !jsonDevices.equals("[]")) {
            Type type = new TypeToken<List<DeviceRequest>>() {}.getType();
            deviceRequests.addAll(gson.fromJson(jsonDevices, type));
        }

        if (jsonFood != null && !jsonFood.equals("[]")) {
            Type type = new TypeToken<List<Food>>() {}.getType();
            foodList.addAll(gson.fromJson(jsonFood, type));
        }
        detailDeviceViewModel.getIs_loaded_data().addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                //recyclerView.setAdapter(detailDeviceViewModel.getListFoodItem());
               // foodItemAdapter.setFood(detailDeviceViewModel.getListFoodItem());
                
            }
        });
        if (jsonFoodItems != null && !jsonFoodItems.equals("[]") && jsonFoodConSump != null && !jsonFoodConSump.equals("[]")) {

            Type type = new TypeToken<List<FoodItemResponse>>() {}.getType();
            List<FoodItemResponse> deviceItemList = gson.fromJson(jsonFoodItems, type);
            type = new TypeToken<FoodConsum>() {}.getType();
            FoodConsum foodConsum = gson.fromJson(jsonFoodConSump, type);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            if (foodConsum != null && !foodConsum.equals("[]")) {
                for (FoodItemResponse response : deviceItemList) {
                    list.add(new FoodItem(
                            response.getFood_name() != null ? response.getFood_name() : "",
                            response.getFood_name() != null ? response.getFood_name() : String.valueOf(foodConsum.getEggsTaken()),
                            simpleDateFormat.format(response.getExpiration_date()) != null ? simpleDateFormat.format(response.getExpiration_date()) : ""
                    ));
                }
            }
        } else {
            detailDeviceViewModel.getAllFoodItem(sharedPreferences.edit(),deviceItemList.get(0).getDevice_item_id());
        }

        detailDeviceViewModel.getIsLoaddedListFoodItem().addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                if (Boolean.TRUE.equals(detailDeviceViewModel.getIsLoaddedListFoodItem().get())){
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    for (FoodItemRequest response : detailDeviceViewModel.getListFoodItem()) {
                        list.add(new FoodItem(
                                response.getFood_name() != null ? response.getFood_name() : "",
                                response.getQuantity() != null ? String.valueOf(response.getQuantity()) : "0",
                                simpleDateFormat.format(response.getExpiration_date() != null ? response.getExpiration_date(): new Date()) != null ? simpleDateFormat.format(response.getExpiration_date()) : ""
                        ));
                        foodItemAdapter.notifyDataSetChanged();

                    }

                }
            }
        });
        if (detailDeviceViewModel.getListFoodItem().isEmpty()){
            list.add(new FoodItem("Meat","430","15"));
            list.add(new FoodItem("Eggs","2","15"));
        }




        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView = activityDetailDeviceBinding.listFoodItems;
        detailDeviceViewModel.getOnclickFood().addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                if (Boolean.TRUE.equals(detailDeviceViewModel.getOnclickFood().get())) {
                    try {
                        detailDeviceViewModel.addFoodItem(list);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        foodItemAdapter = new FoodItemAdapter(this, list, detailDeviceViewModel);
        foodItemAdapter.setFood(foodList);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(foodItemAdapter);
        detailDeviceViewModel.getOnclickBack().addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                if (Boolean.TRUE.equals(detailDeviceViewModel.getOnclickBack().get())) {
                    startActivity(new Intent(DetailDeviceActivity.this, DashboardActivity.class));
                }
            }
        });
        activityDetailDeviceBinding.floatingActionButton.setOnClickListener(view -> {
            if (deviceItemList.get(0) != null && deviceItemList.get(1) != null) {
                detailDeviceViewModel.deleteFoodItemExpired(deviceItemList.get(0).getDevice_item_id());
                detailDeviceViewModel.deleteFoodItemExpired(deviceItemList.get(1).getDevice_item_id());
                list.clear();
                list.add(new FoodItem());
                list.add(new FoodItem());
                foodItemAdapter = new FoodItemAdapter(this, list, detailDeviceViewModel);
                foodItemAdapter.setFood(foodList);
                list.get(0).setList(foodList);
                list.get(1).setList(foodList);
            }
        });
    }
}