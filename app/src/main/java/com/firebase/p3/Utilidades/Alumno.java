package com.firebase.p3.Utilidades;

import java.util.HashMap;
import java.util.Map;

public class Alumno {
    private String id,nombre, apellidos,curp,tel,correo,usuario,pass;

    public Alumno(String nombre, String apellidos, String curp, String tel, String correo, String usuario, String pass) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.curp = curp;
        this.tel = tel;
        this.correo = correo;
        this.usuario = usuario;
        this.pass = pass;
    }

    @Override
    public String toString() {
        return "Nombre Alumno:" + getNombre();
    }

    public Alumno(){};
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("ID",getId());
        result.put("Nombre", getNombre());
        result.put("Apellidos", getApellidos());
        result.put("Curp", getCurp());
        result.put("Telefono", getTel());
        result.put("Correo", getCorreo());
        result.put("Usuario", getUsuario());
        result.put("Contra", getPass());

        return result;
    }

}
