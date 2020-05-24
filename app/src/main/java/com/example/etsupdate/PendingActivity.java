package com.example.etsupdate;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class PendingActivity extends Fragment {

     TextView pndingtv;
     RecyclerView recyclerView;
     View pendingview;
     private DatabaseReference UReqsDbRef;
     private FirebaseAuth rAuth;
     FirebaseUser curentuser;
     private String Online_req_user_id;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        pendingview = inflater.inflate(R.layout.pending_booking,container,false);

        recyclerView = pendingview.findViewById(R.id.rcl);
        rAuth = FirebaseAuth.getInstance();
        Online_req_user_id = rAuth.getCurrentUser().getUid();
        UReqsDbRef = FirebaseDatabase.getInstance().getReference().child("Booking Data");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);


        return pendingview;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<PendingModel, Pendingholder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<PendingModel, Pendingholder>
                (PendingModel.class,R.layout.reclinflate, Pendingholder.class,  UReqsDbRef) {
            @Override
            protected void populateViewHolder(final Pendingholder pendingholder, PendingModel pendingModel, int i) {

//                pendingholder.setbookingType(pendingModel.getBooking_Type());
//                pendingholder.setfromlocation(pendingModel.getFrom_Location());
//                pendingholder.setTolocation(pendingModel.getTo_Location());
//                pendingholder.setBookingDateTime(pendingModel.getBooking_Date_Time());

                final String  ReqUserListId = getRef(i).getKey();

                UReqsDbRef.child(ReqUserListId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


//
//                            String bokingtype = dataSnapshot.child("Booking Type").getValue().toString();
//                            String fromlocation = dataSnapshot.child("From Location").getValue().toString();
//                            String tolocation = dataSnapshot.child("To Location").getValue().toString();
//                            String bokingdatetime = dataSnapshot.child("Booking Date Time").getValue().toString();
//
//                            pendingholder.setbookingType(bokingtype);
//                            pendingholder.setfromlocation(fromlocation);
//                            pendingholder.setTolocation(tolocation);
//                            pendingholder.setBookingDateTime(bokingdatetime);




                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        };

        recyclerView.setAdapter(firebaseRecyclerAdapter);

    }

    public static class Pendingholder extends RecyclerView.ViewHolder{

        View pendingviewhol;
        Context ctx;

        public Pendingholder(View itemView) {
            super(itemView);
            pendingviewhol = itemView;


        }

        public void setbookingType(String bokingtype) {

            TextView tv1 = pendingviewhol.findViewById(R.id.btype);
            tv1.setText(bokingtype);
        }

        public void setfromlocation(String fromlocation) {

            TextView tv2 = pendingviewhol.findViewById(R.id.flocation);
            tv2.setText(fromlocation);
        }

        public void setTolocation(String tolocation) {

            TextView tv3 = pendingviewhol.findViewById(R.id.tolocation);
            tv3.setText(tolocation);

        }

        public void setBookingDateTime(String bokingdatetime) {

            TextView tv4 = pendingviewhol.findViewById(R.id.dttime);
            tv4.setText(bokingdatetime);
        }
    }
}
