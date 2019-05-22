package com.student.check.dailyincomeproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class advice10 extends AppCompatActivity {
    Button buttoneleven;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advice10);

        buttoneleven = findViewById(R.id.button_eleven);
        buttoneleven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentOne=new Intent(advice10.this,Main2Activity.class);
                startActivity(intentOne);
            }
        });
    }
}
