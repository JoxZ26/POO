package com.mycompany.rummikubkendall;

import java.util.ArrayList;
import java.util.Random;
import javax.swing.JOptionPane;

public class Partida {
    private int cantidadJugadores;
    private Jugador[] jugadores;
    private CeldaMesa[][] mesa;
    private Baraja mazo = new Baraja();
    private int turnoActual;

    public Partida(int cantidadJugadores, String[] nombresJugadores) {
        this.cantidadJugadores = cantidadJugadores;
        mazo.generarFichas();
        setMesa();
        setCantidadJugadores(cantidadJugadores);
        crearJugadores(nombresJugadores);
        turnoActual = 0;
    }
    
    public void setCantidadJugadores(int cantidadJugadores) {
        this.jugadores = new Jugador[cantidadJugadores];
    }

    public void setJugador(int pos, Jugador jugador) {
        this.jugadores[pos] = jugador;
    }

   public void setMesa(){
       this.mesa = new CeldaMesa[30][30];
       for (int i = 0; i < 30; i++){
           for (int j = 0; j < 30; j++){
               mesa[i][j] = new CeldaMesa();
           }
       }
   }

    public int getCantidadJugadores() {
        return cantidadJugadores;
    }
    public Baraja getMazo() {
            return mazo;
        }
    public CeldaMesa[][] getMesa(){
        return this.mesa;
    }

    public Jugador getJugadorActual() {
        return jugadores[turnoActual];
    }

    public Jugador[] getJugadores() {
        return jugadores;
    }

    public void crearJugadores(String[] nombres) {
        for (int pos = 0; pos < cantidadJugadores; pos++) {
            Jugador j = new Jugador(nombres[pos], mazo);
            setJugador(pos, j);
        }
    }

        public void turnoInicialAleatorio() {
        Random rand = new Random();
        int numRandom = rand.nextInt(cantidadJugadores);
        this.turnoActual = numRandom;
    }

    public void avanzarTurno() {
        turnoActual = (turnoActual + 1) % jugadores.length;
    }
    
    private int buscarFilaLibre() {
    for (int i = 0; i < mesa.length; i++) {
        boolean filaVacia = true;
        for (int j = 0; j < mesa[i].length; j++) {
            if (mesa[i][j] != null) {  // Si encontramos algo en la posición, la fila no está libre
                filaVacia = false;
                break;
            }
        }
        if (filaVacia) {
            return i;  // Retornamos la fila vacía
        }
    }
    return -1; // Si no hay filas vacías, retornamos -1
}

    public void agregarCombinacionAMesa(ArrayList<Ficha> combinacion) {
    int filaLibre = buscarFilaLibre();  // Función para encontrar una fila libre en la mesa
    int columna = 0;

    // Colocar las fichas en la mesa en la fila y columnas correspondientes
    for (Ficha ficha : combinacion) {
        if (filaLibre != -1) {
            mesa[filaLibre][columna].setFicha(ficha);  // Asignamos la ficha a la celda
            columna++;
        }
    }
}

    public boolean verificarJugada(){
        for (int i = 0;i < 30;i++){
            ArrayList<Ficha> combinacion = new ArrayList<>();
            for (int j = 0; j < 30;j++){
                if (mesa[i][j].getFicha() != null){
                    combinacion.add(mesa[i][j].getFicha());
                }else{
                    if (!validarCombinacion(combinacion)){
                        return false;
                    }
                    combinacion.clear();
                }
            }
        }
        return true;
    }
    
    public boolean validarCombinacion(ArrayList<Ficha> combinacion){
        if (combinacion.size() >= 3 ){
            Combinaciones comb = new Combinaciones();
            comb.setCombinacion(combinacion);
            return comb.esValida();
        }
        return false;
    }
    
    public boolean jugadorFormaCombinacion(ArrayList<Ficha> combinacion) {
    Combinaciones combinacionTemp = new Combinaciones();
    combinacionTemp.setCombinacion(combinacion);
    
    if (combinacionTemp.esEscalera() || combinacionTemp.esSerie()) {
        agregarCombinacionAMesa(combinacion);
        return true;
    } else {
        JOptionPane.showMessageDialog(
            null,
            "La combinación no es válida.",
            "Combinación inválida",
            JOptionPane.WARNING_MESSAGE
        );
        return false;
    }
}
        
    public boolean isEmpty(){
        for (int i = 0; i < 30; i++){
            for (int j = 0;j<30;j++){
                if (mesa[i][j].estaOcupada()){
                    return false;
                }
                }
            }
            return true;
    }

    public boolean hayGanador() {
        for (Jugador j : jugadores) {
            if (j.getBaraja().size() == 0) {
                return true;
            }
        }
        return false;
    }
    public boolean verificarPrimeraJugada(ArrayList<ArrayList<Ficha>> jugada, Jugador jugador) {
        if (jugador.getHizoPrimeraJugada()) {
            return true;
        } else {
           // Si no la ha hecho, validamos la jugada actual
            boolean esValida = puntajePrimeraJugada(jugada);
            if (esValida) {
                // Si la jugada es válida, marcamos que ya hizo su primera jugada
                jugador.setHizoPrimeraJugada(true);
                return true;
            }
            else {
                // Devolver todas las fichas al jugador
                for (ArrayList<Ficha> combinacion : jugada) {
                jugador.getBaraja().addAll(combinacion);
                }
                JOptionPane.showMessageDialog(null,
                "Primera jugada debe sumar al menos 30 puntos","Tus fichas han sido devueltas a tu baraja."+
                "  Jugada inválida",
                JOptionPane.WARNING_MESSAGE);
                return false;
            }
        }
    }
    
    public boolean puntajePrimeraJugada(ArrayList<ArrayList<Ficha>> jugada) {
    int total = 0;
    // Recorremos cada combinación en la jugada del jugador
    for (ArrayList<Ficha> combinacion : jugada) {
        Combinaciones comb = new Combinaciones(); 
        comb.setCombinacion(combinacion); // Le asignamos la lista de fichas
        // Si la combinación no es válida (ni serie ni escalera), la jugada falla
        if (!comb.esValida()) {
            return false;
        }
        // Recorremos cada ficha en la combinación
        for (Ficha f : combinacion) {
            // Sumamos el valor de la ficha
            total += f.getNumeroParaJuego();
        }
    }
    return total >= 30;
}
}

