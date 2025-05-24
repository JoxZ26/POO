package com.mycompany.rummikubkendall;

import java.util.ArrayList; //Importar la clase ArrayList para utilizar arrays dinámicos.
import java.util.Random; //Importar la clase random para generar datos aleatorios.
import javax.swing.JOptionPane; //Importar JOptionPane para I/Os a través de interfaz.

public class Partida {

    private int cantidadJugadores; // Atributo privado, tipo int, cantidadJugadores: Número total de jugadores en la partida.
    private Jugador[] jugadores; // Atributo privado, arreglo de Jugador, jugadores: Arreglo que contiene a todos los jugadores participantes.
    private CeldaMesa[][] mesa; // Atributo privado, matriz de CeldaMesa, mesa: Representa la mesa principal del juego donde se colocan las fichas.
    private Baraja mazo = new Baraja(); // Atributo privado, tipo Baraja, mazo: Baraja del juego que contiene las fichas aún no jugadas.
    private int turnoActual; // Atributo privado, tipo int, turnoActual: Índice del jugador al que le corresponde jugar.
    private CeldaMesa[][] mesaRespaldo; // Atributo privado, matriz de CeldaMesa, mesaRespaldo: Copia de seguridad de la mesa principal para validar jugadas.


    public Partida(int cantidadJugadores, String[] nombresJugadores) { //Primer constructor: Se utiliza cuando se inicia una partida desde 0.
        this.cantidadJugadores = cantidadJugadores;
        mazo.generarFichas();
        setMesa();
        setCantidadJugadores(cantidadJugadores);
        crearJugadores(nombresJugadores); //Utiliza el método crearJugadores.
        turnoActual = 0;
    }
    
    public Partida(int cantidadJugadores, Jugador[] jugadores){ //Segundo constructor: Se utiliza cuando se juega de nuevo una partida.
        this.cantidadJugadores = cantidadJugadores;
        mazo.generarFichas();
        setMesa();
        setCantidadJugadores(cantidadJugadores);
        copiarJugadores(jugadores); //Utiliza el método copiarJugadores.
        turnoActual = 0;
    }

    /* Getters */
    public int getCantidadJugadores() {
        return cantidadJugadores;
    }

    public Baraja getMazo() {
        return mazo;
    }

    public CeldaMesa[][] getMesa() {
        return this.mesa;
    }

    public Jugador getJugadorActual() {
        return jugadores[turnoActual];
    }

    public Jugador[] getJugadores() {
        return jugadores;
    }
    
    public CeldaMesa[][] getMesaRespaldo() {
        return mesaRespaldo;
    }
    

    /* Setters */
    public void setCantidadJugadores(int cantidadJugadores) {
        this.jugadores = new Jugador[cantidadJugadores];
    }

    public void setJugador(int pos, Jugador jugador) {
        this.jugadores[pos] = jugador;
    }

    public void setMesa() { //Método para generar la mesa principal del juego.
        this.mesa = new CeldaMesa[30][30]; //Matriz de celdas, 30x30.
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 30; j++) {
                CeldaMesa celda = new CeldaMesa(); //Crea una nueva celda.
                celda.setI(i); //Añade el índice de fila a una celda.
                celda.setJ(j); // Añade el índice de columna a una celda.
                mesa[i][j] = celda; //Añade una celda en una posición de la mesa.
            }
        }
    }

    /* Metodos */
    public void crearJugadores(String[] nombres) { //Método para crear a los jugadores de la partida.
        for (int pos = 0; pos < cantidadJugadores; pos++) {
            //Por cada jugador registrado, recorre la lista de nombres guardados.
            Jugador j = new Jugador(nombres[pos], mazo);
            //Crea un nuevo jugador, con su nombre y el mazo.
            setJugador(pos, j); //Añade el jugador creado a la lista de jugadores.
        }
    }
    
    public void copiarJugadores(Jugador[] Jugadores){ //Método para copiar a los jugadores de una partida anterior.
        for (int i = 0; i < cantidadJugadores;i++){
            //Por cada jugador registrado, recorre la lista de jugadores guardados.
            Jugador j = new Jugador(Jugadores[i].getNombre(),mazo,Jugadores[i].getPuntuacion());
            //Crea una copia del jugador, con su mismo nombre y acumula la puntuación generada en la partida jugada anteriormente.
            setJugador(i,j); //Se sobreescribe cada posición con su nueva instancia del jugador.
        }
    }


    public void avanzarTurno() { //Método para avanzar al siguiente turno.
        turnoActual = (turnoActual + 1) % jugadores.length;
    }

    private CeldaMesa[][] copiarMesa(CeldaMesa[][] original) { //Método que crea una copia de la mesa original (la recibe como parámetro).
        CeldaMesa[][] copia = new CeldaMesa[30][30];

        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 30; j++) {
                if (original[i][j] != null) {
                    copia[i][j] = new CeldaMesa(original[i][j]); //Se crea una nueva celda copiando el contenido de la original.
                } else {
                    copia[i][j] = null; //Si la celda está vacía en la original, también en la copia.
                }
            }
        }
        return copia; //Devuelve la copia completa de la matriz original.
    }

    public boolean verificarJugada(CeldaMesa[][] matriz) { //Método para verificar si las combinaciones formadas en la mesa son válidas.
        int filas = matriz.length;
        int cols = matriz[0].length;
        for (int i = 0; i < filas; i++) {
            ArrayList<Ficha> combinacion = new ArrayList<>(); //Array para guardar las fichas que formen una combinación.
            for (int j = 0; j < cols; j++) {
                if (matriz[i][j].estaOcupada()) {
                    //Si una celda de la mesa está ocupada (tiene una ficha) se añade a el array de combinación.
                    combinacion.add(matriz[i][j].getFicha());
                } else { //Si encuentra una celda liberada, verifica la combinación.
                    if (!combinacion.isEmpty()) {
                        if (!validarCombinacion(combinacion)) {
                            return false; //Si la combinación no es válida, se retorna false.
                        }
                        combinacion.clear(); //Se limpia la combinación para comenzar otra.
                    }
                }
            }
            if (!combinacion.isEmpty()) {
                if (!validarCombinacion(combinacion)) {
                    return false; //Al final de la fila, se valida cualquier combinación restante.
                }
            }
        }
        return true; //Si todas las combinaciones son válidas, se retorna true.
    }

    public boolean validarCombinacion(ArrayList<Ficha> combinacion) { //Método para validar si una combinación de fichas es una serie o una escalera.
        if (combinacion.size() >= 3) {
            Combinaciones comb = new Combinaciones(); 
            comb.setCombinacion(combinacion);//Se asigna la combinación al objeto Combinaciones para verificar la validación.
            return comb.esValida(); 
        }
        return false;//Si la combinación tiene menos de 3 fichas, no puede ser válida.
    }

    public boolean isEmpty() { //Método que verifica si la mesa no tiene fichas.
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 30; j++) {
                if (mesa[i][j].estaOcupada()) { 
                    //Si una celda tiene una ficha, retorna false.
                    return false;
                }
            }
        }
        return true; //Si todas estan libres, retorna true
    }

    public boolean hayGanador() {
        for (Jugador j : jugadores) {
            if (j.getBaraja().size() == 0) { //Si la baraja de fichas quedó vacía, terminó la partida.
                return true;
            }
        }
        return false;
    }

    public boolean verificarPrimeraJugada(ArrayList<ArrayList<Ficha>> jugada, Jugador jugador) { //Método para verificar la primera jugada de 30 puntos.
        if (jugador.getHizoPrimeraJugada()) {
            return true;
        } else {
            // Si no la ha hecho, validamos la jugada actual
            boolean esValida = puntajePrimeraJugada(jugada);
            if (esValida) {
                // Si la jugada es válida, marcamos que ya hizo su primera jugada
                jugador.setHizoPrimeraJugada(true);
                return true;
            } else {
                // Devolver todas las fichas al jugador
                for (ArrayList<Ficha> combinacion : jugada) {
                    jugador.getBaraja().addAll(combinacion);
                }
                JOptionPane.showMessageDialog(null,
                        "Primera jugada debe sumar al menos 30 puntos", "Tus fichas han sido devueltas a tu baraja."
                        + "  Jugada inválida",
                        JOptionPane.WARNING_MESSAGE);
                return false;
            }
        }
    }

    public boolean puntajePrimeraJugada(ArrayList<ArrayList<Ficha>> jugada) { //Método para calcular el puntaja de la primera jugada del jugador
        int total = 0;
        // Recorremos cada combinación en la jugada del jugador
        for (ArrayList<Ficha> combinacion : jugada) {
            Combinaciones comb = new Combinaciones();
            comb.setCombinacion(combinacion); // Le asignamos la lista de fichas
            // Si la combinación no es válida (ni serie ni escalera), la jugada falla

            // Recorremos cada ficha en la combinación
            for (Ficha f : combinacion) {
                // Sumamos el valor de la ficha si no es comodin
                if (!f.isComodin()) {
                    total += f.getNumeroParaJuego();
                }
            }
        }
        return total >= 30;
    }

    public void hacerRespaldoMesa() { //Método para generar una copia de la mesa.
        mesaRespaldo = copiarMesa(mesa);
    }

    public void restaurarMesaDesdeRespaldo() { //Método para restaurar la copia de la mesa.
        if (mesaRespaldo != null) {
            mesa = copiarMesa(mesaRespaldo);
        }
    }

}
