package com.project.smartfrigde.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.smartfrigde.R;
import com.project.smartfrigde.adapter.StepAdapter;
import com.project.smartfrigde.databinding.ActivityFoodDetailBinding;
import com.project.smartfrigde.model.FoodRecommend;
import com.project.smartfrigde.model.RecipeStep;

import java.util.ArrayList;
import java.util.List;

public class FoodDetailActivity extends AppCompatActivity {
    private ActivityFoodDetailBinding activityFoodDetailBinding;
    private RecyclerView recyclerView;
    private StepAdapter stepAdapter;
    private List<RecipeStep> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityFoodDetailBinding = DataBindingUtil.setContentView(this,R.layout.activity_food_detail);
        Intent intent = getIntent();
        if (intent.hasExtra("food")) {
            FoodRecommend foodRecommend =(FoodRecommend) intent.getSerializableExtra("food");
            list.addAll(foodRecommend.getRecipe());

        }
        activityFoodDetailBinding.foodDetailBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FoodDetailActivity.this,DashboardActivity.class));
            }
        });
        stepAdapter = new StepAdapter(list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView = activityFoodDetailBinding.listStep;
        recyclerView.setAdapter(stepAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
    }
}