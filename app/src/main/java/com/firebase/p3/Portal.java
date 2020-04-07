package com.firebase.p3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.p3.Adaptadore.AdaptadorUsuario;
import com.firebase.p3.Adaptadore.AdapterModificar;
import com.firebase.p3.Utilidades.Alumno;
import com.firebase.p3.Utilidades.utilidades;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Portal extends AppCompatActivity {
    public FirebaseDatabase Database;
    public DatabaseReference Data;
    TextView txt;
    ListView simpleList;
    String idU = utilidades.Id;
    AdapterModificar adaptador;
    RecyclerView recyclerUsuarios;
    AdaptadorUsuario miAdaptadorUsuario;
    int index=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portal);
        txt = findViewById(R.id.idTxtPrueba);
        simpleList = findViewById(R.id.list);
        recyclerUsuarios = findViewById(R.id.recyclerAvatarsId);
        utilidades.IniciarVariablesDB(this);
        this.setTitle(utilidades.mAuth.getUid());
        //Crear ListAdapter para modificar los datos del Usuario con la Sesion Iniciada
        adaptador = new AdapterModificar(this, Alumno.llaves.length - 1, idU, utilidades.DatosUsuario);
        simpleList.setAdapter(adaptador);

        CargarRecyclerUsuarios();//Creary llenar RecyclerView PAra Consultar Datos
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    public void CargarRecyclerUsuarios() {
        recyclerUsuarios.setLayoutManager(new LinearLayoutManager(this));
        recyclerUsuarios.setHasFixedSize(true);

        miAdaptadorUsuario = new AdaptadorUsuario(utilidades.Usuarios, this);
        miAdaptadorUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index=recyclerUsuarios.getChildLayoutPosition(v);
                utilidades.AlumnoSeleccionado = utilidades.Usuarios.get(recyclerUsuarios.getChildLayoutPosition(v));
                txt.setText("Seleccionaste a: "+utilidades.AlumnoSeleccionado.getId());
                Toast.makeText(Portal.this,   recyclerUsuarios.getChildLayoutPosition(v)+"<<Seleccionaste: " + utilidades.AlumnoSeleccionado.getId(), Toast.LENGTH_SHORT).show();
            }
        });
        recyclerUsuarios.setAdapter(miAdaptadorUsuario);

    }

    public void Onclick(View view) {
        switch (view.getId()) {
            case R.id.idCerrarSesion:
                utilidades.mAuth.signOut();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_opciones, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.icon_guardar:
                Map<String, Object> MapaModificar = new HashMap<>();
                AdapterModificar.objeto.setId(idU);
                MapaModificar = AdapterModificar.objeto.toMap();
                //Preguntamos si Nuestros campos Estan Vacios y Nuestro datos son Correctos
                if (AdapterModificar.objeto.ValidarCampos()
                        && AdapterModificar.objeto.ValidaTelefono(AdapterModificar.objeto.getTel())) {
                    utilidades.DataB_Reference.child("Alumno").child(idU).updateChildren(MapaModificar).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                adaptador.notifyDataSetChanged();
                            }
                        }
                    });
                    Toast.makeText(Portal.this, "<<Cambiado Exitosamente",
                            Toast.LENGTH_SHORT).show();
                    miAdaptadorUsuario.notifyDataSetChanged();

                } else {//si no Mandamos a verificar
                    adaptador.notifyDataSetChanged();
                    Toast.makeText(this, "Verifica tus datos", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.icon_eliminar:
                final FirebaseAuth UsuarioEliminar = FirebaseAuth.getInstance();
                if (index != -1 && !utilidades.AlumnoSeleccionado.getId().equals(utilidades.mAuth.getUid())) {//Si Seleccionamos algo
                    DialogoEliminar(UsuarioEliminar).show();//Dialog 2 Opciones
                }else if( index!=-1 && utilidades.AlumnoSeleccionado.getId().equals(utilidades.mAuth.getUid())){//Comparamos si es nuestro propio Index
                    DialogEliminaCuentaActiva(UsuarioEliminar).show();//DialogEliminarCuenta
                }
                else{//Si no Seleccionamos ningun elemento, cuando Carga la App
                        Toast.makeText(this, "Selecciona un Elemento del Recycler", Toast.LENGTH_SHORT).show();
                    }
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public AlertDialog DialogoEliminar(final FirebaseAuth UsuarioEliminar) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Advertencia!!")
                .setMessage("Estas seguro de que Deseas Eliminar a" + utilidades.AlumnoSeleccionado.getNombre().toUpperCase()
                        + "y toda sun informacion?")
                .setNegativeButton("Eliminar Mi Cuenta", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EliminarCuenta(UsuarioEliminar,true);
                                    }
                })
                .setPositiveButton("Eliminar Cuenta Usuario", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        UsuarioEliminar.signInWithEmailAndPassword(utilidades.AlumnoSeleccionado.getCorreo(), utilidades.AlumnoSeleccionado.getPass()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    EliminarCuenta(UsuarioEliminar,false);
                                    txt.setText("Hola");
                                } else {
                                    Toast.makeText(Portal.this, "Ocurrion un error de Autenticacion", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
        return builder.create();
    }
    public AlertDialog DialogEliminaCuentaActiva(final FirebaseAuth UsuarioEliminar) {
        AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
        builder2.setTitle("Advertencia!!")
                .setMessage("Estas seguro de que Deseas Eliminar a " + utilidades.AlumnoSeleccionado.getNombre().toUpperCase()
                        + "y toda sun informacion?")
                .setPositiveButton("Eliminar Mi Cuenta", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EliminarCuenta(UsuarioEliminar,true);

                    }
                });
        return builder2.create();
    }
    public void EliminarCuenta(final FirebaseAuth Usuario,final boolean ban) {
        Log.d("Error", "ingreso a deleteAccount");
        final FirebaseUser currentUser = Usuario.getCurrentUser();//Inicializamos la Variable recibida
        currentUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {//Eliminamos en Autenticacion
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d("MensajeCorrecto", "OK! Works fine!");
                    Toast.makeText(getApplicationContext(), "Eliminado", Toast.LENGTH_SHORT).show();
                    utilidades.DataB_Reference.child("Alumno").child(utilidades.AlumnoSeleccionado.getId()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        //Eliminamos del Relatime
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                if(ban==false){//Para Eliminar al Usuario sin Salir:
                                    //Eliminamos otro usuario sin salir de nuestra sesion y Refrescamos El Adapter
                                    miAdaptadorUsuario.notifyDataSetChanged();
                                    index= -1;
                                }else{//Validamos si Eliminamos en nuestra cuenta con sesion iniciada nuestro usuario Salimos al mainActivity
                                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                    finish();
                                }

                            }
                        }
                    });
                } else {
                    Log.w("MensajeError", "Something is wrong!");
                }
            }
        });
    }
}
