package com.homemart.Fragments;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.homemart.Adapters.DisplayCategoryAdapter;
import com.homemart.Adapters.DisplayOffersAdapter;
import com.homemart.R;
import com.homemart.models.Seller;
import com.homemart.utils.EqualSpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment {


    public Home() {
        // Required empty public constructor

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        //FIREBASE
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        DatabaseReference mOfferlinksDatabaseReference;
        DatabaseReference mCategoryDatabaseReference;
        DatabaseReference mSellerDatabaseReference;
        //FirebaseUser currentUser = mAuth.getCurrentUser();
        //Toast.makeText(getActivity(), ""+mAuth.getUid(), Toast.LENGTH_SHORT).show();

        mOfferlinksDatabaseReference = FirebaseDatabase.getInstance().getReference().child("links").child("offerlink");
        mCategoryDatabaseReference = FirebaseDatabase.getInstance().getReference().child("links").child("category");
        mSellerDatabaseReference = FirebaseDatabase.getInstance().getReference().child("sellers");

        //List of data
        List<String> mOffersList = new ArrayList<>();
        List<DataSnapshot> mCategoryList = new ArrayList<>();
        List<Seller> mSellerList = new ArrayList<>();


        RecyclerView mOffersRecyclerview = rootView.findViewById(R.id.offers_recyclerView);
        RecyclerView mCategoryRecyclerview = rootView.findViewById(R.id.category_recyclerview);
        RecyclerView mSellerRecyclerview = rootView.findViewById(R.id.seller_recyclerview);


        mOffersRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));



        mCategoryRecyclerview.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        mCategoryRecyclerview.addItemDecoration(new EqualSpacingItemDecoration(10,EqualSpacingItemDecoration.VERTICAL));
        mSellerRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));


        //Offers Fetching
        mOfferlinksDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                mOffersList.clear();
                for (DataSnapshot datasnapshotobject : children) {

                    mOffersList.add(datasnapshotobject.getValue().toString());
                }
                mOffersRecyclerview.setAdapter(new DisplayOffersAdapter(mOffersList));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //Category Fetching
        mCategoryDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                Log.d("Log1", "onDataChange: " + children);

                mCategoryList.clear();
                for (DataSnapshot datasnapshotobject : children) {
                    mCategoryList.add(datasnapshotobject);
                }

                mCategoryRecyclerview.setAdapter(new DisplayCategoryAdapter(mCategoryList));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return rootView;
    }

}
