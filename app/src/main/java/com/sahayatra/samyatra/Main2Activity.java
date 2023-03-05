package com.sahayatra.samyatra;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import static com.sahayatra.samyatra.variables.address;
import static com.sahayatra.samyatra.variables.dob;
import static com.sahayatra.samyatra.variables.emailid;
import static com.sahayatra.samyatra.variables.female;
import static com.sahayatra.samyatra.variables.gender;
import static com.sahayatra.samyatra.variables.gender1;
import static com.sahayatra.samyatra.variables.male;
import static com.sahayatra.samyatra.variables.name;
import static com.sahayatra.samyatra.variables.occupation;
import static com.sahayatra.samyatra.variables.state;


public class Main2Activity extends AppCompatActivity {
    Button buttonnext1;
    int b;
    String g = null;
    MainActivity main = new MainActivity();
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        toolbar.setTitle("Personal Details");
        setSupportActionBar(toolbar);
        name=(EditText) findViewById(R.id.name);
        emailid=(EditText) findViewById(R.id.emailid);
        dob=(EditText) findViewById(R.id.dob);
        address=(EditText) findViewById(R.id.address);
        state=(EditText) findViewById(R.id.state);
        occupation=(EditText) findViewById(R.id.occupation);
        buttonnext1=findViewById(R.id.buttonnext1);
        male=findViewById(R.id.male);
        female=findViewById(R.id.female);
        gender=findViewById(R.id.gender);




        buttonnext1.setOnClickListener(new View.OnClickListener()
                                       {
                                           public void onClick(View view)
                                           {
                                               b=checkDataEntered();
                                                gender1=isRadioChecked(female,male);
                                               if(b==1) {
                                                           startActivity(new Intent(Main2Activity.this,Main3Activity.class));
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

  boolean isEmail(EditText text){
        CharSequence email =text.getText().toString();
        return (!TextUtils.isEmpty(email)&& Patterns.EMAIL_ADDRESS.matcher(email).matches());
  }
  boolean isEmpty(EditText text)
  {
      CharSequence str=text.getText().toString();
      return TextUtils.isEmpty(str);

  }
 /*boolean isDate(EditText text){
     String str=text.getText().toString();
        if(str==null|| !str.matches("^(1[0-9]|0[1-9]|3[0-1]|2[0-9]).(0[1-9]|1[0-2]).[0-9]{4}$"))
            return false;
     SimpleDateFormat format=new SimpleDateFormat("dd.MM.yyyy");
     try{
         format.parse(str);
         return true;
     }
     catch(ParseException e)
     {
         return false;
     }

 }*/


 String isRadioChecked(RadioButton female,RadioButton male)
 {


     if(female.isChecked())
     {
          g="Female";

     }
     else
     {
          g="Male";

     }

       return g;
 }


  int checkDataEntered(){
      if (isEmpty(name))
      {
          name.setError("This field is required");
          return 0;
      }
      if(isEmail(emailid)==false)
      {
          emailid.setError("Enter valid email address");
          return 0;
      }
    /*  if(isDate(dob)==false)
      {
          dob.setError("Enter Date in given format");
          return 0;
      }*/
      if (isEmpty(address))
      {
          address.setError("This field is required");
          return 0;
      }
      if(isEmpty(state))
      {
          state.setError("This field is required");
          return 0;
      }
      if (isEmpty(occupation))
      {
          occupation.setError("This field is required");
          return 0;
      }

      return 1;
      }

  }