package com.project.smartfrigde.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.project.smartfrigde.R;
import com.project.smartfrigde.databinding.ItemFoodBinding;
import com.project.smartfrigde.model.FoodItem;

import java.util.ArrayList;
import java.util.List;

public class FoodItemAdapter extends RecyclerView.Adapter<FoodItemAdapter.FoodItemViewHolder>{
    private List<FoodItem> list = new ArrayList<>();

    @NonNull
    @Override
    public FoodItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFoodBinding itemFoodBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_food,parent,false);
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull FoodItemViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class FoodItemViewHolder extends RecyclerView.ViewHolder {
        private ItemFoodBinding itemFoodBinding;
        public FoodItemViewHolder(@NonNull ItemFoodBinding itemFoodBinding) {
            super(itemFoodBinding.getRoot());
        }
    }
}
