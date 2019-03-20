package com.haki.agendaayacucho.Modelos;

public class FaltaHijo {
    private int gestion;
    private String materia, tipofalta,descripcion,observacion,fecha;

    public FaltaHijo(int gestion, String materia, String tipofalta, String descripcion, String observacion, String fecha) {
        this.gestion = gestion;
        this.materia = materia;
        this.tipofalta = tipofalta;
        this.descripcion = descripcion;
        this.observacion = observacion;
        this.fecha = fecha;
    }

    public int getGestion() {
        return gestion;
    }

    public void setGestion(int gestion) {
        this.gestion = gestion;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public String getTipofalta() {
        return tipofalta;
    }

    public void setTipofalta(String tipofalta) {
        this.tipofalta = tipofalta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
