package com.sahayatra.samyatra;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.content.ContentValues.TAG;
import static com.sahayatra.samyatra.variables.phoneno;
public class MyAccountFragment extends Fragment {
    DatabaseReference ref;
    FirebaseAuth auth;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    Toolbar toolbar;
    Button update;
    int b;
    String[] offer_shareloc = new String[100];
    String[] offer_phone1 = new String[100];
    String[] offer_name1 = new String[100];
    String[] offer_email1 = new String[100];
    String[] offer_dob1 = new String[100];
    String[] offer_city = new String[100];
    String[] offer_state1 = new String[100];
    String[] offer_gender = new String[100];
    String[] offer_preference = new String[100];
    EditText acc_name, acc_mobile, acc_email, acc_dob, acc_gender, acc_city, acc_state, acc_sml, acc_syp, acc_lic_no, acc_car_number, acc_car_color, acc_car_model;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myaccount, container, false);
        toolbar = view.findViewById(R.id.toolbar);


        super.onCreate(savedInstanceState);
        acc_name = view.findViewById(R.id.acc_name1);
        acc_mobile = view.findViewById(R.id.acc_mobile1);
        acc_email = view.findViewById(R.id.acc_email1);
        acc_dob = view.findViewById(R.id.acc_dob1);
        acc_gender = view.findViewById(R.id.acc_gender1);
        acc_city = view.findViewById(R.id.acc_city1);
        acc_state = view.findViewById(R.id.acc_state1);
        acc_sml = view.findViewById(R.id.acc_sml1);
        acc_syp = view.findViewById(R.id.acc_syp1);


        ref = FirebaseDatabase.getInstance().getReference().child("data");
        Log.i(TAG, "teena : " + ref);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    int i = 0;

                    for (DataSnapshot d : dataSnapshot.getChildren()) {

                        offer_phone1[i] = (String) d.child("phone1").getValue();
                        offer_name1[i] = (String) d.child("name1").getValue();
                        offer_email1[i] = (String) d.child("emailid1").getValue();
                        offer_dob1[i] = (String) d.child("dob1").getValue();
                        offer_state1[i] = (String) d.child("state1").getValue();
                        offer_city[i] = (String) d.child("address1").getValue();
                        offer_gender[i] = (String) d.child("gender2").getValue();
                        offer_shareloc[i] = (String) d.child("sharelocmobile1").getValue();
                        offer_preference[i] = (String) d.child("preference1").getValue();

                        if (phoneno.equals(offer_phone1[i])) {
                            acc_name.setText(Html.fromHtml("<font><b>" + "NAME : " + "</b></font>" + offer_name1[i]));

                            acc_email.setText(Html.fromHtml("<font><b>" + "EMAIL : " + "</b></font>" + offer_email1[i]));

                            acc_mobile.setText(Html.fromHtml("<font><b>" + "MOBILE No. : " + "</b></font>" + offer_phone1[i]));

                            acc_dob.setText(Html.fromHtml("<font><b>" + "DOB : " + "</b></font>" + offer_dob1[i]));

                            acc_gender.setText(Html.fromHtml("<font><b>" + "GENDER : " + "</b></font>" + offer_gender[i]));

                            acc_city.setText(Html.fromHtml("<font><b>" + "CITY : " + "</b></font>" + offer_city[i]));

                            acc_state.setText(Html.fromHtml("<font><b>" + "STATE : " + "</b></font>" + offer_state1[i]));

                            acc_sml.setText(Html.fromHtml("<font><b>" + "SHARE MY LOCATION WITH : " + "</b></font>" + offer_shareloc[i]));

                            acc_syp.setText(Html.fromHtml("<font><b>" + "YOUR PREFERENCE : " + "</b></font>" + offer_preference[i]));

                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.i(TAG, "Error occured in connecting with database");
            }
        });
        return view;
    }
}







