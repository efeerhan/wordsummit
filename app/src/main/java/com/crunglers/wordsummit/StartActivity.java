package com.crunglers.wordsummit;

import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.List;
import java.util.stream.IntStream;

public class StartActivity extends AppCompatActivity implements QueryDelegate{
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        RequestQueue queue = Volley.newRequestQueue(this);
        GameMode mode = new HomoGameMode(this,queue,this);
        mode.getRoundWord();

        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }

    @Override
    public void didReceiveQuery(List<WordResult> queryWords) {
        queryWords.forEach(s -> System.out.println(s.toString()));
    }
}