package com.crunglers.wordsummit;

import android.content.Context;
import android.os.Handler;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class PlayerImg extends androidx.appcompat.widget.AppCompatImageView {

    Timer t = new Timer();
    int refreshPeriod = 500;

    public PlayerImg(Context context, ImageView balls) {
        super(context);
        t.schedule(new AnimTickLoop(), 0,  refreshPeriod);
        this.setLayoutParams(new ViewGroup.LayoutParams(96, 128));
        setX(balls.getX());
        setY(balls.getY() + balls.getMeasuredHeight() - 64 );
    }

    class AnimTickLoop extends TimerTask {

        int frame = 0;
        Handler handler = new Handler();

        @Override
        public void run() {
            handler.post(() -> {
                if (frame == 1) {
                    frame = 0;
                    PlayerImg.this.setImageResource(R.drawable.figure1);
                }
                else {
                    frame = 1;
                    PlayerImg.this.setImageResource(R.drawable.figure2);
                }
            });
        }
    }

}
