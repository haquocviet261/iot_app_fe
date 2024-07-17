package com.project.smartfrigde.view.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.Observable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.ai.client.generativeai.GenerativeModel;
import com.google.ai.client.generativeai.java.GenerativeModelFutures;
import com.google.ai.client.generativeai.type.Content;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.project.smartfrigde.R;
import com.project.smartfrigde.adapter.FoodRecommendAdapter;
import com.project.smartfrigde.databinding.FragmentFoodRecommendBinding;
import com.project.smartfrigde.model.ChatMessage;
import com.project.smartfrigde.model.FoodRecommend;
import com.project.smartfrigde.model.RecipeStep;
import com.project.smartfrigde.utils.Validation;
import com.project.smartfrigde.viewmodel.ChatViewModel;
import com.project.smartfrigde.viewmodel.FoodRecommendViewModel;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FoodRecommendFragment extends Fragment {
    private FragmentFoodRecommendBinding fragmentFoodRecommendBinding;
    private FoodRecommendViewModel foodRecommendViewModel;
    GenerativeModel gm = new GenerativeModel("gemini-1.5-flash", Validation.APIKEY);
    GenerativeModelFutures model = GenerativeModelFutures.from(gm);
    private FoodRecommendAdapter foodRecommendAdapter;
    ObjectMapper objectMapper = new ObjectMapper();
    private RecyclerView recyclerView;
    private  Gson gson = new Gson();
    private List<FoodRecommend> list = new ArrayList<>();
    private String lunchMessage;
    private String dinnerMessage;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentFoodRecommendBinding = FragmentFoodRecommendBinding.inflate(inflater,container,false);
        foodRecommendViewModel = new FoodRecommendViewModel();
        fragmentFoodRecommendBinding.setFoodRecemmendViewModel(foodRecommendViewModel);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        foodRecommendAdapter = new FoodRecommendAdapter(getContext(),list);
        recyclerView = fragmentFoodRecommendBinding.listFoodRecommend;
        recyclerView.setAdapter(foodRecommendAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey("lunch")) {
            lunchMessage = getArguments().getString("lunch");

        }
        if (bundle != null && bundle.containsKey("dinner")) {
            dinnerMessage = getArguments().getString("dinner");

        }
        if (lunchMessage != null) {
            setupMessageSending(Validation.FOOD_FOR_lUNCH);
        } else if (dinnerMessage != null) {
            setupMessageSending(Validation.FOOD_FOR_DINNER);

        }else {
            setupMessageSending(Validation.FOOD_RECOMMEND);
        }

        fragmentFoodRecommendBinding.refresh.setOnClickListener(view -> {
            setupMessageSending(Validation.FOOD_RECOMMEND);
        });
        foodRecommendViewModel.getIs_loaded_data().addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                boolean isLoaded = foodRecommendViewModel.getIs_loaded_data().get();
                getActivity().runOnUiThread(() -> {
                    if (isLoaded) {
                        try {
                        String text = foodRecommendViewModel.getRepply_message().get().toString();
                        text = text.replace("```json\n", "").replace("\n```", "");
                            List<FoodRecommend> foodList = objectMapper.readValue(text, new TypeReference<List<FoodRecommend>>(){});
                            for (int i = foodList.size() - 1 ; i >=0; i--) {
                                if (foodList.get(i).getName().equals("") || foodList.get(i) == null || foodList.get(i).getDescription().equals("")){
                                    foodList.remove(i);
                                }
                            }
                            list.addAll(foodList);
                            foodRecommendAdapter.setData(list);
                        } catch (Exception e) {
                            Log.d("Food",  e.getMessage());
                        }
                        fragmentFoodRecommendBinding.groupWait.setVisibility(View.GONE);
                    } else {
                       fragmentFoodRecommendBinding.groupWait.setVisibility(View.VISIBLE);
                    }
                });
            }
        });
        foodRecommendViewModel.getIs_loaded_data().set(false);
        return fragmentFoodRecommendBinding.getRoot();
    }
    private void setupMessageSending(String text) {
                Executor executor = Executors.newSingleThreadExecutor();
                Content content = new Content.Builder()
                        .addText(text)
                        .build();
        foodRecommendViewModel.callModel(executor, model, content);
        foodRecommendViewModel.analyzeText(text);
    }
}