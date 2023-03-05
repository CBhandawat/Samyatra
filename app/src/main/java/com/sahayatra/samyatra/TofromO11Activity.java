package com.sahayatra.samyatra;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.sahayatra.samyatra.variables.mDatabase;
import static com.sahayatra.samyatra.variables.phoneno;

public class TofromO11Activity extends AppCompatActivity {
 EditText from2,to2,time2;
 Button offer;
 DatabaseReference ref1;
 String[] name=new String[100];
    String[] pic=new String[100];
 String name9,pic9;
    String[] phone3=new String[100];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tofrom_o11);
        from2=findViewById(R.id.leaving_from3);
        to2=findViewById(R.id.going_to3);
        time2=findViewById(R.id.leaving_time3);
        offer=findViewById(R.id.offer_my_ride_btn);

        from2.addTextChangedListener(textWatcher);
        to2.addTextChangedListener(textWatcher);
        time2.addTextChangedListener(textWatcher);



        mDatabase = FirebaseDatabase.getInstance().getReference();
        offer.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
                                         ref1= FirebaseDatabase.getInstance().getReference().child("data");
                                         ref1.addListenerForSingleValueEvent(new ValueEventListener() {
                                             @Override
                                             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                 if (dataSnapshot.exists()) {
                                                     int i = 0;
                                                     int o = 0;
                                                     for (DataSnapshot d : dataSnapshot.getChildren()) {
                                                         phone3[i] = (String) d.child("phone1").getValue();
                                                         name[i] = (String) d.child("name1").getValue();
                                                         pic[i]=(String) d.child("profile_picture_url1").getValue();
                                                         if(phoneno.equals(phone3[i]))
                                                         {
                                                             name9=name[i];
                                                             pic9=pic[i];
                                                             break;
                                                         }



                                                     }
                                                 }

                                                 User2 user=new User2(from2,to2,phoneno,name9,time2,pic9);
                                                 mDatabase.child("Offering DB").push().setValue(user);
                                                 Toast.makeText(getApplicationContext(), "Your Ride is Offered", Toast.LENGTH_LONG).show();
                                                 Intent i=new Intent(TofromO11Activity.this,Main8navigation.class);
                                                 i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                 startActivity(i);
                                             }

                                             @Override
                                             public void onCancelled(@NonNull DatabaseError databaseError) {

                                             }
                                         });

                                     }
                                 }
        );


    }

    TextWatcher textWatcher=new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            String f=from2.getText().toString().trim();
            String t=to2.getText().toString().trim();
            String ti=time2.getText().toString().trim();
            offer.setEnabled(!f.isEmpty() && !t.isEmpty() && !ti.isEmpty());
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            // TODO Auto-generated method stub

        }

        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub

        }
    };

    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(TofromO11Activity.this,Main8navigation.class));
    }
}

