package com.mycompany.rummikubkendall;
import java.util.ArrayList;

public class Combinaciones { // Clase Combinaciones: representa un grupo de fichas que pueden formar una serie o una escalera
    private ArrayList<Ficha> combinacion; // Atributo privado, tipo ArrayList<Ficha>, combinacion: almacena las fichas que forman la combinación

    public Combinaciones() { // Constructor por defecto: inicializa la combinación como una lista vacía
        this.combinacion = new ArrayList<Ficha>();
    }

    /* Getters */
    public ArrayList<Ficha> getCombinacion() { // Retorna la lista de fichas actuales en la combinación
        return combinacion;
    }

    /* Setters */
    public void setCombinacion(ArrayList<Ficha> Combinacion) { // Asigna una nueva lista de fichas a la combinación
        this.combinacion = Combinacion;
    }

    /* Métodos */
    public boolean esSerie() { // Método que verifica si la combinación actual forma una serie válida
        if (combinacion.size() < 3 || combinacion.size() > 4) { // Una serie válida debe tener entre 3 y 4 fichas
            return false;
        }

        int numBase = -1; // Variable para almacenar el número base que todas las fichas deben compartir
        ArrayList<Integer> coloresUsados = new ArrayList<>(); // Lista para evitar colores repetidos

        for (Ficha i : combinacion) {
            if (i.isComodin()) {
                continue; // Los comodines se permiten y se omiten en esta verificación
            }

            if (numBase == -1) {
                numBase = i.getNumeroParaJuego(); // Se toma el número de la primera ficha no comodín como referencia
            } else if (i.getNumeroParaJuego() != numBase) {
                return false; // Si alguna ficha tiene un número distinto, no es serie
            }

            if (coloresUsados.contains(i.getColor())) {
                return false; // No se permiten colores repetidos en una serie
            } else {
                coloresUsados.add(i.getColor()); // Se registra el color usado
            }
        }

        return true; // Si pasa todas las verificaciones, es una serie válida
    }

    public boolean esEscalera() { // Método que verifica si la combinación actual forma una escalera válida
        if (combinacion.size() < 3) {
            return false; // Una escalera válida debe tener al menos 3 fichas
        }

        int colorInicial = -1; // Variable para asegurar que todas las fichas (excepto comodines) sean del mismo color
        ArrayList<Integer> numeros = new ArrayList<>(); // Lista para guardar los números de las fichas (sin comodines)
        int comodines = 0; // Contador de comodines encontrados

        for (Ficha ficha : combinacion) {
            if (ficha.isComodin()) {
                comodines++; // Se cuentan los comodines para evaluar si pueden completar la secuencia
            } else {
                if (colorInicial == -1) {
                    colorInicial = ficha.getColor(); // Se toma el color de la primera ficha no comodín
                } else if (ficha.getColor() != colorInicial) {
                    return false; // Si alguna ficha es de color distinto, no es escalera
                }

                numeros.add(ficha.getNumeroParaJuego()); // Se guarda el número de la ficha
            }
        }

        // Verificar si los números están en orden estrictamente ascendente
        for (int i = 1; i < numeros.size(); i++) {
            if (numeros.get(i) <= numeros.get(i - 1)) {
                return false; // No debe haber repeticiones ni descensos
            }
        }

        // Calcular cuántos huecos hay entre los números (para saber si los comodines los pueden llenar)
        int huecos = 0;
        for (int i = 1; i < numeros.size(); i++) {
            huecos += (numeros.get(i) - numeros.get(i - 1) - 1); // Huecos = diferencia - 1
        }

        return (huecos <= comodines); // La escalera es válida si los comodines pueden llenar los huecos
    }

    public boolean esValida() { // Método que determina si la combinación es válida (ya sea serie o escalera)
        if (esSerie() || esEscalera()) {
            return true; // Si cumple alguna de las dos condiciones, es válida
        }
        return false; // Si no cumple ninguna, no es válida
    }
}
