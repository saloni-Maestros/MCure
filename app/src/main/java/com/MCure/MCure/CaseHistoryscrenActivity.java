package com.MCure.MCure;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class CaseHistoryscrenActivity extends AppCompatActivity {
    TextView Tv_caseHistry;
    ImageView ivarrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case_historyscren);
        Tv_caseHistry = findViewById(R.id.Tv_caseHistry);
        ivarrow = findViewById(R.id.ivarrow);
        ivarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   startActivity(new Intent(CaseHistoryscrenActivity.this,HomeScreen2Activity.class));

            }
        });
        Tv_caseHistry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // startActivity(new Intent(CaseHistoryscrenActivity.this,CaseHistryDeatilActivity.class));
            }
        });
    }

}