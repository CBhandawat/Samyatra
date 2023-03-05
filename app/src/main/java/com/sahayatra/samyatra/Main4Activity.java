package com.sahayatra.samyatra;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Button;
import android.widget.Toast;

import static com.sahayatra.samyatra.variables.gender;
import static com.sahayatra.samyatra.variables.preference;

public class Main4Activity extends AppCompatActivity {

    Button buttonnext3;
    RadioButton findonly,both;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        buttonnext3=findViewById(R.id.buttonnext3);
        findonly=findViewById(R.id.findonly);
        both=findViewById(R.id.both);
        buttonnext3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                preference = isRadioCheck(findonly, both);
                if (preference == "Both") {
                    startActivity(new Intent(Main4Activity.this, Main5Activity.class));
                }
                else if(preference=="Find Only")
                {
                    startActivity(new Intent(Main4Activity.this, Main6Activity.class));
                }
            }
            }
        );
    }

    String isRadioCheck(RadioButton findonly,RadioButton both)
    {
        if(!findonly.isChecked()||!both.isChecked())
        {
            gender.setError("Please Select an Option");
            gender.requestFocus();
        }
        if(findonly.isChecked())
        {
            String g="Find Only";
            return g;
        }
        else
        {
            String g="Both";
            return g;
        }
    }
    }