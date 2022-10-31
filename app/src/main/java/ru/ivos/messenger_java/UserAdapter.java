package ru.ivos.messenger_java;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder>{

    private List<User> users = new ArrayList<>();
    private OnUserClickListener onUserClickListener;

    public void setUsers(List<User> users) {
        this.users = users;
        notifyDataSetChanged();
    }

    public void setOnUserClickListener(OnUserClickListener onUserClickListener) {
        this.onUserClickListener = onUserClickListener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.user_item,
                parent,
                false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = users.get(position);
        String userInfo = String.format("%s %s, %s", user.getName(), user.getSecondName(), user.getAge());
        holder.tvUserInfoUI.setText(userInfo);

        int backResId;
        if(user.isOnline()){
            backResId = R.drawable.cyrcle_green;
        } else {
            backResId = R.drawable.cyrcle_red;
        }

        Drawable drawable = ContextCompat.getDrawable(holder.itemView.getContext(), backResId);
        holder.vStatusUI.setBackground(drawable);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onUserClickListener != null){
                    onUserClickListener.onUserClick(user);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    interface OnUserClickListener {

        void onUserClick(User user);
    }

    static class UserViewHolder extends RecyclerView.ViewHolder{

        private TextView tvUserInfoUI;
        private View vStatusUI;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUserInfoUI = itemView.findViewById(R.id.tvUserInfoUI);
            vStatusUI = itemView.findViewById(R.id.vStatusUI);
        }
    }
}
