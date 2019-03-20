package com.haki.agendaayacucho.Modelos;

import java.io.Serializable;

public class Curso implements Serializable {
    private int id_curso;
    private String grado, paralelo, materia;
    private int id_asignatura;

    public Curso(int id_curso, String grado, String paralelo, String materia, int id_asignatura) {
        this.id_curso = id_curso;
        this.grado = grado;
        this.paralelo = paralelo;
        this.materia = materia;
        this.id_asignatura = id_asignatura;
    }

    public int getId_curso() {
        return id_curso;
    }

    public void setId_curso(int id_curso) {
        this.id_curso = id_curso;
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

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public int getId_asignatura() {
        return id_asignatura;
    }

    public void setId_asignatura(int id_asignatura) {
        this.id_asignatura = id_asignatura;
    }
}
