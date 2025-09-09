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
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JLayer;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

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
        Ojo.setPreferredSize(new Dimension(200, 40)); // Alto de 100px para que se vea el ojo
        
        // Cargar datos del alumno
        cargarDatosAlumno();
        
        // Agregar listener a la tabla para manejar los cambios en los checkboxes
        MateriasDeLaCarrera.getModel().addTableModelListener(new javax.swing.event.TableModelListener() {
            @Override
            public void tableChanged(javax.swing.event.TableModelEvent e) {
                if (e.getColumn() == 5) { // Solo para la columna de checkboxes
                    int row = e.getFirstRow();
                    MateriasCarreraTableModel model = (MateriasCarreraTableModel) MateriasDeLaCarrera.getModel();

                    if (model.isSelected(row)) {
                        // El checkbox fue marcado, mostrar popup de confirmación
                        Materia materiaSeleccionada = model.getMateriaAt(row);
                        manejarSeleccionMateria(materiaSeleccionada, row);
                    }
                }
            }
        });
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
            // Crear modelo vacío
            MateriasDeLaCarrera.setModel(new MateriasCarreraTableModel(new ArrayList<>()));
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
    }
    
    // Modelo de tabla para las materias de la carrera
    class MateriasCarreraTableModel extends AbstractTableModel {
        private List<Materia> materias;
        private Alumno alumno;
        private List<Boolean> seleccionados; // Para trackear los checkboxes
        private String[] columnNames = {"Nombre de Materia", "Cuatrimestre", "Es Optativa", "Carga Horaria", "Correlativas", "Agregar"};

        public MateriasCarreraTableModel(List<Materia> materias, Alumno alumno) {
            this.materias = new ArrayList<>(materias);
            this.alumno = alumno;
            this.seleccionados = new ArrayList<>();
            // Inicializar todos los checkboxes como no seleccionados
            for (int i = 0; i < materias.size(); i++) {
                seleccionados.add(false);
            }
        }

        public MateriasCarreraTableModel(List<Materia> materias) {
            this.materias = new ArrayList<>(materias);
            this.alumno = null;
            this.seleccionados = new ArrayList<>();
            // Inicializar todos los checkboxes como no seleccionados
            for (int i = 0; i < materias.size(); i++) {
                seleccionados.add(false);
            }
        }

        @Override
        public int getRowCount() {
            return materias.size();
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Materia materia = materias.get(rowIndex);

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
                case 5: // Agregar (ahora es un checkbox)
                    return seleccionados.get(rowIndex);
                default:
                    return null;
            }
        }

        @Override
        public void setValueAt(Object value, int row, int col) {
            if (col == 5) { // Solo para la columna de checkboxes
                seleccionados.set(row, (Boolean) value);
                fireTableCellUpdated(row, col);
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
            return columnIndex == 5;
        }

        @Override
        public String getColumnName(int column) {
            return columnNames[column];
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
            if (row >= 0 && row < materias.size()) {
                return materias.get(row);
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
    
    private void manejarSeleccionMateria(Materia materia, int row) {
        // Verificar si el alumno ya está inscripto en esta materia
        for (InscripcionMateria inscripcion : alumno.getInscripciones()) {
            if (inscripcion.getMateria().equals(materia)) {
                JOptionPane.showMessageDialog(
                    this, 
                    "Ya estás inscripto en esta materia.", 
                    "Inscripción duplicada", 
                    JOptionPane.WARNING_MESSAGE
                );
                // Deseleccionar el checkbox
                ((MateriasCarreraTableModel) MateriasDeLaCarrera.getModel()).setSelected(row, false);
                return;
            }
        }

        // Verificar correlativas
        if (!verificarCorrelativas(materia)) {
            JOptionPane.showMessageDialog(
                this, 
                "No puedes inscribirte en esta materia porque tienes correlativas pendientes.", 
                "Correlativas pendientes", 
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
    
    /**
     * This method is called from within the constructor to initialize the form.
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
            .addGap(0, 200, Short.MAX_VALUE)
        );
        OjoLayout.setVerticalGroup(
            OjoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout FondoLayout = new javax.swing.GroupLayout(Fondo);
        Fondo.setLayout(FondoLayout);
        FondoLayout.setHorizontalGroup(
            FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FondoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(MarcoDeTexto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addContainerGap())
            .addGroup(FondoLayout.createSequentialGroup()
                .addGap(321, 321, 321)
                .addComponent(Ojo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        FondoLayout.setVerticalGroup(
            FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FondoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(MarcoDeTexto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(Ojo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
            .addGap(0, 399, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(Fondo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField CC;
    private javax.swing.JTextField CF;
    private javax.swing.JTextField Dni;
    private javax.swing.JPanel Fondo;
    private javax.swing.JPanel MarcoDeTexto;
    private javax.swing.JTable MateriasDeLaCarrera;
    private javax.swing.JTextField Nombre;
    private javax.swing.JPanel Ojo;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
