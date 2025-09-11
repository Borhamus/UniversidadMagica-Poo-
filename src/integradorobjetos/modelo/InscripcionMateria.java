/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package integradorobjetos.modelo;

/**
 *
 * @author Borhamus
 */
public class InscripcionMateria {
    // Atributos
    private Alumno alumno;
    private Materia materia;
    private EstadoInscripcion estado;
    
    // Constructor
    public InscripcionMateria(Alumno alumno, Materia materia) {
        this.alumno = alumno;
        this.materia = materia;
        this.estado = EstadoInscripcion.INSCRIPTO;
    }
    
    // Métodos
    public void setEstado(EstadoInscripcion nuevoEstado) {
        this.estado = nuevoEstado;
    }
    
    public void aprobarCursada() {
        this.estado = EstadoInscripcion.CURSADA_APROBADA;
    }
    
    public void promocionar() {
        if (estado == EstadoInscripcion.INSCRIPTO) {
            this.estado = EstadoInscripcion.PROMOCIONADO;
        } else {
            throw new IllegalStateException("Solo se puede promocionar desde el estado INSCRIPTO");
        }
    }
    
    public void aprobarFinal() {
        this.estado = EstadoInscripcion.FINAL_APROBADO;
    }
    
    
    // Getters
    public Alumno getAlumno() {
        return alumno;
    }
    
    public Materia getMateria() {
        return materia;
    }
    
    public EstadoInscripcion getEstado() {
        return estado;
    }
}