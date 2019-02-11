package com.example.root.qrproject;

import android.app.VoiceInteractor;
import android.content.Intent;
import android.os.Build;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private Button connectButton;
    private EditText login;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        connectButton = findViewById(R.id.connect_button);
        login = findViewById(R.id.login_edit);
        password = findViewById(R.id.password_edit);

        Intent intent = new Intent(this , MainActivity.class);

        connectButton.setOnClickListener(view -> {
            String URL = "http://192.168.43.157:8080/webservicesGEvt/rest/user/get/"+login.getText().toString()+"/"+password.getText().toString();

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JsonObjectRequest objectRequest = new JsonObjectRequest(
                    Request.Method.GET,
                    URL,
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.e("l'utilisateur : ",response.toString());
                            //Toast.makeText(LoginActivity.this, "Bonjour "+ response.toString(), Toast.LENGTH_SHORT).show();
                            String hote = null;
                            try {
                                hote = response.getString("login");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            intent.putExtra("hotesse", hote);
                            startActivity(intent);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("une erreur : ",error.toString());
                            Toast.makeText(LoginActivity.this, "login ou mot de passe incorrect", Toast.LENGTH_SHORT).show();
                            //maToste();
                        }
                    }
            );
            requestQueue.add(objectRequest);
        });

    }
    public void maToste(){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.hotestoast, (ViewGroup)findViewById(R.id.toste_root));
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);

        toast.show();
    }
}
