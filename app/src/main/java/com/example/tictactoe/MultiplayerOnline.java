package com.example.tictactoe;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;

public class MultiplayerOnline extends AppCompatActivity {
    public static String TAG="MultiplayerOnline";

    EditText etinviteemail,etmyemail;
    Button btnivite,btnaccept,btnlogin;
    String MyEmail;
    private String uid;
    int counter=0;


    //Player 1=Me
    //player 2=He
    // 1. Me,He login :UserLogin()
    // 1.1 me@abc.com he@abc.com
    // 2. Me invites He :buInvite() 172
    // 3. Me emails gets write under he name im database -He
    //                                                     -Jhdjn255:me@abc.com
    // 4. He edittext shows invitation with me@abc.com and green background
    // 5. He accepts ,he emails gets on Me database  -Me
    //                                                 -hJlsj15:he@abc.com
    // 6. Me edittext shows he@abc.com with green signifies acceptance of invitation



    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef=database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplayer_online);

        etinviteemail=findViewById(R.id.etinviteemail);
        etmyemail=findViewById(R.id.etmyemail);
        btnlogin=findViewById(R.id.login);

        mAuth = FirebaseAuth.getInstance();


        mAuthListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user =firebaseAuth.getCurrentUser();

                //uid=user.getUid();

                if(user!=null)
                {
                    Log.d(TAG, "onAuthStateChanged: SIGNED IN"+ user.getUid(),null);
                    MyEmail=user.getEmail();
                    btnlogin.setEnabled(false);
                    etmyemail.setText(MyEmail);

                    myRef.child("Users").child(GettingUsernamefromemail(MyEmail)).child("Request")
                            .setValue(user.getUid());
                    IncommingRequest();

                }
                else{
                    Log.d(TAG, "onAuthStateChanged: SIGNED OUT",null);
                }
            }
        };
    }
    public void IncommingRequest() {
        // Read from the database
        myRef.child("Users").child(GettingUsernamefromemail(MyEmail).trim())
                .child("Request").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                //using hashmap to read multiple request
                // key         value
                //Kjsy15gt     akshat215
                //JksH552      puksjmsjkx

                try {
                    HashMap<String, Object> hashMap = (HashMap<String, Object>) dataSnapshot.getValue();


                    if (hashMap != null) {
                        String value;
                        for (String key : hashMap.keySet()) {
                            value = (String) hashMap.get(key);
                            Log.d(TAG, "Value is: " + value);
                            etinviteemail.setText(value);
                            etinviteemail.setBackgroundColor(Color.GREEN);
                            myRef.child("Users").child(GettingUsernamefromemail(MyEmail))
                                    .child("Request").setValue(uid);
                            break;
                        }
                    }
                }
                catch (Exception ex){}

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    public String GettingUsernamefromemail(String emailtobesplit) {

        String [] split=emailtobesplit.split("@");
        return split[0];
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuthListener!=null)
        { mAuth.removeAuthStateListener(mAuthListener); }
    }

    void UserLogin(String email,String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.d(TAG, "createUserWithEmail:failure", task.getException());
                        Toast.makeText(MultiplayerOnline.this, "Login failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
    }

    public void buclick(View view) {

        if (SessionsId.length()<=0)
            return;

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

        myRef.child("Players").child(SessionsId).child("CellID :"+cellid)
                .setValue(GettingUsernamefromemail(MyEmail.trim()));
    }

    public void buinvite(View view) {
        Log.d(TAG, "buinvite: "+etinviteemail.getText().toString(),null);
        myRef.child("Users").child(GettingUsernamefromemail(etinviteemail.getText().toString().trim()))
                .child("Request").push().setValue(MyEmail);

        StartGame(GettingUsernamefromemail(GettingUsernamefromemail(etinviteemail.getText().toString())+
                " : "+ MyEmail));
        MySample="X";

    }

    public void buaccept(View view) {
        myRef.child("Users").child(GettingUsernamefromemail(etinviteemail.getText().toString().trim()))
                .child("Request").push().setValue(MyEmail);

        //mehe
        StartGame(GettingUsernamefromemail(GettingUsernamefromemail(MyEmail)+
                " : "+etinviteemail.getText().toString()));
        MySample="0";
    }

    String SessionsId="";
    String MySample="X";

    public void StartGame(String PlayerId){
        SessionsId=PlayerId;
        myRef.child("Players").child(SessionsId).removeValue();



        myRef.child("Players").child(PlayerId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.

                        try {
                            player1.clear();
                            player2.clear();
                            activeplayer = 2;

                            HashMap<String, Object> hashmoves = (HashMap<String, Object>) dataSnapshot.getValue();

                             if (hashmoves != null) {
                                String value;

                                for (String key : hashmoves.keySet()) {
                                    value = (String) hashmoves.get(key);
                                    if (!value.equals(GettingUsernamefromemail(MyEmail)))
                                        activeplayer=MySample=="X"?1:2;
                                    else
                                        activeplayer=MySample=="X"?2:1;

                                        String [] keyno=key.split(":");
                                        autoplay(Integer.parseInt(keyno[1]));

                                }
                            }
                        }catch (Exception ex){}
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.w(TAG, "Failed to read value.", error.toException());
                    }
                });

    }

    public void autoplay(int cellid) {

        Button btnauto;
        switch (cellid) {
            case 1:
                btnauto=findViewById(R.id.btn1);
                break;
            case 2:
                btnauto=findViewById(R.id.btn2);
                Toast.makeText(this,"clicked on"+cellid,Toast.LENGTH_SHORT).show();
                break;
            case 3:
                btnauto=findViewById(R.id.btn3);
                break;
            case 4:
                btnauto=findViewById(R.id.btn4);
                break;
            case 5:
                btnauto=findViewById(R.id.btn5);
                break;
            case 6:
                btnauto=findViewById(R.id.btn6);
                break;
            case 7:
                btnauto=findViewById(R.id.btn7);
                break;
            case 8:
                btnauto=findViewById(R.id.btn8);
                break;
            case 9:
                btnauto=findViewById(R.id.btn9);
                break;
            default:
                btnauto=findViewById(R.id.btn1);
                break;
        }
        play(cellid, btnauto);

    }

    public void bulogin(View view) {
        String Email=etmyemail.getText().toString().trim();
        String Pass="123456789".trim();
        UserLogin(Email,Pass);
    }

    public void btnplayagain(View view) {
        Intent intent = new Intent(MultiplayerOnline.this, MultiplayerOnline.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
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
            Toast.makeText(this,"clicked on"+cellid,Toast.LENGTH_SHORT).show();
            autoplay(cellid);

        }
        else if (activeplayer == 2) {
            btn.setText("O");
            btn.setBackgroundColor(Color.BLUE);
            btn.setBackgroundResource(R.drawable.zero);
            btn.setEnabled(false);
            player2.add(cellid);
            Toast.makeText(this,"clicked on"+cellid,Toast.LENGTH_SHORT).show();
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


}
