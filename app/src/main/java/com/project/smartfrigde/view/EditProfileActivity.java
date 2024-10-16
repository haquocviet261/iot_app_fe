package com.project.smartfrigde.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.project.smartfrigde.R;

public class EditProfileActivity extends AppCompatActivity {

    private EditText usernameInput, addressInput, phoneInput, emailInput;
    private Button saveButton;
    private ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        // Initialize EditText fields and button
        usernameInput = findViewById(R.id.username_input);
        addressInput = findViewById(R.id.tv_address);
        phoneInput = findViewById(R.id.tv_phone);
        emailInput = findViewById(R.id.tv_email);
        saveButton = findViewById(R.id.bt_save);
        btnBack = findViewById(R.id.btn_back);

        // Set back button click listener
        btnBack.setOnClickListener(v -> finish());

        // Set save button click listener
        saveButton.setOnClickListener(v -> {
            String username = usernameInput.getText().toString().trim();
            String address = addressInput.getText().toString().trim();
            String phone = phoneInput.getText().toString().trim();
            String email = emailInput.getText().toString().trim();

            // Validate input data
            if (username.isEmpty() || address.isEmpty() || phone.isEmpty() || email.isEmpty()) {
                Toast.makeText(this, "Please fill in all the information.", Toast.LENGTH_SHORT).show();
            } else {
                // Call the method to save user profile data
                saveUserProfile(username, address, phone, email);
            }
        });
    }

    private void saveUserProfile(String username, String address, String phone, String email) {
        // Perform user profile data saving logic here
        // For example: Update user information in a database or API

        // Notify the user after successful save
        Toast.makeText(this, "Information has been saved.", Toast.LENGTH_SHORT).show();
        finish(); // Close Activity
    }
}