    /* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.rummikubkendall;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author kenda
 */
public class PantallaJuego extends javax.swing.JFrame {

    private Partida partida;
    private ArrayList<Ficha> Combinacion;
    private ArrayList<CeldaMesa> celdasUsadas = new ArrayList<>();
    private boolean jugoTurno;
    private JPanel panelFichasJugador;
    private javax.swing.JLabel etiquetaJugadorActual;
    private javax.swing.JButton btnComerCarta;
    private javax.swing.JButton btnListo;
    private javax.swing.JButton btnAvanzar;
    private javax.swing.JLabel ContadorCartasRestantes;
    private javax.swing.JButton btnControlZ;

    private JPanel mesaPanel;
    private JScrollPane scrollMesa;

    private VistaFicha fichaSeleccionada = null;

    public PantallaJuego(Partida partida) {
        initComponents();
        this.partida = partida;
        this.Combinacion = new ArrayList<>();
        this.jugoTurno = false;
        
        getContentPane().setLayout(new BorderLayout());
        panelFichasJugador = new JPanel();
        panelFichasJugador.setLayout(new FlowLayout(FlowLayout.LEFT)); // Horizontal, de izquierda a derecha

        // Panel inferior (donde va estar todo)
        JPanel panelInferior = new JPanel();
        panelInferior.setLayout(new BoxLayout(panelInferior, BoxLayout.Y_AXIS));

        // Panel superior para el turno y los botones que estara en panel inferior
        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new BorderLayout()); // Usamos BorderLayout para distribuir los elementos

        // Etiqueta para el turno
        etiquetaJugadorActual = new javax.swing.JLabel("   Turno de "
                + partida.getJugadorActual().getNombre());
        etiquetaJugadorActual.setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 30));

        //Contador de cartas restantes
        ContadorCartasRestantes = new javax.swing.JLabel(" Cantidad de cartas : "
                + partida.getMazo().getCantidadFichas() + "     ");
        ContadorCartasRestantes.setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 30));

        // Crear los botones
        btnComerCarta = new javax.swing.JButton("Comer Carta");
        btnComerCarta.setFont(new Font("SansSerif", Font.PLAIN, 18));
        btnComerCarta.setPreferredSize(new Dimension(150, 111));

        btnListo = new javax.swing.JButton("Listo");
        btnListo.setFont(new Font("SansSerif", Font.PLAIN, 18));
        btnListo.setPreferredSize(new Dimension(130, 111));

        btnControlZ = new javax.swing.JButton("Control Z");
        btnControlZ.setFont(new Font("SansSerif", Font.PLAIN, 18));
        btnControlZ.setPreferredSize(new Dimension(130, 111));
        
        btnAvanzar = new javax.swing.JButton("Avanzar Turno");
        btnAvanzar.setFont(new Font("SansSerif",Font.PLAIN,18));
        btnAvanzar.setPreferredSize(new Dimension(150, 111));
        

        // Boton para comer carta funcional
        btnComerCarta.addActionListener(e -> {
            // El jugador actual come una carta
            if (!jugoTurno){
                partida.getJugadorActual().comerFicha();
                // Actualizamos el contador de cartas restantes
                ContadorCartasRestantes.setText("Cantidad de cartas: " + partida.getMazo().getCantidadFichas());
            // Actualizamos la interfaz para reflejar las fichas del jugador
                partida.avanzarTurno();
            etiquetaJugadorActual.setText("Turno de " + partida.getJugadorActual().getNombre());
                mostrarFichasJugador();
            }else{
                JOptionPane.showMessageDialog(null,"Ya añadió", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }

            //FALTA QUE CUANDO DE ESTE BOTON, LA MESA VUELVA A SU ESTADO INICIAL
        });
      btnListo.addActionListener(e -> {
    Jugador jugadorActual = partida.getJugadorActual();
    if (!jugadorActual.getHizoPrimeraJugada()) {
        // Crear un ArrayList<ArrayList<Ficha>> con la combinación actual
        ArrayList<ArrayList<Ficha>> jugada = new ArrayList<>();
        jugada.add(Combinacion); // Asumiendo que Combinacion es ArrayList<Ficha>
        
        if (!partida.verificarPrimeraJugada(jugada, jugadorActual)) {
            jugoTurno = false;
            // Si no cumple, limpiar la combinación y actualizar la vista
            Combinacion.clear();
            celdasUsadas.forEach(celda -> {
                celda.removeAll();
                celda.repaint();
            });
            celdasUsadas.clear();
            mostrarFichasJugador(); // Actualizar la vista
            return; // No cumple con los 30 puntos
        }
    }else {if (partida.jugadorFormaCombinacion(Combinacion)) {
        jugoTurno = false;
        mostrarFichasJugador();
        Combinacion.clear();
        celdasUsadas.clear();
        jugoTurno = true;  // marca que ya jugó
        mostrarFichasJugador();
    } else {
        jugoTurno = false;
        for (CeldaMesa celda : celdasUsadas) {
            celda.removeAll();
            celda.revalidate();
            celda.repaint();
        }
        for (Ficha ficha : Combinacion) {
            partida.getJugadorActual().agregarFicha(ficha);
        }
        Combinacion.clear();
        mostrarFichasJugador();
    }}
});

// Avanzar: solo permite el turno si antes se jugó algo
    btnAvanzar.addActionListener(e -> {
        if (jugoTurno) {
            partida.avanzarTurno();
            etiquetaJugadorActual.setText("Turno de " + partida.getJugadorActual().getNombre());
            mostrarFichasJugador();
            jugoTurno = false;
    } else {
            JOptionPane.showMessageDialog(
                this,
                "Debe jugar primero",
                "Atención",
                JOptionPane.WARNING_MESSAGE
        );
    }
});
        //LO DE ANALIZAR ES MESA Y CAMBIO EN MOSTRAR FICHA
        //  Mesa:
        mesaPanel = new JPanel(null); // layout null para colocar celdas con coordenadas        
        // Crear y agregar celdas a mesaPanel
        int columnas = 27;
        int filas = 21;
        int anchoCelda = 86;
        int altoCelda = 185;

        for (int fila = 0; fila < filas; fila++) {
            for (int col = 0; col < columnas; col++) {
                CeldaMesa celda = new CeldaMesa();
                celda.setBounds(col * (anchoCelda ), fila * (altoCelda ), 
                        anchoCelda, altoCelda);
            celda.addMouseListener(new MouseAdapter() {
    @Override
    public void mouseClicked(MouseEvent e) {
        if (fichaSeleccionada != null && celda.getComponentCount() == 0) {
            panelFichasJugador.remove(fichaSeleccionada);
            partida.getJugadorActual().getBaraja().remove(fichaSeleccionada.getFicha());
            panelFichasJugador.revalidate();
            panelFichasJugador.repaint();
            
                    
            celda.setLayout(new BorderLayout());
            celda.add(fichaSeleccionada, BorderLayout.CENTER);
            Combinacion.add(fichaSeleccionada.getFicha());
            
            celda.setFicha(fichaSeleccionada.getFicha());
            partida.getMesa()[celda.getI()][celda.getJ()] = celda;
            
            celdasUsadas.add(celda);
            celda.revalidate();
            celda.repaint();

            fichaSeleccionada = null;
        }
    }
}); 
                mesaPanel.add(celda);
            }
        }
        mesaPanel.setPreferredSize(new Dimension(2500, 4000)); // Tamaño grande para que el scroll tenga sentido

        scrollMesa = new JScrollPane(mesaPanel);
        scrollMesa.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollMesa.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        // Lo agregás a tu panel principal o al layout donde va la mesa
        getContentPane().add(scrollMesa, BorderLayout.CENTER);
        // Panel para los botones alineados a la derecha
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout(FlowLayout.RIGHT)); // Alineación a la derecha
        panelBotones.add(ContadorCartasRestantes);
        panelBotones.add(btnListo);      

        // Añadir al panel superior: nombre del jugador a la izquierda y botones a la derecha
        JPanel panelIzquierdaAbajo = new JPanel();
        panelIzquierdaAbajo.setLayout(new FlowLayout(FlowLayout.LEFT));
        panelIzquierdaAbajo.add(btnComerCarta);
        panelIzquierdaAbajo.add(btnAvanzar);
        panelIzquierdaAbajo.add(etiquetaJugadorActual);

        panelSuperior.add(panelIzquierdaAbajo, BorderLayout.WEST);  // Nombre del jugador (izquierda)
        panelSuperior.add(panelBotones, BorderLayout.EAST);           // Botones (derecha)

        // ScrollPane que envuelve el panel
        JScrollPane scrollFichas = new JScrollPane(panelFichasJugador);
        scrollFichas.setPreferredSize(new Dimension(400, 210));                // Scroll
        scrollFichas.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollFichas.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);

        panelInferior.add(panelSuperior);  // Añadir el panel superior con los botones y nombre
        panelInferior.add(scrollFichas);   // Añadir las fichas del jugador

        // Añadir al JFrame
        add(panelInferior, BorderLayout.SOUTH);
        mostrarFichasJugador();
    }
    
    private boolean puedeMoverDeMesa() {
        // Verificar si el jugador ya hizo su primera jugada o si la mesa está vacía
        return partida.getJugadorActual().getHizoPrimeraJugada() || partida.isEmpty();
    }

    // Modificar el MouseListener de las celdas de la mesa
    private void configurarCeldasMesa() {
        for (Component comp : mesaPanel.getComponents()) {
            if (comp instanceof CeldaMesa) {
                CeldaMesa celda = (CeldaMesa) comp;
                celda.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (!puedeMoverDeMesa() && celda.estaOcupada()) {
                            JOptionPane.showMessageDialog(PantallaJuego.this,
                                "No puedes mover fichas de la mesa hasta completar tu primera jugada de 30+ puntos",
                                "Restricción",
                                JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                    }
                });
            }
        }
    }

    public boolean isJugoTurno() {
        return jugoTurno;
    }

    
    private boolean hayFichaEn(JPanel panel, int x, int y) {
        for (Component comp : panel.getComponents()) {
            if (comp.getBounds().contains(x, y)) {
                return true; // ya hay una ficha ahí
                
            }
        }
        return false;
    }
    
    
    public void mostrarFichasJugador() {
            panelFichasJugador.removeAll(); // Limpia las fichas anteriores (por si se actualiza)
            Jugador jugadorActual = partida.getJugadorActual();
            for (Ficha ficha : jugadorActual.getBaraja()) {
                VistaFicha vista = new VistaFicha(ficha);
                vista.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        fichaSeleccionada = vista;
                    }
                });
                panelFichasJugador.add(vista);
            }
            panelFichasJugador.revalidate();
            panelFichasJugador.repaint();
        }
    
    public void setFichaSeleccionada(VistaFicha ficha) {
    this.fichaSeleccionada = ficha;
}


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1080, 720));

        jPanel1.setPreferredSize(new java.awt.Dimension(1080, 720));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 466, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 351, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 466, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 351, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
