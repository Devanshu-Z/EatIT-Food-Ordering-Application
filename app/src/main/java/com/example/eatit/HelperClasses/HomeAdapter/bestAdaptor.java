package com.example.eatit.HelperClasses.HomeAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.eatit.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class bestAdaptor extends RecyclerView.Adapter<bestAdaptor.bestViewHolder> {

    ArrayList<best_HelperClass> bestLocations;

    public bestAdaptor(ArrayList<best_HelperClass> bestLocations) {
        this.bestLocations = bestLocations;
    }


    @NonNull
    @Override
    public bestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.best_card_design, parent, false);
        bestViewHolder bestViewHolder = new bestViewHolder(view);
        return bestViewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull bestViewHolder holder, int position) {

        best_HelperClass best_helperClass = bestLocations.get(position);
        holder.image.setImageResource(best_helperClass.getImage());
        holder.title.setText(best_helperClass.getTitle());
        holder.desc.setText((best_helperClass.getDesc()));

    }


    @Override
    public int getItemCount() {
        return bestLocations.size();
    }

    public static class bestViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView title, desc;

        public bestViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.best_img);
            title = itemView.findViewById(R.id.best_name);
            desc = itemView.findViewById(R.id.best_desc);

        }
    }
}
