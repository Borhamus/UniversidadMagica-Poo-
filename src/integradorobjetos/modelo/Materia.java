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
public class Materia {
    // Atributos
    private String nombre;
    private String contenido;
    private int cargaHoraria;
    private int cuatrimestre;
    private String profesor;
    private boolean esOptativa;
    private List<Materia> correlativas;
    private boolean esPromocionable;
    
    // Constructor
    public Materia(String nombre, String contenido, int cargaHoraria, int cuatrimestre, String profesor) {
        this.nombre = nombre;
        this.contenido = contenido;
        this.cargaHoraria = cargaHoraria;
        this.cuatrimestre = cuatrimestre;
        this.profesor = profesor;
        this.correlativas = new ArrayList<>();
        this.esOptativa = false; // Por defecto no es optativa
    }
    
    // Métodos
    public void agregarCorrelativa(Materia materia) {
        if (!correlativas.contains(materia)) {
            correlativas.add(materia);
        }
    }
    
    public boolean esPromocionable() {
        return esPromocionable;
    }
    
    public void setPromocionable(boolean promocionable) {
        this.esPromocionable = promocionable;
    }
    
    public void setOptativa(boolean esOptativa) {
        this.esOptativa = esOptativa;
    }
    
    // Getters & Setters
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getContenido() {
        return contenido;
    }
    
    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
    
    public int getCargaHoraria() {
        return cargaHoraria;
    }
    
    public void setCargaHoraria(int cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }
    
    public int getCuatrimestre() {
        return cuatrimestre;
    }
    
    public void setCuatrimestre(int cuatrimestre) {
        this.cuatrimestre = cuatrimestre;
    }
    
    public String getProfesor() {
        return profesor;
    }
    
    public void setProfesor(String profesor) {
        this.profesor = profesor;
    }
    
    public boolean esOptativa() {
        return esOptativa;
    }
    
    public List<Materia> getCorrelativas() {
        return correlativas;
    }
    
    @Override
    public String toString() {
        return "Materia{" + "nombre=" + nombre + ", cuatrimestre=" + cuatrimestre + 
               ", profesor=" + profesor + ", esOptativa=" + esOptativa + '}';
    }
}