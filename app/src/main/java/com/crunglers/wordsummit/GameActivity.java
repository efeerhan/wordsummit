package com.crunglers.wordsummit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.graphics.Path;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import javax.xml.transform.Result;

public class GameActivity extends AppCompatActivity implements QueryDelegate {

    private ResultPool roundPool = null;
    private Timer timer = new Timer();
    private int time = 20;
    private GameMode mode;
    private final int TIMER_PERIOD = 1000;

    private class timeLoop extends TimerTask {

        @SuppressLint("DefaultLocale")
        @Override
        public void run() {
            TextView timerView = findViewById(R.id.timer);
            TextView wordsLeftView = findViewById(R.id.wordsLeft);
            runOnUiThread(() -> {
                if ( time > 0 ){
                    time--;
                }
                else {
                    time = 20;
                    TextView hintTextView = findViewById(R.id.wordHint);
                    hintTextView.setText(String.format(mode.getModeTip(), mode.getRoundWord()));
                    wordsLeftView.setText(String.format("Words left: %d", roundPool.wordsLeft()));
                }
                timerView.setText(String.format("Time: %d", time));
            });
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        RequestQueue queue = Volley.newRequestQueue(this);

        if (getIntent().getExtras().getString("mode").equals("syn")) {
            mode = new SynGameMode(this, queue, this);
        }
        else {
            mode = new HomoGameMode(this, queue, this);
        }

        TextView hintTextView = findViewById(R.id.wordHint);
        hintTextView.setText(String.format(mode.getModeTip(), mode.getRoundWord()));

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
            /*pathAnimator[0].setDuration(1000);
            pathAnimator[0].start();
            player[0].setX((float) (mountain.getX() + mountain.getMeasuredWidth()/2.0));
            player[0].setY(mountain.getY());*/

            System.out.println("LINE: " + String.valueOf(ballsBox.getText()).substring(12));

            if ( roundPool.checkGuess(String.valueOf(ballsBox.getText()).substring(12))) {
                Toast.makeText(getApplicationContext(), "Correct :)", Toast.LENGTH_SHORT).show();
                timer.cancel();
                timer = new Timer();
                time = 20;
                timer.schedule(new timeLoop(), 0, TIMER_PERIOD);
                TextView wordsLeftView = findViewById(R.id.wordsLeft);
                wordsLeftView.setText(String.format("Words left: %d", roundPool.wordsLeft()));
            }
            else
                Toast.makeText(getApplicationContext(),"Incorrect :(",Toast.LENGTH_SHORT).show();

            ballsBox.setText("Your guess: ");
        });

       timer.schedule(new timeLoop(), 0, TIMER_PERIOD);
    }

    @Override
    public void didReceiveQuery(ResultPool QueryWords) {
        roundPool = QueryWords;
    }
}