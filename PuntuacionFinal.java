package com.mycompany.rummikubkendall;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.EmptyBorder;

public class PuntuacionFinal extends JFrame {

    private JPanel panelTitulo;
    private JPanel panelPrincipal;
    private JPanel panelBotones;
    private JPanel panelPuntajes;
    private JButton btnJugarDeNuevo;
    private JButton btnSalir;
    private Partida partida;

    public PuntuacionFinal(Partida partida) {
        this.partida = partida;

        setTitle("Puntuación Final");
        setSize(800, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        panelTitulo = new JPanel();
        panelTitulo.setBackground(Color.WHITE);  // Fondo blanco para asegurar que se vea
        JLabel lblTitulo = new JLabel("¡Fin de Partida!");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 36));  // Fuente grande y negrita
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);  // Centrar el texto
        panelTitulo.add(lblTitulo);  // Añadir el JLabel al panel
        add(panelTitulo, BorderLayout.NORTH);

        // Panel principal (puedes añadir más cosas luego)
        panelPrincipal = new JPanel();
        panelPrincipal.setBackground(Color.WHITE);
        add(panelPrincipal, BorderLayout.CENTER);

        panelPuntajes = new JPanel();
        panelPuntajes.setLayout(new BoxLayout(panelPuntajes, BoxLayout.Y_AXIS));  // Usamos BoxLayout para apilar los nombres
        panelPuntajes.setBackground(Color.WHITE);
        Jugador[] jugadores = partida.getJugadores();
        JLabel lblTituloPuntajes = new JLabel("Puntajes:");
        lblTituloPuntajes.setFont(new Font("Segoe UI", Font.BOLD, 30));
        lblTituloPuntajes.setBorder(new EmptyBorder(20, 23, 10, 10));
        panelPuntajes.add(lblTituloPuntajes);

        for (Jugador jugador : jugadores) {
            JLabel lblJugador = new JLabel(jugador.getNombre() + " = " + jugador.getPuntacionActual() + " / Acumulado: " + jugador.getPuntuacion());
            lblJugador.setFont(new Font("Segoe UI", Font.BOLD, 28));
            lblJugador.setBorder(BorderFactory.createEmptyBorder(10, 23, 10, 10));  // Espacio entre nombres
            panelPuntajes.add(lblJugador);  // Añadir el nombre al panel
        }

        add(panelPuntajes, BorderLayout.CENTER);

        // Panel con botones abajo
        panelBotones = new JPanel(new BorderLayout());
        panelBotones.setBackground(Color.WHITE);
        panelBotones.setBorder(BorderFactory.createEmptyBorder(15, 15, 25, 15));

        btnJugarDeNuevo = new JButton("Jugar de nuevo");
        btnSalir = new JButton("Salir");

        btnJugarDeNuevo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btnJugarDeNuevo.setPreferredSize(new Dimension(250, 60));
        btnSalir.setFont(new Font("Segoe UI", Font.BOLD, 18));

        panelBotones.add(btnJugarDeNuevo, BorderLayout.WEST);
        panelBotones.add(btnSalir, BorderLayout.EAST);
        btnSalir.setPreferredSize(new Dimension(250, 60));
        add(panelBotones, BorderLayout.SOUTH);

        // Acciones de botones
        btnJugarDeNuevo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Jugador[] jugadores = partida.getJugadores();
                String[] nombres = new String[jugadores.length];
                for (int i = 0; i < jugadores.length; i++) {
                    nombres[i] = jugadores[i].getNombre();
                }
                Partida nuevaPartida = new Partida(jugadores.length,jugadores);
                PantallaJuego nuevoJuego = new PantallaJuego(nuevaPartida);
                nuevoJuego.setVisible(true);
                dispose();
            }
        });

        btnSalir.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            Final mensajeFinal = new Final(partida);
            mensajeFinal.setVisible(true);
            dispose();
    }
});

    }
}
