package com.example.banking2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class TransactionActivity extends UsersList {
    Button transferButton, cancelButton;
    EditText transferAmtView;
    TextView balanceText;
    double bal, transferAmt, newBal,rec_bal;
    String sender, recipient;
    boolean status;
    Spinner spinner;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        this.setTitle("Transfer Money");
        transferAmtView = (EditText) findViewById(R.id.transferAmtView);
        balanceText = (TextView) findViewById(R.id.balanceText);
        transferButton = (Button) findViewById(R.id.transferButton);
        cancelButton = (Button) findViewById(R.id.cancelButton);
        spinner = (Spinner) findViewById(R.id.spinner);
        databaseHelper = new DatabaseHelper(this);

        Intent i = getIntent();
        bal = i.getDoubleExtra("balance", 0);
        sender = i.getStringExtra("sender");
        balanceText.setText(String.valueOf(bal));

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,people);
        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                recipient = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transferAmtView.setText(" ");
            }
        });

        transferButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (transferAmtView.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Enter amount", Toast.LENGTH_LONG).show();}
                else {
                    transferAmt = Double.parseDouble(transferAmtView.getText().toString());
                    if (bal <=transferAmt) {
                        Toast.makeText(getApplicationContext(), "Insufficient balance! Try again", Toast.LENGTH_LONG).show();
                    }
                    else {
                        new AlertDialog.Builder(TransactionActivity.this)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setTitle("Confirm transfer")
                                .setMessage("Wish to continue?")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        newBal = bal-transferAmt;
                                        balanceText.setText(String.valueOf(newBal));
                                        databaseHelper.addTransaction(sender,recipient,String.valueOf(transferAmt));
                                        transferAmtView.setText(" ");
                                        updateUsersData();


                                        try {
                                            Thread.sleep(2000);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                        Toast.makeText(getApplicationContext(), "Transfer successful!", Toast.LENGTH_LONG).show();
                                        Intent i2=new Intent(getApplicationContext(),MainActivity.class);
                                        startActivity(i2);
                                    }
                                })
                                .setNegativeButton("No", null)
                                .show();
                    }}
            }
        });

    }
    public void updateUsersData(){
        Cursor c = myDatabase.getSelectedData(sender);
        while(c.moveToNext()){
        myDatabase.updateData(c.getString(0),c.getString(1),sender,c.getString(3),newBal);}
        Cursor d = myDatabase.getSelectedData(recipient);
        while(d.moveToNext()){
            rec_bal = d.getDouble(4)+transferAmt;
            myDatabase.updateData(d.getString(0),d.getString(1),recipient,d.getString(3),rec_bal);
        }

    }

}