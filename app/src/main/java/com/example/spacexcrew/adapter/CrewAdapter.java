package com.example.spacexcrew.adapter;

import android.content.Context;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.spacexcrew.R;
import com.example.spacexcrew.model.Crew;

import java.util.ArrayList;
import java.util.List;

public class CrewAdapter extends RecyclerView.Adapter<CrewAdapter.CrewViewHolder> {

    private Context context;
    private List<Crew> crewList;

    public CrewAdapter(Context context, List<Crew> crewList) {
        this.context = context;
        this.crewList = crewList;
    }

    @NonNull
    @Override
    public CrewAdapter.CrewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CrewViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.crew_member_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CrewAdapter.CrewViewHolder holder, int position) {
        Crew crew = crewList.get(position);
        Log.d("adapter", "onBindViewHolder: " + crew.getName());
        holder.name.setText(crew.getName());
        holder.agency.setText(crew.getAgency());
        holder.status.setText(crew.getStatus());
        holder.wikipedia.setText(crew.getWikipedia());
        holder.wikipedia.setMovementMethod(LinkMovementMethod.getInstance());

        Glide.with(context)
                .load(crew.getImage())
                .into(holder.image);
    }

    public void getAllCrew(List<Crew> crewList) {
        this.crewList = crewList;
    }

    @Override
    public int getItemCount() {
        return crewList.size();
    }

    public static class CrewViewHolder extends RecyclerView.ViewHolder {

        public TextView name, agency, status, wikipedia;
        public ImageView image;

        public CrewViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_name);
            agency = itemView.findViewById(R.id.tv_agency);
            status = itemView.findViewById(R.id.tv_status);
            wikipedia = itemView.findViewById(R.id.tv_wikipedia);
            image = itemView.findViewById(R.id.iv_image);
        }
    }
}
