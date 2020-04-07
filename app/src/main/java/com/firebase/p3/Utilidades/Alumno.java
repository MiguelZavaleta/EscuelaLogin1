package com.firebase.p3.Utilidades;

import java.util.HashMap;
import java.util.Map;

public class Alumno {

    private String id,nombre, apellidos,curp,tel,correo,usuario,pass;
    public static String [] llaves={"ID","Nombre","Apellidos","Curp","Telefono","Correo","Usuario","Contra"};
    public Alumno(String id,String nombre, String apellidos, String curp, String tel, String correo, String usuario, String pass) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.curp = curp;
        this.tel = tel;
        this.correo = correo;
        this.usuario = usuario;
        this.pass = pass;
    }

public String Imprimir(){
        return getNombre()+"\n"
                +getApellidos()+"\n"+
    getCurp()+"\n"+
    getTel()+"\n"+
    getCorreo()+"\n"+
    getUsuario()+"\n"+
    getPass();
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
public boolean ValidarCampos(){
    Map<String, Object> Validar =toMap();
    boolean bandera=false;

    int i=1;
    while(i<llaves.length-1){
        if(!(Validar.get(llaves[i]).toString().trim()).isEmpty()){
          bandera=true;

        }else{
            bandera=false;
            System.out.println("<<CAMPO VACIO == "+llaves[i]);
            break;
                  }
                i++;
    }
   /*if(ValidarCorreo(getCorreo())){
        bandera=true;
        System.out.println("Correo correcto");
    }else{
        bandera=false;
        System.out.println("Correo InCorrecto");
    }
    if( ValidaTelefono(getTel())){
        bandera=true;//ninguno esta vacio
        System.out.println("Telefono Correcto");

    }else{
        bandera=false;
        System.out.println("Telefono InCorrecto");

    }*/
    return bandera;
}
    public boolean ValidarCorreo(String val){
        String Co="[a-zA-Z0-9]+[-_.]*[a-zA-Z0-9]+\\@[a-zA-Z]+\\.[a-zA-Z]+";
        return (val.matches(Co)?true:false);
    }
    public  boolean ValidaTelefono(String num){
        return(num.matches("(\\+?[0-9]{2,3}\\-)?([0-9]{10})")?true:false);

    }
}
