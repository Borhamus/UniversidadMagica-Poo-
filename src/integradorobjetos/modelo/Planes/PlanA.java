/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package integradorobjetos.modelo.Planes;

import integradorobjetos.modelo.Alumno;
import integradorobjetos.modelo.Carrera;
import integradorobjetos.modelo.Materia;
import integradorobjetos.modelo.Plan;

/**
 *
 * @author Borhamus
 */
public class PlanA extends Plan {
    public PlanA() {
        super("Plan A: Aprobó cursadas de correlativas");
    }
    
    @Override
    public boolean puedeCursar(Alumno alumno, Materia materia) {
        // Verificar que el alumno tenga aprobadas las cursadas de todas las correlativas
        for (Materia correlativa : materia.getCorrelativas()) {
            if (!alumno.tieneCursadaAprobada(correlativa)) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public boolean puedePromocionar(Alumno alumno, Materia materia) {
        // Para promocionar en Plan A, se aplican las mismas condiciones que para cursar
        return puedeCursar(alumno, materia);
    }
}
