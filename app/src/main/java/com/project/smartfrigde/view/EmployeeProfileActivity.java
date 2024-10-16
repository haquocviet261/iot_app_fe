package com.project.smartfrigde.view;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.project.smartfrigde.R;

public class EmployeeProfileActivity extends AppCompatActivity {

    private TextView usernameInput, addressInput, phoneInput, dOBInput, emailInput;
    private ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_profile);

        // Khởi tạo các trường thông tin người dùng
        usernameInput = findViewById(R.id.username_input);
        addressInput = findViewById(R.id.tv_address);
        phoneInput = findViewById(R.id.tv_phone);
        dOBInput = findViewById(R.id.tv_dOB);
        emailInput = findViewById(R.id.tv_email);
        btnBack = findViewById(R.id.btn_back);

        // Lấy thông tin người dùng từ Intent hoặc nguồn khác và hiển thị
        // Giả sử bạn đã có thông tin người dùng, bạn có thể thay thế bằng dữ liệu thực tế
        usernameInput.setText("Tên người dùng"); // Thay thế bằng dữ liệu thực tế
        addressInput.setText("Địa chỉ"); // Thay thế bằng dữ liệu thực tế
        phoneInput.setText("Số điện thoại"); // Thay thế bằng dữ liệu thực tế
        dOBInput.setText("Ngày sinh"); // Thay thế bằng dữ liệu thực tế
        emailInput.setText("Email"); // Thay thế bằng dữ liệu thực tế

        // Thiết lập sự kiện nhấn nút Back
        btnBack.setOnClickListener(v -> finish());
    }
}
