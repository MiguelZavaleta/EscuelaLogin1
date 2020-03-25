package com.firebase.p3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Intent InicioActividad=null;//pasar entre actividad
    EditText txtCorreo,txtpas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtCorreo=findViewById(R.id.TxtCorreo);
        txtpas=findViewById(R.id.Txtpass);
    }

    public void Onclick(View v){
        switch (v.getId()){
            case R.id.btnIniciar:
                Toast.makeText(this,"Obra negra :v",Toast.LENGTH_SHORT).show();

                break;
            case R.id.btnCrear:
                InicioActividad=new Intent(this, Crear_UserActivity.class);
                if (InicioActividad!=null){
                    startActivity(InicioActividad);
                }

                break;
        }
    }
}
