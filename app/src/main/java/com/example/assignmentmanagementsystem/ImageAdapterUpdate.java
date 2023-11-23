package com.example.assignmentmanagementsystem;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class ImageAdapterUpdate extends FirebaseRecyclerAdapter<UserFile, ImageAdapterUpdate.ImageViewHolder> {
    private OnItemClickListener mListener;

    public ImageAdapterUpdate(@NonNull FirebaseRecyclerOptions<UserFile> options) {
        super(options);
    }
    @Override
    protected void onBindViewHolder(@NonNull ImageViewHolder holder, int position, @NonNull UserFile model) {
        holder.textViewName.setText("Assignment Name: "+model.getFileName());
        holder.textViewCourse.setText("Course Name: "+model.getFileCourse());
        holder.textViewSatus.setText("Assignment Status: "+model.getFileStatus());


    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item_update, parent, false);
        return new ImageViewHolder(v);
    }


    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView textViewName;
        public TextView textViewCourse;
        public TextView textViewSatus;



        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName= itemView.findViewById(R.id.image_view_name1);
            textViewCourse= itemView.findViewById(R.id.image_view_course1);
            textViewSatus= itemView.findViewById(R.id.image_view_status1);
            itemView.setOnClickListener(this);



        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    mListener.onItemClick(position);
                }
            }
        }

    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener= listener;
    }



}
