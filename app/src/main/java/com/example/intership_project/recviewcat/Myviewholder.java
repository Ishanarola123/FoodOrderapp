package com.example.intership_project.recviewcat;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.intership_project.R;

public class Myviewholder extends RecyclerView.ViewHolder {
    ImageView img;
    TextView tv;

    public Myviewholder(@NonNull View itemView) {
        super(itemView);
        img=itemView.findViewById(R.id.imgcategories);
        tv=itemView.findViewById(R.id.tvcategories);


    }
}
