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
public class PlanB extends Plan {
    public PlanB() {
        super("Plan B: Aprobó finales de correlativas");
    }
    
    @Override
    public boolean puedeCursar(Alumno alumno, Materia materia) {
        // Verificar que el alumno tenga aprobados los finales de todas las correlativas
        for (Materia correlativa : materia.getCorrelativas()) {
            if (!alumno.tieneFinalAprobado(correlativa)) {
                return false;
            }
        }
        return true;
    }
    
    // PlanB - Requiere finales aprobados de correlativas
    @Override
    public boolean puedePromocionar(Alumno alumno, Materia materia) {
        // Verificar que el alumno tenga aprobados los finales de todas las correlativas
        for (Materia correlativa : materia.getCorrelativas()) {
            if (!alumno.tieneFinalAprobado(correlativa)) {
                return false;
            }
        }
        return true;
    }
}