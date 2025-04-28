package com.mycompany.rummikubkendall;
import javax.swing.*;
import java.awt.*;

public class CeldaMesa extends JPanel {
    private boolean ocupada = false;
    
    private Ficha ficha;
    private int I;
    private int J;

public Ficha getFicha() {
    return ficha;
}

    public int getI() {
        return I;
    }

    public void setI(int I) {
        this.I = I;
    }

    public int getJ() {
        return J;
    }

    public void setJ(int J) {
        this.J = J;
    }

    

public void setFicha(Ficha ficha) {
    this.ficha = ficha;
    if (ficha != null) {
        ocupar();
    } else {
        liberar();
    }
}


    public CeldaMesa() {
        setPreferredSize(new Dimension(60, 80)); // tamaño de la celda
        setBorder(BorderFactory.createLineBorder(Color.BLACK)); // borde visible
        setBackground(new Color(240, 240, 240)); // color de fondo
        setLayout(null); // permite posicionar la ficha manualmente dentro
    }

    public boolean estaOcupada() {
        return ocupada;
    }

    public void ocupar() {
        ocupada = true;
    }

    public void liberar() {
        ocupada = false;
    }

}