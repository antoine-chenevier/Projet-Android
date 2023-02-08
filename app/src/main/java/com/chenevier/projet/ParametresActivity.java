package com.chenevier.projet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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
String APIKey = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parametres);

        // Set the objets
        TextView character = (TextView)findViewById(R.id.textViewNbCaractere);
        EditText TextKey = (EditText)findViewById(R.id.editTextCle);

        // Set the API key
        SharedPreferences file = getSharedPreferences("fileKey",MODE_PRIVATE);
        SharedPreferences.Editor editor = file.edit();
        APIKey = file.getString("APIKey","");

        // Get the usage of the API
        AndroidNetworking.get("https://api-free.deepl.com/v2/usage")
                .addHeaders("Authorization","DeepL-Auth-Key "+ APIKey)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int character_count = response.getInt("character_count");
                            int character_limit = response.getInt("character_limit");
                            String test = character_count + "/" + character_limit;
                            character.setText(test);

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
    public void SetKeyButton(View view){

        // Set objets
        TextView character = (TextView)findViewById(R.id.textViewNbCaractere);
        EditText TextKey = (EditText)findViewById(R.id.editTextCle);

        // Set API Key
        SharedPreferences file = getSharedPreferences("fileKey",MODE_PRIVATE);
        SharedPreferences.Editor editor = file.edit();
        editor.putString("APIKey",TextKey.getText().toString());
        editor.commit();
        APIKey = file.getString("APIKey","");

        // Get the usage of the API
        AndroidNetworking.get("https://api-free.deepl.com/v2/usage")
                .addHeaders("Authorization","DeepL-Auth-Key "+ APIKey)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int character_count = response.getInt("character_count");
                            int character_limit = response.getInt("character_limit");

                            String test = character_count + "/" + character_limit;
                            character.setText(test);

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