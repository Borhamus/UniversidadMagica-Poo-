

# Sistema de Gestión Académica Mágico

## Descripción
El Sistema de Gestión Académica Mágico es una aplicación de escritorio desarrollada en Java que permite gestionar de manera eficiente los procesos académicos de una institución educativa. Con una interfaz visual temática mágica, el sistema facilita la administración de alumnos, carreras, materias e inscripciones, aplicando reglas de validación según diferentes planes de estudio.

## Características Principales

### Gestión de Alumnos
- Creación, edición y eliminación de alumnos
- Inscripción en carreras
- Seguimiento del estado de inscripciones a materias
- Visualización de carreras finalizadas

### Gestión de Carreras
- Administración de carreras con planes de estudio personalizados
- Configuración de planes (A, B, C, D, E) con diferentes reglas de correlatividad
- Asignación de materias a cada carrera

### Gestión de Materias
- Creación y edición de materias con información detallada
- Configuración de correlatividades entre materias
- Organización por cuatrimestres
- Definición de materias optativas

### Sistema de Inscripciones
- Inscripción de alumnos en materias con validación automática
- Verificación de correlatividades según el plan de estudio
- Estados de inscripción: INSCRIPTO, CURSADA_APROBADA, FINAL_APROBADO, PROMOCIONADO
- Validaciones específicas por plan:
  - **Plan A**: Requiere cursadas aprobadas de correlativas
  - **Plan B**: Requiere finales aprobados de correlativas
  - **Plan C**: Requiere cursadas aprobadas y finales de 5 cuatrimestres previos
  - **Plan D**: Requiere cursadas aprobadas y finales de 3 cuatrimestres previos
  - **Plan E**: Requiere finales aprobados y finales de 3 cuatrimestres previos

### Interfaz de Usuario
- Diseño visual con tema mágico y animaciones
- Tablas interactivas con estilos personalizados
- Botones con efectos visuales y partículas mágicas
- Fondo estrellado animado
- Ojo mágico como elemento decorativo

## Estructura del Proyecto

```
src/
├── integradorobjetos/
│   ├── controlador/          # Lógica de negocio y servicios
│   ├── modelo/               # Clases de dominio (Alumno, Carrera, Materia, etc.)
│   ├── vista/                # Interfaces de usuario (Vistas)
│   │   ├── Style.java        # Clase con estilos visuales y animaciones
│   │   ├── VistaAlumno1.java # Vista principal de alumnos
│   │   ├── VistaAlumno2.java # Vista de detalles de alumno
│   │   ├── VistaAlumno4.java # Vista de creación de alumno
│   │   ├── VistaAlumno5.java # Vista de inscripciones
│   │   ├── VistaCarrera3.java # Vista de gestión de carreras
│   │   ├── VistaMateria1.java # Vista de gestión de materias
│   │   └── ...               # Otras vistas
│   └── Main.java             # Clase principal de inicio
```

## Tecnologías Utilizadas

- **Java 8+**: Lenguaje de programación principal
- **Java Swing**: Biblioteca para la interfaz gráfica
- **MVC**: Patrón de arquitectura Modelo-Vista-Controlador
- **Patrón Singleton**: Para gestión de instancias únicas (Facultad)

## Instalación y Ejecución

### Requisitos Previos
- Java Development Kit (JDK) 8 o superior
- IDE recomendado: NetBeans, IntelliJ IDEA o Eclipse



### Uso Básico

1. **Inicio de Sesión**: La aplicación inicia directamente en la vista principal.
2. **Gestión de Alumnos**:
   - Crear nuevos alumnos desde el botón "Crear Alumno"
   - Ver detalles de un alumno haciendo clic en "editar" en la tabla
   - Eliminar alumnos seleccionados en la tabla
3. **Gestión de Carreras**:
   - Acceder desde la vista principal
   - Crear nuevas carreras con sus respectivos planes de estudio
4. **Gestión de Materias**:
   - Acceder desde la vista de carreras
   - Crear materias con sus correlativas y cuatrimestres
5. **Inscripciones**:
   - Desde la vista de detalles de alumno
   - Seleccionar materias disponibles según el plan de estudio

## Contribuyentes

- [Tu Nombre] - Desarrollador principal

## Licencia

Este proyecto está bajo la Licencia MIT - ver el archivo [LICENSE.md](LICENSE.md) para detalles.

---

### Notas Adicionales

- El sistema incluye validaciones robustas para garantizar la integridad de los datos académicos.
- La interfaz está diseñada para ser intuitiva y visualmente atractiva con su tema mágico.
- Todas las operaciones críticas incluyen mensajes de confirmación para evitar acciones accidentales.
- El sistema está preparado para extenderse con nuevas funcionalidades como gestión de profesores, calificaciones, etc.
