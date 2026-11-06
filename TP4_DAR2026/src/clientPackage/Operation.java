
package clientPackage;

import java.io.Serializable;

public class Operation implements Serializable {

    private static final long serialVersionUID = 1L;

    private double x;
    private char operateur;
    private double y;

    public Operation(double x, char operateur, double y) {
        this.x = x;
        this.operateur = operateur;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public char getOperateur() {
        return operateur;
    }

    public double getY() {
        return y;
    }
}
