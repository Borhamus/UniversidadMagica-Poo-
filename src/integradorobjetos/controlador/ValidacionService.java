/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package integradorobjetos.controlador;

import integradorobjetos.modelo.Alumno;
import integradorobjetos.modelo.Materia;

/**
 *
 * @author Borhamus
 */
public class ValidacionService {
    public boolean puedeCursarMateria(Alumno alumno, Materia materia) {
        if (alumno.getCarreraInscripta() == null) {
            return false;
        }
        
        return alumno.getCarreraInscripta().puedeCursar(alumno, materia);
    }
    
    public boolean finalizoCarrera(Alumno alumno) {
        if (alumno.getCarreraInscripta() == null) {
            return false;
        }
        
        return alumno.getCarreraInscripta().verificarGraduacion(alumno);
    }
}