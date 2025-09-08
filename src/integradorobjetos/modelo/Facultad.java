package integradorobjetos.modelo;
import java.util.*;

/**
 *
 * @author Borhamus
 */
public class Facultad {
    private static Facultad instancia;
    private String nombre;
    private List<Carrera> carreras;
    private List<Alumno> alumnos;
    
    private Facultad() {
        carreras = new ArrayList<>();
        alumnos = new ArrayList<>();
    }
    
    public static Facultad getInstance() {
        if (instancia == null) {
            instancia = new Facultad();
        }
        return instancia;
    }
    
    public void eliminarCarrera(Carrera carrera) {
        carreras.remove(carrera);
    }
    
    public boolean eliminarAlumno(Alumno alumno) {
        if (alumno != null) {
            return alumnos.remove(alumno);
        }
        return false;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public List<Carrera> getCarreras() {
        return carreras;
    }
    
    public List<Alumno> getAlumnos() {
        return alumnos;
    }
    
    // Método para crear y agregar una carrera
    public Carrera crearCarrera(String nombre, int optativasMinimas) {
        Carrera carrera = new Carrera(nombre, optativasMinimas);
        carreras.add(carrera);
        return carrera;
    }
    
    // Método corregido para crear y agregar un alumno
    public void crearAlumno(String nombre, String dni) {
        Alumno alumno = new Alumno(nombre, dni);
        alumnos.add(alumno);
    }
    
    public Alumno buscarAlumno(String nombre) {
        for (Alumno alumno : alumnos) {
            if (alumno.getNombre().equals(nombre)) {
                return alumno;
            }
        }
        return null;
    }
    
    // Nuevo método: buscar alumno por DNI
    public Alumno buscarAlumnoPorDni(String dni) {
        if (dni == null || dni.isEmpty()) {
            return null;
        }
        
        for (Alumno alumno : alumnos) {
            if (alumno.getDni() != null && alumno.getDni().equals(dni)) {
                return alumno;
            }
        }
        return null;
    }
    
    // Nuevo método: eliminar alumno por DNI
    public boolean eliminarAlumnoPorDni(String dni) {
        Alumno alumno = buscarAlumnoPorDni(dni);
        if (alumno != null) {
            return alumnos.remove(alumno);
        }
        return false;
    }
    
    public Carrera buscarCarrera(String nombre) {
        for (Carrera carrera : carreras) {
            if (carrera.getNombre().equals(nombre)) {
                return carrera;
            }
        }
        return null;
    }
}