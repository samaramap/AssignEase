package com.example.assignmentmanagementsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
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

public class Login extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private EditText p_email, p_password;
    private TextView sign;
    private Button login;
    private FirebaseUser user;
    private ProgressDialog progressDialog;
    NetworkInfo nInfo;

    public static final String SHARED_PREFS= "sharedPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        p_email= (EditText) findViewById(R.id.p_email);
        p_password= (EditText) findViewById(R.id.p_password);
        sign= (TextView) findViewById(R.id.sign);
        login= (Button) findViewById(R.id.login);

        firebaseAuth= FirebaseAuth.getInstance();
        progressDialog= new ProgressDialog(this);
        user= firebaseAuth.getCurrentUser();

        ConnectivityManager cManager = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        nInfo = cManager.getActiveNetworkInfo();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (valid()) {
                    if(nInfo!=null && nInfo.isConnected()) {
                        progressDialog.setMessage("Wait for the authentication approval");
                        progressDialog.show();
                        String ema_user = p_email.getText().toString().trim();
                        String pas_user = p_password.getText().toString().trim();
                        firebaseAuth.signInWithEmailAndPassword(ema_user, pas_user).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    progressDialog.dismiss();

                                    //to keep user logged in
                                    SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                                    SharedPreferences.Editor editor= sharedPreferences.edit();

                                    editor.putString("name", "true");
                                    editor.apply();
                                    //end

                                    Toast.makeText(Login.this, "Successful Login", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(Login.this, HomePage.class));
                                    finish();
                                } else {
                                    Toast.makeText(Login.this, "Login Failed", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }
                            }
                        });
                    } else {
                        Toast.makeText(Login.this, "Network is not available", Toast.LENGTH_LONG).show();}

                }
            }
        });

        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Register.class));
                finish();
            }
        });

    }

    private Boolean valid(){
        boolean result= false;
        String password= p_email.getText().toString();
        String email= p_password.getText().toString();

        if(password.isEmpty() || email.isEmpty()){
            Toast.makeText(this, "Fill both options", Toast.LENGTH_SHORT).show();
        }else {
            result= true;
        }
        return result;
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}