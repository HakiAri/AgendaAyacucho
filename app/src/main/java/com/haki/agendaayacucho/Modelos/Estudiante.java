package com.haki.agendaayacucho.Modelos;

import java.io.Serializable;

public class Estudiante implements Serializable {

    String nombre, paterno,materno,sexo,estado;
    int id_rude, id_kardex;
    String curso;

    public Estudiante(String nombre, String paterno, String materno, String sexo, String estado, int id_rude, int id_kardex, String curso) {
        this.nombre = nombre;
        this.paterno = paterno;
        this.materno = materno;
        this.sexo = sexo;
        this.estado = estado;
        this.id_rude = id_rude;
        this.id_kardex = id_kardex;
        this.curso = curso;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPaterno() {
        return paterno;
    }

    public void setPaterno(String paterno) {
        this.paterno = paterno;
    }

    public String getMaterno() {
        return materno;
    }

    public void setMaterno(String materno) {
        this.materno = materno;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getId_rude() {
        return id_rude;
    }

    public void setId_rude(int id_rude) {
        this.id_rude = id_rude;
    }

    public int getId_kardex() {
        return id_kardex;
    }

    public void setId_kardex(int id_kardex) {
        this.id_kardex = id_kardex;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }
}
