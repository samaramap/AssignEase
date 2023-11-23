package com.example.assignmentmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DeleteFile extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ImageAdapterDelete mAdapter;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private ProgressBar mProgressCircle;
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_file);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view2);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mProgressCircle = (ProgressBar) findViewById(R.id.progress_circle2);

        FirebaseRecyclerOptions<UserFile> options =
                new FirebaseRecyclerOptions.Builder<UserFile>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("Files").child(user.getUid()).child("Assignment"), UserFile.class)
                        .build();

        mAdapter = new ImageAdapterDelete(options);
        mRecyclerView.setAdapter(mAdapter);
        mProgressCircle.setVisibility(View.INVISIBLE);


        mAdapter.setOnItemClickListener(new ImageAdapterDelete.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                key= mAdapter.getRef(position).getKey();
                deleteAssignmentFunction();

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        mAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAdapter.stopListening();
    }

    public void deleteAssignmentFunction() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                DeleteFile.this);
        // Setting Dialog Title
        alertDialog.setTitle("Delete Assignment");
        // Setting Dialog Message
        alertDialog.setMessage("Are you sure you want to delete this Assignment?");
        // Setting Icon to Dialog
        alertDialog.setIcon(R.drawable.logo);
        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseReference ref= FirebaseDatabase.getInstance().getReference("Files").child(user.getUid()).child("Assignment").child(key);
                        ref.getRef().removeValue();
                        startActivity(new Intent(DeleteFile.this, DeleteFile.class));
                        Toast.makeText(DeleteFile.this, "Assignment is Deleted", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to invoke NO event
                        dialog.cancel();
                    }
                });
        // Showing Alert Message
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(DeleteFile.this, HomePage.class));
        finish();

    }
}