package com.example.assignmentmanagementsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateFileOpen extends AppCompatActivity {
    EditText p_name, p_course, p_status;
    ImageView image;
    Button update_Assignment;
    String u_s_name, u_s_course, u_s_status;
    String get_name, get_course, get_status, key, get_url;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    String key_get;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_file_open);

        getIncomingIntent();
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        update_Assignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateServiceFunction();
            }
        });

    }

    private void UpdateServiceFunction() {
        if (validate()) {
            //update values
            final DatabaseReference ref1 =FirebaseDatabase.getInstance().getReference("Files").child(user.getUid()).child("Assignment").child(key);
            UserFile userFile= new UserFile(u_s_name, u_s_course, u_s_status, get_url);
            ref1.setValue(userFile);
            startActivity(new Intent(UpdateFileOpen.this, HomePage.class));
            Toast.makeText(UpdateFileOpen.this, "Assignment Details are Updated", Toast.LENGTH_SHORT).show();
            finish();

        }
    }

    private Boolean validate(){
        boolean result= false;

        u_s_name = p_name.getText().toString();
        u_s_course = p_course.getText().toString();
        u_s_status= p_status.getText().toString();
        if(u_s_name.isEmpty() || u_s_course.isEmpty() || u_s_status.isEmpty()){
            Toast.makeText(this, "Fill every required information", Toast.LENGTH_SHORT).show();
        }else {
            result= true;
        }
        return result;
    }
    private void getIncomingIntent() {

        if (getIntent().hasExtra("f_name") && getIntent().hasExtra("f_course")
                && getIntent().hasExtra("f_status") && getIntent().hasExtra("f_key")
        && getIntent().hasExtra("f_url")) {

            get_name = getIntent().getStringExtra("f_name");
            get_course = getIntent().getStringExtra("f_course");
            get_status = getIntent().getStringExtra("f_status");
            get_url= getIntent().getStringExtra("f_url");
            key= getIntent().getStringExtra("f_key");

            setImage(get_name, get_course, get_status);
            //finish();
        }
    }

    private void setImage(String imageName, String imagePrice, String imageTime) {

        p_name = findViewById(R.id.pic_name);
        p_name.setText(imageName);

        p_course = findViewById(R.id.pic_course);
        p_course.setText(imagePrice);

        p_status = findViewById(R.id.pic_status);
        p_status.setText(imageTime);


        update_Assignment= findViewById(R.id.update_ass);
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(UpdateFileOpen.this, HomePage.class));
        finish();}
}