package com.mycompany.rummikubkendall;

public class RummikubKendall {

    public static void main(String[] args) {
        Baraja b = new Baraja();
        b.generarFichas();
        System.out.println(b.getBaraja().toString());

    }
}
