package com.example.etsupdate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    EditText et1,et2,et3;
    Button login,convertspeachbtn;
    TextToSpeech textToSpeech;

    public static final int RC_sigin_In = 1;

   // FirebaseAuth mAuth;
    ProgressBar bar;
    ProgressDialog log_dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        log_dialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        et1 = findViewById(R.id.email);

        et2 = findViewById(R.id.pass);
        et3 = findViewById(R.id.inputtxt);
        convertspeachbtn = findViewById(R.id.convertspch);

        textToSpeech = new TextToSpeech(MainActivity.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {

                if (status!= TextToSpeech.ERROR){

                    textToSpeech.setLanguage(Locale.ENGLISH);

                }
            }
        });

        convertspeachbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = et3.getText().toString();

                //convert Text to speach
                textToSpeech.speak(text,TextToSpeech.QUEUE_FLUSH,null);
            }
        });

        et1.setText("john@gmail.com");
        et2.setText("123456789");


        login= findViewById(R.id.buttonLogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailid = et1.getText().toString();
                String pass = et2.getText().toString();
                if (!Patterns.EMAIL_ADDRESS.matcher(emailid).matches()){
                    et1.setError("Email is incorrect");
                }
                if (pass.length()<6){
                    et2.setError("Password Week");
                }
                else if (!TextUtils.isEmpty(emailid) || !TextUtils.isEmpty(pass)){

                    log_dialog.setTitle("Loging...");
                    log_dialog.setMessage("Please Wait While User Login");
                    log_dialog.setCanceledOnTouchOutside(false);
                    log_dialog.show();

                    loginUser(emailid,pass);
                }

                Intent bokin = new Intent(MainActivity.this,BookingActivity.class);
                startActivity(bokin);

            }
        });

    }

    @Override
    protected void onPause() {
        if (textToSpeech != null){
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onPause();
    }

    private void loginUser(String emailid, String pass) {

        mAuth.signInWithEmailAndPassword(emailid,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){
                    log_dialog.dismiss();

                        Intent homin = new Intent(MainActivity.this,BookingActivity.class);
                        //homin.putExtra("Intent",user);

                        homin.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(homin);
                        finish();
                        Animatoo.animateSlideLeft(MainActivity.this);

                }

            }
        });
    }


//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        if (FirebaseAuth.getInstance().getCurrentUser() !=null){
//
//            Intent homin = new Intent(MainActivity.this,BookingActivity.class);
//            homin.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            startActivity(homin);
//        }
//    }
}
