package com.project.smartfrigde.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.project.smartfrigde.R;
import com.project.smartfrigde.adapter.HomePagerAdapter;
import com.project.smartfrigde.databinding.ActivityDashboardBinding;
import com.project.smartfrigde.model.User;
import com.project.smartfrigde.utils.UserSecurePreferencesManager;
import com.project.smartfrigde.view.fragment.FoodRecommendFragment;
import com.project.smartfrigde.view.fragment.HomeFragment;
import com.project.smartfrigde.view.fragment.MealFragment;
import com.project.smartfrigde.view.fragment.SettingFragment;
import com.project.smartfrigde.viewmodel.HomeViewmodel;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {
    private ActivityDashboardBinding activityDashboardBinding;
    List<Fragment> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityDashboardBinding = DataBindingUtil.setContentView(this,R.layout.activity_dashboard);
        HomeViewmodel homeViewModel = new HomeViewmodel();
        HomePagerAdapter homePagerAdapter = new HomePagerAdapter(this, list);
        activityDashboardBinding.page.setAdapter(homePagerAdapter);
        list.add(new HomeFragment());
        list.add(new MealFragment());
        list.add(new FoodRecommendFragment());
        list.add(new SettingFragment());

        activityDashboardBinding.bottomNavigation.setOnItemSelectedListener( menuItem -> {
                if (menuItem.getItemId() == R.id.home) {
                    activityDashboardBinding.page.setCurrentItem(0);
                    return true;
                }else if (menuItem.getItemId() == R.id.meal) {
                    activityDashboardBinding.page.setCurrentItem(1);
                    return true;
                } else if (menuItem.getItemId() == R.id.food_rec) {
                    activityDashboardBinding.page.setCurrentItem(2);
                    return true;
                } else if (menuItem.getItemId() == R.id.me) {
                    activityDashboardBinding.page.setCurrentItem(3);
                    return true;
                }
                return false;
        });

        activityDashboardBinding.page.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                activityDashboardBinding.bottomNavigation.getMenu().getItem(position).setChecked(true);
                super.onPageSelected(position);
            }
        });
        homeViewModel.getSelectedPage().observe(this,page -> activityDashboardBinding.page.setCurrentItem(page,true));

    }
}