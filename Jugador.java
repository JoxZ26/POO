package com.mycompany.rummikubkendall;


import java.util.ArrayList;

public class Jugador {
    private String nombre;
    private ArrayList<Ficha> Baraja;
    private Baraja mazo;
    private boolean hizoPrimeraJugada;
    
    public Jugador(String nombre, Baraja mazo){
        this.mazo = mazo;
        setNombre(nombre);
        this.Baraja = new ArrayList<Ficha>();
        iniciarBaraja();
        setHizoPrimeraJugada(false);
    }
    
    /* Getters */
    public String getNombre() {
        return nombre;
    }

    public void mostrarBaraja() {
        for (Ficha ficha : Baraja) {
            System.out.println(ficha.getNumero() + " de " + ficha.getColor());
        }
    }

    public ArrayList<Ficha> getBaraja() {
    return Baraja;
}
    
    public boolean getHizoPrimeraJugada() {
        return hizoPrimeraJugada;
    }
    public void setHizoPrimeraJugada(boolean hizoPrimeraJugada) {
        this.hizoPrimeraJugada = hizoPrimeraJugada;
    }
    
    /* Setters */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setMazo(Baraja mazo) {
        this.mazo = mazo;
    }

    /* Métodos */
    public int puntajeTotal(){
        int total = 0;
        for (Ficha temp : Baraja){
            if (temp.isComodin()){
                total += 30;
            }else{
            total += temp.getNumero();
            }
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
    public void eliminarFicha(Ficha ficha) {
        Baraja.remove(ficha);
    }
    public void agregarFicha(Ficha ficha) {
        Baraja.add(ficha);
    }
}

    