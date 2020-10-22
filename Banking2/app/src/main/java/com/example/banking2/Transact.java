package com.example.banking2;

import androidx.appcompat.app.AppCompatActivity;

public class Transact extends AppCompatActivity {
    private  int id;
    private double send_bal, rec_bal;
    private String sender,recipient,dtime,transfer_amt;

    public Transact(){

    }

    public Transact(double send_bal, double rec_bal, String transfer_amt, String sender, String recipient, String dtime) {
        this.transfer_amt = transfer_amt;
        this.sender = sender;
        this.recipient = recipient;
        this.dtime = dtime;
    }

    public double getSend_bal() {
        return send_bal;
    }

    public void setSend_bal(double send_bal) {
        this.send_bal = send_bal;
    }

    public double getRec_bal() {
        return rec_bal;
    }

    public void setRec_bal(double rec_bal) {
        this.rec_bal = rec_bal;
    }



    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getDtime() {
        return dtime;
    }

    public void setDtime(String dtime) {
        this.dtime = dtime;
    }

    public String getTransfer_amt() {
        return transfer_amt;
    }

    public void setTransfer_amt(String transfer_amt) {
        this.transfer_amt = transfer_amt;
    }
}


