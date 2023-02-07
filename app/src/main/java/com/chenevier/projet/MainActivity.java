package com.chenevier.projet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    String APIKey = "";
    ArrayList<Langue> langue_list = new ArrayList<>();
    ArrayAdapter<Langue> arrayAdapter ;
    String langue_selected = "FR";
    int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AndroidNetworking.initialize(this);

        SharedPreferences file = getSharedPreferences("fileKey",MODE_PRIVATE);
        SharedPreferences.Editor editor = file.edit();
        APIKey = file.getString("APIKey","");

        AndroidNetworking.get("https://api-free.deepl.com/v2/languages")
                .addHeaders("Authorization", "DeepL-Auth-Key " + APIKey)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject langue = response.getJSONObject(i);
                                String language = langue.getString("language");
                                String name = langue.getString("name");
                                Langue langueAdd = new Langue(language, name);
                                langue_list.add(langueAdd);


                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast toast = Toast.makeText(MainActivity.this, anError.toString(), Toast.LENGTH_LONG);
                        toast.show();
                    }
                });
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, langue_list);
        Spinner spinner = findViewById(R.id.spinner);
        //create array adapter and provide arrary list to it

        arrayAdapter.notifyDataSetChanged();
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinner.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
/*
        Intent i = getIntent();
        String values = i.getStringExtra("textATraduire");
        if(!values.isEmpty()){
            EditText traduire = (EditText) findViewById(R.id.editTextTraduire);
            traduire.setText(values);
        }

 */

    }

    public void Historique(View view) {
        Intent nextActivity = new Intent(
                MainActivity.this,
                ActivityHistory.class
        );
        startActivity(nextActivity);
    }

    public  void Traduire(View view){



        SharedPreferences file = getSharedPreferences("fileKey",MODE_PRIVATE);
        SharedPreferences.Editor editor = file.edit();
        APIKey = file.getString("APIKey","");

        EditText texte = (EditText) findViewById(R.id.editTextTraduire);
        TextView traduit = (TextView) findViewById(R.id.textViewTraduction);
        Spinner spinner = (Spinner)findViewById(R.id.spinner);
        //Langue langueL = (Langue) spinner.getSelectedItem();
        //langue_selected = langueL.language;
        AndroidNetworking.post("https://api-free.deepl.com/v2/translate")
                .addHeaders("Authorization","DeepL-Auth-Key "+APIKey)
                .addBodyParameter("text",texte.getText().toString())
                .addBodyParameter("target_lang",langue_selected)
                .build()

                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray texte_translation = response.getJSONArray("translations");
                            JSONObject texte_traduit = texte_translation.getJSONObject(0);
                            String test = texte_traduit.getString("text");
                            traduit.setText(test);

                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast toast = Toast.makeText(MainActivity.this,anError.toString(),Toast.LENGTH_LONG);
                        toast.show();
                    }
                });


            SharedPreferences fileH = getSharedPreferences("fileHistory",MODE_PRIVATE);
            SharedPreferences.Editor editorH = fileH.edit();
            editorH.putString("historique"+ i,texte.getText().toString());
            editorH.commit();
            i++;
            if(i>10){
                i=0;
            }



    }

    public void Parametres(View view) {
        Intent nextActivity = new Intent(
                MainActivity.this,
                ParametresActivity.class
        );
        startActivity(nextActivity);
    }
}
