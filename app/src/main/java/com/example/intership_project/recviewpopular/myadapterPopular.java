package com.example.intership_project.recviewpopular;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.intership_project.MainScreen2;
import com.example.intership_project.R;

import java.util.ArrayList;

public class myadapterPopular extends RecyclerView.Adapter<myviewholderPopular> implements Filterable {
    ArrayList<Modelpopular> data;
    ArrayList<Modelpopular> backup;
    Context context;

    public myadapterPopular(ArrayList<Modelpopular> data, Context context) {
        this.data = data;
        this.context = context;
        backup=new ArrayList<>(data);
    }

    @NonNull
    @Override
    public myviewholderPopular onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.singlerow_popular_categories,parent,false);
        return new myviewholderPopular(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholderPopular holder, int position) {
        //here the temp stores position of all object in to temp variable classes
        final Modelpopular temp=data.get(position);

        holder.itemname.setText(data.get(position).getItemName());
        holder.itemprice.setText(data.get(position).getItemPrice());
        holder.imageView.setImageResource(data.get(position).getImgName());

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //go to next intent activity which has design for particular card views
                Intent intent=new Intent(context, MainScreen2.class);
                intent.putExtra("imagename",temp.getImgName());
                intent.putExtra("itemname",temp.getItemName());
                intent.putExtra("itemprice",temp.getItemPrice());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }
    Filter filter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence keyword) {
            ArrayList<Modelpopular> filtered_data = new ArrayList<>();
            if (keyword.toString().isEmpty()){
                filtered_data.addAll(backup);
            }
            else {
                for (Modelpopular obj: backup){
                    if (obj.getItemName().toString().toLowerCase().contains(keyword.toString().toLowerCase()))
                        filtered_data.add(obj);

                }
            }
            FilterResults filterResults=new FilterResults();
            filterResults.values=filtered_data;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                 data.clear();
                 data.addAll( (ArrayList<Modelpopular>) filterResults.values);
                 notifyDataSetChanged();
        }
    };
}
