package com.project.smartfrigde.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.project.smartfrigde.R;
import com.project.smartfrigde.databinding.ItemLoadingBinding;
import com.project.smartfrigde.databinding.ItemReceiverMessageBinding;
import com.project.smartfrigde.databinding.ItemSenderMessageBinding;
import com.project.smartfrigde.model.ChatMessage;
import com.project.smartfrigde.model.User;
import com.project.smartfrigde.utils.UserSecurePreferencesManager;
import com.project.smartfrigde.utils.Validation;

import java.util.ArrayList;
import java.util.List;

public class ChatMessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private static final int  TYPE_SENDER = 0;
    private static final int  TYPE_BOT_CHAT = 1;
    private static final int TYPE_LOADING = 2;
    private boolean isLoading = false;
    private List<ChatMessage> list =new ArrayList<>();


    @SuppressLint("NotifyDataSetChanged")
    public void clearData() {
        list.clear();
        notifyDataSetChanged();
    }
    @SuppressLint("NotifyDataSetChanged")

    public void setData(List<ChatMessage> list){
        this.list = list;
        notifyDataSetChanged();
    }
    public List<ChatMessage> getData(){
        return this.list;
    }
    @SuppressLint("NotifyDataSetChanged")


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_SENDER:
                ItemSenderMessageBinding itemSenderMessageBinding = DataBindingUtil.inflate(
                        LayoutInflater.from(parent.getContext()), R.layout.item_sender_message, parent, false);
                return new SenderViewHolder(itemSenderMessageBinding);
            case TYPE_BOT_CHAT:
                ItemReceiverMessageBinding itemReceiverMessageBinding = DataBindingUtil.inflate(
                        LayoutInflater.from(parent.getContext()), R.layout.item_receiver_message, parent, false);
                return new ReceiverViewHolder(itemReceiverMessageBinding);
            case TYPE_LOADING:
                ItemLoadingBinding ItemLoadingBinding = DataBindingUtil.inflate(
                        LayoutInflater.from(parent.getContext()), R.layout.item_loading, parent, false);
                return new LoadingRecycleView(ItemLoadingBinding);
            default:
                throw new IllegalStateException("Unexpected view type: " + viewType);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SenderViewHolder) {
            ChatMessage chatMessage = list.get(position);
            ((SenderViewHolder) holder).itemSenderMessageBinding.leftChatTextView.setText(chatMessage.getContent());
        } else if (holder instanceof ReceiverViewHolder) {
            ChatMessage chatMessage = list.get(position);
            ((ReceiverViewHolder) holder).itemReceiverMessageBinding.rightChatTextView.setText(chatMessage.getContent());
        } else if (holder instanceof LoadingRecycleView) {
            ((LoadingRecycleView) holder).itemLoadingBinding.loading.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        }
    }
    public static class LoadingRecycleView extends RecyclerView.ViewHolder{
        private ItemLoadingBinding itemLoadingBinding;
        public LoadingRecycleView(@NonNull ItemLoadingBinding itemLoadingBinding) {
            super(itemLoadingBinding.getRoot());
            this.itemLoadingBinding = itemLoadingBinding;
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }
    @Override
    public int getItemViewType(int position) {
        if (list.get(position).getMessage_type() == 0){
            return TYPE_SENDER;
        } else if (list.get(position).getMessage_type() == 1) {
            return TYPE_BOT_CHAT;
        }else {
            return TYPE_LOADING;
        }
    }
    public static class SenderViewHolder extends RecyclerView.ViewHolder {
        private ItemSenderMessageBinding itemSenderMessageBinding;
        public SenderViewHolder(@NonNull ItemSenderMessageBinding itemSenderMessageBinding) {
            super(itemSenderMessageBinding.getRoot());
            this.itemSenderMessageBinding = itemSenderMessageBinding;
        }
    }
    public static class ReceiverViewHolder extends RecyclerView.ViewHolder {
        private ItemReceiverMessageBinding itemReceiverMessageBinding;
        public ReceiverViewHolder(@NonNull ItemReceiverMessageBinding itemReceiverMessageBinding) {
            super(itemReceiverMessageBinding.getRoot());
            this.itemReceiverMessageBinding = itemReceiverMessageBinding;
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    public void addLoading() {
        if (!isLoading) {
            isLoading = true;
            list.add(new ChatMessage(Validation.TYPE_LOADING, ""));
            notifyItemInserted(list.size() - 1);
        }
    }

    public void removeLoading() {
        for (int i = list.size() - 1; i >= 0; i--) {
            if (list.get(i).getMessage_type() == Validation.TYPE_LOADING) {
                list.remove(i);
                notifyItemRemoved(i);
                break; // Ngừng vòng lặp sau khi xóa phần tử loading
            }
        }
        isLoading = false;
    }
}
