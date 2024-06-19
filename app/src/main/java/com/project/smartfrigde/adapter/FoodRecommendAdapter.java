package com.project.smartfrigde.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.project.smartfrigde.R;
import com.project.smartfrigde.databinding.ItemFoodRecommendBinding;
import com.project.smartfrigde.model.FoodRecommend;
import com.project.smartfrigde.view.FoodDetailActivity;

import java.util.List;

public class FoodRecommendAdapter extends RecyclerView.Adapter<FoodRecommendAdapter.FoodViewHolder> {

    private List<FoodRecommend> foodList;
    private Context context;

    public FoodRecommendAdapter(Context context, List<FoodRecommend> foodList) {
        this.context = context;
        this.foodList = foodList;
    }
    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<FoodRecommend> foodList){
        this.foodList.addAll(foodList);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFoodRecommendBinding itemFoodRecommendBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.item_food_recommend,parent,false);
        return new FoodViewHolder(itemFoodRecommendBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        FoodRecommend food = foodList.get(position);
        holder.itemFoodRecommendBinding.setFoodRecommend(food);
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, FoodDetailActivity.class);
            intent.putExtra("food", food);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public static class FoodViewHolder extends RecyclerView.ViewHolder {

        private ItemFoodRecommendBinding itemFoodRecommendBinding;

        public FoodViewHolder(@NonNull ItemFoodRecommendBinding itemFoodRecommendBinding) {
            super(itemFoodRecommendBinding.getRoot());
           this.itemFoodRecommendBinding = itemFoodRecommendBinding;
        }
    }
}
