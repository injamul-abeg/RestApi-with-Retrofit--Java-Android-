package com.example.restapiwithretrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("users") // Replace with your API endpoint
    Call<List<User>> getUsers();
}