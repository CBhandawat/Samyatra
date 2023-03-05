package com.sahayatra.samyatra;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
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


public class Main5Activity extends AppCompatActivity {
    int b;
    EditText car_number,car_model,car_colour,lic_no;
    Button buttonregister;
    InputStream inputStream;
    String Lic_no;
    String[] ids;
    int flag=0;
    Toolbar toolbar;
    String[] idss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        toolbar.setTitle("Vehicle Details");
        setSupportActionBar(toolbar);

        buttonregister=findViewById(R.id.buttonregister);
        car_number=findViewById(R.id.car_number);
        car_model=findViewById(R.id.car_model);
        car_colour=findViewById(R.id.car_colour);
        lic_no=findViewById(R.id.lic_no);
        Lic_no=lic_no.getText().toString();
        Log.e("License no",""+Lic_no);




        mDatabase = FirebaseDatabase.getInstance().getReference();


        buttonregister.setOnClickListener(new View.OnClickListener() {
                                           public void onClick(View view) {
                                               b=checkdata();
                                               if(b==1)
                                               {
                                               inputStream=getResources().openRawResource(R.raw.licence_dataset);
                                               BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream));
                                                   String csvLine;
                                               try
                                               { while((csvLine=reader.readLine())!=null) {
                                                       ids = csvLine.split(",");
                                                       if ((lic_no.getText().toString()).equals(ids[0]) && (ids[1].equals("Four"))) {
                                                           User2 user2 = new User2(editTextPhone, name, emailid, dob, address, state, occupation, car_number, car_model, car_colour, lic_no, sharelocmobile, gender1, preference,downloadUri);
                                                           uid = mDatabase.push().getKey();
                                                           mDatabase.child("data").child(uid).setValue(user2);
                                                           startActivity(new Intent(Main5Activity.this, Main8navigation.class));
                                                           flag = 1;
                                                           break;
                                                       } else {
                                                           flag = 0;
                                                       }
                                                   }
                                               }catch (IOException e)
                                               { throw new RuntimeException("error in running CSV file");
                                               }if(flag==0)
                                                   { lic_no.setError("License authentication failed");
                                                       lic_no.requestFocus();
                                                   }}
                                               else
                                               {
                                                   Toast.makeText(getApplicationContext(),
                                                           "Enter Valid Details", Toast.LENGTH_LONG).show();
                                               }
                                           }
                                       }
        );
    }

    boolean isEmpty(EditText text)
    {
        CharSequence str=text.getText().toString();
        return TextUtils.isEmpty(str);

    }
    boolean isNumber(EditText text) {
        String str = text.getText().toString();
        if (str == null || !str.matches("^[A-Z]{2}[0-9]{2}\\s[A-Z]{2}\\s[0-9]{4}$"))
            return false;
        else
            return true;
    }
    boolean isLicence(EditText text) {
        String str = text.getText().toString();
        if (str == null || !str.matches("^[A-Z]{2}[0-9]{2}\\s[0-9]{11}$"))
            return false;
        else
            return true;
    }


    int checkdata()
    {
        if(isNumber(car_number)==false)
        {
            car_number.setError("Enter a valid car number");
            car_number.requestFocus();
            return 0;
        }

        if(isEmpty(car_model))
        {
            car_model.setError("Enter a model of your car");
            car_model.requestFocus();
            return 0;
        }

        if(isEmpty(car_colour))
        {
            car_colour.setError("Enter a colour");
            car_colour.requestFocus();
            return 0;
        }

        if(isLicence(lic_no)==false)
        {
            lic_no.setError("Enter a valid licence number");
            lic_no.requestFocus();
            return 0;
        }

        return 1;
    }
}
