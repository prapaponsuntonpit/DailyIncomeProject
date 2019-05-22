package com.student.check.dailyincomeproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class advice4 extends AppCompatActivity {
    Button buttonfive;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advice4);
        buttonfive = findViewById(R.id.button_five);
        buttonfive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentOne=new Intent(advice4.this,advice5.class);
                startActivity(intentOne);
            }
        });
    }
}
