package com.example.tictactoe;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

public class GameModeSelect extends AppCompatActivity {


    CardView c1,c2,c3,c4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_mode_select);

        c1=findViewById(R.id.cardview1);
        c2=findViewById(R.id.cardview2);
        c3=findViewById(R.id.cardview3);
        c4=findViewById(R.id.cardview4);
    }

    public void cardsingleplayer(View view) {
        Intent intent=new Intent(GameModeSelect.this,MainActivity.class);
        startActivity(intent);
    }

    public void cardmutiplayer(View view) {
        Intent intent=new Intent(GameModeSelect.this,MultiplayerOffline.class);
        startActivity(intent);

    }

    public void rateapp(View view) {

        Snackbar snack= Snackbar.make(findViewById(android.R.id.content),"App Not ON PlayStore",Snackbar.LENGTH_SHORT);
        snack.getView().setBackgroundResource(R.color.colorPrimaryDark);
        TextView textView = (TextView)snack.getView().findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snack.show();

    }

    public void multiplayeronline(View view) {
        Intent intent=new Intent(GameModeSelect.this,MultiplayerOnline.class);
        startActivity(intent);
    }
}
