
package com.mycompany.rummikubkendall;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.EmptyBorder;

public class Final extends JFrame{
    private JPanel panelTitulo;
    private JPanel panelPrincipal;
    private JPanel panelPuntajes;
    private Partida partida;
    private JPanel panelBotones;
    private JButton btnSalir;
    
    public Final(Partida partida) {
    this.partida = partida;

    setTitle("¡Felicidades!");
    setSize(800, 700);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    setLayout(new BorderLayout());

    panelTitulo = new JPanel();
    panelTitulo.setBackground(Color.WHITE);
    JLabel lblTitulo = new JLabel("¡Fin de Partida!");
    lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 36));
    lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
    panelTitulo.add(lblTitulo);
    add(panelTitulo, BorderLayout.NORTH);

    panelPrincipal = new JPanel();
    panelPrincipal.setBackground(Color.WHITE);
    add(panelPrincipal, BorderLayout.CENTER);

    panelPuntajes = new JPanel();
    panelPuntajes.setLayout(new BoxLayout(panelPuntajes, BoxLayout.Y_AXIS));
    panelPuntajes.setBackground(Color.WHITE);

    JLabel lblTituloPuntajes = new JLabel("El ganador es:");
    lblTituloPuntajes.setFont(new Font("Segoe UI", Font.BOLD, 30));
    lblTituloPuntajes.setBorder(new EmptyBorder(20, 23, 10, 10));
    panelPuntajes.add(lblTituloPuntajes);

    Jugador ganador = jugadorMaxPuntaje();
    JLabel lblJugador = new JLabel("El jugador: " + ganador.getNombre());
    lblJugador.setFont(new Font("Segoe UI", Font.BOLD, 28));
    lblJugador.setBorder(BorderFactory.createEmptyBorder(10, 23, 10, 10));
    panelPuntajes.add(lblJugador);

    add(panelPuntajes, BorderLayout.CENTER);
    
    panelBotones = new JPanel(new BorderLayout());
    panelBotones.setBackground(Color.WHITE);
    panelBotones.setBorder(BorderFactory.createEmptyBorder(15, 15, 25, 15));
    
    btnSalir = new JButton("Salir");
    btnSalir.setFont(new Font("Segoe UI", Font.BOLD, 18));
    panelBotones.add(btnSalir, BorderLayout.EAST);
    btnSalir.setPreferredSize(new Dimension(250, 60));
    add(panelBotones, BorderLayout.SOUTH);
    
    btnSalir.addActionListener(e -> System.exit(0));
}

    
    public Jugador jugadorMaxPuntaje() { //Método para obtener el jugador con la mayor puntuación actual.
    Jugador[] jugadores = partida.getJugadores();
    int max = jugadores[0].getPuntacionActual(); // Se usa la puntuación actual desde el inicio
    Jugador jugMax = jugadores[0];
    for (int i = 0; i < jugadores.length; i++) {
        if (jugadores[i].getPuntacionActual() > max) {
            max = jugadores[i].getPuntacionActual();
            jugMax = jugadores[i];
        }
    }
    return jugMax; // Retorna el jugador con la mayor puntuación actual
}

}
