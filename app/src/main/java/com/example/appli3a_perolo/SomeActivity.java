package com.example.appli3a_perolo;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SomeActivity extends AppCompatActivity {

    private static final String TAG = "someActivity";

    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate : called");
    }
}
