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
    private Bundle bundle;

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
                FoodRecommendFragment fragment = new FoodRecommendFragment();
                fragment.setArguments(bundle);
                return fragment;
            case 3:
                return new SettingFragment();
        }
        return list.get(position);
    }

    public HomePagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        bundle = new Bundle();
    }
    public void setBundleForFragmentAtPosition(int position, Bundle bundle) {
        if (position == 2) {
            this.bundle = bundle;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
