package com.project.smartfrigde.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.smartfrigde.R;
import com.project.smartfrigde.adapter.UserAdapter;
import com.project.smartfrigde.model.User;
import com.project.smartfrigde.view.fragment.AddUserDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class UserManagementActivity extends AppCompatActivity implements AddUserDialogFragment.AddUserDialogListener {

    private RecyclerView userRecyclerView;
    private UserAdapter userAdapter;
    private List<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_management);

        // Initialize UI components
        userRecyclerView = findViewById(R.id.user_recycler_view);
        View btnBack = findViewById(R.id.btn_back);
        View btnAddUser = findViewById(R.id.btn_add_user);

        // Set up RecyclerView
        userList = new ArrayList<>();
        userRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        userAdapter = new UserAdapter(userList);
        userRecyclerView.setAdapter(userAdapter);

        // Set click listener for back button
        btnBack.setOnClickListener(v -> finish()); // Close the activity

        // Set click listener for add user button
        btnAddUser.setOnClickListener(v -> {
            // Show AddUserDialogFragment
            AddUserDialogFragment dialog = new AddUserDialogFragment(UserManagementActivity.this);
            dialog.show(getSupportFragmentManager(), "AddUserDialogFragment");
        });

//         Load user data
        loadUsers();
    }

    private void loadUsers() {
        // Here you should load your users, e.g., from a database or API
        // For demo purposes, let's add some mock users
        userList.add(new User("Bao", "baotq000000000", "00000000000000"));
        userList.add(new User("Viet", "viethq111111111", "1111111111111111"));
        userList.add(new User("Duc", "ductv222222222222", "2222222222222"));
        userList.add(new User("Bao", "baotq000000000", "00000000000000"));
        userList.add(new User("Viet", "viethq111111111", "1111111111111111"));
        userList.add(new User("Duc", "ductv222222222222", "2222222222222"));

        // Notify adapter about data changes
        userAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDialogPositiveClick(String email) {
        // Add new user to the list
//        userList.add(new User(email));
//        userAdapter.notifyDataSetChanged();
    }
}
