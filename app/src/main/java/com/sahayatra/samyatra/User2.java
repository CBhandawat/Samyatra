package com.sahayatra.samyatra;

import android.widget.EditText;

public class User2 {
    public String phone1;
    public String name1;
    public String emailid1;
    public String dob1;
    public String address1;
    public String state1;
    public String occupation1;
    public String car_number1;
    public String car_model1;
    public String car_colour1;
    public String lic_no1;
    public String sharelocmobile1;
    public String gender2;
    public String preference1,pict;
    public String profile_picture_url1,fromo,too,time1,name2,phone2,sign_up_number1,name4,phone4;


    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)



    public User2(EditText editTextPhone, EditText name, EditText emailid, EditText dob, EditText address, EditText state, EditText occupation, EditText car_number, EditText car_model, EditText car_colour, EditText lic_no, EditText sharelocmobile, String gender1, String preference, String  downloadUri) {
        phone1 = editTextPhone.getText().toString();
        name1 = name.getText().toString();
        emailid1 = emailid.getText().toString();
        dob1 = dob.getText().toString();
        address1 = address.getText().toString();
        state1 = state.getText().toString();
        occupation1 = occupation.getText().toString();
        car_number1 = car_number.getText().toString();
        car_model1 = car_model.getText().toString();
        car_colour1 = car_colour.getText().toString();
        lic_no1 = lic_no.getText().toString();
        sharelocmobile1 = sharelocmobile.getText().toString();
        this.gender2 = gender1;
        this.preference1 = preference;
        profile_picture_url1 = downloadUri.toString();
    }

    public User2(EditText from2,EditText to2,String editTextPhone, String name,EditText time2,String pic){
        fromo = from2.getText().toString();
        too=to2.getText().toString();
        this.phone1 = editTextPhone;
        this.name1 = name;
        time1=time2.getText().toString();
        this.pict=pic;
    }
    public User2(String name,String phone,String sign_up_number0)
    {
        this.name2=name;
        this.phone2=phone;
        this.sign_up_number1=sign_up_number0;
    }
    public User2(String name,String phone)
    {
        this.name4=name;
        this.phone4=phone;
    }
}
