package com.project.smartfrigde.view;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.project.smartfrigde.R;
import com.project.smartfrigde.adapter.FoodAdapter;
import com.project.smartfrigde.model.Food;

import java.util.ArrayList;
import java.util.List;

public class FoodActivity extends AppCompatActivity {

    private RecyclerView foodRecyclerView;
    private FoodAdapter foodAdapter;
    private List<Food> foodList;
    private List<Food> filteredFoodList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        foodRecyclerView = findViewById(R.id.food_recycler_view);
        foodRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Giả sử bạn có danh sách thực phẩm
        foodList = getFoodList();

        // Sao chép danh sách thực phẩm vào danh sách lọc
        filteredFoodList = new ArrayList<>(foodList);

        foodAdapter = new FoodAdapter(filteredFoodList);
        foodRecyclerView.setAdapter(foodAdapter);

        // Xử lý sự kiện click cho các mục category
        TextView categoryAll = findViewById(R.id.category_all);
        TextView categoryMeat = findViewById(R.id.category_meat);
        TextView categoryVegetables = findViewById(R.id.category_vegetables);
        TextView categoryFruits = findViewById(R.id.category_fruits);
        TextView categoryOthers = findViewById(R.id.category_others);

        categoryAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterFoodList("All");
            }
        });

        categoryMeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterFoodList("Thịt");
            }
        });

        categoryVegetables.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterFoodList("Rau");
            }
        });

        categoryFruits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterFoodList("Quả");
            }
        });

        categoryOthers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterFoodList("Khác");
            }
        });
    }

    private List<Food> getFoodList() {
        // Giả lập danh sách thực phẩm (bạn có thể thay thế bằng dữ liệu thực tế)
        List<Food> foodList = new ArrayList<>();
        foodList.add(new Food("Thịt bò", "2024-12-01", "500g", "thịt"));
        foodList.add(new Food("Rau cải", "2024-10-20", "300g", "rau"));
        foodList.add(new Food("Táo", "2024-11-10", "1kg", "quả"));
        foodList.add(new Food("Cá hồi", "2024-12-05", "200g", "thịt"));
        foodList.add(new Food("Gạo", "2025-01-01", "5kg", "thịt"));
        return foodList;
    }

    private void filterFoodList(String category) {
        filteredFoodList.clear();
        if (category.equals("All")) {
            filteredFoodList.addAll(foodList);
        } else {
            for (Food food : foodList) {
                if (food.getCategory().equals(category)) {
                    filteredFoodList.add(food);
                }
            }
        }
        foodAdapter.notifyDataSetChanged();
    }
}