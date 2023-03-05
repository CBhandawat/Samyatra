package com.sahayatra.samyatra;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static android.content.ContentValues.TAG;
import static com.sahayatra.samyatra.variables.from;
import static com.sahayatra.samyatra.variables.time;
import static com.sahayatra.samyatra.variables.to;
import static com.sahayatra.samyatra.variables.user_phone;

public class Fetch_avail11Activity extends AppCompatActivity {
    ListView listView;
    DatabaseReference ref,ref1;
    String[] name=new String[100];
    String[] name1=new String[100];
    String[] offer_fromo=new String[100];
    String[] offer_too=new String[100];
    String[] offer_phone=new String[100];
    String[] offer_name=new String[100];
    String[] offer_time=new String[100];
    String[] offer_pic=new String[100];
    String[] store_phone=new String[100];
    Toolbar toolbar;
    int f=0;
    int flag=0;
    private ArrayList<User> arrayList= new ArrayList<User>();
    UsersAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch_avail11);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        toolbar.setTitle("Available Rides");
        setSupportActionBar(toolbar);
        listView=(ListView)findViewById(R.id.list_view);
         adapter = new UsersAdapter(Fetch_avail11Activity.this, arrayList);
        ref= FirebaseDatabase.getInstance().getReference().child("Offering DB");

            listView.setAdapter(adapter);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    int i=0;
                    int o=0;
                    for(DataSnapshot d : dataSnapshot.getChildren())
                    {

                        name[i]=d.getKey();
                        offer_fromo[i]= (String) d.child("fromo").getValue();
                        offer_too[i]= (String) d.child("too").getValue();
                        offer_phone[i]=(String)d.child("phone1").getValue();
                        offer_name[i]=(String)d.child("name1").getValue();
                        offer_time[i]=(String)d.child("time1").getValue();
                        offer_pic[i]=(String)d.child("pict").getValue();
                        String find_from=from.getText().toString();
                        String find_to=to.getText().toString();
                        String find_time=time.getText().toString();
                        SimpleDateFormat df=new SimpleDateFormat("HH:mm");
                        try {

                            Date date = df.parse(find_time);
                            Calendar cal = Calendar.getInstance();
                            cal.setTime(date);
                            cal.add(Calendar.MINUTE, 15);
                            String newTime = df.format(cal.getTime());
                            Date date1=df.parse(newTime);
                            Date date_offer=df.parse(offer_time[i]);


                            if((date_offer.after(date)) && (date_offer.before(date1)) || (date_offer.equals(date)))
                            {
                                f=1;
                            }
                            else
                                f=0;
                        }
                        catch (Exception e)
                        {

                        }
                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                        String s = sdf.format(new Date());

                        if((find_from.equals(offer_fromo[i]))&& (find_to.equals(offer_too[i])) && (f==1))
                        {
                            Log.i(TAG, "Lucky3445 : " + offer_pic[i]);
                            User newUser = new User(offer_name[i], offer_time[i],offer_pic[i]);
                            adapter.add(newUser);
                            adapter.notifyDataSetChanged();
                            store_phone[o]=offer_phone[i];
                            Log.i(TAG, "STORE USER : " + store_phone[o]);
                            o++;
                            flag=1;
                        }
                        i++;
                    }
                    if(flag==0)
                    {
                        Toast.makeText(getApplicationContext(), "No Rides available yet... Please try again later!!! ", Toast.LENGTH_LONG).show();
                        Intent p=new Intent(Fetch_avail11Activity.this,Main8navigation.class);
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
                if(position==0)
                {
                    user_phone=store_phone[0];
                    startActivity(new Intent(Fetch_avail11Activity.this,User_ProfileActivity.class));
                }
                if(position==1)
                {
                    user_phone=store_phone[1];
                    startActivity(new Intent(Fetch_avail11Activity.this,User_ProfileActivity.class));
                }
            }
        });
    }
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(Fetch_avail11Activity.this,ToFromF10Activity.class));
    }
}
