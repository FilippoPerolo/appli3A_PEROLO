package com.example.appli3a_perolo;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SomeActivity extends AppCompatActivity {

    private static final String BASE_URL = "https://financialmodelingprep.com/";

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private SharedPreferences sharedPreferences;
    private Gson gson;

    private static final String TAG = "someActivity";

    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //Log.d(TAG, "onCreate : called");

        Symbol symbol2 = getDataFromCache();
        showList(symbol2);
        makeApiCall();
    }

    private Symbol getDataFromCache() {
        String jsonSymbols = sharedPreferences.getString(Constants.KEY_SYMBOLS_LIST, null);

        if (jsonSymbols == null){
            return null;
        } else{
            Type listType = new TypeToken<List<Symbol>>(){}.getType();
            return gson.fromJson(jsonSymbols, listType);
        }
    }

    private void showList(Symbol symbol2) {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // define an adapter
        mAdapter = new ListAdapter(symbol2, this);
        recyclerView.setAdapter(mAdapter);
    }

    private void saveList(Symbol symbol2) {
        String jsonString = gson.toJson(symbol2);

        sharedPreferences
                .edit()
                .putString(Constants.KEY_SYMBOLS_LIST, jsonString)
                .apply();

        Toast.makeText(getApplicationContext(), "List Saved", Toast.LENGTH_SHORT).show();

    }


    private void makeApiCall(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        final DetailsAPI detailsAPI = retrofit.create(DetailsAPI.class);

        Call<RestDetailsResponse> call = detailsAPI.getDetailsResponse();
        call.enqueue(new Callback<RestDetailsResponse>() {
            @Override
            public void onResponse(Call<RestDetailsResponse> call, Response<RestDetailsResponse> response) {
                if (response.isSuccessful() && response.body()!=null) {
                    Symbol symbol2 = response.body().getSymbol2();
                    Toast.makeText(getApplicationContext(), "Api Success", Toast.LENGTH_SHORT).show();
                    saveList(symbol2);
                    showList(symbol2);
                } else {
                    showError();
                }
            }

            @Override
            public void onFailure(Call<RestDetailsResponse> call, Throwable t) {
                showError();
            }
        });

    }
    private void showError() {
        Toast.makeText(this, "Api Error", Toast.LENGTH_SHORT).show();
    }

}
