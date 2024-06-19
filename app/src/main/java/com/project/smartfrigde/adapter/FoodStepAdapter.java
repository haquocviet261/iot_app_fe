package com.project.smartfrigde.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.project.smartfrigde.R;
import com.project.smartfrigde.databinding.ItemStepBinding;
import com.project.smartfrigde.model.RecipeStep;

import java.util.ArrayList;
import java.util.List;

public class FoodStepAdapter extends RecyclerView.Adapter<FoodStepAdapter.FoodStepViewHolder>{
    List<RecipeStep> list = new ArrayList<>();
    private Context context;

    @NonNull
    @Override
    public FoodStepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemStepBinding itemStepBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_step,parent,false);
        return new FoodStepViewHolder(itemStepBinding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull FoodStepViewHolder holder, int position) {
        RecipeStep recipeStep = list.get(position);
        holder.itemStepBinding.tvStep.setText("Bước: "+recipeStep.getStep());
        holder.itemStepBinding.tvInstruction.setText("Hướng dẫn: "+recipeStep.getInstruction());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class FoodStepViewHolder extends RecyclerView.ViewHolder {
        private ItemStepBinding itemStepBinding;
        public FoodStepViewHolder(@NonNull ItemStepBinding itemStepBinding) {
            super(itemStepBinding.getRoot());
            this.itemStepBinding = itemStepBinding;
        }
    }
}
