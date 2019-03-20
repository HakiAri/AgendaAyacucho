package com.haki.agendaayacucho.Modelos;

import java.io.Serializable;

public class Falta implements Serializable {

    private int id_falta, estado;
    private String tipoFalta, descripcion;

    public Falta(int id_falta, int estado, String tipoFalta, String descripcion) {
        this.id_falta = id_falta;
        this.estado = estado;
        this.tipoFalta = tipoFalta;
        this.descripcion = descripcion;
    }

    public int getId_falta() {
        return id_falta;
    }

    public void setId_falta(int id_falta) {
        this.id_falta = id_falta;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getTipoFalta() {
        return tipoFalta;
    }

    public void setTipoFalta(String tipoFalta) {
        this.tipoFalta = tipoFalta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}