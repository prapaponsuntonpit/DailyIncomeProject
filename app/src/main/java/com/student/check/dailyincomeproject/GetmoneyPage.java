package com.student.check.dailyincomeproject;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

public class GetmoneyPage extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseUser user;
    EditText edt_money;
    EditText date_money;
    EditText time_money;
    Button btn_cancel;
    Button btn_submit;
    DailyDatabase mDatabaseHelper;
 //   DatabaseReference mData;
 //   FirebaseDatabase firebaseDatabase;
    int year;
    int month;
    int dayofMonth;
    int hour;
    int minute;
    Calendar calendar;
    TimePickerDialog mTimePicker;
    DatePickerDialog datePickerDialog;
    String check_money;
    String check_key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getmoney_page);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        dayofMonth = calendar.get(Calendar.DATE);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);

        mDatabaseHelper = new DailyDatabase(this);

      /*  firebaseDatabase = FirebaseDatabase.getInstance();
        mData = firebaseDatabase.getReference("Income").push();*/

        edt_money = (EditText) findViewById(R.id.get_money);
        time_money = (EditText) findViewById(R.id.get_time_money);
        date_money = (EditText) findViewById(R.id.get_date_money);
        btn_cancel = (Button) findViewById(R.id.cancel_get_money);
        btn_submit = (Button) findViewById(R.id.submit_get_money);

        date_money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog = new DatePickerDialog(GetmoneyPage.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                date_money.setText(day+"-"+(month+1)+"-"+year);
                                String dateSelected = date_money.getText().toString();
                                Cursor cursor = mDatabaseHelper.getAllCotacts(dateSelected);
                                cursor.moveToFirst();
                                if (cursor.getCount() != 0) {
                                    while (cursor.isAfterLast() == false) {
                                        check_key = cursor.getString(cursor.getColumnIndex("ID"));
                                        check_money = cursor.getString(cursor.getColumnIndex("money"));
                                        cursor.moveToNext();
                                        Toast.makeText(GetmoneyPage.this, check_key, Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                        },year,month,dayofMonth);
                datePickerDialog.show();

            }
        });

        time_money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTimePicker = new TimePickerDialog(GetmoneyPage.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int seletedHour, int selectedMinute) {
                          time_money.setText(seletedHour+":"+selectedMinute);
                    }
                },hour,minute,true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });




        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String time = time_money.getText().toString();
               String date = date_money.getText().toString();
               String money = edt_money.getText().toString();
               String total = String.valueOf(Integer.valueOf(money)+Integer.valueOf(check_money));
               addIncome(check_key,date,money,time,"Get",user.getUid());
               updateAcc(check_key,total);
               Intent intent = new Intent(GetmoneyPage.this,Main2Activity.class);
               startActivity(intent);
               finish();

            }
        });


        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GetmoneyPage.this,Main2Activity.class);
                startActivity(intent);
                finish();
            }
        });


    }

    public void updateAcc(String id,String money){
        boolean updateData = mDatabaseHelper.updateAcc(id,money);
        if(updateData){
            Toast.makeText(GetmoneyPage.this, "update account", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(GetmoneyPage.this, "Non-update account", Toast.LENGTH_LONG).show();
        }
    }
    public void addIncome(String acc_id,String date,String money,String time,String cate,String userid){
        boolean insertData = mDatabaseHelper.addIncome(acc_id,date,money,time,cate,userid);
        Log.d("Check",acc_id+","+date+","+money+","+time+","+cate+","+userid);
        if(insertData) {
            Toast.makeText(GetmoneyPage.this, "Create name account", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(GetmoneyPage.this,"Non-Create name account",Toast.LENGTH_LONG).show();

        }
    }
}
