package com.mycompany.rummikubkendall;
import java.util.Collections; //Importar la clase Collections, para utilizar utilidades como revolver una lista.
import java.util.ArrayList; //Importar la clase ArrayList para utilizar arrays dinámicos.

public class Baraja { //Clase Baraja. Representa la baraja donde se conservan todas las fichas.

    private ArrayList<Ficha> baraja; //Atributo privado, tipo Array dinámico de fichas, baraja: Representa la baraja como una lista de fichas.

    public Baraja() { //Constructor de Baraja.
        baraja = new ArrayList<>();
    }
    
    /* Getters */
    public ArrayList<Ficha> getBaraja() {
        return baraja;
    }

    /* Setters */
    public void setBaraja(ArrayList<Ficha> baraja) {
        this.baraja = baraja;
    }
    
    public void generarFichas(){ //Método para generar las 106 fichas de la baraja.
        int contador=1;
        // Primera vuelta: se generan 4 colores × 13 números = 52 fichas
        for (int i = 0; i < 4; i++) {
            for (int j = 1; j < 14; j++) { 
                baraja.add(new Ficha(contador++,i,j)); // i = color; j = valor numérico.
            }
        }
        // Segunda vuelta: se duplican las 52 fichas anteriores:  52 × 2 = 104
        for (int i = 0; i < 4; i++) {
            for (int j = 1; j < 14; j++) {
                baraja.add(new Ficha(contador++,i,j));
            }
        }
        // Se añaden los 2 comodines para un total de : 104 + 2 = 106 fichas
        baraja.add(new Ficha(contador++,4,14)); //Comodín 1.
        baraja.add(new Ficha(contador++,4,14)); //Comodín 2.
        Collections.shuffle(baraja); //Se revuelve la baraja de forma aleatoria.
    }
    
    public Ficha removerFicha() { //Método para remover una ficha de la baraja.
        return baraja.remove(baraja.size() - 1);
    }  
    public int getCantidadFichas() { //Método para obtejer la cantidas de fichas restantes en la baraja.
        return baraja.size();
    }
}