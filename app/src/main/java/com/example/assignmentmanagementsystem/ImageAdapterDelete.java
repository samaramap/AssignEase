package com.example.assignmentmanagementsystem;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class ImageAdapterDelete extends FirebaseRecyclerAdapter<UserFile, ImageAdapterDelete.ImageViewHolder> {
    private OnItemClickListener mListener;

    public ImageAdapterDelete(@NonNull FirebaseRecyclerOptions<UserFile> options) {
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
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item_delete, parent, false);
        return new ImageViewHolder(v);
    }


    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView textViewName;
        public TextView textViewCourse;
        public TextView textViewSatus;
        public Button delete_ass;



        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName= itemView.findViewById(R.id.image_view_name2);
            textViewCourse= itemView.findViewById(R.id.image_view_course2);
            textViewSatus= itemView.findViewById(R.id.image_view_status2);
            delete_ass= itemView.findViewById(R.id.delete_ass);

            delete_ass.setOnClickListener(this);
            //itemView.setOnClickListener(this);



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
