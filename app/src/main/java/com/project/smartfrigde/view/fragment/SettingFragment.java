package com.project.smartfrigde.view.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.databinding.Observable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.project.smartfrigde.R;
import com.project.smartfrigde.databinding.FragmentHomeBinding;
import com.project.smartfrigde.databinding.FragmentSettingBinding;
import com.project.smartfrigde.utils.UserSecurePreferencesManager;
import com.project.smartfrigde.view.LoginActivity;
import com.project.smartfrigde.viewmodel.SettingViewModel;

public class SettingFragment extends Fragment {
    FragmentSettingBinding fragmentSettingBinding;
    private SettingViewModel settingViewModel;
    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentSettingBinding = FragmentSettingBinding.inflate(inflater, container, false);
        settingViewModel = new SettingViewModel();
        fragmentSettingBinding.setSettingViewmodel(settingViewModel);
        Glide.with(this)
                .load(UserSecurePreferencesManager.getUser().getImage_src())
                .placeholder(R.drawable.account)
                .error(R.drawable.error)
                .into( fragmentSettingBinding.images);
        fragmentSettingBinding.userName.setText(UserSecurePreferencesManager.getUser().getFirst_name()+" "+UserSecurePreferencesManager.getUser().getLast_name());
        settingViewModel.getIsLogout().addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                if (Boolean.TRUE.equals(settingViewModel.getIsLogout().get())){
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
            }
        });
        return fragmentSettingBinding.getRoot();
    }
}