package com.example.tictactoe;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class MultiplayerOffline extends AppCompatActivity {
    int counter=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void buclick(View view) {
        Button btn = (Button) view;

        int cellid = 0;
        switch (btn.getId()) {
            case R.id.btn1:
                cellid = 1;
                break;
            case R.id.btn2:
                cellid = 2;
                break;
            case R.id.btn3:
                cellid = 3;
                break;
            case R.id.btn4:
                cellid = 4;
                break;
            case R.id.btn5:
                cellid = 5;
                break;
            case R.id.btn6:
                cellid = 6;
                break;
            case R.id.btn7:
                cellid = 7;
                break;
            case R.id.btn8:
                cellid = 8;
                break;
            case R.id.btn9:
                cellid = 9;
                break;
        }
        play(cellid, btn);
    }

    int activeplayer = 1;
    ArrayList<Integer> player1 = new ArrayList<Integer>();
    ArrayList<Integer> player2 = new ArrayList<Integer>();

    public void play(int cellid, Button btn) {
        if (activeplayer == 1) {
            btn.setText("X");
            btn.setBackgroundColor(Color.MAGENTA);
            btn.setBackgroundResource(R.drawable.cross);
            btn.setEnabled(false);
            player1.add(cellid);
            activeplayer = 2;
        }
        else if (activeplayer == 2) {
            btn.setText("O");
            btn.setBackgroundColor(Color.BLUE);
            btn.setBackgroundResource(R.drawable.zero);
            btn.setEnabled(false);
            player2.add(cellid);
            activeplayer = 1;
        }
        checkwinner();
    }

    public void checkwinner() {
        int Winner = 0;

        //123
        if (player1.contains(1) && player1.contains(2) && player1.contains(3)) {
            Winner = 1;
        }
        if (player2.contains(1) && player2.contains(2) && player2.contains(3)) {
            Winner = 2;
        }

        //456
        if (player1.contains(4) && player1.contains(5) && player1.contains(6)) {
            Winner = 1;
        }
        if (player2.contains(4) && player2.contains(5) && player2.contains(6)) {
            Winner = 2;
        }

        //789
        if (player1.contains(7) && player1.contains(8) && player1.contains(9)) {
            Winner = 1;
        }
        if (player2.contains(7) && player2.contains(8) && player2.contains(9)) {
            Winner = 2;
        }

        ///147
        if (player1.contains(1) && player1.contains(4) && player1.contains(7)) {
            Winner = 1;
        }
        if (player2.contains(1) && player2.contains(4) && player2.contains(7)) {
            Winner = 2;
        }

        //258
        if (player1.contains(2) && player1.contains(5) && player1.contains(8)) {
            Winner = 1;
        }
        if (player2.contains(2) && player2.contains(5) && player2.contains(8)) {
            Winner = 2;
        }

        //369
        if (player1.contains(3) && player1.contains(6) && player1.contains(9)) {
            Winner = 1;
        }
        if (player2.contains(3) && player2.contains(6) && player2.contains(9)) {
            Winner = 2;
        }

        //159
        if (player1.contains(1) && player1.contains(5) && player1.contains(9)) {
            Winner = 1;
        }
        if (player2.contains(1) && player2.contains(5) && player2.contains(9)) {
            Winner = 2;
        }

        //357
        if (player1.contains(3) && player1.contains(5) && player1.contains(7)) {
            Winner = 1;
        }
        if (player2.contains(3) && player2.contains(5) && player2.contains(7)) {
            Winner = 2;
        }
        if (Winner != 0 && counter==0) {
            Toast.makeText(this, "Winner is : " + Winner, Toast.LENGTH_LONG).show();

            counter++;
        }
    }

    ArrayList<Integer> emptycells = new ArrayList<Integer>();
    public void btnplayagain(View view) {
        Intent intent = new Intent(MultiplayerOffline.this, MultiplayerOffline.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void undo(View view) {
    }

}
