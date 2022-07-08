package com.example.intership_project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class UploadItemActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_IMAGE =1 ;
    private ImageView imageViewAdd;
    private Button BtnUpload;
    private TextView textviewProgressBar;
    private EditText inputImageName,inputItemPrice;
    ProgressBar progressBar;
    Uri imageUri;
    boolean isImageAdded=false;

    DatabaseReference DataRef;
    StorageReference StorageRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_item);
        imageViewAdd=findViewById(R.id.imageViewAdd);
        BtnUpload=findViewById(R.id.Btnupload);
        textviewProgressBar =findViewById(R.id.textviewProgress);
        inputImageName=findViewById(R.id.inputImageName);
        inputItemPrice=findViewById(R.id.inputItemPrice);
        progressBar=findViewById(R.id.progreesBar);

        //in starting this both of not there
        textviewProgressBar.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);

        DataRef= FirebaseDatabase.getInstance().getReference().child("Food");
        StorageRef= FirebaseStorage.getInstance().getReference().child("FoodImages");


        //when click on image view then open gallary so that
        imageViewAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,REQUEST_CODE_IMAGE);
            }
        });
        BtnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String ItemName =  inputImageName.getText().toString();
                final double Itemprice= Double.parseDouble(inputItemPrice.getText().toString());
                if (isImageAdded!=false && ItemName !=null)
                {
                    uploadImage(ItemName,Itemprice);
                }

            }
        });
    }
    private void uploadImage(String imageName,double price) {
        //upload data into firebase
        textviewProgressBar.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);

        String  key=DataRef.push().getKey();
        StorageRef.child(key +".jpg").putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                StorageRef.child(key+".jpg").getDownloadUrl().addOnCompleteListener((OnCompleteListener<Uri>) uri -> {
                    HashMap hashMap=new HashMap();
                    hashMap.put("Foodname",imageName);
                    hashMap.put("FoodPrice",price);
                    hashMap.put("ImageUrl",uri.toString());

                    DataRef.child(key).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(UploadItemActivity.this, "Data has been successfully " +
                                    "inserted!", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(UploadItemActivity.this, MainScreen.class);
                            startActivity(intent);

                        }
                    });
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progress=snapshot.getBytesTransferred()*100/snapshot.getTotalByteCount();
                progressBar.setProgress((int) progress);
                textviewProgressBar.setText(progress + "%");



            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE_IMAGE && data!=null)
        {
            //selected image uri stored into imageUri variable
            imageUri=data.getData();
            isImageAdded=true;
            imageViewAdd.setImageURI(imageUri);

            //image is selected after that visibility progressbar


        }
    }
}