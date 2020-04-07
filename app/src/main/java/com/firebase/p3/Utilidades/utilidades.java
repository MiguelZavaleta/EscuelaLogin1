package com.firebase.p3.Utilidades;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class utilidades {
    public static FirebaseDatabase Database;
    public static DatabaseReference DataB_Reference;
    public static FirebaseAuth mAuth;
    public static List<Alumno> Usuarios;
    public static Alumno AlumnoSeleccionado;
    public static List<String> DatosUsuario;
    public static String Id = "";

    public static void IniciarVariablesDB(Activity act) {
        FirebaseApp.initializeApp(act);
        Database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        DataB_Reference = Database.getReference();
    }

    public static void CargarInformacion() {
        DatosUsuario = new ArrayList<>();
        Id = mAuth.getCurrentUser().getUid();
        DataB_Reference.child("Alumno").child(Id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (int i = 0; i < Alumno.llaves.length - 1; i++) {

                    if (dataSnapshot.getKey() != Alumno.llaves[0]) {
                        DatosUsuario.add("" + dataSnapshot.child(Alumno.llaves[i + 1]).getValue());
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public static void CargarUsuarios() {
        Usuarios = new ArrayList<>();
        DataB_Reference.child("Alumno").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    Usuarios.clear();
                    for (DataSnapshot CtrlSnap : dataSnapshot.getChildren()) {
                        Alumno Alumnos = new Alumno();
                        Alumnos.setId(CtrlSnap.child(Alumno.llaves[0]).getValue().toString());
                        Alumnos.setNombre(CtrlSnap.child(Alumno.llaves[1]).getValue().toString());
                        Alumnos.setApellidos(CtrlSnap.child(Alumno.llaves[2]).getValue().toString());
                        Alumnos.setCurp(CtrlSnap.child(Alumno.llaves[3]).getValue().toString());
                        Alumnos.setTel(CtrlSnap.child(Alumno.llaves[4]).getValue().toString());
                        Alumnos.setCorreo(CtrlSnap.child(Alumno.llaves[5]).getValue().toString());
                        Alumnos.setUsuario(CtrlSnap.child(Alumno.llaves[6]).getValue().toString());
                        Alumnos.setPass(CtrlSnap.child(Alumno.llaves[7]).getValue().toString());
                        utilidades.Usuarios.add(Alumnos);

                       /* String id=CtrlSnap.child(Alumno.llaves[0]).getValue().toString();
                        String Nombre=CtrlSnap.child(Alumno.llaves[1]).getValue().toString();
                        String Ape=CtrlSnap.child(Alumno.llaves[2]).getValue().toString();
                        String Curp=CtrlSnap.child(Alumno.llaves[3]).getValue().toString();
                        String Tel=CtrlSnap.child(Alumno.llaves[4]).getValue().toString();
                        String Correo=CtrlSnap.child(Alumno.llaves[5]).getValue().toString();
                        String Usuario=CtrlSnap.child(Alumno.llaves[6]).getValue().toString();
                        String pas=CtrlSnap.child(Alumno.llaves[7]).getValue().toString();
                        CargarDatos.add(new Alumno(id,Nombre,Ape,Curp,Tel,Correo,Usuario,pas));*/
                        Log.d("Datos>>", "");
                    }

                    // miAdaptadorUsuario.notifyDataSetChanged();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    /*trim.isemty pregunta si la cadena esta vacia devuelve true, pero si tiene algo retorna false
     * Modificamos el metodo si mi cadena tiene Texto, devuelveme true
     * si no tiene texto devuelveme false*/
    public static boolean ValidarVacio(String Cadena) {
        return (Cadena.trim().isEmpty());
    }

    public static boolean ValidarCorreo(String val) {
        String Co = "[a-zA-Z0-9]+[-_.]*[a-zA-Z0-9]+\\@[a-zA-Z]+\\.[a-zA-Z]+";
        return (val.matches(Co) ? true : false);
    }

    public static boolean ValidaTelefono(String num) {
        return (num.matches("(\\+?[0-9]{2,3}\\-)?([0-9]{10})") ? true : false);

    }

    public static String DevolverError(String cadena) {
        String[] Errores = {"El formato del token personalizado es incorrecto. Consulte la documentación",
                "El token personalizado corresponde a un público diferente",
                "La credencial de autenticación proporcionada tiene un formato incorrecto o ha caducado",
                "La dirección de correo electrónico está mal formateada",
                "La contraseña no es válida o el usuario no tiene una contraseña",
                "Las credenciales proporcionadas no corresponden al usuario que inició sesión anteriormente",
                "Esta operación es confidencial y requiere autenticación reciente. Inicie sesión nuevamente antes de volver a intentar esta solicitud",
                "Ya existe una cuenta con la misma dirección de correo electrónico pero con credenciales de inicio de sesión diferentes. Inicie sesión con un proveedor asociado con esta dirección de correo electrónico",
                "La dirección de correo electrónico ya está en uso por otra cuenta",
                "Esta credencial ya está asociada con una cuenta de usuario diferente",
                "La cuenta de usuario ha sido deshabilitada por un administrador",
                "La credencial del usuario ya no es válida. El usuario debe iniciar sesión nuevamente",
                "No hay registro de usuario correspondiente a este identificador. El usuario puede haber sido eliminado",
                "La credencial del usuario ya no es válida. El usuario debe iniciar sesión nuevamente",
                "Esta operación no está permitida. Debe habilitar este servicio en la consola",
                "La contraseña dada no es válida"};
        int x = -1;
        switch (cadena) {
            case "ERROR_INVALID_CUSTOM_TOKEN":
                x = 0;
                break;
            case "ERROR_CUSTOM_TOKEN_MISMATCH":
                x = 1;
                break;
            case "ERROR_INVALID_CREDENTIAL":
                x = 2;
                break;
            case "ERROR_INVALID_EMAIL":
                x = 3;
                break;
            case "ERROR_WRONG_PASSWORD":
                x = 4;
                break;
            case "ERROR_USER_MISMATCH":
                x = 5;
                break;
            case "ERROR_REQUIRES_RECENT_LOGIN":
                x = 6;
                break;
            case "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL":
                x = 7;
                break;
            case "ERROR_EMAIL_ALREADY_IN_USE":
                x = 8;
                break;
            case "ERROR_CREDENTIAL_ALREADY_IN_USE":
                x = 9;
                break;
            case "ERROR_USER_DISABLED":
                x = 10;
                break;
            case "ERROR_USER_TOKEN_EXPIRED":
                x = 11;
                break;
            case "ERROR_USER_NOT_FOUND":
                x = 12;
                break;
            case "ERROR_INVALID_USER_TOKEN":
                x = 13;
                break;
            case "ERROR_OPERATION_NOT_ALLOWED":
                x = 14;
                break;
            case "ERROR_WEAK_PASSWORD":
                x = 15;
                break;
            default:
                x = 16;

        }
        return (x != 16) ? Errores[x] : cadena;
    }

}
