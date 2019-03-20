package com.haki.agendaayacucho.Modelos;

import java.io.Serializable;

public class RegistroFalta implements Serializable {
    private String id_user,grado,paralelo,nombre_asignatura,nombrecompleto,fecha,descripcion,id_fal_com;

    public RegistroFalta(String id_user, String grado, String paralelo, String nombre_asignatura, String nombrecompleto, String fecha, String descripcion, String id_fal_com) {
        this.id_user = id_user;
        this.grado = grado;
        this.paralelo = paralelo;
        this.nombre_asignatura = nombre_asignatura;
        this.nombrecompleto = nombrecompleto;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.id_fal_com = id_fal_com;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getGrado() {
        return grado;
    }

    public void setGrado(String grado) {
        this.grado = grado;
    }

    public String getParalelo() {
        return paralelo;
    }

    public void setParalelo(String paralelo) {
        this.paralelo = paralelo;
    }

    public String getNombre_asignatura() {
        return nombre_asignatura;
    }

    public void setNombre_asignatura(String nombre_asignatura) {
        this.nombre_asignatura = nombre_asignatura;
    }

    public String getNombrecompleto() {
        return nombrecompleto;
    }

    public void setNombrecompleto(String nombrecompleto) {
        this.nombrecompleto = nombrecompleto;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getId_fal_com() {
        return id_fal_com;
    }

    public void setId_fal_com(String id_fal_com) {
        this.id_fal_com = id_fal_com;
    }
}
