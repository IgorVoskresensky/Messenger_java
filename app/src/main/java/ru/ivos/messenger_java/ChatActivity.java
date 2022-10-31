package ru.ivos.messenger_java;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private static final String EXTRA_CURRENT_USER_ID = "current_id";
    private static final String EXTRA_USER_USER_ID = "user_id";

    private TextView tvTitleCA;
    private View vStatusCA;
    private RecyclerView rvMessagesCA;
    private EditText etEnterMessageCA;
    private ImageView ivSendMessage;

    private MessagesAdapter adapter;

    private ChatViewModel viewModel;
    private ChatViewModelFactory factory;

    private String currentUserId;
    private String userUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initViews();
        currentUserId = getIntent().getStringExtra(EXTRA_CURRENT_USER_ID);
        userUserId = getIntent().getStringExtra(EXTRA_USER_USER_ID);

        factory = new ChatViewModelFactory(currentUserId, userUserId);
        viewModel = new ViewModelProvider(this, factory).get(ChatViewModel.class);

        adapter = new MessagesAdapter(currentUserId);
        rvMessagesCA.setAdapter(adapter);

        observeViewModel();

        ivSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message message = new Message(
                        etEnterMessageCA.getText().toString().trim(),
                        currentUserId,
                        userUserId
                );
                viewModel.sendMessage(message);
            }
        });
    }

    private void initViews() {
        tvTitleCA = findViewById(R.id.tvTitleCA);
        vStatusCA = findViewById(R.id.vStatusCA);
        rvMessagesCA = findViewById(R.id.rvMessagesCA);
        etEnterMessageCA = findViewById(R.id.etEnterMessageCA);
        ivSendMessage = findViewById(R.id.ivSendMessage);
    }


    @Override
    protected void onResume() {
        super.onResume();
        viewModel.setUserOnline(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        viewModel.setUserOnline(false);
    }

    private void observeViewModel() {
        viewModel.getMessages().observe(this, new Observer<List<Message>>() {
            @Override
            public void onChanged(List<Message> messages) {
                adapter.setMessages(messages);
            }
        });
        viewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String error) {
                if(error != null){
                    Toast.makeText(ChatActivity.this, error, Toast.LENGTH_LONG).show();
                }
            }
        });
        viewModel.getMessageSent().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    etEnterMessageCA.setText("");
                }
            }
        });
        viewModel.getUserUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                String userInfo = String.format("%s %s", user.getName(), user.getSecondName());
                tvTitleCA.setText(userInfo);

                int backResId;
                if(user.isOnline()){
                    backResId = R.drawable.cyrcle_green;
                } else {
                    backResId = R.drawable.cyrcle_red;
                }

                Drawable drawable = ContextCompat.getDrawable(ChatActivity.this, backResId);
                vStatusCA.setBackground(drawable);
            }
        });
    }

    public static Intent newIntent(Context context, String currentUserId, String userUserId) {
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra(EXTRA_CURRENT_USER_ID, currentUserId);
        intent.putExtra(EXTRA_USER_USER_ID, userUserId);
        return intent;
    }
}