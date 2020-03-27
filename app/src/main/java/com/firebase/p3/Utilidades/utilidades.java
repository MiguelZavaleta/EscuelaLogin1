package com.firebase.p3.Utilidades;

import android.app.Activity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.ProviderQueryResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class utilidades {
    public static FirebaseDatabase Database;
    public static DatabaseReference DataB_Reference;
    public static FirebaseAuth mAuth;

    public static List<Alumno> Usuarios;
    public static void IniciarVariablesDB(Activity act){
        FirebaseApp.initializeApp(act);
        Database = FirebaseDatabase.getInstance();
        mAuth=FirebaseAuth.getInstance();
        DataB_Reference= Database.getReference();
    }
    public static boolean ValidarCorreo(String val){
        String Co="[a-zA-Z0-9]+[-_.]*[a-zA-Z0-9]+\\@[a-zA-Z]+\\.[a-zA-Z]+";
        return (val.matches(Co)?true:false);
    }
public static boolean ValidaTelefono(String num){
        return(num.matches("(\\+?[0-9]{2,3}\\-)?([0-9]{10})")?true:false);

}
    private boolean VerificarUsuarioFirebase(String email) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final boolean[] b = new boolean[1];
        mAuth.fetchProvidersForEmail(email).addOnCompleteListener(new OnCompleteListener<ProviderQueryResult>() {
            @Override
            public void onComplete( Task<ProviderQueryResult> task) {
                b[0] = !task.getResult().getProviders().isEmpty();
            }
        });
        return b[0];
    }
}
