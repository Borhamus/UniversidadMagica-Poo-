/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package integradorobjetos.vista;

import integradorobjetos.modelo.Alumno;
import integradorobjetos.modelo.Carrera;
import integradorobjetos.modelo.Facultad;
import integradorobjetos.modelo.Materia;
import integradorobjetos.modelo.Plan;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JLayer;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Borhamus
 */
public class VistaCarrera1 extends javax.swing.JPanel {

    /**
     * Creates new form VistaCarrera1
     */
     public VistaCarrera1() {
        initComponents();
        
        // Aplicar estilo mágico a los botones
        Style.estiloBotonMagico2(CrearCarreraBoton);
        Style.estiloBotonMagico2(EliminarCarreraBoton);

        // CAMBIAR LAS FUENTES DE LOS BOTONES
        CrearCarreraBoton.setFont(Style.FUENTE_NORMAL);
        EliminarCarreraBoton.setFont(Style.FUENTE_NORMAL);
        
        
        Fondo.setOpaque(false);
        
        // Crear el componente del ojo mágico
        OjoMagico ojoMagico = new OjoMagico();
        
        // Configurar el panel Ojo para que contenga nuestro ojo mágico
        Ojo.removeAll(); // Limpiar el panel
        Ojo.setLayout(new BorderLayout()); // Establecer un layout
        Ojo.add(ojoMagico, BorderLayout.CENTER); // Añadir el ojo mágico
        
        // IMPORTANTE: Establecer un tamaño preferido para el panel Ojo
        Ojo.setPreferredSize(new Dimension(443, 75)); // Alto de 100px para que se vea el ojo
        
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
        
        // Cargar datos en la tabla
        cargarDatosTabla();
    }
    
    private void cargarDatosTabla() {
        // Obtener la instancia de la facultad
        Facultad facultad = Facultad.getInstance();
        List<Carrera> carreras = facultad.getCarreras();
        
        // Crear el modelo de la tabla con las columnas necesarias
        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Solo la columna "Editar" será editable
                return column == 3;
            }
        };
        
        // Establecer los nombres de las columnas
        modelo.setColumnIdentifiers(new Object[]{"Nombre de Carrera", "Carga Horaria", "Plan de Estudio", "Editar"});
        
        // Llenar la tabla con los datos de las carreras
        for (Carrera carrera : carreras) {
            // Calcular la carga horaria total de la carrera
            int cargaHorariaTotal = 0;
            for (Materia materia : carrera.getMaterias()) {
                cargaHorariaTotal += materia.getCargaHoraria();
            }
            
            // Obtener el nombre del plan de estudio
            String nombrePlan = "Sin plan";
            Plan plan = carrera.getPlanDeEstudio();
            if (plan != null) {
                nombrePlan = plan.getClass().getSimpleName(); // Obtiene PlanA, PlanB, etc.
            }
            
            // Agregar la fila a la tabla
            modelo.addRow(new Object[]{
                carrera.getNombre(),
                cargaHorariaTotal,
                nombrePlan,
                "Editar"
            });
        }
        
        // Asignar el modelo a la tabla
        jTable1.setModel(modelo);
        
        // Ajustar el ancho de las columnas
        TableColumnModel columnModel = jTable1.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(300); // Nombre de Carrera
        columnModel.getColumn(1).setPreferredWidth(100);  // Carga Horaria
        columnModel.getColumn(2).setPreferredWidth(150);  // Plan de Estudio
        columnModel.getColumn(3).setPreferredWidth(80);   // Editar
        
        // Agregar MouseListener para manejar clics en la columna "Editar"
        jTable1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int column = jTable1.columnAtPoint(e.getPoint());
                int row = jTable1.rowAtPoint(e.getPoint());
                // Verificamos si se hizo clic en la columna "Editar" (índice 3)
                if (column == 3) {
                    // Obtenemos el nombre de la carrera de la fila seleccionada
                    String nombreCarrera = (String) jTable1.getValueAt(row, 0);
                    // Buscamos la carrera en la facultad
                    Facultad facultad = Facultad.getInstance();
                    Carrera carrera = facultad.buscarCarrera(nombreCarrera);
                    if (carrera != null) {
                        // Creamos la VistaCarrera3 y le pasamos la carrera
                        VistaCarrera3 vistaCarrera3 = new VistaCarrera3(carrera);
                        // Reemplazamos el contenido del panel Fondo con esta nueva vista
                        Fondo.removeAll();
                        Fondo.setLayout(new BorderLayout());
                        Fondo.add(vistaCarrera3, BorderLayout.CENTER);
                        Fondo.revalidate();
                        Fondo.repaint();
                    }
                }
            }
        });
        
        // Configurar renderizador para la columna "Editar" para que aparezca como un enlace
        jTable1.getColumnModel().getColumn(3).setCellRenderer(new DefaultTableCellRenderer() {
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
        
        // Después de actualizar, aplicamos el estilo
        aplicarEstiloTablas();
    }
    
    /**
     * Aplica el estilo mágico a todas las tablas de esta vista
    */
    private void aplicarEstiloTablas() {
        // Aplicar estilo a la tabla principal
        Style.estiloTablaMagico(jTable1, new Style.TableConfigurador() {
            @Override
            public void configurar(JTable tabla) {
                // Configurar anchos de columna específicos para esta tabla
                tabla.getColumnModel().getColumn(0).setPreferredWidth(250); // Nombre de Carreras
            }
        });}
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Fondo = new javax.swing.JPanel();
        Ojo = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        CrearCarreraBoton = new javax.swing.JButton();
        EliminarCarreraBoton = new javax.swing.JButton();

        setMaximumSize(new java.awt.Dimension(846, 398));
        setMinimumSize(new java.awt.Dimension(846, 398));

        Fondo.setMaximumSize(new java.awt.Dimension(846, 398));
        Fondo.setMinimumSize(new java.awt.Dimension(846, 398));

        Ojo.setBackground(new java.awt.Color(0, 0, 0));
        Ojo.setMaximumSize(new java.awt.Dimension(443, 75));
        Ojo.setMinimumSize(new java.awt.Dimension(443, 75));

        javax.swing.GroupLayout OjoLayout = new javax.swing.GroupLayout(Ojo);
        Ojo.setLayout(OjoLayout);
        OjoLayout.setHorizontalGroup(
            OjoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 443, Short.MAX_VALUE)
        );
        OjoLayout.setVerticalGroup(
            OjoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 75, Short.MAX_VALUE)
        );

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTable1);

        CrearCarreraBoton.setText("Crear Carrera");
        CrearCarreraBoton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CrearCarreraBotonActionPerformed(evt);
            }
        });

        EliminarCarreraBoton.setText("EliminarCarrera");
        EliminarCarreraBoton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EliminarCarreraBotonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout FondoLayout = new javax.swing.GroupLayout(Fondo);
        Fondo.setLayout(FondoLayout);
        FondoLayout.setHorizontalGroup(
            FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FondoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(FondoLayout.createSequentialGroup()
                        .addComponent(CrearCarreraBoton, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(EliminarCarreraBoton, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                        .addComponent(Ojo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)))
                .addContainerGap())
        );
        FondoLayout.setVerticalGroup(
            FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FondoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(FondoLayout.createSequentialGroup()
                        .addGap(62, 62, 62)
                        .addGroup(FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(CrearCarreraBoton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(EliminarCarreraBoton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(FondoLayout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(Ojo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(58, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 846, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(Fondo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 410, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(Fondo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void CrearCarreraBotonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CrearCarreraBotonActionPerformed
        VistaCarrera2 panel = new VistaCarrera2();
        Fondo.removeAll();
        Fondo.setLayout(new BorderLayout());
        Fondo.add(panel, BorderLayout.CENTER);
        Fondo.revalidate();
        Fondo.repaint();
    }//GEN-LAST:event_CrearCarreraBotonActionPerformed

    private void EliminarCarreraBotonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EliminarCarreraBotonActionPerformed
        // Verificar si hay una fila seleccionada
        int filaSeleccionada = jTable1.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, 
                "Debe seleccionar una carrera para eliminar", 
                "Error de Selección", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Obtener el nombre de la carrera seleccionada
        String nombreCarrera = (String) jTable1.getValueAt(filaSeleccionada, 0);

        // Confirmar la eliminación
        int confirmacion = JOptionPane.showConfirmDialog(this, 
            "¿Está seguro que desea eliminar la carrera " + nombreCarrera + "?\n" +
            "Todos los alumnos inscriptos quedarán sin carrera asignada.", 
            "Confirmar Eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (confirmacion != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            // Obtener la instancia de la facultad
            Facultad facultad = Facultad.getInstance();

            // Buscar la carrera a eliminar
            Carrera carreraAEliminar = facultad.buscarCarrera(nombreCarrera);
            if (carreraAEliminar == null) {
                JOptionPane.showMessageDialog(this, 
                    "No se encontró la carrera especificada", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Desinscribir a todos los alumnos de esta carrera
            desinscribirAlumnosDeCarrera(carreraAEliminar);

            // Eliminar la carrera de la facultad
            facultad.eliminarCarrera(carreraAEliminar);

            // Mostrar mensaje de éxito
            JOptionPane.showMessageDialog(this, 
                "Carrera eliminada con éxito\n" +
                "Los alumnos han sido desinscriptos", 
                "Eliminación Exitosa", JOptionPane.INFORMATION_MESSAGE);

            // Actualizar la tabla
            cargarDatosTabla();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Error al eliminar carrera: " + ex.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void desinscribirAlumnosDeCarrera(Carrera carrera) {
        // Obtener todos los alumnos de la facultad
        List<Alumno> alumnos = Facultad.getInstance().getAlumnos();

        // Recorrer todos los alumnos
        for (Alumno alumno : alumnos) {
            // Verificar si el alumno está inscripto en esta carrera
            if (alumno.getCarreraInscripta() != null && 
                alumno.getCarreraInscripta().getNombre().equals(carrera.getNombre())) {

                // Desinscribir al alumno (dejar sin carrera)
                alumno.setCarreraInscripta(null);
            }
        }
    }//GEN-LAST:event_EliminarCarreraBotonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton CrearCarreraBoton;
    private javax.swing.JButton EliminarCarreraBoton;
    private javax.swing.JPanel Fondo;
    private javax.swing.JPanel Ojo;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
