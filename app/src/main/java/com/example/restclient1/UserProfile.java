package com.example.restclient1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

public class UserProfile extends AppCompatActivity {

    private RecyclerView uRecyclerView;
    private PostByUserAdapter uAdapter;
    private RecyclerView.LayoutManager uLayoutManager;

    private String currentUserName;
    private int currentUserId;

    private TextView textViewId;
    private TextView textViewName;
    private TextView textViewUsername;
    private TextView textViewEmail;
    private TextView textViewPhone;
    private TextView textViewWebsite;
    private TextView textViewCompany;
    private TextView textViewAddress;

    private String currentId;
    private String currentName;
    private String currentUsername;
    private String currentEmail;
    private String currentPhone;
    private String currentWebsite;
    private String currentCompany;
    private String currentAddress;

    ArrayList<User> userArrayList;
    ArrayList<Post> postArrayList;
    ArrayList<Post> postsByUserArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        Log.d("UserProfile", "entered");
        //userArrayList = new ArrayList<>();
        //postArrayList = new ArrayList<>();
        postsByUserArrayList = new ArrayList<>();
        userArrayList = getIntent().getParcelableArrayListExtra("USERINFOPASSEDFROMONE");
        postArrayList = getIntent().getParcelableArrayListExtra("POSTINFOPASSEDFROMONE");
        currentUserName = getIntent().getStringExtra("USERNAMEPASSEDFROMONE");
        Log.d("UserProfile", userArrayList.get(1).getName());
        Log.d("UserProfile", Integer.toString(postArrayList.get(1).getId()));
        Log.d("UserProfile", postArrayList.get(1).getText());
        Log.d("UserProfile", Integer.toString(userArrayList.size()));
        Log.d("UserProfile", currentUserName);

        currentId = "failed to load";
        currentName = "failed to load";
        currentUsername = "failed to load";
        currentEmail = "failed to load";
        currentPhone = "failed to load";
        currentWebsite = "failed to load";
        currentCompany = "failed to load";
        currentAddress = "failed to load";

//        userArrayList.contains()
        currentUserName = currentUserName.trim();
        for (User user : userArrayList) {

            String userName = user.getUserName().trim();
            Log.d("UserProfile", userName.trim());

            if(userName.contains(currentUserName)){
                Log.d("UserProfile", "same user found!!!!");
                currentId = Integer.toString(user.getId());
                currentName = user.getName();
                currentUsername = user.getUserName();
                currentEmail = user.getEmail();
                currentPhone = user.getPhone();
                currentWebsite = user.getWebsite();
                currentCompany = user.getCompany().getFullCompany();
                currentAddress = user.getAddress().getFullAddress();
            }
            else{
                Log.d("UserProfile", "user not found");

            }


//            if (user instanceof Student) {
//
//                // call getGrade();
//
//            } else { // it is an instance of a Teacher
//
//                // call getSubject();
//            }
        }

        textViewId = findViewById(R.id.tv_user_profile_id);
        textViewName = findViewById(R.id.tv_user_profile_name);
        textViewUsername = findViewById(R.id.tv_user_profile_username);
        textViewEmail = findViewById(R.id.tv_user_profile_email);
        textViewPhone = findViewById(R.id.tv_user_profile_phone);
        textViewWebsite = findViewById(R.id.tv_user_profile_website);
        textViewCompany = findViewById(R.id.tv_user_profile_company);
        textViewAddress = findViewById(R.id.tv_user_profile_address);

        textViewId.setText(currentId);
        textViewName.setText(currentName);
        textViewUsername.setText(currentUsername);
        textViewEmail.setText(currentEmail);
        textViewPhone.setText(currentPhone);
        textViewWebsite.setText(currentWebsite);
        textViewCompany.setText(currentCompany);
        textViewAddress.setText(currentAddress);


        currentUserId = Integer.valueOf(currentId);

        for (Post post : postArrayList) {

            int postUserid = post.getUserId();
            Log.d("UserProfile", Integer.toString(postUserid));



            if(currentUserId == postUserid){
                Log.d("UserProfile", "parse current post user id is success!!!!");
                postsByUserArrayList.add(post);
            }
            else{
                Log.d("UserProfile", "user not found");

            }


        }

        uRecyclerView = findViewById(R.id.recyclerview_postsByUser);
        uRecyclerView.setHasFixedSize(true);
        uLayoutManager = new LinearLayoutManager(getApplicationContext());
        uAdapter = new PostByUserAdapter(postsByUserArrayList);

        uRecyclerView.setLayoutManager(uLayoutManager);
        uRecyclerView.setAdapter(uAdapter);
    }
}
