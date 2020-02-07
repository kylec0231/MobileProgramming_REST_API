package com.example.restclient1;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface JsonPlaceHolderApi {

    @GET("posts")
    Call<List<Post>> getPosts();

    @GET("users")
    Call<List<User>> getUsers();

    @GET("comments")
    Call<List<Comment>> getComments();

//    @GET("posts/{postId}")
//    Call<List<objectName>> getobjectName(@Path("postId") int postId);

    @POST("comments")
    Call<Comment> createComment(@Body Comment comment);
}
