package com.project.smartfrigde.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.project.smartfrigde.R;
import com.project.smartfrigde.databinding.FragmentFoodRecommendBinding;
import com.project.smartfrigde.databinding.FragmentMealBinding;
import com.project.smartfrigde.viewmodel.BMIViewModel;

import java.util.Objects;

public class MealFragment extends Fragment {
    private FragmentMealBinding fragmentMealBinding;
    private BMIViewModel bmiViewModel;
    private String[] gender = {"Male", "Female"};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentMealBinding = FragmentMealBinding.inflate(inflater,container,false);
        bmiViewModel = new BMIViewModel();
        fragmentMealBinding.setMealViewModel(bmiViewModel);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(requireContext(),
                android.R.layout.simple_dropdown_item_1line, gender);
        fragmentMealBinding.gender.setAdapter(adapter);
        return fragmentMealBinding.getRoot();
    }
}
