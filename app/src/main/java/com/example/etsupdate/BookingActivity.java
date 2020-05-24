package com.example.etsupdate;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class BookingActivity extends AppCompatActivity {

    TextView nebook,pendingbook;
    ViewPager pageradapter;
    viewPager vp;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    DatabaseReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        mAuth = FirebaseAuth.getInstance();

        currentUser = mAuth.getCurrentUser();

        if (currentUser != null){
            String Online_user = mAuth.getCurrentUser().getUid();
            userRef = FirebaseDatabase.getInstance().getReference().child("Booking Data").child(Online_user);
        }
        nebook = findViewById(R.id.nwbooking);
        pendingbook = findViewById(R.id.pendingbook);
        pageradapter = findViewById(R.id.tabcontainer);

        vp = new viewPager(getSupportFragmentManager());

        pageradapter.setAdapter(vp);

        nebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pageradapter.setCurrentItem(0);
            }
        });

        pendingbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pageradapter.setCurrentItem(1);
            }
        });

        pageradapter.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
              //  onChangeTab(position);
            }

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onPageSelected(int position) {

                onChangeTab(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {


            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void onChangeTab(int position) {

        if (position ==0){
            nebook.setTextSize(20);
            nebook.setTextColor(getColor(R.color.brightColor));

            pendingbook.setTextSize(15);
            pendingbook.setTextColor(getColor(R.color.LightColor));
        }
        if (position ==1){
            nebook.setTextSize(15);
            nebook.setTextColor(getColor(R.color.LightColor));

            pendingbook.setTextSize(20);
            pendingbook.setTextColor(getColor(R.color.brightColor));

        }
    }


}
