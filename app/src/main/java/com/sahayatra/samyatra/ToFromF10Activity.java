package com.sahayatra.samyatra;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.sahayatra.samyatra.variables.from;
import static com.sahayatra.samyatra.variables.mDatabase;
import static com.sahayatra.samyatra.variables.phoneno;
import static com.sahayatra.samyatra.variables.time;
import static com.sahayatra.samyatra.variables.to;
public class ToFromF10Activity extends AppCompatActivity {

    Button fetch;
    int i,f;
    DatabaseReference ref;
    String[] offer_phone=new String[100];
    String[] name=new String[100];
    String h;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_from_f10);
        from=findViewById(R.id.leaving_from1);
        to=findViewById(R.id.going_to1);
        time=findViewById(R.id.leaving_time);
        fetch=findViewById(R.id.find_a_ride);

        from.addTextChangedListener(textWatcher);
        to.addTextChangedListener(textWatcher);
        time.addTextChangedListener(textWatcher);

        ref = FirebaseDatabase.getInstance().getReference().child("data");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int i = 0;

                for (DataSnapshot d : dataSnapshot.getChildren()){
                    offer_phone[i]=(String) d.child("phone1").getValue();
                    name[i]=(String)d.child("name1").getValue();
                    if(phoneno.equals(offer_phone[i]))
                    {
                         h=name[i];
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mDatabase = FirebaseDatabase.getInstance().getReference();
        fetch.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
                                             User user=new User(from,to,phoneno,h,time);
                                             mDatabase.child("Finding DB").push().setValue(user);
                                         startActivity(new Intent(ToFromF10Activity.this,Fetch_avail11Activity.class));

                                     }
                                 }
        );


    }

    TextWatcher textWatcher=new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            String f=from.getText().toString().trim();
            String t=to.getText().toString().trim();
            String ti=time.getText().toString().trim();
            fetch.setEnabled(!f.isEmpty() && !t.isEmpty() && !ti.isEmpty());
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
        startActivity(new Intent(ToFromF10Activity.this,Main8navigation.class));
    }
}
