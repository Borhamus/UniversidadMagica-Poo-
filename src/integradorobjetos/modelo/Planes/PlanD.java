/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package integradorobjetos.modelo.Planes;

import integradorobjetos.modelo.Alumno;
import integradorobjetos.modelo.Carrera;
import integradorobjetos.modelo.Materia;
import integradorobjetos.modelo.Plan;
import java.util.List;

/**
 *
 * @author Borhamus
 */
public class PlanD extends Plan {
    public PlanD() {
        super("Plan D: Aprobó cursadas de correlativas y finales de 3 cuatrimestres previos");
    }
    
    @Override
    public boolean puedeCursar(Alumno alumno, Materia materia) {
        // Primero verificar correlativas (igual que Plan A)
        for (Materia correlativa : materia.getCorrelativas()) {
            if (!alumno.tieneCursadaAprobada(correlativa)) {
                return false;
            }
        }
        
        // Luego verificar finales de 3 cuatrimestres previos
        int cuatrimestreActual = materia.getCuatrimestre();
        int cuatrimestreMinimo = Math.max(1, cuatrimestreActual - 3);
        
        return verificarFinalesCuatrimestresPrevios(alumno, cuatrimestreMinimo, cuatrimestreActual - 1);
    }
    
    private boolean verificarFinalesCuatrimestresPrevios(Alumno alumno, int cuatriMin, int cuatriMax) {
        // Obtener todas las materias de la carrera del alumno en el rango de cuatrimestres
        List<Materia> materiasPrevias = alumno.getCarreraInscripta().materiasDelPeriodo(cuatriMin, cuatriMax + 1);
        
        // Verificar que tenga aprobado el final de cada una
        for (Materia materiaPrevia : materiasPrevias) {
            if (!alumno.tieneFinalAprobado(materiaPrevia)) {
                return false;
            }
        }
        return true;
    }
}