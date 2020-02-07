package com.example.restclient1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

public class PostByUserAdapter extends RecyclerView.Adapter<PostByUserAdapter.PostByUserViewHolder> {

    private ArrayList<Post> pPostList;
    private PostByUserAdapter.OnItemClickListener oListener;
    private HashMap<Integer, String> oUserNameHashMap;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(PostByUserAdapter.OnItemClickListener listener){
        oListener = listener;

    }
    public static class PostByUserViewHolder extends RecyclerView.ViewHolder{

        public TextView textView1;
        public TextView textView2;


        public PostByUserViewHolder(@NonNull View itemView, final PostByUserAdapter.OnItemClickListener listener) {
            super(itemView);

            textView1 = itemView.findViewById(R.id.tv_cardview_postByUserTitle);
            textView2 = itemView.findViewById(R.id.tv_cardview_postByUserText);

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
    public PostByUserAdapter(ArrayList<Post> postArrayList){
        pPostList = postArrayList;
    }

    @NonNull
    @Override
    public PostByUserAdapter.PostByUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_post_by_user , parent, false);
        PostByUserAdapter.PostByUserViewHolder postViewHolder = new PostByUserAdapter.PostByUserViewHolder(v, oListener);

        return postViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PostByUserAdapter.PostByUserViewHolder holder, int position) {

        Post currentPost = pPostList.get(position);
//        User user = userCommentMap.getByKey(currentComment.getUserId());
//        String userName = oUserNameHashMap.get(currentComment.getUserId());
//        holder.textView1.setText("Title: " + Integer.toString(currentComment.getId()));
        holder.textView1.setText("Title: " + currentPost.getTitle());
        holder.textView2.setText("Email: " + currentPost.getText());


    }

    @Override
    public int getItemCount() {
        return pPostList.size();
    }
}
