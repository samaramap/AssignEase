package com.example.assignmentmanagementsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    NetworkInfo nInfo;
    private EditText email, password, cPassword;
    private Button signup;
    String user_email, user_password, user_cPassword;
    private TextView log;
    public static final String SHARED_PREFS= "sharedPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth= FirebaseAuth.getInstance();
        ConnectivityManager cManager = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);

        nInfo = cManager.getActiveNetworkInfo();


        email= (EditText) findViewById(R.id.email);
        password= (EditText) findViewById(R.id.password);
        cPassword= (EditText) findViewById(R.id.cPassword) ;
        signup= (Button) findViewById(R.id.signup);
        log= (TextView) findViewById(R.id.log);

        checkBox();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    if (paswordlength()) {
                        if(paswordmatch()){
                            if (nInfo != null && nInfo.isConnected()) {

                                String email_user = email.getText().toString().trim();
                                String pass_user = password.getText().toString().trim();

                                firebaseAuth.createUserWithEmailAndPassword(email_user, pass_user).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(Register.this, "Successful Registration", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(Register.this, Login.class));
                                            finish();

                                        } else {
                                            Toast.makeText(Register.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            } else {
                                Toast.makeText(Register.this, "Network is not available", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }
            }
        });

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register.this, Login.class));
                finish();
            }
        });
    }

    private void checkBox() {
        SharedPreferences sharedPreferences= getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String check= sharedPreferences.getString("name", "");

        if (check.equals("true")){
            startActivity(new Intent(Register.this, HomePage.class));
            finish();

        }
    }

    private Boolean validate(){
        boolean result= false;

        user_email = email.getText().toString();
        user_password = password.getText().toString();
        user_cPassword= cPassword.getText().toString();
        if(user_password.isEmpty() || user_email.isEmpty() || user_cPassword.isEmpty()){
            Toast.makeText(this, "Fill every required information", Toast.LENGTH_SHORT).show();
        }else {
            result= true;
        }
        return result;
    }

    private Boolean paswordlength(){
        boolean result1= false;
        if (user_password.length() < 6 ){
            Toast.makeText(this, "Password should be of at-least 6 characters", Toast.LENGTH_SHORT).show();
        }else{
            result1= true;
        }
        return result1;
    }

    private Boolean paswordmatch(){
        boolean result2= false;
        if (!user_password.equals(user_cPassword)){
            Toast.makeText(this, "Password does not match", Toast.LENGTH_SHORT).show();
        }else{
            result2= true;
        }
        return result2;
    }

    @Override
    public void onBackPressed() {
        //Display alert message when back button has been pressed
        finish();
    }
}