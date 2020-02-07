package com.example.restclient1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private ArrayList<Comment> oCommentList;
    private CommentAdapter.OnItemClickListener oListener;
    private HashMap<Integer, String> oUserNameHashMap;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(CommentAdapter.OnItemClickListener listener){
        oListener = listener;

    }
    public static class CommentViewHolder extends RecyclerView.ViewHolder{

        public TextView textView1;
        public TextView textView2;
        public TextView textView3;


        public CommentViewHolder(@NonNull View itemView, final CommentAdapter.OnItemClickListener listener) {
            super(itemView);

            textView1 = itemView.findViewById(R.id.tv_cardview_commentTitle);
            textView2 = itemView.findViewById(R.id.tv_cardview_commentEmail);
            textView3 = itemView.findViewById(R.id.tv_cardview_commentText);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }

                }
            });
        }
    }

    // YOu can make modify your constructer to accept the commentArrayList and userArrayList/ userMap
    public CommentAdapter(ArrayList<Comment> commentArrayList){
        oCommentList = commentArrayList;
    }

    @NonNull
    @Override
    public CommentAdapter.CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_comments , parent, false);
        CommentAdapter.CommentViewHolder postViewHolder = new CommentAdapter.CommentViewHolder(v, oListener);

        return postViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.CommentViewHolder holder, int position) {

        Comment currentComment = oCommentList.get(position);
//        User user = userCommentMap.getByKey(currentComment.getUserId());
//        String userName = oUserNameHashMap.get(currentComment.getUserId());
//        holder.textView1.setText("Title: " + Integer.toString(currentComment.getId()));
        holder.textView1.setText("Title: " + currentComment.getName());

//        holder.textView2.setText("UserID: " + Integer.toString(currentComment.getUserId()));
        holder.textView2.setText("Email: " + currentComment.getEmail());
        holder.textView3.setText("Text: " + currentComment.getText());


    }

    @Override
    public int getItemCount() {
        return oCommentList.size();
    }
}
