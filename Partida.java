
package com.mycompany.rummikubp;
import java.util.ArrayList;

public class Partida {
    private int cantidadJugadores;
    private Jugador[] jugadores;
    private ArrayList<ArrayList<Ficha>> mesa;
    private Baraja mazo = new Baraja();

    public Partida(int cantidadJugadores) {
    mazo.generarFichas();
    setMesa();
    setCantidadJugadores(cantidadJugadores);
    crearJugadores();
}
    public int getCantidadJugadores() {
        return cantidadJugadores;
    }

    public void setCantidadJugadores(int cantidadJugadores) {
        this.jugadores = new Jugador[cantidadJugadores];
    }

    public void setJugador(int pos, Jugador jugador) {
        this.jugadores[pos] = jugador;
    }

    public void setMesa() {
        this.mesa = new ArrayList<>();
    }

    public ArrayList<ArrayList<Ficha>> getMesa(int index) {
        return mesa;
    }
    
    /* Métodos */
    public void agregarCombinacionAMesa(ArrayList<Ficha> combinacion) {
    mesa.add(combinacion);
}
    public void crearJugadores() {
    for (int pos = 0; pos < cantidadJugadores; pos++) {
        Jugador j = new Jugador("Jugador " + (pos+1), mazo);
        setJugador(pos, j);
    }
}
    public boolean validarEstadoMesa(ArrayList<ArrayList<Ficha>> mesaTemporal) {
    for (ArrayList<Ficha> combinacion : mesaTemporal) {
        Combinaciones combinacionTemp = new Combinaciones();
        combinacionTemp.setCombinacion(combinacion);
        if (!(combinacionTemp.esEscalera() || combinacionTemp.esSerie())) {
            return false; 
        }
    }
    return true; 
}

    public boolean añadirFichaAJugada(int indexCombinacion, Ficha fichaJugador, ArrayList<ArrayList<Ficha>> mesaTemporal, Jugador jugadorActual) {
    if (indexCombinacion < 0 || indexCombinacion >= mesaTemporal.size()) {
        return false; 
    }
    ArrayList<Ficha> combinacionModificada = new ArrayList<>(mesaTemporal.get(indexCombinacion));
    combinacionModificada.add(fichaJugador);

    Combinaciones combinacion = new Combinaciones();
    combinacion.setCombinacion(combinacionModificada);

    if (combinacion.esEscalera() || combinacion.esSerie()) {
        mesaTemporal.set(indexCombinacion, combinacionModificada);
         jugadorActual.getBaraja().remove(fichaJugador);
         if (validarEstadoMesa(mesaTemporal)){
             this.mesa = mesaTemporal;
             return true;
         }else{
            jugadorActual.getBaraja().add(fichaJugador); 
            mesaTemporal.set(indexCombinacion, new ArrayList<>(mesaTemporal.get(indexCombinacion)));  // Restaurar el estado original de la combinación
            return false;
         }
    }
    return false;
}
    public boolean terminarTurno(Jugador jugadorActual, ArrayList<ArrayList<Ficha>> mesaTemporal) {
    if (validarEstadoMesa(mesaTemporal)) {
        this.mesa = mesaTemporal;
        System.out.println("Turno validado y mesa actualizada.");
        return true;
    } else {
        System.out.println("El turno no es válido. La mesa no se actualiza.");
        return false;
    }
}


     public void mostrarMesa() {
        for (ArrayList<Ficha> fila : mesa) {
            for (Ficha ficha : fila) {
                if (ficha.isComodin()){
                    System.out.println("Comodin");
                }
                else {
                    System.out.println("Numero: " + ficha.getNumero());
                    System.out.println("Color: " + ficha.getColor());
                }
            }
        }
    }
}
