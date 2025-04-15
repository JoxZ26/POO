import java.util.ArrayList;

public class Partida {
    private int cantidadJugadores;
    private Jugador[] jugadores;
    private ArrayList<ArrayList<Ficha>> mesa;
    private Mazo mazo = new Mazo();

    public Partida(int cantidadJugadores) {
        setCantidadJugadores(cantidadJugadores);
        crearJugadores();
    }

    /* Getters */
    public int getCantidadJugadores() {
        return cantidadJugadores;
    }

    /* Setters */
    public void setCantidadJugadores(int cantidadJugadores) {
        this.cantidadJugadores = cantidadJugadores;
        this.jugadores = new Jugador[cantidadJugadores];
    }

    public void setJugador(int pos, Jugador jugador) {
        this.jugadores[pos] = jugador;
    }

    public void setMesa() {
        this.mesa = new ArrayList<>();
    }

    /* Métodos */
    public void crearJugadores() {
        for (int pos = 0; pos < cantidadJugadores; pos++) {
            Jugador j = new Jugador(null, mazo); // recibir nombre
            setJugador(pos, j);
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
