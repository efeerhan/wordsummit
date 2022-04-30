package com.crunglers.wordsummit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Locale;

public class SynonymGameActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_synonym_game);

        LinearLayout row1 = findViewById(R.id.keyboardRow1);
        LinearLayout row2 = findViewById(R.id.keyboardRow2);
        LinearLayout row3 = findViewById(R.id.keyboardRow3);

        TextView ballsBox = findViewById(R.id.inputView);

        for ( int i = 0; i < 10; i++ ) {
            AppCompatButton key = (AppCompatButton)row1.getChildAt(i);
            key.setOnClickListener( v-> {
                CharSequence tmp = ballsBox.getText();
                String ballsHolder = String.valueOf(tmp);
                ballsBox.setText(ballsHolder + key.getText().toString().toLowerCase(Locale.ROOT));
            });
        }

        for ( int i = 0; i < 9; i++ ) {
            AppCompatButton key = (AppCompatButton)row2.getChildAt(i);
            key.setOnClickListener( v-> {
                CharSequence tmp = ballsBox.getText();
                String ballsHolder = String.valueOf(tmp);
                ballsBox.setText(ballsHolder + key.getText().toString().toLowerCase(Locale.ROOT));
            });
        }

        for ( int i = 1; i < 8; i++ ) {
            AppCompatButton key = (AppCompatButton)row3.getChildAt(i);
            key.setOnClickListener( v-> {
                CharSequence tmp = ballsBox.getText();
                String ballsHolder = String.valueOf(tmp);
                ballsBox.setText(ballsHolder + key.getText().toString().toLowerCase(Locale.ROOT));
            });
        }

        AppCompatButton backspace = (AppCompatButton)row3.getChildAt(8);

        backspace.setOnClickListener( v -> {
            CharSequence tmp = ballsBox.getText();
            String ballsHolder = String.valueOf(tmp);
            int len = ballsHolder.length();
            if ( len > 12 ) {
                ballsHolder = ballsHolder.substring(0, len-1);
                ballsBox.setText(ballsHolder);
            }
        });

        AppCompatButton enter = (AppCompatButton)row3.getChildAt(0);
    }
}