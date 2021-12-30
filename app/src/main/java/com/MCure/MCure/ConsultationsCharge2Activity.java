package com.MCure.MCure;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class ConsultationsCharge2Activity extends AppCompatActivity {

    ImageView imgarrow_conult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultations_charge2);

        imgarrow_conult=findViewById(R.id.imgarrow_conult);
        imgarrow_conult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}