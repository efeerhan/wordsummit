package com.crunglers.wordsummit;

import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.graphics.Path;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.crunglers.wordsummit.gamemode.GameMode;
import com.crunglers.wordsummit.gamemode.HomoGameMode;
import com.crunglers.wordsummit.gamemode.SynGameMode;
import com.crunglers.wordsummit.query.QueryDelegate;
import com.crunglers.wordsummit.query.ResultPool;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity implements QueryDelegate {

    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    private int score = 0;
    private int highscore = 0;
    private ResultPool roundPool = null;
    private Timer timer = new Timer();
    private int time = 20;
    private GameMode mode;
    private final int TIMER_PERIOD = 1000;
    TextView wordsLeftView;

    private class timeLoop extends TimerTask {

        @SuppressLint("DefaultLocale")
        @Override
        public void run() {
            TextView timerView = findViewById(R.id.timer);
            runOnUiThread(() -> {
                if ( time == 20 ) {
                    TextView hintTextView = findViewById(R.id.wordHint);
                    hintTextView.setText(String.format(mode.getModeTip(), mode.getRoundWord()));
                }
                if ( time > 0 ) {
                    time--;
                }
                if ( time == 0 ) {
                    time = 20;
                }
                timerView.setText(String.format("Time: %d", time));
            });
        }
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        prefs = getSharedPreferences("MySharedPref",MODE_PRIVATE);
        editor = prefs.edit();
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        RequestQueue queue = Volley.newRequestQueue(this);

        wordsLeftView = findViewById(R.id.wordsLeft);

        if (getIntent().getExtras().getString("mode").equals("syn")) {
            mode = new SynGameMode(this, queue, this);
        }
        else {
            mode = new HomoGameMode(this, queue, this);
        }
        //TODO lookie here matie
        highscore = prefs.getInt(mode.getHighScoreTag(),0);

        TextView hintTextView = findViewById(R.id.wordHint);
        hintTextView.setText(String.format(mode.getModeTip(), mode.getRoundWord()));

        LinearLayout row1 = findViewById(R.id.keyboardRow1);
        LinearLayout row2 = findViewById(R.id.keyboardRow2);
        LinearLayout row3 = findViewById(R.id.keyboardRow3);

        TextView ballsBox = findViewById(R.id.inputView);

        ImageView mountain = findViewById(R.id.gameMount);

        ConstraintLayout layout = findViewById(R.id.gameActivityLayout);

        final PlayerImg[] player = new PlayerImg[1];
        final Path[] path1 = new Path[1];
        final Path[] path2 = new Path[1];
        final Path[] path3 = new Path[1];
        final ValueAnimator[] pathAnimator1 = new ValueAnimator[1];
        final ValueAnimator[] pathAnimator2 = new ValueAnimator[1];
        final ValueAnimator[] pathAnimator3 = new ValueAnimator[1];
        int[] correctCount = new int[1];

        mountain.post(() -> {

            float mX = mountain.getX();
            float mY = mountain.getY();
            float mWidth = mountain.getMeasuredWidth();
            float mHeight = mountain.getMeasuredHeight();

            player[0] = new PlayerImg(GameActivity.this, mountain);
            layout.addView(player[0]);

            path1[0] = new Path();
            path2[0] = new Path();
            path3[0] = new Path();

            path1[0].moveTo(player[0].getX(), player[0].getY());
            path1[0].lineTo((float)(mX + mWidth*0.85), (float)(mY + mHeight*0.66));

            path2[0].moveTo((float)(mX + mWidth*0.85), (float)(mY + mHeight*0.66));
            path2[0].lineTo((float)(mX + mWidth*0.35), (float)(mY + mHeight*0.33));

            path3[0].moveTo((float)(mX + mWidth*0.35), (float)(mY + mHeight*0.33));
            path3[0].lineTo((float)(mX + mWidth*0.5), (float)(mY));

            pathAnimator1[0] = ObjectAnimator.ofFloat(player[0], "x", "y", path1[0]);
            pathAnimator2[0] = ObjectAnimator.ofFloat(player[0], "x", "y", path2[0]);
            pathAnimator3[0] = ObjectAnimator.ofFloat(player[0], "x", "y", path3[0]);
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
            //System.out.println("LINE: " + String.valueOf(ballsBox.getText()).substring(12));

            if ( roundPool.checkGuess(String.valueOf(ballsBox.getText()).substring(12))) {
                correctCount[0]++;
                Toast.makeText(getApplicationContext(), "Correct :)", Toast.LENGTH_SHORT).show();
                timer.cancel();
                timer = new Timer();
                time = 20;
                timer.schedule(new timeLoop(), 0, TIMER_PERIOD);
                TextView wordsLeftView = findViewById(R.id.wordsLeft);
                wordsLeftView.setText(String.format("Similar words: %d", roundPool.wordsLeft()));
                if ( correctCount[0] == 1 ){
                    pathAnimator1[0].setDuration(1000);
                    pathAnimator1[0].start();
                }
                if ( correctCount[0] == 2 ){
                    pathAnimator2[0].setDuration(1000);
                    pathAnimator2[0].start();
                }
                if ( correctCount[0] == 3 ){
                    pathAnimator3[0].setDuration(1000);
                    pathAnimator3[0].start();
                }

            }
            else
                Toast.makeText(getApplicationContext(),"Incorrect :(",Toast.LENGTH_SHORT).show();

            ballsBox.setText("Your guess: ");
        });

       timer.schedule(new timeLoop(), 0, TIMER_PERIOD);
    }

    public void updateHighScore() {
        if (score > highscore) {
            highscore = score;
            //TODO UPDATE HIGH SCORE LABEL
            editor.putInt(mode.getHighScoreTag(),score);
        }
    }


    @SuppressLint("DefaultLocale")
    @Override
    public void didReceiveQuery(ResultPool QueryWords) {
        System.out.println("WORD GOT");
        roundPool = QueryWords;
        mode.modifyPool(roundPool);
        wordsLeftView.setText(String.format("Similar word: %d", roundPool.wordsLeft()));
    }
}