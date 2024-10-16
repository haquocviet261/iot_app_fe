package com.project.smartfrigde.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.project.smartfrigde.view.fragment.ForgetPasswordFragment;
import com.project.smartfrigde.view.fragment.SignInFragment;
import com.project.smartfrigde.view.fragment.SignUpFragment;

public class SignInPagerAdapter extends FragmentStateAdapter {

    public SignInPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new SignInFragment();
            case 1:
                return new SignUpFragment();
            case 2:
                return new ForgetPasswordFragment();
            default:
                return new SignInFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
