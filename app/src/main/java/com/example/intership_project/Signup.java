package com.example.intership_project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;

public class Signup extends AppCompatActivity {
    TextView signin;
    Button register;
    ImageView image_profile_signup;
    EditText username, password, cpassword, email, phoneno;
    private FirebaseAuth mAuth;;
    Uri filepath;
    Bitmap bitmap;

    Uri selectedImageUri;
    private StorageReference storageprofileReference;
    private StorageTask uploadTask;
    private String myUri = "";
    //create object of firebasedatabase-refrence class to access the realtime database

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
       // getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mAuth = FirebaseAuth.getInstance();

        register = findViewById(R.id.signupbtn);
        signin = findViewById(R.id.signintv);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        cpassword = findViewById(R.id.cpassword);
        email = findViewById(R.id.emailaddress);
        phoneno = findViewById(R.id.phonenumber);
//        image_profile_signup = findViewById(R.id.image_profile_signup);
//
//        image_profile_signup.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                chooseprofilepicture();
//            }
//        });


        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Signup.this, Login.class);
                startActivity(intent);
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateusersignup();

            }
        });
    }

    private void chooseprofilepicture() {
        //make alert for that
        AlertDialog.Builder builder = new AlertDialog.Builder(Signup.this);
        LayoutInflater layoutInflater = getLayoutInflater();
        View dialogView = layoutInflater.inflate(R.layout.alertdialog_profile, null);
        builder.setCancelable(false);
        builder.setView(dialogView);
        builder.setTitle("choose photo");

        ImageView alertcamera = dialogView.findViewById(R.id.alertcamera);
        ImageView gallery = dialogView.findViewById(R.id.alert_gallery);

        AlertDialog alertDialogprofile = builder.create();
        alertDialogprofile.show();

        alertcamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkAndRequestpermission()) {
                    takepicturefromcamera();
                    alertDialogprofile.cancel();
                    //uploadprofilepicture();
                }
            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takepicturefromgallery();
                alertDialogprofile.cancel();
                // uploadprofilepicture();
            }
        });
        //after choose profile picture upload into firebase database

    }


    private void takepicturefromcamera() {
        Intent photo_camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (photo_camera.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(photo_camera, 2);
        }
    }

    private void takepicturefromgallery() {
        Intent pickphoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickphoto, 1);
    }

    private boolean checkAndRequestpermission() {
        //if sdk version is greater than 23
        if (Build.VERSION.SDK_INT >= 23) {
            int cameraPermission = ActivityCompat.checkSelfPermission(
                    Signup.this, Manifest.permission.CAMERA);
            if (cameraPermission == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(Signup.this,
                        new String[]{Manifest.permission.CAMERA}, 7);
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 7 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //if permission granted then
            takepicturefromcamera();
        } else {
            Toast.makeText(getApplicationContext(), "permission not granted", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    selectedImageUri = data.getData();
                    try {
                        InputStream inputStream = getContentResolver().openInputStream(selectedImageUri);
                        bitmap = BitmapFactory.decodeStream(inputStream);
                        image_profile_signup.setImageURI(selectedImageUri);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;

            case 2:
                if (resultCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    Bitmap bitmapImage = (Bitmap) bundle.get("data");
                    image_profile_signup.setImageBitmap(bitmapImage);
                }
        }
    }

//    private void uploadprofilepicture() {
//        final ProgressDialog progressDialog=new ProgressDialog(this);
//        progressDialog.setTitle("Set your profile");
//        progressDialog.setMessage("Please wait ,While we are setting your data");
//        progressDialog.show();
//       if (selectedImageUri!=null)
//       {
//           //if image is selected then put into firestore database
//           FirebaseStorage storage = FirebaseStorage.getInstance();
//           StorageReference fileReference=storageprofileReference.child(mAuth.getCurrentUser().getUid()
//           + ".jpg");
//           uploadTask = fileReference.putFile(selectedImageUri);
//           uploadTask.continueWithTask(new Continuation() {
//               @Override
//               public Object then(@NonNull Task task) throws Exception {
//                   if (!task.isSuccessful())
//                   {
//                        throw task.getException();
//                   }
//                   return fileReference.getDownloadUrl();
//               }
//           }).addOnCompleteListener(new OnCompleteListener<Uri>() {
//                       @Override
//                       public void onComplete(@NonNull Task<Uri> task) {
//                           if (task.isSuccessful())
//                           {
//                               Uri downloadUrl= (Uri) task.getResult();
//                               myUri=downloadUrl.toString();
//                           HashMap<String, Object>  usersmap= new HashMap<>();
//                           usersmap.put("image",myUri);
//
//                               DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://foodorderapp-a4e2a-default-rtdb.firebaseio.com/");
//                           databaseReference.child(mAuth.getCurrentUser().getUid()).updateChildren(usersmap);
//                           progressDialog.dismiss();
//                           }
//                       }
//                   });
//       }
//       else {
//           Toast.makeText(this, "image can not selected please try again!", Toast.LENGTH_SHORT).show();
//       }
//    }

    private boolean validateusersignup() {
        final String usernametxt = username.getText().toString();
        final String phonenumtxt = phoneno.getText().toString();
        final String emailtxt = email.getText().toString();
        final String passwordtxt = password.getText().toString();
        final String cpasswordtxt = cpassword.getText().toString();

        if (usernametxt.isEmpty()) {
            username.setError("your username is required");
            username.requestFocus();
            return false;
        } else if (phonenumtxt.isEmpty()) {
            phoneno.setError("your phonenumber is required");
            phoneno.requestFocus();
            return false;
        } else if (emailtxt.isEmpty()) {
            email.setError("your email is required");
            email.requestFocus();
            return false;
        } else if (passwordtxt.isEmpty()) {
            password.setError("your password is required");
            password.requestFocus();
            return false;
        } else if (cpasswordtxt.isEmpty()) {
            cpassword.setError("your confirmpassword is required");
            cpassword.requestFocus();
            return false;
        } else if (passwordtxt.length() < 6) {
            password.setError("minimum password length should be 6 characters ");
            password.requestFocus();
            return false;
        } else if (cpasswordtxt.length() < 6) {
            password.setError("minimum password length should be 6 characters ");
            password.requestFocus();
            return false;
        } else if (!cpasswordtxt.equals(passwordtxt)) {
            password.setError("please enter the password as it enter before ");
            password.requestFocus();
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailtxt).matches()) {
            email.setError("please enter the valid email!");
            email.requestFocus();
            return false;
        } else {
            // Toast.makeText(this, "your all data validate successfully!", Toast.LENGTH_SHORT).show();
            mAuth.createUserWithEmailAndPassword(emailtxt, passwordtxt).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {

                        FirebaseUser user = mAuth.getCurrentUser();

//                        user.sendEmailVerification();
                        Toast.makeText(Signup.this, "Registered successfully.", Toast.LENGTH_LONG).show();

                        //put data into firebase data
                        ReadWriteableUserDetails writeableUserDetails = new ReadWriteableUserDetails(usernametxt, phonenumtxt, emailtxt, passwordtxt);

                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://foodorderapp-a4e2a-default-rtdb.firebaseio.com/");
                        databaseReference.child(user.getUid()).setValue(writeableUserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful()) {
                                    Intent intent = new Intent(Signup.this, MainScreen.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(getApplicationContext(), "User Registered failed!please try again!!!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    } else {
                        Toast.makeText(Signup.this, "can not Registerd !", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        return true;
    }
}