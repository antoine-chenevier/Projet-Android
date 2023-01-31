package com.chenevier.projet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String APIKey = "337206f1-6a79-5d3d-5eb2-ee1ba82d54f7:fx";
    ArrayList<Langue> langue_list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AndroidNetworking.initialize(this);
        AndroidNetworking.get("https://api-free.deepl.com/v2/languages")
                .addHeaders("Authorization","DeepL-Auth-Key "+APIKey)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for(int i=0;i<response.length();i++) {
                                JSONObject langue = response.getJSONObject(i);
                                String language = langue.getString("language");
                                String name = langue.getString("name");
                                Langue langueAdd = new Langue(language,name);
                                langue_list.add(langueAdd);

                            }

                            /*
                            Intent SendHistorique = new Intent();
                            SendHistorique.putExtra("addHistorique",texte.getText().toString());
                            startActivity(SendHistorique);
*/
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

        Spinner spinner = findViewById(R.id.spinner);
        //create array adapter and provide arrary list to it
        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,langue_list);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
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
        TextView traduit = (TextView) findViewById(R.id.textViewTraduction);


        AndroidNetworking.post("https://api-free.deepl.com/v2/translate")
                .addHeaders("Authorization","DeepL-Auth-Key "+APIKey)
                .addBodyParameter("text",texte.getText().toString())
                .addBodyParameter("target_lang","FR")
                .build()

                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray texte_translation = response.getJSONArray("translations");
                            JSONObject texte_traduit = texte_translation.getJSONObject(0);
                            String test = texte_traduit.getString("text");
                            traduit.setText(test.toString());
                            /*
                            Intent SendHistorique = new Intent();
                            SendHistorique.putExtra("addHistorique",texte.getText().toString());
                            startActivity(SendHistorique);
*/

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

    }

    public void Parametres(View view) {
        Intent nextActivity = new Intent(
                MainActivity.this,
                ParametresActivity.class
        );
        startActivity(nextActivity);
    }
}
