package com.chenevier.projet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.text.DateFormatSymbols;

public class ActivityHistory extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView listHistory;
    String[] historique;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        listHistory = findViewById(R.id.listHistique);
        Intent i = getIntent();
        String values = i.getStringExtra("addHistorique");

        historique = new String[]{"t","d"};
        //historique[0] = "values.toString()";
        /*
        historique[0] = values;
        historique[1] = "trois";
        int taille = historique.length;
        historique[taille + 1] = values;
         */
        ArrayAdapter<String> month = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,historique);
        listHistory.setAdapter(month);
        listHistory.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String texte = parent.getItemAtPosition(position).toString();

        Intent nextActivity = new Intent(
                ActivityHistory.this,
                MainActivity.class
        );
        nextActivity.putExtra("textATraduire",texte);
        startActivity(nextActivity);


    }
}