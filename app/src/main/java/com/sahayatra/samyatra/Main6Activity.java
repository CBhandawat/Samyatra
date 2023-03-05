package com.sahayatra.samyatra;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static com.sahayatra.samyatra.variables.address;
import static com.sahayatra.samyatra.variables.dob;
import static com.sahayatra.samyatra.variables.downloadUri;
import static com.sahayatra.samyatra.variables.editTextPhone;
import static com.sahayatra.samyatra.variables.emailid;
import static com.sahayatra.samyatra.variables.gender1;
import static com.sahayatra.samyatra.variables.mDatabase;
import static com.sahayatra.samyatra.variables.name;
import static com.sahayatra.samyatra.variables.occupation;
import static com.sahayatra.samyatra.variables.preference;
import static com.sahayatra.samyatra.variables.sharelocmobile;
import static com.sahayatra.samyatra.variables.state;
import static com.sahayatra.samyatra.variables.uid;

public class Main6Activity extends AppCompatActivity {
    int b;
    EditText aadhar_number;
    Button buttonregister1;
    String[] ids;
    InputStream inputStream;
    Toolbar toolbar;
    int flag=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        toolbar.setTitle("Aadhar Details");
        setSupportActionBar(toolbar);
        buttonregister1=findViewById(R.id.buttonregister1);
        aadhar_number=findViewById(R.id.aadhar_number);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        buttonregister1.setOnClickListener(new View.OnClickListener() {
                                              public void onClick(View view) {
                                                  b=checkdata();
                                                  if(b==1) {
                                                      inputStream=getResources().openRawResource(R.raw.aadhar_dataset);
                                                      BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream));
                                                      String csvLine;
                                                      try
                                                      { while((csvLine=reader.readLine())!=null) {
                                                              if ((aadhar_number.getText().toString()).equals(csvLine)) {
                                                                  User user = new User(editTextPhone,name, emailid,dob,address,state,occupation,aadhar_number,sharelocmobile,gender1,preference,downloadUri);
                                                                  uid=mDatabase.push().getKey();
                                                                  mDatabase.child("data").child(uid).setValue(user);
                                                                  startActivity(new Intent(Main6Activity.this, Main8navigation.class));
                                                                  flag = 1;
                                                                  break;
                                                              }
                                                              else
                                                              {
                                                                  flag=0;
                                                              }
                                                          }
                                                      }catch (IOException e)
                                                      {
                                                          throw new RuntimeException("error in running CSV file");
                                                      }
                                                      if(flag==0)
                                                      {
                                                          aadhar_number.setError("Aadhar authentication failed");
                                                          aadhar_number.requestFocus();
                                                      }


                                                  }
                                                  else
                                                  {
                                                      Toast.makeText(getApplicationContext(),
                                                              "Enter Valid Details", Toast.LENGTH_LONG).show();

                                                  }
                                              }
                                          }
        );
    }
    boolean isAadhar(EditText text) {
        String str = text.getText().toString();
        if (str == null || !str.matches("^[0-9]{4}\\s[0-9]{4}\\s[0-9]{4}$"))
            return false;
        else
            return true;
    }
    int checkdata() {

        if (isAadhar(aadhar_number) == false) {
            aadhar_number.setError("Enter a valid Aadhar Number");
            return 0;
        }
        return 1;
    }
}

