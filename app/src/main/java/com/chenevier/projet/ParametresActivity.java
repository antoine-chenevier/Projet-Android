package com.chenevier.projet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.OkHttpResponseListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Response;

public class ParametresActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parametres);
        TextView character = (TextView)findViewById(R.id.textViewNbCaractere);
        AndroidNetworking.get("https://api-free.deepl.com/v2/usage")
                .addHeaders("Authorization","DeepL-Auth-Key "+"337206f1-6a79-5d3d-5eb2-ee1ba82d54f7:fx")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int character_count = response.getInt("character_count");
                            int character_limit = response.getInt("character_limit");
                            String test = character_count + "/" + character_limit;
                            character.setText(test);

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
                        Toast toast = Toast.makeText(ParametresActivity.this,anError.toString(),Toast.LENGTH_LONG);
                        toast.show();
                    }
                });
    }
}