/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.rummikubkendall;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;     //Todos los import que nos recomendaba NeatBeans
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


/**
 *
 * @author kenda
 */
public class PantallaJuego extends javax.swing.JFrame { //que este no lo manejamos por "Design" de neatbeans

    private Partida partida;
    private ArrayList<Ficha> Combinacion;
    private ArrayList<CeldaMesa> celdasUsadas = new ArrayList<>();
    private List<Ficha> fichasDesdeMano = new ArrayList<>();
    private CeldaMesa[][] copiaMesa;
    private JPanel panelFichasJugador;
    private javax.swing.JLabel etiquetaJugadorActual;
    private javax.swing.JButton btnComerCarta;
    private javax.swing.JButton btnListo;
    private javax.swing.JLabel ContadorCartasRestantes;
    private JPanel mesaPanel;
    private JScrollPane scrollMesa;
    private VistaFicha fichaSeleccionada = null;
    private Jugador ganador = null;

    public PantallaJuego(Partida partida) {
        initComponents();
        this.partida = partida;
        this.Combinacion = new ArrayList<>();
        
        
        //Se crearan varios paneles para menajar botones, la mano, le mesa, etc
        //panel para mano jugador, con scroll
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
        // Boton para comer carta funcional
        btnComerCarta.addActionListener(e -> {
            // El jugador actual come una carta 
            if (!celdasUsadas.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No puede comer carta si hizo lanzó una ficha a la mesa", "Advertencia", JOptionPane.WARNING_MESSAGE);
                revertirJugada();
                restaurarMesa();
            } else {
                partida.getJugadorActual().comerFicha();
                // Actualizamos el contador de cartas restantes
                ContadorCartasRestantes.setText("Cantidad de cartas: " + partida.getMazo().getCantidadFichas());

                if (partida.getMazo().getCantidadFichas() == 0) {
                    int puntajeGanador = 10000000;
                    for (Jugador jugador : partida.getJugadores()) {
                        if (jugador.puntajeTotal() <= puntajeGanador) {
                            puntajeGanador = jugador.puntajeTotal();
                            ganador = jugador;
                        }
                    }
                    if (ganador != null) {
                        JOptionPane.showMessageDialog(null,
                                "🏆 ¡FELICIDADES " + ganador.getNombre().toUpperCase() + "!\n\n"
                                + "Has ganado la partida 🎉",
                                "🎊 JUEGO TERMINADO 🎊",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                    for (Jugador jugador : partida.getJugadores()) {
                        if (jugador == ganador) {
                            int puntoss = 0;
                            for (Jugador jugadorr : partida.getJugadores()) {
                                if (jugadorr != ganador) {
                                    puntoss += jugadorr.puntajeTotal();
                                }
                            }
                            ganador.sumarPuntos(puntoss);  // Actualizas la puntuación acumulada
                        } else {
                            int puntos = jugador.puntajeTotal();
                            jugador.restarPuntos(puntos);  // Actualizas la puntuación acumulada
                        }
                    }

                    PuntuacionFinal puntuacion = new PuntuacionFinal(partida); // Crear la nueva ventana
                    puntuacion.setVisible(true);                   // Mostrarla
                    this.dispose();

                } else {
                    // Actualizamos la interfaz para reflejar las fichas del jugador
                    partida.avanzarTurno();
                    etiquetaJugadorActual.setText("Turno de " + partida.getJugadorActual().getNombre());
                    mostrarFichasJugador();
                }

            }
        });
        //--------------------------------------------------------------------------------------------------------------------------------------------//
        btnListo.addActionListener(e -> {
            if (fichasDesdeMano.isEmpty()) {
                JOptionPane.showMessageDialog(null,
                        "No has colocado fichas desde tu mano",
                        "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            } else {
                Jugador jugadorActual = partida.getJugadorActual();
                boolean jugadaValida = false;
                if (!jugadorActual.getHizoPrimeraJugada()) {
                    ArrayList<ArrayList<Ficha>> jugada = new ArrayList<>();
                    jugada.add(Combinacion);
                    if (partida.verificarJugada(partida.getJugadorActual().getMesaSecundaria())) {
                        if (partida.puntajePrimeraJugada(jugada)) {
                            JOptionPane.showMessageDialog(null, "Primera jugada exitosa");
                            jugadaValida = true;
                            jugadorActual.setHizoPrimeraJugada(true);

                        } else {
                            partida.getJugadorActual().clearMesaSecundaria();
                            revertirJugada();
                            restaurarMesa();
                            JOptionPane.showMessageDialog(null,
                                    "La primera jugada debe sumar al menos 30 puntos",
                                    "Advertencia", JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                    } else {
                        partida.getJugadorActual().clearMesaSecundaria();
                        revertirJugada();
                        restaurarMesa();
                        JOptionPane.showMessageDialog(null,
                                "La jugada es inválida",
                                "Advertencia", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                } // Verificación para jugadas normales
                else if (partida.verificarJugada(partida.getMesa()) && partida.getJugadorActual().isJugoTurno()) {
                    if (jugadorActual.getBaraja().isEmpty()) {
                        JOptionPane.showMessageDialog(null,
                                "🏆 ¡FELICIDADES " + jugadorActual.getNombre().toUpperCase() + "!\n\n"
                                + "Has ganado la partida 🎉",
                                "🎊 JUEGO TERMINADO 🎊",
                                JOptionPane.INFORMATION_MESSAGE);
                        ganador = jugadorActual;
                        for (Jugador jugador : partida.getJugadores()) {
                            if (jugador == ganador) {
                                int puntoss = 0;
                                for (Jugador jugadorr : partida.getJugadores()) {
                                    if (jugadorr != ganador) {
                                        puntoss += jugadorr.puntajeTotal();
                                    }
                                }
                                ganador.sumarPuntos(puntoss);  // Actualizas la puntuación acumulada
                            } else {
                                int puntos = jugador.puntajeTotal();
                                jugador.restarPuntos(puntos);  // Actualizas la puntuación acumulada
                            }
                        }
                        PuntuacionFinal puntuacion = new PuntuacionFinal(partida); // Crear la nueva ventana
                        puntuacion.setVisible(true);                   // Mostrarla
                        this.dispose();
                    } else {
                        jugadaValida = true;
                    }

                } // Jugada inválida
                else {
                    revertirJugada();
                    restaurarMesa();
                    JOptionPane.showMessageDialog(null,
                            "Combinación inválida",
                            "Advertencia", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Si la jugada fue válida
                if (jugadaValida) {
                    copiaMesa = copiarMesa(partida.getMesa());
                    Combinacion.clear();
                    celdasUsadas.clear();
                    fichasDesdeMano.clear();
                    partida.getJugadorActual().setJugoTurno(false);
                    avanzarTurno();
                }
            }
        });
        mesaPanel = new JPanel(null); // Layout null para coordenadas
        int columnas = 30;
        int filas = 30;
        int anchoCelda = 86;
        int altoCelda = 185;
        for (int fila = 0; fila < filas; fila++) {
            for (int col = 0; col < columnas; col++) {
                CeldaMesa celda = new CeldaMesa();
                celda.setI(fila);
                celda.setJ(col);
                celda.setBounds(col * anchoCelda, fila * altoCelda, anchoCelda, altoCelda);
                celda.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (fichaSeleccionada == null || celda.getComponentCount() != 0) {
                            return;
                        }
                        partida.getJugadorActual().setJugoTurno(false);
                        boolean vieneDeMano = panelFichasJugador.isAncestorOf(fichaSeleccionada);
                        boolean vieneDeMesa = !vieneDeMano;

                        if (vieneDeMesa && !puedeMoverDeMesa()) {
                            JOptionPane.showMessageDialog(PantallaJuego.this,
                                    "No puedes mover fichas de la mesa hasta completar tu primera jugada de 30+ puntos",
                                    "Restricción", JOptionPane.WARNING_MESSAGE);
                            Combinacion.clear();
                            return;
                        }

                        if (vieneDeMano) {
                            panelFichasJugador.remove(fichaSeleccionada);
                            partida.getJugadorActual().getBaraja().remove(fichaSeleccionada.getFicha());
                            fichasDesdeMano.add(fichaSeleccionada.getFicha());
                        } else {
                            CeldaMesa[][] mesa = partida.getMesa();
                            for (int i = 0; i < mesa.length; i++) {
                                for (int j = 0; j < mesa[i].length; j++) {
                                    CeldaMesa origen = mesa[i][j];
                                    if (origen.getComponentCount() > 0 && origen.getComponent(0) == fichaSeleccionada) {
                                        origen.remove(fichaSeleccionada);
                                        origen.setFicha(null);
                                        origen.liberar();
                                        origen.revalidate();
                                        origen.repaint();
                                        break;
                                    }
                                }
                            }
                        }

                        celda.setLayout(new BorderLayout());
                        celda.add(fichaSeleccionada, BorderLayout.CENTER);
                        celda.setFicha(fichaSeleccionada.getFicha());
                        partida.getMesa()[celda.getI()][celda.getJ()] = celda;
                        partida.getMesa()[celda.getI()][celda.getJ()].ocupar();

                        if (!partida.getJugadorActual().getHizoPrimeraJugada()) {
                            partida.getJugadorActual().getMesaSecundaria()[celda.getI()][celda.getJ()] = celda;
                            partida.getJugadorActual().getMesaSecundaria()[celda.getI()][celda.getJ()].ocupar();
                        }

                        Combinacion.add(fichaSeleccionada.getFicha());
                        celdasUsadas.add(celda);
                        fichaSeleccionada = null;
                        partida.getJugadorActual().setJugoTurno(true);
                        panelFichasJugador.revalidate();
                        panelFichasJugador.repaint();
                        mesaPanel.revalidate();
                        mesaPanel.repaint();
                        celda.revalidate();
                        celda.repaint();
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

// Mostrar estado inicial mesa también
        restaurarMesa();
    }

    private boolean puedeMoverDeMesa() {
        // Verificar si el jugador ya hizo su primera jugada o si la mesa está vacía
        return partida.getJugadorActual().getHizoPrimeraJugada() || partida.isEmpty();
    }

    private void limpiarMesa() {
        Combinacion.clear();
        celdasUsadas.forEach(celda -> {
            celda.removeAll();
            celda.repaint();
        });
        celdasUsadas.clear();
        mostrarFichasJugador();
    }

    private void revertirJugada() {
        for (CeldaMesa celda : celdasUsadas) {
            celda.removeAll();
            celda.setFicha(null);
            celda.revalidate();
            celda.repaint();
        }

        for (Ficha ficha : fichasDesdeMano) {
            partida.getJugadorActual().agregarFicha(ficha);
            VistaFicha vista = new VistaFicha(ficha);
            vista.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    fichaSeleccionada = vista;
                }
            });
            panelFichasJugador.add(vista);
        }

        limpiarMesa();
        fichasDesdeMano.clear();
        panelFichasJugador.revalidate();
        panelFichasJugador.repaint();
    }

    private void restaurarMesa() {
        if (copiaMesa != null) {
            restaurarMesaDesdeCopia(copiaMesa);
            partida.getJugadorActual().setJugoTurno(false);
        }
    }

    private CeldaMesa[][] copiarMesa(CeldaMesa[][] original) {
        int filas = original.length;
        int columnas = original[0].length;
        CeldaMesa[][] copia = new CeldaMesa[filas][columnas];

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                CeldaMesa originalCelda = original[i][j];
                CeldaMesa nuevaCelda = new CeldaMesa();
                nuevaCelda.setI(i);
                nuevaCelda.setJ(j);

                if (originalCelda.getComponentCount() > 0) {
                    VistaFicha fichaGrafica = (VistaFicha) originalCelda.getComponent(0);
                    Ficha fichaOriginal = fichaGrafica.getFicha();
                    Ficha copiaFicha = new Ficha(fichaOriginal.getId(), fichaOriginal.getColor(), fichaOriginal.getNumero());
                    VistaFicha copiaFichaGrafica = new VistaFicha(copiaFicha);

                    nuevaCelda.setLayout(new BorderLayout());
                    nuevaCelda.add(copiaFichaGrafica, BorderLayout.CENTER);
                    nuevaCelda.setFicha(copiaFicha);
                }

                copia[i][j] = nuevaCelda;
            }
        }

        return copia;
    }

    private void restaurarMesaDesdeCopia(CeldaMesa[][] copia) {
        CeldaMesa[][] actual = partida.getMesa();

        for (int i = 0; i < copia.length; i++) {
            for (int j = 0; j < copia[0].length; j++) {
                CeldaMesa original = copia[i][j];
                CeldaMesa destino = actual[i][j];
                destino.removeAll();

                if (original.getComponentCount() > 0) {
                    VistaFicha fichaGrafica = (VistaFicha) original.getComponent(0);
                    Ficha ficha = fichaGrafica.getFicha();
                    Ficha copiaFicha = new Ficha(ficha.getId(), ficha.getColor(), ficha.getNumero());
                    VistaFicha copiaGrafica = new VistaFicha(copiaFicha);

                    // Agregar MouseListener para poder seleccionar la ficha
                    copiaGrafica.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            fichaSeleccionada = copiaGrafica;
                        }
                    });

                    destino.setLayout(new BorderLayout());
                    destino.add(copiaGrafica, BorderLayout.CENTER);
                    destino.setFicha(copiaFicha);
                } else {
                    destino.setFicha(null);
                }

                destino.revalidate();
                destino.repaint();
            }
        }
    }

    private void avanzarTurno() {
        partida.avanzarTurno();
        partida.hacerRespaldoMesa();
        etiquetaJugadorActual.setText("Turno de " + partida.getJugadorActual().getNombre());
        mostrarFichasJugador();
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
