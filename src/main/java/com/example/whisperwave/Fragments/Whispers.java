package com.example.whisperwave.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.whisperwave.Adapters.UsersAdapter;
import com.example.whisperwave.Models.Users;
import com.example.whisperwave.R;
import com.example.whisperwave.databinding.FragmentWhispersBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Whispers extends Fragment {



    public Whispers() {
        // Required empty public constructor
    }

    FragmentWhispersBinding binding;
    ArrayList<Users> list = new ArrayList<>();
    FirebaseDatabase database;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentWhispersBinding.inflate(inflater, container, false);

        database = FirebaseDatabase.getInstance();

        UsersAdapter adapter = new UsersAdapter(list, getContext());

        binding.chatrecyclerview.setAdapter(adapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        binding.chatrecyclerview.setLayoutManager(linearLayoutManager);


        database.getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();//problem hoti hai jab data retrieve hota hai firebase se toh woh time leta hai isiliye pehle for-each loop pe jaane se pehle data clear karlenge;
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Users users = dataSnapshot.getValue(Users.class);//uske childrens matlab usme jo bhi id hai usko show karega
                    users.setUserid(dataSnapshot.getKey());//user ki id ke hisaab se data lenge
                    if (!users.getUserid().equals(FirebaseAuth.getInstance().getUid())){
                    list.add(users);}
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        return binding.getRoot();



    }
}