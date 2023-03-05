package com.sahayatra.samyatra;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import static android.content.ContentValues.TAG;
import static com.sahayatra.samyatra.variables.f;
import static com.sahayatra.samyatra.variables.mDatabase;
import static com.sahayatra.samyatra.variables.name2;
import static com.sahayatra.samyatra.variables.phoneno;
import static com.sahayatra.samyatra.variables.user_phone;
public class User_ProfileActivity extends AppCompatActivity {
    Toolbar toolbar;
    EditText ur_name,ur_email,ur_carno,ur_carcl,ur_carmo,ur_gender;
    Button us_book,us_close;
    ImageView iv;
    String path;
    DatabaseReference ref,ref1;
    StorageReference storageReference;
    String[] name3=new String[100];
    String[] sign_up_number2=new String[100];
    String[] gender2=new String[100];
    String[] dob2=new String[100];
    String[] occupation2=new String[100];
    String[] name1=new String[100];
    String[] offer_phone1=new String[100];
    String[] offer_phone4=new String[100];
    String[] offer_name1=new String[100];
    String[] offer_email1=new String[100];
    String[] offer_carnumber1=new String[100];
    String[] offer_carcolor1=new String[100];
    String[] offer_carmodel1=new String[100];
    String[] offer_gender=new String[100];
    String[] offer_picurl=new String[100];
    String name8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__profile);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        toolbar.setTitle("Rider Information");
        setSupportActionBar(toolbar);
        ur_name = findViewById(R.id.user_name);
        ur_email = findViewById(R.id.user_email);
        ur_carno = findViewById(R.id.user_car_number);
        ur_carcl = findViewById(R.id.user_car_color);
        ur_carmo = findViewById(R.id.user_car_model);
        ur_gender = findViewById(R.id.user_gender);
        us_book = findViewById(R.id.book);
        us_close = findViewById(R.id.close);
        iv=findViewById(R.id.profile_pic);
        iv.setOnClickListener(new View.OnClickListener() {
            @  Override
            public void onClick(View v) {
                iv.setScaleType(ImageView.ScaleType.FIT_XY);
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                getSupportActionBar().hide();
            }
        });
        mDatabase = FirebaseDatabase.getInstance().getReference();
        storageReference= FirebaseStorage.getInstance().getReference();

        ref = FirebaseDatabase.getInstance().getReference().child("data");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    int i = 0;

                    for (DataSnapshot d : dataSnapshot.getChildren()) {
                        name1[i] = d.getKey();
                        offer_phone1[i] = (String) d.child("phone1").getValue();
                        offer_name1[i] = (String) d.child("name1").getValue();
                        offer_email1[i] = (String) d.child("emailid1").getValue();
                        offer_carnumber1[i] = (String) d.child("car_number1").getValue();
                        offer_carcolor1[i] = (String) d.child("car_colour1").getValue();
                        offer_carmodel1[i] = (String) d.child("car_model1").getValue();
                        offer_gender[i] = (String) d.child("gender2").getValue();
                       offer_picurl[i]=(String) d.child("profile_picture_url1").getValue();
                        Log.i(TAG, "Saransh : " + offer_picurl[i]);
                        for (int n = 0; n < offer_phone1.length; n++) {
                            if (user_phone.equals(offer_phone1[n])) {
                                ur_name.setText(offer_name1[n]);
                                ur_email.setText(offer_email1[n]);
                                ur_carcl.setText(Html.fromHtml("<font><b>" + "CAR COLOUR : " + "</b></font>" +offer_carcolor1[n]));
                                ur_carmo.setText(Html.fromHtml("<font><b>" + "CAR MODEL : " + "</b></font>" +offer_carmodel1[n]));
                                ur_carno.setText(Html.fromHtml("<font><b>" + "CAR NUMBER : " + "</b></font>" +offer_carnumber1[n]));
                                ur_gender.setText(Html.fromHtml("<font><b>" + "GENDER : " + "</b></font>" +offer_gender[n]));
                                f = offer_phone1[n];
                                name2=offer_name1[n];
                                path=offer_picurl[n];
                                Log.i(TAG, "Lakshaya : " + offer_picurl[n]);
                                if (!User_ProfileActivity.this.isFinishing()) {
                                    Glide.with(User_ProfileActivity.this).load(path).apply(RequestOptions.circleCropTransform()).into(iv);
                                }

                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void chat_onClick(View view)
    {

    }
public void close_onClick(View view)
{
    Intent i=new Intent(User_ProfileActivity.this,Fetch_avail11Activity.class);
    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    startActivity(i);
}
public void book_onClick(View view)
{
    f=user_phone;
    AlertDialog.Builder alertDialog = new AlertDialog.Builder(User_ProfileActivity.this, R.style.yourDialog);
    alertDialog.setTitle("BOOK RIDE");
    alertDialog.setMessage("Do you want to book the ride?");
    alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {

            ref= FirebaseDatabase.getInstance().getReference().child("data");
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()) {
                        int i = 0;

                        for (DataSnapshot d : dataSnapshot.getChildren())
                        {
                            sign_up_number2[i]=(String)d.child("phone1").getValue();
                            name3[i]=(String)d.child("name1").getValue();
                            dob2[i]=(String)d.child("dob1").getValue();
                            gender2[i]=(String)d.child("gender2").getValue();
                            occupation2[i]=(String)d.child("occupation1").getValue();
                            for(int o=0;o<sign_up_number2.length;o++)
                            {
                                if(phoneno.equals(sign_up_number2[o]))
                                {
                                    final String message = name3[o]+" has booked your ride.\n" + "Account Information :\n" + "Phone Number : "+sign_up_number2[o]+"\nGender : "+gender2[o]+"\nDate of Birth : "+dob2[o]+"\nOccupation : "+occupation2[o];
                                    SmsManager smsManager = SmsManager.getDefault();
                                    StringBuffer smsBody = new StringBuffer();
                                    smsBody.append(Uri.parse(message));
                                    android.telephony.SmsManager.getDefault().sendTextMessage(user_phone, null, smsBody.toString(), null, null);
                                }
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            AlertDialog.Builder builder = new AlertDialog.Builder(User_ProfileActivity.this, R.style.yourDialog);
            builder.setTitle("Your Ride Is Booked");
            builder.setMessage("You can check your rides on Booked Rides corner")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            ref1= FirebaseDatabase.getInstance().getReference().child("Offering DB");
                            ref1.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()) {
                                        int i = 0;
                                        int o = 0;
                                        for (DataSnapshot d : dataSnapshot.getChildren())
                                        {
                                            offer_phone4[i] = (String) d.child("phone1").getValue();
                                            if(f.equals(offer_phone4[i]))
                                                name8=d.getKey();
                                                d.getRef().removeValue();
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                            User2 user2=new User2(name2,f,phoneno);
                            mDatabase.child("Booked Rides").push().setValue(user2);
                            Intent i=new Intent(User_ProfileActivity.this,Main8navigation.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
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
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(User_ProfileActivity.this,Fetch_avail11Activity.class));
    }
}
