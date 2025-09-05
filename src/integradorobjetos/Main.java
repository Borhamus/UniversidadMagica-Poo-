package integradorobjetos;

import integradorobjetos.modelo.*;
import integradorobjetos.modelo.Planes.*;
import integradorobjetos.vista.App;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.SwingUtilities;

public class Main {
    
    public static void main(String[] args) {
        // Cargar datos iniciales
        cargarDatosIniciales();
        
        // Mostrar interfaz gráfica
        SwingUtilities.invokeLater(() -> {
            App app = new App();
            app.setVisible(true);
        });
    }
    
    public static void cargarDatosIniciales() {
        // Inicializar la facultad
        Facultad facultad = Facultad.getInstance();
        facultad.setNombre("Universidad Mística Arcana");
        
        // Cargar carreras y materias
        cargarCarreras();
        
        // Cargar alumnos
        cargarAlumnos();
        
        // Inscribir algunos alumnos en carreras
        inscribirAlumnosEnCarreras();
        
        // Inscribir algunos alumnos en materias del primer cuatrimestre
        inscribirAlumnosEnMaterias();
    }
    
    public static void mostrarDatosCargados() {
        Facultad facultad = Facultad.getInstance();
        
        System.out.println("===============================================");
        System.out.println("  DATOS CARGADOS EN LA UNIVERSIDAD MÍSTICA ARCANA");
        System.out.println("===============================================");
        System.out.println();
        
        // Mostrar carreras y sus materias
        System.out.println("CARRERAS Y MATERIAS:");
        System.out.println("--------------------");
        for (Carrera carrera : facultad.getCarreras()) {
            System.out.println("\nCARRERA: " + carrera.getNombre());
            System.out.println("Plan de estudio: " + carrera.getPlanDeEstudio().getNombre());
            System.out.println("Optativas mínimas: " + carrera.getOptativasMinimas());
            System.out.println("Materias:");
            
            for (Materia materia : carrera.getMaterias()) {
                System.out.println("  * " + materia.getNombre());
                System.out.println("    - Cuatrimestre: " + materia.getCuatrimestre());
                System.out.println("    - Profesor: " + materia.getProfesor());
                System.out.println("    - Carga horaria: " + materia.getCargaHoraria());
                System.out.println("    - Optativa: " + (materia.esOptativa() ? "Sí" : "No"));
                System.out.println("    - Contenido: " + materia.getContenido());
                
                // Mostrar correlativas
                if (!materia.getCorrelativas().isEmpty()) {
                    System.out.print("    - Correlativas: ");
                    for (Materia correlativa : materia.getCorrelativas()) {
                        System.out.print(correlativa.getNombre() + " ");
                    }
                    System.out.println();
                }
                System.out.println();
            }
        }
        
        // Mostrar alumnos y sus inscripciones
        System.out.println("\n\nALUMNOS Y SUS INSCRIPCIONES:");
        System.out.println("----------------------------");
        for (Alumno alumno : facultad.getAlumnos()) {
            System.out.println("\nALUMNO: " + alumno.getNombre());
            System.out.println("DNI: " + alumno.getDni());
            
            // Mostrar carrera inscripta
            if (alumno.getCarreraInscripta() != null) {
                System.out.println("Carrera inscripta: " + alumno.getCarreraInscripta().getNombre());
            } else {
                System.out.println("Carrera inscripta: Ninguna");
            }
            
            // Mostrar materias inscriptas
            if (!alumno.getInscripciones().isEmpty()) {
                System.out.println("Materias inscriptas:");
                for (InscripcionMateria inscripcion : alumno.getInscripciones()) {
                    System.out.println("  * " + inscripcion.getMateria().getNombre() + 
                                     " (Estado: " + inscripcion.getEstado() + ")");
                }
            } else {
                System.out.println("Materias inscriptas: Ninguna");
            }
        }
        
        System.out.println("\n===============================================");
        System.out.println("  FIN DE LOS DATOS CARGADOS");
        System.out.println("===============================================");
    }
    
    public static void cargarCarreras() {
        Facultad facultad = Facultad.getInstance();
        
        // Nombres de carreras fantásticas
        String[] nombresCarreras = {
            "Magisterio en Hechicería Arcanológica",
            "Arquitectura de Castillos Mágicos",
            "Medicina Draconiana",
            "Derecho Interdimensional",
            "Psicología de Criaturas Fantásticas",
            "Biología de Especies Fantásticas"
        };
        
        // Planes para cada carrera
        Plan[] planes = {
            new PlanA(),
            new PlanB(),
            new PlanC(),
            new PlanD(),
            new PlanE(),
            new PlanA()
        };
        
        // Optativas mínimas para cada carrera
        int[] optativas = {2, 1, 2, 2, 1, 0};
        
        // Crear las carreras
        for (int i = 0; i < nombresCarreras.length; i++) {
            Carrera carrera = facultad.crearCarrera(nombresCarreras[i], optativas[i]);
            carrera.seleccionarPlanDeEstudio(planes[i]);
            
            // Crear 12 materias por carrera (2 por cuatrimestre)
            crearMateriasParaCarrera(carrera, nombresCarreras[i]);
        }
    }
    
    public static void crearMateriasParaCarrera(Carrera carrera, String nombreCarrera) {
    // Materias específicas para cada carrera (2 por cuatrimestre)
    String[][][] materiasPorCarrera = {
        { // Magisterio en Hechicería Arcanológica
            {"Fundamentos Arcanos", "Rituales Básicos"},
            {"Hechizos Defensivos", "Conjuros de Ilusión"},
            {"Necromancia Avanzada", "Magia Elemental"},
            {"Encantamientos", "Control Mental"},
            {"Magia Espacial", "Hechizos Temporales"},
            {"Magia Divina", "Artes Oscuras"}
        },
        { // Arquitectura de Castillos Mágicos
            {"Diseño de Cimientos Mágicos", "Materiales Encantados"},
            {"Estructuras Flotantes", "Protecciones Arcanas"},
            {"Torres de Conjuración", "Salas de Poder"},
            {"Puentes Dimensionales", "Jardines Colgantes"},
            {"Murallas Vivientes", "Sistemas de Defensa"},
            {"Tronos de Poder", "Cámaras del Consejo"}
        },
        { // Medicina Draconiana
            {"Anatomía de Dragones", "Escamas y Huesos"},
            {"Sangre y Veneno", "Aliento de Fuego"},
            {"Cicatrices Mágicas", "Vuelo y Alas"},
            {"Comunicación Dracónica", "Cría de Dragones"},
            {"Rituales de Curación", "Transfusiones"},
            {"Cirugía Arcana", "Implantes Mágicos"}
        },
        { // Derecho Interdimensional
            {"Leyes Fundamentales", "Contratos Dimensionales"},
            {"Tratados Interplanares", "Diplomacia Arcana"},
            {"Jurisprudencia Mágica", "Tribunales Flotantes"},
            {"Derecho de Criaturas", "Leyes de los No-Muertos"},
            {"Propiedad Intelectual Mágica", "Patentes Arcanas"},
            {"Ética Interdimensional", "Códigos de Conducta"}
        },
        { // Psicología de Criaturas Fantásticas
            {"Comportamiento Básico", "Instintos Primarios"},
            {"Miedos y Fobias", "Traumas Mágicos"},
            {"Sueños y Pesadillas", "Conciencia Colectiva"},
            {"Relaciones Interespecies", "Comunicación Animal"},
            {"Terapia de Grupo", "Rituales de Sanación"},
            {"Psicopatología Arcana", "Trastornos Mágicos"}
        },
        { // Biología de Especies Fantásticas
            {"Clasificación de Especies", "Ecosistemas Mágicos"},
            {"Ciclos de Vida", "Reproducción Arcana"},
            {"Adaptaciones Mágicas", "Evolución Rápida"},
            {"Simbiosis y Parasitismo", "Relaciones Especiales"},
            {"Extinción y Renacimiento", "Fósiles Mágicos"},
            {"Genética Arcana", "Manipulación Genética"}
        }
    };
    
    // Profesores para cada carrera
    String[][] profesoresPorCarrera = {
        {"Arquimago Elrond", "Maestra Gandalf", "Drácula el Sabio", "Merlin el Encantador", "Albus Dumbledore", "Voldemort el Erudito"},
        {"Gaudi el Constructor", "Brunelleschi el Hechicero", "Vitruvio Mágico", "Eiffel el Encantador", "Gaudí el Visionario", "Niemeyer el Místico"},
        {"Hipócrates Dracónico", "Galeno el Sabio", "Avicenna el Curandero", "Paracelso el Alquimista", "Pasteur el Sanador", "Fleming el Descubridor"},
        {"Solón Interdimensional", "Hamurabi Mágico", "Justiniano Arcano", "Napoleón el Legislador", "Hugo el Jurista", "Kelsen el Normativo"},
        {"Freud Místico", "Jung el Arcano", "Skinner el Conductista", "Piaget el Evolutivo", "Vygotsky el Social", "Bandura el Observador"},
        {"Darwin Mágico", "Linneo el Clasificador", "Mendel el Genetista", "Lamarck el Evolutivo", "Haeckel el Naturalista", "Dawkins el Moderno"}
    };
    
    // Obtener el índice de la carrera
    int indiceCarrera = -1;
    String[] nombresCarreras = {
        "Magisterio en Hechicería Arcanológica",
        "Arquitectura de Castillos Mágicos",
        "Medicina Draconiana",
        "Derecho Interdimensional",
        "Psicología de Criaturas Fantásticas",
        "Biología de Especies Fantásticas"
    };
    
    for (int i = 0; i < nombresCarreras.length; i++) {
        if (nombresCarreras[i].equals(nombreCarrera)) {
            indiceCarrera = i;
            break;
        }
    }
    
    if (indiceCarrera == -1) return;
    
    // Crear materias y establecer correlativas
    List<Materia> materiasAnteriores = new ArrayList<>();
    
    for (int cuatrimestre = 1; cuatrimestre <= 6; cuatrimestre++) {
        for (int i = 0; i < 2; i++) {
            String nombre = materiasPorCarrera[indiceCarrera][cuatrimestre-1][i];
            String contenido = "Estudio avanzado de " + nombre.toLowerCase() + " para el cuatrimestre " + cuatrimestre;
            int cargaHoraria = 80 + (cuatrimestre * 10);
            String profesor = profesoresPorCarrera[indiceCarrera][cuatrimestre-1];
            
            Materia materia = carrera.crearMateria(nombre, contenido, cargaHoraria, cuatrimestre, profesor);
            
            // Establecer correlativas (materias del cuatrimestre anterior)
            for (Materia anterior : materiasAnteriores) {
                if (anterior.getCuatrimestre() == cuatrimestre - 1) {
                    materia.agregarCorrelativa(anterior);
                }
            }
            
            // Marcar algunas materias como optativas
            if (cuatrimestre % 2 == 0) {
                materia.setOptativa(true);
            }
        }
        // CORRECCIÓN AQUÍ:
        materiasAnteriores.addAll(carrera.materiasDelPeriodo(cuatrimestre, cuatrimestre + 1));
    }
}
    
    public static void cargarAlumnos() {
        Facultad facultad = Facultad.getInstance();
        
        // Nombres de alumnos mágicos
        String[] nombresAlumnos = {
            "Aelricus Von Stormbringer, Señor de las Montañas Heladas",
            "Elorandor Elyndor, Protector de los Bosques Ancestrales",
            "Thalindra Moonshadow, Hija de las Estrellas Caídas",
            "Galadrius Iluvatar, Guerrero del Reino Eterno",
            "Valthorax Firestorm, Portador de la Espada Eterna",
            "Sylas Windrider, Explorador de Tierras Prohibidas",
            "Isolde Darksbane, Hechicera de la Noche Infinita",
            "Baldric Stormhammer, Forjador de Almas Imponentes",
            "Eryndor Silverblade, Guardián de los Templos Perdidos",
            "Aerithiel Brightleaf, Curadora de los Corazones Rotos",
            "Morgrimm Soulrender, Señor de las Sombras Eternas",
            "Lyraelle Stardancer, Sacerdotisa de la Luna Llena",
            "Kaelthas Sunfury, Magister del Fuego Sagrado",
            "Vespera Nightwhisper, Invocadora de Espíritus Antiguos",
            "Thorgar Ironforge, Maestro de Runas de Poder",
            "Elara Moonshadow, Hechicera de las Dimensiones Perdidas",
            "Darian Stormrider, Señor de los Vientos del Norte",
            "Seraphina Lightbringer, Guardiana de la Llama Sagrada",
            "Zephyrus Shadowmend, Sanador de Almas Fragmentadas",
            "Orion Starweaver, Arquitecto de Constelaciones Mágicas"
        };
        
        // Crear DNIs mágicos (como String)
        for (int i = 0; i < nombresAlumnos.length; i++) {
            String dni = "MAGO-" + String.format("%05d", 10000 + i) + "-ARCANA";
            facultad.crearAlumno(nombresAlumnos[i], dni);
        }
    }
    
    public static void inscribirAlumnosEnCarreras() {
        Facultad facultad = Facultad.getInstance();
        Random random = new Random();
        
        // Obtener todos los alumnos y carreras
        List<Alumno> todosLosAlumnos = facultad.getAlumnos();
        List<Carrera> todasLasCarreras = facultad.getCarreras();
        
        // Inscribir 15 de los 20 alumnos en carreras (dejar 5 sin inscribir)
        for (int i = 0; i < 15; i++) {
            Alumno alumno = todosLosAlumnos.get(i);
            Carrera carrera = todasLasCarreras.get(random.nextInt(todasLasCarreras.size()));
            alumno.inscribirCarrera(carrera);
        }
    }
    
    public static void inscribirAlumnosEnMaterias() {
        Facultad facultad = Facultad.getInstance();
        Random random = new Random();
        
        // Obtener todos los alumnos
        List<Alumno> todosLosAlumnos = facultad.getAlumnos();
        
        // Inscribir 10 alumnos en materias del primer cuatrimestre
        for (int i = 0; i < 10; i++) {
            Alumno alumno = todosLosAlumnos.get(i);
            Carrera carrera = alumno.getCarreraInscripta();
            
            if (carrera != null) {
                List<Materia> materiasCuatri1 = carrera.materiasDelPeriodo(1, 2);
                // Inscribir en 1 o 2 materias del primer cuatrimestre
                int numMaterias = random.nextInt(2) + 1;
                
                for (int j = 0; j < numMaterias && j < materiasCuatri1.size(); j++) {
                    Materia materia = materiasCuatri1.get(j);
                    alumno.inscribirMateria(materia);
                }
            }
        }
    }
}