package com.firebase.p3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.firebase.p3.Utilidades.utilidades;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class Portal extends AppCompatActivity {
TextView txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portal);
        txt=findViewById(R.id.idTxtPrueba);
        utilidades.IniciarVariablesDB(this);
        CargarDatos();
    }

    public void Onclick(View view) {
        switch (view.getId()){
            case R.id.idCerrarSesion:
                utilidades.mAuth.signOut();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
                break;
        }
    }
    void CargarDatos(){
        String id=utilidades.mAuth.getCurrentUser().getUid();
        utilidades.DataB_Reference.child("Alumno").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            String cadena="Correo: "+dataSnapshot.child("Correo").getValue()+
                    "\nNombre: "+dataSnapshot.child("Nombre").getValue();
                txt.setText(cadena);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
