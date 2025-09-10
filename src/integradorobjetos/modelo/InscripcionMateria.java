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
    public void aprobarCursada() {
        this.estado = EstadoInscripcion.CURSADA_APROBADA;
    }
    
    public void aprobarFinal() {
        this.estado = EstadoInscripcion.FINAL_APROBADO;
    }
    
    public void promocionar() {
        this.estado = EstadoInscripcion.PROMOCIONADO;
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