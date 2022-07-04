package com.example.myapplication;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ConfirmActivity extends AppCompatActivity {
    TextView txtRecap;
    TextView txtMenu;
    TextView txtTotal;

    Intent intent_Main;

    Button btnEnvoyer;

    int montantMenu1,montantMenu2,montantMenu3;
    int montantTotal;

    String tel;

    public static final int Permission_SMSDirect = 1;
    String TAG = "LifeCycle_Confirm";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);
        intent_Main = getIntent();

        txtRecap = findViewById(R.id.textViewRecap);
        txtMenu = findViewById(R.id.textViewRecapMenu);
        txtTotal = findViewById(R.id.textViewTotal);

        btnEnvoyer = findViewById(R.id.buttonSend);
        btnEnvoyer.setOnClickListener(new MessagePizza());

        String nom = intent_Main.getStringExtra("Name");
        tel = intent_Main.getStringExtra("Phone");
        txtRecap.setText(nom);

        recapMenu();
        Log.i(TAG, "onCreate: ");
    }

    private void recapMenu() {
        String string = txtMenu.getText().toString();
        if (intent_Main.getStringExtra("menu1") != null){
            String menu1 = intent_Main.getStringExtra("menu1");
            montantMenu1 = intent_Main.getIntExtra("montantM1",10);
            string = string + menu1 + " : " + montantMenu1 + "€" + '\n';
            txtMenu.setVisibility(View.VISIBLE);
            montantTotal += 10;
        }

        if (intent_Main.getStringExtra("menu2") != null){
            String menu2 = intent_Main.getStringExtra("menu2");
            montantMenu2 = intent_Main.getIntExtra("montantM2",12);
            string = string + menu2 + " : " + montantMenu2 +"€" + '\n';
            txtMenu.setVisibility(View.VISIBLE);
            montantTotal += 12;
        }

        if (intent_Main.getStringExtra("menu3") != null){
            String menu3 = intent_Main.getStringExtra("menu3");
            montantMenu3 = intent_Main.getIntExtra("montantM3",14);
            string = string + menu3 + " : " + montantMenu3 +"€";
            txtMenu.setVisibility(View.VISIBLE);
            montantTotal += 14;
        }

        txtMenu.setText(string);
        txtTotal.setText("Le montant total de votre commmande est : " + montantTotal+"€");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private class MessagePizza implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            String telPizza = "0101011001";
            String sms = txtRecap + " " + tel +'\n'+ txtMenu.getText().toString() + '\n' + txtTotal.getText().toString();
            if (ContextCompat.checkSelfPermission(ConfirmActivity.this, Manifest.permission.SEND_SMS)!= PackageManager.PERMISSION_GRANTED) {
                
                if(ActivityCompat.shouldShowRequestPermissionRationale(ConfirmActivity.this,Manifest.permission.SEND_SMS)) {
                    Toast.makeText(ConfirmActivity.this,"accorder la permission svp", Toast.LENGTH_SHORT).show();
                    ActivityCompat.requestPermissions(ConfirmActivity.this, new String[]{Manifest.permission.SEND_SMS},Permission_SMSDirect);
                }
                else{
                    ActivityCompat.requestPermissions(ConfirmActivity.this, new String[]{Manifest.permission.SEND_SMS},Permission_SMSDirect);
                }
            }
            else{
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(telPizza, null, sms, null, null);
            }
            Toast.makeText(ConfirmActivity.this,"Commande envoyée",Toast.LENGTH_SHORT).show();
        }
    }
}