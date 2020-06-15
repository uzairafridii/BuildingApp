package com.uzair.buildingapp.AssignUsersBuilding;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.uzair.buildingapp.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterForUserListRecycler extends RecyclerView.Adapter<AdapterForUserListRecycler.MyUserViewHolder>
{
    private Context context;
    private List<AssignUsersModel> list;

    public AdapterForUserListRecycler(Context context, List<AssignUsersModel> list) {
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
        AssignUsersModel assignUsersModel = list.get(position);
        holder.userName.setText(assignUsersModel.getName());
        holder.userPhone.setText("+25"+ assignUsersModel.getPhone());
        holder.setImage(assignUsersModel.getAvatar());
        Log.d("avatarResult", "onBindViewHolder: "+assignUsersModel.getAvatar());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyUserViewHolder extends RecyclerView.ViewHolder
    {
        private CircleImageView userImage;
        private TextView userName , userPhone;
        private View mView;

        public MyUserViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;

            userName = mView.findViewById(R.id.userName);
            userPhone = mView.findViewById(R.id.userPone);
        }

        private void setImage(String imageUrl)
        {
            userImage = mView.findViewById(R.id.userImage);
            Glide.with(context)
                    .load(imageUrl)
                    .placeholder(R.drawable.avatar)
                    .into(userImage);
        }
    }
}
