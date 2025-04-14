package com.mycompany.rummikub;
import java.util.ArrayList;

public class Jugador {
    private String nombre;
    private ArrayList<Ficha> Baraja;
    
    public Jugador(String nombre){
        setNombre(nombre);
        this.Baraja = new ArrayList<Ficha>();
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void getBaraja() {
        for (Ficha temp : Baraja){
            System.out.println(temp);
        }
    }
    public int puntajeTotal(){
        int total = 0;
        for (Ficha temp : Baraja){
            total += temp.getNumero();
        }
        return total;
    }
}
