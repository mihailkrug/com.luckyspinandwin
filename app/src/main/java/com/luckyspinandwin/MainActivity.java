package com.luckyspinandwin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.luckyspinandwin.ScrollView.IEventEnd;
import com.luckyspinandwin.ScrollView.ScrollView;
import java.util.Random;


public class MainActivity extends AppCompatActivity implements IEventEnd {

    public static final int START_MONEY = 1000;
    ScrollView image, image2, image3;
    TextView txt_score;
    int count_end = 0;
    int scoreMon;
    int betting = 0;
    private Button bet50Button, bet100Button, bet500Button, bet1000Button;
    private int minBet = 50;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        scoreMon = START_MONEY;


        bet50Button = findViewById(R.id.bet_50_button);
        bet100Button = findViewById(R.id.bet_100_button);
        bet500Button = findViewById(R.id.bet_500_button);
        bet1000Button = findViewById(R.id.bet_1000_button);

        image = findViewById(R.id.image);
        image2 = findViewById(R.id.image2);
        image3 = findViewById(R.id.image3);

        txt_score = findViewById(R.id.txt_score);

        image.setEventEnd(MainActivity.this);
        image2.setEventEnd(MainActivity.this);
        image3.setEventEnd(MainActivity.this);

        txt_score.setText(String.valueOf(scoreMon));


        bet50Button.setOnClickListener(v -> {
            gameRun(50, true);
        });
        bet100Button.setOnClickListener(v -> {
            gameRun(100, true);
        });
        bet500Button.setOnClickListener(v -> {
            gameRun(500, true);
        });
        bet1000Button.setOnClickListener(v -> {
            gameRun(1000, true);
        });


    }

    private void gameRun(int beat, boolean flag) {
        if (scoreMon >= minBet && flag) {

            betting = beat;
            image.setValueRandom(new Random().nextInt(6),
                    new Random().nextInt((15 - 5) + 1) + 5);
            image2.setValueRandom(new Random().nextInt(6),
                    new Random().nextInt((15 - 5) + 1) + 5);
            image3.setValueRandom(new Random().nextInt(6),
                    new Random().nextInt((15 - 5) + 1) + 5);

            scoreMon -= beat;
            txt_score.setText(String.valueOf(scoreMon));
        } else if (scoreMon < minBet) {
            showMessageEndGame("You have not enough money. You may restart the game.");
        }
    }

    @Override
    public void eventEnd(int result, int count) {
        if (count_end < 2) {
            count_end++;
        } else {


            count_end = 0;
            if (image.getValue() == image2.getValue() &&
                    image2.getValue() == image3.getValue()) {
                showMessage("You won " + betting*4);

                scoreMon += betting*4;
                txt_score.setText(String.valueOf(scoreMon));
            } else if (image.getValue() == image2.getValue() ||
                    image2.getValue() == image3.getValue() ||
                    image.getValue() == image3.getValue()) {
                showMessage("You won " + betting*2);

                scoreMon += betting*2;
                txt_score.setText(String.valueOf(scoreMon));
            } else {
                showMessage("You lose");

            }
        }
    }

    public void showMessageEndGame(String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setCancelable(false).setPositiveButton(R.string.new_game, (dialog, id) -> {
            NewGame();
            dialog.cancel();
        })
                .setNegativeButton(R.string.exit,
                        (dialog, id) -> {
                            finish();
                            dialog.cancel();
                        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void showMessage(String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setCancelable(false).setPositiveButton("Start", (dialog, id) -> {
            gameRun(scoreMon, false);
            dialog.cancel();
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void NewGame() {
        Intent inter = getIntent();
        finish();
        startActivity(inter);
    }
}