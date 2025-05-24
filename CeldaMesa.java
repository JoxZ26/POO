package com.mycompany.rummikubkendall;

import javax.swing.*;
import java.awt.*;

public class CeldaMesa extends JPanel { // Clase CeldaMesa, representa las celdas que hay en la mesa que es una matriz
    private boolean ocupada = false; // Atributo privado, tipo booleano, ocupada: Representa si la celda en una posición está ocupada con una ficha
    private Ficha ficha; // Atributo privado, tipo Ficha, ficha: Representa la ficha que está colocada en la celda
    private int i; // Atributo privado, tipo int, i: Representa la fila en la matriz de la mesa
    private int j; // Atributo privado, tipo int, j: Representa la columna en la matriz de la mesa

    public CeldaMesa() { // Constructor por defecto: define visualmente cómo se ve cada celda
        setPreferredSize(new Dimension(60, 80)); // Define el tamaño de la celda
        setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Establece un borde negro alrededor de la celda
        setBackground(new Color(240, 240, 240)); // Establece el color de fondo de la celda (gris claro)
        setLayout(null); // Permite posicionar los elementos (como fichas) manualmente dentro de la celda
    }

    public CeldaMesa(CeldaMesa otra) { // Constructor de copia: permite crear una nueva celda copiando otra existente
        if (otra.getFicha() != null) {
            this.ficha = new Ficha(otra.getFicha()); // Copia la ficha si no es nula, usando el constructor de copia de Ficha
        } else {
            this.ficha = null; // Si no hay ficha, se asigna null
        }
        this.i = otra.i; // Copia la posición i (fila)
        this.j = otra.j; // Copia la posición j (columna)
        this.ocupada = otra.ocupada; // Copia el estado de ocupación
    }

    /* Getters */

    public int getI() { // Retorna la fila en la matriz
        return i;
    }

    public Ficha getFicha() { // Retorna la ficha que está en la celda (puede ser null)
        return ficha;
    }

    public int getJ() { // Retorna la columna en la matriz
        return j;
    }

    public boolean estaOcupada() { // Retorna true si la celda está ocupada, false si está libre
        return ocupada;
    }

    /* Setters */

    public void setI(int i) { // Asigna una nueva fila a la celda
        this.i = i;
    }

    public void setJ(int j) { // Asigna una nueva columna a la celda
        this.j = j;
    }

    public void setFicha(Ficha ficha) { // Asigna una ficha a la celda
        this.ficha = ficha;
        if (ficha != null) {
            ocupar(); // Si se asigna una ficha, marca la celda como ocupada
        } else {
            liberar(); // Si se asigna null, marca la celda como libre
        }
    }

    /* Métodos */

    public void ocupar() { // Método que marca la celda como ocupada
        ocupada = true;
    }

    public void liberar() { // Método que marca la celda como libre
        ocupada = false;
    }

    public void limpiar() { // Método que elimina cualquier componente visual y deja la celda vacía
        removeAll(); // Quita todos los componentes visuales que haya dentro del panel
        liberar();   // Marca la celda como no ocupada
        ficha = null; // Elimina la referencia a la ficha
    }
}
