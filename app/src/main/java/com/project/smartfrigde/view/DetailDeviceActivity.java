package com.project.smartfrigde.view;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;

import com.project.smartfrigde.R;
import com.project.smartfrigde.databinding.ActivityDetailDeviceBinding;
import com.project.smartfrigde.model.User;
import com.project.smartfrigde.utils.UserSecurePreferencesManager;
import com.project.smartfrigde.viewmodel.DetailDeviceViewModel;

import java.util.List;

public class DetailDeviceActivity extends AppCompatActivity {
    private ActivityDetailDeviceBinding activityDetailDeviceBinding;
    private DetailDeviceViewModel detailDeviceViewModel;
    private User user = UserSecurePreferencesManager.getUser();
    List<>
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDetailDeviceBinding = DataBindingUtil.setContentView(this,R.layout.activity_detail_device);
        detailDeviceViewModel = new DetailDeviceViewModel();
        activityDetailDeviceBinding.setDetailDeviceViewModel(detailDeviceViewModel);

    }
}