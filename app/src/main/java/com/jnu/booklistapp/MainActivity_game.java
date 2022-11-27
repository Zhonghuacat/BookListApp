package com.jnu.booklistapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

import com.jnu.booklistapp.gamedata.GameView;

public class MainActivity_game extends AppCompatActivity {


    public int resultCode = 555;
    private Intent intent;
    private int score;
    private GameView gameView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game);

        intent = getIntent();
        score=intent.getIntExtra("score",0);

        gameView = findViewById(R.id.gameView);
        gameView.bestScore[0] = score;//传入最大值
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode== KeyEvent.KEYCODE_BACK){
            score=Math.max(gameView.score,gameView.bestScore[0]);
            intent.putExtra("score",score);
            setResult(resultCode,intent);
            finish();
        }
        return true;
    }

}