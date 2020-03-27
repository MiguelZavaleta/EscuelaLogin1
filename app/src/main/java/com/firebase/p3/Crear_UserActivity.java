package com.firebase.p3;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.p3.Utilidades.Alumno;
import com.firebase.p3.Utilidades.utilidades;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.ProviderQueryResult;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class Crear_UserActivity extends AppCompatActivity {
EditText [] Txt=new EditText[7];
Button insertar;
ListView lista;
Intent intent=null;
Alumno Alumno=new Alumno();
Alumno AlumnoSeleccionado=null;
ArrayAdapter<Alumno> Adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear__user);
        this.setTitle("Crear una Cuenta");
        Txt[0]=findViewById(R.id.txtNomb);
        Txt[1]=findViewById(R.id.txtAp);
        Txt[2]=findViewById(R.id.txtCurp);
        Txt[3]=findViewById(R.id.txtTel);
        Txt[4]=findViewById(R.id.txtCorreo);
        Txt[5]=findViewById(R.id.txtUsuario);
        Txt[6]=findViewById(R.id.txtContra);
        lista=findViewById(R.id.idListar);
        utilidades.IniciarVariablesDB(this);//mandamos a llamar al Metodo que inicializa las variables desde otr aclase
        Eventos();//llamamos al metodo que le asigna el evento a nuestro listview
       // ListarDatos();//Metodo para visualizar datos de firebase

    }
    public void Eventos(){
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlumnoSeleccionado=(Alumno) parent.getItemAtPosition(position);
                Txt[0].setText(AlumnoSeleccionado.getNombre());
                Txt[1].setText(AlumnoSeleccionado.getApellidos());
                Txt[2].setText(AlumnoSeleccionado.getCurp());
                Txt[3].setText(AlumnoSeleccionado.getTel());
                Txt[4].setText(AlumnoSeleccionado.getCorreo());
                Txt[5].setText(AlumnoSeleccionado.getUsuario());
                Txt[6].setText(AlumnoSeleccionado.getPass());
            }
        });
    }

    public void Onclick(View view) {

        switch (view.getId()){
            case R.id.btnRegistrar:
                RegistrarUsuarioMap();
                break;

        }
    }
    public void CargarObjeto(){

        Alumno.setNombre(Txt[0].getText().toString());
        Alumno.setApellidos(Txt[1].getText().toString());
        Alumno.setCurp(Txt[2].getText().toString());
        Alumno.setTel(Txt[3].getText().toString());
        Alumno.setCorreo(Txt[4].getText().toString());
        Alumno.setUsuario(Txt[5].getText().toString());
        Alumno.setPass(Txt[6].getText().toString());
    }
    public void RegistrarUsuarioMap(){
        CargarObjeto();
    utilidades.mAuth.createUserWithEmailAndPassword(Alumno.getCorreo(),Alumno.getPass()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            if(task.isSuccessful()){

                Map<String,Object> MapaUsuarios=new HashMap<>();

                String id=utilidades.mAuth.getCurrentUser().getUid();
                Alumno.setId(id);
                MapaUsuarios=Alumno.toMap();
               /* utilidades.DataB_Reference.child("Alumno").child(id).setValue(Alumno); cuando es por Objeto
                dialogoUsuario().show();*/
                utilidades.DataB_Reference.child("Alumno").child(id).setValue(MapaUsuarios).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task2) {
                        if(task2.isSuccessful()){
                           // utilidades.mAuth.signOut();
                            Toast.makeText(Crear_UserActivity.this, "Felicidades", Toast.LENGTH_SHORT).show();
                            intent=new Intent(getApplicationContext(),MainActivity.class);
                           ImplementarActividad();
                           finish();
                        }else{

                            Toast.makeText(Crear_UserActivity.this, "Hubo un error al Cargar los datos", Toast.LENGTH_SHORT).show();
                        }

                    }
                });


            }else if(!ComprobarEmail(Alumno.getCorreo())& !task.isSuccessful()){
                //"Hubo un Error\n"+task.getException()
                Toast.makeText(Crear_UserActivity.this,"El Correo Electronico ya Existe Favor de Verificar" , Toast.LENGTH_SHORT).show();
            }

        }
    });
    }
    public void ListarDatos(){
        Map<String, Alumno> td = new HashMap<String, Alumno>();
        utilidades.DataB_Reference.child("Alumno").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                utilidades.Usuarios=new ArrayList<>();
                utilidades.Usuarios.clear();
                for(DataSnapshot ObjSnap: dataSnapshot.getChildren()){
                   Alumno al=ObjSnap.getValue(Alumno.getClass());

                    utilidades.Usuarios.add(al);

                    Adaptador=new ArrayAdapter<>(Crear_UserActivity.this,android.R.layout.simple_list_item_1,utilidades.Usuarios);
                    lista.setAdapter(Adaptador);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    boolean validar(){
        boolean bandera=false;
        for(int i=0; i<Txt.length; i++){
            if(Txt[i].getText().toString()!=null && !Txt[i].getText().toString().trim().equals("")/* &&
            !utilidades.ValidaTelefono(Txt[5].getText().toString())&&
                    !utilidades.ValidarCorreo(Txt[6].getText().toString())*/){
                bandera=true;
            }else{
                bandera=false;
            }
        }
        return bandera;
    }

    void ImplementarActividad(){
        if (intent!=null){//validamos que si nuestro objeto esta lleno, ejecutamos
            this.startActivity(intent);
        }
    }

        public void Limpiar(){
        for(int i=0; i<Txt.length; i++){
          Txt[i].setText("");
        }
    }


    public AlertDialog dialogoUsuario(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Atencion")
                .setMessage("Datos Guardados Correctamente")
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        intent=new Intent(Crear_UserActivity.this, MainActivity.class);
                        ImplementarActividad();

                    }
                });

        return  builder.create();
    }
    private boolean ComprobarEmail(String email) {
        final boolean[] b = new boolean[1];

        utilidades.mAuth.fetchProvidersForEmail(email).addOnCompleteListener(new OnCompleteListener<ProviderQueryResult>() {
            @Override
            public void onComplete(@NonNull Task<ProviderQueryResult> task) {
                b[0] = !task.getResult().getProviders().isEmpty();//vota resultado en falso
            }
        });
        return b[0];
    }
}
