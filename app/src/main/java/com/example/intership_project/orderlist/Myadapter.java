package com.example.intership_project.orderlist;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.intership_project.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

public class Myadapter extends FirebaseRecyclerAdapter<Model,Myadapter.myviewholder> {
    int order;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance()
            .getReferenceFromUrl("https://foodorderapp-a4e2a-default-rtdb.firebaseio.com/");
    public Myadapter(@NonNull FirebaseRecyclerOptions<Model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, @SuppressLint("RecyclerView") final int position, @NonNull Model model) {

//        holder.itemimage.setImageResource(R.drawable.cat_1);
        holder.textviewitemname.setText(model.getItemname() + "");
        holder.textviewprice.setText(model.getPrice() +"");
        holder.textvieworderquantity.setText("order : " + model.getOrderquantity());

        Glide.with(holder.itemimage.getContext())
                .load(model.getImageurl())
                .placeholder(R.drawable.common_google_signin_btn_icon_dark)
                .circleCrop()
                .error(R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(holder.itemimage);


        holder.EditBtn.setOnClickListener(v -> {
            //Toast.makeText(v.getContext(), "edit", Toast.LENGTH_SHORT).show();
            final  DialogPlus dialogPlus=DialogPlus.newDialog(v.getContext()).setContentHolder(new ViewHolder(R.layout.update_popup))
                                         .setExpanded(true,500).create();
            dialogPlus.show();

            //for access data from Update_popup box using this view
            View view=dialogPlus.getHolderView();


              TextView ItemNameShow=view.findViewById(R.id.itemnameshowtv);
              TextView ItemPriceShow=view.findViewById(R.id.priceshowtv);
              ImageView plus=view.findViewById(R.id.plusBtn);
              ImageView minus=view.findViewById(R.id.minusBtn);
              TextView OrderDisplay=view.findViewById(R.id.orderDisplay);
              Button updateBtn=view.findViewById(R.id.updateBtn);
              ImageView itemimageview=view.findViewById(R.id.itemimageview);

              ItemNameShow.setText(model.getItemname());
              ItemPriceShow.setText(model.getPrice());
              OrderDisplay.setText(model.getOrderquantity()+"");

              order =Integer.parseInt(OrderDisplay.getText().toString());
//

            Glide.with(itemimageview.getContext())
                    .load(model.getImageurl())
                    .placeholder(R.drawable.common_google_signin_btn_icon_dark)
                    .circleCrop()
                    .error(R.drawable.common_google_signin_btn_icon_dark_normal)
                    .into(itemimageview);

            plus.setOnClickListener(v1 -> {
                order=order+1;
                OrderDisplay.setText(order + "");
            });

            minus.setOnClickListener(v12 -> {
                 order=order-1;
                 OrderDisplay.setText(order+"");
            });
           updateBtn.setOnClickListener(v13 -> {
               Map<String,Object> map=new HashMap<>();
               map.put("orderquantity",(Integer.parseInt(OrderDisplay.getText().toString())));
              FirebaseDatabase.getInstance().getReference().child("orders").child(getRef(position).getKey()).updateChildren(map)
                       .addOnSuccessListener(new OnSuccessListener<Void>() {
                           @Override
                           public void onSuccess(Void unused) {
                               Toast.makeText(holder.textvieworderquantity.getContext(), "Update Your Order successfully!", Toast.LENGTH_SHORT).show();
                                dialogPlus.dismiss();
                           }
                       }).addOnFailureListener(new OnFailureListener() {
                  @Override
                  public void onFailure(@NonNull Exception e) {
                      Toast.makeText(holder.textvieworderquantity.getContext(), "Your order can not Updated! something is wrong happen", Toast.LENGTH_SHORT).show();
                      dialogPlus.dismiss();
                  }
              });
           });

        });

        holder.DeleteBtn.setOnClickListener(v -> {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(v.getContext());
            builder1.setTitle("Are you sure to Delete your Order?");
            builder1.setMessage("Deleted Data Can't be undo.");
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    "Delete",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            FirebaseDatabase.getInstance().getReference().child("orders").child(getRef(position).getKey()).removeValue();
                            dialog.cancel();
                        }
                    });

            builder1.setNegativeButton(
                    "Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Toast.makeText(v.getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                            dialog.cancel();
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();
        });

    }


    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
//        View view = inflater.inflate(R.layout.singlerow_categories, parent, false);
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_order_list,parent,false);
        return new myviewholder(view);
    }

    public static class myviewholder extends  RecyclerView.ViewHolder{
        ImageView itemimage;
        TextView textviewitemname;
        TextView textvieworderquantity, textviewprice;
        Button EditBtn, DeleteBtn;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            itemimage=itemView.findViewById(R.id.itemimage);
            textviewitemname=itemView.findViewById(R.id.itemname_orderlist);
            textvieworderquantity=itemView.findViewById(R.id.orderquantity);
            textviewprice=itemView.findViewById(R.id.price);
            EditBtn =itemView.findViewById(R.id.EditOrder);
            DeleteBtn =itemView.findViewById(R.id.DeleteOrder);

        }
    }

}
