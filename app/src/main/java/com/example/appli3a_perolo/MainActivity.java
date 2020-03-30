package com.example.appli3a_perolo;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //showList();
        makeApiCall();
    }

    private static final String BASE_URL = "https://financialmodelingprep.com/";

    private void showList(List<Symbol> symbolList) {
            recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            recyclerView.setHasFixedSize(true);
            // use a linear layout manager
            layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
          /*  List<String> input = new ArrayList<>();
            for (int i = 0; i < 100; i++) {
                input.add("Test" + i);
            } */
            // define an adapter
            mAdapter = new ListAdapter(symbolList);
            recyclerView.setAdapter(mAdapter);
        }

    private void makeApiCall(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        SymbolAPI symbolAPI = retrofit.create(SymbolAPI.class);

        Call<APiFinanceResponse> call = symbolAPI.getSymbolResponse();
        call.enqueue(new Callback<APiFinanceResponse>() {
            @Override
            public void onResponse(Call<APiFinanceResponse> call, Response<APiFinanceResponse> response) {
                if (response.isSuccessful() && response.body()!=null) {
                    List<Symbol> symbolsList = response.body().getSymbolsList();
                    Toast.makeText(getApplicationContext(), "Api Success", Toast.LENGTH_SHORT).show();
                    showList(symbolsList);
                } else {
                    showError();
                }
            }

            @Override
            public void onFailure(Call<APiFinanceResponse> call, Throwable t) {
                showError();
            }
        });

    }

    private void showError() {
        Toast.makeText(this, "Api Error", Toast.LENGTH_SHORT).show();
    }
}
