package com.example.intership_project.Fragments_bottomnav;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.intership_project.R;
import com.example.intership_project.UploadItemActivity;
import com.example.intership_project.recviewcat.Model;
import com.example.intership_project.recviewcat.Myadapter;
import com.example.intership_project.recviewpopular.Dynamic.model;
import com.example.intership_project.recviewpopular.Dynamic.myviewholder;
import com.example.intership_project.recviewpopular.Dynamic.myadapter;
import com.example.intership_project.recviewpopular.Modelpopular;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    SearchView search_view_box;
    TextView username_display_login_textview;
    private FirebaseAuth mAuth;
    DatabaseReference databaseReference= FirebaseDatabase.getInstance()
            .getReferenceFromUrl("https://foodorderapp-a4e2a-default-rtdb.firebaseio.com/");
    FloatingActionButton UploadItemFloatingBtn;
    FirebaseRecyclerOptions<model> options;
    FirebaseRecyclerAdapter<model,myviewholder> adapter;
    DatabaseReference DataRef;
    StorageReference StorageRef;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    public HomeFragment(int contentLayoutId) {
        super(contentLayoutId);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

       username_display_login_textview=view.findViewById(R.id.username_display_login);
        mAuth = FirebaseAuth.getInstance();
        RecyclerView rcvcategories,rcvPopular;

        checkloginuser();
        rcvcategories = view.findViewById(R.id.rcvcategories);
        rcvPopular=view.findViewById(R.id.rcv_popular);

        rcvcategories.setLayoutManager(new LinearLayoutManager(view.getContext(),RecyclerView.HORIZONTAL,false));
        rcvPopular.setLayoutManager(new LinearLayoutManager(view.getContext(),RecyclerView.HORIZONTAL,false));

        DataRef= FirebaseDatabase.getInstance().getReference().child("Food");
        StorageRef= FirebaseStorage.getInstance().getReference().child("FoodImages");

        Myadapter myadapter = new Myadapter(dataqueue());
        rcvcategories.setAdapter(myadapter);

        //set adapter into popular categories
//        myadapterPopular myadapterPopular=new myadapterPopular(popular_dataqueue(),getContext());
//        rcvPopular.setAdapter(myadapterPopular);

        FirebaseRecyclerOptions<model> options = new FirebaseRecyclerOptions.Builder<model>()
                .setQuery(DataRef,model.class)
                .build();
       adapter=new myadapter(options);
       adapter.startListening();
       rcvPopular.setAdapter(adapter);


        UploadItemFloatingBtn=view.findViewById(R.id.UploadItemFloatingBtn);
        UploadItemFloatingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UploadItemActivity.class);
                startActivity(intent);

            }
        });
        return view;
    }


    private void checkloginuser() {

        FirebaseUser currentUser = mAuth.getCurrentUser();
        String userid = currentUser.getUid().toString();

              databaseReference.addValueEventListener(new ValueEventListener() {
                  @Override
                  public void onDataChange(@NonNull DataSnapshot snapshot) {
                      String username=snapshot.child(userid).child("username").getValue(String.class);
                     username_display_login_textview.setText("Hii" +"  "+  username);

                  }

                  @Override
                  public void onCancelled(@NonNull DatabaseError error) {

                  }
              });

        }


    private ArrayList<Modelpopular> popular_dataqueue() {
        ArrayList<Modelpopular> holder = new ArrayList<>();
        Modelpopular ob1 = new Modelpopular();
        ob1.setItemName("pepperponi pizza");
        ob1.setItemPrice("$9.70");
        ob1.setImgName(R.drawable.pop_1);
        holder.add(ob1);

        Modelpopular ob2 = new Modelpopular();
        ob2.setItemName("cheese Burger");
        ob2.setItemPrice("$8.79");
        ob2.setImgName(R.drawable.pop_2);
        holder.add(ob2);

        Modelpopular ob3 = new Modelpopular();
        ob3.setItemName("pepperponi pizza");
        ob3.setItemPrice("$9.70");
        ob3.setImgName(R.drawable.pop_3);
        holder.add(ob3);
        return holder;

    }

    public ArrayList<Model> dataqueue() {
        ArrayList<Model> holder = new ArrayList<>();

        Model ob1 = new Model();
        ob1.setCategoriesheader("Pizza");
        ob1.setImgname(R.drawable.cat_1);
        holder.add(ob1);

        Model ob2 = new Model();
        ob2.setCategoriesheader("Burger");
        ob2.setImgname(R.drawable.cat_2);
        holder.add(ob2);

        Model ob3 = new Model();
        ob3.setCategoriesheader("Hotdog");
        ob3.setImgname(R.drawable.cat_3);
        holder.add(ob3);

        Model ob4 = new Model();
        ob4.setCategoriesheader("Drink");
        ob4.setImgname(R.drawable.cat_4);
        holder.add(ob4);

        Model ob5 = new Model();
        ob5.setCategoriesheader("Pizza");
        ob5.setImgname(R.drawable.cat_1);
        holder.add(ob5);

        return holder;
    }



}
