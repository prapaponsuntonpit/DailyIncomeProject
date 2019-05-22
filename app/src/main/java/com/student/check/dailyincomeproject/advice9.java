package com.student.check.dailyincomeproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class advice9 extends AppCompatActivity {
    Button buttonten;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advice9);
        buttonten = findViewById(R.id.button_ten);
        buttonten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentOne=new Intent(advice9.this,advice10.class);
                startActivity(intentOne);
            }
        });
    }
}
