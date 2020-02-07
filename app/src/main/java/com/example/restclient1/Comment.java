package com.example.restclient1;

import com.google.gson.annotations.SerializedName;

public class Comment {

    public Comment(int postId, int id, String name, String email, String text) {
        this.postId = postId;
        this.id = id;
        this.name = name;
        this.email = email;
        this.text = text;
    }

    public Comment(int postId, String name, String email, String text) {
        this.postId = postId;
        this.name = name;
        this.email = email;
        this.text = text;
    }

    private int postId;
    private int id;
    private String name;
    private String email;

    @SerializedName("body")
    private String text;

    public int getPostId() {
        return postId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getText() {
        return text;
    }
}
