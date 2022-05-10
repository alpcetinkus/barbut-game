package com.example.alp_code;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private TextView tw1, tw2, tw3, tw4, tw5, tw6, tw7, tw8, tw9;
    private int[] tahminEdilecekSayilar = new int[9];
    private TextView[] textViewArray = new TextView[9];

    private Button btnGuess;
    private Button btnNewGame;
    private EditText etNumberInput;
    private TextView twGuessHistory;
    private TextView twRemainingTime;
    private TextView twRemainingHak;
    private Timer zamanlayici;

    private int kalanHak = 5;
    private int kalanSure = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tw1 = findViewById(R.id.tw_1);
        tw2 = findViewById(R.id.tw_2);
        tw3 = findViewById(R.id.tw_3);
        tw4 = findViewById(R.id.tw_4);
        tw5 = findViewById(R.id.tw_5);
        tw6 = findViewById(R.id.tw_6);
        tw7 = findViewById(R.id.tw_7);
        tw8 = findViewById(R.id.tw_8);
        tw9 = findViewById(R.id.tw_9);

        btnGuess = findViewById(R.id.btn_guess);
        btnNewGame = findViewById(R.id.btn_new_game);
        etNumberInput = findViewById(R.id.et_number_input);
        twGuessHistory = findViewById(R.id.tw_guess_history);
        twRemainingTime = findViewById(R.id.tw_time);
        twRemainingHak = findViewById(R.id.tw_hak);

        btnGuess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tahminiKontrolEt();
            }
        });

        btnNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                yeniOyunOlustur();
            }
        });

        textViewArray[0] = tw1;
        textViewArray[1] = tw2;
        textViewArray[2] = tw3;
        textViewArray[3] = tw4;
        textViewArray[4] = tw5;
        textViewArray[5] = tw6;
        textViewArray[6] = tw7;
        textViewArray[7] = tw8;
        textViewArray[8] = tw9;

        btnNewGame.setEnabled(true);
        btnGuess.setEnabled(false);
    }


    private void tahminEdilecekSayilariOlustur() {

        for (int i = 0; i < tahminEdilecekSayilar.length; i++) {
            tahminEdilecekSayilar[i] = (int) (Math.random() * 10) + 1;
        }

//        tw1.setText((int) (Math.random() * 50) + "");
//        tw2.setText((int) (Math.random() * 50) + "");
//        tw3.setText((int) (Math.random() * 50) + "");
//        tw4.setText((int) (Math.random() * 50) + "");
//        tw5.setText((int) (Math.random() * 50) + "");
//        tw6.setText((int) (Math.random() * 50) + "");
//        tw7.setText((int) (Math.random() * 50) + "");
//        tw8.setText((int) (Math.random() * 50) + "");
//        tw9.setText((int) (Math.random() * 50) + "");
    }

    private void tahminiKontrolEt() {

        int input = Integer.parseInt(etNumberInput.getText().toString());

        boolean cevapDogruMu = false;

        for (int i = 0; i < tahminEdilecekSayilar.length; i++) {

            if (tahminEdilecekSayilar[i] == input) {
                textViewArray[i].setText(input + "");
                textViewArray[i].setBackgroundColor(Color.parseColor("#FF00FF00"));
                cevapDogruMu = true;
            }
        }

        if (!cevapDogruMu) {
            String value = twGuessHistory.getText().toString();
            value += input + ", ";
            twGuessHistory.setText(value);
            kalanHak--;
        }

        if (kalanHak == 0) {

            yeniOyunaZorla();
            Toast.makeText(this, "Oyun bitti", Toast.LENGTH_LONG).show();
        }

        twRemainingHak.setText(kalanHak + "");
    }


    private void yeniOyunaZorla() {
        btnGuess.setEnabled(false);
        btnNewGame.setEnabled(true);
        twRemainingTime.setText("-");
        zamanlayici.cancel();
    }

    private void yeniOyunOlustur() {

        for (int i = 0; i < tahminEdilecekSayilar.length; i++) {

            textViewArray[i].setText("");
            textViewArray[i].setBackgroundColor(Color.parseColor("#FFFFFFFF"));
        }

        tahminEdilecekSayilariOlustur();
        kalanHak = 5;
        kalanSure = 30;

        twGuessHistory.setText("");
        twRemainingHak.setText(kalanHak + "");
        btnGuess.setEnabled(true);
        btnNewGame.setEnabled(false);
        zamanlayiciBaslat();

    }

    public void zamanlayiciBaslat() {

        zamanlayici = new Timer();

        TimerTask gorev = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        kalanSure--;
                        if (kalanSure <= -1) {
                            Toast.makeText(MainActivity.this, "Oyunun Süresi Bitti.", Toast.LENGTH_SHORT).show();
                            yeniOyunaZorla();
                        } else {
                            twRemainingTime.setText("Kalan Süre:" + kalanSure);
                        }
                    }
                });
            }
        };
        zamanlayici.schedule(gorev, 0, 1000);
    }
}