package com.project.smartfrigde.view.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.Observable;
import androidx.fragment.app.Fragment;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.project.smartfrigde.R;
import com.project.smartfrigde.data.dto.request.BmiRequest;
import com.project.smartfrigde.databinding.FragmentFoodRecommendBinding;
import com.project.smartfrigde.databinding.FragmentMealBinding;
import com.project.smartfrigde.model.Food;
import com.project.smartfrigde.utils.ProgressDialog;
import com.project.smartfrigde.utils.Validation;
import com.project.smartfrigde.viewmodel.BMIViewModel;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;

public class MealFragment extends Fragment {
    private FragmentMealBinding fragmentMealBinding;
    private BMIViewModel bmiViewModel;
    private final Gson gson = new Gson();
    private String[] gender = {"MALE", "FEMALE"};
    private BmiRequest bmiRequest;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentMealBinding = FragmentMealBinding.inflate(inflater,container,false);
        bmiViewModel = new BMIViewModel();
        fragmentMealBinding.setMealViewModel(bmiViewModel);
        progressDialog = new ProgressDialog(getContext(),R.layout.progressdialog);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(Validation.PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        ProgressDialog progressDialog = new ProgressDialog(getContext(),R.layout.progressdialog);
        String jsonBMI = sharedPreferences.getString(Validation.KEY_BMI, null);
        if (jsonBMI != null) {
            progressDialog.dismiss();
            Type type = new TypeToken<BmiRequest>() {}.getType();
            bmiRequest =  gson.fromJson(jsonBMI, type);
            bmiViewModel.setBmiRequest(bmiRequest);
            bmiViewModel.bindingData();
            bmiViewModel.getIs_show_calories().set(true);
        } else {
            progressDialog.show();
            bmiViewModel.getBmi(editor);
        }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(requireContext(),
                android.R.layout.simple_dropdown_item_1line, gender);
        fragmentMealBinding.gender.setAdapter(adapter);
        bmiViewModel.getIsCaculateCalories().addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                if (Boolean.TRUE.equals(bmiViewModel.getIsCaculateCalories().get())){
                    bmiViewModel.saveBmi();
                }
            }
        });
        bmiViewModel.getIsLoadCalories().addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                if (Boolean.TRUE.equals(bmiViewModel.getIsLoadCalories().get())){
                    progressDialog.dismiss();
                }else {
                    progressDialog.show();
                }
            }
        });
        bmiViewModel.getIsLoadCalories().addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                if (Boolean.TRUE.equals(bmiViewModel.getIsLoadCalories().get())){
                    progressDialog.dismiss();
                    Double calories = bmiViewModel.getBmiRequest().getCalories();
                    bmiViewModel.getTotal_calories().set("You need to eat "+String.valueOf(calories.intValue())+ " Calories");
                    bmiViewModel.getIs_show_calories().set(Boolean.TRUE);
                    showToast("Add Bmi successfully !");
                }else {
                    progressDialog.show();
                }
            }
        });
        return fragmentMealBinding.getRoot();

    }
    public void showToast(String msg){
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
