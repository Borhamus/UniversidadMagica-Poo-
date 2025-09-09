/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package integradorobjetos.controlador;

import integradorobjetos.modelo.Alumno;
import integradorobjetos.modelo.Carrera;
import java.util.List;

/**
 *
 * @author Borhamus
 */
public class CarreraService {
    
    public boolean inscribirAlumnoEnCarrera(Alumno alumno, Carrera carrera) {
        // Verificar que el alumno no tenga carrera activa
        if (alumno.getCarreraInscripta() != null) {
            return false;
        }

        // Realizar la inscripción
        alumno.inscribirCarrera(carrera);
        return true;
    }
    
    public boolean terminarCarreraAlumno(Alumno alumno) {
        try {
            alumno.terminarCarreraActual();
            return true;
        } catch (IllegalStateException e) {
            return false;
        }
    }
    
    public List<Carrera> obtenerCarrerasTerminadas(Alumno alumno) {
        return alumno.getCarrerasTerminadas();
    }
    
    public boolean puedeInscribirseEnOtraCarrera(Alumno alumno) {
        return alumno.puedeInscribirseEnOtraCarrera();
    }
}