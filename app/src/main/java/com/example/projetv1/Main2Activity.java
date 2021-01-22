package com.example.projetv1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Main2Activity extends AppCompatActivity {
    private  TextView tv1;
    private ImageView im1;
    private  ImageView im2;
    private float axex, axey;
    private int score=0;
    private Random rnd;
    private  Button bt;
    private ProgressBar barProgression;
    private int compter=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        im1 = findViewById(R.id.im1);
        im2= findViewById(R.id.im2);
        tv1= findViewById(R.id.tv1);
        bt=findViewById(R.id.bt);
        barProgression=findViewById(R.id.progressBar);

        temp();
        // initialisation du gestionnaire de capteurs
        final SensorManager sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        //Mise en place d'un écouteur pour un capteurType donné (enregistrement d'un listener)
        sm.registerListener(accelerometreListener, sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_UI);
    }
    // Méthode appelée lors de la capture de nouvelles données du capteur (événement)
    private final SensorEventListener accelerometreListener  = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent s) {
            // on récupère les informations sur les 2 axes
            axex = s.values[0];
            axey = s.values[1];
            deplacement(im1.getX()-5*axex,im1.getY()+5*axey);
        }
        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {
        }
    };
    public  void temp(){
        final Timer t=new Timer();
        //Crée une nouvelle tâche de minuterie
        TimerTask tt=new TimerTask() {
            @Override
            public void run() {
                compter++;
                barProgression.setProgress(compter);
                if (compter==100){
                    t.cancel();
                    quitterJeu();

                }

            }
        };
        t.schedule(tt,0,600);

    }

    public void deplacement(float x,float y){
        axex=x;
        axey=y;
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int height = metrics.heightPixels;
        int width = metrics.widthPixels;
        if (axex<0) {
            axex = 0;
        }  if (axex>width-im1.getWidth()) {
            axex = (float) (width)-im1.getWidth();
        }
        if (axey<0) {
            axey = 0;
        }  if (axey>height-2.5*im1.getHeight()){
            axey = (float) ((float) (height)-2.5*im1.getHeight());
        }
        rnd = new Random();
        if (Math.abs(((int) im1.getX())- ((int) im2.getX()))<=12 && Math.abs(((int) im1.getY())- ((int) im2.getY()))<=12){
            score++;
            im2.setX(((float)rnd.nextFloat()*(width-im2.getWidth())));
            im2.setY((float) (rnd.nextFloat()*(height-4*im2.getHeight())));
            tv1.setText(Integer.toString(score));
        }
        im1.setX(((int) axex));
        im1.setY(((int) axey));

    }

    public void quitterJeu(){
        Intent intent =getIntent();
        intent.putExtra("Donne",score);
        setResult(Activity.RESULT_OK,intent);
        finish();
    }

    public void clickQuitter(View v){
        Intent intent =getIntent();
        intent.putExtra("Donne",score);
        setResult(Activity.RESULT_OK,intent);
        finish();
    }
}
