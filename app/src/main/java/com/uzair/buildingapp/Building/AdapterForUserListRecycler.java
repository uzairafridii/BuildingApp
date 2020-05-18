package com.uzair.buildingapp.Building;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uzair.buildingapp.R;

import java.util.List;

public class AdapterForUserListRecycler extends RecyclerView.Adapter<AdapterForUserListRecycler.MyUserViewHolder>
{

    private Context context;
    private List<UsersModel> list;

    public AdapterForUserListRecycler(Context context, List<UsersModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View myView = LayoutInflater.from(context).inflate(R.layout.design_for_user_list_recycler , null);
        return new MyUserViewHolder(myView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyUserViewHolder holder, int position)
    {
        UsersModel usersModel = list.get(position);
        holder.userName.setText(usersModel.getName());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyUserViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView userImage;
        private TextView userName;
        private View mView;

        public MyUserViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;

            userImage = mView.findViewById(R.id.userImage);
            userName = mView.findViewById(R.id.userName);
        }
    }
}
