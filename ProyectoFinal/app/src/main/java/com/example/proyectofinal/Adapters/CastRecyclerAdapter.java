package com.example.proyectofinal.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectofinal.Models.Cast;
import com.example.proyectofinal.R;

import java.util.List;

public class CastRecyclerAdapter extends RecyclerView.Adapter<CastViewHolder> {
    Context context;
    List<Cast> list;

    public CastRecyclerAdapter(Context context, List<Cast> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public CastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CastViewHolder(LayoutInflater.from(context).inflate(R.layout.cast_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CastViewHolder holder, int position) {
        holder.textViewActor.setText(list.get(position).getActor());
        holder.textViewCharacter.setText(list.get(position).getCharacter());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class CastViewHolder extends RecyclerView.ViewHolder {
    TextView textViewActor;
    TextView textViewCharacter;
    public CastViewHolder(@NonNull View itemView) {
        super(itemView);
        textViewActor = itemView.findViewById(R.id.textViewActor);
        textViewCharacter = itemView.findViewById(R.id.textViewCharacter);
    }
}