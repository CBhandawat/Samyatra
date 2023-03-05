package com.sahayatra.samyatra;
import android.widget.EditText;

class User {
    public String phone1;
    public String name1;
    public String emailid1;
    public String dob1;
    public String address1;
    public String state1;
    public String occupation1;
    public String sharelocmobile1;
    public String gender2;
    public String preference1;
    public String aadhar_number1,sign_up_number2,h1;
    public String profile_picture_url1,fromf,tof,name,time,time1,pictu;
    public int f;


    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)



    public User(EditText editTextPhone, EditText name, EditText emailid, EditText dob, EditText address, EditText state, EditText occupation, EditText aadhar_number, EditText sharelocmobile, String gender1, String preference,String downloadUri) {
        phone1 = editTextPhone.getText().toString();
        name1 = name.getText().toString();
        emailid1 = emailid.getText().toString();
        dob1 = dob.getText().toString();
        address1 = address.getText().toString();
        state1 = state.getText().toString();
        occupation1 = occupation.getText().toString();
        aadhar_number1 = aadhar_number.getText().toString();
        sharelocmobile1 = sharelocmobile.getText().toString();
        this.gender2 = gender1;
        this.preference1 = preference;
        profile_picture_url1 = downloadUri.toString();
    }

    public User(EditText from,EditText to, String sign_up_number, String h,EditText time){
        fromf = from.getText().toString();
        tof=to.getText().toString();
        this.sign_up_number2=sign_up_number;
         this.h1=h;
        time1 = time.getText().toString();
    }
    public User(String name, String time,String pictu) {
        this.name = name;
        this.time = time;
        this.pictu=pictu;
    }


}

