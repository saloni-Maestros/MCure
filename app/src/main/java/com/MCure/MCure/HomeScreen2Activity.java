package com.MCure.MCure;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class HomeScreen2Activity extends AppCompatActivity {
 TextView Tv_history1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen2);
        Tv_history1 = findViewById(R.id.Tv_history1);
        Tv_history1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeScreen2Activity.this,CaseHistoryscrenActivity.class));
            }
        });
    }
}