package com.example.projetv1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity  {
    private TextView tv1,tv2;
    private Button bt1,bt2,bt3;
    private int score1=0;
    private int ancienScore1=0;
    private int score2=0;
    private int ancienScore2=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv1=findViewById(R.id.tv1);
        tv2=findViewById(R.id.tv2);
        bt1=findViewById(R.id.bt1);
        bt2=findViewById(R.id.bt2);
        bt3=findViewById(R.id.bt3);

        charger1();
        charger2();
    }

    public void Normale(View v){
        Intent intent = new Intent(this, Main2Activity.class);
        startActivityForResult(intent, 1);
    }

    public void Inversée(View v){
        Intent intent = new Intent(this, Main3Activity.class);
        startActivityForResult(intent, 2);
    }

    protected void onActivityResult(int requestCode,int resultCode, Intent Main2Activity) {
        super.onActivityResult(requestCode, resultCode, Main2Activity);
        if (requestCode==1) {
            if (resultCode==RESULT_OK){
                partager1();
                score1=Main2Activity.getIntExtra("Donne",0);
                ancienScore1=getPreferences(MODE_PRIVATE).getInt("cle1",0);
                comparerScoree1();
            }
        }
        if (requestCode==2) {
            if (resultCode==RESULT_OK){
                partager2();
                score2=Main2Activity.getIntExtra("Donne2",0);
                ancienScore2=getPreferences(MODE_PRIVATE).getInt("cle2",0);
                comparerScoree2();
            }
        }
    }


    public void  partager1(){
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("cle1", score1);
        editor.apply();
    }
    public void  partager2(){
        SharedPreferences preferences1 = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences1.edit();
        editor.putInt("cle2", score2);
        editor.apply();
    }
    public  void charger1(){
        tv1.setText(Integer.toString(getPreferences(MODE_PRIVATE).getInt("cle1",0)));
        score1=getPreferences(MODE_PRIVATE).getInt("cle1",0);
    }
    public  void charger2(){
        tv2.setText(Integer.toString(getPreferences(MODE_PRIVATE).getInt("cle2",0)));
        score2=getPreferences(MODE_PRIVATE).getInt("cle2",0);


    }
    public  void comparerScoree1(){
        if (ancienScore1<=score1){
            partager1();
            tv1.setText(Integer.toString(getPreferences(MODE_PRIVATE).getInt("cle1",0)));
        }
    }
    public  void comparerScoree2(){
        if (ancienScore2<=score2){
            partager2();
            tv2.setText(Integer.toString(getPreferences(MODE_PRIVATE).getInt("cle2",0)));
        }
    }
    public  void quitter(View v){
        finish();
    }

}
