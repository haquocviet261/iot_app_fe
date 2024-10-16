package com.project.smartfrigde.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.project.smartfrigde.R;
import com.project.smartfrigde.model.User; // Ensure you have a User model
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private final List<User> userList;

    public UserAdapter(List<User> userList) {
        this.userList = userList;
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        public TextView tvCompany;
        public TextView tvEmail;
        public TextView tvPhone;
        public ImageButton buttonEdit;
        public ImageButton buttonDelete;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCompany = itemView.findViewById(R.id.tv_company);
            tvEmail = itemView.findViewById(R.id.tv_email);
            tvPhone = itemView.findViewById(R.id.tv_phone);
            buttonEdit = itemView.findViewById(R.id.button_edit);
            buttonDelete = itemView.findViewById(R.id.button_delete);
        }
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false); // Replace with your item layout
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);
        holder.tvCompany.setText(user.getUser_name());
        holder.tvEmail.setText(user.getEmail());
        holder.tvPhone.setText(user.getPhone_number());

        // Set up click listeners for edit and delete buttons
        holder.buttonEdit.setOnClickListener(v -> {
            // Handle edit button click
        });

        holder.buttonDelete.setOnClickListener(v -> {
            // Handle delete button click
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
}
