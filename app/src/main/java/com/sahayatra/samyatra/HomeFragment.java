package com.sahayatra.samyatra;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.sahayatra.samyatra.variables.phoneno;

public class HomeFragment extends Fragment  {
    String[] offer_phone=new String[100];
    String[] booked_phone=new String[100];
    String[] flag=new String[100];
    String[] offer_phone1=new String[100];
    String phone;
    DatabaseReference ref1,ref,ref2;
    FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {

        View view= inflater.inflate(R.layout.fragment_home,container,false);
        super.onCreate(savedInstanceState);
       final Button btn=(Button)view.findViewById(R.id.offer_a_ride);
        ref2= FirebaseDatabase.getInstance().getReference().child("data");
        ref2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int p=0;
                for(DataSnapshot d : dataSnapshot.getChildren())
                {

                   flag[p] = (String)d.child("preference1").getValue();
                    offer_phone1[p]=(String)d.child("phone1").getValue();
                   if((phoneno.equals(offer_phone1[p]))&&(flag[p].equals("Find Only")))
                   {
                       btn.setEnabled(false);
                       btn.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View v) {
                           }
                       });

                   }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref= FirebaseDatabase.getInstance().getReference().child("Offering DB");
//offer_a_ride
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        int flag=0;
                            int i=0;
                            for(DataSnapshot d : dataSnapshot.getChildren())
                            {
                                offer_phone[i]=(String)d.child("phone1").getValue();
                                if(phoneno.equals(offer_phone[i]))
                                {
                                    flag=1;
                                    Toast.makeText(getActivity(), "You can only offer one ride ", Toast.LENGTH_LONG).show();
                                    break;
                                }
                    }
                        if(flag==0)
                        {
                            startActivity(new Intent( getActivity(),TofromO11Activity.class));
                        }

                        }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });
        ref1= FirebaseDatabase.getInstance().getReference().child("Booked Rides");

        //find_a_ride
        Button btn1=(Button)view.findViewById(R.id.find_a_ride);
        btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                ref1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        int flag=0;
                        int i=0;
                        for(DataSnapshot d : dataSnapshot.getChildren())
                        {
                            booked_phone[i]=(String)d.child("sign_up_number1").getValue();
                            if(phoneno.equals(booked_phone[i]))
                            {
                                flag=1;
                                Toast.makeText(getActivity(), "You can only book one ride ", Toast.LENGTH_LONG).show();
                                break;
                            }
                        }
                        if(flag==0)
                        {
                            startActivity(new Intent( getActivity(),ToFromF10Activity.class));
                        }

                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

    return view;
    }
}
