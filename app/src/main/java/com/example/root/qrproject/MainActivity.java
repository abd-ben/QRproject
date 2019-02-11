package com.example.root.qrproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements View.OnClickListener  {

    private Button qrbutton;
    private TextView nom,password;

    private IntentIntegrator qrscan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        qrbutton = (Button) findViewById(R.id.qr_scanner_button);
        nom = (TextView) findViewById(R.id.nom);
        password = (TextView) findViewById(R.id.password);

        qrscan = new IntentIntegrator(this);

        qrbutton.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                //Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
                maToste2();
            } else {
                try {
                    Bundle nomhotesse = getIntent().getExtras();
                    String hote = nomhotesse.getString("hotesse");
                    JSONObject obj = new JSONObject(result.getContents());
                    if(hote.equals(obj.getString("nomhotesse"))){
                        nom.setText(obj.getString("nomhotesse"));
                        password.setText(obj.getString("nomEvent"));
                        maToste();
                    }else {
                        maToste2();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    //Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                    maToste2();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onClick(View v) {
        qrscan.initiateScan();
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
    public void maToste2(){
        LayoutInflater inflater = getLayoutInflater(); //trans un fichier xml a un objet de type view
        View layout = inflater.inflate(R.layout.hotestoastrefuser, (ViewGroup)findViewById(R.id.toste_root2));
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);

        toast.show();
    }
}
