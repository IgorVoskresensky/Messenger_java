package ru.ivos.messenger_java;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ChatViewModelFactory implements ViewModelProvider.Factory {

    private String currentUserId;
    private String userUserId;

    public ChatViewModelFactory(String currentUserId, String userUserId) {
        this.currentUserId = currentUserId;
        this.userUserId = userUserId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ChatViewModel(currentUserId, userUserId);
    }
}
