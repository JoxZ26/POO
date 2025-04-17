
package com.mycompany.rummikub;
import java.util.ArrayList;
import java.util.Collections;

public class Combinaciones {
    private ArrayList<Ficha> Combinacion;
    
    public Combinaciones(){
        this.Combinacion = new ArrayList<Ficha>();   
    }

    public ArrayList<Ficha> getCombinacion() {
        return Combinacion;
    }

    public void setCombinacion(ArrayList<Ficha> Combinacion) {
        this.Combinacion = Combinacion;
    }
    
    
    public boolean esSerie() {
    if (Combinacion.size() < 3 || Combinacion.size() > 4) {
        return false;
    }
    int numBase = -1;
    ArrayList<Integer> coloresUsados = new ArrayList<>();
    for (Ficha i : Combinacion) {
        if (i.isComodin()) {
            continue; 
        }
        if (numBase == -1) {
            numBase = i.getNumero();
        } else if (i.getNumero() != numBase) {
            return false; 
        }
        if (coloresUsados.contains(i.getColor())) {
            return false; 
        } else {
            coloresUsados.add(i.getColor());
        }
    }
    return true;
}
    public boolean esEscalera() {
    if (Combinacion.size() < 3) {
        return false; // Mínimo 3 fichas
    }

    // Verificar mismo color y obtener números (ignorando comodines)
    int colorinicial = -1;
    ArrayList<Integer> numeros = new ArrayList<>();
    int comodines = 0;

    for (Ficha ficha : Combinacion) {
        if (ficha.isComodin()) {
            comodines++;
        } else {
            if (colorinicial == -1) {
                colorinicial = ficha.getColor();
            } else if (ficha.getColor() != colorinicial) {
                return false; // Distinto color → no es escalera
            }
            numeros.add(ficha.getNumero());
        }
    }
    // Ordenar los números
    Collections.sort(numeros);
    // Verificar si los números (con ayuda de comodines) son consecutivos
    int huecos = 0;
    for (int i = 1; i < numeros.size(); i++) {
        int diferencia = numeros.get(i) - numeros.get(i - 1);
        if (diferencia == 0) {
            return false; // Números repetidos (no permitido)
        }
        huecos += (diferencia - 1); // Cuántos números faltan entre ellos
    }
    // Comodines deben poder cubrir los huecos
    return (huecos <= comodines);
}
}


