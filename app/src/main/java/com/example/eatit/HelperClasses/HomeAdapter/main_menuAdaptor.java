package com.example.eatit.HelperClasses.HomeAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eatit.R;
import com.example.eatit.menu_donalds;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class main_menuAdaptor extends RecyclerView.Adapter<main_menuAdaptor.mainViewHolder> {

    Context context;
    ArrayList<main_menuHelperClass> mainLocations;

    public main_menuAdaptor(Context context, ArrayList<main_menuHelperClass> mainLocations) {
        this.context = context;
        this.mainLocations = mainLocations;

    }



    @NonNull
    @Override
    public mainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.main_menu_design, parent, false);
        mainViewHolder mainViewHolder = new mainViewHolder(view);
        return mainViewHolder;

    }


    @Override
    public void onBindViewHolder(@NonNull mainViewHolder holder, int position) {

        main_menuHelperClass main_menuHelperClass = mainLocations.get(position);
        holder.image.setImageResource(main_menuHelperClass.getImage());
        holder.title.setText(main_menuHelperClass.getTitle());
        holder.desc.setText((main_menuHelperClass.getDesc()));


    }


    @Override
    public int getItemCount() {
        return mainLocations.size();
    }

    public class mainViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView image;
        TextView title, desc;


        public mainViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.main_menu_img);
            title = itemView.findViewById(R.id.main_menu_title);
            desc = itemView.findViewById(R.id.main_menu_desc);
            itemView.setOnClickListener(this);
        }



        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Toast.makeText(context, "Position "+position, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent( context , menu_donalds.class);
            context.startActivity(intent);


        }
    }



}
