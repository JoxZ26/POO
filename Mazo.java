package com.mycompany.rummikub;
import java.util.ArrayList;

public class Mazo {
    private ArrayList<Ficha> mazo;
    
    public Mazo(){
        mazo = new ArrayList<Ficha>();
        String[] colores = {"rojo","azul","amarillo","negro"};
        for (String color : colores) {
            for (int numero = 1; numero <= 13; numero++) {
                mazo.add(new Ficha(numero, color, false)); 
                mazo.add(new Ficha(numero, color, false)); 
            }
        }
        mazo.add(new Ficha(0,"comodin",true));
        mazo.add(new Ficha(0,"comodin",true));
                
    }
    public Ficha comerFicha() {
        if (mazo.isEmpty()) {
        return null; 
    } else {
        return mazo.remove(mazo.size() - 1); 
    }
}

