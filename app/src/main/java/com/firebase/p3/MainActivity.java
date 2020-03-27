package com.firebase.p3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.p3.Utilidades.utilidades;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.ProviderQueryResult;
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
        this.setTitle("Iniciar Sesion");
        utilidades.IniciarVariablesDB(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(utilidades.mAuth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(),Portal.class));
            finish();
        }
    }

    public void Onclick(View v){
        switch (v.getId()){
            case R.id.btnIniciar:
                utilidades.mAuth.signInWithEmailAndPassword(txtCorreo.getText().toString(),txtpas.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(MainActivity.this, "Felicidades Existes", Toast.LENGTH_SHORT).show();
                            InicioActividad=new Intent(getApplicationContext(),Portal.class);

                        }else{
                            //(!task.isSuccessful()&&.equeals("ERROR_WRONG_PASSWORD"))
                            Toast.makeText(MainActivity.this, "Comprueba tus datos\n"+task.getException().getMessage()/*((FirebaseAuthException)task.getException()).getErrorCode()*/, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                break;
            case R.id.btnCrear:
                InicioActividad=new Intent(this, Crear_UserActivity.class);
                                break;
        }
        if (InicioActividad!=null){
            startActivity(InicioActividad);
            finish();
        }
    }


}
