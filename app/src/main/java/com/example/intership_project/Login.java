package com.example.intership_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class Login extends AppCompatActivity {
    TextView signuptv;
    Button loginbtn;
    EditText password,emailet;
    private FirebaseAuth mAuth;
    DatabaseReference databaseReference= FirebaseDatabase.getInstance()
            .getReferenceFromUrl("https://foodorderapp-a4e2a-default-rtdb.firebaseio.com/");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_login);
        signuptv=findViewById(R.id.signuptv);
        loginbtn=findViewById(R.id.loginbtn);

//        phoneno=findViewById(R.id.phoneno);

        password=findViewById(R.id.loginpassword);
        emailet=findViewById(R.id.emailETlogin);


        signuptv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Login.this,Signup.class);
                startActivity(intent);
            }
        });

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateuser();
//                final String phonetxt = emailet.getText().toString();
//                final String passwordtxt = password.getText().toString();

//                databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        if (snapshot.hasChild(phonetxt)){
//                            final  String getpassword = snapshot.child(phonetxt).child("password").getValue(String.class);
//                            System.out.println(getpassword);
//                            if (getpassword.equals(passwordtxt)){
//                                Toast.makeText(Login.this, "logged in successfully", Toast.LENGTH_LONG).show();
//                                Intent intent=new Intent(Login.this,MainScreen.class);
//                                startActivity(intent);
//                            }
//                            else{
//                                Toast.makeText(getApplicationContext(), "can not logged in", Toast.LENGTH_SHORT).show();
//                            }
//
//                        }
//                        else{
//                            Toast.makeText(getApplicationContext(), "wrong password", Toast.LENGTH_SHORT).show();
//                        }
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });

            }
        });

    }

    private boolean validateuser() {

        final String emailtxt=emailet.getText().toString();
        final String passwordtxt=password.getText().toString();

        if (emailtxt.isEmpty()) {
            emailet.setError("your Emial is required");
            emailet.requestFocus();
            return false;
        }
        else if (passwordtxt.isEmpty())
        {
          password.setError("your password is required!");
          password.requestFocus();
          return false;
        }
        else  if (passwordtxt.length() < 6) {
           password.setError("minimum password length should be 6 characters ");
            password.requestFocus();
            return false;
        }
        else{
//            Toast.makeText(this, "your all data validate successfully!", Toast.LENGTH_SHORT).show();
            mAuth.signInWithEmailAndPassword(emailtxt,passwordtxt).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(Login.this, "logged in  successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Login.this, MainScreen.class);
                        startActivity(intent);
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(Login.this, "can not login!", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }

        return true;
    }

}