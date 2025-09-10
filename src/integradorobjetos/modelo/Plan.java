/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package integradorobjetos.modelo;

/**
 *
 * @author Borhamus
 */
public abstract class Plan {
    protected String nombre;
    
    public Plan(String nombre) {
        this.nombre = nombre;
    }
    
    public abstract boolean puedeCursar(Alumno alumno, Materia materia);
    public abstract boolean puedePromocionar(Alumno alumno, Materia materia);
    
    public String getNombre() {
        return nombre;
    }
}

