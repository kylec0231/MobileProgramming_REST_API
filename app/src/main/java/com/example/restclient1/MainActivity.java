package com.example.restclient1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView textViewResult;
    private JsonPlaceHolderApi jsonPlaceHolderApi;

    private RecyclerView mRecyclerView;
    private PostAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private List<Post> postsList;
    private ArrayList<Post> postArrayList;
    private ArrayList<User> usersArrayList;
    private HashMap<Integer, String> userNameHashMap;
    private HashMap<Integer, String> realNameHashMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        textViewResult = findViewById(R.id.text_view_result);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);


        getUsers();
        getPosts();
        //getComments();
    }

    private void getPosts(){
        Call<List<Post>> call = jsonPlaceHolderApi.getPosts();

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {

                if(!response.isSuccessful()){
                    textViewResult.setText("Code: " + response.code());
                    return;
                }
                postArrayList = new ArrayList<>();
                List<Post> posts = response.body();
                for (Post post : posts){
//                    String content = "";
//                    content += "ID: " + post.getId() + "\n";
//                    content += "User ID: " + post.getUserId() + "\n";
//                    content += "Title: " + post.getTitle() + "\n";
//                    content += "Text: " +post.getText() + "\n\n";
//
//                    textViewResult.append(content);

                    postArrayList.add(new Post(post.getId(), post.getUserId(), post.getTitle(), post.getText()));

                }
                mRecyclerView = findViewById(R.id.recyclerview_posts);
                mRecyclerView.setHasFixedSize(true);
                mLayoutManager = new LinearLayoutManager(getApplicationContext());
                mAdapter = new PostAdapter(postArrayList, userNameHashMap);

                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.setOnItemClickListener(new PostAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        openActualPost(postArrayList.get(position));
//                        Context context = getApplicationContext();
//                        CharSequence text = "Hello toast!";
//                        int duration = Toast.LENGTH_SHORT;
//
//                        Toast toast = Toast.makeText(context, text, duration);
//                        toast.show();
                        //postArrayList.get(position);
                    }
                });
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {

                textViewResult.setText(t.getMessage());
            }
        });
    }




    private void getUsers(){
        Call<List<User>> call = jsonPlaceHolderApi.getUsers();

        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if(!response.isSuccessful()){
                    textViewResult.setText("Code: " + response.code());
                    return;
                }
                usersArrayList = new ArrayList<>();
                userNameHashMap = new HashMap<Integer, String>();
                realNameHashMap = new HashMap<Integer, String>();

                List<User> users = response.body();
                for (User user : users){
//                    String content = "";
//                    content += "ID: " + post.getId() + "\n";
//                    content += "User ID: " + post.getUserId() + "\n";
//                    content += "Title: " + post.getTitle() + "\n";
//                    content += "Text: " +post.getText() + "\n\n";
//
//                    textViewResult.append(content);

                    usersArrayList.add(new User(user.getId(), user.getName(), user.getUserName(), user.getEmail(), user.getAddress(), user.getPhone(), user.getWebsite(), user.getCompany()));
                    userNameHashMap.put(user.getId(), user.getUserName());
                    realNameHashMap.put(user.getId(), user.getName());
                }
//                mRecyclerView = findViewById(R.id.recyclerview_posts);
//                mRecyclerView.setHasFixedSize(true);
//                mLayoutManager = new LinearLayoutManager(getApplicationContext());
//                mAdapter = new PostAdapter(postArrayList);
//
//                mRecyclerView.setLayoutManager(mLayoutManager);
//                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                textViewResult.setText(t.getMessage());

            }
        });

    }
    public void openActualPost(Post currentPost){
       // Intent intent
        Intent intent = new Intent(this, OnePostAndComments.class);
        intent.putExtra("POSTPASSED", currentPost);
        intent.putExtra("REALNAMEHASHMAPPASSED", realNameHashMap);
        intent.putExtra("USERNAMEHASHMAPPASSED", userNameHashMap);
        intent.putExtra("USERINFOPASSED", usersArrayList);
        intent.putExtra("POSTINFOPASSED", postArrayList);
        startActivity(intent);
    }

}
