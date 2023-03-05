package com.surjeetTech.doraemonCatchTheBall;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class result extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView scorelabel=(TextView)findViewById(R.id.scorelabel);
        TextView highscorelable=(TextView)findViewById(R.id.highscore);

      /*  Button exit=(Button)findViewById(R.id.button2);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
                System.exit(0);

            }
        });*/

        Button tryagain=(Button)findViewById(R.id.tryagain);
        tryagain.setOnClickListener(this);

        int score =getIntent().getIntExtra("Score",0);
        scorelabel.setText(score + "");

        SharedPreferences setting =getSharedPreferences("Game_Data", Context.MODE_PRIVATE);
        int highscore=setting.getInt("HIGH_SCORE",0);

        if (score > highscore){
            highscorelable.setText("High Score : " + score);
            //save
            SharedPreferences.Editor editor=setting.edit();
            editor.putInt("HIGH_SCORE",score);
            editor.commit();
        }else {
            highscorelable.setText("High Score : " + highscore);
        }
    }

    @Override
    public void onClick(View view) {

        startActivity((new Intent(getApplicationContext(), start.class)));
    }
    //disable return button

   /* @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure want to Exit")
                .setNegativeButton(android.R.string.no,null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity((new Intent(getApplicationContext(), start.class)));
                    }
                }).create().show();
        super.onBackPressed();
    }
private static long back_pressed;*/

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if( event.getAction()==KeyEvent.ACTION_DOWN){
            switch (event.getKeyCode()){
                case KeyEvent.KEYCODE_BACK:
                    return true;
            }
        }
        return super.dispatchKeyEvent(event);
    }
}
