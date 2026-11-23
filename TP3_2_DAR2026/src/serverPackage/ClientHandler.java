
package serverPackage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import calculatricePackage.Operation;
import calculatricePackage.Resultat;

public class ClientHandler implements Runnable {

    private Socket socket;
    private int numero;

    public ClientHandler(Socket socket, int numero) {
        this.socket = socket;
        this.numero = numero;
    }

    @Override
    public void run() {

        try (
            ObjectOutputStream sortie =
                new ObjectOutputStream(socket.getOutputStream());

            ObjectInputStream entree =
                new ObjectInputStream(socket.getInputStream())
        ) {

            sortie.flush();

            System.out.println(
                "Client " + numero + " connecté : "
                + socket.getRemoteSocketAddress()
            );

            while (true) {

                Operation operation =
                    (Operation) entree.readObject();

                // Le client envoie null pour terminer
                if (operation == null) {
                    break;
                }

                double x = operation.getX();
                double y = operation.getY();
                char operateur = operation.getOperateur();

                double valeur = 0;
                String message;
                boolean succes = true;

                switch (operateur) {

                    case '+':
                        valeur = x + y;
                        message = "Addition réussie";
                        break;

                    case '-':
                        valeur = x - y;
                        message = "Soustraction réussie";
                        break;

                    case '*':
                        valeur = x * y;
                        message = "Multiplication réussie";
                        break;

                    case '/':
                        if (y == 0) {
                            succes = false;
                            message = "Erreur : division par zéro";
                        } else {
                            valeur = x / y;
                            message = "Division réussie";
                        }
                        break;

                    default:
                        succes = false;
                        message = "Erreur : opérateur invalide";
                        break;
                }

                // Incrémenter le compteur uniquement
                // lorsqu'une opération est correctement calculée
                if (succes) {
                    int total = Serveur.incrementerCompteur();

                    System.out.println(
                        "Opération : " + x + " " + operateur + " " + y
                    );

                    System.out.println("Résultat : " + valeur);

                    System.out.println(
                        "Nombre total d'opérations : " + total
                    );

                    System.out.println("---------------------------");
                }

                Resultat resultat =
                    new Resultat(succes, valeur, message);

                sortie.writeObject(resultat);
                sortie.flush();
            }

            System.out.println("Client " + numero + " déconnecté.");

        } catch (IOException | ClassNotFoundException e) {

            System.out.println(
                "Erreur avec le client " + numero + " : "
                + e.getMessage()
            );

        } finally {

            try {
                socket.close();
            } catch (IOException e) {
                System.out.println("Erreur fermeture socket.");
            }
        }
    }
}
