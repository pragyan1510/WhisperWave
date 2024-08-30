package com.example.whisperwave;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.whisperwave.Adapters.chatadapter;
import com.example.whisperwave.Models.messages;
import com.example.whisperwave.databinding.ActivityChatDetailBinding;
import com.example.whisperwave.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

public class ChatDetailActivity extends AppCompatActivity {

    ActivityChatDetailBinding binding;

    FirebaseDatabase database;

FirebaseAuth auth;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityChatDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

//        getSupportActionBar().hide();

        final String senderID = auth.getUid();
        String recieveID = getIntent().getStringExtra("userID");
        String usernameID = getIntent().getStringExtra("username");
        String ProfilepicID = getIntent().getStringExtra("Profilepic");

        binding.Usernamechat.setText(usernameID);
        Picasso.get().load(ProfilepicID).placeholder(R.drawable.cat).into(binding.profileImage);//placeholder tab use hoga jab user ne koi image set ni kar  rakhi hogi

        binding.backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ChatDetailActivity.this,MainActivity.class);
                startActivity(i);
            }
        });

        final ArrayList<messages> messagesArrayList = new ArrayList<>();

        final chatadapter chatadapter = new chatadapter(messagesArrayList , this, recieveID);
        binding.Chatrecyclerview.setAdapter(chatadapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.Chatrecyclerview.setLayoutManager(layoutManager);


        final String senderRoom  = senderID + recieveID;
        final String recieverRoom = recieveID + senderID;

        database.getReference().child("chats")
                        .child(senderRoom)
                                .addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        messagesArrayList.clear();//one mesg at a time
                                        for (DataSnapshot snapshot1 : snapshot.getChildren()){
                                            messages model = snapshot1.getValue(messages.class);
                                            model.setMessageID(snapshot.getKey());
                                            messagesArrayList.add(model);
                                        }
                                        chatadapter.notifyDataSetChanged();//mesg aram se show hojeyaga bina textbar pe click kiye
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });


        binding.sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = binding.etMessage.getText().toString();
                final messages mesg = new messages(senderID, message);

                mesg.setTimestamp(new Date().getTime());
                binding.etMessage.setText("");

                database.getReference().child("chats")
                        .child(senderRoom)
                        .push()
                        .setValue(mesg).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                database.getReference().child("chats")
                                        .child(recieverRoom)
                                        .push()
                                        .setValue(mesg).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {

                                            }
                                        });

                            }
                        });

            }
        });



    }
}