package com.project.smartfrigde.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.smartfrigde.R;
import com.project.smartfrigde.databinding.ItemStepBinding;
import com.project.smartfrigde.model.RecipeStep;

import java.util.List;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepViewHolder> {

    private List<RecipeStep> stepList;

    public StepAdapter(List<RecipeStep> stepList) {
        this.stepList = stepList;
    }

    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_step, parent, false);
        return new StepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepViewHolder holder, int position) {
        RecipeStep step = stepList.get(position);
        holder.itemStepBinding.tvStep.setText(step.getStep());
    }

    @Override
    public int getItemCount() {
        return stepList.size();
    }

    public static class StepViewHolder extends RecyclerView.ViewHolder {

       // TextView tvStep, tvInstruction;
        ItemStepBinding itemStepBinding;

        public StepViewHolder(@NonNull ItemStepBinding itemStepBinding) {
            super(itemStepBinding.getRoot());
            StepViewHolder stepViewHolder = new StepViewHolder(itemStepBinding);
        }
    }
}
