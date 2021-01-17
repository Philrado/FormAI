package com.example.formai;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

    }

    public void goToSelectionActivity(View view) {
        Intent intent = new Intent(this, SelectionActivity.class);
        startActivity(intent);
    }
}