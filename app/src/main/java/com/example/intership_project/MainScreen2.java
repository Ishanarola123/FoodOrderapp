package com.example.intership_project;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.intership_project.Fragments_bottomnav.OrderListFragment;
import com.example.intership_project.SqliteDatabase.DBHelper;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.nio.file.Path;
import java.util.UUID;


public class MainScreen2 extends AppCompatActivity {
    ImageView imageView, imageView_plus, imageView_minus,imagedisplay;
    TextView item_name;
    TextView item_price;
    TextView number_of_order_txt;
    TextView description_text;
    Button add_cart;
    int number_of_order = 1;
    String  ImageUrl;

    TextView orderByUsername, orderPhone;
    private FirebaseAuth mAuth;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance()
            .getReferenceFromUrl("https://foodorderapp-a4e2a-default-rtdb.firebaseio.com/");
    // Uri indicates, where the image will be picked from
    //private Resources filePath;

    // request code
    private final int PICK_IMAGE_REQUEST = 22;

    // instance for firebase storage and StorageReference
    FirebaseStorage storage;
    StorageReference storageRef;

    //uri to store file
    private Uri filePath;

    //firebase objects
    private StorageReference storageReference;
    private DatabaseReference ref;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen2);
        mAuth = FirebaseAuth.getInstance();
        imageView = findViewById(R.id.image);
        item_name = findViewById(R.id.item_name_tv);
        item_price = findViewById(R.id.item_price_tv);
        imageView_plus = findViewById(R.id.imageView_plus);
        imageView_minus = findViewById(R.id.imageView_minus);
        number_of_order_txt = findViewById(R.id.display_count);
        description_text = findViewById(R.id.description_text);
        orderPhone = findViewById(R.id.order_phone);
        orderByUsername = findViewById(R.id.order_By_username);
        number_of_order_txt.setText(String.valueOf(number_of_order));
        add_cart = findViewById(R.id.add_cart_detail);

//        imageView.setImageResource(getIntent().getIntExtra("imagename", 0));
        //for dynamic data
        //we take all things from firebase database
        ref=FirebaseDatabase.getInstance().getReference().child("Food");
        String FoodnameKey=getIntent().getStringExtra("foodname");
        ref.child(FoodnameKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // ig we get data
                if (snapshot.exists()){
                  //we show this data into main screen 2
                  String Foodname=snapshot.child("Foodname").getValue().toString();
                 double  FoodPrice= Double.parseDouble(snapshot.child("FoodPrice").getValue().toString());
                  ImageUrl=snapshot.child("ImageUrl").getValue().toString();
                  //we chow data into this activity

//        item_name.setText(getIntent().getStringExtra("itemname"));

                    //  item_price.setText(" $" + price.toString());
                    Glide.with(imageView).load(ImageUrl).into(imageView);
                    item_name.setText(Foodname +"");
                    item_price.setText("$" + FoodPrice + "");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


//        item_name.setText(getIntent().getStringExtra("itemname"));
//        String subString = getIntent().getStringExtra("itemprice").substring(1);
//        Double price = Double.valueOf(subString).doubleValue();

        //  item_price.setText(" $" + price.toString());

        checkloginuser();
        imageView_plus.setOnClickListener(view -> {
            number_of_order = number_of_order + 1;
            number_of_order_txt.setText(String.valueOf(number_of_order));
            final int order_count = number_of_order;

        });

        imageView_minus.setOnClickListener(view -> {
            if (number_of_order > 1) {
                number_of_order = number_of_order - 1;
                final int order_count = number_of_order;
            }

            number_of_order_txt.setText(String.valueOf(number_of_order));
        });

        DBHelper helper = new DBHelper(this);
        add_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key = databaseReference.push().getKey();
                assert key != null;
                databaseReference.child("orders").child(key).child("username").
                        setValue(orderByUsername.getText().toString());
                databaseReference.child("orders").child(key).child("phonenumber").
                        setValue(orderPhone.getText().toString());
                databaseReference.child("orders").child(key).child("price").
                        setValue(item_price.getText().toString());
                databaseReference.child("orders").child(key).child("orderquantity").
                        setValue(number_of_order);
                databaseReference.child("orders").child(key).child("description").
                        setValue(description_text.getText().toString());
                databaseReference.child("orders").child(key).child("itemname").
                        setValue(item_name.getText().toString());
                databaseReference.child("orders").child(key).child("imageurl").
                        setValue(ImageUrl);


                Toast.makeText(MainScreen2.this, "your order has been taken!", Toast.LENGTH_SHORT).show();

            }
        });


    }




    private void checkloginuser() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String userid = currentUser.getUid().toString();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String username = snapshot.child(userid).child("username").getValue(String.class);
                String phonenumber = snapshot.child(userid).child("phone_number").getValue(String.class);
                orderByUsername.setText(username);
                orderPhone.setText(phonenumber);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


            }
        });

    }
}
