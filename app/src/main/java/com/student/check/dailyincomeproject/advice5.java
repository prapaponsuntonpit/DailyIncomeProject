package com.student.check.dailyincomeproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class advice5 extends AppCompatActivity {
    Button buttonsix;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advice5);

        buttonsix= findViewById(R.id.button_six);
        buttonsix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentOne=new Intent(advice5.this,advice6.class);
                startActivity(intentOne);
            }
        });
    }
}
