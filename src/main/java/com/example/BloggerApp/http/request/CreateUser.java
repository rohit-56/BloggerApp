package com.example.BloggerApp.http.request;

import lombok.Getter;

@Getter
public class CreateUser {

    public String username;

    public String email;

    public String password;

    public String image;

    public String bio;
}
