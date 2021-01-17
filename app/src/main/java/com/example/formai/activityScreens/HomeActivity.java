package com.example.formai.activityScreens;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.formai.R;

public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
    }

    public void goToSelectionActivity(View view) {
        Intent intent = new Intent(this, SelectionActivity.class);
        startActivity(intent);
    }
}
