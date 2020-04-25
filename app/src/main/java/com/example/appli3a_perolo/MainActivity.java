package com.example.appli3a_perolo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private SharedPreferences sharedPreferences;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("Esiea_3A", Context.MODE_PRIVATE);
        gson = new GsonBuilder()
                .setLenient()
                .create();

        List<Symbol> symbolsList = getDataFromCache();
        if (symbolsList != null) {
            showList(symbolsList);
        } else {
            makeApiCall();
        }
    }

    private List<Symbol> getDataFromCache() {
        String jsonSymbols = sharedPreferences.getString(Constants.KEY_SYMBOLS_LIST, null);

        if (jsonSymbols == null) {
            return null;
        } else {
            Type listType = new TypeToken<List<Symbol>>() {
            }.getType();
            return gson.fromJson(jsonSymbols, listType);
        }
    }

    private static final String BASE_URL = "https://financialmodelingprep.com/";

    private void showList(List<Symbol> symbolsList) {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // define an adapter
        mAdapter = new ListAdapter(symbolsList, new ListAdapter.OnItemClickListener() {
            public void onItemClick(Symbol symbolsList) {
                Intent intent = new Intent(getApplicationContext(), Activity2.class);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(mAdapter);
    }

    private void makeApiCall() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        SymbolAPI symbolAPI = retrofit.create(SymbolAPI.class);

        Call<RestFinanceResponse> call = symbolAPI.getSymbolResponse();
        call.enqueue(new Callback<RestFinanceResponse>() {
            @Override
            public void onResponse(Call<RestFinanceResponse> call, Response<RestFinanceResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Symbol> symbolsList = response.body().getSymbolsList();
                    Toast.makeText(getApplicationContext(), "Api Success", Toast.LENGTH_SHORT).show();
                    saveList(symbolsList);
                    showList(symbolsList);
                } else {
                    showError();
                }
            }

            @Override
            public void onFailure(Call<RestFinanceResponse> call, Throwable t) {
                showError();
            }
        });

    }

    private void saveList(List<Symbol> symbolsList) {
        String jsonString = gson.toJson(symbolsList);

        sharedPreferences
                .edit()
                .putString(Constants.KEY_SYMBOLS_LIST, jsonString)
                .apply();

        Toast.makeText(getApplicationContext(), "List Saved", Toast.LENGTH_SHORT).show();

    }

    private void showError() {
        Toast.makeText(this, "Api Error", Toast.LENGTH_SHORT).show();
    }

}
