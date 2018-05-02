package com.quanghuy.busmap.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.quanghuy.busmap.R;
import com.quanghuy.busmap.entity.User;

import java.util.List;

/**
 * Created by Huy on 4/15/2018.
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private final String TAG = "UserAdapter";
    private List<User> users;

    public UserAdapter(List<User> users) {
        this.users = users;
        Log.d(TAG, "UserAdapter: " + users.size());
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: ");
        return new UserViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item_row, parent, false));
    }

    @Override
    public void onBindViewHolder(final UserViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: ");
        User user = users.get(position);
//        holder.textName.setText(user.getFullName() + " " + user.getLastName());
        holder.textUsername.setText(user.getUsername());

        holder.itemView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
                contextMenu.add(holder.getAdapterPosition(), 0, 0, "Sửa");
                contextMenu.add(holder.getAdapterPosition(), 1, 0, "Xóa");
            }
        });
    }



    @Override
    public int getItemCount() {
        return users.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder {
        TextView textUsername, textName;

        public UserViewHolder(View itemView) {
            super(itemView);
            Log.d(TAG, "UserViewHolder: ");
            textUsername = itemView.findViewById(R.id.tvUsername);
            textName = itemView.findViewById(R.id.tvName);
        }
    }
}

