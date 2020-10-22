package com.example.banking2;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class UsersList extends AppCompatActivity {
    ListView listView;
    ArrayList<String> people;
    ArrayList<String> balances;
    DatabaseHelper myDatabase;
    TextView nameText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.users_list);
        listView = (ListView) findViewById(R.id.listView);
        people = new ArrayList<String>();
        balances = new ArrayList<String>();
        this.setTitle("USERS");
        CustomAdapter customAdapter = new CustomAdapter();

        myDatabase = new DatabaseHelper(this);
        /*myDatabase.insertData("987573458","John Meyer","johnm@gmail.com","90000");
         myDatabase.insertData("877849864","Barry Allen","barryallen@gmail.com","75000");
         myDatabase.insertData("987343677","Bruce Wayne","brucewayne@gmail.com","100000");
         myDatabase.insertData("898759653","Peter Parker","peterparker@gmail.com","70000");
         myDatabase.insertData("987742668","Jimmy Carter","jimmycarter@gmail.com","50000");
         myDatabase.insertData("234762357","Louis Sullivan","louissul@gmail.com","45000");
         myDatabase.insertData("355447435","Frank William","willabignail@gmail.com","80000");
         myDatabase.insertData("577632646","Riley Amber","rileyambers@gmail.com","55000");
         myDatabase.insertData("787645333","Lydia Roberts","lydiaroberts@gmail.com","90000");
         myDatabase.insertData("676456357","Matilda Smith","smithmatilda@gmail.com","86000");*/

        updateAdapterView();
        listView.setAdapter(customAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selected_user = people.get(position);
                Intent intent = new Intent(getApplicationContext(),userDetails.class);
                intent.putExtra("uname",selected_user);
                startActivity(intent);
            }
        });



    }
    public void updateAdapterView(){
        people.clear();
        balances.clear();
        Cursor c = myDatabase.getData();
        while(c.moveToNext()){
            people.add(c.getString(c.getColumnIndex("name")));
            balances.add(c.getString(c.getColumnIndex("balance")));
        }
    }

    class CustomAdapter extends BaseAdapter{


        @Override
        public int getCount() {
            return people.size();
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
        public View getView(int position, View view, ViewGroup parent) {
          //  LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
           // View customView = inflater.inflate(R.layout.adapter_view,parent,false);

            view = getLayoutInflater().inflate(R.layout.adapter_view,null);

            nameText= (TextView) view.findViewById(R.id.nameText);
            TextView balanceText = (TextView) view.findViewById(R.id.balanceText);

            nameText.setText(people.get(position));
            balanceText.setText("Balance: "+ balances.get(position));
            return view;
        }
    }

}
