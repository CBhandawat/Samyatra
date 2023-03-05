package com.sahayatra.samyatra;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class Splashscreen extends Activity {

    Thread splashTread;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splashscreen);
        StartAnimations();
    }
    private void StartAnimations(){

        Animation anim= AnimationUtils.loadAnimation(this,R.anim.alpha);
        anim.reset();
        RelativeLayout l=(RelativeLayout) findViewById(R.id.lin_lay);
        l.clearAnimation();
        l.startAnimation(anim);

        anim= AnimationUtils.loadAnimation(this,R.anim.translate);
        anim.reset();

        ImageView iv=(ImageView) findViewById(R.id.splash);
        Animation animation = new TranslateAnimation(0, 600,0, 0);
        animation.setDuration(1200);
        animation.setStartOffset(1600);
        animation.setFillAfter(true);
        iv.clearAnimation();
        iv.startAnimation(animation);

        TextView sa=(TextView) findViewById(R.id.splash_samyatra);
        sa.clearAnimation();
        sa.startAnimation(anim);

        TextView t=(TextView) findViewById(R.id.splash_t_t);
        t.clearAnimation();
        t.startAnimation(anim);


        splashTread=new Thread(){
            @Override
            public void run(){
                try{
                    int waited=0;
                    while(waited<6000)
                    {
                        sleep(100);
                        waited+=100;
                    }
                    Intent intent=new Intent(Splashscreen.this,MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    Splashscreen.this.finish();
                }
                catch (InterruptedException e){

                }
                finally{
                    Splashscreen.this.finish();
                }
            }
        };
        splashTread.start();
    }

}
