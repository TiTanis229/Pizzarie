package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class  MainActivity extends AppCompatActivity {

    CheckBox checkBoxM1,checkBoxM2,checkBoxM3;
    EditText EdtName,EdtPhone;

    Button BtnOK;

    Intent confirmation;

    int montantM1 = 10, montantM2 = 12, montantM3 = 14;

    TextView restoName;

    AlertDialog.Builder dialogBuilder;
    AlertDialog dialog;

    String TAG = "LifeCycle";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        restoName = findViewById(R.id.pizza);
        Bweh bweh = new Bweh();
        restoName.setOnClickListener(bweh);

        checkBoxM1 = findViewById(R.id.checkBoxMenu1);
        checkBoxM1.setOnLongClickListener(new DetailMenu());

        checkBoxM2 = findViewById(R.id.checkBoxMenu2);
        checkBoxM2.setOnLongClickListener(new DetailMenu());

        checkBoxM3 = findViewById(R.id.checkBoxMenu3);
        checkBoxM3.setOnLongClickListener(new DetailMenu());

        EdtName = findViewById(R.id.editTextName);
        EdtPhone = findViewById(R.id.editTextPhone);

        BtnOK = findViewById(R.id.buttonOK);
        BtnOK.setOnClickListener(new OrderBtn());

        confirmation = new Intent(this,ConfirmActivity.class);
    }

    private class OrderBtn implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            if (checkBoxM1.isChecked())
            {
                confirmation.putExtra("menu1",checkBoxM1.getText().toString());
                confirmation.putExtra("montantM1",montantM1);
            }
            if (checkBoxM2.isChecked())
            {
                confirmation.putExtra("menu2",checkBoxM2.getText().toString());
                confirmation.putExtra("montantM2",montantM2);
            }
            if (checkBoxM3.isChecked())
            {
                confirmation.putExtra("menu3",checkBoxM3.getText().toString());
                confirmation.putExtra("montantM3",montantM3);
            }
            Log.i(TAG, "onClick :");

            verification();
        }
    }

    private void verification() {
        if ((checkBoxM1.isChecked()||checkBoxM2.isChecked()||checkBoxM3.isChecked())
                && (!EdtName.getText().toString().equals("") && (!EdtPhone.getText().toString().equals(""))))
        {
            confirmation.putExtra("Name",EdtName.getText().toString());
            confirmation.putExtra("Phone",EdtPhone.getText().toString());
            startActivity(confirmation);
            recreate();
        }
        else {
            Toast.makeText(this, "Veuillez remplir les champs svp", Toast.LENGTH_SHORT).show();}
    }

    private class DetailMenu implements View.OnLongClickListener {
        @Override
        public boolean onLongClick(View view) {
            if (checkBoxM1.isPressed()){
                menuDialog("popup_m1");
            }else if (checkBoxM2.isPressed()){
                menuDialog("popup_m2");
            }else if (checkBoxM3.isPressed()){
                menuDialog("popup_m3");
            }
            return false;
        }
    }

    private void menuDialog(String popup){
        ImageButton imageButtonCancel;

        dialogBuilder = new AlertDialog.Builder(this);
        int id = getResources().getIdentifier(popup,"layout",getPackageName());
        final View popupView = getLayoutInflater().inflate(id,null);

        imageButtonCancel = popupView.findViewById(R.id.imageButtonBack);
        imageButtonCancel.setOnClickListener(view -> dialog.dismiss());

        dialogBuilder.setView(popupView);
        dialog = dialogBuilder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    public class Bweh implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            String url = "https://www.youtube.com/watch?v=dQw4w9WgXcQ";

            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        }

    }
}