package com.sahayatra.samyatra;

import android.app.Application;
import android.content.SharedPreferences;
import android.net.Uri;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;

public class variables extends Application {
    public static EditText time,from,to,name, emailid,dob,address,state,occupation,editTextPhone,car_number,car_model,car_colour,lic_no,sharelocmobile;
    public static RadioButton radioButton,male,female;
   public static RadioGroup radioGroup;
   public static TextView gender;
   public static String gender1, preference,f,name2;
  public static String downloadUri;
  public static DatabaseReference mDatabase;
    public static String uid;
    public static SharedPreferences myPrefs;
   public static String user_phone,sign_up_number,u;
   public static String phoneno;
}
