package com.example.Counter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.*;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;


import static android.R.color.holo_green_light;
import static android.R.color.holo_red_light;
import static android.R.color.white;

/**
 * Created by Denis on 13.08.2014.
 */
public class main extends Activity {

    public static final String APP_PREFERENCES = "mysettings";
    public static final String APP_PREFERENCES_COUNTER = "counter";
    public static final String APP_PREFERENCES_COUNTERTEMP = "countertemp"; //для промежуточной записи значения счетчика (чтоб не сбивался при смене галки в настройках)
    public static final boolean APP_PREFERENCES_COUNTERFLAG = true;
    public static final boolean APP_PREFERENCES_SOUNDFLAG = false;
    public static SharedPreferences mSettings;
    private RelativeLayout RLO;
    int counter;
    boolean sound;  //fun=false;
    byte fun=0;
    Button rb;
    Button b;
    TextView tv;
    long milliseconds = 30;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_lo);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);   //запрет на отключение экрана
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        rb = (Button)findViewById(R.id.button2);
        b = (Button)findViewById(R.id.button);
        tv = (TextView)findViewById(R.id.textView);
        RLO = (RelativeLayout)findViewById(R.id.layout);
        tv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (fun==0){
                    fun++;
                    Toast.makeText(getApplicationContext(), "Веселушка!!", Toast.LENGTH_SHORT).show();
                }
                else{
                    fun=0;
                    Toast.makeText(getApplicationContext(), "Теперь снова все серьезно", Toast.LENGTH_SHORT).show();
                    RLO.setBackgroundColor(Color.BLACK);
                    tv.setTextColor(Color.WHITE);
                }
                return false;
            }
        });
        final MediaPlayer mediaPlayer1 = MediaPlayer.create(getApplicationContext(), R.raw.click); // создаём новый объект mediaPlayer
        final MediaPlayer mediaPlayer2 = MediaPlayer.create(getApplicationContext(), R.raw.reset);
        final MediaPlayer mediaPlayer3 = MediaPlayer.create(getApplicationContext(), R.raw.fart);
        final MediaPlayer mediaPlayer4 = MediaPlayer.create(getApplicationContext(), R.raw.ryg);
        b.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {

                        switch (fun){
                            case 0:
                                if (sound) {
                                    mediaPlayer1.start(); // запускаем воспроизведение
                                }
                                tv.setTextColor(Color.GREEN);
                                break;
                            case 1:
                                if (sound) {
                                    mediaPlayer3.start();
                                }
                                Random rand = new Random();
                                tv.setTextColor(Color.rgb(rand.nextInt(266), rand.nextInt(266), rand.nextInt(266)));
                                RLO.setBackgroundColor(Color.rgb(rand.nextInt(266),rand.nextInt(266),rand.nextInt(266)));
                                break;
                        }
                    Vibrator vi = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    vi.vibrate(milliseconds);
                    counter++;
                    rank(counter);
                }
                if (fun==0) {
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        rank(counter);
                        tv.setTextColor(Color.WHITE);
                    }
                }
                return false;
            }
        });
        rb.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {

                        switch (fun){
                            case 0:
                                if (sound) {
                                    mediaPlayer2.start(); // запускаем воспроизведение
                                }
                                tv.setTextColor(Color.RED);
                                break;
                            case 1:
                                if (sound) {
                                    mediaPlayer4.start();
                                }
                                Random rand = new Random();
                                tv.setTextColor(Color.rgb(rand.nextInt(266), rand.nextInt(266), rand.nextInt(266)));
                                RLO.setBackgroundColor(Color.rgb(rand.nextInt(266),rand.nextInt(266),rand.nextInt(266)));
                                break;
                        }
                    Vibrator vi = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    vi.vibrate(milliseconds);
                    counter=0;
                    tv.setText("000");
                }
                if (fun==0) {
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        rank(counter);
                        tv.setTextColor(Color.WHITE);
                    }
                }
                return false;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mSettings.contains(APP_PREFERENCES_COUNTER)) {
            // Получаем число из настроек
            counter = mSettings.getInt(APP_PREFERENCES_COUNTER, 0);
            rank(counter);
        }
        if (mSettings.contains(String.valueOf(APP_PREFERENCES_SOUNDFLAG))) {
            sound = mSettings.getBoolean(String.valueOf(APP_PREFERENCES_SOUNDFLAG), true);
        }
    }

    @Override
    protected void onResume(){      //так счетчик не будет сбиваться при смене галки в настройках
        super.onResume();
        if (mSettings.contains(APP_PREFERENCES_COUNTERTEMP)) {
            // Получаем число из настроек
            counter = mSettings.getInt(APP_PREFERENCES_COUNTERTEMP, 0);
            rank(counter);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mSettings.contains(String.valueOf(APP_PREFERENCES_COUNTERFLAG))) {
            boolean cf = mSettings.getBoolean(String.valueOf(APP_PREFERENCES_COUNTERFLAG), true);
            SharedPreferences.Editor editor = mSettings.edit();
            if (cf)
                editor.putInt(APP_PREFERENCES_COUNTER, counter);
            else
                editor.putInt(APP_PREFERENCES_COUNTER, 0);
            editor.putInt(APP_PREFERENCES_COUNTERTEMP, counter);    //так счетчик не будет сбиваться при смене галки в настройках
            editor.apply();
        }

    }

    public void rank (int n){   //добавляет нули спереди
        byte k=0;
        while (n!=0){
            n=n/10;
            k++;
        }
        String out="";
        switch (k){
            case 0:
            case 1:
                out+="00";
                break;
            case 2:
                out+="0";
                break;
            case 3:
                break;
        }
        tv.setText(out+counter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {     //появление меню у активности
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        return true;
    }

    @Override
    public  boolean onOptionsItemSelected(MenuItem item) {
        // Операции для выбранного пункта меню
        switch (item.getItemId()) {
            case R.id.action_settings:      //id пункта меню в xml-файле меню
                settings(item);
                return true;
            case R.id.action_info:
                about_info(item);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //меню about
    public void about_info(MenuItem item){
        AlertDialog.Builder builder = new AlertDialog.Builder(main.this);
        builder.setTitle("О программе")
                .setMessage("Простой счетчик для Android с минимумом настроек.\n\nАвтор: Денис Коробков. 2014")
                .setIcon(R.drawable.icon)
                .setCancelable(false)
                .setNegativeButton("Закрыть",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void settings(MenuItem item){
        Intent intent = new Intent(main.this, prefs.class);
        startActivity(intent);
    }
}