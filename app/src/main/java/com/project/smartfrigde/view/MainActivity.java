package com.project.smartfrigde.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.project.smartfrigde.R;
import com.project.smartfrigde.adapter.StepAdapter;
import com.project.smartfrigde.data.dto.request.FoodItemRequest;
import com.project.smartfrigde.data.dto.response.ResponseObject;
import com.project.smartfrigde.data.dto.response.UserResponse;
import com.project.smartfrigde.data.remote.api.retrofit.UserAPIService;
import com.project.smartfrigde.databinding.ActivityMainBinding;
import com.project.smartfrigde.utils.Validation;
import com.project.smartfrigde.viewmodel.UserViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding activityMainBinding;
    List<UserResponse> list = new ArrayList<>();
    StepAdapter stepAdapter;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        recyclerView = activityMainBinding.recycleView;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        stepAdapter = new StepAdapter(new ArrayList<>());
        recyclerView.setAdapter(stepAdapter);
        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
    public void displayUser(){
        activityMainBinding.setUser(list.get(0));
    }
}