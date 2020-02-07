package com.example.restclient1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private ArrayList<Post> mPostList;
    private OnItemClickListener mListener;
    private HashMap<Integer, String> mUserNameHashMap;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;

    }
    public static class PostViewHolder extends RecyclerView.ViewHolder{

        public TextView textView1;
        public TextView textView2;
        public TextView textView3;
        public TextView textView4;


        public PostViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            textView1 = itemView.findViewById(R.id.tv_cardview_postId);
            textView2 = itemView.findViewById(R.id.tv_cardview_userId);
            textView3 = itemView.findViewById(R.id.tv_cardview_postTitle);
            textView4 = itemView.findViewById(R.id.tv_cardview_postText);

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

    // YOu can make modify your constructer to accept the postArrayList and userArrayList/ userMap
    public PostAdapter(ArrayList<Post> postArrayList, HashMap<Integer, String> userNameHashMap){
        mPostList = postArrayList;
        mUserNameHashMap = userNameHashMap;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview , parent, false);
        PostViewHolder postViewHolder = new PostViewHolder(v, mListener);

        return postViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {

        Post currentPost = mPostList.get(position);
//        User user = userPostMap.getByKey(currentPost.getUserId());
        String userName = mUserNameHashMap.get(currentPost.getUserId());
        holder.textView1.setText("Id: " + Integer.toString(currentPost.getId()));
//        holder.textView2.setText("UserID: " + Integer.toString(currentPost.getUserId()));
        holder.textView2.setText("User name: " + userName);
        holder.textView3.setText("Title: " + currentPost.getTitle());
        holder.textView4.setText("Text: " + currentPost.getText());


    }

    @Override
    public int getItemCount() {
        return mPostList.size();
    }
}
