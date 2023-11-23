package com.example.assignmentmanagementsystem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AddFile extends AppCompatActivity {

    private Button upload, attach;
    private EditText course, name, status;
    private DatabaseReference databaseReference;
    private String u_course, u_name, u_status;
    private FirebaseAuth firebaseAuth;

    StorageReference storageReference;

    private int Request_Code= 234;
    private Uri filepath;
    TextView txtFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_file);

        upload=(Button) findViewById(R.id.upload);
        attach= (Button) findViewById(R.id.attach);

        status= (EditText) findViewById(R.id.status);
        name= (EditText) findViewById(R.id.name);
        course= (EditText) findViewById(R.id.course);

        txtFile= (TextView) findViewById(R.id.txtFile);

        firebaseAuth= FirebaseAuth.getInstance();
        storageReference= FirebaseStorage.getInstance().getReference();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        databaseReference= FirebaseDatabase.getInstance().getReference("Files").child(user.getUid()).child("Assignment");



        attach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectAssignment();
            }
        });

    }
    private Boolean validate(){
        boolean result= false;

        u_status= status.getText().toString();
        u_course= course.getText().toString();
        u_name= name.getText().toString();
        if(u_status.isEmpty() || u_course.isEmpty() || u_name.isEmpty()){
            Toast.makeText(AddFile.this, "First add all details of Assignment", Toast.LENGTH_SHORT).show();
        }else {
            result= true;
        }
        return result;
    }

    private void selectAssignment(){
        Intent intent= new Intent();
        intent.setType("application/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Assignment Selected"),12);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode== 12 && resultCode== RESULT_OK && data != null && data.getData() != null){
            filepath= data.getData();
            txtFile.setText(data.getDataString().
                    substring(data.getDataString().lastIndexOf("/")+ 1));

            upload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (validate()){
                        upload(data.getData());
                    }

                    else{
                        Toast.makeText(AddFile.this, "Select a file", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    private void upload(Uri data) {

        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Uploading...");
        progressDialog.show();

        StorageReference reference= storageReference.child("uploads/"+System.currentTimeMillis()+ ".pdf");

        reference.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uri =taskSnapshot.getStorage().getDownloadUrl();
                        while(!uri.isComplete());
                        Uri url= uri.getResult();

                        UserFile userFile= new UserFile(u_name, u_course, u_status,url.toString());
                        databaseReference.child(databaseReference.push().getKey()).setValue(userFile);

                        progressDialog.dismiss();
                        Toast.makeText(AddFile.this, "Assignment Added Successfully", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(AddFile.this, HomePage.class));
                        finish();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        double progress= (100.0 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                        progressDialog.setMessage("uploaded: " +(int)progress+" %");
                    }
                });

    }


    @Override
    public void onBackPressed() {
        //Display alert message when back button has been pressed
        startActivity(new Intent(AddFile.this, HomePage.class));
        finish();
    }
}