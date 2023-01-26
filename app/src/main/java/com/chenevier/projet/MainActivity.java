package com.chenevier.projet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Network;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.langue, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        Intent i = getIntent();
        String values = i.getStringExtra("textATraduire");
        EditText traduire = (EditText) findViewById(R.id.editTextTraduire);
        traduire.setText(values);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(adapterView.getContext(),text,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void Historique(View view) {
        Intent nextActivity = new Intent(
                MainActivity.this,
                ActivityHistory.class
        );
        startActivity(nextActivity);
    }

    public  void Traduire(View view){

        EditText texte = (EditText) findViewById(R.id.editTextTraduire);
        Intent SendHistorique = new Intent();
        SendHistorique.putExtra("addHistorique",texte.getText().toString());
        startActivity(SendHistorique);
    }

    public void Parametres(View view) {
        Intent nextActivity = new Intent(
                MainActivity.this,
                ParametresActivity.class
        );
        startActivity(nextActivity);
    }
}
