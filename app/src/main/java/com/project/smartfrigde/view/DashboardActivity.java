package com.project.smartfrigde.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import com.project.smartfrigde.R;
import com.project.smartfrigde.adapter.HomePagerAdapter;
import com.project.smartfrigde.databinding.ActivityDashboardBinding;
import com.project.smartfrigde.model.BluetoothDevice;
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
    private  HomePagerAdapter homePagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityDashboardBinding = DataBindingUtil.setContentView(this,R.layout.activity_dashboard);
        HomeViewmodel homeViewModel = new HomeViewmodel();
        list.add(new HomeFragment());
        list.add(new MealFragment());
        list.add(new FoodRecommendFragment());
        list.add(new SettingFragment());
         homePagerAdapter = new HomePagerAdapter(this,list);
        activityDashboardBinding.page.setAdapter(homePagerAdapter);
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
        if (getIntent() != null ) {
            handleIntent(getIntent());
        }
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }
    private void handleIntent(Intent intent) {
        Bundle bundle = new Bundle();
        if (intent != null && (intent.hasExtra("lunch") || intent.hasExtra("dinner"))) {
            if (intent.hasExtra("lunch")) {
                bundle.putString("lunch", intent.getStringExtra("lunch"));
            }
            if (intent.hasExtra("dinner")) {
                bundle.putString("dinner", intent.getStringExtra("dinner"));
            }
            if (bundle.size() > 0) {
                FoodRecommendFragment fragment = new FoodRecommendFragment();
                fragment.setArguments(bundle);
                homePagerAdapter.setFoodRecommendFragmentBundle(bundle);
                activityDashboardBinding.page.setCurrentItem(2);
            }
        }
        if (intent != null && intent.hasExtra("calories")) {
            BluetoothDevice bluetoothDevice = intent.getParcelableExtra("device");
            bundle.putParcelable("device",bluetoothDevice);
            if (bundle.size() > 0) {
                MealFragment fragment = new MealFragment();
                fragment.setArguments(bundle);
                homePagerAdapter.setHomeFragmentBundle(bundle);
                activityDashboardBinding.page.setCurrentItem(1);
            }
        }
        if (intent != null && intent.hasExtra("device")) {
            BluetoothDevice bluetoothDevice = intent.getParcelableExtra("device");
            bundle.putParcelable("device",bluetoothDevice);
            if (bundle.size() > 0) {
                HomeFragment fragment = new HomeFragment();
                fragment.setArguments(bundle);
                homePagerAdapter.setHomeFragmentBundle(bundle);
                activityDashboardBinding.page.setCurrentItem(0);
            }
        }
    }
}