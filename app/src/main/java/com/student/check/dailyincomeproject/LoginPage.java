package com.student.check.dailyincomeproject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import dmax.dialog.SpotsDialog;

public class LoginPage extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseUser user;
    EditText login;
    EditText pass;
    Button login_btn;
    Button register_btn;
    DatabaseReference mData;
    FirebaseDatabase firebaseDatabase;
    SpotsDialog pd;
    DailyDatabase mDatabaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        mAuth = FirebaseAuth.getInstance();


        pd = new SpotsDialog(this,R.style.Custom);

      //2  firebaseDatabase = FirebaseDatabase.getInstance();
       // mData = firebaseDatabase.getReference("User");

        login = (EditText) findViewById(R.id.email_login);
        pass= (EditText) findViewById(R.id.pass_login);
        login_btn = (Button) findViewById(R.id.btn_login);
        register_btn = (Button) findViewById(R.id.btn_register);

        mDatabaseHelper = new DailyDatabase(this);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String email = login.getText().toString();
               String password = pass.getText().toString();
               mAuth.signInWithEmailAndPassword(email,password)
                       .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                           @Override
                           public void onComplete(@NonNull Task<AuthResult> task) {
                               user = mAuth.getCurrentUser();
                               checkAccount(user.getUid());
                           }
                       });
            }
        });
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginPage.this,RegisterPage.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        user = mAuth.getCurrentUser();
        if(user != null){
          checkAccount(user.getUid());
        }
    }
    public void checkAccount(String userid){
        pd.show();
        boolean insertData = mDatabaseHelper.hasAcc(userid);

        if(insertData){
            Toast.makeText(this,"Exists",Toast.LENGTH_LONG).show();
            Intent intent= new Intent(LoginPage.this,Main2Activity.class);
            startActivity(intent);
            finish();
        }
        else{
            Toast.makeText(this,"Non-Exists",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(LoginPage.this,create_name_account.class);
            startActivity(intent);
            finish();//ปิดหน้า
        }
      /*  mData.child(user.getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String Status = dataSnapshot.child("status").getValue().toString();
                        if(Status.equals("None")){
                            Intent intent = new Intent(LoginPage.this,create_name_account.class);
                            startActivity(intent);
                            finish();
                        }
                        else if(Status.equals("Have")){
                            Intent intent= new Intent(LoginPage.this,Main2Activity.class);
                            startActivity(intent);
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });*/
        pd.dismiss();
    }
}
