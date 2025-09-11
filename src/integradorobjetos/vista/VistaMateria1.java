/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package integradorobjetos.vista;

import integradorobjetos.modelo.Carrera;
import integradorobjetos.modelo.Facultad;
import integradorobjetos.modelo.Materia;
import integradorobjetos.vista.OjoMagico;
import integradorobjetos.vista.Style;
import integradorobjetos.vista.VistaCarrera3;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JComponent;
import javax.swing.JLayer;
import javax.swing.JOptionPane;

/**
 *
 * @author Borhamus
 */
public class VistaMateria1 extends javax.swing.JPanel {
    private Materia materia;
    private Carrera carrera;
    private boolean esModoEdicion;
    
    /**
     * Creates new form VistaMateria1
     */
    public VistaMateria1(Materia materia, Carrera carrera) {
        this.materia = materia;
        this.carrera = carrera;
        this.esModoEdicion = true;
        initComponents();
        
        // Aplicar estilo mágico a los botones
        Style.estiloBotonMagico2(GuardarCambiosBoton);

        // CAMBIAR LAS FUENTES DE LOS BOTONES
        GuardarCambiosBoton.setFont(Style.FUENTE_NORMAL);
        
        // Configuraciones adicionales
        configurarPanel();
        
        // Cargar los datos de la materia
        cargarDatosMateria();
    }
    
    /**
     * Constructor para crear una nueva materia
     */
    public VistaMateria1(Carrera carrera) {
        this.carrera = carrera;
        this.esModoEdicion = false;
        initComponents();
        
        // Configuraciones adicionales
        configurarPanel();
        
        // Inicializar campos vacíos
        inicializarCamposVacios();
    }
    
    private void configurarPanel() {
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
        Ojo.setPreferredSize(new Dimension(597, 38)); // Alto de 100px para que se vea el ojo
        
        // Configurar el botón Swap
        Swap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SwapActionPerformed(evt);
            }
        });
        
        // Configurar los checkboxes para que solo uno pueda estar seleccionado
        Si.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SiActionPerformed(evt);
            }
        });
        
        No.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NoActionPerformed(evt);
            }
        });
    }
    
    private void inicializarCamposVacios() {
        // Limpiar todos los campos
        NombreDeMateria.setText("");
        CargaHoraria.setText("");
        ProfesorAsignado.setText("");
        Contenido.setText("");
        Cuatrimestre.setText("");
        
        // Deseleccionar ambos checkboxes
        Si.setSelected(false);
        No.setSelected(false);
        
        // Limpiar listas
        ListaDeCorrelativas.removeAll();
        ListaDeMaterias.removeAll();
        
        // Cargar todas las materias de la carrera en ListaDeMaterias
        for (Materia m : carrera.getMaterias()) {
            ListaDeMaterias.add(m.getNombre());
        }
    }
    
    private void cargarDatosMateria() {
        if (!esModoEdicion) return;
        
        // 1. Cargar nombre de la materia
        NombreDeMateria.setText(materia.getNombre());
        
        // 2. Cargar carga horaria
        CargaHoraria.setText(String.valueOf(materia.getCargaHoraria()));
        
        // 3. Cargar profesor asignado
        ProfesorAsignado.setText(materia.getProfesor());
        
        // 4. Cargar contenido
        Contenido.setText(materia.getContenido());
        
        // 5. Cargar cuatrimestre
        Cuatrimestre.setText(String.valueOf(materia.getCuatrimestre()));
        
        // 6. Cargar si es optativa
        if (materia.esOptativa()) {
            Si.setSelected(true);
            No.setSelected(false);
        } else {
            Si.setSelected(false);
            No.setSelected(true);
        }
        
        // 7. Cargar lista de correlativas
        ListaDeCorrelativas.removeAll();
        for (Materia correlativa : materia.getCorrelativas()) {
            ListaDeCorrelativas.add(correlativa.getNombre());
        }
        
        // 8. Cargar lista de materias de la carrera (excluyendo la materia actual y sus correlativas)
        ListaDeMaterias.removeAll();
        for (Materia m : carrera.getMaterias()) {
            // No agregar la materia actual ni las que ya son correlativas
            if (!m.equals(materia) && !materia.getCorrelativas().contains(m)) {
                ListaDeMaterias.add(m.getNombre());
            }
        }
    }
    
    private void SwapActionPerformed(java.awt.event.ActionEvent evt) {
        // Verificar si hay una materia seleccionada en ListaDeMaterias
        if (ListaDeMaterias.getSelectedIndex() != -1) {
            // Mover de ListaDeMaterias a ListaDeCorrelativas
            String materiaSeleccionada = ListaDeMaterias.getSelectedItem();
            Materia correlativa = buscarMateriaPorNombre(materiaSeleccionada);
            
            // Obtener el cuatrimestre de la materia actual
            int cuatrimestreActual;
            if (esModoEdicion) {
                cuatrimestreActual = materia.getCuatrimestre();
            } else {
                try {
                    cuatrimestreActual = Integer.parseInt(Cuatrimestre.getText());
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, 
                        "El cuatrimestre debe ser un número válido", 
                        "Error de validación", 
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            
            // Validar que la correlativa sea de un cuatrimestre anterior
            if (!validarCorrelativaPorCuatrimestre(cuatrimestreActual, correlativa)) {
                JOptionPane.showMessageDialog(this, 
                    "No se puede agregar como correlativa una materia de un cuatrimestre igual o posterior", 
                    "Correlativa inválida", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Si es válida, agregarla a la lista de correlativas
            ListaDeCorrelativas.add(materiaSeleccionada);
            ListaDeMaterias.remove(materiaSeleccionada);
        } 
        // Verificar si hay una materia seleccionada en ListaDeCorrelativas
        else if (ListaDeCorrelativas.getSelectedIndex() != -1) {
            // Mover de ListaDeCorrelativas a ListaDeMaterias
            String correlativaSeleccionada = ListaDeCorrelativas.getSelectedItem();
            ListaDeMaterias.add(correlativaSeleccionada);
            ListaDeCorrelativas.remove(correlativaSeleccionada);
        }
    }
    
    private void actualizarMateriaExistente() {
        // Actualizar los datos de la materia con los valores de los campos
        materia.setNombre(NombreDeMateria.getText());
        
        try {
            materia.setCargaHoraria(Integer.parseInt(CargaHoraria.getText()));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "La carga horaria debe ser un número válido", 
                "Error de validación", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        materia.setProfesor(ProfesorAsignado.getText());
        materia.setContenido(Contenido.getText());
        
        try {
            int nuevoCuatrimestre = Integer.parseInt(Cuatrimestre.getText());
            materia.setCuatrimestre(nuevoCuatrimestre);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "El cuatrimestre debe ser un número válido", 
                "Error de validación", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Actualizar si es optativa
        boolean esOptativa = Si.isSelected();
        materia.setOptativa(esOptativa);
        
        // Validar correlativas antes de actualizar
        if (!validarTodasCorrelativas(materia)) {
            return;
        }
        
        // Actualizar correlativas
        // Primero, limpiar la lista actual de correlativas
        materia.getCorrelativas().clear();
        
        // Luego, agregar las correlativas de la lista
        for (int i = 0; i < ListaDeCorrelativas.getItemCount(); i++) {
            String nombreCorrelativa = ListaDeCorrelativas.getItem(i);
            Materia correlativa = buscarMateriaPorNombre(nombreCorrelativa);
            if (correlativa != null) {
                materia.agregarCorrelativa(correlativa);
            }
        }
        
        // Mostrar mensaje de éxito
        JOptionPane.showMessageDialog(this, 
            "Los cambios se han guardado correctamente", 
            "Guardado exitoso", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void crearNuevaMateria() {
        // Validar que todos los campos estén completos
        if (NombreDeMateria.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "El nombre de la materia no puede estar vacío", 
                "Error de validación", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (ProfesorAsignado.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "El profesor asignado no puede estar vacío", 
                "Error de validación", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (Contenido.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "El contenido no puede estar vacío", 
                "Error de validación", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (!Si.isSelected() && !No.isSelected()) {
            JOptionPane.showMessageDialog(this, 
                "Debe especificar si la materia es optativa", 
                "Error de validación", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            // Crear la nueva materia
            String nombre = NombreDeMateria.getText();
            int cargaHoraria = Integer.parseInt(CargaHoraria.getText());
            String profesor = ProfesorAsignado.getText();
            String contenido = Contenido.getText();
            int cuatrimestre = Integer.parseInt(Cuatrimestre.getText());
            boolean esOptativa = Si.isSelected();
            
            // Crear la materia usando el método de la carrera
            Materia nuevaMateria = carrera.crearMateria(nombre, contenido, cargaHoraria, cuatrimestre, profesor);
            nuevaMateria.setOptativa(esOptativa);
            
            // Validar correlativas antes de agregarlas
            if (!validarTodasCorrelativas(nuevaMateria)) {
                return;
            }
            
            // Agregar correlativas
            for (int i = 0; i < ListaDeCorrelativas.getItemCount(); i++) {
                String nombreCorrelativa = ListaDeCorrelativas.getItem(i);
                Materia correlativa = buscarMateriaPorNombre(nombreCorrelativa);
                if (correlativa != null) {
                    nuevaMateria.agregarCorrelativa(correlativa);
                }
            }
            
            // Mostrar mensaje de éxito
            JOptionPane.showMessageDialog(this, 
                "La materia se ha creado correctamente", 
                "Creación exitosa", 
                JOptionPane.INFORMATION_MESSAGE);
            
            // Volver a la vista de la carrera
            VistaCarrera3 vistaCarrera3 = new VistaCarrera3(carrera);
            Fondo.removeAll();
            Fondo.setLayout(new BorderLayout());
            Fondo.add(vistaCarrera3, BorderLayout.CENTER);
            Fondo.revalidate();
            Fondo.repaint();
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "La carga horaria y el cuatrimestre deben ser números válidos", 
                "Error de validación", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Método auxiliar para buscar una materia por su nombre
    private Materia buscarMateriaPorNombre(String nombre) {
        for (Materia m : carrera.getMaterias()) {
            if (m.getNombre().equals(nombre)) {
                return m;
            }
        }
        return null;
    }
    
    /**
     * Valida que una materia pueda ser correlativa de otra según el cuatrimestre
     * @param materiaActual La materia a la que se quiere agregar la correlativa
     * @param correlativa La materia que se quiere agregar como correlativa
     * @return true si es válida, false si no lo es
     */
    private boolean validarCorrelativaPorCuatrimestre(int cuatrimestreActual, Materia correlativa) {
        // Si la materia actual es del cuatrimestre 1, no puede tener correlativas
        if (cuatrimestreActual == 1) {
            return false;
        }
        
        // La correlativa debe ser de un cuatrimestre anterior
        return correlativa.getCuatrimestre() < cuatrimestreActual;
    }
    
    /**
     * Valida todas las correlativas de la lista actual
     * @param materiaActual La materia a la que pertenecen las correlativas
     * @return true si todas son válidas, false si alguna no lo es
     */
    private boolean validarTodasCorrelativas(Materia materiaActual) {
        // Si la materia actual es del cuatrimestre 1, no puede tener correlativas
        if (materiaActual.getCuatrimestre() == 1) {
            if (ListaDeCorrelativas.getItemCount() > 0) {
                JOptionPane.showMessageDialog(this, 
                    "Una materia del primer cuatrimestre no puede tener correlativas", 
                    "Correlativas inválidas", 
                    JOptionPane.ERROR_MESSAGE);
                return false;
            }
            return true;
        }
        
        // Verificar cada correlativa
        for (int i = 0; i < ListaDeCorrelativas.getItemCount(); i++) {
            String nombreCorrelativa = ListaDeCorrelativas.getItem(i);
            Materia correlativa = buscarMateriaPorNombre(nombreCorrelativa);
            
            if (correlativa != null && !validarCorrelativaPorCuatrimestre(materiaActual.getCuatrimestre(), correlativa)) {
                JOptionPane.showMessageDialog(this, 
                    "La materia '" + nombreCorrelativa + "' no puede ser correlativa porque es de un cuatrimestre igual o posterior", 
                    "Correlativa inválida", 
                    JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        
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
        NombreDeMateria = new javax.swing.JTextField();
        CargaHoraria = new javax.swing.JTextField();
        ProfesorAsignado = new javax.swing.JTextField();
        Contenido = new javax.swing.JTextField();
        Cuatrimestre = new javax.swing.JTextField();
        ListaDeCorrelativas = new java.awt.List();
        ListaDeMaterias = new java.awt.List();
        Swap = new java.awt.Button();
        ContenidoText = new javax.swing.JLabel();
        CorrelativasText = new javax.swing.JLabel();
        MateriasText = new javax.swing.JLabel();
        NMateriaText = new javax.swing.JLabel();
        CargHorText = new javax.swing.JLabel();
        CuatrimestreText = new javax.swing.JLabel();
        EsOptativaText = new javax.swing.JLabel();
        Si = new javax.swing.JCheckBox();
        No = new javax.swing.JCheckBox();
        ProfeNombreText = new javax.swing.JLabel();
        GuardarCambiosBoton = new javax.swing.JButton();
        Ojo = new javax.swing.JPanel();

        setMaximumSize(new java.awt.Dimension(846, 398));
        setMinimumSize(new java.awt.Dimension(846, 398));

        Fondo.setMaximumSize(new java.awt.Dimension(854, 398));
        Fondo.setMinimumSize(new java.awt.Dimension(854, 398));
        Fondo.setPreferredSize(new java.awt.Dimension(854, 398));

        MarcoDeTexto.setBackground(new java.awt.Color(0, 153, 153));

        NombreDeMateria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NombreDeMateriaActionPerformed(evt);
            }
        });

        Swap.setLabel("<->");

        ContenidoText.setBackground(new java.awt.Color(0, 0, 0));
        ContenidoText.setForeground(new java.awt.Color(0, 0, 0));
        ContenidoText.setText("Contenido:");

        CorrelativasText.setBackground(new java.awt.Color(0, 0, 0));
        CorrelativasText.setForeground(new java.awt.Color(0, 0, 0));
        CorrelativasText.setText("Lista de Correlativas:");

        MateriasText.setBackground(new java.awt.Color(0, 0, 0));
        MateriasText.setForeground(new java.awt.Color(0, 0, 0));
        MateriasText.setText("Lista de Materias de la Carrera:");

        NMateriaText.setBackground(new java.awt.Color(0, 0, 0));
        NMateriaText.setForeground(new java.awt.Color(0, 0, 0));
        NMateriaText.setText("Nombre de Materia: ");

        CargHorText.setBackground(new java.awt.Color(0, 0, 0));
        CargHorText.setForeground(new java.awt.Color(0, 0, 0));
        CargHorText.setText("Carga Horaria: ");

        CuatrimestreText.setBackground(new java.awt.Color(0, 0, 0));
        CuatrimestreText.setForeground(new java.awt.Color(0, 0, 0));
        CuatrimestreText.setText("Cuatrimestre: ");

        EsOptativaText.setBackground(new java.awt.Color(0, 0, 0));
        EsOptativaText.setForeground(new java.awt.Color(0, 0, 0));
        EsOptativaText.setText("Es Optativa?:");

        Si.setText("Si");
        Si.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SiActionPerformed(evt);
            }
        });

        No.setText("No");
        No.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NoActionPerformed(evt);
            }
        });

        ProfeNombreText.setBackground(new java.awt.Color(0, 0, 0));
        ProfeNombreText.setForeground(new java.awt.Color(0, 0, 0));
        ProfeNombreText.setText("Profesor Asignado: ");

        javax.swing.GroupLayout MarcoDeTextoLayout = new javax.swing.GroupLayout(MarcoDeTexto);
        MarcoDeTexto.setLayout(MarcoDeTextoLayout);
        MarcoDeTextoLayout.setHorizontalGroup(
            MarcoDeTextoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MarcoDeTextoLayout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addComponent(CorrelativasText)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(MateriasText)
                .addGap(87, 87, 87))
            .addGroup(MarcoDeTextoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(MarcoDeTextoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Contenido, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, MarcoDeTextoLayout.createSequentialGroup()
                        .addComponent(ListaDeCorrelativas, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                        .addComponent(Swap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42)
                        .addComponent(ListaDeMaterias, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(MarcoDeTextoLayout.createSequentialGroup()
                        .addGroup(MarcoDeTextoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ContenidoText)
                            .addGroup(MarcoDeTextoLayout.createSequentialGroup()
                                .addGroup(MarcoDeTextoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(NMateriaText)
                                    .addComponent(ProfeNombreText)
                                    .addComponent(CargHorText))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(MarcoDeTextoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(CargaHoraria, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(MarcoDeTextoLayout.createSequentialGroup()
                                        .addGroup(MarcoDeTextoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(ProfesorAsignado)
                                            .addComponent(NombreDeMateria, javax.swing.GroupLayout.PREFERRED_SIZE, 407, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(MarcoDeTextoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(EsOptativaText)
                                            .addComponent(CuatrimestreText))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(MarcoDeTextoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(MarcoDeTextoLayout.createSequentialGroup()
                                                .addComponent(Si)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(No))
                                            .addComponent(Cuatrimestre, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        MarcoDeTextoLayout.setVerticalGroup(
            MarcoDeTextoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MarcoDeTextoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(MarcoDeTextoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(NombreDeMateria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(NMateriaText)
                    .addComponent(CuatrimestreText)
                    .addComponent(Cuatrimestre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(MarcoDeTextoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ProfesorAsignado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ProfeNombreText)
                    .addComponent(EsOptativaText)
                    .addComponent(Si)
                    .addComponent(No))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(MarcoDeTextoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CargHorText)
                    .addComponent(CargaHoraria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(MarcoDeTextoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CorrelativasText)
                    .addComponent(MateriasText))
                .addGroup(MarcoDeTextoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(MarcoDeTextoLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addGroup(MarcoDeTextoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ListaDeMaterias, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ListaDeCorrelativas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(6, 6, 6))
                    .addGroup(MarcoDeTextoLayout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(Swap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(6, 6, 6)
                .addComponent(ContenidoText)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Contenido, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        GuardarCambiosBoton.setText("Guardar Cambios");
        GuardarCambiosBoton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GuardarCambiosBotonActionPerformed(evt);
            }
        });

        Ojo.setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout OjoLayout = new javax.swing.GroupLayout(Ojo);
        Ojo.setLayout(OjoLayout);
        OjoLayout.setHorizontalGroup(
            OjoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 185, Short.MAX_VALUE)
        );
        OjoLayout.setVerticalGroup(
            OjoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout FondoLayout = new javax.swing.GroupLayout(Fondo);
        Fondo.setLayout(FondoLayout);
        FondoLayout.setHorizontalGroup(
            FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FondoLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(FondoLayout.createSequentialGroup()
                        .addComponent(GuardarCambiosBoton, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Ojo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(MarcoDeTexto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        FondoLayout.setVerticalGroup(
            FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FondoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(MarcoDeTexto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(GuardarCambiosBoton, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                    .addComponent(Ojo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 868, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(Fondo, javax.swing.GroupLayout.DEFAULT_SIZE, 868, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 412, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(Fondo, javax.swing.GroupLayout.DEFAULT_SIZE, 412, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void NombreDeMateriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NombreDeMateriaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NombreDeMateriaActionPerformed

    private void GuardarCambiosBotonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GuardarCambiosBotonActionPerformed
        if (esModoEdicion) {
            // Modo edición: actualizar la materia existente
            actualizarMateriaExistente();
        } else {
            // Modo creación: crear una nueva materia
            crearNuevaMateria();
        }
    }//GEN-LAST:event_GuardarCambiosBotonActionPerformed

    private void SiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SiActionPerformed
        // Cuando se selecciona "Si", deseleccionar "No"
        if (Si.isSelected()) {
            No.setSelected(false);
        }
    }//GEN-LAST:event_SiActionPerformed

    private void NoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NoActionPerformed
        // Cuando se selecciona "No", deseleccionar "Si"
        if (No.isSelected()) {
            Si.setSelected(false);
        }
    }//GEN-LAST:event_NoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel CargHorText;
    private javax.swing.JTextField CargaHoraria;
    private javax.swing.JTextField Contenido;
    private javax.swing.JLabel ContenidoText;
    private javax.swing.JLabel CorrelativasText;
    private javax.swing.JTextField Cuatrimestre;
    private javax.swing.JLabel CuatrimestreText;
    private javax.swing.JLabel EsOptativaText;
    private javax.swing.JPanel Fondo;
    private javax.swing.JButton GuardarCambiosBoton;
    private java.awt.List ListaDeCorrelativas;
    private java.awt.List ListaDeMaterias;
    private javax.swing.JPanel MarcoDeTexto;
    private javax.swing.JLabel MateriasText;
    private javax.swing.JLabel NMateriaText;
    private javax.swing.JCheckBox No;
    private javax.swing.JTextField NombreDeMateria;
    private javax.swing.JPanel Ojo;
    private javax.swing.JLabel ProfeNombreText;
    private javax.swing.JTextField ProfesorAsignado;
    private javax.swing.JCheckBox Si;
    private java.awt.Button Swap;
    // End of variables declaration//GEN-END:variables
}
