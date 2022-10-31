package ru.ivos.messenger_java.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.ivos.messenger_java.entities.Message;
import ru.ivos.messenger_java.R;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MessagesViewHolder>{

    private static final int MY_MESSAGE = 1;
    private static final int USER_MESSAGE = 2;

    private List<Message> messages = new ArrayList<>();

    private String currentUserId;

    public MessagesAdapter(String currentUserId) {
        this.currentUserId = currentUserId;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MessagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutResId;
        if(viewType == MY_MESSAGE){
            layoutResId = R.layout.my_message_item;
        } else {
            layoutResId = R.layout.user_message_item;
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(
                layoutResId,
                parent,
                false
        );
        return new MessagesViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        Message message = messages.get(position);
        if(message.getSenderId().equals(currentUserId)){
            return MY_MESSAGE;
        } else {
            return USER_MESSAGE;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MessagesViewHolder holder, int position) {
        Message message = messages.get(position);
        holder.tvMessage.setText(message.getText());
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    static class MessagesViewHolder extends RecyclerView.ViewHolder{

        private TextView tvMessage;

        public MessagesViewHolder(@NonNull View itemView) {
            super(itemView);

            tvMessage = itemView.findViewById(R.id.tvMessage);
        }
    }
}
