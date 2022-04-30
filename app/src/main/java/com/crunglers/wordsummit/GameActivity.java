package com.crunglers.wordsummit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.graphics.Path;
import android.os.Bundle;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

public class GameActivity extends AppCompatActivity implements QueryDelegate {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        LinearLayout row1 = findViewById(R.id.keyboardRow1);
        LinearLayout row2 = findViewById(R.id.keyboardRow2);
        LinearLayout row3 = findViewById(R.id.keyboardRow3);

        TextView ballsBox = findViewById(R.id.inputView);

        ImageView mountain = findViewById(R.id.gameMount);

        ConstraintLayout layout = findViewById(R.id.gameActivityLayout);

        final PlayerImg[] player = new PlayerImg[1];
        final Path[] path = new Path[1];
        final ValueAnimator[] pathAnimator = new ValueAnimator[1];

        mountain.post(() -> {
            player[0] = new PlayerImg(GameActivity.this, mountain);
            layout.addView(player[0]);
            path[0] = new Path();
            path[0].moveTo(player[0].getX(), player[0].getY());
            path[0].lineTo((float) (mountain.getX() + mountain.getMeasuredWidth()/2.0), mountain.getY());
            pathAnimator[0] = ObjectAnimator.ofFloat(player[0], "x", "y", path[0]);
        });

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

        AppCompatButton go = (AppCompatButton)row3.getChildAt(0);

        go.setOnClickListener( v -> {
            ballsBox.setText("Your guess: ");
            pathAnimator[0].setDuration(1000);
            pathAnimator[0].start();
            player[0].setX((float) (mountain.getX() + mountain.getMeasuredWidth()/2.0));
            player[0].setY(mountain.getY());
        });
    }

    @Override
    public void didReceiveQuery(ResultPool QueryWords) {

    }
}