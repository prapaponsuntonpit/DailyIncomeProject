package com.student.check.dailyincomeproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class advice2 extends AppCompatActivity {
    Button buttonTee;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advice2);

        buttonTee = findViewById(R.id.button_Tee);
        buttonTee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentTwo = new Intent(advice2.this,advice3.class);
                startActivity(intentTwo);
            }
        });
    }
}
