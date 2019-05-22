package com.student.check.dailyincomeproject;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class HomePage extends Fragment {
    View view;
    EditText money_balance;
    EditText date_money;
    RecyclerView recyclerView; //ล/ิสรายการเรียงลงมาๆ
    ImageButton img_add;
    ImageButton img_del;
    FirebaseAuth mAuth;
    FirebaseUser user;
    DailyDatabase mDatabaseHelper;
  /*  DatabaseReference mData;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference Dataincome;*/
    DatePickerDialog datePickerDialog;
    IncomeAdapter mAdapter;
    int year;
    int month;
    int dayofMonth;
    Calendar calendar;
    LinearLayout linearLayout;
    String check_Name;
    String check_key;
    private SQLiteDatabase mData;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_home_page,container,false);
        linearLayout = (LinearLayout) view.findViewById(R.id.btn_date_show);
        money_balance = (EditText) view.findViewById(R.id.edit_money_balance);
        date_money = (EditText) view.findViewById(R.id.edit_date_money);
        img_add = (ImageButton) view.findViewById(R.id.btn_add_income);
        img_del = (ImageButton) view.findViewById(R.id.btn_del_income);


        mDatabaseHelper = new DailyDatabase(getActivity());
        mData = mDatabaseHelper.getWritableDatabase();

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        dayofMonth = calendar.get(Calendar.DATE);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycle_income);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


//บันทึกรับจ่าย
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog = new DatePickerDialog(getActivity(),
                     new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                date_money.setText(day+"-"+(month+1)+"-"+year);
                                String dateSelected = date_money.getText().toString();
                                Cursor cursor = mDatabaseHelper.getAllCotacts(dateSelected);
                                cursor.moveToFirst();
                                if(cursor.getCount() != 0) {
                                    while (cursor.isAfterLast() == false) {
                                        check_key = cursor.getString(cursor.getColumnIndex("ID"));
                                        check_Name = cursor.getString(cursor.getColumnIndex("Name"));
                                        money_balance.setText(cursor.getString(cursor.getColumnIndex("money")));
                                        cursor.moveToNext();
                                        Toast.makeText(getActivity(), check_key, Toast.LENGTH_LONG).show();
                                    }
                                }
                                else{
                                    money_balance.setText("");
                                }
                                mAdapter = new IncomeAdapter(getActivity(),getAllitems(dateSelected));
                                recyclerView.setAdapter(mAdapter);

                            }
                        },year,month,dayofMonth);
                datePickerDialog.show();
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0
                ,ItemTouchHelper.LEFT ) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                String dateSelected = date_money.getText().toString();
                String total = money_balance.getText().toString();
                Toast.makeText(getActivity(),(String)viewHolder.itemView.getTag(),Toast.LENGTH_LONG).show();
                removeItem((String)viewHolder.itemView.getTag(),(String)dateSelected,(String) total,(String) check_key);
            }
        }).attachToRecyclerView(recyclerView);
        img_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),GetmoneyPage.class);
                startActivity(intent);
            }
        });

        img_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),PaymoneyPage.class);
                startActivity(intent);
            }
        });




       /* firebaseDatabase = FirebaseDatabase.getInstance();
        mData = firebaseDatabase.getReference("Account");
        Dataincome = firebaseDatabase.getReference("Income");

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();*/

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        date_money.setText((dayofMonth)+"-"+(month+1)+"-"+year);
        String dateSelected = date_money.getText().toString();
        Cursor cursor = mDatabaseHelper.getAllCotacts(dateSelected);
        cursor.moveToFirst();
        if(cursor.getCount() != 0) {
            while (cursor.isAfterLast() == false) {
                check_key = cursor.getString(cursor.getColumnIndex("ID"));
                check_Name = cursor.getString(cursor.getColumnIndex("Name"));
                money_balance.setText(cursor.getString(cursor.getColumnIndex("money")));
                cursor.moveToNext();
                Toast.makeText(getActivity(), check_key, Toast.LENGTH_LONG).show();
            }
        }
        else{
            money_balance.setText("");
        }

        mAdapter = new IncomeAdapter(getActivity(),getAllitems(dateSelected));
        recyclerView.setAdapter(mAdapter);



    }
    private void  removeItem(String id,String date,String total,String check_key){
        Cursor show = mDatabaseHelper.getAllIncome(id);
        show.moveToFirst();
        String money = null;
        String cate = null;
        String sum = null;
        if(show.getCount() != 0) {
            while (show.isAfterLast() == false) {
                money = show.getString(show.getColumnIndex("money"));
                cate = show.getString(show.getColumnIndex("cate"));
                show.moveToNext();

            }
        }
        if(cate.equals("Get")) {
           sum = String.valueOf(Integer.valueOf(total) - Integer.valueOf(money));
        }
        else if(cate.equals("Pay")){
            sum = String.valueOf(Integer.valueOf(total) + Integer.valueOf(money));
        }
        Log.d("Update",check_key+" "+sum);
        updateAcc(check_key,sum);
        mData.delete("Income","ID"+"="+id,null );
        //Toast.makeText(getActivity(),"delete id ="+id+" and date ="+date,Toast.LENGTH_LONG).show();
        mAdapter.swapCursor(getAllitems(date));

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
    private Cursor getAllitems(String date){
        return mData.query(
                "Income",
                null,
                "date=?",
               new String[]{date},
                null,
                null,
                null
        );
    }

}
