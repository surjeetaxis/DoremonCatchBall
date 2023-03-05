package com.surjeetTech.doraemonCatchTheBall;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
public class start extends AppCompatActivity implements View.OnClickListener{



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_start);
        Button startgame=(Button)findViewById(R.id.startgame);
        startgame.setOnClickListener(this);




    }
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

    @Override
    public void onClick(View view) {
        startActivity(new Intent(getApplicationContext(),MainActivity.class));

    }
}
