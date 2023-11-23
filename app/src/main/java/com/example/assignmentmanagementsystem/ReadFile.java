package com.example.assignmentmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class ReadFile extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ImageAdapterRead mAdapter;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private ProgressBar mProgressCircle;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_file);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view3);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mProgressCircle = (ProgressBar) findViewById(R.id.progress_circle3);

        FirebaseRecyclerOptions<UserFile> options =
                new FirebaseRecyclerOptions.Builder<UserFile>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("Files").child(user.getUid()).child("Assignment"), UserFile.class)
                        .build();

        mAdapter = new ImageAdapterRead(options);
        mRecyclerView.setAdapter(mAdapter);
        mProgressCircle.setVisibility(View.INVISIBLE);

        mAdapter.setOnItemClickListener(new ImageAdapterRead.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                UserFile selectedItem= options.getSnapshots().get(position);
               Intent intent= new Intent(Intent.ACTION_VIEW);
               intent.setType("application/*");
               intent.setData(Uri.parse(selectedItem.getFileUrl()));
               startActivity(intent);

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


    @Override
    public void onBackPressed() {
        startActivity(new Intent(ReadFile.this, HomePage.class));
        finish();


    }
}