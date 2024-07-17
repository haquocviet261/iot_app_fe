package com.project.smartfrigde.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.project.smartfrigde.R;
import com.project.smartfrigde.databinding.ItemFoodBinding;
import com.project.smartfrigde.model.Food;
import com.project.smartfrigde.model.FoodItem;
import com.project.smartfrigde.viewmodel.DetailDeviceViewModel;

import java.util.ArrayList;
import java.util.List;

public class FoodItemAdapter extends RecyclerView.Adapter<FoodItemAdapter.FoodItemViewHolder>{
    private List<Food> list_food = new ArrayList<>();
    private List<FoodItem> list = new ArrayList<>();
    private String[] food_name_recommend ;
    private Context context;
    private  DetailDeviceViewModel detailDeviceViewModel;

    public void setFood(List<Food> list_food) {
        this.list_food = list_food;
        food_name_recommend = new String[list_food.size()];
        for (int i = 0; i < list_food.size() ; i++) {
            food_name_recommend[i] = list_food.get(i).getFood_name();
        }
        notifyDataSetChanged();;
    }
    public FoodItemAdapter(Context context, List<FoodItem> list, DetailDeviceViewModel detailDeviceViewModel) {
        this.list = list;
        this.context = context;
        this.detailDeviceViewModel = detailDeviceViewModel;
    }

    @NonNull
    @Override
    public FoodItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFoodBinding itemFoodBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_food,parent,false);
        itemFoodBinding.setDetailDeviceViewModel(detailDeviceViewModel);
        return new FoodItemViewHolder(itemFoodBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodItemViewHolder holder, int position) {
        ArrayAdapter<String> name_adapter = new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line, food_name_recommend);
        holder.itemFoodBinding.foodName.setAdapter(name_adapter);

        holder.itemFoodBinding.foodName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = (String) adapterView.getItemAtPosition(i);
                holder.itemFoodBinding.foodName.setText(selectedItem);
                int index = -1;
                for (int j = 0; j < list_food.size(); j++) {
                    if (list_food.get(j).getFood_name().equals(selectedItem)) {
                        index = j;
                        break;
                    }
                }
                if (index != -1) {
                    holder.itemFoodBinding.getDetailDeviceViewModel().setFood_id(list_food.get(i).getFood_id());
                    Food selectedFood = list_food.get(index);
                    holder.itemFoodBinding.unit.setText(String.valueOf(selectedFood.getUnit()));
                    holder.itemFoodBinding.dateExpired.setText(String.valueOf(selectedFood.getDate_expired()));
                } else {
                    holder.itemFoodBinding.unit.setText("");
                    holder.itemFoodBinding.dateExpired.setText("");
                }
            }
        });
        FoodItem foodItem = list.get(position);

    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class FoodItemViewHolder extends RecyclerView.ViewHolder {
        private ItemFoodBinding itemFoodBinding;
        public FoodItemViewHolder(@NonNull ItemFoodBinding itemFoodBinding) {
            super(itemFoodBinding.getRoot());
            this.itemFoodBinding = itemFoodBinding;
        }
    }
}
