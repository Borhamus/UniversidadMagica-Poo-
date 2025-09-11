/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package integradorobjetos.vista;

import integradorobjetos.controlador.CarreraService;
import integradorobjetos.modelo.Alumno;
import integradorobjetos.modelo.Carrera;
import integradorobjetos.modelo.Facultad;
import integradorobjetos.vista.OjoMagico;
import integradorobjetos.vista.Style;
import integradorobjetos.vista.VistaAlumno2;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JLayer;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Borhamus
 */
public class VistaAlumno3 extends javax.swing.JPanel {
    private Alumno alumno; //
    
    /**
     * Creates new form VistaAlumno3
     */
    public VistaAlumno3(Alumno alumno) {
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
        Ojo.setPreferredSize(new Dimension(250, 40)); // Alto de 100px para que se vea el ojo
        
        // Cargar datos del alumno y carreras
        cargarDatosAlumno();
        cargarTablaCarreras();
        
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
    }
    
    // Nuevo método para cargar la tabla de carreras
    private void cargarTablaCarreras() {
        // Obtener todas las carreras disponibles (usando Facultad)
        Facultad facultad = Facultad.getInstance();
        List<Carrera> carreras = facultad.getCarreras(); // Necesitarás este método en Facultad
        
        // Crear modelo para la tabla
        CarreraTableModel modelo = new CarreraTableModel(carreras);
        TablaCarreras.setModel(modelo); // Asumiendo que tienes una tabla llamada TablaCarreras
        
        // Configurar columnas
        TablaCarreras.getColumnModel().getColumn(0).setPreferredWidth(400); // Nombre de la carrera
        
        // Después de actualizar, aplicamos el estilo
        aplicarEstiloTablas();
        
    }
    
    
    
    /**
     * Aplica el estilo mágico a todas las tablas de esta vista
    */
    private void aplicarEstiloTablas() {
        // Aplicar estilo a la tabla principal
        Style.estiloTablaMagico(TablaCarreras, new Style.TableConfigurador() {
            @Override
            public void configurar(JTable tabla) {
                // Configurar anchos de columna específicos para esta tabla
                tabla.getColumnModel().getColumn(0).setPreferredWidth(250); // Nombre de Carreras
            }
        });}
    
    // Modelo de tabla para carreras
    class CarreraTableModel extends AbstractTableModel {
        private List<Carrera> carreras;
        private String[] columnNames = {"Nombre de la Carrera"};
        
        public CarreraTableModel(List<Carrera> carreras) {
            this.carreras = new ArrayList<>(carreras);
        }
        
        @Override
        public int getRowCount() {
            return carreras.size();
        }
        
        @Override
        public int getColumnCount() {
            return columnNames.length;
        }
        
        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Carrera carrera = carreras.get(rowIndex);
            return carrera.getNombre();
        }
        
        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }
        
        // MÉTODO FALTANTE: Agregar este método para obtener la carrera en una fila específica
        public Carrera getCarreraAt(int row) {
            if (row >= 0 && row < carreras.size()) {
                return carreras.get(row);
            }
            return null;
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
        MarcoDeTexto = new javax.swing.JPanel();
        Nombre = new javax.swing.JTextField();
        Dni = new javax.swing.JTextField();
        CF = new javax.swing.JTextField();
        CC = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        TablaCarreras = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
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

        TablaCarreras.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(TablaCarreras);

        jButton1.setText("Inscribir a Carrera");
        jButton1.setFocusable(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        Ojo.setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout OjoLayout = new javax.swing.GroupLayout(Ojo);
        Ojo.setLayout(OjoLayout);
        OjoLayout.setHorizontalGroup(
            OjoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 250, Short.MAX_VALUE)
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
                    .addComponent(jScrollPane1)
                    .addGroup(FondoLayout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Ojo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        FondoLayout.setVerticalGroup(
            FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FondoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(MarcoDeTexto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Ojo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
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
            .addGap(0, 398, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(Fondo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // 1. Verificar si hay una carrera seleccionada
        int selectedRow = TablaCarreras.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(
                this, 
                "Primero debe seleccionar una carrera", 
                "Selección requerida", 
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }
        
        // 2. Obtener la carrera seleccionada
        CarreraTableModel modelo = (CarreraTableModel) TablaCarreras.getModel();
        Carrera carreraSeleccionada = modelo.getCarreraAt(selectedRow);
        
        // 3. Usar CarreraService para inscribir al alumno
        CarreraService service = new CarreraService();
        boolean inscripcionExitosa = service.inscribirAlumnoEnCarrera(alumno, carreraSeleccionada);
        
        // 4. Manejar el resultado
        if (inscripcionExitosa) {
            // Mostrar mensaje de éxito
            JOptionPane.showMessageDialog(
                this, 
                "Carrera Inscripta: " + carreraSeleccionada.getNombre(), 
                "Inscripción Exitosa", 
                JOptionPane.INFORMATION_MESSAGE
            );
            
            // Volver a VistaAlumno2 con el alumno actualizado
            VistaAlumno2 panel = new VistaAlumno2(alumno);
            Fondo.removeAll();
            Fondo.setLayout(new BorderLayout());
            Fondo.add(panel, BorderLayout.CENTER);
            Fondo.revalidate();
            Fondo.repaint();
        } else {
            // Mostrar mensaje de error
            JOptionPane.showMessageDialog(
                this, 
                "No se pudo realizar la inscripción. Verifica que cumplas con los requisitos.", 
                "Error de Inscripción", 
                JOptionPane.ERROR_MESSAGE
            );
        }
    
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField CC;
    private javax.swing.JTextField CF;
    private javax.swing.JTextField Dni;
    private javax.swing.JPanel Fondo;
    private javax.swing.JPanel MarcoDeTexto;
    private javax.swing.JTextField Nombre;
    private javax.swing.JPanel Ojo;
    private javax.swing.JTable TablaCarreras;
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
