package com.student.check.dailyincomeproject;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class PaymoneyPage extends AppCompatActivity {
    EditText edit_pay;
    EditText edit_date;
    EditText edit_time;
    Button cancel_btn;
    Button submit_btn;
    FirebaseAuth mAuth;
    FirebaseUser user;
    String check_money;
    String check_key;
    DailyDatabase mDatabaseHelper;
    int year;
    int month;
    int dayofMonth;
    int hour;
    int minute;
    Calendar calendar;
    TimePickerDialog mTimePicker;
    DatePickerDialog datePickerDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paymoney_page);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();


        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        dayofMonth = calendar.get(Calendar.DATE);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);

        mDatabaseHelper = new DailyDatabase(this);

        edit_pay = (EditText) findViewById(R.id.pay_money);
        edit_date = (EditText) findViewById(R.id.pay_date_money);
        edit_time = (EditText) findViewById(R.id.pay_time_money);
        cancel_btn = (Button) findViewById(R.id.cancel_get_money);
        submit_btn = (Button) findViewById(R.id.submit_get_money);

        edit_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog = new DatePickerDialog(PaymoneyPage.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                               edit_date.setText(day+"-"+(month+1)+"-"+year);
                                String dateSelected = edit_date.getText().toString();
                                Cursor cursor = mDatabaseHelper.getAllCotacts(dateSelected);
                                cursor.moveToFirst();
                                if(cursor.getCount() != 0){
                                    while(cursor.isAfterLast() == false) {
                                        check_key = cursor.getString(cursor.getColumnIndex("ID"));
                                        check_money = cursor.getString(cursor.getColumnIndex("money"));
                                        cursor.moveToNext();
                                        Toast.makeText(PaymoneyPage.this,check_key,Toast.LENGTH_LONG).show();
                                    }

                                }
                            }
                        },year,month,dayofMonth);
                datePickerDialog.show();
            }
        });

       edit_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTimePicker = new TimePickerDialog(PaymoneyPage.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int seletedHour, int selectedMinute) {
                        edit_time.setText(seletedHour+":"+selectedMinute);
                    }
                },hour,minute,true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });




        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String money = edit_pay.getText().toString();
                String time = edit_time.getText().toString();
                String date = edit_date.getText().toString();
                String total = String.valueOf(Integer.valueOf(check_money)-Integer.valueOf(money));
                addIncome(check_key,date,money,time,"Pay",user.getUid());
                updateAcc(check_key,total);
                Intent intent =new Intent(PaymoneyPage.this,Main2Activity.class);
                startActivity(intent);
                finish();
            }
        });

        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PaymoneyPage.this,Main2Activity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void updateAcc(String id,String money){
        boolean updateData = mDatabaseHelper.updateAcc(id,money);
        if(updateData){
            Toast.makeText(PaymoneyPage.this, "update account", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(PaymoneyPage.this, "Non-update account", Toast.LENGTH_LONG).show();
        }
    }
    public void addIncome(String acc_id,String date,String money,String time,String cate,String userid){
        boolean insertData = mDatabaseHelper.addIncome(acc_id,date,money,time,cate,userid);

        if(insertData) {
            Toast.makeText(PaymoneyPage.this, "Create name account", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(PaymoneyPage.this,"Non-Create name account",Toast.LENGTH_LONG).show();

        }
    }
}
