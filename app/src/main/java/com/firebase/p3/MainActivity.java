package com.firebase.p3;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.p3.Utilidades.Alumno;
import com.firebase.p3.Utilidades.UtilsNewtwork;
import com.firebase.p3.Utilidades.utilidades;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthException;

import io.grpc.HandlerRegistry;
import io.opencensus.internal.Utils;


public class MainActivity extends AppCompatActivity {
    Intent InicioActividad = null;//pasar entre actividad
    EditText txtCorreo, txtpas;
    String Correo, Pass;
    Button btn;
    boolean Activar;
    private ProgressDialog barProgressDialog;
    private Handler updateBarHandler;
    RelativeLayout MensajeSnack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtCorreo = findViewById(R.id.TxtCorreo);
        txtpas = findViewById(R.id.Txtpass);

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(txtCorreo.getWindowToken(), 0);


        MensajeSnack = findViewById(R.id.idActivity);
        barProgressDialog = new ProgressDialog(this);
        this.setTitle("Iniciar Sesion");
        utilidades.IniciarVariablesDB(this);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)

    @Override
    protected void onStart() {
        super.onStart();

        if (UtilsNewtwork.isOnline(this)) {
            this.onResume();
        } else {
            Activar = false;
            Snackbar snack = Snackbar.make(MensajeSnack, R.string.fui_no_internet, Snackbar.LENGTH_INDEFINITE);
            snack.show();

        }
        if (utilidades.mAuth.getCurrentUser() != null) {
            utilidades.CargarInformacion();
            utilidades.CargarUsuarios();
            startActivity(new Intent(getApplicationContext(), Portal.class));
            finish();
        }
    }

    public void Onclick(View v) {
        switch (v.getId()) {
            case R.id.btnIniciar:
                try {

                    if (!utilidades.ValidarVacio(txtCorreo.getText().toString()) && txtCorreo.getText().toString() != null &&//mientras no este vacio Registro Datos
                            !utilidades.ValidarVacio(txtpas.getText().toString()) && txtpas.getText().toString() != null) {
                        Logear();
                    } else {
                        Toast.makeText(MainActivity.this, "Campos Estan Vacios", Toast.LENGTH_SHORT).show();

                    }
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                }


                break;
            case R.id.btnCrear:
                InicioActividad = new Intent(this, Crear_UserActivity.class);

                break;
        }
        if (InicioActividad != null) {
            startActivity(InicioActividad);

        }
    }

    void Logear() {
        if (utilidades.ValidarCorreo(txtCorreo.getText().toString())) {
            barProgressDialog.setTitle("Validando El Usuario en Linea...");
            barProgressDialog.show();
            utilidades.mAuth.signInWithEmailAndPassword(txtCorreo.getText().toString(), txtpas.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    try {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Felicidades Existes", Toast.LENGTH_SHORT).show();
                            InicioActividad = new Intent(getBaseContext(), Portal.class);
                            barProgressDialog.show();
                            utilidades.IniciarVariablesDB(MainActivity.this);
                            utilidades.CargarInformacion();
                            utilidades.CargarUsuarios();

                            startActivity(InicioActividad);

                        } else {

                            Toast.makeText(MainActivity.this, utilidades.DevolverError(((FirebaseAuthException) task.getException()).getErrorCode()), Toast.LENGTH_SHORT).show();

                        }
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(MainActivity.this, "Correo Invalido", Toast.LENGTH_SHORT).show();
        }
    }
}
