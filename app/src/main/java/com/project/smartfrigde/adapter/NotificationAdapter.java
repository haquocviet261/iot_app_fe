package com.project.smartfrigde.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.smartfrigde.R;
import com.project.smartfrigde.model.Notification;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    private List<Notification> notificationList;

    public NotificationAdapter(List<Notification> notificationList) {
        this.notificationList = notificationList;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_item, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        Notification notification = notificationList.get(position);
        holder.notificationTime.setText(notification.getTime());
        holder.notificationTitle.setText(notification.getTitle());
        holder.notificationContent.setText(notification.getContent());
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    static class NotificationViewHolder extends RecyclerView.ViewHolder {
        TextView notificationTime;
        TextView notificationTitle;
        TextView notificationContent;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            notificationTime = itemView.findViewById(R.id.notification_date_time);
            notificationTitle = itemView.findViewById(R.id.notification_title);
            notificationContent = itemView.findViewById(R.id.notification_content);
        }
    }
}