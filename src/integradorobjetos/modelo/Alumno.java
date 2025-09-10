/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package integradorobjetos.modelo;

import java.util.*;

/**
 *
 * @author Borhamus
 */
public class Alumno {
    private String nombre;
    private String dni;
    private Carrera carreraInscripta; // Carrera activa actual
    private List<Carrera> carrerasTerminadas; // Historial de carreras completadas
    private List<InscripcionMateria> inscripciones;
    
    public Alumno(String nombre, String dni) {
        this.nombre = nombre;
        this.dni = dni;
        this.carrerasTerminadas = new ArrayList<>();
        this.inscripciones = new ArrayList<>();
    }
    
    public void setCarreraInscripta(Carrera carrera) {
        this.carreraInscripta = carrera;
    }   
    
    // Método para inscribirse en una nueva carrera
    public void inscribirCarrera(Carrera carrera) {
        if (carreraInscripta != null) {
            throw new IllegalStateException("El alumno ya tiene una carrera activa");
        }
        this.carreraInscripta = carrera;
    }
    
    public boolean tieneCursadaAprobada(Materia materia) {
        for (InscripcionMateria inscripcion : inscripciones) {
            if (inscripcion.getMateria().equals(materia)) {
                return inscripcion.getEstado() == EstadoInscripcion.CURSADA_APROBADA ||
                       inscripcion.getEstado() == EstadoInscripcion.FINAL_APROBADO ||
                       inscripcion.getEstado() == EstadoInscripcion.PROMOCIONADO;
            }
        }
        return false;
    }
    
    public boolean tieneFinalAprobado(Materia materia) {
        for (InscripcionMateria inscripcion : inscripciones) {
            if (inscripcion.getMateria().equals(materia)) {
                return inscripcion.getEstado() == EstadoInscripcion.FINAL_APROBADO ||
                       inscripcion.getEstado() == EstadoInscripcion.PROMOCIONADO;
            }
        }
        return false;
    }
    
    // Método para terminar la carrera actual
    public void terminarCarreraActual() {
        if (carreraInscripta == null) {
            throw new IllegalStateException("El alumno no tiene una carrera activa");
        }
        
        if (carreraInscripta.verificarGraduacion(this)) {
            carrerasTerminadas.add(carreraInscripta);
            carreraInscripta = null;
        } else {
            throw new IllegalStateException("El alumno no ha completado todos los requisitos");
        }
    }
    
    public void eliminarInscripcion(InscripcionMateria inscripcion) {
        if (inscripciones.contains(inscripcion)) {
            inscripciones.remove(inscripcion);
        }
    }
    
    // Método para obtener todas las carreras terminadas
    public List<Carrera> getCarrerasTerminadas() {
        return new ArrayList<>(carrerasTerminadas);
    }
    
    // Método para verificar si puede inscribirse en otra carrera
    public boolean puedeInscribirseEnOtraCarrera() {
        return carreraInscripta == null;
    }
    
    
    public void inscribirMateria(Materia materia) {
        InscripcionMateria inscripcion = new InscripcionMateria(this, materia);
        inscripciones.add(inscripcion);
    }
    
    
    // Getters & Setters
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getDni() {
        return dni;
    }
    
    public void setDni(String dni) {
        this.dni = dni;
    }
    
    public Carrera getCarreraInscripta() {
        return carreraInscripta;
    }
    
    public List<InscripcionMateria> getInscripciones() {
        return inscripciones;
    }
    
    @Override
    public String toString() {
        return "Alumno{" + "nombre=" + nombre + ", dni=" + dni + '}';
    }
}