package com.surjeetTech.doraemonCatchTheBall;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private TextView score;
    private TextView start;
    private ImageView box;
    private ImageView black;
    private ImageView blue;
    private ImageView green;
    private ImageView yellow;
 //size
    private int frameHeight;
    private int boxSize;
    private int screenWidth;
    private int screenHeight;


    // Position
    private int boxY;
    private int blackX;
    private int blackY;
    private int blueX;
    private int blueY;
    private int yellowX;
    private int yellowY;
    private int greenX;
    private int greenY;
    private int scorecount=0;


//speed
    private int boxspeed;
    private int blackspeed;
    private int bluespeed;
    private int yellowspeed;
    private int greenspeed;


//initialize Class
    private Handler handler=new Handler();
    private Timer timer=new Timer();
    private SoundPlayer sound;
// status check
    private boolean action_flag=false;
    private boolean start_flag=false;
   // private SoundPlay sound;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sound=new SoundPlayer(this);
 //sound=new SoundPlay(this);
        score = (TextView) findViewById(R.id.score);
        start = (TextView) findViewById(R.id.start);
        box = (ImageView) findViewById(R.id.box);
        black = (ImageView) findViewById(R.id.black);
        blue = (ImageView) findViewById(R.id.blue);
        green = (ImageView) findViewById(R.id.green);
        yellow=(ImageView) findViewById(R.id.yellow);


        //screen size
        WindowManager wm =getWindowManager();
        Display disp=wm.getDefaultDisplay();
        Point size=new Point();
        disp.getSize(size);

        screenHeight=size.x;
        screenWidth=size.y;

        //now
        //Nexus4 width:768 height: 1184
        boxspeed=Math.round(screenHeight /60F);
        blackspeed=Math.round(screenHeight /45F);
        bluespeed=Math.round(screenHeight /36F);
        yellowspeed=Math.round(screenHeight /44F);
        greenspeed=Math.round(screenHeight /60F);

        Log.v("SPEED_BOX", boxspeed+"");
        Log.v("SPEED_BLACK", blackspeed+"");
        Log.v("SPEED_BLUE", bluespeed+"");
        Log.v("SPEED_YELLOW", yellowspeed+"");
        Log.v("SPEED_GREEN", greenspeed+"");

// Move to Out Of Screen
        blue.setX(-80);
        blue.setY(-80);
        black.setX(-80);
        black.setY(-80);
        green.setX(-80);
        green.setY(-80);
        yellow.setX(-80);
        yellow.setY(-80);


        score.setText(" score : 0 ");

    }

    public void changePos(){
        hitCheck();
        //green
        greenX -=greenspeed;
        if(greenX<0){
            greenX=screenWidth + 20;
            greenY=(int)Math.floor(Math.random()*(frameHeight-green.getHeight()));
        }
        green.setX(greenX);
        green.setY(greenY);

        //black
       blackX -=blackspeed;
        if(blackX<0){
            blackX=screenWidth + 50;
            blackY=(int)Math.floor(Math.random()*(frameHeight-black.getHeight()));
        }
        black.setX(blackX);
        black.setY(blackY);

        //blue
        blueX -=bluespeed;
        if(blueX<0){
            blueX=screenWidth + 100;
            blueY=(int)Math.floor(Math.random()*(frameHeight-blue.getHeight()));
        }
        blue.setX(blueX);
        blue.setY(blueY);

        //yellow
        yellowX -=yellowspeed;
        if(yellowX<0){
            yellowX=screenWidth + 1000;
            yellowY=(int)Math.floor(Math.random()*(frameHeight-yellow.getHeight()));
        }
        yellow.setX(yellowX);
        yellow.setY(yellowY);

        //Move box
        if (action_flag ==true){
            //Touching
            boxY-=boxspeed;

        }else {
            //Releasing
            boxY+=20;

        }
        if (boxY<0)boxY=0;
        if(boxY>frameHeight-boxSize)boxY=frameHeight-boxSize;

        box.setY(boxY);
        score.setText(" score : "+scorecount);
    }
    public void hitCheck(){
        // if the center of the ball is in tha box, it count as a hit.
        //green
        int greenCenterX=greenX+green.getWidth()/2;
        int greenCenterY=greenY+green.getHeight()/2;
        // 0<= green centerX <= boxWidth
        //boxy <= orangecenter <= boxy+boxheight
        if(0<= greenCenterX && greenCenterX <=boxSize &&
                boxY<=greenCenterY && greenCenterY<=boxY+boxSize){
            scorecount +=10;
            greenX =-10;
            sound.playHitSound();
        }

        //blue
        int blueCenterX=blueX+blue.getWidth()/2;
        int blueCenterY=blueY+blue.getHeight()/2;
        // 0<= bluecenterX <= boxWidth
        //boxy <= bluecenter <= boxy+boxheight
        if(0<= blueCenterX && blueCenterX <=boxSize &&
                boxY<=blueCenterY && blueCenterY<=boxY+boxSize){
            scorecount +=20;
            blueX =-10;
            sound.playHitSound();
        }

        //black
        int blackCenterX=blackX+black.getWidth()/2;
        int blackCenterY=blackY+black.getHeight()/2;
        // 0<= black centerX <= boxWidth
        //boxy <= blackcenter <= boxy+boxheight
        if(0<= blackCenterX && blackCenterX <=boxSize &&
                boxY<=blackCenterY && blackCenterY<=boxY+boxSize) {
            // stop timer
            timer.cancel();
            timer = null;
            sound.plaOverSound();
            // show result
            Intent intent =new Intent(getApplicationContext(),result.class);
            intent.putExtra("Score", scorecount);
            startActivity(intent);

        }
        //yellow
        int yellowCenterX=yellowX+yellow.getWidth()/2;
        int yellowCenterY=yellowY+yellow.getHeight()/2;
        // 0<= yellow centerX <= boxWidth
        //boxy <= yellowcenter <= boxy+boxheight
        if(0<= yellowCenterX && yellowCenterX <=boxSize &&
                boxY<=yellowCenterY && yellowCenterY<=boxY+boxSize){
            scorecount -=10;
            yellowX =-10;
            sound.playHitSound();
        }

    }
    public boolean onTouchEvent(MotionEvent me){
        if (start_flag==false){
            start_flag=true;

            FrameLayout frame=(FrameLayout)findViewById(R.id.frame) ;
            frameHeight=frame.getHeight();

            boxY=(int)box.getY();
            boxSize=box.getHeight();



            start.setVisibility(View.GONE);

            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            changePos();
                        }
                    });
                }
            },0 ,20 );
        }else {
            if (me.getAction() == MotionEvent.ACTION_DOWN) {
                action_flag = true;

            } else if (me.getAction() == MotionEvent.ACTION_UP) {
                action_flag = false;
            }
        }
            return true;
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
    }

