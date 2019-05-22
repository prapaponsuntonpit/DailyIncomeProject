package com.student.check.dailyincomeproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class advice8 extends AppCompatActivity {
    Button buttonnine;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advice8);
        buttonnine = findViewById(R.id.button_nine);
        buttonnine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentOne=new Intent(advice8.this,advice9.class);
                startActivity(intentOne);
            }
        });
    }
}
