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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.sahayatra.samyatra.variables.phoneno;


public class BlockFragment extends Fragment {
    DatabaseReference ref,ref1,ref2;
    String[] sign_up_number2=new String [100];
    String name;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_block,container,false);
        Button delete=view.findViewById(R.id.buttondelete);
        Button notdelete=view.findViewById(R.id.buttonnotdelete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                ref= FirebaseDatabase.getInstance().getReference().child("data");
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()) {
                            int i = 0;

                            for (DataSnapshot d : dataSnapshot.getChildren())
                            {
                                sign_up_number2[i]=(String)d.child("phone1").getValue();

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
                ref2= FirebaseDatabase.getInstance().getReference().child("Offering DB");
                ref2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()) {
                            int i = 0;

                            for (DataSnapshot d : dataSnapshot.getChildren())
                            {
                                sign_up_number2[i]=(String)d.child("phone1").getValue();

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
                Intent i=new Intent(getActivity(),MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(new Intent(getActivity(),MainActivity.class));
                Toast.makeText(getActivity(),
                        "Your Account is Deleted", Toast.LENGTH_LONG).show();
            }
        });

        notdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),Main8navigation.class));
            }
        });
        return view;
    }
    /*public void onDestroyView() {
       super.onDestroyView();
        startActivity(new Intent(getActivity(),MainActivity.class));
    }*/

}
