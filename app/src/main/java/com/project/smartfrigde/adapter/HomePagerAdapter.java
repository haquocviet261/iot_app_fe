package com.project.smartfrigde.adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.project.smartfrigde.view.fragment.FoodRecommendFragment;
import com.project.smartfrigde.view.fragment.HomeFragment;
import com.project.smartfrigde.view.fragment.MealFragment;
import com.project.smartfrigde.view.fragment.SettingFragment;

import java.util.ArrayList;
import java.util.List;

public class HomePagerAdapter extends FragmentStateAdapter {
    List<Fragment> list = new ArrayList<>();
    private Bundle homeFragmentBundle;
    private Bundle mealFragmentBundle;
    private Bundle foodRecommendFragmentBundle;

    public HomePagerAdapter(@NonNull FragmentActivity fragmentActivity,List<Fragment> list) {
        super(fragmentActivity);
        homeFragmentBundle = new Bundle();
        foodRecommendFragmentBundle = new Bundle();
        mealFragmentBundle = new Bundle();
        this.list = list;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                HomeFragment homeFragment = new HomeFragment();
                homeFragment.setArguments(homeFragmentBundle);
                return homeFragment;
            case 1:
                MealFragment mealFragment = new MealFragment();
                mealFragment.setArguments(mealFragmentBundle);
                return mealFragment;
            case 2:
                FoodRecommendFragment foodRecommendFragment = new FoodRecommendFragment();
                foodRecommendFragment.setArguments(foodRecommendFragmentBundle);
                return foodRecommendFragment;
            case 3:
                return new SettingFragment();
        }
        return list.get(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setHomeFragmentBundle(Bundle bundle) {
        if (bundle != null) {
            this.homeFragmentBundle = bundle;
            notifyDataSetChanged();
        }
    }
    public void setMealFragment(Bundle bundle) {
        if (bundle != null) {
            this.mealFragmentBundle = bundle;
            notifyDataSetChanged();
        }
    }

    public void setFoodRecommendFragmentBundle(Bundle bundle) {
        if (bundle != null) {
            this.foodRecommendFragmentBundle = bundle;
            notifyDataSetChanged();
        }
    }
}
