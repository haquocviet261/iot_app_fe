package com.project.smartfrigde.view.fragment;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.Observable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        setupMessageSending();
        fragmentFoodRecommendBinding.refresh.setOnClickListener(view -> {
            setupMessageSending();
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
    private void setupMessageSending() {
            String text = "Bạn hãy gửi cho tôi 1 chuỗi json về đề xuất thức ăn và cách nấu gồm name và description và recipe , trong recipe gồm 1 list object có 2 thuộc tính là step và instruction, đây là mẫu ([{\n" +
                    "    \"name\": \"Bánh mì kẹp thịt\",\n" +
                    "    \"description\": \"Món ăn phổ biến với thịt bò xay, rau và nước sốt ngon miệng.\",\n" +
                    "    \"recipe\": [\n" +
                    "        {\n" +
                    "            \"step\": 1,\n" +
                    "            \"instruction\": \"Trộn thịt bò xay với muối, tiêu, hành tây băm nhỏ và gia vị yêu thích.\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"step\": 2,\n" +
                    "            \"instruction\": \"Tạo hình viên thịt và chiên trên chảo nóng cho đến khi chín vàng đều.\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"step\": 3,\n" +
                    "            \"instruction\": \"Cắt bánh mì thành hai phần, nướng nhẹ hoặc chiên giòn.\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"step\": 4,\n" +
                    "            \"instruction\": \"Chuẩn bị rau như cà chua, dưa chuột, hành tây, xà lách.\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"step\": 5,\n" +
                    "            \"instruction\": \"Cho thịt viên vào bánh mì, thêm rau, nước sốt và phô mai (nếu thích).\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"step\": 6,\n" +
                    "            \"instruction\": \"Đóng bánh mì lại và thưởng thức.\"\n" +
                    "        }\n" +
                    "    ]\n" +
                    "},{\n" +
                    "    \"name\": \"Bánh mì kẹp thịt\",\n" +
                    "    \"description\": \"Món ăn phổ biến với thịt bò xay, rau và nước sốt ngon miệng.\",\n" +
                    "    \"recipe\": [\n" +
                    "        {\n" +
                    "            \"step\": 1,\n" +
                    "            \"instruction\": \"Trộn thịt bò xay với muối, tiêu, hành tây băm nhỏ và gia vị yêu thích.\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"step\": 2,\n" +
                    "            \"instruction\": \"Tạo hình viên thịt và chiên trên chảo nóng cho đến khi chín vàng đều.\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"step\": 3,\n" +
                    "            \"instruction\": \"Cắt bánh mì thành hai phần, nướng nhẹ hoặc chiên giòn.\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"step\": 4,\n" +
                    "            \"instruction\": \"Chuẩn bị rau như cà chua, dưa chuột, hành tây, xà lách.\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"step\": 5,\n" +
                    "            \"instruction\": \"Cho thịt viên vào bánh mì, thêm rau, nước sốt và phô mai (nếu thích).\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"step\": 6,\n" +
                    "            \"instruction\": \"Đóng bánh mì lại và thưởng thức.\"\n" +
                    "        }\n" +
                    "    ]\n" +
                    "}]) và đừng nói gì cả";
                Executor executor = Executors.newSingleThreadExecutor();
                Content content = new Content.Builder()
                        .addText(text)
                        .build();
        foodRecommendViewModel.callModel(executor, model, content);
        foodRecommendViewModel.analyzeText(text);
    }
}