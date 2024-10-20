package com.project.smartfrigde.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.Observable;

import com.project.smartfrigde.R;
import com.project.smartfrigde.databinding.ActivityDashboardBinding;
import com.project.smartfrigde.viewmodel.DashBoardViewModel;

public class DashboardActivity extends AppCompatActivity {
    private ActivityDashboardBinding activityDashboardBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDashboardBinding = DataBindingUtil.setContentView(this,R.layout.activity_dashboard);
        DashBoardViewModel dashBoardViewModel = new DashBoardViewModel();
        activityDashboardBinding.setDashBoardViewModel(dashBoardViewModel);
        dashBoardViewModel.connect();
        dashBoardViewModel.getIsFood().addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                if (Boolean.TRUE.equals(dashBoardViewModel.getIsFood().get())){

                }
            }
        });
        dashBoardViewModel.getIsFood().set(false);
        dashBoardViewModel.getIsNotification().addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                if (Boolean.TRUE.equals(dashBoardViewModel.getIsNotification().get())){

                }
            }
        });
        dashBoardViewModel.getIsNotification().set(false);
        dashBoardViewModel.getIsUser().addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                if (Boolean.TRUE.equals(dashBoardViewModel.getIsUser().get())){

                }
            }
        });
        dashBoardViewModel.getIsUser().set(false);
        dashBoardViewModel.getIsStatistics().addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                if (Boolean.TRUE.equals(dashBoardViewModel.getIsStatistics().get())){

                }
            }
        });
        dashBoardViewModel.getIsStatistics().set(false);
        dashBoardViewModel.getIsDetailUser().addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                if (Boolean.TRUE.equals(dashBoardViewModel.getIsDetailUser().get())){

                }
            }
        });
        dashBoardViewModel.getIsDetailUser().set(false);
        dashBoardViewModel.getIsSetting().addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                if (Boolean.TRUE.equals(dashBoardViewModel.getIsSetting().get())){

                }
            }
        });
        dashBoardViewModel.getIsSetting().set(false);
    }
}