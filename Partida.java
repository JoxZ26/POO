package com.mycompany.rummikub;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class Partida {
    private int cantidadJugadores;
    private Jugador[] jugadores;
    private ArrayList<ArrayList<Ficha>> mesa;
    private Baraja mazo = new Baraja();
    private int turnoActual;

    public Partida(int cantidadJugadores) {
        mazo.generarFichas();
        setMesa();
        setCantidadJugadores(cantidadJugadores);
        crearJugadores();
        turnoActual = 0;
    }

    // SETTERS
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

    // GETTERS
    public int getCantidadJugadores() {
        return cantidadJugadores;
    }

    public ArrayList<ArrayList<Ficha>> getMesa() {
        return mesa;
    }

    public Jugador getJugadorActual() {
        return jugadores[turnoActual];
    }

    // MÉTODOS
    public void crearJugadores() {
        for (int pos = 0; pos < cantidadJugadores; pos++) {
            Jugador j = new Jugador("Jugador " + (pos + 1), mazo);
            setJugador(pos, j);
        }
    }

    public void mostrarBarajasJugadores() {
        for (Jugador j : jugadores) {
            System.out.println("Baraja de " + j.getNombre() + ":");
            j.mostrarBaraja();
        }
    }

    public void elegirTurnoAleatorio() {
        Random rand = new Random();
        int numRandom = rand.nextInt(cantidadJugadores);
        this.turnoActual = numRandom;
    }

    public void avanzarTurno() {
        turnoActual = (turnoActual + 1) % jugadores.length;
    }

    public void agregarCombinacionAMesa(ArrayList<Ficha> combinacion) {
        mesa.add(combinacion);
    }

    public void jugadorFormaCombinacion(Jugador jugador) {
        Scanner sc = new Scanner(System.in);
        boolean creando = true;

        while (creando) {
            ArrayList<Ficha> seleccionadas = new ArrayList<>();
            boolean terminado = false;

            while (!terminado) {
                jugador.mostrarBaraja();
                System.out.println("Fichas seleccionadas:");
                for (int i = 0; i < seleccionadas.size(); i++) {
                    System.out.println(i + ". " + seleccionadas.get(i));
                }

                System.out.println("\nOpciones:");
                System.out.println("1. Agregar ficha a la combinación");
                System.out.println("2. Eliminar una ficha de la combinación");
                System.out.println("3. Cancelar la combinación");
                System.out.println("4. Terminar y validar combinación");

                int opcion = sc.nextInt();
                switch (opcion) {
                    case 1:
                        System.out.println("Ingrese el índice de la ficha que desea añadir:");
                        int indice = sc.nextInt();
                        if (indice >= 0 && indice < jugador.getBaraja().size()) {
                            Ficha ficha = jugador.getBaraja().get(indice);
                            if (!seleccionadas.contains(ficha)) {
                                seleccionadas.add(ficha);
                            } else {
                                System.out.println("Esa ficha ya fue seleccionada.");
                            }
                        } else {
                            System.out.println("Índice inválido.");
                        }
                        break;
                    case 2:
                        if (seleccionadas.isEmpty()) {
                            System.out.println("No hay fichas para eliminar.");
                            break;
                        }
                        System.out.println("Ingrese el índice de la ficha que desea eliminar:");
                        int indiceFicha = sc.nextInt();
                        if (indiceFicha >= 0 && indiceFicha < seleccionadas.size()) {
                            seleccionadas.remove(indiceFicha);
                        } else {
                            System.out.println("Índice inválido.");
                        }
                        break;
                    case 3:
                        seleccionadas.clear();
                        System.out.println("Combinación cancelada.");
                        return;
                    case 4:
                        terminado = true;
                        break;
                    default:
                        System.out.println("Opción inválida.");
                }
            }

            if (seleccionadas.size() < 3) {
                System.out.println("Una combinación debe tener al menos 3 fichas. Intenta nuevamente.");
                continue;
            }

            Combinaciones combinacion = new Combinaciones();
            combinacion.setCombinacion(seleccionadas);

            if (combinacion.esValida()) {
                agregarCombinacionAMesa(seleccionadas);
                for (Ficha ficha : seleccionadas) {
                    jugador.getBaraja().remove(ficha);
                }
                System.out.println("Combinación agregada.");

                System.out.println("¿Desea formar otra combinación? (Sí = 1, No = 0)");
                int respuesta = sc.nextInt();
                if (respuesta == 0) {
                    creando = false;
                }
            } else {
                System.out.println("Combinación inválida. Intenta nuevamente.");
            }
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
            if (validarEstadoMesa(mesaTemporal)) {
                this.mesa = mesaTemporal;
                return true;
            } else {
                jugadorActual.getBaraja().add(fichaJugador);
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

    public boolean Ganador() {
        for (Jugador j : jugadores) {
            if (j.getBaraja().size() == 0) {
                System.out.println("El ganador es " + j.getNombre());
                return true;
            }
        }
        return false;
    }

    public void mostrarMesa() {
        for (ArrayList<Ficha> fila : mesa) {
            for (Ficha ficha : fila) {
                if (ficha.isComodin()) {
                    System.out.println("Comodín");
                } else {
                    System.out.println("Número: " + ficha.getNumero());
                    System.out.println("Color: " + ficha.getColor());
                }
            }
            System.out.println("---");
        }
    }

    public void menúDeDecisiones() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Opciones:");
        System.out.println("1. Formar nueva combinación");
        System.out.println("2. Añadir ficha a combinación existente");
        System.out.println("3. Terminar turno");
        System.out.println("Seleccione una opción:");
        int opcion = sc.nextInt();
        Jugador jugador = getJugadorActual();

        switch (opcion) {
            case 1:
                jugadorFormaCombinacion(jugador);
                break;
            case 2:
                System.out.println("Ingrese el índice de la combinación en la mesa:");
                int indexCombinacion = sc.nextInt();
                System.out.println("Ingrese el índice de la ficha en su baraja:");
                int indexFicha = sc.nextInt();

                if (indexFicha >= 0 && indexFicha < jugador.getBaraja().size()) {
                    Ficha ficha = jugador.getBaraja().get(indexFicha);
                    ArrayList<ArrayList<Ficha>> copiaMesa = new ArrayList<>(mesa);
                    añadirFichaAJugada(indexCombinacion, ficha, copiaMesa, jugador);
                } else {
                    System.out.println("Índice inválido de ficha.");
                }
                break;
            case 3:
                if (terminarTurno(jugador, mesa)) {
                    avanzarTurno();
                }
                break;
            default:
                System.out.println("Opción inválida.");
        }
    } // ESTA INCOMPLETO
}

