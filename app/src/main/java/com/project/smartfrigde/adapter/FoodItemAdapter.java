package com.project.smartfrigde.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.project.smartfrigde.R;
import com.project.smartfrigde.databinding.ItemFoodBinding;
import com.project.smartfrigde.model.Food;
import com.project.smartfrigde.model.FoodConsum;
import com.project.smartfrigde.model.FoodItem;
import com.project.smartfrigde.utils.Validation;
import com.project.smartfrigde.viewmodel.DetailDeviceViewModel;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FoodItemAdapter extends RecyclerView.Adapter<FoodItemAdapter.FoodItemViewHolder>{
    private List<Food> list_food = new ArrayList<>();
    private List<FoodItem> list = new ArrayList<>();
    private String[] food_name_recommend ;
    private Context context;
    private DetailDeviceViewModel detailDeviceViewModel;

    public void setFood(List<Food> list_food) {
        this.list_food = list_food;
        food_name_recommend = new String[list_food.size()];
        for (int i = 0; i < list_food.size() ; i++) {
            food_name_recommend[i] = list_food.get(i).getFood_name();
        }
        notifyDataSetChanged();
    }

    public FoodItemAdapter(Context context, List<FoodItem> list, DetailDeviceViewModel detailDeviceViewModel) {
        this.list = list;
        this.context = context;
        this.detailDeviceViewModel = detailDeviceViewModel;
    }

    @NonNull
    @Override
    public FoodItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFoodBinding itemFoodBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_food, parent, false);
        itemFoodBinding.setDetailDeviceViewModel(detailDeviceViewModel);
        return new FoodItemViewHolder(itemFoodBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodItemViewHolder holder, int position) {
        ArrayAdapter<String> name_adapter = new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line, food_name_recommend);
        holder.itemFoodBinding.foodName.setAdapter(name_adapter);
        SharedPreferences sharedPreferences = context.getSharedPreferences(Validation.PREF_NAME, Context.MODE_PRIVATE);
        String jsonFoodConSump = sharedPreferences.getString(Validation.KEY_FOOD_CONSUMED, null);

        FoodItem currentItem = list.get(position);
        holder.itemFoodBinding.foodName.setOnItemClickListener((adapterView, view, i, l) -> {
            String selectedItem = (String) adapterView.getItemAtPosition(i);
            Gson gson = new Gson();
            Type type = new TypeToken<FoodConsum>() {}.getType();
            FoodConsum foodConsum = gson.fromJson(jsonFoodConSump, type);
            holder.itemFoodBinding.foodName.setText(selectedItem);
            int index = -1;
            for (int j = 0; j < list_food.size(); j++) {
                if (list_food.get(j).getFood_name().equals(selectedItem)) {
                    index = j;
                    break;
                }
            }
            if (index != -1) {
                Food selectedFood = list_food.get(index);
                currentItem.setFood_item_name(selectedItem);
                String eggsTaken = "0"; // Default value
                if (foodConsum != null) {
                    eggsTaken = (foodConsum.getEggsTaken() == null ? "0" : String.valueOf(foodConsum.getEggsTaken()));
                }
                currentItem.setUnit(selectedFood.getUnit().equals("gram") ?
                        String.valueOf(foodConsum.getWeightOfMeat()) :
                        (eggsTaken));
                currentItem.setExpired_date(String.valueOf(selectedFood.getDate_expired()));
                holder.itemFoodBinding.unit.setText(selectedFood.getUnit() != null ? selectedFood.getUnit() : "0");
                holder.itemFoodBinding.dateExpired.setText(selectedFood.getDate_expired() != null ? String.valueOf(selectedFood.getDate_expired()) : "0");
            } else {
                holder.itemFoodBinding.unit.setText("");
                holder.itemFoodBinding.dateExpired.setText("");
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class FoodItemViewHolder extends RecyclerView.ViewHolder {
        private final ItemFoodBinding itemFoodBinding;

        public FoodItemViewHolder(@NonNull ItemFoodBinding itemFoodBinding) {
            super(itemFoodBinding.getRoot());
            this.itemFoodBinding = itemFoodBinding;
        }
    }
}
