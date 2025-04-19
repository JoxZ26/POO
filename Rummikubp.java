
package com.mycompany.rummikub;
import java.util.Scanner;

import java.util.Scanner;


public class Rummikubp {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int cantidadJugadores = 0;

        System.out.println("Bienvenido a Rummikub");
        while (cantidadJugadores < 2 || cantidadJugadores > 4) {
            System.out.print("Ingrese la cantidad de jugadores (2-4): ");
            cantidadJugadores = sc.nextInt();
            if (cantidadJugadores < 2 || cantidadJugadores > 4) {
                System.out.println("Número inválido. Debe ser entre 2 y 4.");
            }
        }

        Partida partida = new Partida(cantidadJugadores);
        partida.mostrarMesa();

        boolean juegoActivo = true;

        while (juegoActivo) {
            Jugador jugadorActual = partida.getJugadorActual();
            System.out.println("\nTurno de " + jugadorActual.getNombre());

            partida.jugadorFormaCombinacion(jugadorActual);
            partida.mostrarMesa();

            if (partida.Ganador()) {
                juegoActivo = false;
                break;
            }

            partida.avanzarTurno();
        }

        System.out.println("Fin del juego.");
    }
}
