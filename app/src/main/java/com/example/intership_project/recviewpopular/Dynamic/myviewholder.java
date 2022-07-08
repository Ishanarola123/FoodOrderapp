package com.example.intership_project.recviewpopular.Dynamic;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.intership_project.R;

public class myviewholder extends RecyclerView.ViewHolder {
    public ImageView imageView;
    TextView itemname;
    TextView itemprice;
    View v;
    public myviewholder(@NonNull View itemView) {
        super(itemView);
        imageView=itemView.findViewById(R.id.img_popular);
        itemname=itemView.findViewById(R.id.itemname);
        itemprice=itemView.findViewById(R.id.item_price);
        v=itemView;
    }

}
