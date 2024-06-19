package com.project.smartfrigde.adapter;

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

    public HomePagerAdapter(@NonNull FragmentActivity fragmentActivity, List<Fragment> list) {
        super(fragmentActivity);
        this.list = list;
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new HomeFragment();
            case 1:
                return new MealFragment();
            case 2:
                return new FoodRecommendFragment();
            case 3:
                return new SettingFragment();
        }
        return list.get(position);
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
