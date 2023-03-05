package com.sahayatra.samyatra;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.content.ContentValues.TAG;
import static com.sahayatra.samyatra.variables.phoneno;

public class ALERTActivity extends AppCompatActivity implements LocationListener,OnMapReadyCallback {
    String number;
    Context context;
    DatabaseReference ref;
    String sml;
    Toolbar toolbar;
    private GoogleMap mMap;
    String[]  sign_up_number2=new String[100];
    String[]  sign_up_number3=new String[100];
    String[]  sml1=new String[100];
    String name;
    DatabaseReference ref1,ref2;
    public ALERTActivity(){

    }

    public ALERTActivity(Context c) {
        context=c;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        toolbar.setTitle("Your ride has started...");
        Button reach=(Button)findViewById(R.id.reach_dest);
        reach.setOnClickListener(new View.OnClickListener() {
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

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(ALERTActivity.this, R.style.yourDialog);

                alertDialog.setTitle("How was your Ride?");
                alertDialog.setPositiveButton("GOOD", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(),
                                "Thank You for your Feedback", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(ALERTActivity.this,Main8navigation.class));

                    }
                });
                alertDialog.setNegativeButton("BAD", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(),
                                "Thank You for your Feedback", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(ALERTActivity.this,Main8navigation.class));
                    }
                });
                alertDialog.show();
            }
        });
        setSupportActionBar(toolbar);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(ALERTActivity.this);
    }

    public void alert_onClick(View view) {
        number = "100";
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ALERTActivity.this, R.style.yourDialog);

        alertDialog.setTitle("PRESS YES WHEN URGENCY!!!");

        alertDialog.setMessage("Do you really want to call the Police as well as Share your Location with your Near Ones?");

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                final Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + number));
                if (ActivityCompat.checkSelfPermission(ALERTActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    //TODO: Consider calling
                    return;
                }
                ALERTActivity g = new ALERTActivity(getApplicationContext());
                Location l = g.getLocation();
                if (l != null) {
                    double lat = l.getLatitude();
                    double lon = l.getLongitude();
                    final String message = "http://maps.google.com/maps?saddr=" + lat + "," + lon+" Hey I am in trouble... HELP!!!";
                    ref= FirebaseDatabase.getInstance().getReference().child("data");
                    ref.addValueEventListener(new ValueEventListener() {

                                                  @Override
                                                  public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                                      if(dataSnapshot.exists()) {
                                                          int i = 0;

                                                          for (DataSnapshot d : dataSnapshot.getChildren())
                                                          {
                                                              sign_up_number3[i]=(String)d.child("phone1").getValue();
                                                              sml1[i]=(String)d.child("sharelocmobile1").getValue();

                                                              for(int o=0;o<sign_up_number3.length;o++)
                                                              {
                                                                  if(phoneno.equals(sign_up_number3[o]))
                                                                  {
                                                                      name=d.getKey();
                                                                      sml=sml1[o];
                                                                  }
                                                              }

                                                          }
                                                      }
                                                      SmsManager smsManager = SmsManager.getDefault();
                                                      StringBuffer smsBody = new StringBuffer();
                                                      smsBody.append(Uri.parse(message));
                                                      android.telephony.SmsManager.getDefault().sendTextMessage(sml, null, smsBody.toString(), null, null);
                                                      startActivity(intent);
                                                  }

                                                  @Override
                                                  public void onCancelled(@NonNull DatabaseError databaseError) {
                                                      Log.i(TAG,"Erri:");
                                                  }

                });


                }
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

    public Location getLocation() {

        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean isGPSEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (isGPSEnabled) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {


                return null;
            }
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 6000, 10, this);
            Location l = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            return l;
        }else{
            Toast.makeText(context,"Please enable GPS", Toast.LENGTH_LONG).show();
        }
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
    public void onBackPressed()
    {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ALERTActivity.this, R.style.yourDialog);

        alertDialog.setTitle("Have you reached your destination");

       alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialog, int which) {
               ref2= FirebaseDatabase.getInstance().getReference().child("Booked Rides");
               ref2.addListenerForSingleValueEvent(new ValueEventListener() {
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
               AlertDialog.Builder alertDialog = new AlertDialog.Builder(ALERTActivity.this, R.style.yourDialog);

               alertDialog.setTitle("How was your Ride?");
               alertDialog.setPositiveButton("GOOD", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       Toast.makeText(getApplicationContext(),
                               "Thank You for your Feedback", Toast.LENGTH_LONG).show();
                       startActivity(new Intent(ALERTActivity.this,Main8navigation.class));

                   }
               });
               alertDialog.setNegativeButton("BAD", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       Toast.makeText(getApplicationContext(),
                               "Thank You for your Feedback", Toast.LENGTH_LONG).show();
                       startActivity(new Intent(ALERTActivity.this,Main8navigation.class));
                   }
               });
               alertDialog.show();
           }
       });
       alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialog, int which) {
               Toast.makeText(getApplicationContext(),
                       "You can not quit this page until you have reached your destination", Toast.LENGTH_LONG).show();
           }
       });
       alertDialog.show();
    }
}
