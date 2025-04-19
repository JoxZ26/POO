/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.rummikub;

import java.util.Collections;
import java.util.ArrayList;

public class Baraja {

    private ArrayList<Ficha> baraja;

    public Baraja() {
        baraja = new ArrayList<>();
    }

    public ArrayList<Ficha> getBaraja() {
        return baraja;
    }

    public void setBaraja(ArrayList<Ficha> baraja) {
        this.baraja = baraja;
    }
    
    public void generarFichas(){
        int contador=1;
        for (int i = 0; i < 4; i++) {
            for (int j = 1; j < 14; j++) {
                baraja.add(new Ficha(contador++,i,j));
            }
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 1; j < 14; j++) {
                baraja.add(new Ficha(contador++,i,j));
            }
        }
        baraja.add(new Ficha(contador++,4,14));
        baraja.add(new Ficha(contador++,4,14));
        Collections.shuffle(baraja);
    }
    
    public Ficha removerFicha() {
        return baraja.remove(baraja.size() - 1);
    }  
}


