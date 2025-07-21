package com.example.carbooking.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carbooking.Entity.User;
import com.example.carbooking.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserViewHolder> {
    private List<User> users = null;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onEditClick(int position);

        void onRemoveClick(int position);
        void onItemClick(int position);
    }

    public UserListAdapter(List<User> users, Context context, OnItemClickListener onItemClickListener) {
        this.users = users;
        this.context = context;
        this.onItemClickListener = onItemClickListener;  // Set the listener here
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_user, parent, false);
        return new UserViewHolder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = users.get(position);
        holder.tvUsername.setText("" + user.getUserName());
        holder.tvPhone.setText("" + user.getPhoneNumber());
        holder.tvEmail.setText("" + user.getEmail());
        if (user.isLocked() == true) {
            holder.tvStatus.setText("Locked");
            holder.imbEdit.setImageResource(R.drawable.active_icon);
        } else {
            holder.tvStatus.setText("Active");
            holder.imbEdit.setImageResource(R.drawable.ban_icon);
        }
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        private TextView tvUsername,
                tvPhone,
                tvEmail,
                tvStatus;
        private ImageButton imbRemove, imbEdit;

        public UserViewHolder(@NotNull View itemView, OnItemClickListener listener) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tv_username);
            tvEmail = itemView.findViewById(R.id.tv_email);
            tvPhone = itemView.findViewById(R.id.tv_phone);
            tvStatus = itemView.findViewById(R.id.tv_status);
            imbEdit = itemView.findViewById(R.id.imb_set_status);
            imbRemove = itemView.findViewById(R.id.imb_user_remove);

            itemView.setOnClickListener(new View.OnClickListener() {  // Thêm sự kiện click cho toàn bộ item
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
            imbEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onEditClick(position);
                        }
                    }
                }
            });
            imbRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION)
                            listener.onRemoveClick(position);
                    }
                }
            });
        }
    }
}
