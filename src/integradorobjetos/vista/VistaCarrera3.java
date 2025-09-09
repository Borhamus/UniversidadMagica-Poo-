/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package integradorobjetos.vista;

import integradorobjetos.modelo.Carrera;
import integradorobjetos.modelo.Materia;
import integradorobjetos.modelo.Plan;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JLayer;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Borhamus
 */
public class VistaCarrera3 extends javax.swing.JPanel {
    private Carrera carrera;
    /**
     * Creates new form VistaCarrera3
     */
    public VistaCarrera3(Carrera carrera) {
        this.carrera = carrera;
        
        // Primero inicializamos los componentes
        initComponents();
        
        // Usamos SwingUtilities para ejecutar las configuraciones después de que el componente sea visible
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                configurarPanel();
                cargarDatosCarrera();
            }
        });
    }
    
    private void configurarPanel() {
        // Configurar el panel Fondo
        Fondo.setOpaque(false);
        
        // Establecer dimensiones exactas para el panel principal
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
        Ojo.setPreferredSize(new Dimension(597, 38)); // Alto de 100px para que se vea el ojo
        
        // Limpiar el campo Optativas antes de cargar los datos
        Optativas.setText("");
        
        // Agregar KeyListener para detectar la tecla Enter
        Optativas.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    guardarOptativasMinimas();
                }
            }
        });
    }
    
    private void cargarDatosCarrera() {
        // 1. Cargar nombre de la carrera
        Nombre.setText("Nombre de la Carrera: " + carrera.getNombre());
        // 2. Calcular y cargar la carga horaria total
        int cargaHorariaTotal = 0;
        for (Materia materia : carrera.getMaterias()) {
            cargaHorariaTotal += materia.getCargaHoraria();
        }
        CargaHoraria.setText("Carga Horaria: " + cargaHorariaTotal + " Hs");
        // 3. Cargar el plan de estudio
        String nombrePlan = "Sin plan";
        Plan plan = carrera.getPlanDeEstudio();
        if (plan != null) {
            nombrePlan = plan.getClass().getSimpleName(); // Obtiene PlanA, PlanB, etc.
        }
        PlanDeEstudio.setText("Plan de Estudio: " + nombrePlan);
        // 4. Cargar las optativas mínimas (solo el número, sin texto adicional)
        Optativas.setText(String.valueOf(carrera.getOptativasMinimas()));
        // 5. Cargar la tabla de materias
        cargarTablaMaterias();
    }
    
    private void cargarTablaMaterias() {
        // Crear el modelo de tabla para las materias
        MateriasCarreraTableModel modelo = new MateriasCarreraTableModel(carrera.getMaterias());
        jTable1.setModel(modelo);
        
        // Configurar columnas
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(200); // Nombre de Materia
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(100); // Cuatrimestre
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(100); // Es Optativa
        jTable1.getColumnModel().getColumn(3).setPreferredWidth(100); // Carga Horaria
        jTable1.getColumnModel().getColumn(4).setPreferredWidth(200); // Correlativas
        jTable1.getColumnModel().getColumn(5).setPreferredWidth(80);  // Editar
        
        // Configurar renderizador para la columna "Editar"
        jTable1.getColumnModel().getColumn(5).setCellRenderer(new DefaultTableCellRenderer() {
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
    
    // Modelo de tabla para las materias de la carrera
    class MateriasCarreraTableModel extends AbstractTableModel {
        private List<Materia> materias;
        private String[] columnNames = {"Nombre de Materia", "Cuatrimestre", "Es Optativa", "Carga Horaria", "Correlativas", "Editar"};
        
        public MateriasCarreraTableModel(List<Materia> materias) {
            this.materias = new ArrayList<>(materias);
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
                case 5: // Editar
                    return "Editar";
                default:
                    return null;
            }
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
    }
    
    // Método para actualizar las optativas mínimas
    private void OptativasFocusGained(java.awt.event.FocusEvent evt) {                                           
        // Cuando el JTextField gana el foco, seleccionamos todo el texto
        Optativas.selectAll();
    }  
    
    private void OptativasFocusLost(java.awt.event.FocusEvent evt) {                                          
        // Cuando el JTextField pierde el foco, validamos y actualizamos el valor
        try {
            String textoOptativas = Optativas.getText();
            if (textoOptativas != null && !textoOptativas.trim().isEmpty()) {
                int nuevasOptativas = Integer.parseInt(textoOptativas);
                // Validar que sea un número positivo
                if (nuevasOptativas < 0) {
                    // Restaurar el valor anterior (solo el número)
                    Optativas.setText(String.valueOf(carrera.getOptativasMinimas()));
                    return;
                }
                // Validar que no haya más optativas mínimas que materias optativas
                int materiasOptativasExistentes = contarMateriasOptativas();
                if (nuevasOptativas > materiasOptativasExistentes) {
                    // Restaurar el valor anterior (solo el número)
                    Optativas.setText(String.valueOf(carrera.getOptativasMinimas()));
                    return;
                }
                // Si todo es válido, actualizar el valor
                carrera.setOptativasMinimas(nuevasOptativas);
            } else {
                // Restaurar el valor anterior (solo el número)
                Optativas.setText(String.valueOf(carrera.getOptativasMinimas()));
            }
        } catch (NumberFormatException e) {
            // Restaurar el valor anterior (solo el número)
            Optativas.setText(String.valueOf(carrera.getOptativasMinimas()));
        }
    }                                         
    
    private int contarMateriasOptativas() {
        int contador = 0;
        for (Materia materia : carrera.getMaterias()) {
            if (materia.esOptativa()) {
                contador++;
            }
        }
        return contador;
    }
    
    private void guardarOptativasMinimas() {
        try {
            // Obtener el texto del campo
            String textoOptativas = Optativas.getText();
            // Validar que no esté vacío
            if (textoOptativas == null || textoOptativas.trim().isEmpty()) {
                JOptionPane.showMessageDialog(
                    this, 
                    "El campo de optativas mínimas no puede estar vacío", 
                    "Error de validación", 
                    JOptionPane.ERROR_MESSAGE
                );
                // Restaurar el valor anterior (solo el número)
                Optativas.setText(String.valueOf(carrera.getOptativasMinimas()));
                return;
            }
            // Convertir a número
            int nuevasOptativas = Integer.parseInt(textoOptativas);
            // Validar que sea un número positivo
            if (nuevasOptativas < 0) {
                JOptionPane.showMessageDialog(
                    this, 
                    "El número de optativas mínimas debe ser mayor o igual a cero", 
                    "Error de validación", 
                    JOptionPane.ERROR_MESSAGE
                );
                // Restaurar el valor anterior (solo el número)
                Optativas.setText(String.valueOf(carrera.getOptativasMinimas()));
                return;
            }
            // Validar que no haya más optativas mínimas que materias optativas
            int materiasOptativasExistentes = contarMateriasOptativas();
            if (nuevasOptativas > materiasOptativasExistentes) {
                JOptionPane.showMessageDialog(
                    this, 
                    "El número de optativas mínimas no puede ser mayor que la cantidad de materias optativas existentes\n" +
                    "Materias optativas existentes: " + materiasOptativasExistentes + "\n" +
                    "Optativas mínimas solicitadas: " + nuevasOptativas, 
                    "Error de validación", 
                    JOptionPane.ERROR_MESSAGE
                );
                // Restaurar el valor anterior (solo el número)
                Optativas.setText(String.valueOf(carrera.getOptativasMinimas()));
                return;
            }
            // Actualizar el valor en la carrera
            int valorAnterior = carrera.getOptativasMinimas();
            carrera.setOptativasMinimas(nuevasOptativas);
            // Mostrar mensaje de confirmación
            JOptionPane.showMessageDialog(
                this, 
                "El número de optativas mínimas se ha actualizado correctamente\n" +
                "Valor anterior: " + valorAnterior + "\n" +
                "Valor nuevo: " + nuevasOptativas + "\n" +
                "Materias optativas existentes: " + materiasOptativasExistentes, 
                "Actualización exitosa", 
                JOptionPane.INFORMATION_MESSAGE
            );
        } catch (NumberFormatException e) {
            // Si no es un número válido
            JOptionPane.showMessageDialog(
                this, 
                "Por favor ingrese un número válido en el campo de optativas mínimas", 
                "Error de formato", 
                JOptionPane.ERROR_MESSAGE
            );
            // Restaurar el valor anterior (solo el número)
            Optativas.setText(String.valueOf(carrera.getOptativasMinimas()));
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
        CargaHoraria = new javax.swing.JTextField();
        PlanDeEstudio = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        Optativas = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        CrearMateriaBoton = new javax.swing.JButton();
        EliminarMateriaBoton = new javax.swing.JButton();
        Ojo = new javax.swing.JPanel();

        setMaximumSize(new java.awt.Dimension(846, 398));
        setMinimumSize(new java.awt.Dimension(846, 398));

        Fondo.setMaximumSize(new java.awt.Dimension(846, 398));
        Fondo.setMinimumSize(new java.awt.Dimension(846, 398));

        MarcoDeTexto.setBackground(new java.awt.Color(0, 153, 153));

        Nombre.setText("Nombre de Carrera:");
        Nombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NombreActionPerformed(evt);
            }
        });

        CargaHoraria.setText("Carga Horaria:");

        PlanDeEstudio.setText("Plan de Estudio:");

        jLabel1.setText("Optativas Minimas:");

        Optativas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OptativasActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout MarcoDeTextoLayout = new javax.swing.GroupLayout(MarcoDeTexto);
        MarcoDeTexto.setLayout(MarcoDeTextoLayout);
        MarcoDeTextoLayout.setHorizontalGroup(
            MarcoDeTextoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MarcoDeTextoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(MarcoDeTextoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(MarcoDeTextoLayout.createSequentialGroup()
                        .addComponent(CargaHoraria, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(PlanDeEstudio, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(Nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 479, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(MarcoDeTextoLayout.createSequentialGroup()
                        .addGap(114, 114, 114)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Optativas, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        MarcoDeTextoLayout.setVerticalGroup(
            MarcoDeTextoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MarcoDeTextoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(MarcoDeTextoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CargaHoraria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PlanDeEstudio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(MarcoDeTextoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Optativas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addContainerGap(12, Short.MAX_VALUE))
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

        CrearMateriaBoton.setText("Crear Materia");

        EliminarMateriaBoton.setText("Eliminar Materia");

        Ojo.setBackground(new java.awt.Color(0, 0, 0));
        Ojo.setMaximumSize(new java.awt.Dimension(597, 38));
        Ojo.setMinimumSize(new java.awt.Dimension(597, 38));

        javax.swing.GroupLayout OjoLayout = new javax.swing.GroupLayout(Ojo);
        Ojo.setLayout(OjoLayout);
        OjoLayout.setHorizontalGroup(
            OjoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        OjoLayout.setVerticalGroup(
            OjoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 38, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout FondoLayout = new javax.swing.GroupLayout(Fondo);
        Fondo.setLayout(FondoLayout);
        FondoLayout.setHorizontalGroup(
            FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FondoLayout.createSequentialGroup()
                .addContainerGap(39, Short.MAX_VALUE)
                .addGroup(FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(FondoLayout.createSequentialGroup()
                        .addComponent(CrearMateriaBoton, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(EliminarMateriaBoton, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Ojo, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 780, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(FondoLayout.createSequentialGroup()
                        .addComponent(MarcoDeTexto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(151, 151, 151)))
                .addGap(35, 35, 35))
        );
        FondoLayout.setVerticalGroup(
            FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FondoLayout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(MarcoDeTexto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Ojo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(EliminarMateriaBoton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(CrearMateriaBoton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 854, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(Fondo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 398, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(Fondo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void NombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NombreActionPerformed

    private void OptativasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OptativasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_OptativasActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField CargaHoraria;
    private javax.swing.JButton CrearMateriaBoton;
    private javax.swing.JButton EliminarMateriaBoton;
    private javax.swing.JPanel Fondo;
    private javax.swing.JPanel MarcoDeTexto;
    private javax.swing.JTextField Nombre;
    private javax.swing.JPanel Ojo;
    private javax.swing.JTextField Optativas;
    private javax.swing.JTextField PlanDeEstudio;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
