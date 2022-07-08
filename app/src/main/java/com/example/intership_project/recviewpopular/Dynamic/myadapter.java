package com.example.intership_project.recviewpopular.Dynamic;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.intership_project.Fragments_bottomnav.HomeFragment;
import com.example.intership_project.MainScreen2;
import com.example.intership_project.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class myadapter extends FirebaseRecyclerAdapter<model,myviewholder> {
    public myadapter(@NonNull FirebaseRecyclerOptions<model> options) {
        super(options);
    }
    Context context;

    @Override
    protected void onBindViewHolder(@NonNull myviewholder myviewholder, @SuppressLint("RecyclerView") final int position, @NonNull model model) {
//        final int i=myviewholder.getAbsoluteAdapterPosition();
        myviewholder.itemname.setText(model.getFoodname() + "");
        myviewholder.itemprice.setText( "$"+ (int) model.getFoodPrice() + "");
        Glide.with(myviewholder.imageView.getContext())
                .load(model.getImageUrl())
                .placeholder(R.drawable.common_google_signin_btn_icon_dark)
                .circleCrop()
                .error(R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(myviewholder.imageView);
        //Glide.with(myviewholder.imageView.getContext()).load(model.getImageUrl()).into(myviewholder.imageView);
        myviewholder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainScreen2.class);
                intent.putExtra("foodname",getRef(position).getKey());
//                intent.putExtra("itemname",getRef(i).getItemName());
//                intent.putExtra("itemprice",temp.getItemPrice());
                v.getContext().startActivity(intent);

            }
        });
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow_popular_categories,parent,false);
        return new myviewholder(view);
    }
}
