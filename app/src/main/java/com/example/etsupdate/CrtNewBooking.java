package com.example.etsupdate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

import com.github.florent37.singledateandtimepicker.SingleDateAndTimePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
//import com.vivekkaushik.datepicker.DatePickerTimeline;
//import com.vivekkaushik.datepicker.OnDateSelectedListener;
//import com.vivekkaushik.datepicker.TimelineView;

import java.io.File;
import java.io.IOException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class CrtNewBooking extends AppCompatActivity {


    DatabaseReference bokingdb;
    CheckBox chrtn,chrpt;
    Spinner spinner;
    CardView crd1,crd2,crd3,crd4;
    LinearLayout linearLayout;
    SingleDateAndTimePicker datePickerTimeline;
    //TimelineView timelineView;
    TextView tv1,tv2,tv3,tv4,dt;
    static TextView tm,rtdate,returndate;
    Intent intent;
    String datetime,timeboking, frombooking, tobooking,fromtype,toype, statusbooking;
    Button bokingbtn;
    ProgressBar bokingpbar;
    Button tapandholdtorecord;
    TextView tvrecord;
    //String[] type = {"Select","Repeat","Return"};
    MediaRecorder mRecoder;
    MediaPlayer mPlayer;
    String recordfilename;
    DatabaseReference RecordingDb,userrecordDb;
    FirebaseUser fuser;
    private StorageReference mStoageRef;
    String uid;
    ImageView playbtuttn;
    boolean Recirding = false;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crt_new_booking);

        mStoageRef = FirebaseStorage.getInstance().getReference();
        RecordingDb = FirebaseDatabase.getInstance().getReference().child("Audio Recording");

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String dttm = df.format(c.getTime());
        recordfilename = Environment.getExternalStorageDirectory().getAbsolutePath()+"/recorded_audio.mp3"+"\n"+dttm;
       // recordfilename += ;

        intent = getIntent();

        tvrecord = findViewById(R.id.recordtv);
        playbtuttn = findViewById(R.id.playbtn);

        String str = intent.getStringExtra("inname");
//        FirebaseApp.initializeApp(this);
        FirebaseUser curentUser = FirebaseAuth.getInstance().getCurrentUser();
        uid = curentUser.getUid();

        bokingdb = FirebaseDatabase.getInstance().getReference().child("Booking Data").child(uid);

        tapandholdtorecord = findViewById(R.id.voicerecord);
        bokingpbar = findViewById(R.id.bokingloadbar);
        bokingbtn = findViewById(R.id.bok);
        crd3 = findViewById(R.id.tmcrd);
       // crd4 = findViewById(R.id.crdrpt);
        chrpt = findViewById(R.id.rpt);
        chrtn = findViewById(R.id.rtrn);
        linearLayout = findViewById(R.id.rtrndatetime);
        crd1 = findViewById(R.id.crdhome);
        crd2 = findViewById(R.id.crdofc);

        tm = findViewById(R.id.tm);
        returndate = findViewById(R.id.curnetdate);
        tv1 = findViewById(R.id.fromloc);
        tv1.setText("Gurgaon Office");
        fromtype = tv1.getText().toString();
        rtdate = findViewById(R.id.rptdatepic);
        tv2 = findViewById(R.id.fulladd);

        tv2.setText("Plot 92, Sector-44, Gurgaon");
        frombooking = tv2.getText().toString();
        tv3 = findViewById(R.id.toloc);
        tv3.setText("Home");
        toype = tv3.getText().toString();
        tv4 = findViewById(R.id.fulladdresh);
        tv4.setText("65/59 NewRohtak Road, Karol Bagh, New Delhi");
        tobooking = tv4.getText().toString();
        datePickerTimeline = findViewById(R.id.datepicker);

        dt = findViewById(R.id.dattime);


        returndate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDatePickerDialog();
            }
        });
        chrtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){
                    ShowTimePickerDialog();
                    linearLayout.setVisibility(View.VISIBLE);
                    Toast.makeText(CrtNewBooking.this, chrtn.getText(), Toast.LENGTH_SHORT).show();
                }else {
                    linearLayout.setVisibility(View.GONE);
                }
            }
        });

        chrpt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                if (isChecked){
                    ShowDatePickerDialog();
                }else {
                    rtdate.setVisibility(View.GONE);
                }
                
            }
        });


        tapandholdtorecord.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction()==event.ACTION_DOWN){

                   recordAudio();

                    playbtuttn.setVisibility(View.GONE);
                    tvrecord.setText("Recoding Started...");

                }else if (event.getAction() == event.ACTION_UP){

                    stopAudio();
                    tvrecord.setText("Recording Stopped");

                }

                return false;
            }
        });

        playbtuttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RecordingDb.child(uid).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        String recordingfile = (String) dataSnapshot.child("AudioTitle").getValue();
                        mPlayer = new MediaPlayer();
                        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                        try {
                            mPlayer.setDataSource(recordfilename);
                            mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                @Override
                                public void onPrepared(MediaPlayer mp) {

                                    mp.start();

                                }
                            });
                            mPlayer.prepare();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

//        crd4 = findViewById(R.id.tmcrd);

        crd3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ShowDatePickerDialog();

            }
        });


        tm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowTimePickerDialog();
            }
        });

        int m = datePickerTimeline.getDate().getMonth();
        int  st = datePickerTimeline.getDate().getDate();
        int  yr = datePickerTimeline.getDate().getYear();

        String stri = (st+"-"+(1+m)+"-"+yr);
        returndate.setText(stri);
        dt.setText(stri);

        datePickerTimeline.addOnDateChangedListener(new SingleDateAndTimePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(String displayed, Date date) {
                dt.setText(displayed);

                datetime = dt.getText().toString();

                int  m = date.getMonth();
                int  st = date.getDate();
                int  yr = date.getYear();

                String stri = (1+m+"-"+st+"-"+yr);
                returndate.setText(displayed);
                returndate.setText(stri);

            }
        });

        //String currentDate = sdf.format(new Date());

//        datePickerTimeline.setOnDateSelectedListener(new OnDateSelectedListener() {
//            @Override
//            public void onDateSelected(int year, int month, int day, int dayOfWeek) {
//
//                dt.setText(day + "/" + (month+1) + "/" + year);
//
//            }
//
//            @Override
//            public void onDisabledDateSelected(int year, int month, int day, int dayOfWeek, boolean isDisabled) {
//
//            }
//        });
//        int dates = Calendar.getInstance().getWeekYear();
//
//        Date date = new Date(dates);
//
//        datePickerTimeline.deactivateDates(new Date[]{date});


        bokingbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bokingbtn.setVisibility(View.GONE);
                bokingpbar.setVisibility(View.VISIBLE);

                if (frombooking.isEmpty()){
                    tv1.setError("Plsese Chose Your From Location");
                }
                if (tobooking.isEmpty()){
                    tv3.setError("PLese select your Destination");
                }
                if (datetime.isEmpty()){
                    dt.setError("Plese Chose Your Booking Date and Time");
                }

                else if (!TextUtils.isEmpty(frombooking) || !TextUtils.isEmpty(tobooking) || !TextUtils.isEmpty(datetime)){

                    Map bokingdata = new HashMap();
                    bokingdata.put("Booking Type",fromtype + "to" + toype);
                    bokingdata.put("From Location",frombooking);
                    bokingdata.put("To Location",tobooking);
                    bokingdata.put("Booking Date Time",datetime);

                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Booking Record").child(uid).push();

                    String UserKey = reference.getKey();

                    Map bodyMap = new HashMap();
                    bodyMap.put("UserKey:- "+UserKey,bokingdata);

                    bokingdb.updateChildren(bodyMap, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {

                            if (databaseError == null){

                                Toast.makeText(CrtNewBooking.this, "Cab Booked", Toast.LENGTH_SHORT).show();
                                Intent homein = new Intent();
                                setResult(Activity.RESULT_OK,homein);
                                Animatoo.animateSwipeLeft(CrtNewBooking.this);
                                finish();
                                bokingpbar.setVisibility(View.GONE);
                            }
                            else {
                                bokingbtn.setVisibility(View.VISIBLE);
                                bokingpbar.setVisibility(View.GONE);
                                Toast.makeText(CrtNewBooking.this, "Error "+databaseError, Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                }

            }
        });

    }

    private void stopAudio() {

        try {
            mRecoder.stop();
        }catch (RuntimeException stopexp){
            stopexp.printStackTrace();
        }

        mRecoder.release();
        mRecoder = null;
        uploadAudio();
    }

    private void recordAudio() {

     //   Recirding = true;
        try {
            mRecoder = new MediaRecorder();
            mRecoder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mRecoder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
            mRecoder.setOutputFile(recordfilename);
            mRecoder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mRecoder.prepare();
            mRecoder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

//    private void startRecording(){
//
//        Recirding= true;
//
//        try {
//
//            mRecoder = new MediaRecorder();
//            mRecoder.setAudioSource(MediaRecorder.AudioSource.MIC);
//            mRecoder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
//            mRecoder.setOutputFile(recordfilename);
//            mRecoder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
//            mRecoder.prepare();
//
//
//        }catch (IOException e){
//            Log.e("Record Log","Prepare() failed");
//            e.printStackTrace();
//        }
//        mRecoder.start();
//
//    }
//
//    private void stopRecording(){
//
//        mRecoder.stop();
//        mRecoder.release();
//        mRecoder = null;
//
//
//        uploadAudio();
//    }

    private void uploadAudio() {

        Uri uri = Uri.fromFile(new File(recordfilename));
        bokingpbar.setVisibility(View.VISIBLE);
        StorageReference filepath = mStoageRef.child("Audio").child("new_audio.mp3");


        filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                final DatabaseReference new_post = RecordingDb.push();

                bokingdb.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        new_post.child("AudioTitle").setValue(recordfilename);
                        new_post.child("UserId").setValue(uid).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                
                                if (task.isSuccessful()){

                                    bokingpbar.setVisibility(View.GONE);

                                    tvrecord.setText("Tap to Play");
                                    playbtuttn.setVisibility(View.VISIBLE);

                                    Toast.makeText(CrtNewBooking.this, "Recording Send", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });




                //retrieveAudioFile();


            }
        });

    }

    private void ShowDatePickerDialog() {

        DialogFragment datedialogFragment = new DatePickerFragment();
        datedialogFragment.show(getSupportFragmentManager(),"DatePicker");

    }

    private void ShowTimePickerDialog() {

        DialogFragment dialogFragment = new TimePickerFragment();
        dialogFragment.show(getSupportFragmentManager(),"TimePicker");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.animateSlideDown(this);
    }

    @Override
    public boolean onSupportNavigateUp() {

        onBackPressed();
        return super.onSupportNavigateUp();
    }

    public static class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

            final Calendar  calendar = Calendar.getInstance();
            int hors = calendar.get(Calendar.HOUR_OF_DAY);
            int min = calendar.get(Calendar.MINUTE);

            return new TimePickerDialog(getActivity(),this,hors,min, DateFormat.is24HourFormat(getActivity()));
        }

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            tm.setText(hourOfDay +":"+ minute);

        }

    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            final Calendar dtcal = Calendar.getInstance();
            int yr = dtcal.get(Calendar.YEAR);
            int mnth = dtcal.get(Calendar.MONTH);
            int date = dtcal.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(),this,yr,mnth,date);

        }



        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

            rtdate.setText((month+1) + "/" + dayOfMonth + "/" + year);
            rtdate.setVisibility(View.VISIBLE);
            returndate.setText((month+1) + "/" + dayOfMonth + "/" + year);

        }
    }
}
