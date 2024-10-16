package com.project.smartfrigde.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.project.smartfrigde.R;
import com.project.smartfrigde.adapter.SignInPagerAdapter;
import com.shobhitpuri.custombuttons.GoogleSignInButton;

public class SignInActivity extends AppCompatActivity {

    private ViewPager2 viewPager2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        viewPager2 = findViewById(R.id.view_pager_2);

        SignInPagerAdapter signInPagerAdapter = new SignInPagerAdapter(this);
        viewPager2.setAdapter(signInPagerAdapter);
    }
}