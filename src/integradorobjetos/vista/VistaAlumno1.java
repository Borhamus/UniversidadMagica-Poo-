/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package integradorobjetos.vista;

import integradorobjetos.modelo.Alumno;
import integradorobjetos.modelo.Carrera;
import integradorobjetos.modelo.Facultad;
import integradorobjetos.vista.Style;
import integradorobjetos.vista.VistaAlumno4;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
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
public class VistaAlumno1 extends javax.swing.JPanel {
    private AlumnoTableModel modeloTabla; // Declaración de la variable
    
    /**
     * Creates new form VistaAlumno1
     */
    public VistaAlumno1() {
        initComponents();
        
        // Crear el componente del ojo mágico
        OjoMagico ojoMagico = new OjoMagico();
        
        // Configurar el panel Ojo para que contenga nuestro ojo mágico
        Ojo.removeAll(); // Limpiar el panel
        Ojo.setLayout(new BorderLayout()); // Establecer un layout
        Ojo.add(ojoMagico, BorderLayout.CENTER); // Añadir el ojo mágico
        
        // IMPORTANTE: Establecer un tamaño preferido para el panel Ojo
        Ojo.setPreferredSize(new Dimension(433, 67)); // Alto de 100px para que se vea el ojo
        
        Fondo.setOpaque(false);
        
        // Establecer dimensiones exactas
        setMaximumSize(new java.awt.Dimension(846, 398));
        setMinimumSize(new java.awt.Dimension(846, 398));
        setPreferredSize(new java.awt.Dimension(846, 398));
        
        // Configurar el panel Fondo para que ocupe todo el espacio
        Fondo.setMaximumSize(new java.awt.Dimension(846, 398));
        Fondo.setMinimumSize(new java.awt.Dimension(846, 398));
        Fondo.setPreferredSize(new java.awt.Dimension(846, 398));
        
        JLayer<JComponent> capaEstrellada = Style.aplicarFondoEstrellado(Fondo, 1, 30);
        setLayout(new BorderLayout());
        remove(Fondo);
        add(capaEstrellada, BorderLayout.CENTER);
        
        // Inicializar componentes con datos del modelo
        inicializarComponentes();
        actualizarTabla();
    }
    
    private void inicializarComponentes() {
        // Crear el modelo de tabla personalizado
        Facultad facultad = Facultad.getInstance();
        modeloTabla = new AlumnoTableModel(facultad.getAlumnos()); // Inicialización aquí
        TablaContenido.setModel(modeloTabla);
        
        // Cargar opciones especiales y carreras en el combo
        DefaultComboBoxModel<String> modeloCombo = new DefaultComboBoxModel<>();
        
        // Agregar opciones especiales al principio
        modeloCombo.addElement("Todos");
        modeloCombo.addElement("En ninguna carrera");
        
        // Agregar carreras después de las opciones especiales
        for (Carrera carrera : facultad.getCarreras()) {
            modeloCombo.addElement(carrera.getNombre());
        }
        
        BarraDeCarreras.setModel(modeloCombo);
        
        // Agregar listener para actualizar tabla al cambiar selección
        BarraDeCarreras.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    actualizarTabla();
                }
            }
        });
        
        // Seleccionar "Todos" por defecto
        if (modeloCombo.getSize() > 0) {
            BarraDeCarreras.setSelectedIndex(0);
        }
        
        TablaContenido.setModel(modeloTabla);
    
        // Agregar MouseListener para manejar clics en la columna "Ver"
        TablaContenido.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int column = TablaContenido.columnAtPoint(e.getPoint());
                int row = TablaContenido.rowAtPoint(e.getPoint());

                // Verificamos si se hizo clic en la columna "Ver" (índice 4)
                if (column == 4) {
                    // Obtenemos el alumno de la fila seleccionada
                    Alumno alumno = modeloTabla.getAlumnoAt(row);

                    // Creamos la VistaAlumno2 y le pasamos el alumno
                    VistaAlumno2 vistaAlumno2 = new VistaAlumno2(alumno);

                    // Reemplazamos el contenido del panel Fondo con esta nueva vista
                    Fondo.removeAll();
                    Fondo.setLayout(new BorderLayout());
                    Fondo.add(vistaAlumno2, BorderLayout.CENTER);
                    Fondo.revalidate();
                    Fondo.repaint();
                }
            }
        });
        
        TablaContenido.getColumnModel().getColumn(4).setCellRenderer(new DefaultTableCellRenderer() {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, 
                                                       boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if (c instanceof JLabel) {
                JLabel label = (JLabel) c;
                label.setText("<html><u>" + value + "</u></html>");
                label.setForeground(Color.BLUE);
                label.setHorizontalAlignment(SwingConstants.CENTER);
            }
            return c;
        }
    });
    }
    
    private void actualizarTabla() {
        String seleccion = (String) BarraDeCarreras.getSelectedItem();
        if (seleccion == null) return;
        
        Facultad facultad = Facultad.getInstance();
        List<Alumno> alumnosFiltrados = new ArrayList<>();
        
        for (Alumno alumno : facultad.getAlumnos()) {
            boolean agregarAlumno = false;
            
            if (seleccion.equals("Todos")) {
                agregarAlumno = true;
            } 
            else if (seleccion.equals("En ninguna carrera") && alumno.getCarreraInscripta() == null) {
                agregarAlumno = true;
            } 
            else if (alumno.getCarreraInscripta() != null && 
                     alumno.getCarreraInscripta().getNombre().equals(seleccion)) {
                agregarAlumno = true;
            }
            
            if (agregarAlumno) {
                alumnosFiltrados.add(alumno);
            }
        }
        
        modeloTabla.actualizarDatos(alumnosFiltrados);
    }
    
    class AlumnoTableModel extends AbstractTableModel {
        private List<Alumno> alumnos;
        private String[] columnNames = {"Nombre", "DNI", "Carrera", "Estado", "Ver"};
        
        public AlumnoTableModel(List<Alumno> alumnos) {
            this.alumnos = new ArrayList<>(alumnos);
        }
        
        @Override
        public int getRowCount() {
            return alumnos.size();
        }
        
         @Override
        public int getColumnCount() {
            return columnNames.length; // Ahora devuelve 5
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Alumno alumno = alumnos.get(rowIndex);
            switch (columnIndex) {
                case 0: return alumno.getNombre();
                case 1: return alumno.getDni();
                case 2: 
                    if (alumno.getCarreraInscripta() != null) {
                        return alumno.getCarreraInscripta().getNombre();
                    } else {
                        return "Ninguna";
                    }
                case 3:
                    if (alumno.getCarreraInscripta() != null) {
                        return "Inscripto";
                    } else {
                        return "Sin carrera";
                    }
                case 4: return "editar"; // Nueva columna
                default: return null;
            }
        }
        
        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }
        
        public Alumno getAlumnoAt(int rowIndex) {
            return alumnos.get(rowIndex);
        }
        
        public void actualizarDatos(List<Alumno> nuevosAlumnos) {
            this.alumnos = new ArrayList<>(nuevosAlumnos);
            fireTableDataChanged();
        }
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
        BarraDeCarreras = new javax.swing.JComboBox<>();
        ContTabla = new javax.swing.JScrollPane();
        TablaContenido = new javax.swing.JTable();
        Ojo = new javax.swing.JPanel();
        CrearAlumnoBoton = new javax.swing.JButton();
        EliminarAlumnoBoton = new javax.swing.JButton();

        setMaximumSize(new java.awt.Dimension(846, 398));
        setMinimumSize(new java.awt.Dimension(846, 398));
        setPreferredSize(new java.awt.Dimension(846, 398));

        Fondo.setMaximumSize(new java.awt.Dimension(846, 398));
        Fondo.setMinimumSize(new java.awt.Dimension(846, 398));
        Fondo.setPreferredSize(new java.awt.Dimension(846, 398));

        BarraDeCarreras.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Carreras" }));

        TablaContenido.setModel(new javax.swing.table.DefaultTableModel(
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
        ContTabla.setViewportView(TablaContenido);

        Ojo.setBackground(new java.awt.Color(0, 0, 0));
        Ojo.setMaximumSize(new java.awt.Dimension(433, 67));
        Ojo.setMinimumSize(new java.awt.Dimension(433, 67));
        Ojo.setPreferredSize(new java.awt.Dimension(433, 67));

        javax.swing.GroupLayout OjoLayout = new javax.swing.GroupLayout(Ojo);
        Ojo.setLayout(OjoLayout);
        OjoLayout.setHorizontalGroup(
            OjoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 433, Short.MAX_VALUE)
        );
        OjoLayout.setVerticalGroup(
            OjoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        CrearAlumnoBoton.setText("Crear Alumno");
        CrearAlumnoBoton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CrearAlumnoBotonActionPerformed(evt);
            }
        });

        EliminarAlumnoBoton.setText("Eliminar Alumno");
        EliminarAlumnoBoton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EliminarAlumnoBotonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout FondoLayout = new javax.swing.GroupLayout(Fondo);
        Fondo.setLayout(FondoLayout);
        FondoLayout.setHorizontalGroup(
            FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FondoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ContTabla)
                    .addGroup(FondoLayout.createSequentialGroup()
                        .addGroup(FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(FondoLayout.createSequentialGroup()
                                .addComponent(Ojo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(BarraDeCarreras, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(FondoLayout.createSequentialGroup()
                                .addComponent(CrearAlumnoBoton, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(EliminarAlumnoBoton, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 3, Short.MAX_VALUE)))
                .addContainerGap())
        );
        FondoLayout.setVerticalGroup(
            FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, FondoLayout.createSequentialGroup()
                .addGroup(FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(FondoLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(Ojo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18))
                    .addGroup(FondoLayout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(BarraDeCarreras, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)))
                .addComponent(ContTabla, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CrearAlumnoBoton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(EliminarAlumnoBoton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Fondo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Fondo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void CrearAlumnoBotonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CrearAlumnoBotonActionPerformed
        VistaAlumno4 panel = new VistaAlumno4();
        Fondo.removeAll();
        Fondo.setLayout(new BorderLayout());
        Fondo.add(panel, BorderLayout.CENTER);
        Fondo.revalidate();
        Fondo.repaint();
         
    }//GEN-LAST:event_CrearAlumnoBotonActionPerformed

    private void EliminarAlumnoBotonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EliminarAlumnoBotonActionPerformed
        int filaSeleccionada = TablaContenido.getSelectedRow();
        
        if (filaSeleccionada >= 0) {
            // Obtener el objeto Alumno directamente del modelo
            Alumno alumno = modeloTabla.getAlumnoAt(filaSeleccionada);
            
            // Confirmación antes de eliminar
            int confirmacion = JOptionPane.showConfirmDialog(
                this, 
                "¿Está seguro que desea eliminar al alumno " + alumno.getNombre() + "?", 
                "Confirmar eliminación", 
                JOptionPane.YES_NO_OPTION);
            
            if (confirmacion == JOptionPane.YES_OPTION) {
                Facultad facultad = Facultad.getInstance();
                boolean eliminado = facultad.eliminarAlumno(alumno);
                
                if (eliminado) {
                    actualizarTabla();
                    JOptionPane.showMessageDialog(this, 
                        "Alumno eliminado correctamente", 
                        "Eliminación exitosa", 
                        JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "No se pudo eliminar el alumno", 
                        "Error al eliminar", 
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, 
                "Por favor seleccione un alumno para eliminar", 
                "Selección requerida", 
                JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_EliminarAlumnoBotonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> BarraDeCarreras;
    private javax.swing.JScrollPane ContTabla;
    private javax.swing.JButton CrearAlumnoBoton;
    private javax.swing.JButton EliminarAlumnoBoton;
    private javax.swing.JPanel Fondo;
    private javax.swing.JPanel Ojo;
    private javax.swing.JTable TablaContenido;
    // End of variables declaration//GEN-END:variables
}
