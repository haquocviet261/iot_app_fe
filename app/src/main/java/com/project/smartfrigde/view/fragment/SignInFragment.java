package com.project.smartfrigde.view.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.project.smartfrigde.R;
import com.project.smartfrigde.view.UserManagementActivity;

public class SignInFragment extends Fragment {

    private ViewPager2 viewPager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        viewPager = getActivity().findViewById(R.id.view_pager_2);

        TextView tvForgetPassword = view.findViewById(R.id.tv_forget_password);
        TextView tvSignUp = view.findViewById(R.id.tv_sign_up);
        Button signInButton = view.findViewById(R.id.bt_sign_in);

        tvForgetPassword.setOnClickListener(v -> {
            viewPager.setCurrentItem(2); // Index for Forget Password Fragment
        });

        tvSignUp.setOnClickListener(v -> {
            viewPager.setCurrentItem(1); // Index for Sign Up Fragment
        });

        signInButton.setOnClickListener(v -> {
            // Navigate to User Management activity
            Intent intent = new Intent(getActivity(), UserManagementActivity.class);
            startActivity(intent);
        });

        return view;
    }
}