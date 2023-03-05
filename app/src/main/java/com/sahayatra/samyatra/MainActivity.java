package com.sahayatra.samyatra;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static android.content.ContentValues.TAG;
import static com.sahayatra.samyatra.variables.editTextPhone;
import static com.sahayatra.samyatra.variables.mDatabase;
import static com.sahayatra.samyatra.variables.myPrefs;
import static com.sahayatra.samyatra.variables.phoneno;
import static com.sahayatra.samyatra.variables.sign_up_number;

public class MainActivity extends AppCompatActivity {
    Button sendotp,verify;
    EditText editTextCode;
    FirebaseAuth mAuth;
    DatabaseReference ref,ref1;
    String codeSent,s,name;
    SharedPreferences sp;
    String[] offer_phone=new String[100];
    String[] offer_time=new String[100];
    int flag=0;
    int f=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sp = getSharedPreferences("login",MODE_PRIVATE);
        if(sp.getBoolean("logged",false)){
            startActivity(new Intent(MainActivity.this,Main8navigation.class));
        }

        mAuth= FirebaseAuth.getInstance();
        editTextPhone=(EditText) findViewById(R.id.phoneno);
        editTextCode=(EditText) findViewById(R.id.otp);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        ref = FirebaseDatabase.getInstance().getReference().child("data");


        sendotp=(Button)this.findViewById(R.id.buttonsendotp);
        verify=(Button)this.findViewById(R.id.buttonverify);
        verify.setEnabled(false);


        sendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myPrefs = getSharedPreferences("prefID", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = myPrefs.edit();
                editor.putString("ageKey", editTextPhone.getText().toString());
                editor.apply();
             sendVerificationPhone();


            }
        });



        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                verifyOtp();

            }
        });

    }
    private void verifyOtp()
    {
        sendVerificationPhone();
        sign_up_number=editTextPhone.getText().toString();
        //offering
       ref1= FirebaseDatabase.getInstance().getReference().child("Offering DB");
        ref1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    int i = 0;
                    int o = 0;
                    for (DataSnapshot d : dataSnapshot.getChildren()) {

                        offer_time[i] = (String) d.child("time1").getValue();
                        SimpleDateFormat df=new SimpleDateFormat("HH:mm");
                        try {
                            Date date_offer = df.parse(offer_time[i]);
                            Calendar cal = Calendar.getInstance();
                            cal.setTime(date_offer);
                            cal.add(Calendar.MINUTE, 30);
                            String newTime = df.format(cal.getTime());
                            Date date1=df.parse(newTime);
                            String s1 = df.format(new Date());
                            Date date=df.parse(s1);
                            if(date.after(date1))
                            {
                                name=d.getKey();
                                d.getRef().removeValue();
                                f=1;
                            }
                            else
                            {
                                f=0;
                            }
                        }
                        catch (Exception e){

                        }
                        if(f==1)
                        {
                            name=d.getKey();
                            d.getRef().removeValue();
                        }

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        String code= editTextCode.getText().toString();
        if(code.isEmpty())
        {
            editTextCode.setError("This field is required");
            editTextCode.requestFocus();
            return;
        }
        if(code.length()<6)
        {
            editTextCode.setError("Invalid OTP");
            editTextCode.requestFocus();
            return;
        }
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeSent, code);
        signInWithPhoneAuthCredential(credential);

    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    int i=0;

                                    for (DataSnapshot d : dataSnapshot.getChildren()) {
                                        offer_phone[i] = (String) d.child("phone1").getValue();

                                            if (sign_up_number.equals(offer_phone[i]))
                                    {

                                        flag=1;
                                        break;

                                    }
                                }
                                    if(flag==1) {
                                    startActivity(new Intent(MainActivity.this,Main8navigation.class));
                                    Toast.makeText(getApplicationContext(),
                                            "Welcome",Toast.LENGTH_LONG).show();
                                        sp.edit().putBoolean("logged",true).apply();
                                        myPrefs = getSharedPreferences("prefID", Context.MODE_PRIVATE);


                                        phoneno= myPrefs.getString("ageKey","No name");
                                        Log.i(TAG, "oila : " + phoneno);


                                    }
                                    else
                                    {
                                        startActivity(new Intent(MainActivity.this, Main1Activity.class));
                                        Toast.makeText(getApplicationContext(),
                                                "Verification successful", Toast.LENGTH_LONG).show();
                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                        }
                        else {
                             if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Toast.makeText(getApplicationContext(),
                                        "Incorrect Verification Code",Toast.LENGTH_LONG).show();

                            }

                        }
                    }
                });
    }

    private void sendVerificationPhone()
    {
        String phoneNumber= editTextPhone.getText().toString();

        if(phoneNumber.isEmpty())
        {

            editTextPhone.setError("This field is required");
            editTextPhone.requestFocus();
            return;
        }
      /* if(phoneNumber.length()<13)
        {

            editTextPhone.setError("Please enter a valid phone number");
            editTextPhone.requestFocus();
            return;
        }*/
        verify.setEnabled(true);

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks

    }



    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

        }

        @Override
        public void onVerificationFailed(FirebaseException e) {

        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            codeSent= s;
        }
    };
}

