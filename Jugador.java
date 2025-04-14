package com.mycompany.rummikub;
import java.util.ArrayList;

public class Jugador {
    private String nombre;
    private ArrayList<Ficha> Baraja;
    private Mazo mazo;
    
    public Jugador(String nombre,Mazo mazo){
        setNombre(nombre);
        this.Baraja = new ArrayList<Ficha>();
        this.mazo = mazo;
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
    
    public void iniciarBaraja(){
        for (int i = 0; i < 14;i++){
            Ficha añadida = mazo.removerFicha();
            Baraja.add(añadida);
        }
    }
    
    public void comerFicha(){
        Ficha añadida = mazo.removerFicha(); 
            Baraja.add(añadida);
        }
}
