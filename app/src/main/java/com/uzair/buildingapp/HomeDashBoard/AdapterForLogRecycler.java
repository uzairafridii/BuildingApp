package com.uzair.buildingapp.HomeDashBoard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.uzair.buildingapp.R;

import java.util.List;

public class AdapterForLogRecycler extends RecyclerView.Adapter<AdapterForLogRecycler.MyLogViewHolder> {
    private Context context;
    private List<LogModel> buildingModelList;

    public AdapterForLogRecycler(Context context, List<LogModel> buildingModelList) {
        this.context = context;
        this.buildingModelList = buildingModelList;
    }

    @NonNull
    @Override
    public MyLogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myView = LayoutInflater.from(context).inflate(R.layout.home_log_recycler_design, null);
        return new MyLogViewHolder(myView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyLogViewHolder holder, int position)
    {

        final LogModel model = buildingModelList.get(position);
      //  holder.name.setText(model.ge());
        holder.distance.setText(model.getDistance()+" km");
        holder.setImageView(model.getDetectionType());

    }

    @Override
    public int getItemCount() {
        return buildingModelList.size();
    }

    public class MyLogViewHolder extends RecyclerView.ViewHolder {
        private TextView name, distance;
        private CardView cardView;
        private ImageView imageView;
        private View mView;


        public MyLogViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            name = itemView.findViewById(R.id.logName);
            distance = itemView.findViewById(R.id.logDistance);
            cardView = itemView.findViewById(R.id.buildingCard);
        }

        private void setImageView(String image) {
            imageView = mView.findViewById(R.id.logImage);

            switch (image) {
                case "Sky Blue": {
                    imageView.setImageResource(R.drawable.sky_blue);
                    break;
                }
                case "Red": {
                    imageView.setImageResource(R.drawable.red);
                    break;
                }
                case "Green": {
                    imageView.setImageResource(R.drawable.circle);
                    break;
                }
                case "Parrot": {
                    imageView.setImageResource(R.drawable.parrot);
                    break;
                }
                case "Pink": {
                    imageView.setImageResource(R.drawable.pink);
                    break;
                }
                case "Yellow": {
                    imageView.setImageResource(R.drawable.yellow);
                    break;
                }
                case "Purple": {
                    imageView.setImageResource(R.drawable.purple);
                    break;
                }
                case "Orange": {
                    imageView.setImageResource(R.drawable.orange);
                    break;
                }

                default:
                    {
                        imageView.setImageResource(R.drawable.pink);
                    break;
                }


            }

        }
    }
}
