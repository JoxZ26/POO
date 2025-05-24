package com.mycompany.rummikubkendall;

public class Ficha { //Clase Ficha. Representa las fichas con las que se juega la partida.
    private int id; // Atributo privado, tipo int, id: Un identificador para asociar cada ficha.
    private int color; //Atributo privado, tipo int, color: Color de cada ficha, representado como entero.
    private int numero; // Atributo privado, tipo int, número: Valor numérico de cada ficha.
    private int valorTemporal; //Atributo privado, tipo int, valorTemporal: Caso especial en caso de ser una ficha de tipo comodín.

    public Ficha(int id, int color, int numero) { //Constructor de fichas.
        this.id = id;
        this.color = color;
        this.numero = numero;
        this.valorTemporal = -1;
    }
    
    public Ficha(Ficha copia) { //Constructor para copiar una ficha.
        this.id = copia.id;
        this.color = copia.color;
        this.numero = copia.numero;
        this.valorTemporal = copia.valorTemporal;
    }

    /* Getters */
    public int getValorTemporal() {
        return valorTemporal;
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
    
    public boolean isComodin() { //Método para verificar si una ficha es un comodín.
        return numero == 14; //Comodín tiene un valor numérico especial de 14.
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
    
    public void setValorTemporal(int valor) {
        this.valorTemporal = valor;
    }
    
    /* Metodos */
    public int getNumeroParaJuego() {//Método que retorna el valor númerico que debe usarse en el juego para una ficha.
        //Si es un comodín y su valor temporal es distinto de -1. se devuelve el valor temporal. Sino, se devuelve el número original de la ficha.
        return isComodin() && valorTemporal != -1 ? valorTemporal : numero; 
    }
    
    /* toString */
    public String toString() {
        return "#" + id + "|C" + color + "N" + numero ;
    } 
}