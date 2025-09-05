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
public class Carrera {
    // Atributos
    private String nombre;
    private int optativasMinimas;
    private Plan planDeEstudio;
    private List<Materia> materiasDeLaCarrera;
    
    // Constructor
    public Carrera(String nombre, int optativasMinimas) {
        this.nombre = nombre;
        this.optativasMinimas = optativasMinimas;
        this.materiasDeLaCarrera = new ArrayList<>();
    }
    
    // Métodos
    
    // Método mejorado para verificar graduación
    public boolean verificarGraduacion(Alumno alumno) {
        if (alumno.getCarreraInscripta() != this) {
            return false;
        }
        
        int obligatoriasAprobadas = 0;
        int optativasAprobadas = 0;
        int totalObligatorias = 0;
        
        for (Materia materia : materiasDeLaCarrera) {
            if (materia.esOptativa()) {
                if (alumno.tieneFinalAprobado(materia)) {
                    optativasAprobadas++;
                }
            } else {
                totalObligatorias++;
                if (alumno.tieneFinalAprobado(materia)) {
                    obligatoriasAprobadas++;
                }
            }
        }
        
        boolean cumpleObligatorias = obligatoriasAprobadas == totalObligatorias;
        boolean cumpleOptativas = optativasAprobadas >= optativasMinimas;
        
        return cumpleObligatorias && cumpleOptativas;
    }
    
    public void agregarMateria(Materia materia) {
        materiasDeLaCarrera.add(materia);
    }
    
    public Materia crearMateria(String nombre, String contenido, int cargaHoraria, 
                               int cuatrimestre, String profesor) {
        Materia materia = new Materia(nombre, contenido, cargaHoraria, cuatrimestre, profesor);
        materiasDeLaCarrera.add(materia);
        return materia;
    }
    
    
    public List<Materia> materiasDelPeriodo(int cuatriMin, int cuatriMax) {
        List<Materia> resultado = new ArrayList<>();
        for (Materia materia : materiasDeLaCarrera) {
            int cuatrimestre = materia.getCuatrimestre();
            if (cuatrimestre >= cuatriMin && cuatrimestre < cuatriMax) {
                resultado.add(materia);
            }
        }
        return resultado;
    }
    
    // Patrón Strategy
    public boolean puedeCursar(Alumno alumno, Materia materia) {
        return planDeEstudio.puedeCursar(alumno, materia);
    }
    
    public void seleccionarPlanDeEstudio(Plan plan) {
        this.planDeEstudio = plan;
    }
    
    // Getters & Setters
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public int getOptativasMinimas() {
        return optativasMinimas;
    }
    
    public void setOptativasMinimas(int optativasMinimas) {
        this.optativasMinimas = optativasMinimas;
    }
    
    public Plan getPlanDeEstudio() {
        return planDeEstudio;
    }
    
    public void setPlanDeEstudio(Plan planDeEstudio) {
        this.planDeEstudio = planDeEstudio;
    }
    
    public List<Materia> getMaterias() {
        return materiasDeLaCarrera;
    }
    
    @Override
    public String toString() {
        return "Carrera{" + "nombre=" + nombre + ", optativasMinimas=" + optativasMinimas + 
               ", planDeEstudio=" + planDeEstudio + '}';
    }
}