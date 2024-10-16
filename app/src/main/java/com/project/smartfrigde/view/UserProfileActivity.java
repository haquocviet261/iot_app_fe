package com.project.smartfrigde.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.project.smartfrigde.R;

public class UserProfileActivity extends AppCompatActivity {

    private TextView usernameInput, addressInput, phoneInput, emailInput;
    private ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        // Khởi tạo các trường thông tin người dùng
        usernameInput = findViewById(R.id.username_input);
        addressInput = findViewById(R.id.tv_address);
        phoneInput = findViewById(R.id.tv_phone);
        emailInput = findViewById(R.id.tv_email);
        btnBack = findViewById(R.id.btn_back);

        // Lấy thông tin người dùng từ Intent hoặc nguồn khác và hiển thị
        // Giả sử bạn đã có thông tin người dùng, bạn có thể thay thế bằng dữ liệu thực tế
        usernameInput.setText("Tên người dùng"); // Thay thế bằng dữ liệu thực tế
        addressInput.setText("Địa chỉ"); // Thay thế bằng dữ liệu thực tế
        phoneInput.setText("Số điện thoại"); // Thay thế bằng dữ liệu thực tế
        emailInput.setText("Email"); // Thay thế bằng dữ liệu thực tế

        // Thiết lập sự kiện nhấn nút Back
        btnBack.setOnClickListener(v -> finish());
    }
}
