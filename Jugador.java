package com.mycompany.rummikubkendall;

import java.util.ArrayList; //Importar la clase ArrayList para utilizar arrays dinámicos.

public class Jugador { // Clase jugador. Representa a los jugadores de la partida.

    private int puntuacion; //Atributo privado, tipo entero, puntuación: La puntuación generada al final de la partida por un jugador.
    private int puntacionActual;
    private String nombre; // Atributo privado, tipo String, nombre: El nombre de cada jugador.
    private ArrayList<Ficha> baraja; // Atributo privado, tipo Array dinámico de fichas, baraja: Mano de cada jugador. 
    private CeldaMesa[][] mesaSecundaria; //Atributo privado, tipo Array de objetos CeldaMesa, mesaSecundaria: Copia de la mesa para la primera jugada.
    private Baraja mazo; //Atributo privado, tipo Baraja, mazo: El mazo de cartas (lugar donde el jugador come fichas).
    private boolean hizoPrimeraJugada; //Atributo privado, tipo boolean, hizoPrimeraJugada: Válida si el jugador realizó la primera jugada de 30 puntos.
    private boolean jugoTurno; //Atributo privado, tipo boolean, jugoTurno: Válida si el jugador movió alguna carta en su turno.

    public Jugador(String nombre, Baraja mazo) { //Primer constructor: Se utiliza para iniciar una partida desde 0.
        puntacionActual = 0;
        puntuacion = 0; 
        this.mazo = mazo;
        setNombre(nombre);
        this.baraja = new ArrayList<Ficha>();
        setMesaSecundaria();
        iniciarBaraja();
        setHizoPrimeraJugada(false);

    }
    
    public Jugador(String nombre, Baraja mazo, int puntuacion){ //Segundo constructor: Se utiliza para jugar de nuevo una partida.
        puntacionActual = 0;
        setPuntuacion(puntuacion);
        setMazo(mazo);
        setNombre(nombre);
        setMesaSecundaria();
        this.baraja = new ArrayList<>(); 
        iniciarBaraja();
        setHizoPrimeraJugada(false);    
    }

    /* Getters */
    public String getNombre() {
        return nombre;
    }
    
    public int getPuntuacion() {
        return puntuacion;
    }
    
    public CeldaMesa[][] getMesaSecundaria() {
        return mesaSecundaria;
    }

    public ArrayList<Ficha> getBaraja() {
        return baraja;
    }

    public boolean getHizoPrimeraJugada() {
        return hizoPrimeraJugada;
    }
    
    public boolean isJugoTurno() {
        return jugoTurno;
    }

    public int getPuntacionActual() {
        return puntacionActual;
    }
    

    /* Setters */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setMazo(Baraja mazo) {
        this.mazo = mazo;
    }

    public void setJugoTurno(boolean jugoTurno) {
        this.jugoTurno = jugoTurno;
    }
    
    public void setHizoPrimeraJugada(boolean hizoPrimeraJugada) {
        this.hizoPrimeraJugada = hizoPrimeraJugada;
    }

    public void setMesaSecundaria() { //Crea una mesa identica a la original, donde cada jugador hará su primera jugada.
        this.mesaSecundaria = new CeldaMesa[30][30]; //Matriz estática de celdas, 30x30.
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 30; j++) {
                CeldaMesa celda = new CeldaMesa(); //Crea una nueva celda.
                celda.setI(i); //Añade el índice de fila a una celda.
                celda.setJ(j); // Añade el índice de columna a una celda.
                mesaSecundaria[i][j] = celda; //Añade una celda en una posición de la mesa.
            }
        }
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }
    

    /* Metodos */
    public void mostrarBaraja() { //Método que muestra la mano del jugador.
        for (Ficha ficha : baraja) {
            System.out.println(ficha.getNumero() + " de " + ficha.getColor());
        }
    }
    
    public void clearMesaSecundaria() { //Limpia la mesa secundaria del jugador.
        for (int i = 0; i < mesaSecundaria.length; i++) {
            for (int j = 0; j < mesaSecundaria[i].length; j++) {
                CeldaMesa celda = mesaSecundaria[i][j];
                if (celda != null) {
                    celda.removeAll(); //Remueve el valor contenido en las celdas.
                    celda.setFicha(null); //Establece su valor como nulo.
                    celda.liberar(); //Marca la celda como liberada.
                    celda.revalidate();
                    celda.repaint(); //Actualiza.
                }
            }
        }
    }

    public void sumarPuntos(int puntosNuevos) {//Método para sumar el puntaje ganado al puntaje actual del jugador.
        this.puntuacion += puntosNuevos;
        this.puntacionActual += puntosNuevos;
    }

    public void restarPuntos(int puntosNuevos) { //Método para restar el puntaje ganado al puntaje actual del jugador.
        this.puntuacion -= puntosNuevos;
        this.puntacionActual -= puntosNuevos;
    }
    
    public int puntajeTotal() { //Método para calcular el puntaje generado por el jugador en la partida y retornarlo.
        int total = 0; //Un contador para el puntaje.
        for (Ficha temp : baraja) {//Se recorre cada ficha de su mano.
            if (temp.isComodin()) { 
                total += 30; //Si tiene una ficha de tipo comodín, sumar 30 puntos.
            } else {
                total += temp.getNumero(); //Sumar el valor numérico de la ficha.
            }
        }
        return total; //Retorna el puntaje generado.
    }

    public void iniciarBaraja() { //Método para añadir fichas a la mano del jugador.
        for (int i = 0; i < 14; i++) {
            Ficha añadida = mazo.removerFicha();
            baraja.add(añadida); //Toma 14 fichas del maso.
        }
    }

    public void comerFicha() { //Método para tomar una ficha del maso, estilo pila (LIFO).
        Ficha añadida = mazo.removerFicha();
        baraja.add(añadida); //Toma la ficha que esté más arriba y la añade a su mano.
    }

    public void eliminarFicha(Ficha ficha) { //Método que remueve una ficha de la mano del jugador.
        baraja.remove(ficha);
    }

    public void agregarFicha(Ficha ficha) { //Método que agrega una ficha a la mano del jugador.
        baraja.add(ficha);
    }
}
