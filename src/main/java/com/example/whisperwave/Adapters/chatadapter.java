package com.example.whisperwave.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whisperwave.Models.messages;
import com.example.whisperwave.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class chatadapter extends RecyclerView.Adapter{
    ArrayList<messages> messagesArrayList;
    Context context;
    String recID;

    int SENDER_VIEW_TYPE = 1;
    int RECIEVER_VIEW_TYPE = 2;

    public chatadapter(ArrayList<messages> messagesArrayList, Context context) {
        this.messagesArrayList = messagesArrayList;
        this.context = context;
    }

    public chatadapter(ArrayList<messages> messagesArrayList, Context context, String recID) {
        this.messagesArrayList = messagesArrayList;
        this.context = context;
        this.recID = recID;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType == SENDER_VIEW_TYPE){
            View view = LayoutInflater.from(context).inflate(R.layout.sample_sender,parent,false);
            return new senderviewholder(view);
        }else {
            View view = LayoutInflater.from(context).inflate(R.layout.sample_reciever, parent, false);
            return new recieverviewholder(view);
        }

    }

    @Override
    public int getItemViewType(int position) {
        if(messagesArrayList.get(position).getUid().equals(FirebaseAuth.getInstance().getUid())){
            return SENDER_VIEW_TYPE;
        }else{
            return RECIEVER_VIEW_TYPE;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        messages messagesarray = messagesArrayList.get(position);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                new AlertDialog.Builder(context)
                        .setTitle("Delete")
                        .setMessage("Are you sure?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                String sender = FirebaseAuth.getInstance().getUid() + recID;

                                database.getReference().child("chats").child(sender)
                                        .child(messagesarray.getMessageID())
                                        .setValue(null);




                            }
                        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();

                            }
                        }).show();

                return false;
            }
        });

        if(holder.getClass() == senderviewholder.class){
            ((senderviewholder)holder).sendermsg.setText(messagesarray.getMessages());
        }else{
            ((recieverviewholder)holder).recieverMsg.setText(messagesarray.getMessages());
        }

    }

    @Override
    public int getItemCount() {
        return messagesArrayList.size();
    }

    public class recieverviewholder extends RecyclerView.ViewHolder{

        TextView recieverMsg, recievertime;

        public recieverviewholder(@NonNull View itemView) {
            super(itemView);
            recieverMsg = itemView.findViewById(R.id.recievermsg);
            recievertime = itemView.findViewById(R.id.recievertime);

        }
    }

    public class senderviewholder extends RecyclerView.ViewHolder{

        TextView sendermsg, sendertime;

        public senderviewholder(@NonNull View itemView) {
            super(itemView);
            sendermsg = itemView.findViewById(R.id.sendermsg);
            sendertime = itemView.findViewById(R.id.sendertime);
        }
    }
}
