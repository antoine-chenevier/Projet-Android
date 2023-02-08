package com.chenevier.projet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.text.DateFormatSymbols;
import java.util.ArrayList;

public class ActivityHistory extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView listHistory;
    ArrayList<String> historique = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        // Set objets
        listHistory = findViewById(R.id.listHistique);

        // Read the history
        SharedPreferences fileH = getSharedPreferences("fileHistory",MODE_PRIVATE);
        SharedPreferences.Editor editorH = fileH.edit();
        for(int i=0;i<10;i++){
            String value = fileH.getString("historique"+ i,"");
            historique.add(value);
        }

        // Set the display for history
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,historique);
        listHistory.setAdapter(arrayAdapter);
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
    public void cleanHistory(View view){
        // Clean History function

        // Reset fil History
        for(int i=0;i<10;i++){
            // Save the last 10 text translated
            SharedPreferences fileH = getSharedPreferences("fileHistory",MODE_PRIVATE);
            SharedPreferences.Editor editorH = fileH.edit();
            editorH.putString("historique"+ i,"");
            editorH.commit();
        }

        // Reset the history list
        historique = new ArrayList<String>();

        // Set the display for history
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,historique);
        listHistory.setAdapter(arrayAdapter);
        listHistory.setOnItemClickListener(this);
    }
}