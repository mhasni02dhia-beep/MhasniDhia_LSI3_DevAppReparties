
package clientPackage;

import java.io.Serializable;

public class Resultat implements Serializable {

    private static final long serialVersionUID = 1L;

    private boolean succes;
    private double valeur;
    private String message;

    public Resultat(boolean succes, double valeur, String message) {
        this.succes = succes;
        this.valeur = valeur;
        this.message = message;
    }

    public boolean isSucces() {
        return succes;
    }

    public double getValeur() {
        return valeur;
    }

    public String getMessage() {
        return message;
    }
}
