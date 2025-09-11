/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package integradorobjetos.vista;

import integradorobjetos.modelo.Alumno;
import integradorobjetos.modelo.Carrera;
import integradorobjetos.modelo.EstadoInscripcion;
import integradorobjetos.modelo.InscripcionMateria;
import integradorobjetos.modelo.Materia;
import integradorobjetos.modelo.Plan;
import integradorobjetos.modelo.Planes.PlanA;
import integradorobjetos.modelo.Planes.PlanB;
import integradorobjetos.modelo.Planes.PlanC;
import integradorobjetos.modelo.Planes.PlanD;
import integradorobjetos.modelo.Planes.PlanE;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JLayer;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Borhamus
 */
public class VistaAlumno5 extends javax.swing.JPanel {
    private Alumno alumno;
    /**
     * Creates new form VistaAlumno5
     */
    public VistaAlumno5(Alumno alumno)  {
        this.alumno = alumno;
        initComponents();
        Fondo.setOpaque(false);

        // Establecer dimensiones exactas
        setMaximumSize(new java.awt.Dimension(846, 398));
        setMinimumSize(new java.awt.Dimension(846, 398));
        setPreferredSize(new java.awt.Dimension(846, 398));

        // Configurar el panel Fondo para que ocupe todo el espacio
        Fondo.setMaximumSize(new java.awt.Dimension(846, 398));
        Fondo.setMinimumSize(new java.awt.Dimension(846, 398));
        Fondo.setPreferredSize(new java.awt.Dimension(846, 398));

        //Estilo estrellado
        JLayer<JComponent> capaEstrellada = Style.aplicarFondoEstrellado(Fondo, 1, 30);
        setLayout(new BorderLayout());
        remove(Fondo);
        add(capaEstrellada, BorderLayout.CENTER);

        // Crear el componente del ojo mágico
        OjoMagico ojoMagico = new OjoMagico();

        // Configurar el panel Ojo para que contenga nuestro ojo mágico
        Ojo.removeAll(); // Limpiar el panel
        Ojo.setLayout(new BorderLayout()); // Establecer un layout
        Ojo.add(ojoMagico, BorderLayout.CENTER); // Añadir el ojo mágico

        // IMPORTANTE: Establecer un tamaño preferido para el panel Ojo
        Ojo.setPreferredSize(new Dimension(110, 40)); // Alto de 100px para que se vea el ojo

        // Cargar datos del alumno
        cargarDatosAlumno();
    }
    
    private void cargarDatosAlumno() {
        // 1. Cargar nombre completo del alumno
        Nombre.setText("Nombre: " + alumno.getNombre());
        // 2. Cargar DNI con formato especial
        Dni.setText("DNI: " + alumno.getDni() );
        // 3. Cargar carreras finalizadas
        StringBuilder carrerasFinalizadas = new StringBuilder("Carreras Finalizadas: ");
        if (alumno.getCarrerasTerminadas().isEmpty()) {
            carrerasFinalizadas.append("Ninguna");
        } else {
            for (Carrera carrera : alumno.getCarrerasTerminadas()) {
                carrerasFinalizadas.append(carrera.getNombre()).append(", ");
            }
            // Eliminar la última coma y espacio
            carrerasFinalizadas.setLength(carrerasFinalizadas.length() - 2);
        }
        CF.setText(carrerasFinalizadas.toString());
        // 4. Cargar carrera cursando actualmente
        String textoCarreraActiva = "Carrera Activa: ";
        if (alumno.getCarreraInscripta() != null) {
            textoCarreraActiva += alumno.getCarreraInscripta().getNombre();
        } else {
            textoCarreraActiva += "Sin Carrera";
        }
        CC.setText(textoCarreraActiva);
        // 5. Cargar tabla de las materias que existen en la carrera inscripta
        cargarTablaMateriasDeLaCarrera();
    }
    
    private void cargarTablaMateriasDeLaCarrera() {
        // Verificar si el alumno tiene una carrera inscripta
        if (alumno.getCarreraInscripta() == null) {
            // Mostrar mensaje o tabla vacía
            JOptionPane.showMessageDialog(
                this, 
                "No tienes una carrera activa. Debes inscribirte a una carrera primero.", 
                "Sin Carrera Activa", 
                JOptionPane.WARNING_MESSAGE
            );
            // Crear modelo vacío - CORREGIDO: incluir el objeto alumno
            MateriasDeLaCarrera.setModel(new MateriasCarreraTableModel(new ArrayList<>(), alumno));
            return;
        }

        // Obtener la carrera inscripta del alumno
        Carrera carreraActiva = alumno.getCarreraInscripta();

        // Obtener todas las materias de esa carrera
        List<Materia> materias = carreraActiva.getMaterias();

        // Crear modelo para la tabla
        MateriasCarreraTableModel modelo = new MateriasCarreraTableModel(materias, alumno);
        MateriasDeLaCarrera.setModel(modelo);

        // Configurar columnas con nuevos anchos
        MateriasDeLaCarrera.getColumnModel().getColumn(0).setPreferredWidth(150); // Nombre de Materia
        MateriasDeLaCarrera.getColumnModel().getColumn(1).setPreferredWidth(50);  // Cuatrimestre
        MateriasDeLaCarrera.getColumnModel().getColumn(2).setPreferredWidth(50);  // Es Optativa
        MateriasDeLaCarrera.getColumnModel().getColumn(3).setPreferredWidth(50); // Carga Horaria
        MateriasDeLaCarrera.getColumnModel().getColumn(4).setPreferredWidth(250); // Correlativas
        MateriasDeLaCarrera.getColumnModel().getColumn(5).setPreferredWidth(50);  // Agregar

        // Hacer que la tabla sea más alta para mostrar bien los checkboxes
        MateriasDeLaCarrera.setRowHeight(25);

        // Configurar renderizador y editor después de cargar los datos
        configurarTabla();
    }
    
    private void configurarTabla() {
        // Configurar renderizador y editor personalizados para la columna "Agregar"
        if (MateriasDeLaCarrera.getColumnCount() > 5) {
            MateriasDeLaCarrera.getColumnModel().getColumn(5).setCellRenderer(new CheckboxRenderer());
            MateriasDeLaCarrera.getColumnModel().getColumn(5).setCellEditor(new CheckboxEditor());

            // Agregar listener a la tabla para manejar los cambios en los checkboxes
            MateriasDeLaCarrera.getModel().addTableModelListener(new javax.swing.event.TableModelListener() {
                @Override
                public void tableChanged(javax.swing.event.TableModelEvent e) {
                    if (e.getColumn() == 5) { // Solo para la columna de checkboxes
                        int row = e.getFirstRow();
                        MateriasCarreraTableModel model = (MateriasCarreraTableModel) MateriasDeLaCarrera.getModel();
                        Object value = model.getValueAt(row, 5);
                        // Solo manejar si es un Boolean y está marcado
                        if (value instanceof Boolean && (Boolean) value) {
                            // El checkbox fue marcado, mostrar popup de confirmación
                            Materia materiaSeleccionada = model.getMateriaAt(row);
                            manejarSeleccionMateria(materiaSeleccionada, row);
                        }
                    }
                }
            });
        }
        // Después de actualizar, aplicamos el estilo
        aplicarEstiloTablas();
    }
    
    /**
     * Aplica el estilo mágico a todas las tablas de esta vista
    */
    private void aplicarEstiloTablas() {
        // Aplicar estilo a la tabla principal
        Style.estiloTablaMagico(MateriasDeLaCarrera, new Style.TableConfigurador() {
            @Override
            public void configurar(JTable tabla) {
                // Configurar anchos de columna específicos para esta tabla
                tabla.getColumnModel().getColumn(0).setPreferredWidth(150); // Nombre de Materia
                tabla.getColumnModel().getColumn(1).setPreferredWidth(40);  // Cuatrimestre
                tabla.getColumnModel().getColumn(2).setPreferredWidth(40);  // EsOptativa
                tabla.getColumnModel().getColumn(3).setPreferredWidth(40);  // CargaHoraria
                tabla.getColumnModel().getColumn(4).setPreferredWidth(200); // Correlativa
                tabla.getColumnModel().getColumn(5).setPreferredWidth(30);  // Editar
                
            }
        });}
    
    // Modelo de tabla para las materias de la carrera
    class MateriasCarreraTableModel extends AbstractTableModel {
        private List<Materia> materiasOriginales; // Lista completa de materias
        private List<Materia> materiasFiltradas; // Lista de materias a mostrar
        private Alumno alumno;
        private List<Boolean> seleccionados; // Para trackear los checkboxes
        private String[] columnNames = {"Nombre de Materia", "Cuatrimestre", "Es Optativa", "Carga Horaria", "Correlativas", "Agregar"};

        public MateriasCarreraTableModel(List<Materia> materias, Alumno alumno) {
            this.materiasOriginales = new ArrayList<>(materias);
            this.alumno = alumno;
            this.seleccionados = new ArrayList<>();

            // Filtrar las materias según el estado de inscripción
            filtrarMaterias();
        }

        private void filtrarMaterias() {
            this.materiasFiltradas = new ArrayList<>();
            this.seleccionados = new ArrayList<>();
            for (Materia materia : materiasOriginales) {
                EstadoInscripcion estado = obtenerEstadoInscripcion(materia);

                // Excluir materias que ya están en cualquier estado que no permita reinscripción
                if (estado != null && (
                    estado == EstadoInscripcion.INSCRIPTO || 
                    estado == EstadoInscripcion.CURSADA_APROBADA || 
                    estado == EstadoInscripcion.FINAL_APROBADO ||
                    estado == EstadoInscripcion.PROMOCIONADO)) {
                    continue; // Saltar esta materia
                }

                // Agregar la materia a la lista filtrada
                materiasFiltradas.add(materia);
                // Inicializar el estado del checkbox (siempre falso porque no está inscripto)
                seleccionados.add(false);
            }
        }

        @Override
        public int getRowCount() {
            return materiasFiltradas.size();
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Materia materia = materiasFiltradas.get(rowIndex);
            switch (columnIndex) {
                case 0: // Nombre de Materia
                    return materia.getNombre();
                case 1: // Cuatrimestre
                    return materia.getCuatrimestre() + "º";
                case 2: // Es Optativa
                    return materia.esOptativa() ? "Sí" : "No";
                case 3: // Carga Horaria
                    return materia.getCargaHoraria() + " hs";
                case 4: // Correlativas
                    return obtenerNombresCorrelativas(materia);
                case 5: // Agregar (checkbox)
                    // Como filtramos las materias, todas las que quedan son no inscriptas
                    // Solo devolvemos el estado del checkbox
                    return seleccionados.get(rowIndex);
                default:
                    return null;
            }
        }

        @Override
        public void setValueAt(Object value, int row, int col) {
            if (col == 5) { // Solo para la columna de checkboxes
                // Solo actualizar si es un Boolean
                if (value instanceof Boolean) {
                    seleccionados.set(row, (Boolean) value);
                    fireTableCellUpdated(row, col);
                }
            }
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            if (columnIndex == 5) {
                return Boolean.class;
            }
            return super.getColumnClass(columnIndex);
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            // Solo la columna de checkboxes es editable
            if (columnIndex == 5) {
                if (alumno == null) return false;

                Materia materia = materiasFiltradas.get(rowIndex);
                EstadoInscripcion estado = obtenerEstadoInscripcion(materia);

                // Solo editable si no está inscripto en ningún estado
                return estado == null;
            }
            return false;
        }

        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }

        // Método para obtener el estado de inscripción de una materia
        private EstadoInscripcion obtenerEstadoInscripcion(Materia materia) {
            if (alumno == null) return null;
            for (InscripcionMateria inscripcion : alumno.getInscripciones()) {
                if (inscripcion.getMateria().equals(materia)) {
                    return inscripcion.getEstado();
                }
            }
            return null; // No está inscripto
        }

        // Método para obtener los nombres de las correlativas como texto
        private String obtenerNombresCorrelativas(Materia materia) {
            List<Materia> correlativas = materia.getCorrelativas();
            if (correlativas == null || correlativas.isEmpty()) {
                return "Ninguna";
            }
            StringBuilder nombres = new StringBuilder();
            for (Materia correlativa : correlativas) {
                nombres.append(correlativa.getNombre()).append(", ");
            }
            // Eliminar la última coma y espacio
            if (nombres.length() > 0) {
                nombres.setLength(nombres.length() - 2);
            }
            return nombres.toString();
        }

        // Método para obtener la materia en una fila específica
        public Materia getMateriaAt(int row) {
            if (row >= 0 && row < materiasFiltradas.size()) {
                return materiasFiltradas.get(row);
            }
            return null;
        }

        // Método para verificar si una fila está seleccionada
        public boolean isSelected(int row) {
            return seleccionados.get(row);
        }

        // Método para establecer el estado de selección
        public void setSelected(int row, boolean selected) {
            seleccionados.set(row, selected);
            fireTableCellUpdated(row, 5);
        }
    }

    private boolean verificarCorrelativas(Materia materia) {
        List<Materia> correlativas = materia.getCorrelativas();

        // Si no hay correlativas, se puede inscribir
        if (correlativas == null || correlativas.isEmpty()) {
            return true;
        }

        // Verificar cada correlativa
        for (Materia correlativa : correlativas) {
            boolean correlativaAprobada = false;

            // Buscar si el alumno tiene aprobada esta correlativa
            for (InscripcionMateria inscripcion : alumno.getInscripciones()) {
                if (inscripcion.getMateria().equals(correlativa)) {
                    if (inscripcion.getEstado() == EstadoInscripcion.CURSADA_APROBADA || 
                        inscripcion.getEstado() == EstadoInscripcion.FINAL_APROBADO) {
                        correlativaAprobada = true;
                        break;
                    }
                }
            }

            // Si alguna correlativa no está aprobada, no se puede inscribir
            if (!correlativaAprobada) {
                return false;
            }
        }

        // Todas las correlativas están aprobadas
        return true;
    }
    
    class CheckboxRenderer extends DefaultTableCellRenderer {
        private JCheckBox checkbox = new JCheckBox();

        public CheckboxRenderer() {
            checkbox.setOpaque(true);
            checkbox.setHorizontalAlignment(SwingConstants.CENTER);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, 
                boolean isSelected, boolean hasFocus, int row, int column) {

            if (value instanceof Boolean) {
                // Es un checkbox
                checkbox.setSelected((Boolean) value);
                checkbox.setEnabled(table.isCellEditable(row, column));
                checkbox.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
                checkbox.setForeground(isSelected ? table.getSelectionForeground() : table.getForeground());
                return checkbox;
            } else {
                // Es un String (ej. "Aprobada")
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                label.setHorizontalAlignment(SwingConstants.CENTER);
                label.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
                label.setForeground(isSelected ? table.getSelectionForeground() : table.getForeground());
                return label;
            }
        }
    }
    
    class CheckboxEditor extends DefaultCellEditor {
        private JCheckBox checkbox;

        public CheckboxEditor() {
            super(new JCheckBox());
            this.checkbox = (JCheckBox) getComponent();
            checkbox.setOpaque(true);
            checkbox.setHorizontalAlignment(SwingConstants.CENTER);
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, 
                boolean isSelected, int row, int column) {
            if (value instanceof Boolean) {
                checkbox.setSelected((Boolean) value);
                checkbox.setEnabled(true);
                return checkbox;
            } else {
                // Si no es un Boolean, no permitimos edición
                return null;
            }
        }

        @Override
        public boolean isCellEditable(EventObject anEvent) {
            // Simplificamos la lógica para evitar el error
            // El modelo de tabla ya determina qué celdas son editables
            return true;
        }
    }
    
    private void manejarSeleccionMateria(Materia materia, int row) {
        // Obtener el estado actual de inscripción
        EstadoInscripcion estadoActual = null;
        for (InscripcionMateria inscripcion : alumno.getInscripciones()) {
            if (inscripcion.getMateria().equals(materia)) {
                estadoActual = inscripcion.getEstado();
                break;
            }
        }

        // Si ya está inscripto, no hacemos nada
        if (estadoActual == EstadoInscripcion.INSCRIPTO) {
            return;
        }

        // Verificar condiciones según el plan de estudios
        if (!verificarCondicionesPlan(materia)) {
            // Mensaje de error personalizado según el plan
            String mensaje = obtenerMensajeErrorPlan(materia);
            JOptionPane.showMessageDialog(
                this, 
                mensaje, 
                "Condiciones no cumplidas", 
                JOptionPane.WARNING_MESSAGE
            );
            // Deseleccionar el checkbox
            ((MateriasCarreraTableModel) MateriasDeLaCarrera.getModel()).setSelected(row, false);
            return;
        }

        // Mostrar popup de confirmación
        int opcion = JOptionPane.showConfirmDialog(
            this, 
            "¿Estás seguro de que quieres inscribirte en la materia: " + materia.getNombre() + "?", 
            "Confirmar inscripción", 
            JOptionPane.YES_NO_OPTION
        );
        if (opcion == JOptionPane.YES_OPTION) {
            // Realizar la inscripción
            alumno.inscribirMateria(materia);
            // Mostrar mensaje de éxito
            JOptionPane.showMessageDialog(
                this, 
                "Te has inscripto exitosamente en la materia: " + materia.getNombre(), 
                "Inscripción exitosa", 
                JOptionPane.INFORMATION_MESSAGE
            );
            // Actualizar la tabla para reflejar el nuevo estado
            cargarTablaMateriasDeLaCarrera();
        } else {
            // Si el usuario cancela, deseleccionar el checkbox
            ((MateriasCarreraTableModel) MateriasDeLaCarrera.getModel()).setSelected(row, false);
        }
    }

    private String obtenerMensajeErrorPlan(Materia materia) {
        Plan plan = alumno.getCarreraInscripta().getPlanDeEstudio();

        if (plan instanceof PlanA) {
            return "No puedes inscribirte en esta materia porque no tienes aprobadas las cursadas de todas las correlativas.";
        } else if (plan instanceof PlanB) {
            return "No puedes inscribirte en esta materia porque no tienes aprobados los finales de todas las correlativas.";
        } else if (plan instanceof PlanC) {
            return "No puedes inscribirte en esta materia porque no cumples con las condiciones del Plan C: " +
                   "debes tener aprobadas las cursadas de las correlativas y los finales de todas las materias de 5 cuatrimestres previos.";
        } else if (plan instanceof PlanD) {
            return "No puedes inscribirte en esta materia porque no cumples con las condiciones del Plan D: " +
                   "debes tener aprobadas las cursadas de las correlativas y los finales de todas las materias de 3 cuatrimestres previos.";
        } else if (plan instanceof PlanE) {
            return "No puedes inscribirte en esta materia porque no cumples con las condiciones del Plan E: " +
                   "debes tener aprobados los finales de las correlativas y los finales de todas las materias de 3 cuatrimestres previos.";
        }

        return "No puedes inscribirte en esta materia porque no cumples con las condiciones requeridas.";
    }
    
    private boolean verificarCondicionesPlan(Materia materia) {
        Carrera carrera = alumno.getCarreraInscripta();
        Plan plan = carrera.getPlanDeEstudio();

        // Si la materia no tiene correlativas, solo verificamos condiciones de cuatrimestres previos si aplica
        boolean tieneCorrelativas = materia.getCorrelativas() != null && !materia.getCorrelativas().isEmpty();

        // Verificar condiciones según el tipo de plan
        if (plan instanceof PlanA) {
            return verificarPlanA(materia);
        } else if (plan instanceof PlanB) {
            return verificarPlanB(materia);
        } else if (plan instanceof PlanC) {
            return tieneCorrelativas ? verificarPlanC(materia) : true;
        } else if (plan instanceof PlanD) {
            return tieneCorrelativas ? verificarPlanD(materia) : true;
        } else if (plan instanceof PlanE) {
            return tieneCorrelativas ? verificarPlanE(materia) : true;
        }

        return false; // Plan no reconocido
    }

    private boolean verificarPlanA(Materia materia) {
        // Verificar correlativas: CURSADA_APROBADA, FINAL_APROBADO o PROMOCIONADO
        for (Materia correlativa : materia.getCorrelativas()) {
            EstadoInscripcion estado = obtenerEstadoInscripcion(correlativa);
            if (estado != EstadoInscripcion.CURSADA_APROBADA && 
                estado != EstadoInscripcion.FINAL_APROBADO && 
                estado != EstadoInscripcion.PROMOCIONADO) {
                return false;
            }
        }
        return true;
    }

    private boolean verificarPlanB(Materia materia) {
        // Verificar correlativas: FINAL_APROBADO o PROMOCIONADO
        for (Materia correlativa : materia.getCorrelativas()) {
            EstadoInscripcion estado = obtenerEstadoInscripcion(correlativa);
            if (estado != EstadoInscripcion.FINAL_APROBADO && 
                estado != EstadoInscripcion.PROMOCIONADO) {
                return false;
            }
        }
        return true;
    }

    private boolean verificarPlanC(Materia materia) {
        // 1. Verificar correlativas (igual que Plan A)
        if (!verificarPlanA(materia)) {
            return false;
        }

        // 2. Verificar finales de materias de 5 cuatrimestres previos SOLO si hay suficientes cuatrimestres
        int cuatriActual = materia.getCuatrimestre();
        if (cuatriActual > 5) { // Solo verificar si hay al menos 5 cuatrimestres previos
            return verificarFinalesCuatrimestresPrevios(materia, 5);
        }
        return true; // Si no hay suficientes cuatrimestres, no se verifica
    }

    private boolean verificarPlanD(Materia materia) {
        // 1. Verificar correlativas (igual que Plan A)
        if (!verificarPlanA(materia)) {
            return false;
        }

        // 2. Verificar finales de materias de 3 cuatrimestres previos SOLO si hay suficientes cuatrimestres
        int cuatriActual = materia.getCuatrimestre();
        if (cuatriActual > 3) { // Solo verificar si hay al menos 3 cuatrimestres previos
            return verificarFinalesCuatrimestresPrevios(materia, 3);
        }
        return true; // Si no hay suficientes cuatrimestres, no se verifica
    }

    private boolean verificarPlanE(Materia materia) {
        // 1. Verificar correlativas (igual que Plan B)
        if (!verificarPlanB(materia)) {
            return false;
        }

        // 2. Verificar finales de materias de 3 cuatrimestres previos SOLO si hay suficientes cuatrimestres
        int cuatriActual = materia.getCuatrimestre();
        if (cuatriActual > 3) { // Solo verificar si hay al menos 3 cuatrimestres previos
            return verificarFinalesCuatrimestresPrevios(materia, 3);
        }
        return true; // Si no hay suficientes cuatrimestres, no se verifica
    }

    private boolean verificarFinalesCuatrimestresPrevios(Materia materia, int cuatrimestresPrevios) {
        int cuatriActual = materia.getCuatrimestre();
        int inicio = Math.max(1, cuatriActual - cuatrimestresPrevios); // No menor a 1

        // Obtener todas las materias de los cuatrimestres previos
        List<Materia> materiasPrevias = new ArrayList<>();
        for (Materia m : alumno.getCarreraInscripta().getMaterias()) {
            if (m.getCuatrimestre() >= inicio && m.getCuatrimestre() < cuatriActual) {
                materiasPrevias.add(m);
            }
        }

        // Si no hay materias previas, no hay problema
        if (materiasPrevias.isEmpty()) {
            return true;
        }

        // Verificar que todas tengan final aprobado
        for (Materia m : materiasPrevias) {
            EstadoInscripcion estado = obtenerEstadoInscripcion(m);
            if (estado != EstadoInscripcion.FINAL_APROBADO && 
                estado != EstadoInscripcion.PROMOCIONADO) {
                return false;
            }
        }

        return true;
    }

    // Método auxiliar para obtener el estado de inscripción de una materia
    private EstadoInscripcion obtenerEstadoInscripcion(Materia materia) {
        for (InscripcionMateria inscripcion : alumno.getInscripciones()) {
            if (inscripcion.getMateria().equals(materia)) {
                return inscripcion.getEstado();
            }
        }
        return null; // No está inscripto
    }
    
    
    
     /* This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Fondo = new javax.swing.JPanel();
        MarcoDeTexto = new javax.swing.JPanel();
        Nombre = new javax.swing.JTextField();
        Dni = new javax.swing.JTextField();
        CF = new javax.swing.JTextField();
        CC = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        MateriasDeLaCarrera = new javax.swing.JTable();
        Ojo = new javax.swing.JPanel();
        Volver = new javax.swing.JButton();

        setMaximumSize(new java.awt.Dimension(846, 398));
        setMinimumSize(new java.awt.Dimension(846, 398));

        Fondo.setMaximumSize(new java.awt.Dimension(846, 398));
        Fondo.setMinimumSize(new java.awt.Dimension(846, 398));

        MarcoDeTexto.setBackground(new java.awt.Color(0, 153, 153));

        Nombre.setText("Nombre:");

        Dni.setText("DNI:");

        CF.setText("Carreras Finalizadas:");

        CC.setText("Carrera en Curso:");

        javax.swing.GroupLayout MarcoDeTextoLayout = new javax.swing.GroupLayout(MarcoDeTexto);
        MarcoDeTexto.setLayout(MarcoDeTextoLayout);
        MarcoDeTextoLayout.setHorizontalGroup(
            MarcoDeTextoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MarcoDeTextoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(MarcoDeTextoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Nombre)
                    .addComponent(Dni, javax.swing.GroupLayout.DEFAULT_SIZE, 822, Short.MAX_VALUE)
                    .addComponent(CF, javax.swing.GroupLayout.DEFAULT_SIZE, 822, Short.MAX_VALUE)
                    .addComponent(CC, javax.swing.GroupLayout.DEFAULT_SIZE, 822, Short.MAX_VALUE))
                .addContainerGap())
        );
        MarcoDeTextoLayout.setVerticalGroup(
            MarcoDeTextoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MarcoDeTextoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Dni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(CF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(CC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        MateriasDeLaCarrera.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(MateriasDeLaCarrera);

        Ojo.setBackground(new java.awt.Color(0, 0, 0));
        Ojo.setPreferredSize(new java.awt.Dimension(200, 40));

        javax.swing.GroupLayout OjoLayout = new javax.swing.GroupLayout(Ojo);
        Ojo.setLayout(OjoLayout);
        OjoLayout.setHorizontalGroup(
            OjoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 110, Short.MAX_VALUE)
        );
        OjoLayout.setVerticalGroup(
            OjoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );

        Volver.setText("Volver");
        Volver.setFocusable(false);
        Volver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                VolverActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout FondoLayout = new javax.swing.GroupLayout(Fondo);
        Fondo.setLayout(FondoLayout);
        FondoLayout.setHorizontalGroup(
            FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FondoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, FondoLayout.createSequentialGroup()
                        .addComponent(Ojo, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Volver, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(399, 399, 399))
                    .addComponent(MarcoDeTexto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        FondoLayout.setVerticalGroup(
            FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FondoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(MarcoDeTexto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Ojo, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Volver, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 846, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(Fondo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 402, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(Fondo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void VolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VolverActionPerformed
        // Crear una nueva instancia de VistaAlumno2 con el mismo alumno
        VistaAlumno2 vistaAlumno2 = new VistaAlumno2(alumno);

        // Reemplazar el contenido del panel Fondo con VistaAlumno2
        Fondo.removeAll();
        Fondo.setLayout(new BorderLayout());
        Fondo.add(vistaAlumno2, BorderLayout.CENTER);
        Fondo.revalidate();
        Fondo.repaint();
    }//GEN-LAST:event_VolverActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField CC;
    private javax.swing.JTextField CF;
    private javax.swing.JTextField Dni;
    private javax.swing.JPanel Fondo;
    private javax.swing.JPanel MarcoDeTexto;
    private javax.swing.JTable MateriasDeLaCarrera;
    private javax.swing.JTextField Nombre;
    private javax.swing.JPanel Ojo;
    private javax.swing.JButton Volver;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
