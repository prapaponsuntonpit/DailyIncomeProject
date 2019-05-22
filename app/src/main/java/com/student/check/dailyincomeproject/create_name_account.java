package com.student.check.dailyincomeproject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class create_name_account extends AppCompatActivity {
    EditText name_account; //ชื่อบัญชี
    EditText money_account;//เงิน
    Button btn_cancel; //ปุ่มยกเลิก
    Button btn_submit;//ปุ่มยื่นยัน
    FirebaseAuth mAuth;
    FirebaseUser user;
    DailyDatabase mDatabaseHelper; //ดาต้าเบส DailyDatabase โดยเรียกมาจากคลาส DailyDatabaseอันที่2
  /*  DatabaseReference mUser;
    DatabaseReference mData;
    FirebaseDatabase firebaseDatabase;*/
    int year;
    int month;
    int dayofMonth;
    Calendar calendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_name_account);

        name_account = (EditText) findViewById(R.id.name_account_new);
        money_account = (EditText) findViewById(R.id.money_account_new);
        btn_cancel = (Button) findViewById(R.id.btn_cancel_accont);
        btn_submit = (Button) findViewById(R.id.btn_submit_account);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

       /* firebaseDatabase = FirebaseDatabase.getInstance();
        mData = firebaseDatabase.getReference("Account").push();
        mUser = firebaseDatabase.getReference("User").child(user.getUid());*/

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        dayofMonth = calendar.get(Calendar.DATE);

        mDatabaseHelper = new DailyDatabase(this);//เอาดาต้าเบสมาประกาศตัวแปลเพื่อเรียกใช้


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = name_account.getText().toString();
                final String money = money_account.getText().toString();
                String date = dayofMonth+"-"+(month+1)+"-"+year;
                if(name.isEmpty() || money.isEmpty()){
                    Toast.makeText(create_name_account.this,"Field is empty",Toast.LENGTH_LONG).show();
                }
                else{

                    addAcc(name,date,money,user.getUid());
                 /*   mUser.child("status").setValue("Have")
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    mData.child("userid").child("Userid").setValue(user.getUid());
                                    mData.child("name_ac").setValue(name_account);
                                    mData.child(dayofMonth+"-"+(month+1)+"-"+year).child("money").setValue(money);

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });*/
                }
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent intent = new Intent(create_name_account.this,LoginPage.class);
                startActivity(intent);
                finish();
            }
        });
    }
    public void addAcc(String name,String date,String money,String userid){
        boolean insertData = mDatabaseHelper.addAcc(name,date,money,userid);

        if(insertData){
            Toast.makeText(this,"Create name account",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(create_name_account.this,Main2Activity.class);
            startActivity(intent);
            finish();
        }
        else{
            Toast.makeText(this,"Non-Create name account",Toast.LENGTH_LONG).show();

        }
    }

}
