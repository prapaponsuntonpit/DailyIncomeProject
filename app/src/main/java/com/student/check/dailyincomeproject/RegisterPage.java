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

public class RegisterPage extends AppCompatActivity {
    DatabaseReference mData;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth mAuth;
    FirebaseUser user;
    EditText name_full;
    EditText email_re;
    EditText phone_re;
    EditText password_re;
    EditText repass_re;
    Button btn_can;
    Button btn_submit;
    SpotsDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        mAuth = FirebaseAuth.getInstance();


        firebaseDatabase = FirebaseDatabase.getInstance();
        mData = firebaseDatabase.getReference("User");

        pd = new SpotsDialog(this,R.style.Custom);

        name_full = (EditText) findViewById(R.id.full_name_regis);
        email_re = (EditText) findViewById(R.id.email_regis);
        phone_re = (EditText) findViewById(R.id.phone_regis);
        password_re = (EditText) findViewById(R.id.password_regis);
        repass_re = (EditText) findViewById(R.id.re_password_regis);
        btn_can = (Button) findViewById(R.id.cancel_btn);
        btn_submit = (Button) findViewById(R.id.submit_btn);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd.show();
                final String name = name_full.getText().toString();
                final String email = email_re.getText().toString();
                final String phone = phone_re.getText().toString();
                final String pass = password_re.getText().toString();
                String repass = repass_re.getText().toString();
                if(!pass.equals(repass)){
                    Toast.makeText(RegisterPage.this,"Password don't match",Toast.LENGTH_LONG).show();
                }
                else{
                    mAuth.createUserWithEmailAndPassword(email,pass)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    user = mAuth.getCurrentUser();
                                  if(task.isSuccessful()){
                                       mData.child(user.getUid()).child("name").setValue(name);
                                       mData.child(user.getUid()).child("email").setValue(email);
                                       mData.child(user.getUid()).child("phone").setValue(phone);
                                      // mData.child(user.getUid()).child("status").setValue("None");
                                       Intent intent= new Intent(RegisterPage.this,create_name_account.class);
                                       startActivity(intent);
                                       finish();
                                   }
                                }
                            });
                    pd.dismiss();
                }
            }
        });

        btn_can.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterPage.this,LoginPage.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
