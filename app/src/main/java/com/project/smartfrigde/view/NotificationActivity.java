package com.project.smartfrigde.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.smartfrigde.R;
import com.project.smartfrigde.adapter.NotificationAdapter;
import com.project.smartfrigde.model.Notification;

import java.util.ArrayList;
import java.util.List;

public class NotificationActivity extends AppCompatActivity {

    private RecyclerView notificationRecyclerView;
    private NotificationAdapter notificationAdapter;
    private List<Notification> notificationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification); // Đảm bảo bạn có layout activity_notification.xml

        // Khởi tạo RecyclerView
        notificationRecyclerView = findViewById(R.id.notification_recycler_view);
        notificationRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Khởi tạo danh sách thông báo
        notificationList = new ArrayList<>();

        // Thêm các thông báo mẫu vào danh sách
        loadNotifications();

        // Khởi tạo adapter và gán vào RecyclerView
        notificationAdapter = new NotificationAdapter(notificationList);
        notificationRecyclerView.setAdapter(notificationAdapter);
    }

    private void loadNotifications() {
        // Thêm các thông báo mẫu
        notificationList.add(new Notification("14/10/2024 10:30 AM", "Thông báo 1", "Nội dung thông báo 1"));
        notificationList.add(new Notification("14/10/2024 11:00 AM", "Thông báo 2", "Nội dung thông báo 2"));
        notificationList.add(new Notification("14/10/2024 11:30 AM", "Thông báo 3", "Nội dung thông báo 3"));
        // Thêm nhiều thông báo khác nếu cần
    }
}
