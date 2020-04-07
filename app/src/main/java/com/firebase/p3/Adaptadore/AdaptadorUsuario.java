package com.firebase.p3.Adaptadore;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.p3.R;
import com.firebase.p3.Utilidades.Alumno;
import com.firebase.p3.Utilidades.utilidades;

import java.util.LinkedList;
import java.util.List;

public class AdaptadorUsuario extends RecyclerView.Adapter<AdaptadorUsuario.ViewHolderJugador> implements View.OnClickListener {

    private View.OnClickListener listener;
    List<Alumno> ListaUsuario;
    View vista;
    Activity Actividad;
    LinkedList <Alumno>lista;
    public static int IndexEliminar=-1;
    int posicionMarcada=0;
    public AdaptadorUsuario(List<Alumno> listaJugador, Activity ac) {
        this.ListaUsuario = listaJugador;
        this.Actividad=ac;
    }

    @NonNull
    @Override
    public ViewHolderJugador onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        vista= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_usuario,viewGroup,false);
        vista.setOnClickListener(this);
        return new ViewHolderJugador(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderJugador viewHolderJugador, int i) {
        final int pos = i;
        //se resta uno ya que buscamos la lista de elementos que inicia en la pos 0
        viewHolderJugador.imgAvatar.setImageResource(R.drawable.birrete);
        viewHolderJugador.txtNombre.setText(ListaUsuario.get(i).getId());//correo del usuario
        viewHolderJugador.txtGenero.setText(ListaUsuario.get(i).getCorreo());

viewHolderJugador.cardAvatar.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        IndexEliminar=pos;

        utilidades.AlumnoSeleccionado = utilidades.Usuarios.get(IndexEliminar);
        Toast.makeText(Actividad, "Tocado"+IndexEliminar
                +"\nY el Id es:"+utilidades.AlumnoSeleccionado.getId(), Toast.LENGTH_SHORT).show();
       // notifyDataSetChanged();
    }
});
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return ListaUsuario.size();
    }

    @Override
    public void onClick(View view) {
        if(listener!=null){
            listener.onClick(view);
        }
    }

    public class ViewHolderJugador extends RecyclerView.ViewHolder {
        CardView cardAvatar;
        ImageView imgAvatar;
        TextView txtNombre;
        TextView txtGenero;

        public ViewHolderJugador(@NonNull View itemView) {
            super(itemView);
            imgAvatar=itemView.findViewById(R.id.idAvatar);
            txtNombre=itemView.findViewById(R.id.idNombre);
            txtGenero=itemView.findViewById(R.id.idDes);
            cardAvatar=itemView.findViewById(R.id.cardAvatar);
        }

    }
}
