package com.MCure.MCure;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class CaseHistryDeatilActivity extends AppCompatActivity {
 ImageView Iv_arow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case_histry_deatil);
        Iv_arow = findViewById(R.id.Iv_arow);
        Iv_arow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              onBackPressed();
            }
        });
    }
}