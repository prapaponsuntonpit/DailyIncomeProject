package com.student.check.dailyincomeproject;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class SavemonryPage extends Fragment {
    Button btn_save;
    EditText number_save;
    EditText date_save;
    View view;
    FirebaseAuth mAuth;
    FirebaseUser user;
    DailyDatabase mDatabaseHelper;
  /*  DatabaseReference mData;
    FirebaseDatabase firebaseDatabase;*/
    int year;
    int month;
    int dayofMonth;
    Calendar calendar;
    DatePickerDialog datePickerDialog;
    String check_Name;
    String check_key;
    boolean check_exist;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_savemonry_page,container,false);
        btn_save = (Button) view.findViewById(R.id.btn_save_activity);
        number_save = (EditText) view.findViewById(R.id.number_save_input);
        date_save = (EditText) view.findViewById(R.id.date_save_input);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        dayofMonth = calendar.get(Calendar.DATE);

        mDatabaseHelper = new DailyDatabase(getActivity());

        check_exist = false;

        date_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                date_save.setText(day+"-"+(month+1)+"-"+year);
                                String dateSelected = date_save.getText().toString();
                                Cursor cursor = mDatabaseHelper.getAllCotacts(dateSelected);
                                cursor.moveToFirst();
                                if(cursor.getCount() != 0){
                                    while(cursor.isAfterLast() == false) {
                                        check_key = cursor.getString(cursor.getColumnIndex("ID"));
                                        check_Name = cursor.getString(cursor.getColumnIndex("Name"));
                                        number_save.setText(cursor.getString(cursor.getColumnIndex("money")));
                                        check_exist = true;
                                        cursor.moveToNext();
                                        Toast.makeText(getActivity(),check_key,Toast.LENGTH_LONG).show();
                                    }

                                }
                                else{
                                    while(cursor.isAfterLast() == false) {
                                        check_Name = cursor.getString(cursor.getColumnIndex("Name"));
                                        cursor.moveToNext();
                                    }
                                    number_save.setText("");
                                    check_exist = false;
                                }

                            }
                        },year,month,dayofMonth);
                datePickerDialog.show();
            }
        });



        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

       /* firebaseDatabase = FirebaseDatabase.getInstance();
        mData = firebaseDatabase.getReference("Account").child("userid").child(user.getUid());*/

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String money = number_save.getText().toString();
                String date = date_save.getText().toString();
               // mData.child(date).child("money").setValue(money);
                if(check_exist) {
                    updateAcc(check_key,money);
                }
                else{
                    addAcc(check_Name, date, money, user.getUid());
                    Toast.makeText(getActivity(),"create Account",Toast.LENGTH_LONG).show();
                }
                 Toast.makeText(getActivity(),"Sucess !!",Toast.LENGTH_LONG).show();
               // Toast.makeText(getActivity(),check_key,Toast.LENGTH_LONG).show();
                date_save.setText("วว/ดด/ปป");
                number_save.setText("");
            }
        });


        return view;
    }
    public void updateAcc(String id,String money){
        boolean updateData = mDatabaseHelper.updateAcc(id,money);
        if(updateData){
            Toast.makeText(getActivity(), "update account", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(getActivity(), "Non-update account", Toast.LENGTH_LONG).show();
        }
    }
    public void addAcc(String name,String date,String money,String userid){
        boolean insertData = mDatabaseHelper.addAcc(name,date,money,userid);

        if(insertData) {
            Toast.makeText(getActivity(), "Create name account", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(getActivity(),"Non-Create name account",Toast.LENGTH_LONG).show();

        }
    }
}
