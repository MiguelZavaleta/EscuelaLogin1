package com.firebase.p3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.p3.Utilidades.Alumno;
import com.firebase.p3.Utilidades.utilidades;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import java.util.HashMap;
import java.util.Map;

public class Crear_UserActivity extends AppCompatActivity {
    EditText[] Txt = new EditText[7];
    Intent intent = null;
    Alumno Alumno = new Alumno();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear__user);
        this.setTitle("Crear una Cuenta");
        Txt[0] = findViewById(R.id.txtNomb);
        Txt[1] = findViewById(R.id.txtAp);
        Txt[2] = findViewById(R.id.txtCurp);
        Txt[3] = findViewById(R.id.txtTel);
        Txt[4] = findViewById(R.id.txtCorreo);
        Txt[5] = findViewById(R.id.txtUsuario);
        Txt[6] = findViewById(R.id.txtContra);
        utilidades.IniciarVariablesDB(this);//mandamos a llamar al Metodo que inicializa las variables desde otr aclase
    }

    public void CargarObjeto() {//Asignamos el Contenido de nuestras Variables al Objeto
        Alumno.setNombre(Txt[0].getText().toString());
        Alumno.setApellidos(Txt[1].getText().toString());
        Alumno.setCurp(Txt[2].getText().toString());
        Alumno.setTel(Txt[3].getText().toString());
        Alumno.setCorreo(Txt[4].getText().toString());
        Alumno.setUsuario(Txt[5].getText().toString());
        Alumno.setPass(Txt[6].getText().toString());
    }

    public void Onclick(View view) {//Evento onclick para el boton(es)
        switch (view.getId()) {
            case R.id.btnRegistrar:
                if (validar()) {
                    if (Alumno.ValidaTelefono(Txt[3].getText().toString()) && Alumno.ValidarCorreo(Txt[4].getText().toString())) {
                        RegistrarUsuarioMap();
                    } else {
                        Toast.makeText(this, "Verifica tu Campo Telefono u \n" +
                                " Correo sean Correctos", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(this, "Verifica tus campos", Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }

    public void RegistrarUsuarioMap() {//Metodo para Crear el Mapa con Objetos y validar
        CargarObjeto();//cargamos nuestro objeto
        utilidades.mAuth.createUserWithEmailAndPassword(Alumno.getCorreo(), Alumno.getPass()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Map<String, Object> MapaUsuarios = new HashMap<>();
                    String id = utilidades.mAuth.getCurrentUser().getUid();
                    Alumno.setId(id);
                    MapaUsuarios = Alumno.toMap();
                    utilidades.DataB_Reference.child("Alumno").child(id).setValue(MapaUsuarios).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if (task2.isSuccessful()) {
                                // utilidades.mAuth.signOut();
                                Toast.makeText(Crear_UserActivity.this, "Felicidades", Toast.LENGTH_SHORT).show();
                                intent = new Intent(getApplicationContext(), MainActivity.class);
                                ImplementarActividad();
                                finish();
                            } else {
                                Toast.makeText(Crear_UserActivity.this, "Hubo un error al Cargar los datos", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                } else {
                    //"Hubo un Error\n"+task.getException()
                    Toast.makeText(Crear_UserActivity.this, " Verificar", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    boolean validar() {
        boolean bandera = false;
        for (int i = 0; i < Txt.length; i++) {
            if (!Txt[i].getText().toString().trim().isEmpty()) {
                bandera = true;//si se valida correctamente los campos no estan vacios
            } else {
                Txt[i].requestFocus();
                bandera = false;//los campos estan vacios
                break;
            }
        }
        return bandera;
    }

    void ImplementarActividad() {
        if (intent != null) {//validamos que si nuestro objeto esta lleno, ejecutamos
            this.startActivity(intent);
        }
    }
}
