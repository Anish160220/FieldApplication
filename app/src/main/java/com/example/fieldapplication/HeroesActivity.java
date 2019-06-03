package com.example.fieldapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import adapter.HeroesAdapter;
import heroesapi.HeroesAPI;
import model.Heroes;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import url.Url;

public class HeroesActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    List<Heroes> heroesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heroes);
        recyclerView = findViewById(R.id.recyclerView);

        HeroesAPI heroesAPI = Url.getInstance().create(HeroesAPI.class);

         Call<List<Heroes>> listCall = heroesAPI.getAllHeroes(Url.Cookie);
        listCall.enqueue(new Callback<List<Heroes>>() {
            @Override
            public void onResponse(Call<List<Heroes>> call, Response<List<Heroes>> response) {
                heroesList = response.body();
                HeroesAdapter heroesAdapter = new HeroesAdapter(HeroesActivity.this,heroesList);
                recyclerView.setAdapter(heroesAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(HeroesActivity.this));

            }

            @Override
            public void onFailure(Call<List<Heroes>> call, Throwable t) {
                Toast.makeText(HeroesActivity.this, "Error : "+t.getLocalizedMessage(), Toast.LENGTH_LONG).show();

            }


        });

            }
}
