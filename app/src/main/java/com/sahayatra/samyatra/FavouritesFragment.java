package com.sahayatra.samyatra;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.sahayatra.samyatra.variables.phoneno;
import static com.sahayatra.samyatra.variables.u;

public class FavouritesFragment extends Fragment {
    String[] phone3=new String[100];
    String[] sign_up_number2=new String[100];
    String[] name3=new String[100];
    String[] name=new String[100];
    private ArrayList<User2> arrayList= new ArrayList<User2>();
    UsersAdapter1 adapter;
    ListView listView;
    public View view;
    DatabaseReference ref;
    int flag=0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_favourites,container,false);
        listView=(ListView)view.findViewById(R.id.list_view1);
        adapter = new UsersAdapter1(getActivity(), arrayList);
            listView.setAdapter(adapter);


        ref= FirebaseDatabase.getInstance().getReference().child("Booked Rides");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    int i = 0;

                    for (DataSnapshot d : dataSnapshot.getChildren())
                    {
                        name[i]=d.getKey();
                        name3[i]=(String) d.child("name2").getValue();
                        phone3[i]=(String)d.child("phone2").getValue();
                        sign_up_number2[i]=(String)d.child("sign_up_number1").getValue();
                        for(int o=0;o<sign_up_number2.length;o++)
                        {
                            if(phoneno.equals(sign_up_number2[o]))
                            {
                                User2 newUser = new User2(name3[i],phone3[i]);
                                adapter.add(newUser);
                                adapter.notifyDataSetChanged();
                                u=phone3[i];
                                flag=1;
                            }
                        }
                       /* for(int t=0;t<sign_up_number2.length;t++)
                        {
                            if(u.equals(phone3[t])) {
                                User2 newUser = new User2(name3[i], sign_up_number2[t]);
                                adapter.add(newUser);
                                adapter.notifyDataSetChanged();
                            }
                        }*/

                    }
                    if(flag==0)
                    {
                        Toast.makeText(getActivity(), "NO BOOKINGS YET!!! ", Toast.LENGTH_LONG).show();
                        Intent p=new Intent(getActivity(),Main8navigation.class);
                        p.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(p);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                startActivity(new Intent(getActivity(),StartrideActivity.class));
            }
        });
        return view;
    }
    public void onDestroyView() {
        super.onDestroyView();
    }
}
