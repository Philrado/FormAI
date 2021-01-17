package com.example.formai;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class FormCheckActivity extends AppCompatActivity {

    private boolean correct = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        Log.d("myTag", extras.getString("source"));
        setContentView(R.layout.form_check_activity);

        ImageView backButton = findViewById(R.id.backButton);
        Button correctButton = findViewById(R.id.correctButton);
        TextView workOutTypeText = findViewById(R.id.workoutType);

        workOutTypeText.setText(extras.getString("source"));
        correctButton.setBackgroundColor(getResources().getColor(R.color.green));

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        correctButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                correct = !correct;
                if (correct) {
                    correctButton.setBackgroundColor(getResources().getColor(R.color.green));
                    correctButton.setText(R.string.correct);
                } else {
                    correctButton.setBackgroundColor(getResources().getColor(R.color.red));
                    correctButton.setText(R.string.incorrect);
                    correctButton.setTextColor(getResources().getColor(R.color.white));
                }
            }
        });
    }


}