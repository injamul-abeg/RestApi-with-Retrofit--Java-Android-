package com.example.restapiwithretrofit;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private List<User> userList = new ArrayList<>();
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        userAdapter = new UserAdapter(userList);
        recyclerView.setAdapter(userAdapter);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/") // Replace with your API base URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);

        fetchUsers();
    }

    private void fetchUsers() {
        Call<List<User>> call = apiService.getUsers();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    userList.addAll(response.body());
                    userAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                // Handle API call failure
                Toast.makeText(MainActivity.this, "Failed to fetch users.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
