package com.micromax.hack.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import com.micromax.hack.R;

/**
 * Created by Rakshith on 28-06-2015.
 */
public class SplashActivity extends Activity {
    ImageView txt,pdf,mp3,video,micro,micro1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        txt= (ImageView) findViewById(R.id.txt);
        pdf= (ImageView) findViewById(R.id.pdf);

        mp3= (ImageView) findViewById(R.id.mp3);
        micro= (ImageView) findViewById(R.id.micro);
        micro1= (ImageView) findViewById(R.id.micro1);
        video= (ImageView) findViewById(R.id.video);
        txt.setVisibility(View.INVISIBLE);
        pdf.setVisibility(View.INVISIBLE);
        micro.setVisibility(View.INVISIBLE);
        micro1.setVisibility(View.INVISIBLE);

        mp3.setVisibility(View.INVISIBLE);

        video.setVisibility(View.INVISIBLE);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                txt.setVisibility(View.VISIBLE);
                txt.animate().scaleX(2).scaleY(2).translationY(400.0f).setDuration(200);;
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        txt.animate().scaleX(1).scaleY(1).translationY(100.0f).setDuration(100);

                        pdf.setVisibility(View.VISIBLE);
                        pdf.animate().scaleX(2).scaleY(2).translationY(400.0f).setDuration(200);;
                        new Handler().postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                txt.setVisibility(View.INVISIBLE);

                                pdf.animate().scaleX(1).scaleY(1).translationY(100.0f).setDuration(100);
                                mp3.setVisibility(View.VISIBLE);
                                mp3.animate().scaleX(2).scaleY(2).translationY(400.0f).setDuration(200);;
                                new Handler().postDelayed(new Runnable() {

                                    @Override
                                    public void run() {
                                        pdf.setVisibility(View.INVISIBLE);

                                        mp3.animate().scaleX(1).scaleY(1).translationY(100.0f).setDuration(100);
                                        video.setVisibility(View.VISIBLE);
                                        video.animate().scaleX(2).scaleY(2).translationY(400.0f).setDuration(200);;
                                        new Handler().postDelayed(new Runnable() {

                                            @Override
                                            public void run() {
                                                mp3.setVisibility(View.INVISIBLE);
                                                new Handler().postDelayed(new Runnable() {

                                                    @Override
                                                    public void run() {

                                                        video.setVisibility(View.INVISIBLE);
                                                        micro.setVisibility(View.VISIBLE);
                                                        micro.animate().scaleY(1.5f).scaleX(1.5f).translationY(150.0f).setDuration(200);
new Handler().postDelayed(new Runnable() {
    @Override
    public void run() {

        micro.animate().scaleY(1f).scaleX(1f).setDuration(200);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

micro.setVisibility(View.INVISIBLE);
            micro1.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                      Intent i=new Intent(SplashActivity.this,HomeActivity.class);
                        startActivity(i);
                        finish();
                    }
                },300);}
        }, 300);
    }
},300);

                                                    }
                                                }, 300);


                                            }
                                        }, 300);


                                    }
                                }, 300);

                            }
                        }, 300);

                    }
                }, 300);

            }
        }, 300);
    }
}
