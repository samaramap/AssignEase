package com.example.assignmentmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateFile extends AppCompatActivity{
    private RecyclerView mRecyclerView;
    private ImageAdapterUpdate mAdapter;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private ProgressBar mProgressCircle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_file);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view1);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mProgressCircle = (ProgressBar) findViewById(R.id.progress_circle1);

        FirebaseRecyclerOptions<UserFile> options =
                new FirebaseRecyclerOptions.Builder<UserFile>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("Files").child(user.getUid()).child("Assignment"), UserFile.class)
                        .build();

        mAdapter = new ImageAdapterUpdate(options);
        mRecyclerView.setAdapter(mAdapter);
        mProgressCircle.setVisibility(View.INVISIBLE);

        mAdapter.setOnItemClickListener(new ImageAdapterUpdate.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                UserFile selectedItem= options.getSnapshots().get(position);
                String im_name = selectedItem.getFileName();
                String im_course= selectedItem.getFileCourse();
                String im_status= selectedItem.getFileStatus();
                String im_url= selectedItem.getFileUrl();

                String key= mAdapter.getRef(position).getKey();
                Intent intent = new Intent(UpdateFile.this, UpdateFileOpen.class);
                intent.putExtra("f_name", im_name);
                intent.putExtra("f_course", im_course);
                intent.putExtra("f_status", im_status);
                intent.putExtra("f_url", im_url);
                intent.putExtra("f_key", key);

                startActivity(intent);
                finish();
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
        startActivity(new Intent(UpdateFile.this, HomePage.class));
        finish();


    }
}