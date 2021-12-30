package com.MCure.MCure;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.button.MaterialButton;

public class CaseHistoryChatActivity extends AppCompatActivity {
    MaterialButton mbtn_caseHistory;
    ImageView ivarrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case_history_chat);
        mbtn_caseHistory = findViewById(R.id.mbtn_caseHistory);
        ivarrow = findViewById(R.id.ivarrow);
        mbtn_caseHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),CaseHistryDeatilActivity.class));
            }
        });
        ivarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}