package com.example.intership_project.Fragments_bottomnav;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.intership_project.MainScreen2;
import com.example.intership_project.R;
import com.example.intership_project.orderlist.Model;

import com.example.intership_project.orderlist.Myadapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OrderListFragment extends Fragment {

    FirebaseRecyclerAdapter<Model, Myadapter.myviewholder> adapter;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("orders");
    FloatingActionButton floatingBtn;
    RecyclerView recyclerViewOrderListRec;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_orderlist, container, false);
        floatingBtn = view.findViewById(R.id.floatingBtn);
        recyclerViewOrderListRec = (RecyclerView) view.findViewById(R.id.recviewOrderlist);
        recyclerViewOrderListRec.setLayoutManager(new LinearLayoutManager(view.getContext()));


        FirebaseRecyclerOptions<Model> options = new FirebaseRecyclerOptions.Builder<Model>()
                .setQuery(databaseReference, Model.class)
                .build();

        adapter = new Myadapter(options);
        adapter.startListening();
        recyclerViewOrderListRec.setAdapter(adapter);

        floatingBtn.setOnClickListener(v -> {
            Toast.makeText(getContext(), "You can add your order!", Toast.LENGTH_SHORT).show();
        });
        setHasOptionsMenu(true);
        return view;

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        inflater.inflate(R.menu.searchfromorderlist, menu);
        MenuItem menuItem=menu.findItem(R.id.SearchFromOrderList);
        SearchView searchView=(SearchView) menuItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                txtsearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                txtsearch(newText);
                return false;
            }
        });
       super.onCreateOptionsMenu(menu, inflater);

    }

    private  void txtsearch(String str){
        FirebaseRecyclerOptions<Model> options = new FirebaseRecyclerOptions.Builder<Model>()
                .setQuery(databaseReference.orderByChild("itemname").startAt(str).endAt(str + "~"), Model.class)
                .build();

        Myadapter myadapter=new Myadapter(options);
        myadapter.startListening();
        recyclerViewOrderListRec.setAdapter(myadapter);

    }
}