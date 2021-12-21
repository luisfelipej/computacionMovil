package com.example.proyectofinal.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectofinal.Models.Movie;
import com.example.proyectofinal.R;

import java.util.List;

public class SavedRecyclerAdapter extends RecyclerView.Adapter<SavedViewHolder> {
    Context context;

    public SavedRecyclerAdapter(Context context, List<Movie> list) {
        this.context = context;
        this.list = list;
    }

    List<Movie> list;
    @NonNull
    @Override
    public SavedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SavedViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_saved_movies, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SavedViewHolder holder, int position) {
        holder.textViewSavedMovieName.setText(list.get(position).getTitle());
        holder.cbHasViewed.setChecked(list.get(position).getHasViewed());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class SavedViewHolder extends RecyclerView.ViewHolder {
    TextView textViewSavedMovieName;
    CheckBox cbHasViewed;

    public SavedViewHolder(@NonNull View itemView) {
        super(itemView);
        textViewSavedMovieName = itemView.findViewById(R.id.textViewSavedMovieName);
        cbHasViewed = itemView.findViewById(R.id.cbHasViewed);
    }
}