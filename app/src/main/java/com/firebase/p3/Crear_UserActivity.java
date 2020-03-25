package com.firebase.p3;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
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
        Txt[0]=findViewById(R.id.txtNomb);
        Txt[1]=findViewById(R.id.txtAp);
        Txt[2]=findViewById(R.id.txtCurp);
        Txt[3]=findViewById(R.id.txtTel);
        Txt[4]=findViewById(R.id.txtCorreo);
        Txt[5]=findViewById(R.id.txtUsuario);
        Txt[6]=findViewById(R.id.txtContra);
        lista=findViewById(R.id.idListar);

        utilidades.IniciarVariablesDB(this);
        Eventos();

        ListarDatos();

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
                // if(validar()){
                //String [] Apellidos=Txt[1].getText().toString().split(" ");
                try {
                    String id=UUID.randomUUID().toString();

                    Alumno.setId(id);
                    Alumno.setNombre(Txt[0].getText().toString());
                    Alumno.setApellidos(Txt[1].getText().toString());
                    Alumno.setCurp(Txt[2].getText().toString());
                    Alumno.setTel(Txt[3].getText().toString());
                    Alumno.setCorreo(Txt[4].getText().toString());
                    Alumno.setUsuario(Txt[5].getText().toString());
                    Alumno.setPass(Txt[6].getText().toString());
                    utilidades.DataB_Reference.child("Alumno").child(id).setValue(Alumno);


                    dialogoUsuario().show();
                    // Limpiar();
                }catch (Exception e){
                    Toast.makeText(Crear_UserActivity.this,""+ e, Toast.LENGTH_LONG).show();
                }


                // }else{
                //Toast.makeText(Crear_UserActivity.this, "Verifica si tus datos son Correctos", Toast.LENGTH_SHORT).show();
                // }
                break;
            case R.id.btnModificar:

                Alumno.setId(AlumnoSeleccionado.getId());
                Alumno.setNombre(Txt[0].getText().toString());
                Alumno.setApellidos(Txt[1].getText().toString());
                Alumno.setCurp(Txt[2].getText().toString());
                Alumno.setTel(Txt[3].getText().toString());
                Alumno.setCorreo(Txt[4].getText().toString());
                Alumno.setUsuario(Txt[5].getText().toString());
                Alumno.setPass(Txt[6].getText().toString());
                utilidades.DataB_Reference.child("Alumno").child(AlumnoSeleccionado.getId()).setValue(Alumno);
               Limpiar();
                break;
            case R.id.btnEliminar:
                String idOperacional=AlumnoSeleccionado.getId();
                utilidades.DataB_Reference.child("Alumno").child(AlumnoSeleccionado.getId()).removeValue();
                Toast.makeText(this, "Eliminado Correctamenter:\n"+idOperacional, Toast.LENGTH_SHORT).show();
                Limpiar();
                break;

        }
    }
    public void ListarDatos(){

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
    void ImplementarActividad(){

        if (intent!=null){//validamos que si nuestro objeto esta lleno, ejecutamos
            this.startActivity(intent);
        }}

        public void Limpiar(){
        boolean bandera=false;
        for(int i=0; i<Txt.length; i++){
          Txt[i].setText("");
        }

    }


}
