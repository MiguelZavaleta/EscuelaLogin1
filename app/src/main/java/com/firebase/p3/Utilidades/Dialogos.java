package com.firebase.p3.Utilidades;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class Dialogos extends AppCompatDialogFragment {
Activity actividad;
String Titulo,Contenido,btn1,btn2;

    public Dialogos(Activity actividad, String titulo, String contenido, String btn1, String btn2) {
        this.actividad = actividad;
        Titulo = titulo;
        Contenido = contenido;
        this.btn1 = btn1;
        this.btn2 = btn2;
    }

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Context context;
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setTitle(Titulo)
                .setMessage("This is a Dialog")
                .setPositiveButton(btn1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                    }
                }).setNegativeButton(btn2, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        return super.onCreateDialog(savedInstanceState);
    }
}
