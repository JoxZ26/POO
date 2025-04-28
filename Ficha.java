package com.mycompany.rummikubkendall;
public class Ficha {
    private int id;
    private int color;
    private int numero;
    private int valorTemporal;

    public Ficha(int id, int color, int numero) {
        this.id = id;
        this.color = color;
        this.numero = numero;
        this.valorTemporal = -1;
    }

    /* Getters */
    public int getValorTemporal() {
        return valorTemporal;
    }

    public void setValorTemporal(int valor) {
        this.valorTemporal = valor;
    }
    
    public int getId() {
        return id;
    }

    public int getColor() {
        return color;
    }
    
    public int getNumero() {
        return numero;
    }
    
    public boolean isComodin() {
        return numero == 14;
    }
    
    
    /* Setters */
    public void setId(int id) {
        this.id = id;
    }
    
    public void setColor(int color) {
        this.color = color;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }
    
    public int getNumeroParaJuego() {
        return isComodin() && valorTemporal != -1 ? valorTemporal : numero;
    }
    
    /* toString */
    public String toString() {
        return "#" + id + "|C" + color + "N" + numero ;
    } 
}