package com.project.smartfrigde.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.Observable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.ai.client.generativeai.GenerativeModel;
import com.google.ai.client.generativeai.java.GenerativeModelFutures;
import com.google.ai.client.generativeai.type.Content;
import com.project.smartfrigde.R;
import com.project.smartfrigde.adapter.ChatMessageAdapter;
import com.project.smartfrigde.databinding.ActivityConversationUserBinding;
import com.project.smartfrigde.model.ChatMessage;
import com.project.smartfrigde.utils.Validation;
import com.project.smartfrigde.viewmodel.ChatViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ChatActivity extends AppCompatActivity {
    private ActivityConversationUserBinding activityConversationUserBinding;
    private ChatViewModel chatViewModel;
    private RecyclerView recyclerView;
    private ChatMessageAdapter chatMessageAdapter;
    private GridLayoutManager gridLayoutManager;
    GenerativeModel gm = new GenerativeModel("gemini-1.5-flash", Validation.APIKEY);
    GenerativeModelFutures model = GenerativeModelFutures.from(gm);
    List<ChatMessage> list = new ArrayList<>();
    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityConversationUserBinding = DataBindingUtil.setContentView(this, R.layout.activity_conversation_user);
        chatViewModel = new ChatViewModel();
        recyclerView = activityConversationUserBinding.chatContent;
        chatMessageAdapter = new ChatMessageAdapter();
        recyclerView.setAdapter(chatMessageAdapter);
        gridLayoutManager = new GridLayoutManager(this, 1);
        activityConversationUserBinding.setConversationViewModel(chatViewModel);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.scrollToPosition(chatMessageAdapter.getItemCount() - 1);
        chatViewModel.getOnclickBack().addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                if (Boolean.TRUE.equals(chatViewModel.getOnclickBack().get())) {
                    Intent intent = new Intent(ChatActivity.this,DashboardActivity.class);
                    startActivity(intent);
                }
            }
        });
        setupMessageSending();
        chatViewModel.getOnclickBack().addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                if (Boolean.TRUE.equals(chatViewModel.getOnclickBack().get())){
                    startActivity(new Intent(ChatActivity.this,DashboardActivity.class));
                }
            }
        });
        chatViewModel.getIs_loaded_data().addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                boolean isLoaded = chatViewModel.getIs_loaded_data().get();
                runOnUiThread(() -> {
                    if (isLoaded) {
                        chatMessageAdapter.removeLoading();
                        ChatMessage chatMessage = new ChatMessage(Validation.TYPE_BOT, chatViewModel.getRepply_message().get());
                        list.add(chatMessage);
                        chatMessageAdapter.setData(list);
                        recyclerView.scrollToPosition(chatMessageAdapter.getItemCount() - 1);
                    } else {
                        chatMessageAdapter.addLoading();
                    }
                });
            }
        });
    }

    private void setupMessageSending() {
        activityConversationUserBinding.sendButton.setOnClickListener(v -> {
            String text = activityConversationUserBinding.messageText.getText().toString();
            if (!text.equals("")) {
                Executor executor = Executors.newSingleThreadExecutor();

                Content content = new Content.Builder()
                        .addText(text)
                        .build();
                chatViewModel.callModel(executor, model, content);
                ChatMessage chatMessage = new ChatMessage(Validation.TYPE_USER, text);
                list.add(chatMessage);
                runOnUiThread(() -> {
                    chatMessageAdapter.setData(list);
                    recyclerView.scrollToPosition(chatMessageAdapter.getItemCount() - 1);
                });
                chatViewModel.analyzeText(text);
                activityConversationUserBinding.messageText.setText("");
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}