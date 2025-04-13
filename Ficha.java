

public class Ficha {
    private int numero;
    private String color;
    private boolean comodin;

    public Ficha(int numero, String color, boolean comodin) {
        setNumero(numero);
        setColor(color);
        setComodin(comodin);
    }

    /* Getters */
    public int getNumero() {
        return numero;
    }
    public String getColor() {
        return color;
    }
    public boolean isComodin() {
        return comodin;
    }

    /* Setters */
    public void setNumero(int numero) {
        this.numero = numero;
    }
    public void setColor(String color) {
        this.color = color;
    }
    public void setComodin(boolean comodin) {
        this.comodin = comodin;
    }

    
}
