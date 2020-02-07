package com.example.restclient1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OnePostAndComments extends AppCompatActivity {

    private RecyclerView oRecyclerView;
    private CommentAdapter oAdapter;
    private RecyclerView.LayoutManager oLayoutManager;
    private TextView oneTextViewResult;
    private JsonPlaceHolderApi ojsonPlaceHolderApi;


    private TextView textViewTitle;
    private TextView textViewName;
    private TextView textViewText;
    private FloatingActionButton floatingActionButton;
    private String userName;
    private Post currentPost;
    private HashMap<Integer, String> oUserNameHashMap;
    private HashMap<Integer, String> oRealNameHashMap;
    private ArrayList<Comment> commentArrayList;
    private ArrayList<Comment> associatedCommentsArrayList;
    private ArrayList<User> userArrayList;
    private ArrayList<Post> postArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_post_and_comments);
        Post post = getIntent().getParcelableExtra("POSTPASSED");
        oUserNameHashMap = (HashMap<Integer, String>) getIntent().getSerializableExtra("USERNAMEHASHMAPPASSED");
        oRealNameHashMap = (HashMap<Integer, String>) getIntent().getSerializableExtra("REALNAMEHASHMAPPASSED");
        userArrayList = getIntent().getParcelableArrayListExtra("USERINFOPASSED");
        postArrayList = getIntent().getParcelableArrayListExtra("POSTINFOPASSED");

        Log.d("entered post activity", post.getText());
        Log.d("mRealNameHashMap", oRealNameHashMap.get(1));
        Log.d("OneP, userArrayList", userArrayList.get(3).getName());
        Log.d("OneP, postArrayList", Integer.toString(postArrayList.get(3).getId()));


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ojsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        oneTextViewResult = findViewById(R.id.one_text_view_result);

        textViewTitle = findViewById(R.id.tv_one_post_title);
        textViewName = findViewById(R.id.tv_one_post_name);
        textViewText = findViewById(R.id.tv_one_post_text);
        floatingActionButton = findViewById(R.id.fab);

        userName = oUserNameHashMap.get(post.getUserId());
        textViewTitle.setText(post.getTitle());
        textViewName.setText(userName);
        textViewText.setText(post.getText());

        textViewName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("textViewName", "is clicked");

                Intent intent = new Intent(v.getContext(), UserProfile.class);
                intent.putExtra("USERINFOPASSEDFROMONE", userArrayList);
                intent.putExtra("POSTINFOPASSEDFROMONE", postArrayList);
                intent.putExtra("USERNAMEPASSEDFROMONE", userName);


                startActivity(intent);

            }
        });

        currentPost = post;
        associatedCommentsArrayList = new ArrayList<>();


        getComments();
//        createComment();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createComment();
            }
        });
//
    }


    private void getComments(){

        Call<List<Comment>> call = ojsonPlaceHolderApi.getComments();
        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {

                if(!response.isSuccessful()){
                    oneTextViewResult.setText("Code: " + response.code());
                    return;
                }
                commentArrayList = new ArrayList<>();
                List<Comment> comments = response.body();
                for (Comment comment : comments){


                    commentArrayList.add(new Comment(comment.getPostId(), comment.getId(), comment.getName(), comment.getEmail(), comment.getText()));
                    if(currentPost.getId() == comment.getPostId())
                        associatedCommentsArrayList.add(new Comment(comment.getPostId(), comment.getId(), comment.getName(), comment.getEmail(), comment.getText()));
                }

                oRecyclerView = findViewById(R.id.recyclerview_comments);
                oRecyclerView.setHasFixedSize(true);
                oLayoutManager = new LinearLayoutManager(getApplicationContext());
                oAdapter = new CommentAdapter(associatedCommentsArrayList);

                oRecyclerView.setLayoutManager(oLayoutManager);
                oRecyclerView.setAdapter(oAdapter);
//                oAdapter.setOnItemClickListener(new PostAdapter.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(int position) {
//                        openActualPost(postArrayList.get(position));
////                        Context context = getApplicationContext();
////                        CharSequence text = "Hello toast!";
////                        int duration = Toast.LENGTH_SHORT;
////
////                        Toast toast = Toast.makeText(context, text, duration);
////                        toast.show();
//                        //postArrayList.get(position);
//                    }
//                });

            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                oneTextViewResult.setText(t.getMessage());
            }
        });
    }

    private void createComment(){
        Comment comment = new Comment(currentPost.getId(), "new title", "example@email.com", "new Body Text");
        Call<Comment> call = ojsonPlaceHolderApi.createComment(comment);
        call.enqueue(new Callback<Comment>() {
            @Override
            public void onResponse(Call<Comment> call, Response<Comment> response) {
                if(!response.isSuccessful()){
                    oneTextViewResult.setText("code: " + response.code());
                    return;
                }
                Comment comment = response.body();

                    commentArrayList.add(new Comment(comment.getPostId(), comment.getId(), comment.getName(), comment.getEmail(), comment.getText()));
                    if(currentPost.getId() == comment.getPostId())
                        associatedCommentsArrayList.add(new Comment(comment.getPostId(), comment.getId(), comment.getName(), comment.getEmail(), comment.getText()));


                oRecyclerView = findViewById(R.id.recyclerview_comments);
                oRecyclerView.setHasFixedSize(true);
                oLayoutManager = new LinearLayoutManager(getApplicationContext());
                oAdapter = new CommentAdapter(associatedCommentsArrayList);

                oRecyclerView.setLayoutManager(oLayoutManager);
                oRecyclerView.setAdapter(oAdapter);
            }

            @Override
            public void onFailure(Call<Comment> call, Throwable t) {

            }
        });

    }
}
