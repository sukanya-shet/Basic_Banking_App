package com.example.banking2;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class TransactionView extends AppCompatActivity {
    DatabaseHelper mydatabase;
    ArrayList<Transact> transactList;
    ListView tranListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_view);
        mydatabase = new DatabaseHelper(this);
        transactList = mydatabase.getTransactions();
        tranListView = (ListView) findViewById(R.id.tranListView);
        CustomAdapter customAdapter = new CustomAdapter();
        tranListView.setAdapter(customAdapter);


    }

    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return transactList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.adapter_transaction_view, null);
            TextView senderText = (TextView) convertView.findViewById(R.id.senderText);
            TextView recText = (TextView) convertView.findViewById(R.id.recText);
            TextView dayText = (TextView) convertView.findViewById(R.id.dayText);
            TextView amtText = (TextView) convertView.findViewById(R.id.amtText);

            senderText.setText(transactList.get(position).getSender());
            recText.setText(transactList.get(position).getRecipient());
            dayText.setText(transactList.get(position).getDtime());
            amtText.setText(String.valueOf(transactList.get(position).getTransfer_amt()));
            Log.i("Details: ",transactList.get(position).getSender()+transactList.get(position).getDtime()+transactList.get(position).getRecipient()+(transactList.get(position).getTransfer_amt()));

            return convertView;
        }
    }
}