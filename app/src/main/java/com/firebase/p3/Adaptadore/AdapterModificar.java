package com.firebase.p3.Adaptadore;

import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.p3.R;
import com.firebase.p3.Utilidades.Alumno;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdapterModificar extends BaseAdapter {
    public static List<String> Respuestas;
    public static Alumno objeto;
    public static boolean pivote;
    int longitud = 0;
    TextView llave;
    EditText Campo;
    LayoutInflater inflter;
    Activity actividad;
    View vista;
    String id;

    public AdapterModificar(Activity act, int l, String id, List<String> Array) {
        this.Respuestas = Array;
        this.id = id;
        this.longitud = l;
        this.actividad = act;
        inflter = (LayoutInflater.from(actividad));
        notifyDataSetChanged();

    }

    @Override
    public int getCount() {
        return longitud;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        vista = inflter.inflate(R.layout.row, null);
        final int pos = position;//iterador de poscision
        llave = (TextView) vista.findViewById(R.id.idLLave);//declaramos campos de row.xml
        Campo = (EditText) vista.findViewById(R.id.idCampo);
        llave.setText(Alumno.llaves[pos + 1]);//obtenemos el texto
        Campo.setText(Respuestas.get(pos));
        Campo.setSelection(Campo.length());//
        if (llave.getText().toString().equalsIgnoreCase("correo")) {//bloqueamos el Campo Correo Electronico para que no se pueda Modificar
            Campo.setEnabled(false);
            Campo.setBackgroundColor(vista.getResources().getColor(R.color.md_red_100));
        }
        //Evento de detecrtar Caracteres en nuestra Caja de Texto:
        Campo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // System.out.println("Antes de>>"+s.toString() + " " + start + " " + count + " " + after);

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {//evento mientras escribimos
                // System.out.println("Durante>>" + s.toString() + " " + start + " " + count);
                Respuestas.set(pos, s.toString());//cada que escribimos vamos modificando kla poscision del elemento
                CargarObjeto();//carga,mos el objeto

            }

            @Override
            public void afterTextChanged(Editable s) {
                // System.out.println(pos + "Despues de>>" + s.toString());
                /*Respuestas.set(pos,s.toString());
                CargarObjeto();/*/
            }

        });
        CargarObjeto();
        Alerta(pos, Respuestas.get(pos));
        return vista;
    }

    void Alerta(int x, String s) {//alertas de campos incorrectos
        switch (x) {
            case 3:

                if (!objeto.ValidaTelefono(s)) {
                    System.out.println("Valor " + pivote);
                    Campo.setError("Telefono incorrecto");
                    Campo.requestFocus();
                    pivote = objeto.ValidaTelefono(s);
                } else {
                    pivote = objeto.ValidaTelefono(s);
                }
                break;
            case 4:
                if (!objeto.ValidarCorreo(s)) {
                    System.out.println("Valor " + pivote);
                    Campo.setError("Correo incorrecto");
                    Campo.requestFocus();
                    pivote = false;
                } else {
                    pivote = true;
                }

                break;
        }
    }

    void CargarObjeto() {
        objeto = new Alumno();
        objeto.setNombre(Respuestas.get(0));
        objeto.setApellidos(Respuestas.get(1));
        objeto.setCurp(Respuestas.get(2));
        objeto.setTel(Respuestas.get(3));
        objeto.setCorreo(Respuestas.get(4));
        objeto.setUsuario(Respuestas.get(5));
        objeto.setPass(Respuestas.get(6));
    }

}
