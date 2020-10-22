package com.example.banking2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class userDetails extends AppCompatActivity {
    TextView nameText,emailText,accText,balanceText;
    Button transferButton;
    Intent intent;
    double balanceAmt;
    DatabaseHelper myDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        this.setTitle("User Details");

        myDatabase = new DatabaseHelper(this);
        nameText = (TextView) findViewById(R.id.nameText);
        accText = (TextView) findViewById(R.id.accText);
        emailText = (TextView) findViewById(R.id.emailText);
        balanceText = (TextView) findViewById(R.id.balanceText);
        transferButton = (Button) findViewById(R.id.transferButton);

        intent = getIntent();
        final String selected_user = intent.getStringExtra("uname");
        nameText.setText(selected_user);

        Cursor c = myDatabase.getSelectedData(selected_user);
        while(c.moveToNext()){
            accText.setText(c.getString(1));
            emailText.setText(c.getString(3));
            balanceText.setText(c.getString(4));
            balanceAmt = Double.parseDouble(c.getString(4));
        }


        transferButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("say","error is here");
                Intent i = new Intent(getApplicationContext(),TransactionActivity.class);
                i.putExtra("balance",balanceAmt);
                i.putExtra("sender",selected_user);
                startActivity(i);


            }
        });
    }
}