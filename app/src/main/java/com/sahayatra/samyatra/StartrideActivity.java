package com.sahayatra.samyatra;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.sahayatra.samyatra.variables.f;
import static com.sahayatra.samyatra.variables.mDatabase;
import static com.sahayatra.samyatra.variables.phoneno;
import static com.sahayatra.samyatra.variables.u;
import static com.sahayatra.samyatra.variables.user_phone;

public class StartrideActivity extends AppCompatActivity {
    Toolbar toolbar;
    EditText ur_name,ur_email,ur_carno,ur_carcl,ur_carmo,ur_gender;
    String[] name1=new String[100];
    String[] offer_phone1=new String[100];
    String[] offer_name1=new String[100];
    String[] offer_email1=new String[100];
    String[] offer_carnumber1=new String[100];
    String[] offer_carcolor1=new String[100];
    String[] offer_carmodel1=new String[100];
    String[] offer_gender=new String[100];
    String[] offer_picurl=new String[100];
    String[]  sign_up_number2=new String[100];
    String[]  sign_up_number3=new String[100];
    String name,name2;
    DatabaseReference ref,ref1,ref2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startride);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        toolbar.setTitle("User Information");
        setSupportActionBar(toolbar);
        ur_name = findViewById(R.id.user_name1);
        ur_carno = findViewById(R.id.user_car_number1);
        ur_carcl = findViewById(R.id.user_car_color1);
        ur_carmo = findViewById(R.id.user_car_model1);
        ur_gender = findViewById(R.id.user_gender1);
        Button start_ride=(Button)findViewById(R.id.start_ride);
        Button cancel_ride=(Button)findViewById(R.id.cancel_ride);
        Button call_rider=(Button)findViewById(R.id.call_rider);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        ref = FirebaseDatabase.getInstance().getReference().child("data");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    int i = 0;

                    for (DataSnapshot d : dataSnapshot.getChildren())
                    {
                        name1[i] = d.getKey();
                        offer_phone1[i] = (String) d.child("phone1").getValue();
                        offer_name1[i] = (String) d.child("name1").getValue();
                        offer_email1[i] = (String) d.child("emailid1").getValue();
                        offer_carnumber1[i] = (String) d.child("car_number1").getValue();
                        offer_carcolor1[i] = (String) d.child("car_colour1").getValue();
                        offer_carmodel1[i] = (String) d.child("car_model1").getValue();
                        offer_gender[i] = (String) d.child("gender2").getValue();
                        offer_picurl[i] = (String) d.child("profile_picture_url1").getValue();

                            if (u.equals(offer_phone1[i]))
                            {
                                ur_name.setText(offer_name1[i]);
                                ur_carcl.setText(Html.fromHtml("<font><b>" + "CAR COLOUR : " + "</b></font>" +offer_carcolor1[i]));
                                ur_carmo.setText(Html.fromHtml("<font><b>" + "CAR MODEL : " + "</b></font>" +offer_carmodel1[i]));
                                ur_carno.setText(Html.fromHtml("<font><b>" + "CAR NUMBER : " + "</b></font>" +offer_carnumber1[i]));
                                ur_gender.setText(Html.fromHtml("<font><b>" + "GENDER : " + "</b></font>" +offer_gender[i]));
                                break;
                            }
                        }
                    }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });


        start_ride.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartrideActivity.this,ALERTActivity.class));
            }
        });

        cancel_ride.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                f=user_phone;
                AlertDialog.Builder alertDialog=new AlertDialog.Builder(StartrideActivity.this,R.style.yourDialog);
                alertDialog.setTitle("Do you want to cancel the ride?");
                alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        ref1= FirebaseDatabase.getInstance().getReference().child("Booked Rides");
                        ref1.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.exists()) {
                                    int i = 0;

                                    for (DataSnapshot d : dataSnapshot.getChildren())
                                    {
                                        sign_up_number2[i]=(String)d.child("sign_up_number1").getValue();

                                        for(int o=0;o<sign_up_number2.length;o++)
                                        {
                                            if(phoneno.equals(sign_up_number2[o]))
                                            {
                                                name=d.getKey();
                                                d.getRef().removeValue();
                                            }
                                        }

                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        String message="Your ride has been cancelled by the user... Sorry for any inconvienience caused..";
                        SmsManager smsManager = SmsManager.getDefault();
                        StringBuffer smsBody = new StringBuffer();
                        smsBody.append(Uri.parse(message));
                        android.telephony.SmsManager.getDefault().sendTextMessage(u, null, smsBody.toString(), null, null);
                        /*ref2= FirebaseDatabase.getInstance().getReference().child("Offering DB");
                        ref2.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.exists()) {
                                    int i = 0;

                                    for (DataSnapshot d : dataSnapshot.getChildren())
                                    {
                                        sign_up_number3[i]=(String)d.child("phone1").getValue();

                                        for(int o=0;o<sign_up_number3.length;o++)
                                        {
                                            if(u.equals(sign_up_number3[o]))
                                            {
                                                name2=d.getKey();
                                                d.getRef().removeValue();
                                            }
                                        }

                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });*/

                        Intent i=new Intent(StartrideActivity.this,Main8navigation.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        Toast.makeText(getApplicationContext(), "Your ride is cancelled", Toast.LENGTH_SHORT).show();

                    }
                });
                alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alertDialog.show();
            }
        });
        call_rider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(StartrideActivity.this, R.style.yourDialog);
                alertDialog.setTitle("CALL RIDER");
                alertDialog.setMessage("Do you really want to call the Rider?");
                alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {


                        final Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:" + u));
                        if (ActivityCompat.checkSelfPermission(StartrideActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            //TODO: Consider calling
                            return;
                        }
                        startActivity(intent);
                    }
                });

                alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        Toast.makeText(getApplicationContext(), "OK", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                });

                alertDialog.show();
            }
        });
    }
    public void onBackPressed()
    {
        startActivity(new Intent(StartrideActivity.this,Main8navigation.class));
    }
}
