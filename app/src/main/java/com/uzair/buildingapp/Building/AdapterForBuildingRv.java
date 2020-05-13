package com.uzair.buildingapp.Building;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uzair.buildingapp.R;

import java.util.List;

public class AdapterForBuildingRv extends RecyclerView.Adapter<AdapterForBuildingRv.MyBuildingViewHolder>
{
    private Context context;
    private List<BuildingModel> buildingModelList;

    public AdapterForBuildingRv(Context context, List<BuildingModel> buildingModelList) {
        this.context = context;
        this.buildingModelList = buildingModelList;
    }

    @NonNull
    @Override
    public MyBuildingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View myView = LayoutInflater.from(context).inflate(R.layout.design_for_building_recycler_view, null);
        return new MyBuildingViewHolder(myView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyBuildingViewHolder holder, int position)
    {
        BuildingModel model = buildingModelList.get(position);
        holder.name.setText(model.getName());
        holder.distance.setText(model.getDistance()+" km");

    }

    @Override
    public int getItemCount() {
        return buildingModelList.size();
    }

    public class MyBuildingViewHolder extends RecyclerView.ViewHolder
    {
        private TextView name , distance;

        public MyBuildingViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            distance = itemView.findViewById(R.id.distance);
        }

    }
}
