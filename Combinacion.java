package com.mycompany.rummikub;
import java.util.ArrayList;

public class Combinacion {
    private ArrayList<Ficha> Combinacion;
    
    public Combinacion(){
        this.Combinacion = new ArrayList<Ficha>();   
    }
    public boolean esSerie() {
    if (Combinacion.size() < 3 || Combinacion.size() > 4) {
        return false;
    }
    int numBase = -1;
    ArrayList<String> coloresUsados = new ArrayList<>();
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


    
}
