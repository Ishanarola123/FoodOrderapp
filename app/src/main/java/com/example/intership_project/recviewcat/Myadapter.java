package com.example.intership_project.recviewcat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.intership_project.R;

import java.util.ArrayList;

public class Myadapter extends RecyclerView.Adapter<Myviewholder> {
    public Myadapter(ArrayList<Model> data) {
        this.data = data;
    }

    ArrayList<Model> data;

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.singlerow_categories,parent,false);
        return new Myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Myviewholder holder, int position) {
        holder.tv.setText(data.get(position).getCategoriesheader());
        holder.img.setImageResource(data.get(position).getImgname());


    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
